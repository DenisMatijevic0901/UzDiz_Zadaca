package org.foi.uzdiz.classes;

public class Podrucje {
  private int id;
  private String gradUlica;

  public Podrucje(int id, String gradUlica) {
    this.id = id;
    this.gradUlica = gradUlica;
  }

  public int dohvatiId() {
    return id;
  }

  public String dohvatiGradUlica() {
    return gradUlica;
  }
}
