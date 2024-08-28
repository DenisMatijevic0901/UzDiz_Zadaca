package org.foi.uzdiz.chains;

import org.foi.uzdiz.singletons.TvrtkaSingleton;

public class HandlerKomandeQ implements HandlerKomandi {
  private HandlerKomandi sljedbenik;

  @Override
  public void obradiKomandu(String opcija, String[] opcijaKomande) {
    if (opcijaKomande[0].equals("Q")) {
      TvrtkaSingleton.getInstance().radi = false;
    } else if (sljedbenik != null) {
      sljedbenik.obradiKomandu(opcija, opcijaKomande);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
  }

}
