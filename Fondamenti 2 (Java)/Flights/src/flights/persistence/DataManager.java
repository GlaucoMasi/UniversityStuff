package flights.persistence;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.FlightSchedule;

public class DataManager {
	private HashMap<String, Aircraft> aircraftMap;
	private HashMap<String, Airport> airportMap;
	private AircraftsReader aircraftsReader;
	private CitiesReader citiesReader;
	private FlightScheduleReader flightScheduleReader;
	private Collection<FlightSchedule> flightSchedules;
	
	public DataManager(CitiesReader citiesReader, AircraftsReader aircraftsReader,
			FlightScheduleReader flightScheduleReader) {
		Objects.requireNonNull(citiesReader, "Reader città nullo");
		Objects.requireNonNull(aircraftsReader, "Reader aerei nullo");
		Objects.requireNonNull(flightScheduleReader, "Reader voli nullo");
		this.aircraftsReader = aircraftsReader;
		this.citiesReader = citiesReader;
		this.flightScheduleReader = flightScheduleReader;
	}
	
	public void readAll() throws IOException, BadFileFormatException {
		try (Reader reader = new FileReader("Cities.txt")) {
			airportMap = new HashMap<String, Airport>();
			citiesReader.read(reader).forEach(c -> {
				c.getAirports().forEach(a -> airportMap.put(a.getCode(), a));
			});
		}
		
		try (Reader reader = new FileReader("Aircrafts.txt")) {
			aircraftMap = new HashMap<String, Aircraft>();
			aircraftsReader.read(reader).forEach(a -> {
				aircraftMap.put(a.getCode(), a);
			});	
		}
		
		try (Reader reader = new FileReader("FlightSchedule.txt")) {
			flightSchedules = flightScheduleReader.read(reader, airportMap, aircraftMap);
		}
	}

	public Map<String, Aircraft> getAircraftMap() {
		return aircraftMap;
	}

	public Map<String, Airport> getAirportMap() {
		return airportMap;
	}

	public Collection<FlightSchedule> getFlightSchedules() {
		return flightSchedules;
	}
}
