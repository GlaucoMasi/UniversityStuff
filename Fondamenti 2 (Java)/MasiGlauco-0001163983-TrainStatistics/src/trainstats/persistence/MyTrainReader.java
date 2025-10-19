package trainstats.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import trainstats.model.Recording;
import trainstats.model.Train;


public class MyTrainReader implements TrainReader {
	private static final int TOKENS_COUNT = 5;
	private static final String REGEX = ";+";
	private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
	
	@Override
	public Train readTrain(Reader rdr) throws IOException {
		if(rdr == null) throw new IllegalArgumentException("Il reader fornito è nullo");
		
		String s;
		List<Recording> recordings = new ArrayList<>();
		BufferedReader br = new BufferedReader(rdr);
		while((s = br.readLine()) != null && !s.isBlank()) {
			String[] tokens = s.split(REGEX);
			if(tokens.length != TOKENS_COUNT) throw new BadFileFormatException("Riga malformata: " + s);
			
			String station = tokens[0].trim();
			
			List<Optional<LocalTime>> times = new ArrayList<>();
			for(int i = 1; i < 5; i++) {
				if(tokens[i].trim().equals("--")) times.add(Optional.empty());
				else {
					try {
						times.add(Optional.of(LocalTime.parse(tokens[i].trim(), formatter)));
					} catch(DateTimeParseException e) {
						throw new BadFileFormatException("Orario di arrivo malformato: " + tokens[1].trim());
					}
				}	
			}
			try {				
				recordings.add(new Recording(station, times.get(0), times.get(1), times.get(2), times.get(3)));
			} catch(IllegalArgumentException e) {
				throw new BadFileFormatException("Forniti argomenti illegali: " + s);
			}
		}
		
		try {
			return new Train(recordings);
		} catch(IllegalArgumentException e) {
			throw new BadFileFormatException("La lista di rilevazioni fornita non è valida");
		}
	}

}