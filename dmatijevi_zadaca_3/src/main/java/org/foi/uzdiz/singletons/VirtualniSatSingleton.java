package org.foi.uzdiz.singletons;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class VirtualniSatSingleton {
	public static VirtualniSatSingleton virtualniSatSingleton;
	private LocalDateTime pravoVrijeme;
	public LocalDateTime virtualnoVrijeme;

	private VirtualniSatSingleton(){}

	public static VirtualniSatSingleton getInstance()
	{
		if (virtualniSatSingleton == null)
		{
			virtualniSatSingleton = new VirtualniSatSingleton();
		}
		return virtualniSatSingleton;
	}

	 public void postaviVS() {
		 Instant instant = TvrtkaSingleton.getInstance().dohvatiVS().toInstant();
		 pravoVrijeme = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
	 }
	 
	 public LocalDateTime dohvatiPravoVrijeme() {
		 return pravoVrijeme;
	 }
	 
	 public void postaviVirtualnoVrijeme(LocalDateTime vrijemeSustava) {
		 virtualnoVrijeme = vrijemeSustava;
	 }
	 
	 public void azurirajVirtualnoVrijeme(LocalDateTime novoVrijemeSustava) {
		 pravoVrijeme = novoVrijemeSustava;
	 }
	 
}
