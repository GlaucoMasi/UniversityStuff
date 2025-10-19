package cruciverba.controller;

public enum Modalita {
	AGEVOLATA(4), ESPERTA(3);

	private int lunghezzaParola;

	Modalita(int i) {
		this.lunghezzaParola = i;
	}

	public int numeroCaratteriDistinti() {
		return lunghezzaParola;
	}	
	
}
