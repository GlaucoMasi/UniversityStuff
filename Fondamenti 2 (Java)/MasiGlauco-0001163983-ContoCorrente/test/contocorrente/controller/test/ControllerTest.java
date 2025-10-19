package contocorrente.controller.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import contocorrente.controller.Controller;
import contocorrente.model.Movimento;
import contocorrente.model.ContoCorrente;
import contocorrente.model.Tipologia;


class ControllerTest {
	
	@Test
	void testOK() {
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
		var m3 = new Movimento(LocalDate.of(2022,2,2),LocalDate.of(2022,2,2), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		assertEquals(3, cc.getMovimenti().size());
		assertEquals(m1, cc.getMovimenti().get(0));
		assertEquals(m3, cc.getMovimenti().get(1));
		assertEquals(m2, cc.getMovimenti().get(2));
		//----
		Controller controller = new Controller(cc);	
		assertEquals(List.of(m1,m3,m2), controller.movimenti());
		assertEquals(cc.getId(), controller.ccId());
		assertEquals(cc.getIntestatario(), controller.ccIntestatario());
		assertEquals(9700, controller.saldoAl(LocalDate.of(2022,2,3)));
		assertEquals(10000, controller.saldoAl(LocalDate.of(2022,2,1)));
		assertEquals(9800, controller.saldoAl(LocalDate.of(2022,2,2)));		
	}

}
