package contocorrente.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import contocorrente.model.Movimento;
import contocorrente.model.ContoCorrente;
import contocorrente.model.Tipologia;
import java.time.LocalDate;


class ContoCorrenteTest {

	@Test
	void testOK_SingoloMovimento() {
		var cc = new ContoCorrente(987654321L);
		assertEquals(0, cc.getMovimenti().size());
		var m = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 50000, "saldo iniziale");
		cc.aggiungi(m);
		assertEquals(1, cc.getMovimenti().size());
		assertEquals(m, cc.getMovimenti().get(0));
		cc.storna(m);
		assertEquals(0, cc.getMovimenti().size());
		assertEquals("Anonimo", cc.getIntestatario());
	}

	@Test
	void testOK_Intestatario() {
		var cc = new ContoCorrente(987654321L);
		assertEquals(0, cc.getMovimenti().size());
		assertEquals("Anonimo", cc.getIntestatario());
		cc.setIntestatario("Zio Paperone");
		assertEquals("Zio Paperone", cc.getIntestatario());
		cc.setIntestatario("");
		assertEquals("Anonimo", cc.getIntestatario());
	}
	
	@Test
	void testOK_TreMovimenti() {
		var cc = new ContoCorrente(987654321L);
		assertEquals(0, cc.getMovimenti().size());
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		assertEquals(1, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		assertEquals(2, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m2, cc.getMovimenti().get(1));
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		assertEquals(3, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m3, cc.getMovimenti().get(1));
		assertEquals(m2, cc.getMovimenti().get(2));
	}
	
	@Test
	void testOK_PiuMovimenti() {
		var cc = new ContoCorrente(987654321L);
		assertEquals(0, cc.getMovimenti().size());
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		assertEquals(1, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		assertEquals(2, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m2, cc.getMovimenti().get(1));
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		assertEquals(3, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m3, cc.getMovimenti().get(1));
		assertEquals(m2, cc.getMovimenti().get(2));		
		var m4 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,4), Tipologia.ACCREDITO, 1200.40, "rimborso");
		cc.aggiungi(m4);
		assertEquals(4, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m3, cc.getMovimenti().get(1));
		assertEquals(m4, cc.getMovimenti().get(2));
		assertEquals(m2, cc.getMovimenti().get(3));
	}
	
	@Test
	void testOK_TreMovimentiConStorno() {
		var cc = new ContoCorrente(987654321L);
		assertEquals(0, cc.getMovimenti().size());
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		assertEquals(1, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		assertEquals(2, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m2, cc.getMovimenti().get(1));
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		assertEquals(3, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m3, cc.getMovimenti().get(1));
		assertEquals(m2, cc.getMovimenti().get(2));
		cc.storna(m2);
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m3, cc.getMovimenti().get(1));
		cc.storna(m1);
		assertEquals(m3, cc.getMovimenti().get(0));
	}
	
	@Test
	void testOK_PiuMovimentiConStorno() {
		var cc = new ContoCorrente(987654321L);
		assertEquals(0, cc.getMovimenti().size());
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		assertEquals(1, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		assertEquals(2, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m2, cc.getMovimenti().get(1));
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		assertEquals(3, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m3, cc.getMovimenti().get(1));
		assertEquals(m2, cc.getMovimenti().get(2));		
		var m4 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,4), Tipologia.ACCREDITO, 1200.40, "rimborso");
		cc.aggiungi(m4);
		assertEquals(4, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m3, cc.getMovimenti().get(1));
		assertEquals(m4, cc.getMovimenti().get(2));
		assertEquals(m2, cc.getMovimenti().get(3));
		cc.storna(m2);
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m3, cc.getMovimenti().get(1));
		assertEquals(m4, cc.getMovimenti().get(2));
		cc.storna(m1);
		assertEquals(m3, cc.getMovimenti().get(0));
		assertEquals(m4, cc.getMovimenti().get(1));
		cc.storna(m3);
		assertEquals(m4, cc.getMovimenti().get(0));
	}
	
	@Test
	void testKO_AggiungiNullo() {
		var cc = new ContoCorrente(987654321L);
		assertThrows(IllegalArgumentException.class, () -> cc.aggiungi(null));
	}

	@Test
	void testKO_StornaNullo() {
		var cc = new ContoCorrente(987654321L);
		assertEquals(0, cc.getMovimenti().size());
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		var m3 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		var m4 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,2,4), Tipologia.ACCREDITO, 1200.40, "rimborso");
		cc.aggiungi(m4);
		assertThrows(IllegalArgumentException.class, () -> cc.storna(null));
	}
	
	@Test
	void testKO_IntestatarioNullo() {
		var cc = new ContoCorrente(987654321L);
		assertEquals(0, cc.getMovimenti().size());
		assertEquals("Anonimo", cc.getIntestatario());
		assertThrows(IllegalArgumentException.class, () -> cc.setIntestatario(null));
	}
}
