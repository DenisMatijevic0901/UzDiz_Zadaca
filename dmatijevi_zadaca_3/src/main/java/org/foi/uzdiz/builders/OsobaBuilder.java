package org.foi.uzdiz.builders;

import org.foi.uzdiz.classes.Osoba;

public interface OsobaBuilder {
  OsobaBuilder postaviOsobu(String osoba);

  OsobaBuilder postaviGrad(int grad);

  OsobaBuilder postaviUlicu(int ulica);

  OsobaBuilder postaviKbr(int kbr);

  Osoba kreirajOsobu();
}
