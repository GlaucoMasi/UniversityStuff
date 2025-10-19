package autonoleggio.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import autonoleggio.model.Agency;
import autonoleggio.model.OpeningTime;
import autonoleggio.model.Slot;


class AgencyTest {
	
	static OpeningTime openingTimeSoloMattino0800_1230, openingTimeOrarioContinuato0830_2359, openingTimeMattinoEPomeriggio0800_1230_1430_1830;
	
	@BeforeAll
	static void setup() {
		var slotMattino0800_1230    = new Slot(LocalTime.of(8,0), LocalTime.of(12,30));
		var slotPomeriggio1430_1830 = new Slot(LocalTime.of(14,30), LocalTime.of(18,30));
		var slotUnico0830_2359      = new Slot(LocalTime.of(8,30), LocalTime.of(23,59));
		openingTimeSoloMattino0800_1230 = new OpeningTime(slotMattino0800_1230);
		openingTimeOrarioContinuato0830_2359 = new OpeningTime(slotUnico0830_2359);
		openingTimeMattinoEPomeriggio0800_1230_1430_1830 = new OpeningTime(slotMattino0800_1230, slotPomeriggio1430_1830);
	}

	@Test
	void testOK1() {
		@SuppressWarnings("unused")
		var agency = new Agency("Bologna", "centro", openingTimeOrarioContinuato0830_2359, openingTimeSoloMattino0800_1230, OpeningTime.CHIUSO);
	}
	
	@Test
	void testOK2() {
		@SuppressWarnings("unused")
		var agency = new Agency("Bologna", "aeroporto", openingTimeMattinoEPomeriggio0800_1230_1430_1830, openingTimeMattinoEPomeriggio0800_1230_1430_1830, openingTimeMattinoEPomeriggio0800_1230_1430_1830);
	}
	
	@Test
	void testOK3() {
		@SuppressWarnings("unused")
		var agency = new Agency("Bologna", "centro", openingTimeMattinoEPomeriggio0800_1230_1430_1830, OpeningTime.CHIUSO, OpeningTime.CHIUSO);
	}
	
	@Test
	void testOK_IsOpenAt() {
		var agency = new Agency("Bologna", "centro", openingTimeOrarioContinuato0830_2359, openingTimeSoloMattino0800_1230, OpeningTime.CHIUSO);
		assertTrue( agency.isOpenAt(LocalDateTime.of(2024,12,17, 9,00))); // un martedì
		assertTrue( agency.isOpenAt(LocalDateTime.of(2024,12,21, 9,00))); // un sabato
		assertFalse(agency.isOpenAt(LocalDateTime.of(2024,12,22, 9,00))); // una domenica
		assertFalse(agency.isOpenAt(LocalDateTime.of(2024,12,21,13,30))); // un sabato
		assertFalse(agency.isOpenAt(LocalDateTime.of(2024,12,17, 8,00))); // un martedì
	}
	
	@Test
	void testKO_CityBlank() {
		assertThrows(IllegalArgumentException.class, () -> new Agency("", "centro", openingTimeMattinoEPomeriggio0800_1230_1430_1830, OpeningTime.CHIUSO, OpeningTime.CHIUSO));
	}

	@Test
	void testKO_AgencyNameBlank() {
		assertThrows(IllegalArgumentException.class, () -> new Agency("Bologna", "", openingTimeMattinoEPomeriggio0800_1230_1430_1830, OpeningTime.CHIUSO, OpeningTime.CHIUSO));
	}
	
	@Test
	void testKO_NullCity() {
		assertThrows(NullPointerException.class, () -> new Agency(null, "centro", openingTimeMattinoEPomeriggio0800_1230_1430_1830, OpeningTime.CHIUSO, OpeningTime.CHIUSO));
	}

	@Test
	void testKO_NullAgency() {
		assertThrows(NullPointerException.class, () -> new Agency("Bologna", null, openingTimeMattinoEPomeriggio0800_1230_1430_1830, OpeningTime.CHIUSO, OpeningTime.CHIUSO));
	}

	@Test
	void testKO_NullOpeningTimeMonFri() {
		assertThrows(NullPointerException.class, () -> new Agency("Bologna", "centro", null, OpeningTime.CHIUSO, OpeningTime.CHIUSO));
	}

	@Test
	void testKO_NullOpeningTimeSat() {
		assertThrows(NullPointerException.class, () -> new Agency("Bologna", "centro", openingTimeMattinoEPomeriggio0800_1230_1430_1830, null, OpeningTime.CHIUSO));
	}

	@Test
	void testKO_NullOpeningTimeSun() {
		assertThrows(NullPointerException.class, () -> new Agency("Bologna", "centro", openingTimeMattinoEPomeriggio0800_1230_1430_1830, OpeningTime.CHIUSO, null));
	}

}
