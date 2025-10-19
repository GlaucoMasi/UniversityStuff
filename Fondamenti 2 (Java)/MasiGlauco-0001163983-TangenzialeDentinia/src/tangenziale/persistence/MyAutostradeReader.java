package tangenziale.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import tangenziale.model.Autostrada;
import tangenziale.model.Tipologia;


public class MyAutostradeReader implements AutostradeReader {

	public Map<String,Autostrada> leggiAutostrade(Reader reader) throws IOException, BadFileFormatException {
		//
		// **** DA FARE ****
		//
		// non c’è alcun costruttore
		//
		// Il metodo leggiAutostrade legge le autostrade e le accumula via via in una mappa, che poi restituirà. 
		// Deve effettuare due fondamentali controlli di consistenza:
		//
		//  1.  Uno su ogni singola autostrada, verificando che in ogni autostrada letta non vi siano due caselli 
		//	    o allacciamenti alla stessa progressiva chilometrica
		//
		//  2.  Un altro al termine della lettura, verificando che *nella rete autostradale nel suo complesso* vi sia 
		//      sempre *una e una sola* tangenziale. 
		//
		
		Objects.requireNonNull(reader, "Fornito reader nullo");
		BufferedReader br = new BufferedReader(reader);
		
		String s;
		Map<String, Autostrada> rete = new TreeMap<>();
		
		while((s = br.readLine()) != null) {
			if(s.isBlank()) continue;
			leggiCaselli(br, rete, s);
		}
		
		if(rete.values().stream().filter(strada -> strada.tipologia() == Tipologia.TANGENZIALE).count() != 1)
			throw new BadFileFormatException("Non c'è esattamente una strada tangenziale");
		
		return rete;
	}

	private void leggiCaselli(BufferedReader br, Map<String, Autostrada> rete, String nome) throws IOException, BadFileFormatException {
		String s;
		SortedMap<Integer, String> profilo = new TreeMap<>();
		
		Tipologia tipologia = (nome.equals(Autostrada.TANGENZIALE) ? Tipologia.TANGENZIALE : Tipologia.AUTOSTRADA);
		
		while((s = br.readLine()) != null && !s.equals(AutostradeReader.END_TAG)) {
			if(s.isBlank()) continue;
			
			String[] tokens = s.split("\t+");
			if(tokens.length != 2) throw new BadFileFormatException("Numero di parti sbagliato: " + s);
			
			try {
				int chilometraggio = Integer.parseInt(tokens[0].trim());
				if(chilometraggio < 0) throw new BadFileFormatException("Il chilometraggio deve essere maggior o uguale a 0");
				if(profilo.containsKey(chilometraggio)) throw new BadFileFormatException("Ci sono più caselli allo stesso chilometraggio");
				profilo.put(chilometraggio, tokens[1].trim());
			} catch(NumberFormatException e) {
				throw new BadFileFormatException("Valore del chilometraggio malformato: " + tokens[0].trim());
			}
		}

		if(s == null) throw new BadFileFormatException("L'elenco dei caselli non è terminato con " + AutostradeReader.END_TAG);
		
		try{
			rete.put(nome, new Autostrada(tipologia, nome, profilo));
		} catch(IllegalArgumentException e) {
			throw new BadFileFormatException(e);
		}
	}
	
}
