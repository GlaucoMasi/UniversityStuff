package autonoleggio.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import autonoleggio.model.Slot;


class SlotTest {

	@Test
	void testOK_OrarioCentrale() {
		var slotMattino1 = new Slot(LocalTime.of(8,0), LocalTime.of(12,30));
		assertEquals(Duration.ofHours(4).plusMinutes(30), slotMattino1.duration());
		assertTrue(slotMattino1.contains(LocalTime.of(10,0)));
	}
	
	@Test
	void testOK_OrarioLimiteIniziale() {
		var slotMattino1 = new Slot(LocalTime.of(8,0), LocalTime.of(12,30));
		assertEquals(Duration.ofHours(4).plusMinutes(30), slotMattino1.duration());
		assertTrue(slotMattino1.contains(LocalTime.of(8,0)));
		assertFalse(slotMattino1.contains(LocalTime.of(8,0).minusMinutes(1)));
	}
	
	@Test
	void testOK_OrarioLimiteFinale() {
		var slotMattino1 = new Slot(LocalTime.of(8,0), LocalTime.of(12,30));
		assertEquals(Duration.ofHours(4).plusMinutes(30), slotMattino1.duration());
		assertTrue(slotMattino1.contains(LocalTime.of(12,30).minusMinutes(1)));
		assertFalse(slotMattino1.contains(LocalTime.of(12,30)));
	}
	
	
	@Test
	void testKO_OrarioInizialeNull() {
		assertThrows(NullPointerException.class, () -> new Slot(null, LocalTime.of(12,30)));
	}
	
	@Test
	void testKO_OrarioFinaleNull() {
		assertThrows(NullPointerException.class, () -> new Slot(LocalTime.of(8,0), null));
	}
	
	@Test
	void testKO_OrarioInvertito() {
		assertThrows(IllegalArgumentException.class, () -> new Slot(LocalTime.of(8,0), LocalTime.of(7,30)));
	}
	

}
