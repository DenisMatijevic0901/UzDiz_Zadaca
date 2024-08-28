package org.foi.uzdiz.concreteCreators;

import org.foi.uzdiz.concreteProducts.UliceCsvProdukt;
import org.foi.uzdiz.creators.CsvKreator;
import org.foi.uzdiz.products.CsvProdukt;

public class UliceCsvKreator extends CsvKreator {

  @Override
  public CsvProdukt kreirajCsvProdukt(String datoteka) {
    CsvProdukt produkt = new UliceCsvProdukt();
    return produkt;
  }

}
