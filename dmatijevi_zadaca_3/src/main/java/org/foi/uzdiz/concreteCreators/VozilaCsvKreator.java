package org.foi.uzdiz.concreteCreators;

import org.foi.uzdiz.concreteProducts.VozilaCsvProdukt;
import org.foi.uzdiz.creators.CsvKreator;
import org.foi.uzdiz.products.CsvProdukt;

public class VozilaCsvKreator extends CsvKreator{

	@Override
	public CsvProdukt kreirajCsvProdukt(String datoteka) {
		CsvProdukt produkt = new VozilaCsvProdukt();
		return produkt;
	}

}
