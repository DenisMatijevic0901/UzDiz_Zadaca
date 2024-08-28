package org.foi.uzdiz.proxies;

public class PaketProxy implements PaketSubjektProxy {
  private RealniPaketSubjekt realniSubjekt;

  public PaketProxy() {
    this.realniSubjekt = new RealniPaketSubjekt();
  }

  @Override
  public void obrisiPaket(String osoba, String paket) {
    realniSubjekt.obrisiPaket(osoba, paket);
  }
}
