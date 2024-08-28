package org.foi.uzdiz.chains;

import org.foi.uzdiz.classes.Mjesto;
import org.foi.uzdiz.classes.Podrucje;
import org.foi.uzdiz.classes.Ulica;
import org.foi.uzdiz.composites.HijerarhijaComponent;
import org.foi.uzdiz.composites.MjestoComposite;
import org.foi.uzdiz.composites.PodrucjeComposite;
import org.foi.uzdiz.composites.UlicaLeaf;
import org.foi.uzdiz.singletons.TvrtkaSingleton;

public class HandlerKomandePP implements HandlerKomandi {
  private HandlerKomandi sljedbenik;

  @Override
  public void obradiKomandu(String opcija, String[] opcijaKomande) {
    if (provjeriKomanduPP(opcija) && opcijaKomande[0].equals("PP")) {
      komandaPP();
    } else if (sljedbenik != null) {
      sljedbenik.obradiKomandu(opcija, opcijaKomande);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
  }

  private static boolean provjeriKomanduPP(String opcija) {
    String regexOpcijaPP = "^PP$";
    if (opcija.matches(regexOpcijaPP)) {
      return true;
    } else {
      return false;
    }
  }

  private void komandaPP() {
    for (Podrucje podrucje : TvrtkaSingleton.getInstance().listaPodrucja) {
      HijerarhijaComponent komponenta = konstruirajHijerarhiju(podrucje);
      komponenta.ispisiDetalje(0);
    }
  }

  private HijerarhijaComponent konstruirajHijerarhiju(Podrucje podrucje) {
    PodrucjeComposite root = new PodrucjeComposite(podrucje.dohvatiId(), "Područje");
    String[] gradUlica = podrucje.dohvatiGradUlica().split(",");
    String trenutnoMjestoNaziv = null;
    MjestoComposite trenutnoMjesto = null;

    for (String gradUlicaPar : gradUlica) {
      String[] gradUlicaSplit = gradUlicaPar.split(":");
      String grad = gradUlicaSplit[0].trim();
      String ulica = gradUlicaSplit[1].trim();

      for (Mjesto mjesto : TvrtkaSingleton.getInstance().listaMjesta) {
        if (mjesto.dohvatiId() == Integer.parseInt(grad)) {
          if (trenutnoMjesto == null || !mjesto.dohvatiNaziv().equals(trenutnoMjestoNaziv)) {
            trenutnoMjestoNaziv = mjesto.dohvatiNaziv();
            trenutnoMjesto = new MjestoComposite(mjesto.dohvatiId(), trenutnoMjestoNaziv);
            root.dodajKomponentu(trenutnoMjesto);
          }

          if (!ulica.equals("*")) {
            for (Ulica ulic : TvrtkaSingleton.getInstance().listaUlica) {
              if (ulic.dohvatiId() == Integer.parseInt(ulica)) {
                trenutnoMjesto
                    .dodajKomponentu(new UlicaLeaf(ulic.dohvatiId(), ulic.dohvatiNaziv()));
              }
            }
          } else {
            String sveUliceTrenutnogMjesta = mjesto.dohvatiUlicu();
            String[] jednaPoJedna = sveUliceTrenutnogMjesta.split(",");
            for (String ulicaId : jednaPoJedna) {
              for (Ulica trenutnaUlica : TvrtkaSingleton.getInstance().listaUlica) {
                if (trenutnaUlica.dohvatiId() == Integer.parseInt(ulicaId)) {
                  trenutnoMjesto.dodajKomponentu(
                      new UlicaLeaf(trenutnaUlica.dohvatiId(), trenutnaUlica.dohvatiNaziv()));
                }
              }
            }
          }
        }
      }
    }
    return root;
  }
}
