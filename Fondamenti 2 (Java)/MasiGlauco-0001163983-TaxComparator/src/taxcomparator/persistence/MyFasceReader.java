package taxcomparator.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import taxcomparator.model.Fasce;
import taxcomparator.model.Fascia;

public class MyFasceReader implements FasceReader {
	private NumberFormat formatter = NumberFormat.getNumberInstance(Locale.ITALY);
	
	@Override
	public Fasce readFasce(String descr, Reader reader) throws BadFileFormatException, IOException {
		if(reader == null || descr == null || descr.isEmpty()) throw new BadFileFormatException("Forniti argomenti invalidi");
	
		BufferedReader br = new BufferedReader(reader);
		String s = br.readLine();
		
		double noTaxArea;
		if(!s.toLowerCase().startsWith("no-tax area:")) throw new BadFileFormatException("Prima riga malformata: " + s);
		
		try {
			noTaxArea = formatter.parse(s.substring("no-tax area:".length()).trim()).doubleValue();
			if(noTaxArea < 0) throw new BadFileFormatException("La no-tax area deve essere non negativa: " + s);
		} catch(ParseException e) {
			throw new BadFileFormatException("La no-tax area è malformata");
		}
		
		List<Fascia> fasce = new ArrayList<>();
		while((s = br.readLine()) != null) {
			if(s.isBlank()) continue;
			
			String[] tokens = s.split("-+|\\t+|\\s+");
			
			if(!tokens[1].endsWith(":")) throw new BadFileFormatException("Il range non termina con ':' " + s);
			tokens[1] = tokens[1].substring(0, tokens[1].length()-1);
			
			try {
				double min = formatter.parse(tokens[0]).doubleValue();
				double max = tokens[1].equalsIgnoreCase("oltre") ? Double.MAX_VALUE : formatter.parse(tokens[1]).doubleValue();
				fasce.add(new Fascia(min, max, tokens[2]));
			} catch(ParseException e) {
				throw new BadFileFormatException("I valori non rispettano la convenzione italiana: " + s);
			} catch(IllegalArgumentException e) {
				throw new BadFileFormatException(e);
			}
		}
		
		return new Fasce(descr, fasce, noTaxArea);
	}
	
}
