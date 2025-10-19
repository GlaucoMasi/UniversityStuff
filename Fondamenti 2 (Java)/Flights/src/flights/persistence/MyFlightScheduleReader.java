package flights.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.FlightSchedule;

public class MyFlightScheduleReader implements FlightScheduleReader {
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALIAN);
	
	private Collection<DayOfWeek> readDaysOfWeek(String days) throws BadFileFormatException{
		if(days.length() != 7) throw new BadFileFormatException("Stringa dei giorni invalida: " + days);
		
		ArrayList<DayOfWeek> ans = new ArrayList<>();
		for(int i = 0; i < 7; i++) {
			if(days.charAt(i) == '-') continue;
			if(days.charAt(i) != '1'+i) throw new BadFileFormatException("Stringa dei giorni malformata: " + days);
			ans.add(DayOfWeek.of(i+1));
		}
		
		return ans;
	}
	
	private FlightSchedule readSchedule(BufferedReader reader, Map<String, Airport> airportMap, Map<String, Aircraft> aircraftMap) throws BadFileFormatException, IOException {
		String s = reader.readLine();
		if(s == null || s.isBlank()) return null;
		
		String[] tokens = s.split(",");
		if(tokens.length != 8) throw new BadFileFormatException("Riga malformata: " + s);
		
		int dayOffset;
		Aircraft aircraft;
		String code = tokens[0];
		Collection<DayOfWeek> daysOfWeek = readDaysOfWeek(tokens[6]);

		if(!airportMap.containsKey(tokens[1])) throw new BadFileFormatException("Aeroporto di partenza inesistente: " + tokens[1]);
		if(!airportMap.containsKey(tokens[3])) throw new BadFileFormatException("Aeroporto di arrivo inesistente: " + tokens[3]);
		Airport departureAirport = airportMap.get(tokens[1]), arrivalAirport = airportMap.get(tokens[3]);
		
		LocalTime departureLocalTime, arrivalLocalTime;
		try {
			departureLocalTime = LocalTime.parse(tokens[2], timeFormatter);
			arrivalLocalTime = LocalTime.parse(tokens[4], timeFormatter);
		} catch(DateTimeParseException e) {
			throw new BadFileFormatException("Data di partenza o di arrivo invalida: " + tokens[2] + " " + tokens[4]);
		}
		
		try {
			dayOffset = Integer.parseInt(tokens[5]);
		} catch(NumberFormatException e) {
			throw new BadFileFormatException("Offset di giorni invalido: " + tokens[5]);
		}
		
		if(!aircraftMap.containsKey(tokens[7])) throw new BadFileFormatException("Aeroplano inesistente: " + tokens[7]);
		aircraft = aircraftMap.get(tokens[7]);
		
		try {
			return new FlightSchedule(code, departureAirport, departureLocalTime, arrivalAirport, arrivalLocalTime, dayOffset, daysOfWeek, aircraft);
		} catch (IllegalArgumentException e) {
			throw new BadFileFormatException(e);
		}
	}
	
	@Override
	public Collection<FlightSchedule> read(Reader reader, Map<String, Airport> airportMap,
			Map<String, Aircraft> aircraftMap) throws IOException, BadFileFormatException {
		
		Objects.requireNonNull(reader, "Il reader fornito non può essere nullo");
		Objects.requireNonNull(airportMap, "La mappa degli aeroporti fornita non può essere nulla");
		Objects.requireNonNull(aircraftMap, "La mappa degli aeroplani fornita non può essere nulla");
		
		FlightSchedule curr;
		BufferedReader br = new BufferedReader(reader);
		ArrayList<FlightSchedule> ans = new ArrayList<>();
		while((curr = readSchedule(br, airportMap, aircraftMap)) != null) ans.add(curr);
		return ans;
	}

}
