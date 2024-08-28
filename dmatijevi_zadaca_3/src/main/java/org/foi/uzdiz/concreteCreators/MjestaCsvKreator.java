package org.foi.uzdiz.concreteCreators;

import org.foi.uzdiz.concreteProducts.MjestaCsvProdukt;
import org.foi.uzdiz.creators.CsvKreator;
import org.foi.uzdiz.products.CsvProdukt;

public class MjestaCsvKreator extends CsvKreator {

  @Override
  public CsvProdukt kreirajCsvProdukt(String datoteka) {
    CsvProdukt produkt = new MjestaCsvProdukt();
    return produkt;
  }
}
