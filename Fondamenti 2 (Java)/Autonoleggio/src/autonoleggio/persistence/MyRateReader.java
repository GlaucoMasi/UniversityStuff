package autonoleggio.persistence;


import java.io.Reader;
import java.util.TreeMap;
import java.util.Objects;
import java.util.SortedMap;
import java.io.BufferedReader;
import java.io.IOException;

import autonoleggio.model.CarType;
import autonoleggio.model.Formatters;
import autonoleggio.model.Rate;

public class MyRateReader implements RateReader {
	private double extractRate(String item, String prefix) throws BadFileFormatException {
		String costo = item.substring(prefix.length()+1);
		
		try {
			return Formatters.euroFormatter.parse(costo).doubleValue();
		}catch(Exception e) {
			throw new BadFileFormatException("Fallito parsing della rata");
		}
	}
	
	public SortedMap<CarType,Rate> readAllRates(Reader reader) throws IOException, BadFileFormatException {
		Objects.requireNonNull(reader, "Il reader non può essere nullo");
		SortedMap<CarType, Rate> ans = new TreeMap<CarType, Rate>();
		
		String s;
		BufferedReader br = new BufferedReader(reader);
		while((s = br.readLine()) != null && !s.isBlank()) {
			if(s.length() < 4) throw new BadFileFormatException("Rata malformata");

			char type = s.charAt(0);
			if(type - 'A' < 0 || type - 'A' > 3) throw new BadFileFormatException("Tipo macchina malformato");
			CarType c = CarType.values()[type-'A'];
			if(ans.keySet().contains(c)) throw new BadFileFormatException("Tipo macchina duplicato");
			
			String[] tokens = s.substring(3).split(",");
			
			tokens[0] = tokens[0].trim();
			int idx = tokens[0].indexOf("=");
			if(idx == -1 ) throw new BadFileFormatException("Rata daily malformata");
			String prefix = tokens[0].substring(0, idx+1);
			if(!prefix.equals("daily=")) throw new BadFileFormatException("Nome rata daily malformata");
			double dailyRate = extractRate(tokens[0], prefix);
			
			tokens[1] = tokens[1].trim();
			idx = tokens[1].indexOf("=");
			if(idx == -1 ) throw new BadFileFormatException("Rata weekend malformata");
			prefix = tokens[1].substring(0, idx+1);
			if(!prefix.equals("weekend=")) throw new BadFileFormatException("Nome rata weekend malformata");
			double weekendRate = extractRate(tokens[1], prefix);
			
			tokens[2] = tokens[2].trim();
			idx = tokens[2].indexOf("=");
			if(idx == -1 ) throw new BadFileFormatException("Rata dropoff malformata");
			prefix = tokens[2].substring(0, idx+1);
			if(!prefix.equals("dropoff=")) throw new BadFileFormatException("Nome rata dropoff malformata");
			double dropOffRate = extractRate(tokens[2], prefix);
			
			ans.put(c, new Rate(c, dailyRate, weekendRate, dropOffRate));
		}
		
		return ans;
	}
}
