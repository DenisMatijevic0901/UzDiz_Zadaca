package org.foi.uzdiz.chains;

import org.foi.uzdiz.mementos.CaretakerKomande;
import org.foi.uzdiz.mementos.MementoKomande;
import org.foi.uzdiz.mementos.OriginatorKomande;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;
import org.foi.uzdiz.singletons.UredPrijemSingleton;
import org.foi.uzdiz.singletons.VirtualniSatSingleton;

public class HandlerKomandePPV implements HandlerKomandi {
  private HandlerKomandi sljedbenik;

  @Override
  public void obradiKomandu(String opcija, String[] opcijaKomande) {
    if (provjeriKomanduPPV(opcija) && opcijaKomande[0].equals("PPV")) {
      komandaPPV(opcija);
    } else if (sljedbenik != null) {
      sljedbenik.obradiKomandu(opcija, opcijaKomande);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
  }

  private static boolean provjeriKomanduPPV(String opcija) {
    String regexOpcijaPPV = "^PPV '[a-zA-Z0-9ćčšđžĆČŠĐŽ._ -]+'$";
    if (opcija.matches(regexOpcijaPPV)) {
      return true;
    } else {
      return false;
    }
  }

  private void komandaPPV(String opcija) {
    String dijeloviOpcije[] = opcija.split("'");
    String naziv = dijeloviOpcije[1].trim();
    OriginatorKomande originator = TvrtkaSingleton.getInstance().dohvatiOriginator();
    CaretakerKomande caretaker = TvrtkaSingleton.getInstance().dohvatiCaretaker();
    MementoKomande memento = caretaker.dohvati(naziv);

    if (memento != null) {
      originator.dohvatiStanjeIzMemento(memento);

      VirtualniSatSingleton.getInstance()
          .azurirajVirtualnoVrijeme(originator.dohvatiVirtualnoVrijeme());

      UredPrijemSingleton.getInstance().listaPaketiPrijem.clear();
      UredPrijemSingleton.getInstance().listaPaketiPrijem.addAll(originator.dohvatiPakete());

      TvrtkaSingleton.getInstance().listaVozila.clear();
      TvrtkaSingleton.getInstance().listaVozila.addAll(originator.dohvatiVozila());

      System.out.println("Trenutno stanje postavljeno na vrijednost iz stanja '" + naziv + "'.");
    } else {
      GreskeSingleton.getInstance()
          .dohvatiGreskuS("Nema spremljenog stanja pod nazivom '" + naziv + "'.");
    }
  }
}
