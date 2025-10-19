package autonoleggio.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

import autonoleggio.model.OpeningTime;
import autonoleggio.model.Slot;


class OpeningTimeTest {
	
	@Test
	void testOK() {
		var slotMattino    = new Slot(LocalTime.of(8,0), LocalTime.of(12,30));
		var slotPomeriggio = new Slot(LocalTime.of(14,30), LocalTime.of(18,30));
		var slotUnico      = new Slot(LocalTime.of(8,30), LocalTime.of(23,59));
		
		assertEquals(Duration.ofHours(4).plusMinutes(30), slotMattino.duration());
		assertEquals(Duration.ofHours(4), slotPomeriggio.duration());
		assertEquals(Duration.ofHours(15).plusMinutes(29), slotUnico.duration());
		
		var openingTimeA = new OpeningTime(slotMattino);
		var openingTimeB = new OpeningTime(slotMattino, slotPomeriggio);
		var openingTimeC = new OpeningTime(slotUnico);
		
		assertTrue(openingTimeA.isOpenAt(LocalTime.of(8,30)));
		assertTrue(openingTimeA.isOpenAt(LocalTime.of(12,30).minusMinutes(1)));
		assertFalse(openingTimeA.isOpenAt(LocalTime.of(12,30)));
		
		assertTrue(openingTimeB.isOpenAt(LocalTime.of(14,30)));
		assertTrue(openingTimeB.isOpenAt(LocalTime.of(18,30).minusMinutes(1)));
		assertFalse(openingTimeB.isOpenAt(LocalTime.of(18,30)));

		assertTrue(openingTimeC.isOpenAt(LocalTime.of(8,30)));
		assertTrue(openingTimeC.isOpenAt(LocalTime.of(23,59).minusMinutes(1)));
		assertFalse(openingTimeC.isOpenAt(LocalTime.of(23,59)));
	}
	
	@Test
	void testKO_SlotUnicoNull() {
		assertThrows(NullPointerException.class, () -> new OpeningTime(null));
	}
	
	@Test
	void testKO_SlotMattinoNull() {
		var slotPomeriggio = new Slot(LocalTime.of(14,30), LocalTime.of(18,30));
		assertThrows(NullPointerException.class, () -> new OpeningTime(null, slotPomeriggio));
	}
	
	@Test
	void testKO_SlotPomeriggioNull() {
		var slotMattino    = new Slot(LocalTime.of(8,0), LocalTime.of(12,30));
		assertThrows(NullPointerException.class, () -> new OpeningTime(slotMattino,null));
	}

}
	