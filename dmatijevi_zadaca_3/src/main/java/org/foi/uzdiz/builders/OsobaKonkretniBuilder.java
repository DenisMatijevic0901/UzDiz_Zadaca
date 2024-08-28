package org.foi.uzdiz.builders;

import org.foi.uzdiz.classes.Osoba;

public class OsobaKonkretniBuilder implements OsobaBuilder {
  private String osoba;
  private int grad;
  private int ulica;
  private int kbr;

  @Override
  public OsobaBuilder postaviOsobu(String osoba) {
    this.osoba = osoba;
    return this;
  }

  @Override
  public OsobaBuilder postaviGrad(int grad) {
    this.grad = grad;
    return this;
  }

  @Override
  public OsobaBuilder postaviUlicu(int ulica) {
    this.ulica = ulica;
    return this;
  }

  @Override
  public OsobaBuilder postaviKbr(int kbr) {
    this.kbr = kbr;
    return this;
  }

  @Override
  public Osoba kreirajOsobu() {
    return new Osoba(osoba, grad, ulica, kbr);
  }
}
