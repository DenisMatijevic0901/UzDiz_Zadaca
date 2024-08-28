package org.foi.uzdiz.chains;

public interface HandlerKomandi {
  void obradiKomandu(String opcija, String[] opcijaKomande);

  void postaviSljedbenika(HandlerKomandi sljedbenik);
}
