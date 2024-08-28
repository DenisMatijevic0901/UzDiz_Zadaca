package org.foi.uzdiz.observers;

import org.foi.uzdiz.classes.Paket;

public interface OsobaObserver {
  void azurirajPrijem(Paket paket);

  void azurirajDostava(Paket paket);
}
