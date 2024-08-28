package org.foi.uzdiz.singletons;

public class GreskeSingleton {
	public static GreskeSingleton greskeSingleton;
	private Integer brojacGresaka = 0;

	private GreskeSingleton(){}

	public static GreskeSingleton getInstance()
	{
		if (greskeSingleton == null)
		{
			greskeSingleton = new GreskeSingleton();
		}
		return greskeSingleton;
	}
	
	public void dohvatiGreskuS(String greska)
	{
		brojacGresaka++;
		System.out.println("\nBroj greške: " + brojacGresaka + "\nOpis greške: " + greska + "\n");
	}

}
