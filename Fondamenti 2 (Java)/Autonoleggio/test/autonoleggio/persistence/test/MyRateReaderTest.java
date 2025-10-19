package autonoleggio.persistence.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

import autonoleggio.persistence.BadFileFormatException;
import autonoleggio.persistence.MyAgencyReader;
import autonoleggio.persistence.MyRateReader;


public class MyRateReaderTest {

	@Test
	void testOK() throws IOException, BadFileFormatException {
		String fakefile = 
				      "A: daily=45\u00A0€, weekend=87\u00A0€, dropoff=100\u00A0€\r\n"
				    + "B: daily=51\u00A0€, weekend=97\u00A0€, dropoff=120\u00A0€\r\n"
				    + "C: daily=58\u00A0€, weekend=115\u00A0€, dropoff=140\u00A0€\r\n"
				    + "D: daily=66\u00A0€, weekend=121\u00A0€, dropoff=170\u00A0€";
		var rates = new MyRateReader().readAllRates(new StringReader(fakefile));
		assertEquals(4, rates.size());
	}
	
	@Test
	void testKO_NumItemsNon4_MissingComma() throws IOException, BadFileFormatException {
		String fakefile = 
				    "A: daily 45€ weekend 87€, dropoff 100€\\r\\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumItemsNon4_ExtraComma() throws IOException, BadFileFormatException {
		String fakefile = 
				    "A: daily 45€, weekend 87€ dropoff 100€\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumItemsNon4_NoSemiColon() throws IOException, BadFileFormatException {
		String fakefile = 
				    "A daily 45€, weekend 87€, dropoff 100€\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_NumItemsNon4_MissingElement() throws IOException, BadFileFormatException {
		String fakefile = 
				    "R: daily 45€, weekend 87€, dropoff 100€\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongCarType() throws IOException, BadFileFormatException {
		String fakefile = 
			    "A: daily 45€, weekend 87€, dropoff 100€\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongDaily() throws IOException, BadFileFormatException {
		String fakefile = 
			    "A: abcde 45€, weekend 87€, dropoff 100€\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongWeekend() throws IOException, BadFileFormatException {
		String fakefile = 
			    "A: daily 45€, 87€, dropoff 100€\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongDropoff() throws IOException, BadFileFormatException {
		String fakefile = 
			    "A: daily 45€, weekend 87€, dropof 100€\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongDropOff_NoSpace() throws IOException, BadFileFormatException {
		String fakefile = 
			    "A: daily 45€, weekend 87€, dropoff100€\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongWeekend_NoEuroSign() throws IOException, BadFileFormatException {
		String fakefile = 
			    "A: daily 45€, weekend 87, dropoff 100€\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongRate_Neg() throws IOException, BadFileFormatException {
		String fakefile = 
			    "A: daily -45€, weekend 87€, dropoff 100€\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
	@Test
	void testKO_WrongRate_Wrong() throws IOException, BadFileFormatException {
		String fakefile = 
			    "A: daily 4a5€, weekend 87€, dropoff 100€\r\n";
		assertThrows(BadFileFormatException.class, () -> new MyAgencyReader().readAllAgencies(new StringReader(fakefile)));
	}
	
}
