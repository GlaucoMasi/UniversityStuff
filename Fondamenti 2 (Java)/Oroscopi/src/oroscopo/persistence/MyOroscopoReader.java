package oroscopo.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import oroscopo.model.ArchivioPrevisioni;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class MyOroscopoReader implements OroscopoReader {

	/*
	AMORE
	avrai la testa un po' altrove				4	ARIETE,TORO,GEMELLI
	divertimento nello stare insieme			8
	chi e' single avra' nuove occasioni			6
	servira' un po' di pazienza					4
	lasciatevi andare alle vostre sensazioni	6	PESCI
	grande intimita'							9
	dedicate piu' tempo al partner				6
	FINE
	...
	 * */

	@Override
	public ArchivioPrevisioni leggiPrevisioni(Reader inputReader) throws IOException, BadFileFormatException {
		Objects.requireNonNull(inputReader, "Il reader fornito non può essere null");

		Map<String, List<Previsione>> mappaPrevisioni = new HashMap<>();

		BufferedReader reader = new BufferedReader(inputReader);
		String sezione = null;
		while ((sezione = reader.readLine()) != null) {
			if (sezione.contains(" ") || sezione.contains("\t")) {
				throw new BadFileFormatException("linea sezione errata");
			}
			mappaPrevisioni.put(sezione.trim().toUpperCase(), leggiPrevisioniSezione(reader));
		}
		return new ArchivioPrevisioni(mappaPrevisioni);
	}


	private List<Previsione> leggiPrevisioniSezione(BufferedReader reader) throws IOException, BadFileFormatException {
		List<Previsione> previsioni = new ArrayList<>();
		try {
			String riga = null;
			while (!(riga = reader.readLine().trim()).equalsIgnoreCase("FINE")) {
				if (riga.isEmpty()) {
					continue;
				}
				String[] tokens = riga.split("\\t+");
				if(tokens.length<2 || tokens.length>3 ) {
					throw new BadFileFormatException("Errato numero di elementi in riga: " + riga);
				}

				String frase = tokens[0].trim();
				String valoreAsString = tokens[1].trim();
				int valore;
				try {
					valore = Integer.parseInt(valoreAsString );
				}
				catch(NumberFormatException e) {
					throw new BadFileFormatException("Valore numerico non valido in riga: " + riga);
				}

				Previsione previsione = null;

				if (tokens.length==3) {
					String allowedSignsToken = tokens[2].trim();
					try {
						String[] signs = allowedSignsToken.split(",");
						Set<SegnoZodiacale> allowedSigns = new HashSet<>();
						for (String sign : signs) {
							allowedSigns.add(SegnoZodiacale.valueOf(sign.trim().toUpperCase()));
						}
						previsione = new Previsione(frase, valore, allowedSigns);
					} catch (Exception e) {
						throw new BadFileFormatException("lista segni contiene elementi errati: " + riga);
					}
				} else {
					previsione = new Previsione(frase, valore);
				}

				previsioni.add(previsione);
			}

		} catch (Exception e) {
			throw new BadFileFormatException(e);
		}

		if (previsioni.size() == 0) {
			throw new BadFileFormatException("Nessuna previsione");
		}

		return previsioni;
	}

}
