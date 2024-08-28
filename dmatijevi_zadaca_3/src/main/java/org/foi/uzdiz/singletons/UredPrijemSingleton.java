package org.foi.uzdiz.singletons;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.observers.KonkretniPaketSubjekt;
import org.foi.uzdiz.observers.OsobaObserver;
import org.foi.uzdiz.observers.PaketSubjekt;
import org.foi.uzdiz.observers.PosiljateljObserver;
import org.foi.uzdiz.observers.PrimateljObserver;

public class UredPrijemSingleton {
  public static UredPrijemSingleton uredPrijemSingleton;
  public List<Paket> listaPaketiPrijem = new ArrayList<>();

  private UredPrijemSingleton() {}

  public static UredPrijemSingleton getInstance() {
    if (uredPrijemSingleton == null) {
      uredPrijemSingleton = new UredPrijemSingleton();
    }
    return uredPrijemSingleton;
  }

  public void evidentirajPakete(LocalDateTime virtualnoVrijeme) {

    for (Paket paket : TvrtkaSingleton.getInstance().listaPaketi) {
      PaketSubjekt konkretniSubjekt = new KonkretniPaketSubjekt();
      if (paket.dohvatiVrijemePrijemaLocalDateTime().isBefore(virtualnoVrijeme)
          && !oznakaVecPostoji(paket)) {
        OsobaObserver posiljatelj = new PosiljateljObserver(paket.dohvatiPosiljatelja());
        OsobaObserver primatelj = new PrimateljObserver(paket.dohvatiPrimatelja());
        if (paket.dohvatiVrstuPaketa().equals("X")) {
          if (paket.dohvatiTezinu() <= TvrtkaSingleton.getInstance().dohvatiMT()) {
            listaPaketiPrijem.add(paket);
            for (String podaci : TvrtkaSingleton.getInstance().listaPO) {
              String[] dijelovi = podaci.split(";");
              if (paket.dohvatiOznaku().equals(dijelovi[0])) {
                if (dijelovi[3].equals("D")) {
                  konkretniSubjekt.dodajObserver(posiljatelj);
                }
                if (dijelovi[4].equals("D")) {
                  konkretniSubjekt.dodajObserver(primatelj);
                }
                konkretniSubjekt.obavijestiOsobuPrijem(paket);
              }
            }

          }
        } else {
          listaPaketiPrijem.add(paket);
          for (String podaci : TvrtkaSingleton.getInstance().listaPO) {
            String[] dijelovi = podaci.split(";");
            if (paket.dohvatiOznaku().equals(dijelovi[0])) {
              if (dijelovi[3].equals("D")) {
                konkretniSubjekt.dodajObserver(posiljatelj);
              }
              if (dijelovi[4].equals("D")) {
                konkretniSubjekt.dodajObserver(primatelj);
              }
              konkretniSubjekt.obavijestiOsobuPrijem(paket);
            }
          }
        }
      }
    }
  }

  private boolean oznakaVecPostoji(Paket paket) {
    for (Paket paketPrijem : listaPaketiPrijem) {
      if (paketPrijem.dohvatiOznaku().equals(paket.dohvatiOznaku())) {
        return true;
      }
    }
    return false;
  }
}
