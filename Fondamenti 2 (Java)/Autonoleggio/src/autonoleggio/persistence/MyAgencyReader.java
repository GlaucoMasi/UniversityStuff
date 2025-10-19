package autonoleggio.persistence;

import java.util.Set;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.IOException;
import java.time.LocalTime;
import java.io.BufferedReader;

import autonoleggio.model.Slot;
import autonoleggio.model.Agency;
import autonoleggio.model.Formatters;
import autonoleggio.model.OpeningTime;

public class MyAgencyReader implements AgencyReader {
	private String nome, citta;
	private List<Agency> agenzie;
	private LocalTime inizio, fine;
	private OpeningTime settimanale, sabato, domenica;

	private Slot makeSlot(String fasciaOraria) throws BadFileFormatException {
		if(fasciaOraria == null || fasciaOraria.isBlank()) throw new BadFileFormatException("La fascia oraria è invalida");
		String[] orari = fasciaOraria.split("-");
		if(orari.length != 2) throw new BadFileFormatException("La fascia oraria non è composta da due orari");
		
		try {
			inizio = LocalTime.parse(orari[0], Formatters.timeFormatter);
			fine = LocalTime.parse(orari[1], Formatters.timeFormatter);
			return new Slot(inizio, fine);
		}catch(Exception e) {
			throw new BadFileFormatException("Fallita parse degli orari " + fasciaOraria);
		}
	}
	
	private OpeningTime extractOpeningTime(String item, String prefix) throws BadFileFormatException {
		String orario = item.substring(prefix.length()+1);
		if(orario.equals("chiuso")) return OpeningTime.CHIUSO;
				
		String[] orari = orario.split("/");
		if(orari.length == 0 || orari.length > 2) throw new BadFileFormatException("Formato dell'orario invalido");
		
		try {
			if(orari.length == 1) return new OpeningTime(makeSlot(orari[0]));
			else return new OpeningTime(makeSlot(orari[0]), makeSlot(orari[1]));
		}catch(Exception e) {
			throw new BadFileFormatException("Fallita creazione dell'OpeningTime");
		}
	}
	
	@Override
	public Set<Agency> readAllAgencies(Reader reader) throws IOException, BadFileFormatException {
		Objects.requireNonNull(reader, "Il reader non può essere nullo");
		agenzie = new ArrayList<Agency>();
		
		BufferedReader br = new BufferedReader(reader);
		String s;
		while((s = br.readLine()) != null) {
			String[] tokens = s.split(",");
			if(tokens.length != 5) throw new BadFileFormatException("Campi mancanti");
			
			citta = tokens[0].trim();
			nome = tokens[1].trim();

			tokens[2] = tokens[2].trim();
			int idxSpace = tokens[2].indexOf(" ");
			if(idxSpace == -1) throw new BadFileFormatException("Orario settimanale malformato");
			String prefix = tokens[2].substring(0, idxSpace);
			if(!prefix.equals("lun-ven")) throw new BadFileFormatException("Sbagliata la scrittura lun-ven");
			settimanale = extractOpeningTime(tokens[2], prefix);
			
			tokens[3] = tokens[3].trim();
			idxSpace = tokens[3].indexOf(" ");
			if(idxSpace == -1) throw new BadFileFormatException("Orario del sabato malformato");
			prefix = tokens[3].substring(0, idxSpace);
			if(!prefix.equals("sab")) throw new BadFileFormatException("Sbagliata la scrittura sab");
			sabato = extractOpeningTime(tokens[3], prefix);
			
			tokens[4] = tokens[4].trim();
			idxSpace = tokens[4].indexOf(" ");
			if(idxSpace == -1) throw new BadFileFormatException("Orario della domenica malformato");
			prefix = tokens[4].substring(0, idxSpace);
			if(!prefix.equals("dom")) throw new BadFileFormatException("Sbagliata la scrittura dom");
			domenica = extractOpeningTime(tokens[4], prefix);
			
			agenzie.add(new Agency(citta, nome, settimanale, sabato, domenica));
		}
		
		return new HashSet<Agency>(agenzie);
	}

}
