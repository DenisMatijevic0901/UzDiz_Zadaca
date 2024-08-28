package org.foi.uzdiz.builders;

import org.foi.uzdiz.classes.Osoba;

public class OsobaDirektor {
  private OsobaBuilder builder;

  public OsobaDirektor(OsobaBuilder builder) {
    this.builder = builder;
  }

  public Osoba konstruirajOsobu(String osoba, int grad, int ulica, int kbr) {
    builder.postaviOsobu(osoba);
    builder.postaviGrad(grad);
    builder.postaviUlicu(ulica);
    builder.postaviKbr(kbr);
    return builder.kreirajOsobu();
  }
}
