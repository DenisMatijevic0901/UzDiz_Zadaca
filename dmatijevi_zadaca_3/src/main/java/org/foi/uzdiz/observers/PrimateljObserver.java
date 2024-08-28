package org.foi.uzdiz.observers;

import org.foi.uzdiz.classes.Paket;

public class PrimateljObserver implements OsobaObserver {
  private String osoba;

  public PrimateljObserver(String osoba) {
    this.osoba = osoba;
  }

  @Override
  public void azurirajPrijem(Paket paket) {
    System.out.println("Obavještava se primatelj: " + osoba + " da je paket "
        + paket.dohvatiOznaku() + " zaprimljen.\n");
  }

  @Override
  public void azurirajDostava(Paket paket) {
    System.out.println("Obavještava se primatelj: " + osoba + " da je paket "
        + paket.dohvatiOznaku() + " preuzet.\n");
  }
}
