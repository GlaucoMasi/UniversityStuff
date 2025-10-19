package ticketsostaevoluto;
import ticketsosta.Tariffa;
import java.util.StringJoiner;
import java.time.*;


public class ParcometroEvoluto {
	Tariffa[] tariffe;
	
	public ParcometroEvoluto(Tariffa[] tariffe) {
		this.tariffe = new Tariffa[tariffe.length];
		for(int i = 0; i < tariffe.length; i++) this.tariffe[i] = tariffe[i];
	}
	
	private double calcolaCosto(double costoOrario, LocalTime da, LocalTime a) {
		Duration duration = Duration.between(da, a);
		if(duration.toMinutes() < 0) duration = duration.plusHours(24);
		return costoOrario*(duration.toSeconds()+1)/3600.0;
	}
	
	private double calcolaCostoSuPiuGiorni(LocalDateTime inizio, LocalDateTime fine) {
		double costoTotale = 0;
		boolean isFirst = true;

		while(inizio.isBefore(fine)) {
			DayOfWeek current = inizio.getDayOfWeek();
			int idx = (current.getValue()+6)%7;
			
			LocalTime da = inizio.toLocalTime();
			LocalTime a = (inizio.getDayOfYear() == fine.getDayOfYear() ? fine.toLocalTime().minusSeconds(1) : LocalTime.of(23, 59, 59));
			
			Duration ans;
			if(da.equals(LocalTime.of(0, 0, 0)) && da.equals(a)) ans = Duration.ofHours(24);
			else {
				ans = Duration.between(da, a);
				if(ans.toMinutes() < 0) ans = ans.plusHours(24);
			}
			
			if(isFirst) {
				ans = ans.minusMinutes(tariffe[idx].getMinutiFranchigia());
				if(ans.toMinutes() < 0) ans = Duration.ofMinutes(0);
				isFirst = false;
			}
			
			if(ans.toMinutes() < tariffe[idx].getDurataMinima()) ans = Duration.ofMinutes(tariffe[idx].getDurataMinima());
			
			costoTotale += calcolaCosto(tariffe[idx].getTariffaOraria(), LocalTime.of(0, 0), LocalTime.of(0, 0).plus(ans));
			
			inizio = inizio.plusDays(1).withHour(0).withMinute(0).withSecond(0);
		}
		
		return costoTotale;
	}
	
	public TicketEvoluto emettiTicket(LocalDateTime inizio, LocalDateTime fine) {
		return new TicketEvoluto(inizio, fine, calcolaCostoSuPiuGiorni(inizio, fine));
	}
	
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner("Parcometro configurato con le seguenti tariffe:\n");
		for(int i = 0; i < tariffe.length; i++) {
			sj.add(tariffe[i].toString() + "\n");
		}
		return sj.toString();
	}

}
