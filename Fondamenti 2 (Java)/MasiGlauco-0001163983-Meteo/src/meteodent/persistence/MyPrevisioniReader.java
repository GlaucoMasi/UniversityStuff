package meteodent.persistence;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import meteodent.model.Formatters;
import meteodent.model.Previsione;
import meteodent.model.ProbPioggia;
import meteodent.model.Temperatura;

public class MyPrevisioniReader implements PrevisioniReader {
	
	private static final int    TOKEN_COUNT = 5;
	private static final String SEPARATOR = ",";
	private static final String DEGREE_SYMBOL = "°";
	private static final String PERCENT_SYMBOL = "%";

	@Override
	public Map<String, SortedSet<Previsione>> leggiPrevisioni(Reader reader) throws IOException, BadFileFormatException {
		if(reader == null) throw new IllegalArgumentException("Reader fornito nullo");
		
		Previsione pr;
		BufferedReader br = new BufferedReader(reader);
		Map<String, SortedSet<Previsione>> ans = new HashMap<>();
		while((pr = leggiSingolaPrevisione(br)) != null) {
			if(!ans.containsKey(pr.getLocalita())) ans.put(pr.getLocalita(), 
					new TreeSet<Previsione>(Comparator.comparing(Previsione::getDataOra)));
			ans.get(pr.getLocalita()).add(pr);
		}
		
		return ans;
	}

	private Previsione leggiSingolaPrevisione(BufferedReader reader) throws IOException, BadFileFormatException {
		String s = reader.readLine();
		if(s == null || s.isBlank()) return null;
		
		String[] tokens = s.split(SEPARATOR);
		if(tokens.length != TOKEN_COUNT) throw new BadFileFormatException("Riga malformata: " + s);
		for(int i = 0; i < tokens.length; i++) tokens[i] = tokens[i].trim();
		
		if(tokens[0].isBlank()) throw new BadFileFormatException("Località mancante");
		
		LocalDateTime dateTime;
		try {
			dateTime = LocalDateTime.parse(tokens[1] + ", " + tokens[2], Formatters.datetimeFormatter);
		} catch(DateTimeParseException e) {
			throw new BadFileFormatException("Data e ora malformate: " + tokens[1] + ", " + tokens[2]);
		}
		
		if(!tokens[3].endsWith(DEGREE_SYMBOL)) throw new BadFileFormatException("Temperatura malformata: " + tokens[3]);

		int temperatura;
		try {
			temperatura = Integer.parseInt(tokens[3], 0, tokens[3].length()-1, 10);
		} catch(NumberFormatException e) {
			throw new BadFileFormatException("Temperatura malformata: " + tokens[3]);
		}
		
		if(!tokens[4].endsWith(PERCENT_SYMBOL)) throw new BadFileFormatException("Probabilità di pioggia malformata: " + tokens[4]);
		
		int pioggia;
		try {
			pioggia = Integer.parseInt(tokens[4], 0, tokens[4].length()-1, 10);
		} catch(NumberFormatException e) {
			throw new BadFileFormatException("Probabilità di pioggia malformata: " + tokens[4]);
		}

		try {
			return new Previsione(tokens[0].trim(), dateTime.toLocalDate(), dateTime.toLocalTime(), 
					Temperatura.of(temperatura), ProbPioggia.of(pioggia));
		} catch(IllegalArgumentException e) {
			throw new BadFileFormatException("Forniti argomenti illegali: " + s);
		}
	}

}
