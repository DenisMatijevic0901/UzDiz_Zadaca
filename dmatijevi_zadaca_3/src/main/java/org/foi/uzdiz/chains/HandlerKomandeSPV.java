package org.foi.uzdiz.chains;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.classes.Vozilo;
import org.foi.uzdiz.mementos.CaretakerKomande;
import org.foi.uzdiz.mementos.OriginatorKomande;
import org.foi.uzdiz.singletons.TvrtkaSingleton;
import org.foi.uzdiz.singletons.UredPrijemSingleton;
import org.foi.uzdiz.singletons.VirtualniSatSingleton;

public class HandlerKomandeSPV implements HandlerKomandi {
  private HandlerKomandi sljedbenik;

  @Override
  public void obradiKomandu(String opcija, String[] opcijaKomande) {
    if (provjeriKomanduSPV(opcija) && opcijaKomande[0].equals("SPV")) {
      komandaSPV(opcija);
    } else if (sljedbenik != null) {
      sljedbenik.obradiKomandu(opcija, opcijaKomande);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
  }

  private static boolean provjeriKomanduSPV(String opcija) {
    String regexOpcijaSPV = "^SPV '[a-zA-Z0-9ćčšđžĆČŠĐŽ._ -]+'$";
    if (opcija.matches(regexOpcijaSPV)) {
      return true;
    } else {
      return false;
    }
  }

  private void komandaSPV(String opcija) {
    String dijeloviOpcije[] = opcija.split("'");
    String naziv = dijeloviOpcije[1].trim();
    LocalDateTime trenutnoVrijeme = VirtualniSatSingleton.getInstance().dohvatiPravoVrijeme();
    List<Paket> listaPaketiPrijem = UredPrijemSingleton.getInstance().listaPaketiPrijem;
    List<Vozilo> listaVozila = TvrtkaSingleton.getInstance().listaVozila;

    OriginatorKomande originator = TvrtkaSingleton.getInstance().dohvatiOriginator();
    CaretakerKomande caretaker = TvrtkaSingleton.getInstance().dohvatiCaretaker();

    List<Paket> kopijaListePaketa = kopirajListuPaketa(listaPaketiPrijem);
    List<Vozilo> kopijaListeVozila = kopirajListuVozila(listaVozila);

    originator.postaviStanje(trenutnoVrijeme, kopijaListePaketa, kopijaListeVozila);
    caretaker.dodajUZbirkuMemento(naziv, originator.spremiStanjeUMemento());

    System.out.println("Stanje spremljeno pod nazivom '" + naziv + "'.");
  }

  private List<Paket> kopirajListuPaketa(List<Paket> original) {
    List<Paket> kopija = new ArrayList<>();
    for (Paket paket : original) {
      kopija.add(paket.kloniraj());
    }
    return kopija;
  }

  private List<Vozilo> kopirajListuVozila(List<Vozilo> original) {
    List<Vozilo> kopija = new ArrayList<>();
    for (Vozilo vozilo : original) {
      kopija.add(vozilo.kloniraj());
    }
    return kopija;
  }
}
