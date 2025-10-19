package trainstats.model;

import java.util.Optional;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Objects;

public class Recording {
	
	private static final String UNDEFINED_TIME = " --- ";
	private Optional<LocalTime> scheduledArrival, actualArrival, scheduledDeparture, actualDeparture;
	private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
	private String station;

	public Recording(String station, Optional<LocalTime> scheduledArrival, Optional<LocalTime> actualArrival, 
									 Optional<LocalTime> scheduledDeparture, Optional<LocalTime> actualDeparture) {
		if(station == null) throw new IllegalArgumentException("La stazione è nulla");
		if(station.isBlank()) throw new IllegalArgumentException("La stazione è vuota");
		if(scheduledDeparture == null) throw new IllegalArgumentException("L'orario di partenza previsto è nullo");
		if(actualDeparture == null) throw new IllegalArgumentException("L'orario di partenza effettivo è nullo");
		if(scheduledArrival == null) throw new IllegalArgumentException("L'orario di arrivo previsto è nullo");
		if(actualArrival == null) throw new IllegalArgumentException("L'orario di arrivo effettivo è nullo");
		
		if(scheduledArrival.isPresent() != actualArrival.isPresent()) 
			throw new IllegalArgumentException("Uso illegale di optional empty negli arrivi");
		
		if(scheduledDeparture.isPresent() != actualDeparture.isPresent()) 
			throw new IllegalArgumentException("Uso illegale di optional empty nelle partenze");
		
		if(scheduledArrival.isEmpty() && scheduledDeparture.isEmpty()) 
			throw new IllegalArgumentException("Optional empty sia in partenze che in arrivi");
		
		if(scheduledDeparture.isPresent() && scheduledArrival.isPresent() && scheduledDeparture.get().isBefore(scheduledArrival.get())) 
			throw new IllegalArgumentException("Partenza prevista antecendete ad arrivo previsto");
		
		if(actualDeparture.isPresent() && scheduledArrival.isPresent() && actualDeparture.get().isBefore(scheduledArrival.get())) 
			throw new IllegalArgumentException("Partenza effettiva antecendete ad arrivo previsto");
		
		if(actualDeparture.isPresent() && actualArrival.isPresent() && actualDeparture.get().isBefore(actualArrival.get())) 
			throw new IllegalArgumentException("Partenza effettiva antecendete ad arrivo effettivo");
		
		this.scheduledArrival = scheduledArrival;
		this.actualArrival = actualArrival;
		this.scheduledDeparture = scheduledDeparture;
		this.actualDeparture = actualDeparture;
		this.station=station;
	}

	public String getStation() {
		return station;
	}

	public Optional<LocalTime> getScheduledArrivalTime() {
		return scheduledArrival;
	}

	public Optional<LocalTime> getActualArrivalTime() {
		return actualArrival;
	}

	public Optional<LocalTime> getScheduledDepartureTime() {
		return scheduledDeparture;
	}

	public Optional<LocalTime> getActualDepartureTime() {
		return actualDeparture;
	}

	private String formatTime(Optional<LocalTime> time) {
		return time.isEmpty() ? UNDEFINED_TIME : formatter.format(time.get());
	}
	
	@Override
	public String toString() {
		return 	String.format("%-20s", station) + "\t" + 
				formatTime(scheduledArrival) + "\t" + formatTime(actualArrival) + "\t" +
				formatTime(scheduledDeparture) + "\t" + formatTime(actualDeparture);
	}

	@Override
	public int hashCode() {
		return Objects.hash(station, scheduledArrival, actualArrival, scheduledDeparture, actualDeparture);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Recording other = (Recording) obj;
		return  Objects.equals(station, other.station) &&
				Objects.equals(scheduledArrival, other.scheduledArrival) && Objects.equals(actualArrival, other.actualArrival) &&
				Objects.equals(scheduledDeparture, other.scheduledDeparture) && Objects.equals(actualDeparture, other.actualDeparture);
	}	
	
}
