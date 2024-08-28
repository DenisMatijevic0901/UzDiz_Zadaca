package org.foi.uzdiz.singletons;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.classes.Osoba;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.classes.Vozilo;
import org.foi.uzdiz.classes.Vrsta;
import org.foi.uzdiz.observers.KonkretniPaketSubjekt;
import org.foi.uzdiz.observers.OsobaObserver;
import org.foi.uzdiz.observers.PaketSubjekt;
import org.foi.uzdiz.observers.PosiljateljObserver;
import org.foi.uzdiz.observers.PrimateljObserver;

public class UredDostavaSingleton {
  public static UredDostavaSingleton uredDostavaSingleton;
  public List<Paket> listaPaketiDostava = new ArrayList<>();
  public List<String> listaPaketiPreuzeti = new ArrayList<>();
  public List<Paket> listaRasporedenihHitnihIObicnihPaketa = new ArrayList<>();

  private UredDostavaSingleton() {}

  public static UredDostavaSingleton getInstance() {
    if (uredDostavaSingleton == null) {
      uredDostavaSingleton = new UredDostavaSingleton();
    }
    return uredDostavaSingleton;
  }

  public void ukrcajPakete(LocalDateTime virtualnoVrijeme) {
    sortirajVozilaPoRedoslijedu();
    rasporediPakete();
    ukrcajRasporedenePakete(virtualnoVrijeme);
  }

  public void ukrcajRasporedenePakete(LocalDateTime virtualnoVrijeme) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    sortirajVozilaPoRedoslijedu();
    provjeriHitniPaketIPosaljiVozilo(virtualnoVrijeme, format);
    for (Paket primljeniPaket : listaRasporedenihHitnihIObicnihPaketa) {
      for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
        if (vozilo.dohvatiSlobodno() == true) {
          if (!listaPaketiDostava.contains(primljeniPaket)
              && primljeniPaket.brojacGreskiPrimatelj == 0) {
            if (provjeriPrimatelja(primljeniPaket)) {
              if (provjeriProstor(primljeniPaket, vozilo) == true
                  && provjeriTezinu(primljeniPaket, vozilo) == true
                  && provjeriGepek(primljeniPaket, vozilo, virtualnoVrijeme) == false
                  && provjeriHitniPaket(primljeniPaket, vozilo, virtualnoVrijeme) == false) {
                listaPaketiDostava.add(primljeniPaket);
                vozilo.gepek.add(primljeniPaket);
                System.out
                    .println("\nPaket: " + primljeniPaket.dohvatiOznaku() + " je ukrcan u vozilo: "
                        + vozilo.dohvatiOpis() + " - " + vozilo.dohvatiRegistraciju()
                        + " u vrijeme virtualnog sata: " + virtualnoVrijeme.format(format) + "\n");
                if (vozilo.postojiVrijemePrvogPaketa == false) {
                  vozilo.vrijemePrvogPaketa = virtualnoVrijeme;
                  vozilo.postojiVrijemePrvogPaketa = true;
                }
              } else {
                vozilo.slobodno = false;
                System.out.println(
                    "\nVozilo " + vozilo.dohvatiOpis() + " - " + vozilo.dohvatiRegistraciju()
                        + " odlazi na dostavu " + "paketa u vrijeme virtualnog sata: "
                        + virtualnoVrijeme.format(format) + "\n");
                isporuciPakete(vozilo, virtualnoVrijeme);
                if (provjeriPrimatelja(primljeniPaket)) {
                  dodajPaketUIduceVozilo(primljeniPaket, virtualnoVrijeme, format);
                }
              }
            }
          }
          break;
        }
      }
    }
  }

  private boolean provjeriPrimatelja(Paket primljeniPaket) {
    boolean postoji = false;

    for (Osoba osoba : TvrtkaSingleton.getInstance().listaOsobe) {
      if (osoba.dohvatiOsobu().equals(primljeniPaket.dohvatiPrimatelja())) {
        postoji = true;
        primljeniPaket.postojiPrimatelj = true;
      }
    }

    if (postoji == false && primljeniPaket.brojacGreskiPrimatelj == 0) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Primatelj " + primljeniPaket.dohvatiPrimatelja()
          + " za paket " + primljeniPaket.dohvatiOznaku() + " ne postoji.");
      primljeniPaket.brojacGreskiPrimatelj++;
    }
    return postoji;
  }

  private void provjeriHitniPaketIPosaljiVozilo(LocalDateTime virtualnoVrijeme,
      DateTimeFormatter format) {
    for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      if (vozilo.vrijemePrvogPaketa != null && vozilo.slobodno == true) {
        if (virtualnoVrijeme.isAfter(vozilo.vrijemePrvogPaketa.plusHours(1))
            || virtualnoVrijeme.isEqual(vozilo.vrijemePrvogPaketa.plusHours(1))) {
          for (Paket paketUGepeku : vozilo.gepek) {
            if (paketUGepeku.dohvatiUsluguDostave().equals("H")) {
              System.out.println("\nVozilo " + vozilo.dohvatiOpis() + " - "
                  + vozilo.dohvatiRegistraciju() + " odlazi na dostavu "
                  + "paketa u vrijeme virtualnog sata: " + virtualnoVrijeme.format(format) + "\n");
              vozilo.slobodno = false;
              isporuciPakete(vozilo, virtualnoVrijeme);
              break;
            }
          }
        }
      }
    }
  }

  private void dodajPaketUIduceVozilo(Paket primljeniPaket, LocalDateTime virtualnoVrijeme,
      DateTimeFormatter format) {
    Vozilo iduceSlobodnoVozilo = pronadiIduceSlobodnoVozilo();

    if (iduceSlobodnoVozilo != null) {
      if (provjeriProstor(primljeniPaket, iduceSlobodnoVozilo) == true
          && provjeriTezinu(primljeniPaket, iduceSlobodnoVozilo) == true) {
        listaPaketiDostava.add(primljeniPaket);
        iduceSlobodnoVozilo.gepek.add(primljeniPaket);
        System.out.println("\nPaket: " + primljeniPaket.dohvatiOznaku() + " je ukrcan u vozilo: "
            + iduceSlobodnoVozilo.dohvatiOpis() + " - " + iduceSlobodnoVozilo.dohvatiRegistraciju()
            + " u vrijeme virtualnog sata: " + virtualnoVrijeme.format(format) + "\n");
      }
    } else {
      System.out.println("Nema dostupnih slobodnih vozila za dodavanje paketa.");
    }
  }

  public Vozilo pronadiIduceSlobodnoVozilo() {
    for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      if (vozilo.dohvatiSlobodno()) {
        return vozilo;
      }
    }
    return null;
  }

  private void rasporediPakete() {
    for (Paket primljeniPaket : UredPrijemSingleton.getInstance().listaPaketiPrijem) {
      if (primljeniPaket.dohvatiUsluguDostave().equals("H")) {
        listaRasporedenihHitnihIObicnihPaketa.add(0, primljeniPaket);
      } else {
        listaRasporedenihHitnihIObicnihPaketa.add(primljeniPaket);
      }
    }
  }

  private void isporuciPakete(Vozilo vozilo, LocalDateTime virtualnoVrijeme) {
    int brojacVi = 1;
    for (Paket paket : vozilo.gepek) {
      LocalDateTime vrijemeIsporuke =
          virtualnoVrijeme.plusMinutes(TvrtkaSingleton.getInstance().dohvatiVI() * brojacVi);
      if (vrijemeIsporuke.isAfter(virtualnoVrijeme)) {
        listaPaketiPreuzeti
            .add(paket.dohvatiOznaku() + ";" + vrijemeIsporuke + ";" + paket.dohvatiPrimatelja());
      }
      brojacVi++;
    }

  }

  public void ispisiDostavu(LocalDateTime virtualnoVrijeme) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    for (Paket paket : listaPaketiDostava) {
      PaketSubjekt konkretniSubjekt = new KonkretniPaketSubjekt();
      for (String podatak : listaPaketiPreuzeti) {
        String[] dijelovi = podatak.split(";");
        if (paket.dohvatiOznaku().equals(dijelovi[0])) {
          LocalDateTime vrijemeIsporukePaketa = LocalDateTime.parse(dijelovi[1]);
          if ((vrijemeIsporukePaketa.isEqual(virtualnoVrijeme)
              || vrijemeIsporukePaketa.isBefore(virtualnoVrijeme)) && paket.isporucen == false) {
            OsobaObserver posiljatelj = new PosiljateljObserver(paket.dohvatiPosiljatelja());
            OsobaObserver primatelj = new PrimateljObserver(paket.dohvatiPrimatelja());
            System.out.println("Paket: " + dijelovi[0] + " je isporučen primatelju: " + dijelovi[2]
                + " u vrijeme virtualnog sata: " + vrijemeIsporukePaketa.format(format) + "\n");
            paket.isporucen = true;
            for (String podaci : TvrtkaSingleton.getInstance().listaPO) {
              String[] dijeloviPO = podaci.split(";");
              if (paket.dohvatiOznaku().equals(dijeloviPO[0])) {
                if (dijeloviPO[3].equals("D")) {
                  konkretniSubjekt.dodajObserver(posiljatelj);
                }
                if (dijeloviPO[4].equals("D")) {
                  konkretniSubjekt.dodajObserver(primatelj);
                }
                konkretniSubjekt.obavijestiOsobuDostava(paket);
              }
            }
          }
        }
      }
    }
  }

  private void sortirajVozilaPoRedoslijedu() {
    int brojVozila = TvrtkaSingleton.getInstance().listaVozila.size();
    for (int i = 1; i < brojVozila; i++) {
      Vozilo vozilo = TvrtkaSingleton.getInstance().listaVozila.get(i);
      int j = i - 1;
      while (j >= 0 && vozilo.dohvatiRedoslijed() < TvrtkaSingleton.getInstance().listaVozila.get(j)
          .dohvatiRedoslijed()) {
        TvrtkaSingleton.getInstance().listaVozila.set(j + 1,
            TvrtkaSingleton.getInstance().listaVozila.get(j));
        j--;
      }
      TvrtkaSingleton.getInstance().listaVozila.set(j + 1, vozilo);
    }
  }

  private boolean provjeriProstor(Paket primljeniPaket, Vozilo vozilo) {
    boolean imaMjesta = true;
    Float prostorIzracun = (float) 0;

    if (primljeniPaket.dohvatiVrstuPaketa().equals("X")) {
      prostorIzracun = primljeniPaket.dohvatiVisinu() * primljeniPaket.dohvatiSirinu()
          * primljeniPaket.dohvatiDuzinu();
    } else {
      for (Vrsta vrsta : TvrtkaSingleton.getInstance().listaVrste) {
        if (primljeniPaket.dohvatiVrstuPaketa().equals(vrsta.dohvatiOznaku())) {
          prostorIzracun = vrsta.dohvatiVisinu() * vrsta.dohvatiSirinu() * vrsta.dohvatiDuzinu();
        }
      }
    }

    float noviTrenutniKapacitet = vozilo.dohvatiTrenutniKapacitetProstoraUM3() + prostorIzracun;

    if (noviTrenutniKapacitet > vozilo.dohvatiKapacitetProstoraUM3()) {
      imaMjesta = false;
    } else {
      vozilo.trenutniKapacitetProstoraUM3 = noviTrenutniKapacitet;
    }

    return imaMjesta;
  }

  private boolean provjeriTezinu(Paket primljeniPaket, Vozilo vozilo) {
    boolean imaMjesta = true;
    Float tezinaIzracun = (float) 0;
    tezinaIzracun = primljeniPaket.dohvatiTezinu();
    vozilo.trenutniKapacitetTezineUKG += tezinaIzracun;
    if (vozilo.dohvatiTrenutniKapacitetTezineUKg() > vozilo.dohvatiKapacitetTezineUKg()) {
      imaMjesta = false;
    }
    return imaMjesta;
  }

  private boolean provjeriGepek(Paket primljeniPaket, Vozilo vozilo,
      LocalDateTime virtualnoVrijeme) {
    boolean mogucaDostava = false;
    if (vozilo.vrijemePrvogPaketa != null) {
      if (virtualnoVrijeme.isAfter(vozilo.vrijemePrvogPaketa.plusHours(1))
          || virtualnoVrijeme.isEqual(vozilo.vrijemePrvogPaketa.plusHours(1))) {
        if (vozilo
            .dohvatiTrenutniKapacitetProstoraUM3() >= (vozilo.dohvatiKapacitetProstoraUM3() / 2)) {
          mogucaDostava = true;
        }
        if (vozilo
            .dohvatiTrenutniKapacitetTezineUKg() >= (vozilo.dohvatiKapacitetTezineUKg() / 2)) {
          mogucaDostava = true;
        }
      }
    }
    return mogucaDostava;
  }

  private boolean provjeriHitniPaket(Paket primljeniPaket, Vozilo vozilo,
      LocalDateTime virtualnoVrijeme) {
    boolean mogucaDostava = false;
    if (vozilo.vrijemePrvogPaketa != null) {
      if (virtualnoVrijeme.isAfter(vozilo.vrijemePrvogPaketa.plusHours(1))
          || virtualnoVrijeme.isEqual(vozilo.vrijemePrvogPaketa.plusHours(1))) {
        for (Paket paketUGepeku : vozilo.gepek) {
          if (paketUGepeku.dohvatiUsluguDostave().equals("H")) {
            mogucaDostava = true;
          }
        }
      }
    }
    return mogucaDostava;
  }

  public void ispisSvihPaketa() {
    if (!listaPaketiDostava.isEmpty()) {
      System.out.println("Popis svih paketa:\n");
      for (Paket paket : listaPaketiDostava) {
        System.out.println("Oznaka: " + paket.dohvatiOznaku());
        System.out.println("Vrijeme prijema: " + paket.dohvatiVrijemePijema());
        System.out.println("Pošiljatelj: " + paket.dohvatiPosiljatelja());
        System.out.println("Primatelj: " + paket.dohvatiPrimatelja());
        System.out.println("Vrsta paketa: " + paket.dohvatiVrstuPaketa());
        System.out.println("Visina: " + paket.dohvatiVisinu());
        System.out.println("Širina: " + paket.dohvatiSirinu());
        System.out.println("Dužina: " + paket.dohvatiDuzinu());
        System.out.println("Težina: " + paket.dohvatiTezinu());
        System.out.println("Usluga dostave: " + paket.dohvatiUsluguDostave());
        System.out.println("Iznos pouzeća: " + paket.dohvatiIznosPouzeca());
        System.out.println("");
      }
    } else {
      System.out.println("Lista paketa je prazna.");
    }
  }
}
