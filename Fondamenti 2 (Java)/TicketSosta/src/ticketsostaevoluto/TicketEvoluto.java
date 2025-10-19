package ticketsostaevoluto;
import java.util.Locale;
import java.time.Duration;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TicketEvoluto {
	private double costo;
	private LocalDateTime inizio, fine;
	
	public TicketEvoluto(LocalDateTime inizio, LocalDateTime fine, double costo) {
		this.inizio = inizio;
		this.fine = fine;
		this.costo = costo;
	}
	
	public double getCosto() {
		return this.costo;
	}
	
	public String getCostoAsString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
		return formatter.format(costo);
	}
	
	public LocalDateTime getInizioSosta() {
		return this.inizio;
	}
	
	public LocalDateTime getFineSosta() {
		return this.inizio;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm:ss", Locale.ITALY);
				
		return "Sosta autorizzata da " +
				inizio.format(formatter) + " a " +
				fine.format(formatter) + "\n" +
				"Durata totale : " + toStringDuration(Duration.between(inizio, fine)) + "\n" +
				"Importo pagato: " + getCostoAsString() + "\n";
				
	}
	
	private String toStringDuration(Duration duration) {
		int giorni = (int) duration.toDaysPart(), ore = duration.toHoursPart(), minuti = duration.toMinutesPart();
		return giorni + (giorni == 1 ? " giorno, " : " giorni, ") + 
			   ore + (ore == 1 ? " ora, " : " ore, ") + 
			   minuti + (minuti == 1 ? " minuto, " : " minuti");
	}

}
