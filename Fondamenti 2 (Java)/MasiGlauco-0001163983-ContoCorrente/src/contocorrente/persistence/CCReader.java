package contocorrente.persistence;

import java.io.IOException;
import java.io.Reader;

import contocorrente.model.ContoCorrente;

public interface CCReader {
	public ContoCorrente readCC(Reader rdr) throws IOException;
}