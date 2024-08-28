package org.foi.uzdiz.concreteCreators;

import org.foi.uzdiz.concreteProducts.PaketiCsvProdukt;
import org.foi.uzdiz.creators.CsvKreator;
import org.foi.uzdiz.products.CsvProdukt;

public class PaketiCsvKreator extends CsvKreator{

	@Override
	public CsvProdukt kreirajCsvProdukt(String datoteka) {
		CsvProdukt produkt = new PaketiCsvProdukt();
		return produkt;
	}

}
