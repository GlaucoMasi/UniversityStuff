package speedcollege.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

import speedcollege.model.AttivitaFormativa;
import speedcollege.model.Carriera;
import speedcollege.model.Esame;
import speedcollege.model.Voto;


public class MyCarrieraReader implements CarrieraReader {

	/*
	 * 	27991	ANALISI MATEMATICA T-1 (cfu:9)			12/01/20	RT
		27991	ANALISI MATEMATICA T-1 (cfu:9)			10/02/20	22
		28004	FONDAMENTI DI INFORMATICA T-1 (cfu:12)	13/02/20	24
		29228	GEOMETRIA E ALGEBRA T (cfu:6)			18/01/20	26
		26337	LINGUA INGLESE B-2 (cfu:6)
		27993	ANALISI MATEMATICA T-2 (cfu:6)			10/06/20	RE
		27993	ANALISI MATEMATICA T-2 (cfu:6)			02/07/20	RT
		28006	FONDAMENTI DI INFORMATICA T-2 (cfu:12)
		28011	RETI LOGICHE T (cfu:6)		
		...
	*/

	private void leggiEsame(String s, Carriera carriera) throws BadFileFormatException {
		if(s.isBlank()) return;
		String[] tokens = s.split("\t+");
		
		if(tokens.length != 2 && tokens.length != 4) 
			throw new BadFileFormatException("Riga malformata, numero invalido di elementi: " + s);
		
		Long codice;
		try {
			codice = Long.parseLong(tokens[0].trim());
		} catch(NumberFormatException e) {
			throw new BadFileFormatException("Codice dell'attività formativa malformato: " + tokens[0]);
		}
		
		tokens[1] = tokens[1].trim();
		String[] twoParts = tokens[1].split("\\(");
		if(twoParts.length != 2 
				|| !twoParts[1].endsWith(")")
				|| !twoParts[1].startsWith("cfu:")) 
			throw new BadFileFormatException("Crediti non presenti nel formato corretto: " + tokens[1]);
		
		try {
			int cfu = Integer.parseInt(twoParts[1].substring("cfu:".length(), twoParts[1].length()-1));

			AttivitaFormativa af = new AttivitaFormativa(codice, twoParts[0].trim(), cfu);
			if(tokens.length == 2) return;

			LocalDate data = LocalDate.parse(tokens[2].trim(), DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY));
			Voto voto = Voto.of(tokens[3].trim());
			
			carriera.inserisci(new Esame(af, data, voto));
		} catch(NumberFormatException e) {
			throw new BadFileFormatException("Numero di cfu malformato: " + twoParts[1]);
		} catch(DateTimeParseException e) {
			throw new BadFileFormatException("Data dell'esame malformata: " + tokens[2].trim());
		} catch(IllegalArgumentException e) {
			throw new BadFileFormatException(e);
		}
	}
	
	@Override
	public Carriera leggiCarriera(Reader rdr) throws IOException, BadFileFormatException {
		if(rdr == null) throw new IllegalArgumentException("Fornito un reader nullo");
		BufferedReader br = new BufferedReader(rdr);
		
		String s;
		Carriera carriera = new Carriera();
		while((s = br.readLine()) != null) leggiEsame(s, carriera);
		
		return carriera;
	}

}