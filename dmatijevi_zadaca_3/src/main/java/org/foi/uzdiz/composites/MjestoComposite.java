package org.foi.uzdiz.composites;

import java.util.ArrayList;
import java.util.List;

public class MjestoComposite implements HijerarhijaComponent {
  private int id;
  private String naziv;
  private List<HijerarhijaComponent> komponente = new ArrayList<>();

  public MjestoComposite(int id, String naziv) {
    this.id = id;
    this.naziv = naziv;
  }

  public void dodajKomponentu(HijerarhijaComponent komponenta) {
    komponente.add(komponenta);
  }

  @Override
  public void ispisiDetalje(int razina) {
    String uvlaka = " ".repeat(4 * razina);
    System.out.println(uvlaka + id + " " + naziv);
    for (HijerarhijaComponent komponenta : komponente) {
      komponenta.ispisiDetalje(razina + 1);
    }
  }
}

