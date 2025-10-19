package contocorrente.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import contocorrente.model.Movimento;
import contocorrente.model.ContoCorrente;
import contocorrente.model.Tipologia;
import contocorrente.model.Analizzatore;
import contocorrente.model.MyAnalizzatore;

class MyAnalizzatoreTest {

	@Test
	void testOK1_SaldoBasicSenzaSaldiIntermedi() {
		var cc = new ContoCorrente(987654321L);
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		var m4 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,4), Tipologia.ACCREDITO, 1200.40, "rimborso");
		cc.aggiungi(m4);
		Analizzatore ed = new MyAnalizzatore(cc);
		assertEquals(11000.40, ed.getSaldo(LocalDate.of(2022,2,1)), 0.001);
		assertEquals(11000.40, ed.getSaldo(LocalDate.of(2022,2,2)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,3)), 0.001);
		var m5 = new Movimento(LocalDate.of(2022,2,7),LocalDate.of(2022,2,7), Tipologia.ACCREDITO, 38.60, "rimborso utenza");
		cc.aggiungi(m5);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,4)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,5)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,6)), 0.001);
		assertEquals(10939.00, ed.getSaldo(LocalDate.of(2022,2,7)), 0.001);
		assertEquals(10939.00, ed.getSaldo(LocalDate.now()), 0.001);
	}
	
	@Test
	void testOK2_SaldoBasicSenzaSaldiIntermedi() {
		var cc = new ContoCorrente(987654321L);
		var m1 = new Movimento(LocalDate.of(2022,1,31),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 100, "saldo iniziale");
		cc.aggiungi(m1);
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -500, "prelievo bancomat");
		cc.aggiungi(m2);
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		var m4 = new Movimento(LocalDate.of(2022,2,5),LocalDate.of(2022,2,5), Tipologia.ACCREDITO, 1200.50, "rimborso");
		cc.aggiungi(m4);
		Analizzatore ed = new MyAnalizzatore(cc);
		assertEquals(100, ed.getSaldo(LocalDate.of(2022,1,31)), 0.001);
		assertEquals(-100, ed.getSaldo(LocalDate.of(2022,2,1)), 0.001);
		assertEquals(-100, ed.getSaldo(LocalDate.of(2022,2,2)), 0.001);
		assertEquals(-600, ed.getSaldo(LocalDate.of(2022,2,3)), 0.001);
		assertEquals(-600, ed.getSaldo(LocalDate.of(2022,2,4)), 0.001);
		assertEquals(600.5, ed.getSaldo(LocalDate.of(2022,2,5)), 0.001);
		var m5 = new Movimento(LocalDate.of(2022,2,4),LocalDate.of(2022,2,4), Tipologia.ADDEBITO, -550, "riparazione caldaia");
		cc.aggiungi(m5);
		assertEquals(100, ed.getSaldo(LocalDate.of(2022,1,31)), 0.001);
		assertEquals(-100, ed.getSaldo(LocalDate.of(2022,2,1)), 0.001);
		assertEquals(-100, ed.getSaldo(LocalDate.of(2022,2,2)), 0.001);
		assertEquals(-600, ed.getSaldo(LocalDate.of(2022,2,3)), 0.001);
		assertEquals(-1150, ed.getSaldo(LocalDate.of(2022,2,4)), 0.001);
		assertEquals(50.5, ed.getSaldo(LocalDate.of(2022,2,5)), 0.001);
	}
	
	@Test
	void testOK3_SaldoBasicSenzaSaldiIntermedi() {
		var cc = new ContoCorrente(987654321L);
		var m1 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ACCREDITO, 100, "saldo iniziale");
		cc.aggiungi(m1);
		var m2 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -500, "prelievo bancomat");
		cc.aggiungi(m2);
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		var m4 = new Movimento(LocalDate.of(2022,2,5),LocalDate.of(2022,2,5), Tipologia.ACCREDITO, 500.25, "rimborso");
		cc.aggiungi(m4);
		Analizzatore ed = new MyAnalizzatore(cc);
		assertEquals(0, ed.getSaldo(LocalDate.of(2022,1,31)), 0.001);
		assertEquals(-700, ed.getSaldo(LocalDate.of(2022,2,1)), 0.001);
		assertEquals(-700, ed.getSaldo(LocalDate.of(2022,2,2)), 0.001);
		assertEquals(-600, ed.getSaldo(LocalDate.of(2022,2,3)), 0.001);
		assertEquals(-99.75, ed.getSaldo(LocalDate.of(2022,2,5)), 0.001);
	}
	
	@Test
	void testOK4_SaldoConSaldiIntermedi() {
		var cc = new ContoCorrente(987654321L);
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		var m4 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,4), Tipologia.ACCREDITO, 1200.40, "rimborso");
		cc.aggiungi(m4);
		Analizzatore ed = new MyAnalizzatore(cc);
		assertEquals(11000.40, ed.getSaldo(LocalDate.of(2022,2,1)), 0.001);
		assertEquals(11000.40, ed.getSaldo(LocalDate.of(2022,2,2)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,3)), 0.001);
		//
		var m5 = new Movimento(LocalDate.of(2022,2,7),LocalDate.of(2022,2,7), Tipologia.SALDO, 10900.40, "saldo attuale");
		cc.aggiungi(m5);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,4)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,5)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,6)), 0.001);
		assertEquals(10900.4, ed.getSaldo(LocalDate.of(2022,2,7)), 0.001);
		assertEquals(10900.4, ed.getSaldo(LocalDate.now()), 0.001);
	}
	
	@Test
	void testOK5_SaldoConSaldiIntermedi() {
		var cc = new ContoCorrente(987654321L);
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		var m4 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,4), Tipologia.ACCREDITO, 1200.40, "rimborso");
		cc.aggiungi(m4);
		Analizzatore ed = new MyAnalizzatore(cc);
		assertEquals(11000.40, ed.getSaldo(LocalDate.of(2022,2,1)), 0.001);
		assertEquals(11000.40, ed.getSaldo(LocalDate.of(2022,2,2)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,3)), 0.001);
		//
		var m5 = new Movimento(LocalDate.of(2022,2,7),LocalDate.of(2022,2,7), Tipologia.SALDO, 10900.40, "saldo attuale");
		cc.aggiungi(m5);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,4)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,5)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,6)), 0.001);
		assertEquals(10900.4, ed.getSaldo(LocalDate.of(2022,2,7)), 0.001);
		assertEquals(10900.4, ed.getSaldo(LocalDate.now()), 0.001);
		//
		cc.storna(m5); // perché aggiungendo un movimento in data 6/2, il saldo al 7/2 cambia
		var m6 = new Movimento(LocalDate.of(2022,2,6),LocalDate.of(2022,2,6), Tipologia.ACCREDITO, 1099.60, "rimborso");
		cc.aggiungi(m6);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,4)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,5)), 0.001);
		assertEquals(12000.00, ed.getSaldo(LocalDate.of(2022,2,6)), 0.001);
		assertEquals(12000.00, ed.getSaldo(LocalDate.of(2022,2,7)), 0.001);
		assertEquals(12000.00, ed.getSaldo(LocalDate.now()), 0.001);	
		var m7 = new Movimento(LocalDate.of(2022,2,7),LocalDate.of(2022,2,7), Tipologia.SALDO, 12000, "saldo attuale");
		cc.aggiungi(m7);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,4)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,5)), 0.001);
		assertEquals(12000.00, ed.getSaldo(LocalDate.of(2022,2,6)), 0.001);
		assertEquals(12000.00, ed.getSaldo(LocalDate.of(2022,2,7)), 0.001);
		assertEquals(12000.00, ed.getSaldo(LocalDate.now()), 0.001);	
	}
	
	@Test
	void testOK6_SaldoConSaldiIntermedi() {
		var cc = new ContoCorrente(987654321L);
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		var m4 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,4), Tipologia.ACCREDITO, 1200.40, "rimborso");
		cc.aggiungi(m4);
		Analizzatore ed = new MyAnalizzatore(cc);
		assertEquals(11000.40, ed.getSaldo(LocalDate.of(2022,2,1)), 0.001);
		assertEquals(11000.40, ed.getSaldo(LocalDate.of(2022,2,2)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,3)), 0.001);
		//
		var m5 = new Movimento(LocalDate.of(2022,2,7),LocalDate.of(2022,2,7), Tipologia.SALDO, 10900.40, "saldo attuale");
		cc.aggiungi(m5);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,4)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,5)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,6)), 0.001);
		assertEquals(10900.4, ed.getSaldo(LocalDate.of(2022,2,7)), 0.001);
		assertEquals(10900.4, ed.getSaldo(LocalDate.now()), 0.001);
		//
		var m6 = new Movimento(LocalDate.of(2022,2,8),LocalDate.of(2022,2,8), Tipologia.ACCREDITO, 1099.60, "rimborso");
		cc.aggiungi(m6);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,4)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,5)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,6)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,7)), 0.001); // saldo attuale
		assertEquals(12000.00, ed.getSaldo(LocalDate.of(2022,2,8)), 0.001);
		assertEquals(12000.00, ed.getSaldo(LocalDate.now()), 0.001);	
		var m7 = new Movimento(LocalDate.of(2022,2,9),LocalDate.of(2022,2,9), Tipologia.SALDO, 12000, "saldo attuale");
		cc.aggiungi(m7);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,4)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,5)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,6)), 0.001);
		assertEquals(10900.40, ed.getSaldo(LocalDate.of(2022,2,7)), 0.001); // saldo attuale
		assertEquals(12000.00, ed.getSaldo(LocalDate.of(2022,2,8)), 0.001);
		assertEquals(12000.00, ed.getSaldo(LocalDate.now()), 0.001);	
	}
	
	@Test
	void testOK7_SommaPerTipologiaConSaldiIntermedi() {
		var cc = new ContoCorrente(987654321L);
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		var m4 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,4), Tipologia.ACCREDITO, 1200.40, "rimborso");
		cc.aggiungi(m4);
		Analizzatore ed = new MyAnalizzatore(cc);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,1),Tipologia.ACCREDITO), 0.001);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,2),Tipologia.ACCREDITO), 0.001);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,3),Tipologia.ACCREDITO), 0.001);
		assertEquals( -200.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,1),Tipologia.ADDEBITO), 0.001);
		assertEquals( -200.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,2),Tipologia.ADDEBITO), 0.001);
		assertEquals( -300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,3),Tipologia.ADDEBITO), 0.001);
		//
		var m5 = new Movimento(LocalDate.of(2022,2,7),LocalDate.of(2022,2,7), Tipologia.SALDO, 10900.40, "saldo attuale");
		cc.aggiungi(m5);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,4),Tipologia.ACCREDITO), 0.001);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,5),Tipologia.ACCREDITO), 0.001);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,6),Tipologia.ACCREDITO), 0.001);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,7),Tipologia.ACCREDITO), 0.001);
		assertEquals( -300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,4),Tipologia.ADDEBITO), 0.001);
		assertEquals( -300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,5),Tipologia.ADDEBITO), 0.001);
		assertEquals( -300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,6),Tipologia.ADDEBITO), 0.001);
		assertEquals( -300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,7),Tipologia.ADDEBITO), 0.001);
		//
		var m6 = new Movimento(LocalDate.of(2022,2,8),LocalDate.of(2022,2,8), Tipologia.ACCREDITO, 1099.60, "rimborso");
		cc.aggiungi(m6);
		assertEquals(12300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,8),Tipologia.ACCREDITO), 0.001);
		assertEquals( -300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,8),Tipologia.ADDEBITO), 0.001);
		var m7 = new Movimento(LocalDate.of(2022,2,9),LocalDate.of(2022,2,9), Tipologia.SALDO, 12000, "saldo attuale");
		cc.aggiungi(m7);
		assertEquals(12300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,9),Tipologia.ACCREDITO), 0.001);
		assertEquals( -300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,9),Tipologia.ADDEBITO), 0.001);
		var m8 = new Movimento(LocalDate.of(2022,2,9),LocalDate.of(2022,2,9), Tipologia.ADDEBITO, -10000, "fattura serramenti");
		cc.aggiungi(m8);
		assertEquals( 12300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,9),Tipologia.ACCREDITO), 0.001);
		assertEquals(-10300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,9),Tipologia.ADDEBITO), 0.001);
	}
	
	@Test
	void testOK8_SommaPerTipologiaSoloAddebiti() {
		var cc = new ContoCorrente(987654321L);
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		Analizzatore ed = new MyAnalizzatore(cc);
		assertEquals(0.0, ed.getSommaPerTipologia(LocalDate.of(2022,2,1),Tipologia.ACCREDITO), 0.001);
		assertEquals(0.0, ed.getSommaPerTipologia(LocalDate.of(2022,2,2),Tipologia.ACCREDITO), 0.001);
		assertEquals(0.0, ed.getSommaPerTipologia(LocalDate.of(2022,2,3),Tipologia.ACCREDITO), 0.001);
		assertEquals(-200.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,1),Tipologia.ADDEBITO), 0.001);
		assertEquals(-200.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,2),Tipologia.ADDEBITO), 0.001);
		assertEquals(-300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,3),Tipologia.ADDEBITO), 0.001);
		//
		var m5 = new Movimento(LocalDate.of(2022,2,7),LocalDate.of(2022,2,7), Tipologia.SALDO, 10900.40, "saldo attuale");
		cc.aggiungi(m5);
		assertEquals(0.0, ed.getSommaPerTipologia(LocalDate.of(2022,2,4),Tipologia.ACCREDITO), 0.001);
		assertEquals(0.0, ed.getSommaPerTipologia(LocalDate.of(2022,2,5),Tipologia.ACCREDITO), 0.001);
		assertEquals(0.0, ed.getSommaPerTipologia(LocalDate.of(2022,2,6),Tipologia.ACCREDITO), 0.001);
		assertEquals(0.0, ed.getSommaPerTipologia(LocalDate.of(2022,2,7),Tipologia.ACCREDITO), 0.001);
		assertEquals( -300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,4),Tipologia.ADDEBITO), 0.001);
		assertEquals( -300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,5),Tipologia.ADDEBITO), 0.001);
		assertEquals( -300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,6),Tipologia.ADDEBITO), 0.001);
		assertEquals( -300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,7),Tipologia.ADDEBITO), 0.001);
		//
		var m8 = new Movimento(LocalDate.of(2022,2,9),LocalDate.of(2022,2,9), Tipologia.ADDEBITO, -10000, "fattura serramenti");
		cc.aggiungi(m8);
		assertEquals( 0.0, ed.getSommaPerTipologia(LocalDate.of(2022,2,9),Tipologia.ACCREDITO), 0.001);
		assertEquals(-10300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,9),Tipologia.ADDEBITO), 0.001);
	}
	
	@Test
	void testOK9_SommaPerTipologiaSoloAccrediti() {
		var cc = new ContoCorrente(987654321L);
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		var m4 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,4), Tipologia.ACCREDITO, 1200.40, "rimborso");
		cc.aggiungi(m4);
		Analizzatore ed = new MyAnalizzatore(cc);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,1),Tipologia.ACCREDITO), 0.001);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,2),Tipologia.ACCREDITO), 0.001);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,3),Tipologia.ACCREDITO), 0.001);
		assertEquals(0.0, ed.getSommaPerTipologia(LocalDate.of(2022,2,1),Tipologia.ADDEBITO), 0.001);
		assertEquals(0.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,2),Tipologia.ADDEBITO), 0.001);
		assertEquals(0.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,3),Tipologia.ADDEBITO), 0.001);
		//
		var m5 = new Movimento(LocalDate.of(2022,2,7),LocalDate.of(2022,2,7), Tipologia.SALDO, 10900.40, "saldo attuale");
		cc.aggiungi(m5);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,4),Tipologia.ACCREDITO), 0.001);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,5),Tipologia.ACCREDITO), 0.001);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,6),Tipologia.ACCREDITO), 0.001);
		assertEquals(11200.40, ed.getSommaPerTipologia(LocalDate.of(2022,2,7),Tipologia.ACCREDITO), 0.001);
		assertEquals(0.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,4),Tipologia.ADDEBITO), 0.001);
		assertEquals(0.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,5),Tipologia.ADDEBITO), 0.001);
		assertEquals(0.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,6),Tipologia.ADDEBITO), 0.001);
		assertEquals(0.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,7),Tipologia.ADDEBITO), 0.001);
		//
		var m6 = new Movimento(LocalDate.of(2022,2,8),LocalDate.of(2022,2,8), Tipologia.ACCREDITO, 1099.60, "rimborso");
		cc.aggiungi(m6);
		assertEquals(12300.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,8),Tipologia.ACCREDITO), 0.001);
		assertEquals(0.00, ed.getSommaPerTipologia(LocalDate.of(2022,2,8),Tipologia.ADDEBITO), 0.001);
	}
	
	@Test
	void testKO_SommaPerTipologia_TipologiaErrataSaldo() {
		var cc = new ContoCorrente(987654321L);
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		var m4 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,4), Tipologia.ACCREDITO, 1200.40, "rimborso");
		cc.aggiungi(m4);
		Analizzatore ed = new MyAnalizzatore(cc);
		assertThrows(IllegalArgumentException.class, () -> ed.getSommaPerTipologia(LocalDate.of(2022,2,1),Tipologia.SALDO));
	}
	
	@Test
	void testKO_SommaPerTipologia_TipologiaErrataNullo() {
		var cc = new ContoCorrente(987654321L);
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		var m4 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,4), Tipologia.ACCREDITO, 1200.40, "rimborso");
		cc.aggiungi(m4);
		Analizzatore ed = new MyAnalizzatore(cc);
		assertThrows(IllegalArgumentException.class, () -> ed.getSommaPerTipologia(LocalDate.of(2022,2,1),Tipologia.NULLO));
	}
	
}
