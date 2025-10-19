package pianodistudi.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import pianodistudi.model.AttivitaFormativa;
import pianodistudi.model.Ssd;

public class MyAttivitaFormativeReader implements AttivitaFormativeReader {

	@Override
	public Map<String, AttivitaFormativa> recuperaElenco(Reader rdr) throws IOException {
		if(rdr == null) throw new BadFileFormatException("Reader nullo");
		
		String s;
		BufferedReader br = new BufferedReader(rdr);
		
		Map<String, AttivitaFormativa> mappa = new HashMap<>();
		
		
		while((s = br.readLine()) != null) {
			if(s.isBlank()) continue;
			
			String[] tokens = s.split("\t+");
			if(tokens.length != 4) throw new BadFileFormatException("Numero sbagliato di elementi: " + s);
			if(!tokens[0].matches("\\d+")) throw new BadFileFormatException("Codice non numerico: "+ tokens[0]);

			try{
				mappa.put(tokens[1], new AttivitaFormativa(tokens[1], Ssd.of(tokens[2]), Integer.parseInt(tokens[3])));
			} catch(NumberFormatException e) {
				throw new BadFileFormatException("Numero di cfu malformato: " + tokens[3]);
			} catch(IllegalArgumentException e) {
				throw new BadFileFormatException(e.getMessage());
			}
		}
		
		return mappa;
	}

}
