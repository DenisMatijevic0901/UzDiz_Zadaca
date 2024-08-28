package org.foi.uzdiz.concreteProducts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.foi.uzdiz.classes.Vrsta;
import org.foi.uzdiz.products.CsvProdukt;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;

public class VrsteCsvProdukt implements CsvProdukt {

  private String linija;
  private Float novaMaksimalnaTezina = null;

  @Override
  public void ucitaj(String datoteka) {
    File datotekaVrste = new File(datoteka);

    if (datotekaVrste.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(datotekaVrste);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String prvaLinija = bufferedReader.readLine();

        if (prvaLinija != null
            && prvaLinija.equals("Oznaka;Opis;Visina;Širina;Dužina;Maksimalna težina;"
                + "Cijena;Cijena hitno;CijenaP;CijenaT")) {
          boolean ostatakDatotekePrazan = true;
          while ((linija = bufferedReader.readLine()) != null) {
            if (!linija.trim().isEmpty()) {
              ostatakDatotekePrazan = false;
              String[] atributi = linija.split(";");
              if (atributi.length == 10) {
                dodajVrstu(atributi, linija, datoteka);
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

  private void dodajVrstu(String[] atributi, String linija, String datoteka) {
    boolean omoguciUnos = true;
    String oznaka = atributi[0];
    String opis = atributi[1];
    String regexOznaka = "^(A|B|C|D|E|X)$";
    String regexTipskiPaket = "^(A|B|C|D|E)$";
    String regexNula = "^[0](?:[.,][0]+)?$";

    for (Vrsta vrsta : TvrtkaSingleton.getInstance().listaVrste) {
      if (oznaka.equals(vrsta.dohvatiOznaku())) {
        omoguciUnos = false;
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + " zapis: "
            + linija + " - Vrsta paketa s tom oznakom već postoji.");
      }
    }

    omoguciUnos = provjeriVrste(oznaka, opis, atributi, linija, datoteka, omoguciUnos, regexOznaka,
        regexTipskiPaket, regexNula);

    if (oznaka.matches(regexTipskiPaket)) {
      unesiTipskiPaket(atributi, omoguciUnos, datoteka, oznaka, opis);
    }

    if (oznaka.equals("X")) {
      unesiPaketX(atributi, omoguciUnos, datoteka, oznaka, opis, novaMaksimalnaTezina);
    }
  }

  private boolean provjeriVrste(String oznaka, String opis, String[] atributi, String linija,
      String datoteka, boolean omoguciUnos, String regexOznaka, String regexTipskiPaket,
      String regexNula) {
    if (oznaka.trim().isEmpty() || opis.trim().isEmpty()) {
      omoguciUnos = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Datoteka " + datoteka + ","
              + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija
              + " - postoji prazan atribut");
    } else {
      if (!oznaka.matches(regexOznaka)) {
        omoguciUnos = false;
        GreskeSingleton.getInstance().dohvatiGreskuS(
            "Datoteka " + datoteka + "," + " podatak nije u zadanom formatu za zapis: " + linija
                + " Oznaka treba biti: A,B,C,D,E ili X");
      }
    }

    if (oznaka.matches(regexTipskiPaket)) {
      if (!atributi[8].matches(regexNula) || !atributi[9].trim().matches(regexNula)) {
        omoguciUnos = false;
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + "zapis: "
            + linija + " Tipski paket treba imati cijenaP i cijenaT 0.0");
      }
    }

    if (oznaka.equals("X")) {
      if (!atributi[5].matches(regexNula)) {
        omoguciUnos = false;
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + "zapis: "
            + linija + " Vrsta paketa X treba imati maksimalnu težinu 0.0");
      } else {
        racunskeOperacijekPaketX(atributi);
      }
    }
    return omoguciUnos;
  }

  private void racunskeOperacijekPaketX(String[] atributi) {
    novaMaksimalnaTezina = (float) TvrtkaSingleton.getInstance().dohvatiMT();
  }

  private void unesiTipskiPaket(String[] atributi, boolean omoguciUnos, String datoteka,
      String oznaka, String opis) {
    try {
      String regexFloat = "^[0-9]+(?:[.,][0-9]+)?$";
      Float visina = Float.parseFloat(atributi[2].replace(",", "."));
      Float sirina = Float.parseFloat(atributi[3].replace(",", "."));
      Float duzina = Float.parseFloat(atributi[4].replace(",", "."));
      Float maksimalnaTezina = Float.parseFloat(atributi[5].replace(",", "."));
      Float cijena = Float.parseFloat(atributi[6].replace(",", "."));
      Float cijenaHitno = Float.parseFloat(atributi[7].replace(",", "."));
      Float cijenaP = Float.parseFloat(atributi[8].replace(",", "."));
      Float cijenaT = Float.parseFloat(atributi[9].trim().replace(",", "."));

      if (!atributi[2].matches(regexFloat) || !atributi[3].matches(regexFloat)
          || !atributi[4].matches(regexFloat) || !atributi[5].matches(regexFloat)
          || !atributi[6].matches(regexFloat) || !atributi[7].matches(regexFloat)
          || !atributi[8].matches(regexFloat) || !atributi[9].trim().matches(regexFloat)) {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka: " + datoteka
            + ",podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
        omoguciUnos = false;
      }

      if (omoguciUnos) {
        TvrtkaSingleton.getInstance().spremiVrstu(oznaka, opis, visina, sirina, duzina,
            maksimalnaTezina, cijena, cijenaHitno, cijenaP, cijenaT);
      }
    } catch (Exception e) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
    }
  }

  private void unesiPaketX(String[] atributi, boolean omoguciUnos, String datoteka, String oznaka,
      String opis, Float novaMaksimalnaTezina) {
    try {
      String regexFloat = "^[0-9]+(?:[.,][0-9]+)?$";
      Float visina = Float.parseFloat(atributi[2].replace(",", "."));
      Float sirina = Float.parseFloat(atributi[3].replace(",", "."));
      Float duzina = Float.parseFloat(atributi[4].replace(",", "."));
      Float cijena = Float.parseFloat(atributi[6].replace(",", "."));
      Float cijenaHitno = Float.parseFloat(atributi[7].replace(",", "."));
      Float cijenaP = Float.parseFloat(atributi[8].replace(",", "."));
      Float cijenaT = Float.parseFloat(atributi[9].trim().replace(",", "."));

      if (!atributi[2].matches(regexFloat) || !atributi[3].matches(regexFloat)
          || !atributi[4].matches(regexFloat) || !atributi[5].matches(regexFloat)
          || !atributi[6].matches(regexFloat) || !atributi[7].matches(regexFloat)
          || !atributi[8].matches(regexFloat) || !atributi[9].trim().matches(regexFloat)) {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka: " + datoteka
            + ",podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
        omoguciUnos = false;
      }

      if (omoguciUnos) {
        TvrtkaSingleton.getInstance().spremiVrstu(oznaka, opis, visina, sirina, duzina,
            novaMaksimalnaTezina, cijena, cijenaHitno, cijenaP, cijenaT);
      }
    } catch (Exception e) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
    }
  }

}
