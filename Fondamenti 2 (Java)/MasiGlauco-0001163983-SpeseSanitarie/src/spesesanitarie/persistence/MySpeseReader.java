package spesesanitarie.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;

import spesesanitarie.model.Formatters;
import spesesanitarie.model.DocumentoDiSpesa;
import spesesanitarie.model.Tipologia;
import spesesanitarie.model.VoceDiSpesa;

	/*
		03/01/2022;Farmacia;€ 25,71
		FC;€ 4,98
		FC;€ 8,04
		FC;€ 4,41
		FC;€ 8,28
		18/01/2022;Farmacia;€ 16,65
		FC;€ 16,65
		24/01/2022;Farmacia;€ 3,00
		TK;€ 3,00
		25/01/2022;Dentista;€ 300,00
		LP;€ 300,00
		...
	*/

public class MySpeseReader implements SpeseReader {

	@Override
	public List<DocumentoDiSpesa> leggiSpese(Reader rdr) throws IOException {
		if(rdr == null) throw new IllegalArgumentException("Fornito reader nullo");
		BufferedReader br = new BufferedReader(rdr);
		
		String s;
		List<DocumentoDiSpesa> documenti = new ArrayList<>();
		
		while((s = br.readLine()) != null) {
			if(s.isBlank()) continue;
			
			String[] tokens = s.split(";");
			if(tokens.length != 4) throw new BadFileFormatException("Numero sbagliato di parti: " + s);
			
			try {
				LocalDate data = LocalDate.parse(tokens[0], Formatters.itDateFormatter);
				
				String emittente = tokens[1];
				if(emittente.isBlank()) throw new BadFileFormatException("Nome vuoto: " + tokens[1]);
				
				int items = Integer.parseInt(tokens[2]);
				if(items <= 0) throw new BadFileFormatException("Numero di elementi minore di 1: " + items);
				
				double importo = Formatters.itPriceFormatter.parse(tokens[3]).doubleValue();
				
				documenti.add(new DocumentoDiSpesa(data, emittente, importo, leggiVoci(items, br)));
			} catch(DateTimeParseException e) {
				throw new BadFileFormatException("Data malformata: " + tokens[0]);
			} catch(NumberFormatException e) {
				throw new BadFileFormatException("Numero di voci malformato: " + tokens[2]);
			} catch (ParseException e) {
				throw new BadFileFormatException("Importo malformato: " + tokens[3]);
			} catch(IllegalArgumentException e) {
				throw new BadFileFormatException("Parametri del Documento di Spesa illegali");
			}
		}
		
		return documenti;
	}


	private List<VoceDiSpesa> leggiVoci(int nItems, BufferedReader br) throws IOException {
		String s;
		List<VoceDiSpesa> spese = new ArrayList<>();
		
		while(nItems > 0) {
			s = br.readLine();
			if(s == null) throw new BadFileFormatException("Mancano " + nItems + " righe");
			if(s.isBlank()) continue;
			
			nItems--;
			
			String[] tokens = s.split(";");
			if(tokens.length != 2) throw new BadFileFormatException("Numero sbagliato di parti: " + s);
			
			try {
				Tipologia tipologia = Tipologia.valueOf(tokens[0]);
				double importo = Formatters.itPriceFormatter.parse(tokens[1]).doubleValue();
				spese.add(new VoceDiSpesa(tipologia, importo));
			} catch(ParseException e) {
				throw new BadFileFormatException("Importo malformato: " + tokens[1]);
			} catch(IllegalArgumentException e) {
				throw new BadFileFormatException("Parametri della Voe di Spesa illegali o tipologia inesistente");
			}
		}
		
		return spese;
	}

}