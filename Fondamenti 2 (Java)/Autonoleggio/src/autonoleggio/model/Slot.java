package autonoleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public record Slot(LocalTime from, LocalTime to) {
	
	public Slot {
		Objects.requireNonNull(from, "L'orario iniziale non può essere nullo");
		Objects.requireNonNull(to,   "L'orario finale non può essere nullo");
		if (!to.isAfter(from)) throw new IllegalArgumentException("Apertura deve precedere chiusura");
	}
	
	public Duration duration() {
		return Duration.between(from, to);
	}
	
	@Override
	public String toString() {
		return Formatters.timeFormatter.format(from) + "-" + Formatters.timeFormatter.format(to);
	}
	
	public boolean contains(LocalTime time) {
		return !time.isBefore(from) && time.isBefore(to); 
	}
}

