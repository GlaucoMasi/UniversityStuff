package contocorrente.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import contocorrente.model.Movimento;
import contocorrente.model.Tipologia;

class MovimentoTest {

	@Test
	void testOK_Accredito() {
		var m = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		assertEquals(LocalDate.of(2022,2,1), m.getDataContabile());
		assertEquals(LocalDate.of(2022,1,31), m.getDataValuta());
		assertEquals(Tipologia.ACCREDITO, m.getTipologia());
		assertEquals(10000, m.getImporto(), 0.001);
		assertEquals("SALDO INIZIALE", m.getCausale());
	}
	
	@Test
	void testOK_Addebito() {
		var m = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ADDEBITO, -150.30, "pagamento utenze");
		assertEquals(LocalDate.of(2022,2,1), m.getDataContabile());
		assertEquals(LocalDate.of(2022,1,31), m.getDataValuta());
		assertEquals(Tipologia.ADDEBITO, m.getTipologia());
		assertEquals(-150.30, m.getImporto(), 0.001);
		assertEquals("pagamento utenze".toUpperCase(), m.getCausale());
	}
	
	@Test
	void testOK_ImportoNullo() {
		var m = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.NULLO, 0, "pagamento utenze");
		assertEquals(LocalDate.of(2022,2,1), m.getDataContabile());
		assertEquals(LocalDate.of(2022,1,31), m.getDataValuta());
		assertEquals(Tipologia.NULLO, m.getTipologia());
		assertEquals(0, m.getImporto(), 0.001);
		assertEquals("pagamento utenze".toUpperCase(), m.getCausale());
	}
	
	@Test
	void testKO_nullItem1() {
		assertThrows(IllegalArgumentException.class, () -> new Movimento(
				null,LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale")
				);
	}
	@Test
	void testKO_nullItem2() {
		assertThrows(IllegalArgumentException.class, () -> new Movimento(
				LocalDate.of(2022,2,1),null, Tipologia.ACCREDITO, 10000, "saldo iniziale")
				);
	}
	@Test
	void testKO_nullItem3() {
		assertThrows(IllegalArgumentException.class, () -> new Movimento(
				LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), null, 10000, "saldo iniziale")
				);
	}
	@Test
	void testKO_nullItem4() {
		assertThrows(IllegalArgumentException.class, () -> new Movimento(
				LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, Double.NaN, "saldo iniziale")
				);
	}
	@Test
	void testKO_nullItem5() {
		assertThrows(IllegalArgumentException.class, () -> new Movimento(
				LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, null)
				);
	}
	
	@Test
	void testKO_Accredito() {
		assertThrows(IllegalArgumentException.class, () -> new Movimento(
			LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ADDEBITO, 10000, "saldo iniziale"));
	}
	
	@Test
	void testKO_Addebito() {
		assertThrows(IllegalArgumentException.class, () -> new Movimento(
				LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, -150.30, "pagamento utenze"));
	}
	
	@Test
	void testKO_MovimentoNulloMaImportoPositivo() {
		assertThrows(IllegalArgumentException.class, () -> new Movimento(
				LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.NULLO, 10, "pagamento utenze"));
	}

	@Test
	void testKO_MovimentoNulloMaImportoNegativo() {
		assertThrows(IllegalArgumentException.class, () -> new Movimento(
				LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.NULLO, -5, "pagamento utenze"));
	}
}
