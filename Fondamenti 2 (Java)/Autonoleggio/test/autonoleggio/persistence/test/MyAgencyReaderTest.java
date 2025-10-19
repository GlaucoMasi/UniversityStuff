package autonoleggio.persistence.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

import autonoleggio.persistence.BadFileFormatException;
import autonoleggio.persistence.MyAgencyReader;


public class MyAgencyReaderTest {

	@Test
	void testOK() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lun-ven 08:00-23:59, sab 08:00-23:59, dom 08:00-23:59\r\n"
				  + "BOLOGNA, Stazione centrale, lun-ven 09:00-13:00/14:00-18:00, sab chiuso, dom chiuso\r\n"
				  + "MODENA, centro, lun-ven 08:30-12:00/15:00-18:00, sab chiuso, dom chiuso\r\n"
				  + "REGGIO EMILIA, centro, lun-ven 08:30-12:30/14:30-18:30, sab 08:30-12:30, dom chiuso\r\n"
				  + "PARMA, centro, lun-ven 08:30-12:30/14:30-18:00, sab 08:30-12:00, dom chiuso\r\n"
				  + "FERRARA, centro, lun-ven 08:30-12:30/14:30-18:30, sab 08:30-12:30, dom chiuso\r\n"
				  + "RIMINI, centro, lun-ven 08:30-12:30/15:00-19:00, sab 09:00-13:00, dom chiuso\r\n"
				  + "VERONA, Aeroporto Catullo, lun-ven 08:30-22:30, sab 08:30-22:30, dom 08:30-22:30\r\n"
				  + "VERONA, centro, lun-ven 08:30-12:30/14:00-18:00, sab 08:30-12:30, dom chiuso\r\n"
				  + "MANTOVA, centro, lun-ven 09:00-13:00, sab chiuso, dom chiuso\r\n"
				  + "VENEZIA, Aeroporto Marco Polo, lun-ven 08:00-23:59, sab 08:00-23:59, dom 08:00-23:59\r\n"
				  + "VENEZIA, Mestre stazione, lun-ven 08:30-18:30, sab 08:00-12:30, dom chiuso\r\n"
				  + "MILANO, Aeroporto Malpensa T1, lun-ven 07:30-23:59, sab 07:30-23:59, dom 07:30-23:59\r\n"
				  + "MILANO, Aeroporto Malpensa T2, lun-ven 08:00-14:00/18:00-23:59, sab chiuso, dom 08:00-14:00/18:00-23:59\r\n"
				  + "MILANO, Aeroporto Linate, lun-ven 07:30-23:59, sab 07:30-23:59, dom 07:30-23:59\r\n"
				  + "MILANO, Stazione Rogoredo, lun-ven 08:00-17:00, sab 08:30-12:00, dom chiuso\r\n"
				  + "MILANO, Stazione centrale, lun-ven 08:30-20:00, sab 08:30-20:00, dom 08:30-17:00\r\n"
				  + "MILANO, centro, lun-ven 08:30-17:00, sab 08:30-17:00, dom chiuso\r\n"
				  + "TORINO, Aeroporto Caselle, lun-ven 08:30-23:00, sab 08:30-21:00, dom 08:30-20:00\r\n"
				  + "TORINO, centro, lun-ven 08:30-18:30, sab 09:00-12:30, dom chiuso\r\n"
				  + "FIRENZE, Aeroporto Peretola, lun-ven 08:00-23:00, sab 08:00-23:00, dom 08:00-23:00\r\n"
				  + "FIRENZE, Stazione SMN, lun-ven 09:00-17:00, sab 09:00-13:00, dom chiuso";
		var agencies = new MyAgencyReader().readAllAgencies(new StringReader(fakefile));
		System.out.println(agencies.size());
		System.out.println(agencies);
		assertEquals(22, agencies.size());
	}
	
	@Test
	void testKO_NumItemsNon5_MissingComma() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA Aeroporto Marconi, lun-ven 08:00-23:59, sab 08:00-23:59, dom 08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumItemsNon5_ExtraComma() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA,, Aeroporto Marconi, lun-ven 08:00-23:59, sab 08:00-23:59, dom 08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumItemsNon5_ExtraElement() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto, Marconi, lun-ven 08:00-23:59, sab 08:00-23:59, dom 08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumItemsNon5_MissingElement() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lun-ven 08:00-23:59, sab 08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongLunVen() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lunven 08:00-23:59, sab 08:00-23:59, dom 08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongSab() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lun-ven 08:00-23:59, sabb 08:00-23:59, dom 08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongDom() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lun-ven 08:00-23:59, sab 08:00-23:59, ddom 08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongTimeLunVen_NoSpace() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lun-ven08:00-23:59, sab 08:00-23:59, dom 08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongTimeSab_NoSpace() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lun-ven 08:00-23:59, sab08:00-23:59, dom 08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongTimeDom_NoSpace() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lun-ven 08:00-23:59, sab 08:00-23:59, dom08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongTimeLunVen() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lun-ven 08:a0-23:59, sab 08:00-23:59, dom 08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongTimeSab() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lun-ven 08:00-23:59, sab 08:00-2b:59, dom 08:00-23:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongTimeDom() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lun-ven 08:00-23:59, sab 08:00-23:59, dom 08:00-2359\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongTime_InvertedDom() throws IOException, BadFileFormatException {
		String fakefile = 
				    "BOLOGNA, Aeroporto Marconi, lun-ven 08:00-23:59, sab 08:00-23:59, dom 15:00-13:59\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongTime_InvertedMattinoPom() throws IOException, BadFileFormatException {
		String fakefile = 
				    "MODENA, centro, lun-ven 08:30-12:00/11:30-18:00, sab chiuso, dom chiuso\\r\\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
}
