package org.foi.uzdiz.chains;

import org.foi.uzdiz.singletons.GreskeSingleton;

public class HandlerKomandeDefault implements HandlerKomandi {
  private HandlerKomandi sljedbenik;

  @Override
  public void obradiKomandu(String opcija, String[] opcijaKomande) {
    if (sljedbenik != null) {
      sljedbenik.obradiKomandu(opcija, opcijaKomande);
    } else {
      GreskeSingleton.getInstance().dohvatiGreskuS(
          "Nijedan handler ne može obraditi komandu jer format upisane komande nije ispravan.");
    }
  }

  @Override
  public void postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
  }
}
