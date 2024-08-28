package org.foi.uzdiz.singletons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.foi.uzdiz.builders.OsobaDirektor;
import org.foi.uzdiz.classes.Mjesto;
import org.foi.uzdiz.classes.Osoba;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.classes.Podrucje;
import org.foi.uzdiz.classes.Ulica;
import org.foi.uzdiz.classes.Vozilo;
import org.foi.uzdiz.classes.Vrsta;
import org.foi.uzdiz.concreteCreators.MjestaCsvKreator;
import org.foi.uzdiz.concreteCreators.OsobeCsvKreator;
import org.foi.uzdiz.concreteCreators.PaketiCsvKreator;
import org.foi.uzdiz.concreteCreators.PodrucjaCsvKreator;
import org.foi.uzdiz.concreteCreators.UliceCsvKreator;
import org.foi.uzdiz.concreteCreators.VozilaCsvKreator;
import org.foi.uzdiz.concreteCreators.VrsteCsvKreator;
import org.foi.uzdiz.mementos.CaretakerKomande;
import org.foi.uzdiz.mementos.OriginatorKomande;
import org.foi.uzdiz.products.CsvProdukt;

public class TvrtkaSingleton {
  public static TvrtkaSingleton tvrtkaSingleton;
  public List<Vozilo> listaVozila = new ArrayList<>();
  public List<Vrsta> listaVrste = new ArrayList<>();
  public List<Paket> listaPaketi = new ArrayList<>();
  public List<Osoba> listaOsobe = new ArrayList<>();
  public List<Mjesto> listaMjesta = new ArrayList<>();
  public List<Podrucje> listaPodrucja = new ArrayList<>();
  public List<Ulica> listaUlica = new ArrayList<>();
  public List<String> listaPO = new ArrayList<>();
  OriginatorKomande originator = new OriginatorKomande();
  CaretakerKomande caretaker = new CaretakerKomande();

  public String vp;
  public String pv;
  public String pp;
  public String po;
  public String pm;
  public String pu;
  public String pmu;
  public int mt;
  public int vi;
  public Date vs;
  public int ms;
  public Date pr;
  public Date kr;
  public int isporuka;
  public String gps;
  public boolean radi = true;

  private TvrtkaSingleton() {}

  public static TvrtkaSingleton getInstance() {
    if (tvrtkaSingleton == null) {
      tvrtkaSingleton = new TvrtkaSingleton();
    }
    return tvrtkaSingleton;
  }

  public void spremiVP(String argVP) {
    vp = argVP;
  }

  public String dohvatiVP() {
    return this.vp;
  }

  public void spremiPV(String argPV) {
    pv = argPV;
  }

  public String dohvatiPV() {
    return this.pv;
  }

  public void spremiPP(String argPP) {
    pp = argPP;
  }

  public String dohvatiPP() {
    return this.pp;
  }

  public void spremiPO(String argPO) {
    po = argPO;
  }

  public String dohvatiPO() {
    return this.po;
  }

  public void spremiPM(String argPM) {
    pm = argPM;
  }

  public String dohvatiPM() {
    return this.pm;
  }

  public void spremiPU(String argPU) {
    pu = argPU;
  }

  public String dohvatiPU() {
    return this.pu;
  }

  public void spremiPMU(String argPMU) {
    pmu = argPMU;
  }

  public String dohvatiPMU() {
    return this.pmu;
  }

  public void spremiMT(String argMT) {
    mt = Integer.parseInt(argMT);
  }

  public int dohvatiMT() {
    return this.mt;
  }

  public void spremiVI(String argVI) {
    vi = Integer.parseInt(argVI);
  }

  public int dohvatiVI() {
    return this.vi;
  }

  public void spremiVS(String argVS) {
    SimpleDateFormat formatDatum = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    Date vsDatum;
    try {
      vsDatum = formatDatum.parse(argVS);
      vs = vsDatum;
    } catch (ParseException e) {
      GreskeSingleton.getInstance().dohvatiGreskuS(e.getMessage());
    }
  }

  public Date dohvatiVS() {
    return this.vs;
  }

  public void spremiMS(String argMS) {
    ms = Integer.parseInt(argMS);
  }

  public int dohvatiMS() {
    return this.ms;
  }

  public void spremiPR(String argPR) {
    SimpleDateFormat formatHhMm = new SimpleDateFormat("HH:mm");
    Date prVrijeme;
    try {
      prVrijeme = formatHhMm.parse(argPR);
      pr = prVrijeme;
    } catch (ParseException e) {
      GreskeSingleton.getInstance().dohvatiGreskuS(e.getMessage());
    }
  }

  public Date dohvatiPR() {
    return this.pr;
  }

  public LocalTime dohvatiLocalTimePR() {
    Instant instant = pr.toInstant();
    LocalTime localTimePR = instant.atZone(ZoneId.systemDefault()).toLocalTime();
    return localTimePR;
  }

  public void spremiKR(String argKR) {
    SimpleDateFormat formatHhMm = new SimpleDateFormat("HH:mm");
    Date krVrijeme;
    try {
      krVrijeme = formatHhMm.parse(argKR);
      kr = krVrijeme;
    } catch (ParseException e) {
      GreskeSingleton.getInstance().dohvatiGreskuS(e.getMessage());
    }
  }

  public Date dohvatiKR() {
    return this.kr;
  }

  public LocalTime dohvatiLocalTimeKR() {
    Instant instant = kr.toInstant();
    LocalTime localTimeKR = instant.atZone(ZoneId.systemDefault()).toLocalTime();
    return localTimeKR;
  }

  public void spremiIsporuka(String argIsporuka) {
    isporuka = Integer.parseInt(argIsporuka);
  }

  public int dohvatiIsporuka() {
    return this.isporuka;
  }

  public void spremiGps(String argGps) {
    gps = argGps;
  }

  public String dohvatiGps() {
    return this.gps;
  }

  public void dohvatiPodatke() {
    dohvatiPodrucja();
    dohvatiVozila();
    dohvatiVrste();
    dohvatiPakete();
    sortirajKronoloskiPremaVremenu();
    dohvatiOsobe();
    dohvatiUlice();
    dohvatiMjesta();
  }

  private void dohvatiVozila() {
    CsvProdukt datotekaVozila = new VozilaCsvKreator().kreirajCsvProdukt(pv);
    if (datotekaVozila == null) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka --pv se ne može učitati: " + pv);
    } else {
      datotekaVozila.ucitaj(pv);
    }
  }

  public void spremiVozilo(String registracija, String opis, Float kapacitetTezineUKg,
      Float kapacitetProstoraUM3, int redoslijed, Float prosjecnaBrzina, String podrucjaPoRangu,
      String status) {
    Vozilo novoVozilo = new Vozilo(registracija, opis, kapacitetTezineUKg, kapacitetProstoraUM3,
        redoslijed, prosjecnaBrzina, podrucjaPoRangu, status);
    listaVozila.add(novoVozilo);
  }

  public void ispisSvihVozila() {
    if (!listaVozila.isEmpty()) {
      System.out.println("Popis svih vozila:\n");
      for (Vozilo vozilo : listaVozila) {
        System.out.println("Registracija: " + vozilo.dohvatiRegistraciju());
        System.out.println("Opis: " + vozilo.dohvatiOpis());
        System.out.println("Kapacitet težine u kg: " + vozilo.dohvatiKapacitetTezineUKg());
        System.out.println("Kapacitet prostora u m3: " + vozilo.dohvatiKapacitetProstoraUM3());
        System.out.println("Redoslijed: " + vozilo.dohvatiRedoslijed());
        System.out.println("Prosječna brzina: " + vozilo.dohvatiProsjecnuBrzinu());
        System.out.println("Područja po rangu: " + vozilo.dohvatiPodrucjaPoRangu());
        System.out.println("Status: " + vozilo.dohvatiStatus());
        System.out.println("");
      }
    } else {
      System.out.println("Lista vozila je prazna.");
    }
  }

  private void dohvatiVrste() {
    CsvProdukt datotekaVrste = new VrsteCsvKreator().kreirajCsvProdukt(vp);
    if (datotekaVrste == null) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka --vp se ne može učitati: " + vp);
    } else {
      datotekaVrste.ucitaj(vp);
    }
  }

  public void spremiVrstu(String oznaka, String opis, Float visina, Float sirina, Float duzina,
      Float maksimalnaTezina, Float cijena, Float cijenaHitno, Float cijenaP, Float cijenaT) {
    Vrsta novaVrsta = new Vrsta(oznaka, opis, visina, sirina, duzina, maksimalnaTezina, cijena,
        cijenaHitno, cijenaP, cijenaT);
    listaVrste.add(novaVrsta);
  }

  public void ispisSvihVrsta() {
    if (!listaVrste.isEmpty()) {
      System.out.println("Popis svih vrsta:\n");
      for (Vrsta vrsta : listaVrste) {
        System.out.println("Oznaka: " + vrsta.dohvatiOznaku());
        System.out.println("Opis: " + vrsta.dohvatiOpis());
        System.out.println("Visina: " + vrsta.dohvatiVisinu());
        System.out.println("Sirina: " + vrsta.dohvatiSirinu());
        System.out.println("Duzina: " + vrsta.dohvatiDuzinu());
        System.out.println("Maksimalna tezina: " + vrsta.dohvatiMaksimalnuTezinu());
        System.out.println("Cijena: " + vrsta.dohvatiCijenu());
        System.out.println("Cijena hitno: " + vrsta.dohvatiCijenuHitno());
        System.out.println("CijenaP: " + vrsta.dohvatiCijenuP());
        System.out.println("CijenaT: " + vrsta.dohvatiCijenuT());
        System.out.println("");
      }
    } else {
      System.out.println("Lista vrsta je prazna.");
    }
  }

  private void dohvatiPakete() {
    CsvProdukt datotekaPaketi = new PaketiCsvKreator().kreirajCsvProdukt(pp);
    if (datotekaPaketi == null) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka --pp se ne može učitati: " + pp);
    } else {
      datotekaPaketi.ucitaj(pp);
    }
  }

  public void spremiPaket(String oznaka, Date vrijemePrijema, String posiljatelj, String primatelj,
      String vrstaPaketa, Float visina, Float sirina, Float duzina, Float tezina,
      String uslugaDostave, Float iznosPouzeca) {
    Paket noviPaket = new Paket(oznaka, vrijemePrijema, posiljatelj, primatelj, vrstaPaketa, visina,
        sirina, duzina, tezina, uslugaDostave, iznosPouzeca);
    listaPaketi.add(noviPaket);
  }

  public void ispisSvihPaketa() {
    if (!listaPaketi.isEmpty()) {
      System.out.println("Popis svih paketa:\n");
      for (Paket paket : listaPaketi) {
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

  private void sortirajKronoloskiPremaVremenu() {
    int brojPaketa = listaPaketi.size();
    for (int i = 1; i < brojPaketa; i++) {
      Paket trenutniPaket = listaPaketi.get(i);
      int j = i - 1;
      while (j >= 0 && trenutniPaket.dohvatiVrijemePijema()
          .compareTo(listaPaketi.get(j).dohvatiVrijemePijema()) < 0) {
        listaPaketi.set(j + 1, listaPaketi.get(j));
        j--;
      }
      listaPaketi.set(j + 1, trenutniPaket);
    }
  }

  private void dohvatiOsobe() {
    CsvProdukt datotekaOsobe = new OsobeCsvKreator().kreirajCsvProdukt(po);
    if (datotekaOsobe == null) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka --po se ne može učitati: " + po);
    } else {
      datotekaOsobe.ucitaj(po);
    }
  }

  public void spremiOsobu(OsobaDirektor direktor, String osobaIme, int grad, int ulica, int kbr) {
    Osoba novaOsoba = direktor.konstruirajOsobu(osobaIme, grad, ulica, kbr);
    listaOsobe.add(novaOsoba);
  }

  public void ispisSvihOsoba() {
    if (!listaOsobe.isEmpty()) {
      System.out.println("Popis svih osoba:\n");
      for (Osoba osoba : listaOsobe) {
        System.out.println("Osoba: " + osoba.dohvatiOsobu());
        System.out.println("Grad: " + osoba.dohvatiGrad());
        System.out.println("Ulica: " + osoba.dohvatiUlicu());
        System.out.println("Kbr: " + osoba.dohvatiKbr());
        System.out.println("");
      }
    } else {
      System.out.println("Lista osoba je prazna.");
    }
  }

  private void dohvatiMjesta() {
    CsvProdukt datotekaMjesta = new MjestaCsvKreator().kreirajCsvProdukt(pm);
    if (datotekaMjesta == null) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka --pm se ne može učitati: " + pm);
    } else {
      datotekaMjesta.ucitaj(pm);
    }
  }

  public void spremiMjesto(int id, String naziv, String ulica) {
    Mjesto novoMjesto = new Mjesto(id, naziv, ulica);
    listaMjesta.add(novoMjesto);
  }

  public void ispisSvihMjesta() {
    if (!listaMjesta.isEmpty()) {
      System.out.println("Popis svih mjesta:\n");
      for (Mjesto mjesto : listaMjesta) {
        System.out.println("Id: " + mjesto.dohvatiId());
        System.out.println("Naziv: " + mjesto.dohvatiNaziv());
        System.out.println("Ulica: " + mjesto.dohvatiUlicu());
        System.out.println("");
      }
    } else {
      System.out.println("Lista mjesta je prazna.");
    }
  }

  private void dohvatiPodrucja() {
    CsvProdukt datotekaPodrucja = new PodrucjaCsvKreator().kreirajCsvProdukt(pmu);
    if (datotekaPodrucja == null) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka --pmu se ne može učitati: " + pmu);
    } else {
      datotekaPodrucja.ucitaj(pmu);
    }
  }

  public void spremiPodrucje(int id, String gradUlica) {
    Podrucje novoPodrucje = new Podrucje(id, gradUlica);
    listaPodrucja.add(novoPodrucje);
  }

  public void ispisSvihPodrucja() {
    if (!listaPodrucja.isEmpty()) {
      System.out.println("Popis svih podrucja:\n");
      for (Podrucje podrucje : listaPodrucja) {
        System.out.println("Id: " + podrucje.dohvatiId());
        System.out.println("Grad:ulica: " + podrucje.dohvatiGradUlica());
        System.out.println("");
      }
    } else {
      System.out.println("Lista podrucja je prazna.");
    }
  }

  private void dohvatiUlice() {
    CsvProdukt datotekaUlice = new UliceCsvKreator().kreirajCsvProdukt(pu);
    if (datotekaUlice == null) {
      GreskeSingleton.getInstance().dohvatiGreskuS("Datoteka --pu se ne može učitati: " + pu);
    } else {
      datotekaUlice.ucitaj(pu);
    }
  }

  public void spremiUlicu(int id, String naziv, Double gpsLat1, Double gpsLon1, Double gpsLat2,
      Double gpsLon2, int najveciKucniBroj) {
    Ulica novaUlica = new Ulica(id, naziv, gpsLat1, gpsLon1, gpsLat2, gpsLon2, najveciKucniBroj);
    listaUlica.add(novaUlica);
  }

  public void ispisSvihUlica() {
    if (!listaUlica.isEmpty()) {
      System.out.println("Popis svih ulica:\n");
      for (Ulica ulica : listaUlica) {
        System.out.println("Id: " + ulica.dohvatiId());
        System.out.println("Naziv: " + ulica.dohvatiNaziv());
        System.out.println("Gps_Lat_1: " + ulica.dohvatiGpsLat1());
        System.out.println("Gps_Lon_1: " + ulica.dohvatiGpsLon1());
        System.out.println("Gps_Lat_2: " + ulica.dohvatiGpsLat2());
        System.out.println("Gps_Lon_2: " + ulica.dohvatiGpsLon2());
        System.out.println("Najveći kućni broj: " + ulica.dohvatiNajveciKucniBroj());
        System.out.println("");
      }
    } else {
      System.out.println("Lista ulica je prazna.");
    }
  }

  public OriginatorKomande dohvatiOriginator() {
    return originator;
  }

  public CaretakerKomande dohvatiCaretaker() {
    return caretaker;
  }
}
