package flights.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FlightSchedule {
	private Aircraft aircraft;
	private Airport departureAirport, arrivalAirport;
	private LocalTime departureLocalTime, arrivalLocalTime;
	private String code;
	private int dayOffset;
	private Set<DayOfWeek> daysOfWeek;
	
	private OffsetDateTime departure, arrival;
	
	public FlightSchedule(String code, Airport departureAirport, LocalTime departureLocalTime, Airport arrivalAirport, LocalTime arrivalLocalTime, 
			int dayOffset, Collection<DayOfWeek> daysOfWeek, Aircraft aircraft) {
		Objects.requireNonNull(code, "La stringa codice non può essere nulla");
		Objects.requireNonNull(departureLocalTime, "Il tempo di partenza non può essere nullo");
		Objects.requireNonNull(departureAirport, "L'aeroporto di partenza non può essere nullo");
		Objects.requireNonNull(arrivalLocalTime, "Il tempo di arrivo non può essere nullo");
		Objects.requireNonNull(arrivalAirport, "L'aeroporto di arrivo non può essere nullo");
		Objects.requireNonNull(daysOfWeek, "Il set dei giorni non può essere nullo");
		Objects.requireNonNull(aircraft, "L'aereo non può essere nullo");
		if(code.isBlank()) throw new IllegalArgumentException("Il codice non può essere vuoto");
		if(daysOfWeek.isEmpty()) throw new IllegalArgumentException("Il set dei giorni non può essere vuoto");
		if(dayOffset < -1 || dayOffset > 1) throw new IllegalArgumentException("L'offset deve essere tra -1 e 1");
		
		this.aircraft = aircraft;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureLocalTime = departureLocalTime;
		this.arrivalLocalTime = arrivalLocalTime;
		this.code = code;
		this.dayOffset = dayOffset;
		this.daysOfWeek = new HashSet<DayOfWeek>(daysOfWeek);
		
		departure = OffsetDateTime.of(LocalDate.now(), getDepartureLocalTime(), 
				ZoneOffset.ofHours(getDepartureAirport().getCity().getTimeZone()));
		
		arrival = OffsetDateTime.of(LocalDate.now().plusDays(getDayOffset()), getArrivalLocalTime(), 
				ZoneOffset.ofHours(getArrivalAirport().getCity().getTimeZone()));
		
		if(departure.isAfter(arrival)) throw new IllegalArgumentException("L'arrivo è precedente alla partenza");
	}
	
	public Duration getFlightDuration() {
		Duration temp = Duration.between(departure, arrival);
		if(temp.isNegative()) temp = temp.plusDays(1);
		
		return temp;
	}

	public Aircraft getAircraft() {
		return aircraft;
	}

	public Airport getDepartureAirport() {
		return departureAirport;
	}

	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	public LocalTime getDepartureLocalTime() {
		return departureLocalTime;
	}

	public LocalTime getArrivalLocalTime() {
		return arrivalLocalTime;
	}

	public String getCode() {
		return code;
	}

	public int getDayOffset() {
		return dayOffset;
	}

	public Set<DayOfWeek> getDaysOfWeek() {
		return daysOfWeek;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aircraft, arrivalAirport, arrivalLocalTime, code, dayOffset, daysOfWeek, departureAirport,
				departureLocalTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightSchedule other = (FlightSchedule) obj;
		return Objects.equals(aircraft, other.aircraft) && Objects.equals(arrivalAirport, other.arrivalAirport)
				&& Objects.equals(arrivalLocalTime, other.arrivalLocalTime) && Objects.equals(code, other.code)
				&& dayOffset == other.dayOffset && Objects.equals(daysOfWeek, other.daysOfWeek)
				&& Objects.equals(departureAirport, other.departureAirport)
				&& Objects.equals(departureLocalTime, other.departureLocalTime);
	}
	
	
	
}
