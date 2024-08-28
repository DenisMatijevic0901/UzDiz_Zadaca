package org.foi.uzdiz.classes;

public class Osoba {
  private String osoba;
  private int grad;
  private int ulica;
  private int kbr;

  public Osoba(String osoba, int grad, int ulica, int kbr) {
    this.osoba = osoba;
    this.grad = grad;
    this.ulica = ulica;
    this.kbr = kbr;
  }

  public String dohvatiOsobu() {
    return osoba;
  }

  public int dohvatiGrad() {
    return grad;
  }

  public int dohvatiUlicu() {
    return ulica;
  }

  public int dohvatiKbr() {
    return kbr;
  }
}
