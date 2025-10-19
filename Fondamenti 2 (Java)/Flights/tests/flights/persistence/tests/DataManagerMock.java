package flights.persistence.tests;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.City;
import flights.model.FlightSchedule;
import flights.persistence.DataManager;

public class DataManagerMock extends DataManager {
	private Map<String, Airport> airportMap;
	private Map<String, Aircraft> aircraftMap;
	private Collection<FlightSchedule> flightSchedules;

	public DataManagerMock() {
		super(new CitiesReaderMock(), new AircraftsReaderMock(),
				new FlightScheduleReaderMock());

		airportMap = new HashMap<String, Airport>();
		City alf = new City("Alf", "Alfonsine", 2);
		Airport dep = new Airport("Dep", "Departure", alf);
		airportMap.put(dep.getCode(), dep);
		airportMap.put("Dep1", new Airport("Dep1", "Departure 1", alf));
		Airport arr = new Airport("Arr", "Arrival", new City("Can", "Canicattì", 4));
		airportMap.put(arr.getCode(), arr);

		aircraftMap = new HashMap<String, Aircraft>();
		Aircraft lit = new Aircraft("111", "Little", 10);
		aircraftMap.put(lit.getCode(), lit);
		Aircraft big = new Aircraft("999", "Big", 500);
		aircraftMap.put(big.getCode(), big);

		ArrayList<FlightSchedule> schedules = new ArrayList<FlightSchedule>();

		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		FlightSchedule fs = new FlightSchedule("A", dep, LocalTime.of(10, 10), // dep is UTC+2
													arr, LocalTime.of(13, 11), // arr is UTC+4 
													0, days, lit);
		schedules.add(fs);

		days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.TUESDAY);
		fs = new FlightSchedule("B", dep, LocalTime.of(12, 12), arr, LocalTime.of(14, 14), 0, days, big);
		schedules.add(fs);

		days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.WEDNESDAY);
		days.add(DayOfWeek.THURSDAY);
		days.add(DayOfWeek.FRIDAY);
		fs = new FlightSchedule("C", dep, LocalTime.of(13, 13), arr, LocalTime.of(15, 15), 0, days, big);
		schedules.add(fs);

		days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.WEDNESDAY);
		days.add(DayOfWeek.FRIDAY);
		fs = new FlightSchedule("D", dep, LocalTime.of(14, 14), arr, LocalTime.of(16, 16), 0, days, big);
		schedules.add(fs);


		days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.WEDNESDAY);
		days.add(DayOfWeek.SATURDAY);
		days.add(DayOfWeek.FRIDAY);
		fs = new FlightSchedule("E", dep, LocalTime.of(15, 15), arr, LocalTime.of(17, 17), 0, days, big);
		schedules.add(fs);


		days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.FRIDAY);
		fs = new FlightSchedule("F", dep, LocalTime.of(16, 16), arr, LocalTime.of(18, 18), 0, days, big);
		schedules.add(fs);

		days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.SATURDAY);
		fs = new FlightSchedule("G", dep, LocalTime.of(17, 17), arr, LocalTime.of(19, 19), 0, days, big);
		schedules.add(fs);

		flightSchedules = schedules;
	}

	@Override
	public Map<String, Airport> getAirportMap() {
		return airportMap;
	}

	@Override
	public Map<String, Aircraft> getAircraftMap() {
		return aircraftMap;
	}

	@Override
	public Collection<FlightSchedule> getFlightSchedules() {
		return flightSchedules;
	}

	@Override
	public void readAll() {
		
	}

}
