package flights.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import flights.model.Airport;
import flights.model.FlightSchedule;
import flights.persistence.DataManager;

public class MyController implements Controller {
	private DataManager dataManager;
	private List<Airport> sortedAirports;
	
	public MyController(DataManager dataManager) {
		this.dataManager = dataManager;
		sortedAirports = new ArrayList<Airport>(dataManager.getAirportMap().values());
		Comparator<Airport> comp = Comparator.comparing(a -> a.getCity().getName());
		Comparator<Airport> complete = comp.thenComparing(a -> a.getName());
		sortedAirports.sort(complete);
	}

	public List<Airport> getSortedAirports() {
		return sortedAirports;
	}

	@Override
	public List<FlightSchedule> searchFlights(Airport departureAirport, Airport arrivalAirport, LocalDate date) {
		return dataManager.getFlightSchedules().stream().filter(fs -> fs.getDepartureAirport().equals(departureAirport) &&
				fs.getArrivalAirport().equals(arrivalAirport) && fs.getDaysOfWeek().contains(date.getDayOfWeek())).collect(Collectors.toList());
	}

}
