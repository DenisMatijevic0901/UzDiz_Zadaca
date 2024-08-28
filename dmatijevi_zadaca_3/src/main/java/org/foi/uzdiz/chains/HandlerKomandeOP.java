package org.foi.uzdiz.chains;

import org.foi.uzdiz.proxies.PaketProxy;
import org.foi.uzdiz.proxies.PaketSubjektProxy;

public class HandlerKomandeOP implements HandlerKomandi {
  private HandlerKomandi sljedbenik;

  @Override
  public void obradiKomandu(String opcija, String[] opcijaKomande) {
    if (provjeriKomanduOP(opcija) && opcijaKomande[0].equals("OP")) {
      komandaOP(opcija);
    } else if (sljedbenik != null) {
      sljedbenik.obradiKomandu(opcija, opcijaKomande);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
  }

  private static boolean provjeriKomanduOP(String opcija) {
    String regexOpcijaOP = "^OP '[a-zA-Z0-9ćčšđžĆČŠĐŽ._ -]+' [a-zA-Z0-9ćčšđžĆČŠĐŽ_-]+$";
    if (opcija.matches(regexOpcijaOP)) {
      return true;
    } else {
      return false;
    }
  }

  private void komandaOP(String opcija) {
    String dijeloviOpcije[] = opcija.split(" ");
    String dijeloviOpcijeOsoba[] = opcija.split("'");
    String osoba = dijeloviOpcijeOsoba[1].trim();
    String paket = dijeloviOpcije[dijeloviOpcije.length - 1];

    PaketSubjektProxy proxy = new PaketProxy();
    proxy.obrisiPaket(osoba, paket);
  }
}
