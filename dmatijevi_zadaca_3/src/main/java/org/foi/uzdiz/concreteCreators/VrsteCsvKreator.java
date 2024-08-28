package org.foi.uzdiz.concreteCreators;

import org.foi.uzdiz.concreteProducts.VrsteCsvProdukt;
import org.foi.uzdiz.creators.CsvKreator;
import org.foi.uzdiz.products.CsvProdukt;

public class VrsteCsvKreator extends CsvKreator {

	@Override
	public CsvProdukt kreirajCsvProdukt(String datoteka) {
		CsvProdukt produkt = new VrsteCsvProdukt();
		return produkt;
	}

}
