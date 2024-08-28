package org.foi.uzdiz;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import org.foi.uzdiz.chains.HandlerKomandeDefault;
import org.foi.uzdiz.chains.HandlerKomandeIP;
import org.foi.uzdiz.chains.HandlerKomandeOP;
import org.foi.uzdiz.chains.HandlerKomandePO;
import org.foi.uzdiz.chains.HandlerKomandePP;
import org.foi.uzdiz.chains.HandlerKomandePPV;
import org.foi.uzdiz.chains.HandlerKomandePS;
import org.foi.uzdiz.chains.HandlerKomandeQ;
import org.foi.uzdiz.chains.HandlerKomandeSPV;
import org.foi.uzdiz.chains.HandlerKomandeSV;
import org.foi.uzdiz.chains.HandlerKomandeVR;
import org.foi.uzdiz.chains.HandlerKomandi;
import org.foi.uzdiz.classes.ArgumentValidator;
import org.foi.uzdiz.classes.ParametarValidator;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;
import org.foi.uzdiz.singletons.VirtualniSatSingleton;

public class Main {
  public static void main(String[] args) {
    Scanner skener = new Scanner(System.in);

    if (args.length != 1) {
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Potrebno je unijeti samo jedan argument naziva datoteke s parametrima.");
      skener.close();
      return;
    } else {
      if (!provjeriNazivDatotekeParametara(args[0])) {
        skener.close();
        return;
      } else {
        Properties parametri = new Properties();
        try {
          parametri.load(new FileInputStream(args[0]));
          ArgumentValidator argumentValidator = new ArgumentValidator();
          ParametarValidator parametarValidator = new ParametarValidator(argumentValidator);
          parametarValidator.validiraj(parametri);
          if (argumentValidator.ispravno == true && provjeriBrojArgumenata(args[0]) == true) {
            TvrtkaSingleton.getInstance().dohvatiPodatke();

          } else {
            GreskeSingleton.getInstance().dohvatiGreskuS("Parametri nisu dobri.");
            return;
          }
        } catch (IOException e) {
          GreskeSingleton.getInstance().dohvatiGreskuS("Neuspješno učitavanje postavki.");
        }
      }
    }

    VirtualniSatSingleton.getInstance().postaviVS();

    HandlerKomandi handlerKomandeIP = new HandlerKomandeIP();
    HandlerKomandi handlerKomandeVR = new HandlerKomandeVR();
    HandlerKomandi handlerKomandePS = new HandlerKomandePS();
    HandlerKomandi handlerKomandePP = new HandlerKomandePP();
    HandlerKomandi handlerKomandePO = new HandlerKomandePO();
    HandlerKomandi handlerKomandeOP = new HandlerKomandeOP();
    HandlerKomandi handlerKomandeSV = new HandlerKomandeSV();
    HandlerKomandi handlerKomandeSPV = new HandlerKomandeSPV();
    HandlerKomandi handlerKomandePPV = new HandlerKomandePPV();
    HandlerKomandi handlerKomandeQ = new HandlerKomandeQ();
    HandlerKomandi handlerKomandeDefault = new HandlerKomandeDefault();

    handlerKomandeIP.postaviSljedbenika(handlerKomandeVR);
    handlerKomandeVR.postaviSljedbenika(handlerKomandePS);
    handlerKomandePS.postaviSljedbenika(handlerKomandePP);
    handlerKomandePP.postaviSljedbenika(handlerKomandePO);
    handlerKomandePO.postaviSljedbenika(handlerKomandeOP);
    handlerKomandeOP.postaviSljedbenika(handlerKomandeSV);
    handlerKomandeSV.postaviSljedbenika(handlerKomandeSPV);
    handlerKomandeSPV.postaviSljedbenika(handlerKomandePPV);
    handlerKomandePPV.postaviSljedbenika(handlerKomandeQ);
    handlerKomandeQ.postaviSljedbenika(handlerKomandeDefault);

    do {
      System.out.println("\nUnesite komandu ili unesite Q za završetak rada programa:");
      String opcija = skener.nextLine();
      String[] opcijaKomande = opcija.split(" ");

      handlerKomandeIP.obradiKomandu(opcija, opcijaKomande);

    } while (TvrtkaSingleton.getInstance().radi == true);
    skener.close();
  }

  private static boolean provjeriBrojArgumenata(String nazivDatoteke) {
    int brojacParametara = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(nazivDatoteke))) {
      String linija;
      while ((linija = reader.readLine()) != null) {
        if (linija.trim().isEmpty()) {
          continue;
        }
        String[] dijelovi = linija.split("=");
        if (dijelovi.length >= 2) {
          brojacParametara++;
        }
      }
    } catch (IOException e) {
      GreskeSingleton.getInstance().dohvatiGreskuS(
          "Greška prilikom učitavanja datoteke s " + "parametrima (provjera broja argumenata).");
    }
    if (brojacParametara != 15) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Broj parametara treba biti 15.");
      return false;
    } else {
      return true;
    }
  }

  private static boolean provjeriNazivDatotekeParametara(String nazivDatoteke) {
    String regexOpcijaDatotekaParametara = "^[a-zA-Z0-9ćčšđžĆČŠĐŽ_-]+\\.txt$";
    if (nazivDatoteke.matches(regexOpcijaDatotekaParametara)) {
      Path putanja = Paths.get(nazivDatoteke);
      if (Files.exists(putanja) && Files.isRegularFile(putanja)) {
        return true;
      } else {
        GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka s parametrima ne postoji.");
        return false;
      }
    } else {
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Naziv datoteke s parametrima ne zadovoljava zadani format.");
      return false;
    }
  }
}
