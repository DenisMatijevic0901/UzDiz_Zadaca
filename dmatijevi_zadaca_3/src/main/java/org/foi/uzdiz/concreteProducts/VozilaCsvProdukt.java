package org.foi.uzdiz.concreteProducts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.foi.uzdiz.classes.Podrucje;
import org.foi.uzdiz.classes.Vozilo;
import org.foi.uzdiz.products.CsvProdukt;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;

public class VozilaCsvProdukt implements CsvProdukt {

  private String linija;

  @Override
  public void ucitaj(String datoteka) {
    File datotekaVozila = new File(datoteka);

    if (datotekaVozila.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(datotekaVozila);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String prvaLinija = bufferedReader.readLine();

        if (prvaLinija != null
            && prvaLinija.equals("Registracija;Opis;Kapacitet težine u kg;Kapacitet prostora u m3;"
                + "Redoslijed;Prosječna brzina;Područja po rangu;Status")) {
          boolean ostatakDatotekePrazan = true;
          while ((linija = bufferedReader.readLine()) != null) {
            if (!linija.trim().isEmpty()) {
              ostatakDatotekePrazan = false;
              String[] atributi = linija.split(";");
              if (atributi.length == 8) {
                dodajVozilo(atributi, linija, datoteka);
              } else {
                GreskeSingleton.getInstance().dohvatiGreskuS(
                    "U datoteci " + datoteka + " ovaj zapis nema sve atribute: " + linija);
              }
            }
            if (ostatakDatotekePrazan) {
              GreskeSingleton.getInstance()
                  .dohvatiGreskuS("Ostatak datoteke je prazan: " + datoteka);
            }
          }
          bufferedReader.close();
        } else {
          GreskeSingleton.getInstance()
              .dohvatiGreskuS("Prva linija datoteke " + datoteka + " nije u zadanom formatu.");
        }
      } catch (Exception e) {
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Datoteka " + datoteka + ","
                + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija + " "
                + e.getMessage());
      }

    } else {
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Datoteka s ovim nazivom ne postoji: " + datoteka);
    }
  }

  private void dodajVozilo(String[] atributi, String linija, String datoteka) {
    boolean omoguciUnos = true;
    String registracija = atributi[0];
    String opis = atributi[1];
    String podrucjaPoRangu = atributi[6].trim();
    String status = atributi[7].trim();

    omoguciUnos = provjeriRegexPodrucjaPoRangu(podrucjaPoRangu, datoteka);

    for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      if (registracija.equals(vozilo.dohvatiRegistraciju())) {
        omoguciUnos = false;
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + " zapis: "
            + linija + " - Vozilo s tom registracijom već postoji.");
      }
    }

    if (registracija.trim().isEmpty() || opis.trim().isEmpty() || podrucjaPoRangu.trim().isEmpty()
        || status.trim().isEmpty()) {
      omoguciUnos = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Datoteka " + datoteka + ","
              + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija
              + " - postoji prazan atribut");
    }

    String[] podrucja = podrucjaPoRangu.split(",");
    StringBuilder postojanaPodrucja = provjeriPostojanjePodrucja(podrucja, datoteka);

    try {
      int redoslijed = Integer.parseInt(atributi[4]);
      String regexFloat = "^[0-9]+(?:[.,][0-9]+)?$";
      Float kapacitetTezineUKg = Float.parseFloat(atributi[2].replace(",", "."));
      Float kapacitetProstoraUM3 = Float.parseFloat(atributi[3].replace(",", "."));
      Float prosjecnaBrzina = Float.parseFloat(atributi[5].replace(",", "."));

      if (!atributi[2].matches(regexFloat) || !atributi[3].matches(regexFloat)
          || !atributi[5].matches(regexFloat)) {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka: " + datoteka + ","
            + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
        omoguciUnos = false;
      }

      if (omoguciUnos) {
        TvrtkaSingleton.getInstance().spremiVozilo(registracija, opis, kapacitetTezineUKg,
            kapacitetProstoraUM3, redoslijed, prosjecnaBrzina, postojanaPodrucja.toString(),
            status);
      }
    } catch (Exception e) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
    }
  }

  private boolean provjeriRegexPodrucjaPoRangu(String podrucjaPoRangu, String datoteka) {
    String regexKomandaPodrucjaPoRangu = "^\\d+(,\\d+)*$";
    if (podrucjaPoRangu.trim().matches(regexKomandaPodrucjaPoRangu)) {
      return true;
    } else {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu za zapis: " + linija + " (podrucja po rangu)");
      return false;
    }
  }

  private StringBuilder provjeriPostojanjePodrucja(String[] podrucja, String datoteka) {
    StringBuilder postojanaPodrucja = new StringBuilder();
    boolean postoji = false;
    for (String trenutnoPodrucje : podrucja) {
      for (Podrucje podrucje : TvrtkaSingleton.getInstance().listaPodrucja) {
        if (podrucje.dohvatiId() == Integer.parseInt(trenutnoPodrucje)) {
          postojanaPodrucja.append(trenutnoPodrucje);
          postojanaPodrucja.append(",");
          postoji = true;
        }
      }
      if (postoji == false) {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + " za zapis "
            + linija + ", područje s ID-jem " + trenutnoPodrucje + " ne postoji u listi područja.");
      }
    }
    if (postojanaPodrucja.length() > 0) {
      postojanaPodrucja.deleteCharAt(postojanaPodrucja.length() - 1);
    }
    return postojanaPodrucja;
  }
}
