package org.foi.uzdiz.concreteProducts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.foi.uzdiz.classes.Ulica;
import org.foi.uzdiz.products.CsvProdukt;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;

public class UliceCsvProdukt implements CsvProdukt {

  private String linija;

  @Override
  public void ucitaj(String datoteka) {
    File datotekaUlice = new File(datoteka);

    if (datotekaUlice.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(datotekaUlice);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String prvaLinija = bufferedReader.readLine();

        if (prvaLinija != null && prvaLinija
            .equals("id; naziv; gps_lat_1; gps_lon_1; gps_lat_2; gps_lon_2; najveći kućni broj")) {
          boolean ostatakDatotekePrazan = true;
          while ((linija = bufferedReader.readLine()) != null) {
            if (!linija.trim().isEmpty()) {
              ostatakDatotekePrazan = false;
              String[] atributi = linija.split(";");
              if (atributi.length == 7) {
                dodajUlicu(atributi, linija, datoteka);
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

  private void dodajUlicu(String[] atributi, String linija, String datoteka) {
    boolean omoguciUnos = true;
    int id = 0;
    try {
      id = Integer.parseInt(atributi[0]);
    } catch (Exception e) {
    }

    String naziv = atributi[1].trim();

    omoguciUnos = provjeriRegexGps(atributi, datoteka);

    for (Ulica ulica : TvrtkaSingleton.getInstance().listaUlica) {
      if (id == ulica.dohvatiId()) {
        omoguciUnos = false;
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + " zapis: "
            + linija + " - Ulica s tim id-jem već postoji.");
      }
    }

    if (naziv.trim().isEmpty()) {
      omoguciUnos = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Datoteka " + datoteka + ","
              + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija
              + " - postoji prazan atribut");
    }

    try {
      String regexInt = "^[\\d]+$";
      String regexDouble = "^[0-9]+(?:[.,][0-9]+)?$";
      int najveciKucniBroj = Integer.parseInt(atributi[6]);
      Double gpsLat1 = Double.parseDouble(atributi[2].trim().replace(",", "."));
      Double gpsLon1 = Double.parseDouble(atributi[3].trim().replace(",", "."));
      Double gpsLat2 = Double.parseDouble(atributi[4].trim().replace(",", "."));
      Double gpsLon2 = Double.parseDouble(atributi[5].trim().replace(",", "."));

      if (!atributi[0].matches(regexInt) || !atributi[6].trim().matches(regexInt)
          || !atributi[2].trim().matches(regexDouble) || !atributi[3].trim().matches(regexDouble)
          || !atributi[4].trim().matches(regexDouble) || !atributi[5].trim().matches(regexDouble)) {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka: " + datoteka + ","
            + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
        omoguciUnos = false;
      }

      if (omoguciUnos) {
        TvrtkaSingleton.getInstance().spremiUlicu(id, naziv, gpsLat1, gpsLon1, gpsLat2, gpsLon2,
            najveciKucniBroj);
      }
    } catch (Exception e) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
    }
  }

  private boolean provjeriRegexGps(String[] atributi, String datoteka) {
    String regexKomandaGps = "^[-+]?[0-9]{1,2}\\.[0-9]+$";
    boolean omoguciUnos = true;
    try {
      if (!atributi[2].trim().matches(regexKomandaGps)
          || !atributi[3].trim().matches(regexKomandaGps)
          || !atributi[4].trim().matches(regexKomandaGps)
          || !atributi[5].trim().matches(regexKomandaGps)) {
        omoguciUnos = false;
      }
    } catch (Exception e) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
    }
    return omoguciUnos;
  }
}


