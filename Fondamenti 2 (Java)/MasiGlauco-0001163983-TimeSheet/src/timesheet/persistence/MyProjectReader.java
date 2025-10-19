package timesheet.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.io.BufferedReader;


public class MyProjectReader implements ProjectReader {
	
	private static final int    TOKEN_COUNT = 3;
	private static final String REGEX = "\t+|:";

	
	public Map<String,Integer> projectHours(Reader reader) throws IOException, BadFileFormatException  {
		Objects.requireNonNull(reader, "Il reader fornito non può essere nullo");
		
		String in;
		Map<String, Integer> ans = new HashMap<>();
		BufferedReader br = new BufferedReader(reader);
		
		while((in = br.readLine()) != null) {
			String[] tokens = in.split(REGEX);
			
			if(tokens.length != TOKEN_COUNT) throw new BadFileFormatException("Riga malformata: " + in);
			
			if(!tokens[1].trim().equals("ore previste")) throw new BadFileFormatException("Frase ore previste: non presente" + tokens[1].trim());

			Integer ore;
			try {
				ore = Integer.parseInt(tokens[2].trim());
			} catch(NumberFormatException e) {
				throw new BadFileFormatException("Numero di ore malformato: " + tokens[2]);
			}
			
			if(ore <= 0) throw new BadFileFormatException("Numero di ore non positivo: " + ore);
			
			ans.put(tokens[0].trim(), ore);
		}
		
		return ans;
	}
	
}
