package flights.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flights.model.Airport;
import flights.model.City;

public class CityTests {
City city;

@BeforeEach
void setup() {
	city=  new City("LON", "London", 1);
	city.getAirports().add(new Airport("LHR", "Heathrow",city));
	city.getAirports().add(new Airport("LGW", "Gatwick",city));
	city.getAirports().add(new Airport("STN", "Stansted",city));
}
@Test
void testGetCode() {
	assertEquals("LON", city.getCode());
}

@Test
void testGetName() {
	assertEquals("London", city.getName());
}

@Test
void testGetTimeZone() {
	assertEquals(1, city.getTimeZone());
}

@Test
void testGetAirports() {
	assertEquals(3, city.getAirports().size());
}

@Test
void testIllegalArgNPE() {
	assertThrows(IllegalArgumentException.class, () -> new City("", "London", 1));
	assertThrows(IllegalArgumentException.class, () -> new City("LON", "", 1));
	assertThrows(IllegalArgumentException.class, () -> new City("LON", "London", 15));
	assertThrows(IllegalArgumentException.class, () -> new City("LON", "London", -13));
	assertThrows(NullPointerException.class, () -> new City("LON",null,1));
	assertThrows(NullPointerException.class, () -> new City(null,"London", 1));
}
}
