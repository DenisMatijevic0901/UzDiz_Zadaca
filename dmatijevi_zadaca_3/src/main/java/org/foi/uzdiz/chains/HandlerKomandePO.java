package org.foi.uzdiz.chains;

import org.foi.uzdiz.classes.Osoba;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;

public class HandlerKomandePO implements HandlerKomandi {
  private HandlerKomandi sljedbenik;

  @Override
  public void obradiKomandu(String opcija, String[] opcijaKomande) {
    if (provjeriKomanduPO(opcija) && opcijaKomande[0].equals("PO")) {
      komandaPO(opcija);
    } else if (sljedbenik != null) {
      sljedbenik.obradiKomandu(opcija, opcijaKomande);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
  }

  private static boolean provjeriKomanduPO(String opcija) {
    String regexOpcijaPO = "^PO '[a-zA-Z0-9ćčšđžĆČŠĐŽ._ -]+' [a-zA-Z0-9ćčšđžĆČŠĐŽ_-]+ [DN]$";
    if (opcija.matches(regexOpcijaPO)) {
      return true;
    } else {
      return false;
    }
  }

  private void komandaPO(String opcija) {
    String dijeloviOpcije[] = opcija.split(" ");
    String dijeloviOpcijeOsoba[] = opcija.split("'");

    String osoba = dijeloviOpcijeOsoba[1].trim();
    String paket = dijeloviOpcije[dijeloviOpcije.length - 2];
    String status = dijeloviOpcije[dijeloviOpcije.length - 1];

    if (provjeriOsobu(osoba) && provjeriPaket(paket)) {
      String posiljateljPrimatelj = provjeriPrimateljaIliPosiljatelja(osoba, paket);
      for (int i = 0; i < TvrtkaSingleton.getInstance().listaPO.size(); i++) {
        String podaci = TvrtkaSingleton.getInstance().listaPO.get(i);
        String[] dijelovi = podaci.split(";");
        String oznaka = dijelovi[0];
        String posiljatelj = dijelovi[1];
        String primatelj = dijelovi[2];
        String posiljateljStatus = dijelovi[3];
        String primateljStatus = dijelovi[4];

        promijeniRedakPosiljatelj(i, posiljateljPrimatelj, paket, oznaka, status, osoba,
            posiljatelj, primatelj, primateljStatus);
        promijeniRedakPrimatelj(i, posiljateljPrimatelj, paket, oznaka, status, osoba, posiljatelj,
            primatelj, posiljateljStatus);
      }
    }
  }

  private void promijeniRedakPrimatelj(int i, String posiljateljPrimatelj, String paket,
      String oznaka, String status, String osoba, String posiljatelj, String primatelj,
      String posiljateljStatus) {
    if (posiljateljPrimatelj.equals("Primatelj")) {
      if (paket.equals(oznaka)) {

        if (status.equals("D")) {
          System.out.println("Primatelj " + osoba + " želi primati obavijesti za paket: " + paket);
        }
        if (status.equals("N")) {
          System.out
              .println("Primatelj " + osoba + " ne želi primati obavijesti za paket: " + paket);
        }
        StringBuilder noviRedak = new StringBuilder();
        noviRedak.append(oznaka).append(";").append(posiljatelj).append(";").append(primatelj)
            .append(";").append(posiljateljStatus).append(";").append(status);
        TvrtkaSingleton.getInstance().listaPO.set(i, noviRedak.toString());
      }
    }
  }

  private void promijeniRedakPosiljatelj(int i, String posiljateljPrimatelj, String paket,
      String oznaka, String status, String osoba, String posiljatelj, String primatelj,
      String primateljStatus) {
    if (posiljateljPrimatelj.equals("Pošiljatelj")) {
      if (paket.equals(oznaka)) {

        if (status.equals("D")) {
          System.out
              .println("Pošiljatelj " + osoba + " želi primati obavijesti za paket: " + paket);
        }
        if (status.equals("N")) {
          System.out
              .println("Pošiljatelj " + osoba + " ne želi primati obavijesti za paket: " + paket);
        }
        StringBuilder noviRedak = new StringBuilder();
        noviRedak.append(oznaka).append(";").append(posiljatelj).append(";").append(primatelj)
            .append(";").append(status).append(";").append(primateljStatus);
        TvrtkaSingleton.getInstance().listaPO.set(i, noviRedak.toString());
      }
    }
  }

  private boolean provjeriOsobu(String osoba) {
    for (Osoba trazenaOsoba : TvrtkaSingleton.getInstance().listaOsobe) {
      if (trazenaOsoba.dohvatiOsobu().equals(osoba)) {
        return true;
      }
    }
    GreskeSingleton.getInstance().dohvatiGreskuS("Osoba " + osoba + " ne postoji u listi osoba.");
    return false;
  }

  private boolean provjeriPaket(String paket) {
    for (Paket trazeniPaket : TvrtkaSingleton.getInstance().listaPaketi) {
      if (trazeniPaket.dohvatiOznaku().equals(paket)) {
        return true;
      }
    }
    GreskeSingleton.getInstance().dohvatiGreskuS("Paket " + paket + " ne postoji u listi paketa.");
    return false;
  }

  private String provjeriPrimateljaIliPosiljatelja(String osoba, String paket) {
    String primateljIliPosiljatelj = "";
    for (Paket trazeniPaket : TvrtkaSingleton.getInstance().listaPaketi) {
      if (trazeniPaket.dohvatiOznaku().equals(paket)) {
        if (trazeniPaket.dohvatiPrimatelja().equals(osoba)) {
          primateljIliPosiljatelj = "Primatelj";
        } else if (trazeniPaket.dohvatiPosiljatelja().equals(osoba)) {
          primateljIliPosiljatelj = "Pošiljatelj";
        } else {
          GreskeSingleton.getInstance().dohvatiGreskuS(
              "Osoba: " + osoba + " nije ni pošiljatelj, a ni primatelj za paket: " + paket);
        }
      }
    }
    return primateljIliPosiljatelj;
  }
}
