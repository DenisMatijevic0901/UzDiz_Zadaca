package org.foi.uzdiz.classes;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Paket {
  private String oznaka;
  private Date vrijemePrijema;
  private String posiljatelj;
  private String primatelj;
  private String vrstaPaketa;
  private Float visina;
  private Float sirina;
  private Float duzina;
  private Float tezina;
  private String uslugaDostave;
  private Float iznosPouzeca;
  public boolean isporucen;
  public boolean postojiPrimatelj;
  public int brojacGreskiPrimatelj;

  public Paket(String oznaka, Date vrijemePrijema, String posiljatelj, String primatelj,
      String vrstaPaketa, Float visina, Float sirina, Float duzina, Float tezina,
      String uslugaDostave, Float iznosPouzeca) {
    this.oznaka = oznaka;
    this.vrijemePrijema = vrijemePrijema;
    this.posiljatelj = posiljatelj;
    this.primatelj = primatelj;
    this.vrstaPaketa = vrstaPaketa;
    this.visina = visina;
    this.sirina = sirina;
    this.duzina = duzina;
    this.tezina = tezina;
    this.uslugaDostave = uslugaDostave;
    this.iznosPouzeca = iznosPouzeca;
    this.isporucen = false;
    postojiPrimatelj = false;
    brojacGreskiPrimatelj = 0;
  }

  public String dohvatiOznaku() {
    return oznaka;
  }

  public Date dohvatiVrijemePijema() {
    return vrijemePrijema;
  }

  public LocalDateTime dohvatiVrijemePrijemaLocalDateTime() {
    Instant instant = vrijemePrijema.toInstant();
    LocalDateTime localDateTimeVrijemePrijema =
        instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    return localDateTimeVrijemePrijema;
  }

  public String dohvatiPosiljatelja() {
    return posiljatelj;
  }

  public String dohvatiPrimatelja() {
    return primatelj;
  }

  public String dohvatiVrstuPaketa() {
    return vrstaPaketa;
  }

  public Float dohvatiVisinu() {
    return visina;
  }

  public Float dohvatiSirinu() {
    return sirina;
  }

  public Float dohvatiDuzinu() {
    return duzina;
  }

  public Float dohvatiTezinu() {
    return tezina;
  }

  public String dohvatiUsluguDostave() {
    return uslugaDostave;
  }

  public Float dohvatiIznosPouzeca() {
    return iznosPouzeca;
  }

  public Paket(Paket original) {
    this.oznaka = original.oznaka;
    this.vrijemePrijema = original.vrijemePrijema;
    this.posiljatelj = original.posiljatelj;
    this.primatelj = original.primatelj;
    this.vrstaPaketa = original.vrstaPaketa;
    this.visina = original.visina;
    this.sirina = original.sirina;
    this.duzina = original.duzina;
    this.tezina = original.tezina;
    this.uslugaDostave = original.uslugaDostave;
    this.iznosPouzeca = original.iznosPouzeca;
    this.isporucen = original.isporucen;
    this.postojiPrimatelj = original.postojiPrimatelj;
    this.brojacGreskiPrimatelj = original.brojacGreskiPrimatelj;
  }

  public Paket kloniraj() {
    return new Paket(this);
  }
}
