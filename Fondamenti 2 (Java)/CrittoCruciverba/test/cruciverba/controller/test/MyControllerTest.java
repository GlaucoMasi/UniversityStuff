package cruciverba.controller.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cruciverba.model.SchemaNumerato;
import cruciverba.controller.Modalita;
import cruciverba.controller.MyController;
import cruciverba.controller.Controller;
import cruciverba.model.Cruciverba;


class MyControllerTest {

	static Cruciverba cruciverba;
	static SchemaNumerato schemaNumerato;
	static SchemaNumerato schemaCorrente;
	static Controller controller; 
	
	@BeforeAll
	static void setup() {
		Cruciverba cruciverba = new Cruciverba(11,17);
		
		assertEquals(11, cruciverba.getNumRighe());
		assertEquals(17, cruciverba.getNumColonne());
		
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
		
		//System.out.print(cruciverba);
		
		assertArrayEquals(new String[] {"LEM", "EA", "COCO"},cruciverba.paroleInColonna(0));
		assertArrayEquals(new String[] {"ISAAC", "IASI"},cruciverba.paroleInColonna(1));
		assertArrayEquals(new String[] {"TIC", "ANTE", "A"},cruciverba.paroleInColonna(2));
		assertArrayEquals(new String[] {"A", "CETTO", "AL"},cruciverba.paroleInColonna(3));
		assertArrayEquals(new String[] {"VAJONT", "NILO"},cruciverba.paroleInColonna(4));
		assertArrayEquals(new String[] {"LENTAMENTE"},cruciverba.paroleInColonna(5));	
		assertArrayEquals(new String[] {"NETTUNO", "IE"},cruciverba.paroleInColonna(6));
		assertArrayEquals(new String[] {"OA", "ESALTARE"},cruciverba.paroleInColonna(7));
		assertArrayEquals(new String[] {"L", "NIGER", "CN"},cruciverba.paroleInColonna(8));
		assertArrayEquals(new String[] {"IR", "TALCO", "OO"},cruciverba.paroleInColonna(9));
		assertArrayEquals(new String[] {"EVASIONE", "T"},cruciverba.paroleInColonna(10));
		assertArrayEquals(new String[] {"TERMALI", "AE"},cruciverba.paroleInColonna(11));
		assertArrayEquals(new String[] {"VERSATA", "SNC"},cruciverba.paroleInColonna(12));
		assertArrayEquals(new String[] {"L", "BIRO", "SUSA"},cruciverba.paroleInColonna(13));
		assertArrayEquals(new String[] {"ASA", "E", "LEGA"},cruciverba.paroleInColonna(14));
		assertArrayEquals(new String[] {"DINO", "MAGA", "A"},cruciverba.paroleInColonna(15));
		assertArrayEquals(new String[] {"MORSE", "ARGO"},cruciverba.paroleInColonna(16));
		
		assertArrayEquals(new String[] {"LI",  "AV",   "NOLI", "VLAD"}, cruciverba.paroleInRiga(0));
		assertArrayEquals(new String[] {"EST", "ALEA", "RETE", "SIM"}, cruciverba.paroleInRiga(1));
		assertArrayEquals(new String[] {"MAI", "JET",  "VERBANO"}, cruciverba.paroleInRiga(2));
		assertArrayEquals(new String[] {"ACCONTENTARSI", "OR"}, cruciverba.paroleInRiga(3));
		assertArrayEquals(new String[] {"EC", "ENTUSIASMARE", "S"}, cruciverba.paroleInRiga(4));
		assertArrayEquals(new String[] {"A", "ATTANAGLIATO", "ME"}, cruciverba.paroleInRiga(5));
		assertArrayEquals(new String[] {"INT", "MOLECOLA", "LA"}, cruciverba.paroleInRiga(6));
		assertArrayEquals(new String[] {"CATONE", "TRONI", "SEGA"}, cruciverba.paroleInRiga(7));
		assertArrayEquals(new String[] {"OSE", "INIA", "E", "SUGAR"}, cruciverba.paroleInRiga(8));
		assertArrayEquals(new String[] {"CI", "ALTERCO", "ANSA", "G"}, cruciverba.paroleInRiga(9));
		assertArrayEquals(new String[] {"O", "ALOE", "ENOTECA", "AO"}, cruciverba.paroleInRiga(10));
		
		// --- predisposizione controller 
		
		schemaNumerato = new SchemaNumerato(cruciverba);	
		controller = new MyController(new File("fake"));
		controller.setSchemaNumerato(schemaNumerato);
	}
	
	@Test
	public void testOK_ParolaIniziale_ModalitaEsperta() {
		controller.setModalita(Modalita.ESPERTA);
		assertSame(schemaNumerato, controller.getSchemaNumerato());
		assertSame(Modalita.ESPERTA, controller.getModalita());
		assertTrue(List.of("EST", "ALEA", "RETE", "SIM", "MAI", "JET", "INT", "OSE", "INIA", "ANSA").contains(controller.parolaIniziale()));
	}
	
	@Test
	public void testOK_ParolaIniziale_ModalitaAgevolata() {
		controller.setModalita(Modalita.AGEVOLATA);
		assertSame(schemaNumerato, controller.getSchemaNumerato());
		assertSame(Modalita.AGEVOLATA, controller.getModalita());
		assertTrue(List.of("NOLI", "VLAD", "SEGA", "ALOE").contains(controller.parolaIniziale()));
	}
	
	@Test
	public void testOK_impostaCaratteriIniziali_ModalitaAgevolata() {
		controller.setModalita(Modalita.AGEVOLATA);
		assertSame(schemaNumerato, controller.getSchemaNumerato());
		assertSame(Modalita.AGEVOLATA, controller.getModalita());
		assertTrue(List.of("NOLI", "VLAD", "SEGA", "ALOE").contains(controller.parolaIniziale()));
		controller.impostaCaratteriIniziali();
		//System.out.println(controller.getSchemaNumerato());
	}
	
	@Test
	public void testOK_associazione_ModalitaAgevolata(){
		controller.setModalita(Modalita.AGEVOLATA);
		controller.associa(1, 'L');
		assertEquals('L', controller.getAssociazione(1) );
		controller.disassocia(1);
		assertEquals(Cruciverba.BIANCA, controller.getAssociazione(1) );
	}
	
	@Test
	public void testOK_associazione_ModalitaEsperta(){
		controller.setModalita(Modalita.ESPERTA);
		controller.associa(1, 'L');
		assertEquals('L', controller.getAssociazione(1) );
		controller.disassocia(1);
		assertEquals(Cruciverba.BIANCA, controller.getAssociazione(1) );
	}
	
	@Test
	public void testOK_associazioniMultiple_ModalitaAgevolata(){
		controller.setModalita(Modalita.AGEVOLATA);
		controller.associa(1, 'L');
		assertEquals('L', controller.getAssociazione(1) );
		assertThrows(UnsupportedOperationException.class, () -> controller.associa(1, 'M'));
		controller.disassocia(1);
		assertEquals(Cruciverba.BIANCA, controller.getAssociazione(1) );
		assertThrows(UnsupportedOperationException.class, () -> controller.associa(1, 'M'));
	}
	
	@Test
	public void testOK_associazioniMultiple_ModalitaEsperta(){
		controller.setModalita(Modalita.ESPERTA);
		controller.associa(1, 'L');
		assertEquals('L', controller.getAssociazione(1) );
		controller.associa(1, 'M');
		assertEquals('M', controller.getAssociazione(1) );
		controller.disassocia(1);
		assertEquals(Cruciverba.BIANCA, controller.getAssociazione(1) );
	}
	
	@Test 
	public void testOK_lettereDisponibili() {
		assertEquals(List.of('A', 'B', 'C', 'D', 'E', 'G', 'I', 'J', 'L', 'M', 'N', 'O', 'R', 'S', 'T', 'U', 'V'), controller.lettereDisponibili());
	}
	
	@Test 
	public void testOK_numeriDisponibili() {		
		assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17), controller.numeriDisponibili());
	}
	
}
