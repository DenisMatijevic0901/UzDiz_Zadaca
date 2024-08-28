package org.foi.uzdiz.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.singletons.GreskeSingleton;
import org.foi.uzdiz.states.AktivnoStanje;
import org.foi.uzdiz.states.NijeAktivnoStanje;
import org.foi.uzdiz.states.NijeIspravnoStanje;
import org.foi.uzdiz.states.VoziloState;
import org.foi.uzdiz.visitors.VoziloElement;
import org.foi.uzdiz.visitors.VoziloVisitor;

public class Vozilo implements VoziloElement {
  private String registracija;
  private String opis;
  private Float kapacitetTezineUKg;
  private Float kapacitetProstoraUM3;
  public Float trenutniKapacitetTezineUKG;
  public Float trenutniKapacitetProstoraUM3;
  private int redoslijed;
  public Float prosjecnaBrzina;
  public String podrucjaPoRangu;
  public boolean slobodno;
  public LocalDateTime vrijemePrvogPaketa;
  public boolean postojiVrijemePrvogPaketa;
  public List<Paket> gepek = new ArrayList<>();
  public VoziloState trenutnoStanje;

  public Vozilo(String registracija, String opis, Float kapacitetTezineUKg,
      Float kapacitetProstoraUM3, int redoslijed, Float prosjecnaBrzina, String podrucjaPoRangu,
      String status) {
    this.registracija = registracija;
    this.opis = opis;
    this.kapacitetTezineUKg = kapacitetTezineUKg;
    this.kapacitetProstoraUM3 = kapacitetProstoraUM3;
    this.redoslijed = redoslijed;
    this.prosjecnaBrzina = prosjecnaBrzina;
    this.podrucjaPoRangu = podrucjaPoRangu;
    postaviStanjeNaTemeljuStatusa(status);
    slobodno = true;
    trenutniKapacitetTezineUKG = (float) 0;
    trenutniKapacitetProstoraUM3 = (float) 0;
    vrijemePrvogPaketa = null;
    postojiVrijemePrvogPaketa = false;
  }

  private void postaviStanjeNaTemeljuStatusa(String status) {
    if (status.equals("A")) {
      this.trenutnoStanje = new AktivnoStanje();
    } else if (status.equals("NI")) {
      this.trenutnoStanje = new NijeIspravnoStanje();
    } else if (status.equals("NA")) {
      this.trenutnoStanje = new NijeAktivnoStanje();
    } else {
      GreskeSingleton.getInstance().dohvatiGreskuS("Nepoznat status");
    }
  }

  public String dohvatiRegistraciju() {
    return registracija;
  }

  public String dohvatiOpis() {
    return opis;
  }

  public Float dohvatiKapacitetTezineUKg() {
    return kapacitetTezineUKg;
  }

  public Float dohvatiKapacitetProstoraUM3() {
    return kapacitetProstoraUM3;
  }

  public int dohvatiRedoslijed() {
    return redoslijed;
  }

  public Float dohvatiProsjecnuBrzinu() {
    return prosjecnaBrzina;
  }

  public String dohvatiPodrucjaPoRangu() {
    return podrucjaPoRangu;
  }

  public String dohvatiStatus() {
    String nazivKlaseStanja = trenutnoStanje.getClass().getSimpleName();
    String stanje = "";
    if (nazivKlaseStanja.equals("AktivnoStanje")) {
      stanje = "A";
    }
    if (nazivKlaseStanja.equals("NijeIspravnoStanje")) {
      stanje = "NI";
    }
    if (nazivKlaseStanja.equals("NijeAktivnoStanje")) {
      stanje = "NA";
    }
    return stanje;
  }

  public boolean dohvatiSlobodno() {
    return slobodno;
  }

  public Float dohvatiTrenutniKapacitetTezineUKg() {
    return trenutniKapacitetTezineUKG;
  }

  public Float dohvatiTrenutniKapacitetProstoraUM3() {
    return trenutniKapacitetProstoraUM3;
  }

  public void postaviTrenutnoStanje(VoziloState stanje) {
    this.trenutnoStanje = stanje;
  }

  public void promijeniStatus(Vozilo vozilo, String status, DateTimeFormatter format) {
    trenutnoStanje.promijeniStatus(vozilo, status, format);
  }

  public void ispisiGepek() {
    if (!gepek.isEmpty()) {
      System.out.println("Popis paketa u autu:\n");
      for (Paket paket : gepek) {
        System.out.println("Id: " + paket.dohvatiOznaku());
        System.out.println("");
      }
    } else {
      System.out.println("Lista paketa u autu je prazna.");
    }
  }

  @Override
  public void accept(VoziloVisitor visitor) {
    visitor.visitVozilo(this);
  }

  public Vozilo(Vozilo original) {
    this.registracija = original.registracija;
    this.opis = original.opis;
    this.kapacitetTezineUKg = original.kapacitetTezineUKg;
    this.kapacitetProstoraUM3 = original.kapacitetProstoraUM3;
    this.redoslijed = original.redoslijed;
    this.prosjecnaBrzina = original.prosjecnaBrzina;
    this.podrucjaPoRangu = original.podrucjaPoRangu;
    this.trenutnoStanje = original.trenutnoStanje;
    this.slobodno = original.slobodno;
    this.trenutniKapacitetTezineUKG = original.trenutniKapacitetTezineUKG;
    this.trenutniKapacitetProstoraUM3 = original.trenutniKapacitetProstoraUM3;
    this.vrijemePrvogPaketa = original.vrijemePrvogPaketa;
    this.postojiVrijemePrvogPaketa = original.postojiVrijemePrvogPaketa;
  }

  public Vozilo kloniraj() {
    return new Vozilo(this);
  }
}
