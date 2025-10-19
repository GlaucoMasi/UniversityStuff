package cruciverba.controller;

import java.io.File;
import java.io.IOException;

import cruciverba.model.Cruciverba;
import cruciverba.model.SchemaNumerato;
import cruciverba.persistence.BadFileFormatException;


public class MockController extends MyController {
	
	public MockController() {
		super(new File("fake -- never used"));
	}

	public void leggiSchema() throws BadFileFormatException, IOException {
		setSchemaNumerato(new SchemaNumerato( creaCruciverbaLocale() ));
		setModalita(Modalita.ESPERTA);
	}
	
	private Cruciverba creaCruciverbaLocale() {
		Cruciverba cruciverba = new Cruciverba(11,17);
		
		cruciverba.setParola(0,0,"LI");
		cruciverba.setParola(0,3,"AV");
		cruciverba.setParola(0,6,"NOLI");
		cruciverba.setParola(0,12,"VLAD");
		
		cruciverba.setParola(1,0,"EST");
		cruciverba.setParola(1,4,"ALEA");
		cruciverba.setParola(1,9,"RETE");
		cruciverba.setParola(1,14,"SIM");
		
		cruciverba.setParola(2,0,"MAI");
		cruciverba.setParola(2,4,"JET");
		cruciverba.setParola(2,10,"VERBANO");

		cruciverba.setParola(3,1,"ACCONTENTARSI");
		cruciverba.setParola(3,15,"OR");
		
		cruciverba.setParola(4,0,"EC");
		cruciverba.setParola(4,3,"ENTUSIASMARE");
		cruciverba.setParola(4,16,"S");
			
		cruciverba.setParola(5,0,"A");
		cruciverba.setParola(5,2,"ATTANAGLIATO");
		cruciverba.setParola(5,15,"ME");
		
		cruciverba.setParola(6,1,"INT");
		cruciverba.setParola(6,5,"MOLECOLA");
		cruciverba.setParola(6,14,"LA");
		
		cruciverba.setParola(7,0,"CATONE");
		cruciverba.setParola(7,7,"TRONI");
		cruciverba.setParola(7,13,"SEGA");
		
		cruciverba.setParola(8,0,"OSE");
		cruciverba.setParola(8,4,"INIA");
		cruciverba.setParola(8,10,"E");
		cruciverba.setParola(8,12,"SUGAR");
		
		cruciverba.setParola(9,0,"CI");
		cruciverba.setParola(9,3,"ALTERCO");
		cruciverba.setParola(9,11,"ANSA");
		cruciverba.setParola(9,16,"G");

		cruciverba.setParola(10,0,"O");
		cruciverba.setParola(10,2,"ALOE");
		cruciverba.setParola(10,7,"ENOTECA");
		cruciverba.setParola(10,15,"AO");
		
		return cruciverba;
	}
	
}
