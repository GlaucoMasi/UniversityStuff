package flights.model.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.City;
import flights.model.FlightSchedule;

public class FlightScheduleTests {
	
	
	@Test
	public void testFlightScheduleCtorAndGetters() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf", "Alfonsine", 1));
		Airport arr = new Airport("Arr", "Arrival", new City("Can", "Canicattì", 1));
		LocalTime depTime = LocalTime.of(10, 12);
		LocalTime arrTime = LocalTime.of(12, 40);
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, 0, days, air);

		assertEquals("A", fs.getCode());
		assertSame(dep, fs.getDepartureAirport());
		assertSame(arr, fs.getArrivalAirport());
		assertEquals(0, fs.getDayOffset());
		assertEquals(1, fs.getDaysOfWeek().size());
		assertTrue(fs.getDaysOfWeek().contains(DayOfWeek.MONDAY));
		assertSame(air, fs.getAircraft());
	}

	@Test
	public void testGetFlightDuration_SameTimeZone() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf", "Alfonsine", 1));
		Airport arr = new Airport("Arr", "Arrival", new City("Can", "Canicatt�", 1));
		LocalTime depTime = LocalTime.of(10, 12);
		LocalTime arrTime = LocalTime.of(12, 40);
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, 0, days, air);
		assertEquals(Duration.ofMinutes(148), fs.getFlightDuration());
	}

	@Test
	public void testGetFlightDuration_WestBound() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf", "Alfonsine", 2));
		Airport arr = new Airport("Arr", "Arrival", new City("Can", "Canicattì", 4));
		LocalTime depTime = LocalTime.of(17, 30); // 17:30 UTC+2 = 15:30 UTC
		LocalTime arrTime = LocalTime.of( 2, 00); // 02:00 UTC+4 = 22:00 UTC -> durata = 6:30
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, 1, days, air);
		assertEquals(Duration.ofMinutes(6*60+30), fs.getFlightDuration());
	}

	@Test
	public void testGetFlightDuration_EastBound() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf", "Alfonsine", 2));
		Airport arr = new Airport("Arr", "Arrival", new City("Can", "Canicattì", -4));
		LocalTime depTime = LocalTime.of(10, 00); // 10.00 UTC+2 = 08:00 UTC
		LocalTime arrTime = LocalTime.of(13, 55); // 13:55 UTC-4 = 17:55 UTC -> durata = 9:55 
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, 0, days, air);
		assertEquals(Duration.ofMinutes(9*60+55), fs.getFlightDuration());
	}

	@Test
	public void testGetFlightDuration_WestBound_CrossDayChangeLine() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf", "Alfonsine", 9));
		Airport arr = new Airport("Arr", "Arrival", new City("Can", "Canicattì", -11));
		LocalTime depTime = LocalTime.of(12, 10);  // 12:10 UTC+9 = 03:10 UTC (d)
		LocalTime arrTime = LocalTime.of(20, 15);  // 20:15 UTC-11(d-1) = 7:15 UTC (d)
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, -1, days, air);
		assertEquals(Duration.ofMinutes(4*60+5), fs.getFlightDuration());
	}

	@Test
	public void testGetFlightDuration_EastBound_CrossDayChangeLine() {
		Airport dep = new Airport("Dep", "Departure", new City("Can", "Canicattì", -7));
		Airport arr = new Airport("Arr", "Arrival", new City("Oce", "Isoletta", 10));
		LocalTime depTime = LocalTime.of(5, 20); // 05:20 UTC-7 = 12:20 UTC (d)
		LocalTime arrTime = LocalTime.of(7, 45); // 07:45 UTC+10 (d+1) = 21:45 UTC (d)
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		FlightSchedule fs = new FlightSchedule("A", dep, depTime, arr, arrTime, 1, days, air);
		assertEquals(Duration.ofMinutes(9*60+25), fs.getFlightDuration());
	}
	
	@Test
	void testIllegalArgNPE() {
		Airport dep = new Airport("Dep", "Departure", new City("Alf", "Alfonsine", -7));
		Airport arr = new Airport("Arr", "Arrival", new City("Can", "Canicattì", 10));
		LocalTime depTime = LocalTime.of(23, 20);
		LocalTime arrTime = LocalTime.of(7, 45);
		ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
		days.add(DayOfWeek.MONDAY);
		Aircraft air = new Aircraft("Fok", "Fokker", 15);

		assertThrows(NullPointerException.class, () -> new FlightSchedule(null, dep, depTime, arr, arrTime, 1, days, air));
		assertThrows(NullPointerException.class, () -> new FlightSchedule("A", null, depTime, arr, arrTime, 1, days, air));
		assertThrows(NullPointerException.class, () -> new FlightSchedule("A", dep, null, arr, arrTime, 1, days, air));
		assertThrows(NullPointerException.class, () -> new FlightSchedule("A", dep, depTime, null, arrTime, 1, days, air));
		assertThrows(NullPointerException.class, () -> new FlightSchedule("A", dep, depTime, arr, null, 1, days, air));
		assertThrows(NullPointerException.class, () -> new FlightSchedule("A", dep, depTime, arr, arrTime, 1, null, air));
		assertThrows(NullPointerException.class, () -> new FlightSchedule("A", dep, depTime, arr, arrTime, 1, days, null));

		assertThrows(IllegalArgumentException.class, () -> new FlightSchedule("", dep, depTime, arr, arrTime, 1, days, air));
		assertThrows(IllegalArgumentException.class, () -> new FlightSchedule("A", dep, depTime, arr, arrTime, -2, days, air));
		assertThrows(IllegalArgumentException.class, () -> new FlightSchedule("A", dep, depTime, arr, arrTime, 2, days, air));
		assertThrows(IllegalArgumentException.class, () -> new FlightSchedule("A", dep, depTime, arr, arrTime, 1, new ArrayList<DayOfWeek>(), air));
	}

}
