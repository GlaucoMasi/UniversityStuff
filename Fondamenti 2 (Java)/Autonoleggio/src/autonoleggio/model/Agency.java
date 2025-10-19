package autonoleggio.model;

import java.util.Objects;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public record Agency(String city, String agencyName, OpeningTime openingTimeMonFri, OpeningTime openingTimeSat, OpeningTime openingTimeSun) implements Comparable<Agency> {
	public Agency{
		Objects.requireNonNull(city, "Il nome della città non può essere nullo");
		if(city.isBlank()) throw new IllegalArgumentException("Il nome della città non può essere vuoto");
		Objects.requireNonNull(agencyName, "Il nome dell'agenzia non può essere nullo");
		if(agencyName.isBlank()) throw new IllegalArgumentException("Il nome dell'agenzia non può essere vuoto");
		Objects.requireNonNull(openingTimeMonFri, "L'orario Mon-Fri non può essere nullo");
		Objects.requireNonNull(openingTimeSat, "L'orario Sat non può essere nullo");
		Objects.requireNonNull(openingTimeSun, "L'orario Sun non può essere nullo");
	}

	@Override
	public int compareTo(Agency that) {
		if(this.city().equals(that.city())) return this.agencyName().compareTo(that.agencyName());
		return this.city().compareTo(that.city());
	}
	
	public boolean isOpenAt(LocalDateTime dateTime) {
		return switch(dateTime.getDayOfWeek()) {
			case DayOfWeek.SATURDAY -> openingTimeSat.isOpenAt(dateTime.toLocalTime());
			case DayOfWeek.SUNDAY -> openingTimeSun.isOpenAt(dateTime.toLocalTime());
			default -> openingTimeMonFri.isOpenAt(dateTime.toLocalTime());
		};
	}
	
	public String toString() {
		return "Agenzia " + this.agencyName() + ", " + this.city();
	}
}
