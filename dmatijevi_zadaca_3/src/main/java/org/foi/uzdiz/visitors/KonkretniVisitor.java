package org.foi.uzdiz.visitors;

import java.text.DecimalFormat;
import org.foi.uzdiz.classes.Paket;
import org.foi.uzdiz.classes.Vozilo;

public class KonkretniVisitor implements VoziloVisitor {

  @Override
  public void visitVozilo(Vozilo voziloElement) {
    int brojHitnihPaketa = izracunajHitnePakete(voziloElement);
    int brojObicnihPaketa = izracunajObicnePakete(voziloElement);
    int brojIsporucenihPaketa = izracunajIsporucenePakete(voziloElement);
    Double zauzeceProstora = izracunajZauzeceProstora(voziloElement);
    Double zauzeceTezine = izracunajZauzeceTezine(voziloElement);

    System.out.printf("|%-15s|%-20s|%-15s|%-20s|%-25s|%-20s|%-20s|%-15s|\n",
        voziloElement.dohvatiRegistraciju(), voziloElement.dohvatiOpis(),
        voziloElement.dohvatiStatus(), " ", "H: " + brojHitnihPaketa + "    " + "O: "
            + brojObicnihPaketa + "    " + "I: " + brojIsporucenihPaketa,
        zauzeceProstora, zauzeceTezine, " ");
  }

  private int izracunajHitnePakete(Vozilo voziloElement) {
    int brojHitnihPaketa = 0;
    for (Paket paket : voziloElement.gepek) {
      if (paket.dohvatiUsluguDostave().equals("H")) {
        brojHitnihPaketa++;
      }
    }
    return brojHitnihPaketa;
  }

  private int izracunajObicnePakete(Vozilo voziloElement) {
    int brojObicnihPaketa = 0;
    for (Paket paket : voziloElement.gepek) {
      if (!paket.dohvatiUsluguDostave().equals("H")) {
        brojObicnihPaketa++;
      }
    }
    return brojObicnihPaketa;
  }

  private int izracunajIsporucenePakete(Vozilo voziloElement) {
    int brojIsporucenihPaketa = 0;
    for (Paket paket : voziloElement.gepek) {
      if (paket.isporucen == true) {
        brojIsporucenihPaketa++;
      }
    }
    return brojIsporucenihPaketa;
  }

  private Double izracunajZauzeceProstora(Vozilo voziloElement) {
    Double zauzeceProstoraPostotak = (double) ((voziloElement.dohvatiTrenutniKapacitetProstoraUM3()
        / voziloElement.dohvatiKapacitetProstoraUM3()) * 100);
    DecimalFormat format = new DecimalFormat("#.##");
    String formatiraniPostotak = format.format(zauzeceProstoraPostotak);
    return Double.parseDouble(formatiraniPostotak);
  }

  private Double izracunajZauzeceTezine(Vozilo voziloElement) {
    Double zauzeceTezinePostotak = (double) ((voziloElement.dohvatiTrenutniKapacitetTezineUKg()
        / voziloElement.dohvatiKapacitetTezineUKg()) * 100);
    DecimalFormat format = new DecimalFormat("#.##");
    String formatiraniPostotak = format.format(zauzeceTezinePostotak);
    return Double.parseDouble(formatiraniPostotak);
  }
}
