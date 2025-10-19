package cruciverba.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Objects;

import cruciverba.model.Cruciverba;


public class MyCruciverbaReader implements CruciverbaReader {
		
	public Cruciverba leggiCruciverba(Reader inputReader) throws BadFileFormatException, IOException {
		Objects.requireNonNull(inputReader, "Fornito un reader nullo");
		
		BufferedReader br = new BufferedReader(inputReader);
		String firstLine = br.readLine().trim();
		
		if(!firstLine.startsWith(CruciverbaReader.ESORDIO + " ")) throw new BadFileFormatException("Prima riga malformata: " + firstLine);
		String[] dims = firstLine.substring(CruciverbaReader.ESORDIO.length()).trim().split(" +" + CruciverbaReader.PER + " +");
		if(dims.length != 2) throw new BadFileFormatException("Prima riga malformata: " + firstLine);
		
		int righe, colonne;
		Cruciverba cruciverba;
		try {
			righe = Integer.parseInt(dims[0]);
			colonne = Integer.parseInt(dims[1]);
			cruciverba = new Cruciverba(righe, colonne);
		} catch(NumberFormatException e) {
			throw new BadFileFormatException("Dimensioni malformate: " + dims[0] + dims[1]);
		} catch(IllegalArgumentException e) {
			throw new BadFileFormatException(e);
		}

		for(int i = 0; i < righe; i++) {
			String s = br.readLine();
			if(s == null) throw new BadFileFormatException("Riga numero " + (i+1) + " inesistente");
			if(s.isBlank()) continue;
			if(s.charAt(0) != CruciverbaReader.SEPARATORE) throw new BadFileFormatException("Separatore mancante all'inizio della riga numero " + (i+1) + ": " + s);
			
			String[] tokens = s.substring(1).split("\\"+CruciverbaReader.SEPARATORE);
			if(tokens.length != colonne) throw new BadFileFormatException("Riga numero " + (i+1) +  " malformata: " + s);
			if(Arrays.stream(tokens).filter(c -> c.length() != 1).count() != 0) throw new BadFileFormatException("Riga numero " + (i+1) + " contenente sequenze di più caratteri: " + s);
			
			for(int j = 0; j < colonne; j++) if(!tokens[j].equals(" ")) cruciverba.setCella(i, j, tokens[j].charAt(0));
		}
		
		String lastLine = br.readLine();
		if(lastLine != null && !lastLine.isBlank()) throw new BadFileFormatException("Più righe del previsto");
		
		return cruciverba;
	}
}
