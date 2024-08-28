package org.foi.uzdiz.classes;

public class Mjesto {
  private int id;
  private String naziv;
  private String ulica;

  public Mjesto(int id, String naziv, String ulica) {
    this.id = id;
    this.naziv = naziv;
    this.ulica = ulica;
  }

  public int dohvatiId() {
    return id;
  }

  public String dohvatiNaziv() {
    return naziv;
  }

  public String dohvatiUlicu() {
    return ulica;
  }
}
