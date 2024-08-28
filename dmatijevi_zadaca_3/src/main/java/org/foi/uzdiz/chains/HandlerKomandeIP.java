package org.foi.uzdiz.chains;

import java.util.List;
import org.foi.uzdiz.classes.Osoba;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.decorators.ComponentIP;
import org.foi.uzdiz.decorators.ConcreteComponentIP;
import org.foi.uzdiz.decorators.ConcreteDecoratorIP;
import org.foi.uzdiz.decorators.ConcreteDecoratorPrimPosIP;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.singletons.TvrtkaSingleton;
import org.foi.uzdiz.singletons.UredPrijemSingleton;

public class HandlerKomandeIP implements HandlerKomandi {
  private HandlerKomandi sljedbenik;

  @Override
  public void obradiKomandu(String opcija, String[] opcijaKomande) {
    if ((provjeriKomanduIP(opcija) && opcijaKomande[0].equals("IP"))
        || provjeriKomanduPrimPosIP(opcija)) {
      komandaIP(opcija);
    } else if (sljedbenik != null) {
      sljedbenik.obradiKomandu(opcija, opcijaKomande);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
  }

  private static boolean provjeriKomanduIP(String opcija) {
    String regexOpcijaIP = "^IP$";
    if (opcija.matches(regexOpcijaIP)) {
      return true;
    } else {
      return false;
    }
  }

  private static boolean provjeriKomanduPrimPosIP(String opcija) {
    String regexOpcijaPrimPosIP = "^IP '[a-zA-Z0-9ćčšđžĆČŠĐŽ._ -]+'$";
    if (opcija.matches(regexOpcijaPrimPosIP)) {
      return true;
    } else {
      return false;
    }
  }

  private static void komandaIP(String opcija) {
    List<Paket> listaPaketiPrijem = UredPrijemSingleton.getInstance().listaPaketiPrijem;

    ComponentIP componentIP = new ConcreteComponentIP(listaPaketiPrijem);

    if (provjeriKomanduIP(opcija)) {
      ComponentIP concreteDecoratorIP = new ConcreteDecoratorIP(componentIP);
      concreteDecoratorIP.ispisi();
    } else {
      String dijeloviOpcijeOsoba[] = opcija.split("'");
      String osoba = dijeloviOpcijeOsoba[1].trim();
      if (provjeriOsobu(osoba) == true) {
        ComponentIP concreteDecoratorPrimPosIP = new ConcreteDecoratorPrimPosIP(componentIP, osoba);
        concreteDecoratorPrimPosIP.ispisi();
      }
    }
  }

  private static boolean provjeriOsobu(String osoba) {
    for (Osoba trazenaOsoba : TvrtkaSingleton.getInstance().listaOsobe) {
      if (trazenaOsoba.dohvatiOsobu().equals(osoba)) {
        return true;
      }
    }
    GreskeSingleton.getInstance().dohvatiGreskuS("Osoba " + osoba + " ne postoji u listi osoba.");
    return false;
  }
}
