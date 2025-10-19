package bancaore.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Optional;

import bancaore.model.Causale;
import bancaore.model.Cedolino;
import bancaore.model.SettimanaLavorativa;
import bancaore.model.VoceCedolino;


public class MyCedolinoReader implements CedolinoReader {

	/*  Dipendente:	Rossi Mario
		Mese di:	GENNAIO 2022
		Ore previste: 	 	6H/9H/6H/9H/6H/0H/0H
		Saldo precedente:	12H07M
		
		03 Lunedì	08:30	14:30
		04 Martedì	08:30	17:30
		05 Mercoledì	08:30	14:30	Riposo Compensativo
		07 Venerdì	08:30	14:30	Riposo Compensativo
		10 Lunedì	07:30	13:42	
		10 Lunedì	13:42	13:53	Pausa Pranzo
		10 Lunedì	13:53	15:45	
		11 Martedì	07:30	13:28		
		...
	 **/

	@Override
	public Cedolino leggiCedolino(Reader rdr) throws IOException {
		if (rdr==null) throw new IllegalArgumentException("reader is null");
		BufferedReader reader = new BufferedReader(rdr);
		
		// Lettura e validazione intestazione
		String nomeDipendente  		  = analizzaIntestazioneRiga1(reader.readLine()); // fornito
		LocalDate dataCedolino 		  = analizzaIntestazioneRiga2(reader.readLine()); // *** DA FARE *** 
		SettimanaLavorativa settimana = analizzaIntestazioneRiga3(reader.readLine()); // fornito
		Duration saldoPrecedente 	  = analizzaIntestazioneRiga4(reader.readLine()); // fornito
		
		// creazione del cedolino inizialmente vuoto
		Cedolino cedolino = new Cedolino(nomeDipendente,dataCedolino,settimana,saldoPrecedente);

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
		
		// Lettura ed elaborazione delle righe con le singole voci
		String s;
		while((s = reader.readLine()) != null) {
			if(s.isBlank()) continue;
			String[] tokens = s.split("\t+");
			
			if(tokens.length < 3 || tokens.length > 4) throw new BadFileFormatException("Riga malformata: " + s);
			
			String[] parts = tokens[0].split(" +");
			if(parts.length != 2 || parts[0].length() != 2) throw new BadFileFormatException("Prima parte della riga malformata: " + s);
			
			try {
				LocalDate date = cedolino.getData().withDayOfMonth(Integer.parseInt(parts[0]));
				if(!parts[1].toLowerCase().equals(CedolinoReader.getDayOfWeekName(date.getDayOfWeek(), Locale.ITALY)))
					throw new BadFileFormatException("Nome del giorno e numero non coincidenti: " + tokens[0]);
				
				LocalTime start = LocalTime.parse(tokens[1], timeFormatter), end = LocalTime.parse(tokens[2], timeFormatter);
				Optional<Causale> causale = Optional.empty();
				
				if(tokens.length == 4) {
					String strCausale = tokens[3].trim().toUpperCase().replace(' ', '_');
					causale = Optional.of(Causale.valueOf(strCausale));
				}
								
				cedolino.aggiungi(new VoceCedolino(date, start, end, causale));
			} catch(DateTimeParseException e) {
				throw new BadFileFormatException("Date o orari malformati: " + s);
			} catch(DateTimeException e) {
				throw new BadFileFormatException("Fornito numero di giorno illegale: " + tokens[0]);
			}catch(NumberFormatException e) {
				throw new BadFileFormatException("Numero del giorno malformato: " + tokens[0]);
			} catch(IllegalArgumentException e) {
				throw new BadFileFormatException("Forniti valori illegali: " + s);
			}
		}

		return cedolino;
	}

	private Duration analizzaIntestazioneRiga4(String line) throws BadFileFormatException {
		// Elaborazione quarta riga di intestazione
		// Saldo precedente:	12H07M
		String[] items = line.split(":");
		// validazione input quarta riga di intestazione
		if (items.length!=2)
			throw new BadFileFormatException("Numero elementi errato nella quarta riga di intestazione: " + line);
		if (!items[0].toLowerCase().equals("saldo precedente"))
			throw new BadFileFormatException("La riga di intestazione non inizia con 'Saldo precedente:'\nRiga: " + items[0]);
		String strSaldoPrec = items[1].trim().toUpperCase();
		return Duration.parse("PT"+strSaldoPrec);
	}

	private SettimanaLavorativa analizzaIntestazioneRiga3(String line) throws BadFileFormatException {
		// Elaborazione terza riga di intestazione
		// Ore previste: 9H/6H/9H/6H/9H/0H/0H
		String[] items = line.split(":");
		if (items.length!=2)
			throw new BadFileFormatException("Numero elementi errato nella terza riga di intestazione: " + line);
		if (!items[0].toLowerCase().equals("ore previste"))
			throw new BadFileFormatException("La riga di intestazione non inizia con 'Ore previste:'\nRiga: " + items[0]);
		String[] strSettLav = items[1].trim().toUpperCase().split("/+");
		
		try {
			return new SettimanaLavorativa(strSettLav);
		}
		catch(IllegalArgumentException | DateTimeParseException e) {
			throw new BadFileFormatException("La riga di intestazione non contiene una settimana lavorativa correttamente formattata\nRiga: " + items[1].trim());
		}
	}

	private LocalDate analizzaIntestazioneRiga2(String line) throws BadFileFormatException {
		// Elaborazione seconda riga di intestazione
		// Mese di:	GENNAIO 2022
		String[] items = line.split(":");
		if (items.length!=2)
			throw new BadFileFormatException("Numero elementi errato nella seconda riga di intestazione: " + line);
		if (!items[0].toLowerCase().equals("mese di"))
			throw new BadFileFormatException("La riga di intestazione non inizia con 'Mese di:'\nRiga: " + items[0]);
		
		try {			
			return LocalDate.parse("01 " + items[1].trim().toLowerCase(), 
					DateTimeFormatter.ofPattern("dd LLLL yyyy").withLocale(Locale.ITALY));
		}
		catch(DateTimeParseException e) {
			throw new BadFileFormatException("La seconda riga non contiene una data correttamente formattata\nRiga: " + items[1].trim());
		}
	}

	private String analizzaIntestazioneRiga1(String line) throws BadFileFormatException {
		// Elaborazione prima riga di intestazione
		// Dipendente:	Rossi Mario
		String[] items = line.split(":");
		if (items.length!=2)
			throw new BadFileFormatException("Numero elementi errato nella prima riga di intestazione: " + line);
		if (!items[0].toLowerCase().equals("dipendente"))
			throw new BadFileFormatException("La riga di intestazione non inizia con 'Dipendente:'\nRiga: " + line);
		return items[1].trim();
	}
	
}