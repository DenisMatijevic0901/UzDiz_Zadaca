package org.foi.uzdiz.concreteProducts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.foi.uzdiz.builders.OsobaBuilder;
import org.foi.uzdiz.builders.OsobaDirektor;
import org.foi.uzdiz.builders.OsobaKonkretniBuilder;
import org.foi.uzdiz.classes.Osoba;
import org.foi.uzdiz.products.CsvProdukt;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;

public class OsobeCsvProdukt implements CsvProdukt {

  private String linija;

  @Override
  public void ucitaj(String datoteka) {
    File datotekaOsobe = new File(datoteka);

    if (datotekaOsobe.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(datotekaOsobe);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String prvaLinija = bufferedReader.readLine();

        if (prvaLinija != null && prvaLinija.equals("osoba; grad; ulica; kbr")) {
          boolean ostatakDatotekePrazan = true;
          while ((linija = bufferedReader.readLine()) != null) {
            if (!linija.trim().isEmpty()) {
              ostatakDatotekePrazan = false;
              String[] atributi = linija.split(";");
              if (atributi.length == 4) {
                dodajOsobu(atributi, linija, datoteka);
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

  private void dodajOsobu(String[] atributi, String linija, String datoteka) {
    boolean omoguciUnos = true;
    String osobaIme = atributi[0];

    for (Osoba osoba : TvrtkaSingleton.getInstance().listaOsobe) {
      if (osobaIme.equals(osoba.dohvatiOsobu())) {
        omoguciUnos = false;
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + " zapis: "
            + linija + " - Osoba s tim imenom već postoji.");
      }
    }

    if (osobaIme.trim().isEmpty()) {
      omoguciUnos = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Datoteka " + datoteka + ","
              + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija
              + " - postoji prazan atribut");
    }

    try {
      String regexInt = "^[\\d]+$";
      int grad = Integer.parseInt(atributi[1]);
      int ulica = Integer.parseInt(atributi[2]);
      int kbr = Integer.parseInt(atributi[3].trim());

      if (!atributi[1].matches(regexInt) || !atributi[2].matches(regexInt)
          || !atributi[3].trim().matches(regexInt)) {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka: " + datoteka + ","
            + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
        omoguciUnos = false;
      }

      if (omoguciUnos) {
        OsobaBuilder konkretniBuilder = new OsobaKonkretniBuilder();
        OsobaDirektor osobaDirektor = new OsobaDirektor(konkretniBuilder);
        TvrtkaSingleton.getInstance().spremiOsobu(osobaDirektor, osobaIme, grad, ulica, kbr);
      }

    } catch (Exception e) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
    }
  }
}


