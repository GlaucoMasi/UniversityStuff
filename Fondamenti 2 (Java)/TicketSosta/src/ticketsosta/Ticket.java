package ticketsosta;
import java.util.Locale;
import java.time.Duration;
import java.time.LocalTime;
import java.text.NumberFormat;
import java.time.format.FormatStyle;
import java.time.format.DateTimeFormatter;

public class Ticket {
	private double costo;
	private LocalTime inizio, fine;

	public Ticket(LocalTime inizio, LocalTime fine, double costo) {
		this.inizio = inizio;
		this.fine = fine;
		this.costo = costo;
	}
	
	public double getCosto() {
		return this.costo;
	}
	
	public LocalTime getInizioSosta() {
		return this.inizio;
	}
	
	public LocalTime getFineSosta() {
		return this.fine;
	}
	
	public String getCostoAsString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
		return formatter.format(costo);
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
		
		return "Sosta autorizzata\ndalle " +
				inizio.format(formatter) + " alle " +
				fine.format(formatter) + "\n" +
				"Durata totale: " + toStringDuration(Duration.between(inizio, fine)) + "\n" +
				"Importo pagato: " + getCostoAsString() + "\n";
	}
	
	private String toStringDuration(Duration duration) {
		int minuti = duration.toMinutesPart();
		return duration.toHours() + ":" + (minuti < 10 ? '0' : '0') + minuti;
	}
	
}
