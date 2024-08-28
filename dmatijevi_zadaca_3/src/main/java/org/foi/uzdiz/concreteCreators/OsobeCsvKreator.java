package org.foi.uzdiz.concreteCreators;

import org.foi.uzdiz.concreteProducts.OsobeCsvProdukt;
import org.foi.uzdiz.creators.CsvKreator;
import org.foi.uzdiz.products.CsvProdukt;

public class OsobeCsvKreator extends CsvKreator {

  @Override
  public CsvProdukt kreirajCsvProdukt(String datoteka) {
    CsvProdukt produkt = new OsobeCsvProdukt();
    return produkt;
  }
}
