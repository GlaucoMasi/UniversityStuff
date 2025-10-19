package formattazione;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;


public class FormattatoriDaTipi {
	public void formattatoriDaTipi() {
		Integer.parseInt("10");
		Integer.parseInt("10.4"); // NumberFormatException
		
		Double.parseDouble("43.32");
		
		LocalDate.parse("14:32", DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG).withLocale(Locale.ITALY));
		LocalDate.parse("4:32", DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG).withLocale(Locale.ITALY)); // DateTimeParseException
		LocalDate.parse("g2h32", DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG).withLocale(Locale.ITALY)); // DateTimeParseException
		
		// Analogo per LocalTime e LocalDateTime, con adeguati formattatori
		
		Duration.parse("PT"+"30M"); // DateTimeParseException
	}
}
