package org.foi.uzdiz.chains;

import java.time.format.DateTimeFormatter;
import org.foi.uzdiz.singletons.TvrtkaSingleton;
import org.foi.uzdiz.singletons.VirtualniSatSingleton;
import org.foi.uzdiz.visitors.KonkretniVisitor;
import org.foi.uzdiz.visitors.VoziloElement;
import org.foi.uzdiz.visitors.VoziloVisitor;

public class HandlerKomandeSV implements HandlerKomandi {
  private HandlerKomandi sljedbenik;

  @Override
  public void obradiKomandu(String opcija, String[] opcijaKomande) {
    if (provjeriKomanduSV(opcija) && opcijaKomande[0].equals("SV")) {
      komandaSV(opcija);
    } else if (sljedbenik != null) {
      sljedbenik.obradiKomandu(opcija, opcijaKomande);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
  }

  private static boolean provjeriKomanduSV(String opcija) {
    String regexOpcijaSV = "^SV$";
    if (opcija.matches(regexOpcijaSV)) {
      return true;
    } else {
      return false;
    }
  }

  private void komandaSV(String opcija) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    System.out.println("Pregled podataka za vozila u vrijeme virtualnog sata: "
        + VirtualniSatSingleton.getInstance().dohvatiPravoVrijeme().format(format) + "\n");
    int ukupnaDuljinaLinije = 15 + 20 + 15 + 20 + 25 + 20 + 20 + 15 + 5 + 4;
    System.out.println("-".repeat(ukupnaDuljinaLinije));
    System.out.printf("|%-15s|%-20s|%-15s|%-20s|%-25s|%-20s|%-20s|%-15s|\n", "Registracija", "Opis",
        "Status", "Odvoženi kilometri", "Broj paketa", "Zauzeće prostora(%)", "Zauzeće težine(%)",
        "Broj vožnji");
    System.out.println("-".repeat(ukupnaDuljinaLinije));

    VoziloVisitor visitor = new KonkretniVisitor();
    for (VoziloElement vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      vozilo.accept(visitor);
    }
    System.out.println("-".repeat(ukupnaDuljinaLinije));
  }
}
