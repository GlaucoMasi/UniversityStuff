package cruciverba.model.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.OptionalInt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import cruciverba.model.SchemaNumerato;

import cruciverba.model.Cruciverba;


class SchemaNumeratoTest {

	static Cruciverba cruciverba;
	static SchemaNumerato schemaNumerato;
	
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
		
		System.out.print(cruciverba);
		
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
		
		///--------------- fine check Cruciverba ---------------------- 
		
		schemaNumerato = new SchemaNumerato(cruciverba);
		
		assertSame(cruciverba, schemaNumerato.getCruciverba());
		assertEquals(cruciverba.getNumRighe(), schemaNumerato.getNumRighe());
		assertEquals(cruciverba.getNumColonne(), schemaNumerato.getNumColonne());
		
		System.out.print(schemaNumerato);
	}
	
	@Test
	public void testOK_numerazioneCelle() {

		assertEquals(  1, schemaNumerato.getCella(0, 0) );
		assertEquals(  2, schemaNumerato.getCella(0, 1) );
		assertEquals(  3, schemaNumerato.getCella(0, 3) );
		assertEquals(  4, schemaNumerato.getCella(0, 4) );
		assertEquals(  4, schemaNumerato.getCella(0,12) );
		assertEquals(  7, schemaNumerato.getCella(0,15) );
		
		assertEquals( 12, schemaNumerato.getCella(1,16) );
		
		assertEquals( 12, schemaNumerato.getCella(2, 0) );
		assertEquals( 13, schemaNumerato.getCella(2, 4) );
		assertEquals(  4, schemaNumerato.getCella(2,10) );
		assertEquals( 14, schemaNumerato.getCella(2,13) );
		assertEquals(  3, schemaNumerato.getCella(2, 1) );
		assertEquals(  2, schemaNumerato.getCella(2, 2) );
		assertEquals(  6, schemaNumerato.getCella(2,16));
		
		assertEquals(  0, schemaNumerato.getCella(3, 0) );
		assertEquals(  3, schemaNumerato.getCella(3, 1) );
		assertEquals( 15, schemaNumerato.getCella(3, 3) );
		assertEquals( 10, schemaNumerato.getCella(3, 9) );
		assertEquals(  6, schemaNumerato.getCella(3,15) );
		
		assertEquals(  0, schemaNumerato.getCella(6, 0) );
		assertEquals(  2, schemaNumerato.getCella(6, 1) );
		assertEquals( 12, schemaNumerato.getCella(6, 5) );
		assertEquals(  1, schemaNumerato.getCella(6,14) );
		
		assertEquals(  3, schemaNumerato.getCella(7,16) );
		
		assertEquals(  6, schemaNumerato.getCella(8, 0) );
		assertEquals(  2, schemaNumerato.getCella(8, 4) );
		assertEquals(  2, schemaNumerato.getCella(8, 6) );
		assertEquals(  9, schemaNumerato.getCella(8,12) );
		
		
		assertEquals( OptionalInt.of( 1),  schemaNumerato.getMapping('L'));
		assertEquals( OptionalInt.of( 2),  schemaNumerato.getMapping('I'));
		assertEquals( OptionalInt.of( 3),  schemaNumerato.getMapping('A'));
		assertEquals( OptionalInt.of( 4),  schemaNumerato.getMapping('V'));
		assertEquals( OptionalInt.of( 5),  schemaNumerato.getMapping('N'));
		assertEquals( OptionalInt.of( 6),  schemaNumerato.getMapping('O'));
		assertEquals( OptionalInt.of( 7),  schemaNumerato.getMapping('D'));
		assertEquals( OptionalInt.of( 8),  schemaNumerato.getMapping('E'));
		assertEquals( OptionalInt.of( 9),  schemaNumerato.getMapping('S'));
		assertEquals( OptionalInt.of(10),  schemaNumerato.getMapping('T'));
		assertEquals( OptionalInt.of(11),  schemaNumerato.getMapping('R'));
		assertEquals( OptionalInt.of(12),  schemaNumerato.getMapping('M'));
		assertEquals( OptionalInt.of(13),  schemaNumerato.getMapping('J'));
		assertEquals( OptionalInt.of(14),  schemaNumerato.getMapping('B'));
		assertEquals( OptionalInt.of(15),  schemaNumerato.getMapping('C'));
		assertEquals( OptionalInt.of(16),  schemaNumerato.getMapping('U'));
		assertEquals( OptionalInt.of(17),  schemaNumerato.getMapping('G'));
		
		assertEquals( OptionalInt.empty(), schemaNumerato.getMapping('F'));
		assertEquals( OptionalInt.empty(), schemaNumerato.getMapping('H'));
		assertEquals( OptionalInt.empty(), schemaNumerato.getMapping('K'));
		assertEquals( OptionalInt.empty(), schemaNumerato.getMapping('P'));
		assertEquals( OptionalInt.empty(), schemaNumerato.getMapping('Q'));
		assertEquals( OptionalInt.empty(), schemaNumerato.getMapping('W'));
		assertEquals( OptionalInt.empty(), schemaNumerato.getMapping('X'));
		assertEquals( OptionalInt.empty(), schemaNumerato.getMapping('Y'));
		assertEquals( OptionalInt.empty(), schemaNumerato.getMapping('Z'));
	}
	
	@Test
	public void testOK_associazione(){
		schemaNumerato.associa(1, 'L');
		assertEquals('L', schemaNumerato.getAssociazione(1) );
		schemaNumerato.disassocia(1);
		assertEquals(Cruciverba.BIANCA, schemaNumerato.getAssociazione(1) );
	}
	
	@Test
	public void testOK_associazioniMultiple(){
		schemaNumerato.associa(1, 'L');
		assertEquals('L', schemaNumerato.getAssociazione(1) );
		schemaNumerato.associa(1, 'M');
		assertEquals('M', schemaNumerato.getAssociazione(1) );
		schemaNumerato.disassocia(1);
		assertEquals(Cruciverba.BIANCA, schemaNumerato.getAssociazione(1) );
	}
	
	@Test
	public void testKO_associaNumeroIllegaleMinoreDi1(){
		assertThrows(IllegalArgumentException.class, () -> schemaNumerato.associa(0, 'L'));
	}

	@Test
	public void testKO_associaNumeroIllegaleNegativo(){
		assertThrows(IllegalArgumentException.class, () -> schemaNumerato.associa(-1, 'L'));
	}

	@Test
	public void testKO_associaNumeroIllegaleMaggioreDi26(){
		assertThrows(IllegalArgumentException.class, () -> schemaNumerato.associa(27, 'L'));
	}

}
