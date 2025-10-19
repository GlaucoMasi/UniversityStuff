package oroscopo.persistence;

import java.io.IOException;
import java.io.Reader;
import oroscopo.model.ArchivioPrevisioni;


public interface OroscopoReader {

	ArchivioPrevisioni leggiPrevisioni(Reader inputReader) throws IOException, BadFileFormatException;

}
