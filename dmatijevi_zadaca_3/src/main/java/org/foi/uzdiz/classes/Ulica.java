package org.foi.uzdiz.classes;

public class Ulica {
  private int id;
  private String naziv;
  private Double gpsLat1;
  private Double gpsLon1;
  private Double gpsLat2;
  private Double gpsLon2;
  private int najveciKucniBroj;

  public Ulica(int id, String naziv, Double gpsLat1, Double gpsLon1, Double gpsLat2, Double gpsLon2,
      int najveciKucniBroj) {
    this.id = id;
    this.naziv = naziv;
    this.gpsLat1 = gpsLat1;
    this.gpsLon1 = gpsLon1;
    this.gpsLat2 = gpsLat2;
    this.gpsLon2 = gpsLon2;
    this.najveciKucniBroj = najveciKucniBroj;
  }

  public int dohvatiId() {
    return id;
  }

  public String dohvatiNaziv() {
    return naziv;
  }

  public Double dohvatiGpsLat1() {
    return gpsLat1;
  }

  public Double dohvatiGpsLon1() {
    return gpsLon1;
  }

  public Double dohvatiGpsLat2() {
    return gpsLat2;
  }

  public Double dohvatiGpsLon2() {
    return gpsLon2;
  }

  public int dohvatiNajveciKucniBroj() {
    return najveciKucniBroj;
  }

}
