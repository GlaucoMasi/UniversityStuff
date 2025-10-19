package ticketsosta;
import java.time.Duration;
import java.time.LocalTime;

public class Parcometro {
	private Tariffa tariffa;
	
	public Parcometro(Tariffa tariffa) {
		this.tariffa = tariffa;
	}

	private double calcolaCosto(double costoOrario, LocalTime da, LocalTime a) {
		return costoOrario*calcolaDurataSosta(da, a, tariffa.getMinutiFranchigia()).toSeconds()/3600.0;
	}
	
	private Duration calcolaDurataSosta(LocalTime inizio, LocalTime fine, int minutiFranchigia) {
		Duration ans = Duration.between(inizio, fine);
		if(ans.isNegative()) ans = ans.plusHours(24);
		ans = ans.minusMinutes(minutiFranchigia);
		if(ans.toMinutes() < tariffa.getDurataMinima()) ans = Duration.ofMinutes(tariffa.getDurataMinima());
		return ans;
	}
	
	public Ticket emettiTicket(LocalTime inizio, LocalTime fine) {
		return new Ticket(inizio, fine, calcolaCosto(tariffa.getTariffaOraria(), inizio, fine));
	}
	
	@Override
	public String toString() {
		return "Parcometro configurato con tariffa: " + tariffa.toString();
	}
}
