package org.foi.uzdiz.concreteProducts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.foi.uzdiz.classes.Podrucje;
import org.foi.uzdiz.products.CsvProdukt;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;

public class PodrucjaCsvProdukt implements CsvProdukt {

  private String linija;

  @Override
  public void ucitaj(String datoteka) {
    File datotekaPodrucja = new File(datoteka);

    if (datotekaPodrucja.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(datotekaPodrucja);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String prvaLinija = bufferedReader.readLine();

        if (prvaLinija != null && prvaLinija.equals("id;grad:ulica,grad:ulica,grad:*,...")) {
          boolean ostatakDatotekePrazan = true;
          while ((linija = bufferedReader.readLine()) != null) {
            if (!linija.trim().isEmpty()) {
              ostatakDatotekePrazan = false;
              String[] atributi = linija.split(";");
              if (atributi.length == 2) {
                dodajPodrucje(atributi, linija, datoteka);
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

  private void dodajPodrucje(String[] atributi, String linija, String datoteka) {
    boolean omoguciUnos = true;
    int id = 0;
    try {
      id = Integer.parseInt(atributi[0]);
    } catch (Exception e) {
    }

    String gradUlica = atributi[1].trim();

    omoguciUnos = provjeriRegexGradUlica(gradUlica, datoteka);

    for (Podrucje podrucje : TvrtkaSingleton.getInstance().listaPodrucja) {
      if (id == podrucje.dohvatiId()) {
        omoguciUnos = false;
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + " zapis: "
            + linija + " - Podrucje s tim id-jem već postoji.");
      }
    }

    if (gradUlica.trim().isEmpty()) {
      omoguciUnos = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Datoteka " + datoteka + ","
              + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija
              + " - postoji prazan atribut");
    }

    try {
      String regexInt = "^[\\d]+$";

      if (!atributi[0].matches(regexInt)) {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka: " + datoteka + ","
            + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
        omoguciUnos = false;
      }

      if (omoguciUnos) {
        TvrtkaSingleton.getInstance().spremiPodrucje(id, gradUlica);
      }
    } catch (Exception e) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
    }
  }

  private boolean provjeriRegexGradUlica(String gradUlica, String datoteka) {
    String regexKomandaGradUlica = "^(\\d+:(\\d+|\\*),)*\\d+:(\\d+|\\*)$";
    if (gradUlica.trim().matches(regexKomandaGradUlica)) {
      return true;
    } else {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu za zapis: " + linija + " (grad:ulica)");
      return false;
    }
  }
}


