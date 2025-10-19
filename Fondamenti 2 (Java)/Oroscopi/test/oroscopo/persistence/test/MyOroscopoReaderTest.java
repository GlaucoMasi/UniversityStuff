package oroscopo.persistence.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import oroscopo.model.SegnoZodiacale;
import oroscopo.persistence.BadFileFormatException;
import oroscopo.persistence.MyOroscopoReader;


public class MyOroscopoReaderTest {
	
	@Test
	public void testOK_ctor() throws IOException, BadFileFormatException {
		var fakeFile = "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE";
		new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile));
	}

	@Test
	public void testKO_CtorBadParam() throws IOException, BadFileFormatException {
		assertThrows(NullPointerException.class, () -> new MyOroscopoReader().leggiPrevisioni(null));
	}

	@Test
	public void testOK_MultiSezione() throws IOException, BadFileFormatException {
		var fakeFile = "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE";
		new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile));
	}

	@Test
	public void testKO_NomeSezioneMancante1() throws IOException, BadFileFormatException {
		var fakeFile = "avrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE";
		assertThrows(BadFileFormatException.class, () -> new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_NomeSezioneMancante2() throws IOException, BadFileFormatException {
		var fakeFile = "stringasenzaspazichepuosempresuccedere\t\t4\tARIETE,TORO,GEMELLI\naltrastringasenzaspazi\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE";
		assertThrows(BadFileFormatException.class, () -> new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_MancaValore() throws IOException, BadFileFormatException {
		var fakeFile = "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
					   + "SEZIONE2\ntesto di prova\t\t\t\t\t\t\nFINE";
		assertThrows(BadFileFormatException.class, () -> new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_MancaFine() throws IOException, BadFileFormatException {
		var fakeFile = "avrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\n\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE";
		assertThrows(BadFileFormatException.class, () -> new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_SezioneSenzaFrasi() throws IOException, BadFileFormatException {
		var fakeFile = "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\n\nFINE";
		assertThrows(BadFileFormatException.class, () -> new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile)));
	}

	@Test
	public void testKO_SegniNonParsabili() throws IOException, BadFileFormatException {
		var fakeFile = "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tMUCCA,SIAMESI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE";
		assertThrows(BadFileFormatException.class, () -> new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile)));
	}

	@Test
	public void testOK_getPrevisioniPerSezioneEsistenteLowercase() throws IOException, BadFileFormatException {
		var fakeFile = "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE";

		var archivio = new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile));
		assertEquals(2, archivio.getPrevisioni("nomesezione").size());
		assertEquals(1, archivio.getPrevisioni("sezione2").size());
	}

	@Test
	public void testOK_LetturaCorrettaPrevisioni1() throws IOException, BadFileFormatException {
		var fakeFile = "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE";
		var archivio = new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile));
		
		assertEquals("avrai la testa un po' altrove", archivio.getPrevisioni("nomesezione").get(0).getPrevisione());
		assertEquals(4, archivio.getPrevisioni("nomesezione").get(0).getValore());

		Set<SegnoZodiacale> validi = Set.of(SegnoZodiacale.ARIETE, SegnoZodiacale.TORO, SegnoZodiacale.GEMELLI);
		for (SegnoZodiacale s : SegnoZodiacale.values()) {
			if (validi.contains(s))
				assertTrue(archivio.getPrevisioni("nomesezione").get(0).validaPerSegno(s));
			else
				assertFalse(archivio.getPrevisioni("nomesezione").get(0).validaPerSegno(s));
		}

		assertEquals("grande intimita'", archivio.getPrevisioni("nomesezione").get(1).getPrevisione());
		assertEquals(9, archivio.getPrevisioni("nomesezione").get(1).getValore());
		for (SegnoZodiacale s : SegnoZodiacale.values()) {
			assertTrue(archivio.getPrevisioni("nomesezione").get(1).validaPerSegno(s));
		}
	}

	@Test
	public void testOK_LetturaCorrettaPrevisioni2() throws IOException, BadFileFormatException {
		var fakeFile = "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE";
		
		var archivio = new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile));				
		assertEquals(1, archivio.getPrevisioni("sezione2").size());
		assertEquals("testo di prova", archivio.getPrevisioni("sezione2").get(0).getPrevisione());
		assertEquals(66, archivio.getPrevisioni("sezione2").get(0).getValore());

		for (SegnoZodiacale s : SegnoZodiacale.values()) {
			assertTrue(archivio.getPrevisioni("sezione2").get(0).validaPerSegno(s));
		}
	}

	@Test
	public void testOK_getPrevisioniPerSezioneEsistenteNomeUppercase() throws IOException, BadFileFormatException {
		var fakeFile = "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE";
		var archivio = new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile));				
		assertEquals(2, archivio.getPrevisioni("NOMESEZIONE").size());
	}

	@Test
	public void testOK_getPrevisioniPerSezioneNonEsistenteRestituisceNull() throws IOException, BadFileFormatException {
		var fakeFile = "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE";
		var archivio = new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile));				
		assertEquals(List.of(), archivio.getPrevisioni("SEZIONECHENONCE"));
	}

	@Test
	public void testOK_getSezioni() throws IOException, BadFileFormatException {
		var fakeFile = "NOMESEZIONE\navrai la testa un po' altrove\t\t4\tARIETE,TORO,GEMELLI\ngrande intimita'\t9\nFINE\n"
						+ "SEZIONE2\ntesto di prova\t\t\t\t\t66\t\nFINE";
		
		var archivio = new MyOroscopoReader().leggiPrevisioni(new StringReader(fakeFile));				
		assertEquals(2, archivio.getSettori().size());
		
		TreeSet<String> settori = new TreeSet<String>( (s1, s2) -> s1.compareToIgnoreCase(s2) );
		
		settori.addAll(archivio.getSettori());
		assertTrue(settori.contains("noMeSeZione"));
		assertTrue(settori.contains("sezione2"));
	}

}
