package org.foi.uzdiz.observers;

import org.foi.uzdiz.classes.Paket;

public interface PaketSubjekt {
  void dodajObserver(OsobaObserver observer);

  void ukloniObserver(OsobaObserver observer);

  void obavijestiOsobuPrijem(Paket paket);

  void obavijestiOsobuDostava(Paket paket);
}
