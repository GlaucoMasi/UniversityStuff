package formattazione;

import java.time.*;
import java.util.Locale;
import java.time.format.*;

public class FormattatoriDateOrari {
	public void formattazioneLocalDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.ITALY);
		LocalDate.now().format(formatter);
		
		/*
		 * SHORT: 17/05/25
		 * MEDIUM: 17 mag 2025
		 * LONG: 17 maggio 2025
		 * FULL: sabato 17 maggio 2025
		 */
		
		DateTimeFormatter formatterOfPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate.now().format(formatterOfPattern); // 17/05/2025
		
		LocalDate.parse("12 febbraio 2023", formatter);
	}
	
	public void formattazioneLocalTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
		LocalDate.now().format(formatter);
		
		/*
		 * SHORT: 12:15
		 * MEDIUM: 12:15:50
		 */
		
		LocalTime.parse("12:30", formatter);
	}
	
	public void formattazioneLocalDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT).withLocale(Locale.ITALY);
		LocalDateTime.now().format(formatter);
		
		LocalDateTime.parse("12 febbraio 2023, 12:30", formatter);
	}
	
	public void formattazioneLocalizedDateTime() {
		DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.ITALY);
		DateTimeFormatter formatter2 = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
		DateTimeFormatter formatter3 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT).withLocale(Locale.ITALY);
				
		ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("CET")).format(formatter1);
		ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("CET")).format(formatter2);
		ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("CET")).format(formatter3);
		OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.ofHours(2)).format(formatter1);
		OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.ofHours(2)).format(formatter2);
		OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.ofHours(2)).format(formatter3);
	}
}
