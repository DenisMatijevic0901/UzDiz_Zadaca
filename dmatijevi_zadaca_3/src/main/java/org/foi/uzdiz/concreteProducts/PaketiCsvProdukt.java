package org.foi.uzdiz.concreteProducts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.classes.Vrsta;
import org.foi.uzdiz.products.CsvProdukt;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;

public class PaketiCsvProdukt implements CsvProdukt {

  private String linija;

  @Override
  public void ucitaj(String datoteka) {
    File datotekaPaketi = new File(datoteka);

    if (datotekaPaketi.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(datotekaPaketi);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String prvaLinija = bufferedReader.readLine();

        if (prvaLinija != null
            && prvaLinija.equals("Oznaka;Vrijeme prijema;Pošiljatelj;Primatelj;Vrsta paketa;"
                + "Visina;Širina;Dužina;Težina;Usluga dostave;Iznos pouzeća")) {
          boolean ostatakDatotekePrazan = true;
          while ((linija = bufferedReader.readLine()) != null) {
            if (!linija.trim().isEmpty()) {
              ostatakDatotekePrazan = false;
              String[] atributi = linija.split(";");
              if (atributi.length == 11) {
                dodajPaket(atributi, linija, datoteka);
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

  private void dodajPaket(String[] atributi, String linija, String datoteka) {
    boolean omoguciUnos = true;
    String oznaka = atributi[0];
    String posiljatelj = atributi[2];
    String primatelj = atributi[3];
    String vrstaPaketa = atributi[4];
    String uslugaDostave = atributi[9];
    String vrijemePrijemaString = atributi[1];
    SimpleDateFormat formatVrijemePrijema = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    Date vrijemePrijema = null;
    String regexVrstaPaketa = "^(A|B|C|D|E|X)$";
    String regexNula = "^[0](?:[.,][0]+)?$";
    String regexUslugaDostave = "^(S|H|P|R)$";
    String regexDatum =
        "^(0[1-9]|[12]\\d|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}\\. (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

    for (Paket paket : TvrtkaSingleton.getInstance().listaPaketi) {
      if (oznaka.equals(paket.dohvatiOznaku())) {
        omoguciUnos = false;
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + " zapis: "
            + linija + " - Paket s tom oznakom već postoji.");
      }
    }

    if (!vrijemePrijemaString.matches(regexDatum)) {
      omoguciUnos = false;
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + " zapis: "
          + linija + " - Datum nije ispravnog formata: dd.MM.yyyy HH:mm:ss");
    } else {
      try {
        vrijemePrijema = (Date) formatVrijemePrijema.parse(vrijemePrijemaString);
      } catch (ParseException e) {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + " zapis: "
            + linija + "Greška prilikom parsiranja datuma");
      }
    }

    omoguciUnos = provjeriPakete(oznaka, posiljatelj, primatelj, vrstaPaketa, uslugaDostave,
        atributi, linija, datoteka, omoguciUnos, regexVrstaPaketa, regexNula, regexUslugaDostave);

    unesiPaket(atributi, datoteka, oznaka, posiljatelj, primatelj, vrstaPaketa, uslugaDostave,
        omoguciUnos, vrijemePrijema);

  }

  private boolean provjeriPakete(String oznaka, String posiljatelj, String primatelj,
      String vrstaPaketa, String uslugaDostave, String[] atributi, String linija, String datoteka,
      boolean omoguciUnos, String regexVrstaPaketa, String regexNula, String regexUslugaDostave) {
    String regexFloat = "^[0-9]+(?:[.,][0-9]+)?$";

    if (oznaka.trim().isEmpty() || posiljatelj.trim().isEmpty() || primatelj.trim().isEmpty()
        || vrstaPaketa.trim().isEmpty() || uslugaDostave.trim().isEmpty()) {
      omoguciUnos = false;
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Datoteka " + datoteka + ","
              + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija
              + " - postoji prazan atribut");
    }

    if (!vrstaPaketa.matches(regexVrstaPaketa)) {
      omoguciUnos = false;
      GreskeSingleton.getInstance().dohvatiGreskuS(
          "Datoteka " + datoteka + "," + " podatak nije u zadanom formatu za zapis: " + linija
              + " Vrsta paketa treba biti: A,B,C,D,E ili X");
    } else {
      if (vrstaPaketa.equals("X")) {
        if (!atributi[5].matches(regexFloat) || !atributi[6].matches(regexFloat)
            || !atributi[7].matches(regexFloat)) {
          omoguciUnos = false;
          GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + "zapis: "
              + linija + " Paket X treba imati visinu, širinu i dužinu različitu od 0.0");
        }
      } else {
        if (!atributi[5].matches(regexNula) || !atributi[6].matches(regexNula)
            || !atributi[7].matches(regexNula)) {
          omoguciUnos = false;
          GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + "zapis: "
              + linija + " Tipski paket treba imati visinu, širinu i dužinu: 0.0");
        }
      }
    }

    if (!uslugaDostave.matches(regexUslugaDostave)) {
      omoguciUnos = false;
      GreskeSingleton.getInstance().dohvatiGreskuS(
          "Datoteka " + datoteka + "," + " podatak nije u zadanom formatu za zapis: " + linija
              + " Usluga dostave može biti: S, H, P ili R");
    } else {
      if (!uslugaDostave.equals("P")) {
        if (!atributi[10].trim().matches(regexNula)) {
          GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + "zapis: "
              + linija + " Usluge dostave S, H i R trebaju imati iznos pouzeća: 0.0");
        }
      }
    }
    return omoguciUnos;
  }

  private void unesiPaket(String[] atributi, String datoteka, String oznaka, String posiljatelj,
      String primatelj, String vrstaPaketa, String uslugaDostave, boolean omoguciUnos,
      Date vrijemePrijema) {
    try {
      String regexFloat = "^[0-9]+(?:[.,][0-9]+)?$";
      Float visina = Float.parseFloat(atributi[5].replace(",", "."));
      Float sirina = Float.parseFloat(atributi[6].replace(",", "."));
      Float duzina = Float.parseFloat(atributi[7].replace(",", "."));
      Float tezina = Float.parseFloat(atributi[8].replace(",", "."));
      Float iznosPouzeca = Float.parseFloat(atributi[10].trim().replace(",", "."));

      if (!atributi[5].matches(regexFloat) || !atributi[6].matches(regexFloat)
          || !atributi[7].matches(regexFloat) || !atributi[8].matches(regexFloat)
          || !atributi[10].trim().matches(regexFloat)) {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka: " + datoteka
            + ",podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
      }

      omoguciUnos = provjeriTezinuXPaketa(omoguciUnos, vrstaPaketa, tezina, datoteka);
      omoguciUnos = provjeriVisinuSirinuDuzinuXPaketa(omoguciUnos, vrstaPaketa, visina, sirina,
          duzina, datoteka);

      if (omoguciUnos) {
        TvrtkaSingleton.getInstance().spremiPaket(oznaka, vrijemePrijema, posiljatelj, primatelj,
            vrstaPaketa, visina, sirina, duzina, tezina, uslugaDostave, iznosPouzeca);
        TvrtkaSingleton.getInstance().listaPO
            .add(oznaka + ";" + posiljatelj + ";" + primatelj + ";D;D");
      }

    } catch (Exception e) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + ","
          + " podatak nije u zadanom formatu (greška parsiranja) za zapis: " + linija);
    }
  }

  private boolean provjeriTezinuXPaketa(boolean omoguciUnos, String vrstaPaketa, float tezina,
      String datoteka) {
    if (vrstaPaketa.equals("X") && tezina > (float) TvrtkaSingleton.getInstance().dohvatiMT()) {
      omoguciUnos = false;
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + "zapis: " + linija
          + " Paket je prekoračio maksimalnu težinu paketa kod vrste X paketa");
    }
    return omoguciUnos;
  }

  private boolean provjeriVisinuSirinuDuzinuXPaketa(boolean omoguciUnos, String vrstaPaketa,
      Float visina, Float sirina, Float duzina, String datoteka) {
    for (Vrsta vrsta : TvrtkaSingleton.getInstance().listaVrste) {
      if (vrsta.dohvatiOznaku().equals("X")) {
        if (visina > vrsta.dohvatiVisinu()) {
          omoguciUnos = false;
          GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + "zapis: "
              + linija + " Paket je prekoračio maksimalnu visinu paketa kod vrste X paketa");
        }
        if (sirina > vrsta.dohvatiSirinu()) {
          omoguciUnos = false;
          GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + "zapis: "
              + linija + " Paket je prekoračio maksimalnu širinu paketa kod vrste X paketa");
        }
        if (duzina > vrsta.dohvatiDuzinu()) {
          omoguciUnos = false;
          GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka " + datoteka + "," + "zapis: "
              + linija + " Paket je prekoračio maksimalnu dužinu paketa kod vrste X paketa");
        }
      }
    }
    return omoguciUnos;
  }
}
