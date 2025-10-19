package australia.elections.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.SortedMap;
import java.util.TreeMap;

import australia.elections.model.Scheda;

public class MySchedeReader implements SchedeReader {

	public List<Scheda> leggiSchede(Reader reader) throws IOException, BadFileFormatException {
		Objects.requireNonNull(reader, "Reader nullo");
		
		String s;
		BufferedReader br = new BufferedReader(reader);
		List<Scheda> schede = new ArrayList<>();
		
		OptionalInt numeroCandidati = OptionalInt.empty();
		Optional<String[]> candidati = Optional.empty();
		
		while((s = br.readLine()) != null) {
			if(s.isBlank()) continue;
			
			String[] tokens = s.split(", ");
			for(int i = 0; i < tokens.length; i++) tokens[i] = tokens[i].trim();
			if(tokens.length%2 != 0) throw new BadFileFormatException("Riga malformata: " + s);
			
			for(int i = 0; i < tokens.length; i += 2) {
				if(tokens[i].chars().filter(c -> (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')).count() == 0)
					throw new BadFileFormatException("Nome solo numerico: " + tokens[i]);
			}
			
			if(numeroCandidati.isEmpty()) {
				numeroCandidati = OptionalInt.of(tokens.length/2);
				candidati = Optional.of(new String[numeroCandidati.getAsInt()]);
				for(int i = 0; i < tokens.length/2; i++) candidati.get()[i] = tokens[2*i];
			}else {
				if(numeroCandidati.getAsInt() != tokens.length/2) throw new BadFileFormatException("Numero sbagliato di candidati: " + tokens.length/2);
				for(int i = 0; i < tokens.length/2; i++) 
					if(!candidati.get()[i].equals(tokens[2*i])) throw new BadFileFormatException("Candidati di nome sbagliato: " + tokens[2*i]);
			}
			
			try {
				SortedMap<String, Integer> scheda = new TreeMap<>();
				
				for(int i = 1; i < tokens.length; i += 2) {
					Integer voto = Integer.parseInt(tokens[i]);
					if(voto < 1 || voto > numeroCandidati.getAsInt()) throw new BadFileFormatException("Numero di preferenza invalido " + voto);
					scheda.put(tokens[i-1], voto);
				}
				
				schede.add(new Scheda(scheda));
			} catch(NumberFormatException e) {
				throw new BadFileFormatException("Numero di preferenza malformato " + s);
			} catch(IllegalArgumentException e) {
				throw new BadFileFormatException(e);
			}
		}
		
		return schede;
	}


}
