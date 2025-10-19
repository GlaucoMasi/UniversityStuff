package autonoleggio.controller.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import autonoleggio.controller.Controller;
import autonoleggio.model.Agency;
import autonoleggio.model.CarType;
import autonoleggio.model.OpeningTime;
import autonoleggio.model.Rate;
import autonoleggio.model.Slot;


class ControllerTest {
	
	private static Set<Agency> agencies;
	private static TreeMap<CarType,Rate> rates;
	private static Agency agency1, agency2, agency3;
	private static LocalDateTime friMorning, friEvening, sunEvening, nextMonMorning, nextWedEvening, satEvening;
	private static Rate rateA, rateB, rateC, rateD;

	@BeforeAll
	static void setup() {
		var slotMattino    = new Slot(LocalTime.of(8,0), LocalTime.of(12,30));
		var slotPomeriggio = new Slot(LocalTime.of(14,30), LocalTime.of(18,30));
		var slotUnico      = new Slot(LocalTime.of(8,30), LocalTime.of(23,59));
		var openingTimeSoloMattino = new OpeningTime(slotMattino);
		var openingTimeOrarioContinuato = new OpeningTime(slotUnico);
		var openingTimeMattinoEPomeriggio = new OpeningTime(slotMattino, slotPomeriggio);
		
		agency1 = new Agency("Bologna", "centro",   openingTimeOrarioContinuato, openingTimeSoloMattino, OpeningTime.CHIUSO);
		agency2 = new Agency("Bologna", "aeroporto", openingTimeMattinoEPomeriggio, openingTimeMattinoEPomeriggio, openingTimeMattinoEPomeriggio);
		agency3 = new Agency("Modena",  "centro",   openingTimeMattinoEPomeriggio, OpeningTime.CHIUSO, OpeningTime.CHIUSO);
		agencies = Set.of(agency1,agency2,agency3);

		rateA = new Rate(CarType.A, 45.50, 80.30,  100.0);
		rateB = new Rate(CarType.B, 51.66, 93.30,  120.0);
		rateC = new Rate(CarType.C, 57.77, 110.00, 140.0);
		rateD = new Rate(CarType.D, 78.80, 158.00, 180.0);
		
		rates = new TreeMap<>(Map.of(CarType.A, rateA, CarType.B, rateB, CarType.C, rateC, CarType.D, rateD));
		
		friMorning = LocalDateTime.of(2024,12,13,11,30);
		friEvening = LocalDateTime.of(2024,12,13,18,30);
		satEvening = LocalDateTime.of(2024,12,14,16,30);
		sunEvening = LocalDateTime.of(2024,12,15,17,30);		
		nextMonMorning = LocalDateTime.of(2024,12,16,10,30);
		nextWedEvening = LocalDateTime.of(2024,12,18,18,30);
	}
	
	@Test 
	void testOK_Ctor() {
		var controller = new Controller(agencies, rates);
		assertSame(agencies, controller.getAgencies());
		assertSame(rates, controller.getRates());
		assertEquals(List.of("Bologna","Modena"), controller.getCities());
		assertEquals(List.of(agency2, agency1), controller.getAgencies("Bologna"));
		assertEquals(List.of(agency2, agency1), controller.getAgencies("bolOGna"));
		assertEquals(List.of(agency3), controller.getAgencies("Modena"));
		assertEquals(List.of(agency3), controller.getAgencies("MODENa"));
	}
	
	@Test 
	void testOK_CalcolaTariffaADaily_1day() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateA.dailyRate(), controller.calcolaTariffa("MILANO", agency1, friMorning, friEvening, CarType.A, false).importo(), 0.01);
	}

	@Test 
	void testOK_CalcolaTariffaBDaily_1day() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateB.dailyRate(), controller.calcolaTariffa("MILANO", agency1, friMorning, friEvening, CarType.B, false).importo(), 0.01);
	}

	@Test 
	void testOK_CalcolaTariffaCDaily_1day() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateC.dailyRate(), controller.calcolaTariffa("MILANO", agency1, friMorning, friEvening, CarType.C, false).importo(), 0.01);
	}

	@Test 
	void testOK_CalcolaTariffaDDaily_1day() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateD.dailyRate(), controller.calcolaTariffa("MILANO", agency1, friMorning, friEvening, CarType.D, false).importo(), 0.01);
	}
	
	@Test 
	void testOK_CalcolaTariffaBDaily_1day_dropoff() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateB.dailyRate()+rateB.dropoffCharge(), controller.calcolaTariffa("MILANO", agency1, friMorning, friEvening, CarType.B, true).importo(), 0.01);
	}
	
	@Test 
	void testOK_CalcolaTariffaBDaily_MonWed_3days() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateB.dailyRate()*3, controller.calcolaTariffa("MILANO", agency1, nextMonMorning, nextWedEvening, CarType.B, false).importo(), 0.01);
	}
	
	@Test 
	void testOK_CalcolaTariffaBDaily_MonWed_3days_dropoff() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateB.dailyRate()*3+rateB.dropoffCharge(), controller.calcolaTariffa("MILANO", agency1, nextMonMorning, nextWedEvening, CarType.B, true).importo(), 0.01);
	}
	
	@Test 
	void testOK_CalcolaTariffaBWeekend_3days() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateB.weekendRate(), controller.calcolaTariffa("MILANO", agency1, friMorning, sunEvening, CarType.B, false).importo(), 0.01);
	}
	
	@Test 
	void testOK_CalcolaTariffaBWeekend_2days() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateB.weekendRate(), controller.calcolaTariffa("MILANO", agency1, friMorning, satEvening, CarType.B, false).importo(), 0.01);
	}
	
	@Test 
	void testOK_CalcolaTariffaDWeekend_2days_NonConvenient() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateD.dailyRate()*2, controller.calcolaTariffa("MILANO", agency1, friMorning, satEvening, CarType.D, false).importo(), 0.01);
	}
	
	@Test 
	void testOK_CalcolaTariffaBWeekend_1day_NonConvenient() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateB.dailyRate(), controller.calcolaTariffa("MILANO", agency1, friMorning, friEvening, CarType.B, false).importo(), 0.01);
	}
	
	@Test 
	void testOK_CalcolaTariffaCDaily_WedSat_3days_dropoff() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateC.dailyRate()*3+rateC.dropoffCharge(), controller.calcolaTariffa("MILANO", agency1, nextWedEvening.minusDays(7), satEvening, CarType.C, true).importo(), 0.01);
	}
	
	@Test 
	void testOK_CalcolaTariffaCDaily_WedNextMon_5days() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateC.dailyRate()*5, controller.calcolaTariffa("MILANO", agency1, nextWedEvening.minusDays(7), nextMonMorning, CarType.C, false).importo(), 0.01);
	}
	
	@Test 
	void testOK_CalcolaTariffaCDaily_FriNextMon_3days() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateC.dailyRate()*3, controller.calcolaTariffa("MILANO", agency1, friMorning, nextMonMorning, CarType.C, false).importo(), 0.01);
	}
	
	@Test 
	void testOK_CalcolaTariffaCDaily_FriNextSat_9days() {
		var controller = new Controller(agencies, rates);
		assertEquals(rateC.dailyRate()*9, controller.calcolaTariffa("MILANO", agency1, friMorning, satEvening.plusDays(7), CarType.C, false).importo(), 0.01);
	}
	
	// ------ test KO

	@Test
	void testKO_CtorArg1Null() {
		assertThrows(NullPointerException.class, () -> new Controller(null, rates));
	}
	
	@Test
	void testKO_CtorArg2Null() {
		assertThrows(NullPointerException.class, () -> new Controller(agencies, null));
	}
	
	@Test
	void testKO_CalcolaTariffaArg1Null() {
		var controller = new Controller(agencies, rates);
		assertThrows(NullPointerException.class, () -> controller.calcolaTariffa(null, agency1, friMorning, friEvening, CarType.B, false));
	}
	
	@Test
	void testKO_CalcolaTariffaArg1Vuoto() {
		var controller = new Controller(agencies, rates);
		assertThrows(IllegalArgumentException.class, () -> controller.calcolaTariffa(" ", agency1, friMorning, friEvening, CarType.B, false));
	}
	
	@Test
	void testKO_CalcolaTariffaArg2Null() {
		var controller = new Controller(agencies, rates);
		assertThrows(NullPointerException.class, () -> controller.calcolaTariffa("MILANO", null, friMorning, friEvening, CarType.B, false));
	}
	
	@Test
	void testKO_CalcolaTariffaArg3Null() {
		var controller = new Controller(agencies, rates);
		assertThrows(NullPointerException.class, () -> controller.calcolaTariffa("MILANO", agency1, null, friEvening, CarType.B, false));
	}
	
	@Test
	void testKO_CalcolaTariffaArg4Null() {
		var controller = new Controller(agencies, rates);
		assertThrows(NullPointerException.class, () -> controller.calcolaTariffa("MILANO", agency1, friMorning, null, CarType.B, false));
	}
	
	@Test
	void testKO_CalcolaTariffaArg5Null() {
		var controller = new Controller(agencies, rates);
		assertThrows(NullPointerException.class, () -> controller.calcolaTariffa("MILANO", agency1, friMorning, friEvening, null, false));
	}
	
	@Test
	void testKO_CalcolaTariffaEndBeforeStart() {
		var controller = new Controller(agencies, rates);
		assertThrows(IllegalArgumentException.class, () -> controller.calcolaTariffa("MILANO", agency1, friEvening, friMorning, CarType.B, false));
	}

}
