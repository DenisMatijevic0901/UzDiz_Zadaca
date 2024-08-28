package org.foi.uzdiz.mementos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.classes.Vozilo;

public class OriginatorKomande {
  private LocalDateTime virtualnoVrijeme;
  private List<Paket> listaPaketiPrijem = new ArrayList<>();
  private List<Vozilo> listaVozila = new ArrayList<>();

  public void postaviStanje(LocalDateTime virtualnoVrijeme, List<Paket> listaPaketiPrijem,
      List<Vozilo> listaVozila) {
    this.virtualnoVrijeme = virtualnoVrijeme;
    this.listaPaketiPrijem = listaPaketiPrijem;
    this.listaVozila = listaVozila;
  }

  public LocalDateTime dohvatiVirtualnoVrijeme() {
    return virtualnoVrijeme;
  }

  public List<Paket> dohvatiPakete() {
    return listaPaketiPrijem;
  }

  public List<Vozilo> dohvatiVozila() {
    return listaVozila;
  }

  public MementoKomande spremiStanjeUMemento() {
    return new MementoKomande(virtualnoVrijeme, listaPaketiPrijem, listaVozila);
  }

  public void dohvatiStanjeIzMemento(MementoKomande memento) {
    virtualnoVrijeme = memento.dohvatiVirtualnoVrijeme();
    listaPaketiPrijem = memento.dohvatiPakete();
    listaVozila = memento.dohvatiVozila();
  }
}

