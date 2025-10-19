package flights.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import flights.model.Airport;
import flights.model.City;

public class AirportTests {
	Airport aer;

	@BeforeEach
	void setup() {
		aer= new Airport("LHR", "Heathrow", new City("LON", "London", 1));
	}
	@Test
	void testGetCode() {
		assertEquals("LHR", aer.getCode());
	}

	@Test
	void testGetName() {
		assertEquals("Heathrow", aer.getName());
	}

	@Test
	void testGetCity() {
		assertEquals("London", aer.getCity().getName());
	}

	@Test
	void testIllegalArgNPE() {
		assertThrows(IllegalArgumentException.class, () -> new Airport("", "Heathrow", new City("LON", "London", 1)));
		assertThrows(IllegalArgumentException.class, () -> new Airport("LHR", "", new City("LON", "London", 1)));
		
		assertThrows(NullPointerException.class, () -> new Airport(null, "Heathrow", new City("LON", "London", 1)));
		assertThrows(NullPointerException.class, () -> new Airport("LHR",null, new City("LON", "London", 1)));
		assertThrows(NullPointerException.class, () -> new Airport("LHR","Heathrow", null));
	}
	
}
