package org.foi.uzdiz.proxies;

import org.foi.uzdiz.classes.Osoba;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;
import org.foi.uzdiz.singletons.UredPrijemSingleton;

public class RealniPaketSubjekt implements PaketSubjektProxy {

  public void obrisiPaket(String osoba, String paket) {
    if (provjeriOsobu(osoba) && provjeriPaket(paket)) {
      String posiljateljPrimatelj = provjeriPrimateljaIliPosiljatelja(osoba, paket);
      if (posiljateljPrimatelj.equals("Pošiljatelj")) {
        boolean postojiPaket = false;
        for (Paket paketPrijem : UredPrijemSingleton.getInstance().listaPaketiPrijem) {
          if (paketPrijem.dohvatiOznaku().equals(paket)) {
            postojiPaket = true;
            break;
          }
        }
        if (postojiPaket == false) {
          UredPrijemSingleton.getInstance().listaPaketiPrijem
              .removeIf(p -> p.dohvatiOznaku().equals(paket));
          TvrtkaSingleton.getInstance().listaPaketi.removeIf(p -> p.dohvatiOznaku().equals(paket));
          System.out.println("Pošiljatelj: " + osoba + " je odustao od slanja paketa: " + paket);
        } else {
          GreskeSingleton.getInstance()
              .dohvatiGreskuS("Paket je već ukrcan u dostavno vozilo te pošiljatelj: " + osoba
                  + " ne može odustati od slanja paketa: " + paket);
        }
      }
      if (posiljateljPrimatelj.equals("Primatelj")) {
        GreskeSingleton.getInstance()
            .dohvatiGreskuS("Osoba: " + osoba + " nije pošiljatelj za paket: " + paket);
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
