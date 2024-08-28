package org.foi.uzdiz.mementos;

import java.util.HashMap;
import java.util.Map;

public class CaretakerKomande {
  private Map<String, MementoKomande> mementoMap = new HashMap<>();

  public void dodajUZbirkuMemento(String naziv, MementoKomande memento) {
    mementoMap.put(naziv, memento);
  }

  public MementoKomande dohvati(String naziv) {
    return mementoMap.get(naziv);
  }
}
