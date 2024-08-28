package org.foi.uzdiz.concreteProducts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.foi.uzdiz.classes.Mjesto;
import org.foi.uzdiz.classes.Ulica;
import org.foi.uzdiz.products.CsvProdukt;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;

public class MjestaCsvProdukt implements CsvProdukt {

  private String linija;

  @Override
  public void ucitaj(String datoteka) {
    File datotekaMjesta = new File(datoteka);

    if (datotekaMjesta.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(datotekaMjesta);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String prvaLinija = bufferedReader.readLine();

        if (prvaLinija != null && prvaLinija.equals("id; naziv; ulica,ulica,ulica,...")) {
          boolean ostatakDatotekePrazan = true;
          while ((linija = bufferedReader.readLine()) != null) {
            if (!linija.trim().isEmpty()) {
              ostatakDatotekePrazan = false;
              String[] atributi = linija.split(";");
              if (atributi.length == 3) {
                dodajMjesto(atributi, linija, datoteka);
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

  private void dodajMjesto(String[] atributi, String linija, String datoteka) {
    boolean omoguciUnos = true;
    int id = 0;
    try {
      id = Integer.parseInt(atributi[0]);
    } catch (Exception e) {
    }

    String naziv = atributi[1].trim();
    String ulica = atributi[2].trim();

    omoguciUnos = provjeriRegexUlica(ulica, datoteka);

    for (Mjesto mjesto : TvrtkaSingleton.getInstance().listaMjesta) {
      if (id == mjesto.dohvatiId()) {
        omoguciUnos = false;
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + " zapis: "
            + linija + " - Mjesto s tim id-jem već postoji.");
      }
    }

    if (naziv.trim().isEmpty() || ulica.trim().isEmpty()) {
      omoguciUnos = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Datoteka " + datoteka + ","
              + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija
              + " - postoji prazan atribut");
    }

    String[] ulice = ulica.split(",");
    StringBuilder postojaneUlice = provjeriPostojanjeUlice(ulice, datoteka);

    try {
      String regexInt = "^[\\d]+$";

      if (!atributi[0].matches(regexInt)) {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka: " + datoteka + ","
            + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
        omoguciUnos = false;
      }

      if (omoguciUnos) {
        TvrtkaSingleton.getInstance().spremiMjesto(id, naziv, postojaneUlice.toString());
      }
    } catch (Exception e) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
    }
  }

  private StringBuilder provjeriPostojanjeUlice(String[] ulice, String datoteka) {
    StringBuilder postojaneUlice = new StringBuilder();
    boolean postoji = false;
    for (String trenutnaUlica : ulice) {
      for (Ulica ulica : TvrtkaSingleton.getInstance().listaUlica) {
        if (ulica.dohvatiId() == Integer.parseInt(trenutnaUlica)) {
          postojaneUlice.append(trenutnaUlica);
          postojaneUlice.append(",");
          postoji = true;
        }
      }
      if (postoji == false) {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + " za zapis "
            + linija + ", ulica s ID-jem " + trenutnaUlica + " ne postoji u listi ulica.");
      }
    }
    if (postojaneUlice.length() > 0) {
      postojaneUlice.deleteCharAt(postojaneUlice.length() - 1);
    }
    return postojaneUlice;
  }

  private boolean provjeriRegexUlica(String ulica, String datoteka) {
    String regexKomandaUlica = "^\\d+(,\\d+)*$";
    if (ulica.trim().matches(regexKomandaUlica)) {
      return true;
    } else {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu za zapis: " + linija + " (ulica)");
      return false;
    }
  }
}


