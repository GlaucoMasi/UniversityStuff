package autonoleggio.model;

import java.util.Objects;

public record Rate(CarType carType, double dailyRate, double weekendRate, double dropoffCharge) {
	
	public Rate {
		Objects.requireNonNull(carType, "CarType non può essere nullo");
		if (dailyRate<=0) throw new IllegalArgumentException("La tariffa giornaliera non può essere zero o negativa");
		if (weekendRate<=0) throw new IllegalArgumentException("La tariffa weekend non può essere zero o negativa");
		if (dropoffCharge<0) throw new IllegalArgumentException("La tariffa di dropoff non può essere negativa"); // ma può essere zero
	}
	
}
