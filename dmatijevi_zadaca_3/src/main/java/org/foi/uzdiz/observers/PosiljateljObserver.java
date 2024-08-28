package org.foi.uzdiz.observers;

import org.foi.uzdiz.classes.Paket;

public class PosiljateljObserver implements OsobaObserver {
  private String osoba;

  public PosiljateljObserver(String osoba) {
    this.osoba = osoba;
  }

  @Override
  public void azurirajPrijem(Paket paket) {
    System.out.println("Obavještava se pošiljatelj: " + osoba + " da je paket "
        + paket.dohvatiOznaku() + " zaprimljen.");
  }

  @Override
  public void azurirajDostava(Paket paket) {
    System.out.println("Obavještava se pošiljatelj: " + osoba + " da je paket "
        + paket.dohvatiOznaku() + " preuzet.\n");
  }
}
