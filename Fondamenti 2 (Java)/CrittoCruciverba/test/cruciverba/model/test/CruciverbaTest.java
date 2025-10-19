package cruciverba.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import cruciverba.model.Cruciverba;

public class CruciverbaTest {

	@Test
	public void testOK_SchemaInizialeNonConfigurato() {
		Cruciverba schema = new Cruciverba(8,10);
		//System.out.print(schema);
		assertEquals( 8, schema.getNumRighe());
		assertEquals(10, schema.getNumColonne());
		
		for(int r=0; r<schema.getNumRighe(); r++)
			for(int c=0; c<schema.getNumColonne(); c++) {
				assertEquals(Cruciverba.NERA, schema.getCella(r, c));
			}
	}
	
	@Test
	public void testOK_SchemaInizialeVerificaRigheColonne() {
		Cruciverba schema = new Cruciverba(8,10);
		assertEquals( 8, schema.getNumRighe());
		assertEquals(10, schema.getNumColonne());
		
		char[] rigaVuota = new char[schema.getNumColonne()];
		Arrays.fill(rigaVuota,Cruciverba.NERA);
		for(int r=0; r<schema.getNumRighe(); r++)
			assertArrayEquals(rigaVuota, schema.getRiga(r));

		char[] colonnaVuota = new char[schema.getNumRighe()];
		Arrays.fill(colonnaVuota,Cruciverba.NERA);
		for(int c=0; c<schema.getNumColonne(); c++)
			assertArrayEquals(colonnaVuota, schema.getColonna(c));
	}

	@Test
	public void testOK_SchemaConfigurato() {
		Cruciverba schema = new Cruciverba(11,17);
		//System.out.print(schema);
		assertEquals(11, schema.getNumRighe());
		assertEquals(17, schema.getNumColonne());
		
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
		
		System.out.print(schema);
		
		assertArrayEquals(new String[] {"LEM", "EA", "COCO"},schema.paroleInColonna(0));
		assertArrayEquals(new String[] {"ISAAC", "IASI"},schema.paroleInColonna(1));
		assertArrayEquals(new String[] {"TIC", "ANTE", "A"},schema.paroleInColonna(2));
		assertArrayEquals(new String[] {"A", "CETTO", "AL"},schema.paroleInColonna(3));
		assertArrayEquals(new String[] {"VAJONT", "NILO"},schema.paroleInColonna(4));
		assertArrayEquals(new String[] {"LENTAMENTE"},schema.paroleInColonna(5));	
		assertArrayEquals(new String[] {"NETTUNO", "IE"},schema.paroleInColonna(6));
		assertArrayEquals(new String[] {"OA", "ESALTARE"},schema.paroleInColonna(7));
		assertArrayEquals(new String[] {"L", "NIGER", "CN"},schema.paroleInColonna(8));
		assertArrayEquals(new String[] {"IR", "TALCO", "OO"},schema.paroleInColonna(9));
		assertArrayEquals(new String[] {"EVASIONE", "T"},schema.paroleInColonna(10));
		assertArrayEquals(new String[] {"TERMALI", "AE"},schema.paroleInColonna(11));
		assertArrayEquals(new String[] {"VERSATA", "SNC"},schema.paroleInColonna(12));
		assertArrayEquals(new String[] {"L", "BIRO", "SUSA"},schema.paroleInColonna(13));
		assertArrayEquals(new String[] {"ASA", "E", "LEGA"},schema.paroleInColonna(14));
		assertArrayEquals(new String[] {"DINO", "MAGA", "A"},schema.paroleInColonna(15));
		assertArrayEquals(new String[] {"MORSE", "ARGO"},schema.paroleInColonna(16));
		
		assertArrayEquals(new String[] {"LI",  "AV",   "NOLI", "VLAD"}, schema.paroleInRiga(0));
		assertArrayEquals(new String[] {"EST", "ALEA", "RETE", "SIM"}, schema.paroleInRiga(1));
		assertArrayEquals(new String[] {"MAI", "JET",  "VERBANO"}, schema.paroleInRiga(2));
		assertArrayEquals(new String[] {"ACCONTENTARSI", "OR"}, schema.paroleInRiga(3));
		assertArrayEquals(new String[] {"EC", "ENTUSIASMARE", "S"}, schema.paroleInRiga(4));
		assertArrayEquals(new String[] {"A", "ATTANAGLIATO", "ME"}, schema.paroleInRiga(5));
		assertArrayEquals(new String[] {"INT", "MOLECOLA", "LA"}, schema.paroleInRiga(6));
		assertArrayEquals(new String[] {"CATONE", "TRONI", "SEGA"}, schema.paroleInRiga(7));
		assertArrayEquals(new String[] {"OSE", "INIA", "E", "SUGAR"}, schema.paroleInRiga(8));
		assertArrayEquals(new String[] {"CI", "ALTERCO", "ANSA", "G"}, schema.paroleInRiga(9));
		assertArrayEquals(new String[] {"O", "ALOE", "ENOTECA", "AO"}, schema.paroleInRiga(10));
	}

}
