package cityparking.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class MyCalcolatoreSosta extends CalcolatoreSosta {

	public MyCalcolatoreSosta(Tariffa tariffa) {
		super(tariffa);
	}

	@Override
	public Ricevuta calcola(LocalDateTime inizio, LocalDateTime fine) throws BadTimeIntervalException {
		if(inizio == null) throw new IllegalArgumentException("Orario di inizio nullo");
		if(fine == null) throw new IllegalArgumentException("Orario di fine nullo");
		if(!fine.isAfter(inizio)) throw new BadTimeIntervalException("Durata non positiva");
	
		double minuti = Duration.between(inizio, fine).toMinutes();
		int intervalli = (int) Math.ceil(minuti/super.getTariffa().getDurataUnita().toMinutes());
		double costo = Math.min(
				super.getTariffa().getCapGiornaliero(),
				super.getTariffa().getCostoIniziale() + (intervalli-1)*super.getTariffa().getCostoUnitaSuccessive());

		if(minuti > 24*60) costo += Math.ceil((minuti-24*60)/(12*60))*super.getTariffa().getOltre();
		
		return new Ricevuta(inizio, fine, costo);
	}
}
