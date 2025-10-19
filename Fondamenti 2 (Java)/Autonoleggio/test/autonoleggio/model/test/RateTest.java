package autonoleggio.model.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import autonoleggio.model.CarType;
import autonoleggio.model.Rate;


class RateTest {
	
	@Test
	void testOK1() {
		@SuppressWarnings("unused")
		var rate = new Rate(CarType.A, 45.50, 80.30, 100.0);
	}
	
	@Test
	void testOK_ZeroDropoffCharge() {
		@SuppressWarnings("unused")
		var rate = new Rate(CarType.A, 45.50, 80.30, 0);
	}
	
	@Test
	void testKO_NullCarType() {
		assertThrows(NullPointerException.class, () -> new Rate(null, 45.50, 80.30, 100.0));
	}

	@Test
	void testKO_WrongDailyRate() {
		assertThrows(IllegalArgumentException.class, () -> new Rate(CarType.A, -45.50, 80.30, 100.0));
	}

	@Test
	void testKO_WrongWeekendRate() {
		assertThrows(IllegalArgumentException.class, () -> new Rate(CarType.A, 45.50, -80.30, 100.0));
	}

	@Test
	void testKO_WrongDropoffCharge() {
		assertThrows(IllegalArgumentException.class, () -> new Rate(CarType.A, 45.50, 80.30, -100.0));
	}
	
	@Test
	void testKO_ZeroDailyRate() {
		assertThrows(IllegalArgumentException.class, () -> new Rate(CarType.A, 0, 80.30, 100.0));
	}

	@Test
	void testKO_ZeroWeekendRate() {
		assertThrows(IllegalArgumentException.class, () -> new Rate(CarType.A, 45.50, 0, 100.0));
	}

}
