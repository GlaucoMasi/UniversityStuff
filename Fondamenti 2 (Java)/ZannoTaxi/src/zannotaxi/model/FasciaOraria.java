package zannotaxi.model;

import java.time.LocalTime;

public class FasciaOraria {
	private double costoScattoIniziale;
	private LocalTime inizio, fine;
	
	public FasciaOraria(LocalTime inizio, LocalTime fine, double costoScattoIniziale) {
		this.costoScattoIniziale = costoScattoIniziale;
		this.inizio = inizio;
		this.fine = fine;
	}
	
	public double getCostoScattoIniziale() {
		return costoScattoIniziale;
	}
	
	public boolean contiene(LocalTime t) {
		return !(t.isBefore(inizio) || t.isAfter(fine));
	}
}
