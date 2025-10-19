package zannotaxi.model;

import java.time.LocalTime;
import java.util.Optional;

public class Tassametro implements ITassametro {
	private FasciaOraria[] fasceOrarie;
	private ITariffaTaxi[] tariffe;
	
	public Tassametro(ITariffaTaxi[] tariffe, FasciaOraria[] fasceOrarie) {
		this.fasceOrarie = fasceOrarie;
		this.tariffe = tariffe;
	}
	
	public double calcolaCostoCorsa(CorsaTaxi corsaTaxi) {
		int tempoTrascorso = 0;
		double costoCorrente = 0, spazioPercorso = 0;
		double[] rilevazioni = corsaTaxi.getRilevazioniDistanze();
		
		for(int i = 1; i < rilevazioni.length; i++) {
			tempoTrascorso++;
			spazioPercorso += rilevazioni[i]-rilevazioni[i-1];
			Optional<Scatto> scatto = findScatto(tempoTrascorso, spazioPercorso, costoCorrente);

			if(scatto.isPresent()) {
				costoCorrente += scatto.get().getCosto();
				tempoTrascorso -= scatto.get().getTempo();
				spazioPercorso -= scatto.get().getSpazio();
			}
		}
		
		return costoCorrente + getScattoIniziale(corsaTaxi.getOraPartenza());
	}
	
	private Optional<Scatto> findScatto(int tempoTrascorsoDaUltimoScatto, double spazioPercorsoDaUltimoScatto, double costoCorrente){
		for(ITariffaTaxi tariffa : tariffe) {
			Optional<Scatto> curr = tariffa.getScattoCorrente(tempoTrascorsoDaUltimoScatto, spazioPercorsoDaUltimoScatto, costoCorrente);
			if(curr.isPresent()) return curr;
		}
		
		return Optional.empty();
	}
	
	private double getScattoIniziale(LocalTime t) {
		for(FasciaOraria fasciaOraria : fasceOrarie) {
			if(fasciaOraria.contiene(t)) {
				return fasciaOraria.getCostoScattoIniziale();
			}
		}
		
		throw new IllegalArgumentException("Nessuna fascia copre questo LocalTime");
	}
}
