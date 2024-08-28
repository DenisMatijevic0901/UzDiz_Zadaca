package org.foi.uzdiz.decorators;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.classes.Vrsta;
import org.foi.uzdiz.singletons.TvrtkaSingleton;
import org.foi.uzdiz.singletons.UredDostavaSingleton;
import org.foi.uzdiz.singletons.VirtualniSatSingleton;

public class ConcreteComponentIP implements ComponentIP {

  private List<Paket> listaPaketiPrijem;

  public ConcreteComponentIP(List<Paket> listaPaketiPrijem) {
    this.listaPaketiPrijem = listaPaketiPrijem;
  }

  @Override
  public void ispisi() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    String trenutnoVirtualnoVrijeme =
        VirtualniSatSingleton.getInstance().dohvatiPravoVrijeme().format(format);

    if (!listaPaketiPrijem.isEmpty()) {
      System.out.println("Popis primljenih paketa (" + listaPaketiPrijem.size() + ") u  "
          + "vrijeme virtualnog sata: " + trenutnoVirtualnoVrijeme + "\n");
      ispisiTablicu(VirtualniSatSingleton.getInstance().dohvatiPravoVrijeme());
    } else {
      System.out.println("Lista paketa je prazna.");
    }
  }


  public void ispisiTablicu(LocalDateTime trenutnoVirtualnoVrijeme) {
    String[] zaglavlje = {"Oznaka paketa", "Vrijeme prijema", "Vrsta paketa", "Vrsta usluge",
        "Status isporuke", "Iznos dostave", "Iznos pouzeća", "Vrijeme preuzimanja"};
    int[] velicinaKolona = {20, 20, 20, 20, 20, 20, 20, 20};
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    ispisiRed(zaglavlje, velicinaKolona);

    for (Paket paket : listaPaketiPrijem) {
      String oznakaPaketa = paket.dohvatiOznaku();
      LocalDateTime vrijemePrijema = paket.dohvatiVrijemePrijemaLocalDateTime();
      String vrstaPaketa = paket.dohvatiVrstuPaketa();
      String vrstaUsluge = paket.dohvatiUsluguDostave();
      String iznosDostave = provjeriIznosDostave(paket);
      String iznosPouzeca = provjeriIznosPouzeca(paket);
      String vrijemePreuzimanja = provjeriVrijemePreuzimanja(paket);
      String statusIsporuke = provjeriStatusIsporuke(vrijemePrijema, trenutnoVirtualnoVrijeme,
          vrijemePreuzimanja, paket);
      String[] red;
      if (vrijemePreuzimanja.equals("")) {
        red = new String[] {oznakaPaketa, vrijemePrijema.format(format), vrstaPaketa, vrstaUsluge,
            statusIsporuke, iznosDostave, iznosPouzeca, ""};
      } else {
        red = new String[] {oznakaPaketa, vrijemePrijema.format(format), vrstaPaketa, vrstaUsluge,
            statusIsporuke, iznosDostave, iznosPouzeca,
            LocalDateTime.parse(vrijemePreuzimanja).format(format)};
      }
      ispisiRed(red, velicinaKolona);
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

  private String provjeriIznosDostave(Paket paket) {
    String iznosDostave = "";
    Float iznosDostaveX;
    for (Vrsta vrsta : TvrtkaSingleton.getInstance().listaVrste) {
      if (paket.dohvatiVrstuPaketa().equals(vrsta.dohvatiOznaku())) {
        if (paket.dohvatiUsluguDostave().equals("H")) {
          iznosDostave = vrsta.dohvatiCijenuHitno().toString();
        } else if (paket.dohvatiUsluguDostave().equals("P")) {
          iznosDostave = "0.0";
        } else {
          iznosDostave = vrsta.dohvatiCijenu().toString();
        }
        if (paket.dohvatiVrstuPaketa().equals("X")) {
          if (paket.dohvatiUsluguDostave().equals("H")) {
            iznosDostaveX = (vrsta.dohvatiCijenuHitno()
                + (vrsta.dohvatiCijenuP()
                    * (paket.dohvatiVisinu() * paket.dohvatiSirinu() * paket.dohvatiDuzinu()))
                + vrsta.dohvatiCijenuT() * paket.dohvatiTezinu());

          } else {
            iznosDostaveX = (vrsta.dohvatiCijenu()
                + (vrsta.dohvatiCijenuP()
                    * (paket.dohvatiVisinu() * paket.dohvatiSirinu() * paket.dohvatiDuzinu()))
                + vrsta.dohvatiCijenuT() * paket.dohvatiTezinu());
          }
          DecimalFormat format = new DecimalFormat("#.##");
          iznosDostave = format.format(iznosDostaveX);
        }
      }
    }
    return iznosDostave;
  }

  private String provjeriIznosPouzeca(Paket paket) {
    String iznosPouzeca = "";
    iznosPouzeca = paket.dohvatiIznosPouzeca().toString();
    return iznosPouzeca;
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
