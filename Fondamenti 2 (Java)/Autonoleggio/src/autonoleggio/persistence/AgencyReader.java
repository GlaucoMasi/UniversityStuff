package autonoleggio.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import autonoleggio.model.Agency;


public interface AgencyReader {
	Set<Agency> readAllAgencies(Reader reader) throws IOException, BadFileFormatException;
}
