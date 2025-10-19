package cruciverba.persistence.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;

import cruciverba.persistence.CruciverbaReader;
import cruciverba.persistence.BadFileFormatException;
import cruciverba.persistence.MyCruciverbaReader;


public class MyCruciverbaReaderTest {
	
	@Test
	public void testOK() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba 11 x 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		var cruciverba = myReader.leggiCruciverba(new StringReader(fakeFile));
		assertEquals(11, cruciverba.getNumRighe());
		assertEquals(17, cruciverba.getNumColonne());
	}
	
	@Test
	public void testOK_ParoleLette() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba 11 x 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		var cruciverba = myReader.leggiCruciverba(new StringReader(fakeFile));
		assertArrayEquals(new String[] {"LEM", "EA", "COCO"}, cruciverba.paroleInColonna(0));
		assertArrayEquals(new String[] {"ISAAC", "IASI"}, cruciverba.paroleInColonna(1));
		assertArrayEquals(new String[] {"TIC", "ANTE", "A"}, cruciverba.paroleInColonna(2));
		assertArrayEquals(new String[] {"A", "CETTO", "AL"}, cruciverba.paroleInColonna(3));
		assertArrayEquals(new String[] {"VAJONT", "NILO"}, cruciverba.paroleInColonna(4));
		assertArrayEquals(new String[] {"LENTAMENTE"}, cruciverba.paroleInColonna(5));
		assertArrayEquals(new String[] {"NETTUNO", "IE"}, cruciverba.paroleInColonna(6));
		assertArrayEquals(new String[] {"OA", "ESALTARE"}, cruciverba.paroleInColonna(7));
		assertArrayEquals(new String[] {"L", "NIGER", "CN"}, cruciverba.paroleInColonna(8));
		assertArrayEquals(new String[] {"IR", "TALCO", "OO"}, cruciverba.paroleInColonna(9));
		assertArrayEquals(new String[] {"EVASIONE", "T"}, cruciverba.paroleInColonna(10));
		assertArrayEquals(new String[] {"TERMALI", "AE"}, cruciverba.paroleInColonna(11));
		assertArrayEquals(new String[] {"VERSATA", "SNC"}, cruciverba.paroleInColonna(12));
		assertArrayEquals(new String[] {"L", "BIRO", "SUSA"}, cruciverba.paroleInColonna(13));
		assertArrayEquals(new String[] {"ASA", "E", "LEGA"}, cruciverba.paroleInColonna(14));
		assertArrayEquals(new String[] {"DINO", "MAGA", "A"}, cruciverba.paroleInColonna(15));
		assertArrayEquals(new String[] {"MORSE", "ARGO"}, cruciverba.paroleInColonna(16));
	}
		
	@Test
	public void testKO_PrimaRigaSbagliata_MancaCruciverba() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "11 x 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_PrimaRigaSbagliata_HeaderErrato() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "CruCCiverba 11 x 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_PrimaRigaSbagliata_MancaX() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba 11 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_PrimaRigaSbagliata_MancaNumeroRighe() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba x 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_PrimaRigaSbagliata_MancaNumeroColonne() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba 11 x \r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_PrimaRigaSbagliata_NumeroRigheZero() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba 0 x 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_PrimaRigaSbagliata_NumeroColonneZero() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba 11 x 0 \r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_PrimaRigaSbagliata_NumeroRigheNegativo() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba -11 x 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_PrimaRigaSbagliata_NumeroColonneNegativo() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba 11 x -17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_UnaRigaPiuLunga() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba 11 x 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |U|\r\n" // qui
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_UnaRigaPiuCorta() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba 11 x 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A| |\r\n" // qui
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_NumeroRigheMinoreDelPrevisto() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba 12 x 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}
	
	@Test
	public void testKO_NumeroRigheMaggioreDelPrevisto() throws BadFileFormatException, IOException {
		String fakeFile = 
				  "Cruciverba 10 x 17\r\n"
				  + "|L|I| |A|V| |N|O|L|I| | |V|L|A|D| |\r\n"
				  + "|E|S|T| |A|L|E|A| |R|E|T|E| |S|I|M|\r\n"
				  + "|M|A|I| |J|E|T| | | |V|E|R|B|A|N|O|\r\n"
				  + "| |A|C|C|O|N|T|E|N|T|A|R|S|I| |O|R|\r\n"
				  + "|E|C| |E|N|T|U|S|I|A|S|M|A|R|E| |S|\r\n"
				  + "|A| |A|T|T|A|N|A|G|L|I|A|T|O| |M|E|\r\n"
				  + "| |I|N|T| |M|O|L|E|C|O|L|A| |L|A| |\r\n"
				  + "|C|A|T|O|N|E| |T|R|O|N|I| |S|E|G|A|\r\n"
				  + "|O|S|E| |I|N|I|A| | |E| |S|U|G|A|R|\r\n"
				  + "|C|I| |A|L|T|E|R|C|O| |A|N|S|A| |G|\r\n"
				  + "|O| |A|L|O|E| |E|N|O|T|E|C|A| |A|O|";
		CruciverbaReader myReader = new MyCruciverbaReader();
		assertThrows(BadFileFormatException.class, () -> myReader.leggiCruciverba(new StringReader(fakeFile)));
	}
}
