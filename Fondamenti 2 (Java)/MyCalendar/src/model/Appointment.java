package model;

import java.util.Locale;
import java.util.Objects;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.time.format.DateTimeFormatter;

public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;
	private String description;
	private LocalDateTime from, to;
	
	public Appointment(String description, LocalDateTime from, LocalDateTime to) {
		this.description = description;
		this.from = from;
		this.to = to;
	}
	
	public Appointment(String description, LocalDateTime from, Duration duration) {
		this.description = description;
		this.from = from;
		this.to = from.plus(duration);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDateTime getFrom() {
		return from;
	}
	
	public void setFrom(LocalDateTime from) {
		this.from = from;
	}
	
	public LocalDateTime getTo() {
		return to;
	}
	
	public void setTo(LocalDateTime to) {
		this.to = to;
	}
	
	public Duration getDuration() {
		return Duration.between(from, to);
	}
	
	public void setDuration(Duration duration) {
		to = from.plus(duration);
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.ITALY);
		return "Appuntamento da: " + formatter.format(from) + " a " + formatter.format(to) + ": " + description + System.lineSeparator();
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, from, to);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Appointment)) {
			return false;
		}
		Appointment other = (Appointment) obj;
		return Objects.equals(description, other.description) && Objects.equals(from, other.from) && Objects.equals(to, other.to);
	}
}
