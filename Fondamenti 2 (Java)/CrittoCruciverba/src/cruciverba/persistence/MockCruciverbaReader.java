package cruciverba.persistence;

import java.io.IOException;
import java.io.Reader;

import cruciverba.model.Cruciverba;


public class MockCruciverbaReader implements CruciverbaReader {
		
	public Cruciverba leggiCruciverba(Reader inputReader) throws BadFileFormatException, IOException {
		Cruciverba schema = new Cruciverba(11,17);

		schema.setParola(0,0,"LI");
		schema.setParola(0,3,"AV");
		schema.setParola(0,6,"NOLI");
		schema.setParola(0,12,"VLAD");
		
		schema.setParola(1,0,"EST");
		schema.setParola(1,4,"ALEA");
		schema.setParola(1,9,"RETE");
		schema.setParola(1,14,"SIM");
		
		schema.setParola(2,0,"MAI");
		schema.setParola(2,4,"JET");
		schema.setParola(2,10,"VERBANO");

		schema.setParola(3,1,"ACCONTENTARSI");
		schema.setParola(3,15,"OR");
		
		schema.setParola(4,0,"EC");
		schema.setParola(4,3,"ENTUSIASMARE");
		schema.setParola(4,16,"S");
			
		schema.setParola(5,0,"A");
		schema.setParola(5,2,"ATTANAGLIATO");
		schema.setParola(5,15,"ME");
		
		schema.setParola(6,1,"INT");
		schema.setParola(6,5,"MOLECOLA");
		schema.setParola(6,14,"LA");
		
		schema.setParola(7,0,"CATONE");
		schema.setParola(7,7,"TRONI");
		schema.setParola(7,13,"SEGA");
		
		schema.setParola(8,0,"OSE");
		schema.setParola(8,4,"INIA");
		schema.setParola(8,10,"E");
		schema.setParola(8,12,"SUGAR");
		
		schema.setParola(9,0,"CI");
		schema.setParola(9,3,"ALTERCO");
		schema.setParola(9,11,"ANSA");
		schema.setParola(9,16,"G");

		schema.setParola(10,0,"O");
		schema.setParola(10,2,"ALOE");
		schema.setParola(10,7,"ENOTECA");
		schema.setParola(10,15,"AO");	
		
		return schema;
	}
}
