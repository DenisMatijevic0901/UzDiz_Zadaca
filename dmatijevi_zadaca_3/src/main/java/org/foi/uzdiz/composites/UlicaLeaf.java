package org.foi.uzdiz.composites;

public class UlicaLeaf implements HijerarhijaComponent {
  private int id;
  private String naziv;

  public UlicaLeaf(int id, String naziv) {
    this.id = id;
    this.naziv = naziv;
  }

  @Override
  public void ispisiDetalje(int razina) {
    String uvlaka = " ".repeat(4 * razina);
    System.out.println(uvlaka + id + " " + naziv);
  }
}
