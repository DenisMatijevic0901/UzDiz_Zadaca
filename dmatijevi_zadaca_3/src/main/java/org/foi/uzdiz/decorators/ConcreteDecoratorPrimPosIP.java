package org.foi.uzdiz.decorators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.singletons.UredDostavaSingleton;
import org.foi.uzdiz.singletons.UredPrijemSingleton;
import org.foi.uzdiz.singletons.VirtualniSatSingleton;

public class ConcreteDecoratorPrimPosIP extends DecoratorIP {

  private String osoba;
  private List<Paket> listaPaketiPrijem = UredPrijemSingleton.getInstance().listaPaketiPrijem;

  public ConcreteDecoratorPrimPosIP(ComponentIP componentIP, String osoba) {
    super(componentIP);
    this.osoba = osoba;
  }

  @Override
  public void ispisi() {
    super.ispisi();
    System.out.println("\n");
    System.out.println("Dodatna funkcionalnost (komanda: IP 'primatelj/posiljatelj'):\n");
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    String trenutnoVirtualnoVrijeme =
        VirtualniSatSingleton.getInstance().dohvatiPravoVrijeme().format(format);

    if (!listaPaketiPrijem.isEmpty()) {
      System.out.println("Popis poslanih ili primljenih paketa za osobu: " + osoba + " u "
          + "vrijeme virtualnog sata: " + trenutnoVirtualnoVrijeme + "\n");
      ispisiTablicu(VirtualniSatSingleton.getInstance().dohvatiPravoVrijeme());
    } else {
      System.out.println("Lista poslanih ili primljenih paketa za osobu: " + osoba + " je prazna.");
    }
  }

  public void ispisiTablicu(LocalDateTime trenutnoVirtualnoVrijeme) {
    String[] zaglavlje = {"Oznaka paketa", "Vrijeme prijema", "Posiljatelj", "Primatelj",
        "Vrsta paketa", "Vrsta usluge", "Status isporuke", "Vrijeme preuzimanja"};
    int[] velicinaKolona = {20, 20, 20, 20, 20, 20, 20, 20};
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    ispisiRed(zaglavlje, velicinaKolona);

    for (Paket paket : listaPaketiPrijem) {
      if (paket.dohvatiPrimatelja().equals(osoba) || paket.dohvatiPosiljatelja().equals(osoba)) {
        String oznakaPaketa = paket.dohvatiOznaku();
        LocalDateTime vrijemePrijema = paket.dohvatiVrijemePrijemaLocalDateTime();
        String posiljatelj = paket.dohvatiPosiljatelja();
        String primatelj = paket.dohvatiPrimatelja();
        String vrstaPaketa = paket.dohvatiVrstuPaketa();
        String vrstaUsluge = paket.dohvatiUsluguDostave();


        String vrijemePreuzimanja = provjeriVrijemePreuzimanja(paket);
        String statusIsporuke = provjeriStatusIsporuke(vrijemePrijema, trenutnoVirtualnoVrijeme,
            vrijemePreuzimanja, paket);
        String[] red;
        if (vrijemePreuzimanja.equals("")) {
          red = new String[] {oznakaPaketa, vrijemePrijema.format(format), posiljatelj, primatelj,
              vrstaPaketa, vrstaUsluge, statusIsporuke, ""};
        } else {
          red = new String[] {oznakaPaketa, vrijemePrijema.format(format), posiljatelj, primatelj,
              vrstaPaketa, vrstaUsluge, statusIsporuke,
              LocalDateTime.parse(vrijemePreuzimanja).format(format)};
        }
        ispisiRed(red, velicinaKolona);
      }
    }
  }

  private String provjeriStatusIsporuke(LocalDateTime vrijemePrijema,
      LocalDateTime trenutnoVirtualnoVrijeme, String vrijemePreuzimanja, Paket paket) {
    String statusIsporuke = "";
    if (vrijemePrijema.isBefore(trenutnoVirtualnoVrijeme)) {
      statusIsporuke = "PRIMLJEN";
    } else {
      statusIsporuke = "NIJE PRIMLJEN";
    }
    if (vrijemePreuzimanja.equals("")) {
      statusIsporuke = "PRIMLJEN";
    } else if (paket.isporucen == true) {
      statusIsporuke = "DOSTAVLJEN";
    } else {
      statusIsporuke = "U DOSTAVI";
    }
    return statusIsporuke;
  }

  private String provjeriVrijemePreuzimanja(Paket paket) {
    String vrijemePreuzimanja = "";
    for (String podatak : UredDostavaSingleton.getInstance().listaPaketiPreuzeti) {
      String[] dijelovi = podatak.split(";");
      if (paket.dohvatiOznaku().equals(dijelovi[0])) {
        vrijemePreuzimanja = dijelovi[1];
        break;
      }
    }
    return vrijemePreuzimanja;
  }

  public static void ispisiRed(String[] podaci, int[] velicinaKolona) {
    StringBuilder red = new StringBuilder("| ");
    for (int i = 0; i < podaci.length; i++) {
      red.append(String.format("%-" + velicinaKolona[i] + "s | ", podaci[i]));
    }
    System.out.println(red);
  }

}
