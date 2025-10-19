package cruciverba.persistence;

import java.io.IOException;
import java.io.Reader;

import cruciverba.model.Cruciverba;


public interface CruciverbaReader {
	final static char SEPARATORE = '|';
	final static String ESORDIO = "Cruciverba";
	final static String PER = "x";
	
	Cruciverba leggiCruciverba(Reader inputReader) throws BadFileFormatException, IOException;	
}
