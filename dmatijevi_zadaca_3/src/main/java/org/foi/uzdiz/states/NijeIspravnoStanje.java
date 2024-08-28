package org.foi.uzdiz.states;

import java.time.format.DateTimeFormatter;
import org.foi.uzdiz.classes.Vozilo;
import org.foi.uzdiz.singletons.VirtualniSatSingleton;

public class NijeIspravnoStanje implements VoziloState {
  @Override
  public void promijeniStatus(Vozilo vozilo, String status, DateTimeFormatter format) {
    System.out.println("\nVozilu s registracijskom oznakom " + vozilo.dohvatiRegistraciju()
        + " postavljen je status: " + status + " - nije ispravno, u vrijeme virtualnog sata: "
        + VirtualniSatSingleton.getInstance().dohvatiPravoVrijeme().format(format));
  }
}
