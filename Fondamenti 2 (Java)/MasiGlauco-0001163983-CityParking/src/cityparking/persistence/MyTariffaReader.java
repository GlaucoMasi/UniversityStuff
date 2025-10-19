package cityparking.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import cityparking.model.Tariffa;


public class MyTariffaReader implements TariffaReader {
	private NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);

	private Duration leggiDurata(BufferedReader br, String descrizione) throws IOException, BadFileFormatException {
		String s;
		while((s = br.readLine()) != null) {
			if(s.isBlank()) continue;
			
			String[] tokens = s.split(" += +");
			if(tokens.length != 2) throw new BadFileFormatException("Riga malformata: " + s);
			
			if(!tokens[0].equals(descrizione)) 
				throw new BadFileFormatException("Descrizione riga malformata: " + tokens[0]);
			
			try {
				return Duration.parse("PT"+tokens[1].toUpperCase());
			} catch (DateTimeParseException e) {
				throw new BadFileFormatException("Durata malformata: " + tokens[1]); 
			}
		}
		
		throw new BadFileFormatException("Numero insufficiente di righe");		
	}
	
	private double leggiCosto(BufferedReader br, String descrizione) throws IOException, BadFileFormatException {
		String s;
		while((s = br.readLine()) != null) {
			if(s.isBlank()) continue;
			
			String[] tokens = s.split(" += +");
			if(tokens.length != 2) throw new BadFileFormatException("Riga malformata: " + s);
			
			if(!tokens[0].equals(descrizione)) 
				throw new BadFileFormatException("Descrizione riga malformata: " + tokens[0]);
			
			try {
				return formatter.parse(tokens[1]).doubleValue();
			} catch (ParseException e) {
				throw new BadFileFormatException("Costo malformato: " + tokens[1]); 
			}
		}
		
		throw new BadFileFormatException("Numero insufficiente di righe");
	}
	
	
	public Tariffa leggiTariffa(Reader reader) throws IOException, BadFileFormatException {
		if(reader == null) throw new IllegalArgumentException("Reader nullo");	
		
		Tariffa tariffa;
		BufferedReader br = new BufferedReader(reader);
		
		try {
			tariffa = new Tariffa(
					leggiDurata(br, "Durata unità"),
					leggiCosto(br, "Costo iniziale"),
					leggiCosto(br, "Unità successive"),
					leggiCosto(br, "Cap giornaliero"),
					leggiCosto(br, "12h successive")
					);
		} catch(IllegalArgumentException e) {
			throw new BadFileFormatException(e);
		}
		
		String end = br.readLine();
		if(end != null && !end.isBlank()) throw new BadFileFormatException("Numero eccessivo di righe");
	
		return tariffa;
	}
}
