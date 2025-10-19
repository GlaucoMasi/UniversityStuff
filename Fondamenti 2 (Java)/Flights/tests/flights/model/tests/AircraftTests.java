package flights.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import flights.model.Aircraft;

public class AircraftTests {
Aircraft aereo;
	
	@BeforeEach
	void setup() {
		aereo = new Aircraft("738", "Boeing 737-800",189);
	}
	

	@Test
	void testGetCode() {
		assertEquals("738", aereo.getCode());
	}

	@Test
	void testGetDescription() {
		assertEquals("Boeing 737-800", aereo.getDescription());
	}

	@Test
	void testGetSeats() {
		assertEquals(189, aereo.getSeats());
	}

	@Test
	void testIllegalArgNPE() {
		assertThrows(IllegalArgumentException.class, () -> new Aircraft("", "Airbus", 100));
		assertThrows(IllegalArgumentException.class, () -> new Aircraft("2345", "", 100));
		assertThrows(IllegalArgumentException.class, () -> new Aircraft("2345", "Airbus", -100));
		assertThrows(NullPointerException.class, () -> new Aircraft(null,"Airbus", 100));
		assertThrows(NullPointerException.class, () -> new Aircraft("2345",null, 100));
	}

}
