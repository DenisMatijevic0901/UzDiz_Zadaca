package org.foi.uzdiz.chains;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.foi.uzdiz.singletons.TvrtkaSingleton;
import org.foi.uzdiz.singletons.UredDostavaSingleton;
import org.foi.uzdiz.singletons.UredPrijemSingleton;
import org.foi.uzdiz.singletons.VirtualniSatSingleton;

public class HandlerKomandeVR implements HandlerKomandi {
  private HandlerKomandi sljedbenik;

  @Override
  public void obradiKomandu(String opcija, String[] opcijaKomande) {
    if (provjeriKomanduVR(opcija) && opcijaKomande[0].equals("VR")) {
      komandaVR(opcijaKomande[1]);
    } else if (sljedbenik != null) {
      sljedbenik.obradiKomandu(opcija, opcijaKomande);
    }
  }

  @Override
  public void postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
  }

  private static boolean provjeriKomanduVR(String opcija) {
    String regexOpcijaVR = "^VR (0?[1-9]|1[0-9]|2[0-4])$";
    if (opcija.matches(regexOpcijaVR)) {
      return true;
    } else {
      return false;
    }
  }

  private static void komandaVR(String brojSati) {
    int mnoziteljSekundi = TvrtkaSingleton.getInstance().dohvatiMS();
    LocalDateTime krajnjeVrijemeRada = VirtualniSatSingleton.getInstance().dohvatiPravoVrijeme()
        .plusHours(Integer.parseInt(brojSati));
    LocalDateTime virtualnoVrijeme = VirtualniSatSingleton.getInstance().dohvatiPravoVrijeme();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");

    String formatiranoStvarnoVrijeme =
        VirtualniSatSingleton.getInstance().dohvatiPravoVrijeme().format(format);
    System.out.println("\nTrenutno vrijeme virtualnog sata: " + formatiranoStvarnoVrijeme);

    while (virtualnoVrijeme.isBefore(krajnjeVrijemeRada) && virtualnoVrijeme.toLocalTime()
        .isBefore(TvrtkaSingleton.getInstance().dohvatiLocalTimeKR())) {
      virtualnoVrijeme = virtualnoVrijeme.plusSeconds(mnoziteljSekundi);
      VirtualniSatSingleton.getInstance().postaviVirtualnoVrijeme(virtualnoVrijeme);
      String formatiranoVrijeme = virtualnoVrijeme.format(format);
      System.out.println("\nVirtualno vrijeme: " + formatiranoVrijeme);

      try {

        if (virtualnoVrijeme.toLocalTime()
            .isAfter(TvrtkaSingleton.getInstance().dohvatiLocalTimePR())) {
          Thread.sleep(1000);
          UredPrijemSingleton.getInstance().evidentirajPakete(virtualnoVrijeme);
          UredDostavaSingleton.getInstance().ukrcajPakete(virtualnoVrijeme);
          UredDostavaSingleton.getInstance().ispisiDostavu(virtualnoVrijeme);
        } else {
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    VirtualniSatSingleton.getInstance().azurirajVirtualnoVrijeme(virtualnoVrijeme);
    if (virtualnoVrijeme.toLocalTime() == TvrtkaSingleton.getInstance().dohvatiLocalTimeKR()) {
      System.out.println("Kraj radnog vremena.");
    } else {
      System.out.println("Vrijeme zadano komandom VR hh je isteklo.");
    }
  }
}
