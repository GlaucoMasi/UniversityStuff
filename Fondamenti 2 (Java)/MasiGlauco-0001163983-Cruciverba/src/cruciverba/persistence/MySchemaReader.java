package cruciverba.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import cruciverba.model.Cruciverba;
import cruciverba.model.Orientamento;


public class MySchemaReader implements SchemaReader {

	private Reader inputReader;
	
	public MySchemaReader(Reader inputReader) {
		this.inputReader = inputReader;
	}
	
	public Cruciverba leggiSchema() throws BadFileFormatException, IOException {
		BufferedReader br = new BufferedReader(inputReader);
		List<String> rows = new ArrayList<>();
		
		String line;
		OptionalInt length = OptionalInt.empty();
		while((line = br.readLine()) != null) {
			if(length.isEmpty()) length = OptionalInt.of(line.length());
			else if(length.getAsInt() != line.length()) throw new BadFileFormatException("Righe di lunghezza diversa");
			rows.add(line);
		}
		
		if(rows.isEmpty()) throw new BadFileFormatException("File vuoto");
		
		Cruciverba cruciverba = new Cruciverba(rows.size(), length.getAsInt());
		
		for(int i = 0; i < rows.size(); i++) {
			List<ElementoRiga> elementi = SchemaReader.elementiRiga(rows.get(i));
			for(ElementoRiga elem : elementi) cruciverba.setParola(i, elem.pos(), elem.string(), Orientamento.ORIZZONTALE);
		}
		
		return cruciverba;
	}

}
