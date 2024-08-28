package org.foi.uzdiz.observers;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.classes.Paket;

public class KonkretniPaketSubjekt implements PaketSubjekt {
  private List<OsobaObserver> listaObservera = new ArrayList<>();

  @Override
  public void dodajObserver(OsobaObserver observer) {
    listaObservera.add(observer);
  }

  @Override
  public void ukloniObserver(OsobaObserver observer) {
    listaObservera.remove(observer);
  }

  @Override
  public void obavijestiOsobuPrijem(Paket paket) {
    for (OsobaObserver observer : listaObservera) {
      observer.azurirajPrijem(paket);
    }
  }

  @Override
  public void obavijestiOsobuDostava(Paket paket) {
    for (OsobaObserver observer : listaObservera) {
      observer.azurirajDostava(paket);
    }
  }
}
