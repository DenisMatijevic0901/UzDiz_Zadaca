package org.foi.uzdiz.chains;

import java.time.format.DateTimeFormatter;
import org.foi.uzdiz.classes.Vozilo;
import org.foi.uzdiz.singletons.TvrtkaSingleton;
import org.foi.uzdiz.states.AktivnoStanje;
import org.foi.uzdiz.states.NijeAktivnoStanje;
import org.foi.uzdiz.states.NijeIspravnoStanje;

public class HandlerKomandePS implements HandlerKomandi {
  private HandlerKomandi sljedbenik;

  @Override
  public void obradiKomandu(String opcija, String[] opcijaKomande) {
    if (provjeriKomanduPS(opcija) && opcijaKomande[0].equals("PS")) {
      komandaPS(opcija);
    } else if (sljedbenik != null) {
      sljedbenik.obradiKomandu(opcija, opcijaKomande);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
  }

  private static boolean provjeriKomanduPS(String opcija) {
    String regexOpcijaPS = "^PS [a-zA-Z0-9ćčšđžĆČŠĐŽ_-]+ (A|NI|NA)$";
    if (opcija.matches(regexOpcijaPS)) {
      return true;
    } else {
      return false;
    }
  }

  private void komandaPS(String opcija) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    boolean pronaden = false;
    String[] opcijaKomande = opcija.split(" ");
    String dohvacenaRegistracija = opcijaKomande[1];
    String dohvaceniStatus = opcijaKomande[2];

    for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      if (dohvacenaRegistracija.equals(vozilo.dohvatiRegistraciju())) {
        pronaden = true;

        if (dohvaceniStatus.equals("A")) {
          vozilo.postaviTrenutnoStanje(new AktivnoStanje());
          vozilo.promijeniStatus(vozilo, "A", format);
        }
        if (dohvaceniStatus.equals("NI")) {
          vozilo.postaviTrenutnoStanje(new NijeIspravnoStanje());
          vozilo.promijeniStatus(vozilo, "NI", format);
        }
        if (dohvaceniStatus.equals("NA")) {
          vozilo.postaviTrenutnoStanje(new NijeAktivnoStanje());
          vozilo.promijeniStatus(vozilo, "NA", format);
        }
      }
    }

    if (pronaden == false) {
      System.out
          .println("Vozilo s registracijskom oznakom " + dohvacenaRegistracija + " ne postoji.");
    }
  }
}
