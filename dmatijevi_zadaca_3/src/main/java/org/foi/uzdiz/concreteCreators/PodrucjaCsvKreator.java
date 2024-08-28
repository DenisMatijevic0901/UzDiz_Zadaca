package org.foi.uzdiz.concreteCreators;

import org.foi.uzdiz.concreteProducts.PodrucjaCsvProdukt;
import org.foi.uzdiz.creators.CsvKreator;
import org.foi.uzdiz.products.CsvProdukt;

public class PodrucjaCsvKreator extends CsvKreator {

  @Override
  public CsvProdukt kreirajCsvProdukt(String datoteka) {
    CsvProdukt produkt = new PodrucjaCsvProdukt();
    return produkt;
  }

}
