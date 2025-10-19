package oroscopo.controller.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oroscopo.controller.Controller;
import oroscopo.controller.MyController;
import oroscopo.controller.MyStrategiaSelezione;
import oroscopo.controller.StrategiaSelezione;
import oroscopo.model.ArchivioPrevisioni;
import oroscopo.model.Oroscopo;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class MyControllerTest {

	private ArchivioPrevisioni archivio;
	private StrategiaSelezione strategia;

	@BeforeEach
	public void setUp() {
		try {
			var listaPrevisioniAmore = List.of( 
					new Previsione("Nuove occasioni per i single", 4, Set.of(SegnoZodiacale.TORO, SegnoZodiacale.LEONE)),
					new Previsione("grande intimità", 9)
			 );
			var listaPrevisioniLavoro = List.of( 
					new Previsione("Grandi soddisfazioni", 6),
					new Previsione("diffidate di un collega invidioso",	2),
					new Previsione("impegnatevi di più", 6, Set.of(SegnoZodiacale.BILANCIA, SegnoZodiacale.CANCRO))
			 );
			var listaPrevisioniSalute = List.of( 
					new Previsione("attenzione alle piccole infiammazioni", 1) 
			 );
			
			var mappaPrevisioni = Map.of(
				"AMORE", listaPrevisioniAmore,
				"LAVORO", listaPrevisioniLavoro,
				"SALUTE", listaPrevisioniSalute
				);

			this.archivio = new ArchivioPrevisioni(mappaPrevisioni);
			this.strategia = new MyStrategiaSelezione();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testMyController() throws IOException {
		new MyController(archivio, strategia);
	}

	@Test
	public void testGetSegni() throws IOException {

		Controller underTest = new MyController(archivio, strategia);

		SegnoZodiacale[] segni = underTest.getSegni();

		assertEquals(Controller.NUMERO_SEGNI, segni.length);

		assertEquals(segni[0], SegnoZodiacale.ARIETE);
		assertEquals(segni[1], SegnoZodiacale.TORO);
		assertEquals(segni[2], SegnoZodiacale.GEMELLI);
		assertEquals(segni[3], SegnoZodiacale.CANCRO);
		assertEquals(segni[4], SegnoZodiacale.LEONE);
		assertEquals(segni[5], SegnoZodiacale.VERGINE);
		assertEquals(segni[6], SegnoZodiacale.BILANCIA);
		assertEquals(segni[7], SegnoZodiacale.SCORPIONE);
		assertEquals(segni[8], SegnoZodiacale.SAGITTARIO);
		assertEquals(segni[9], SegnoZodiacale.CAPRICORNO);
		assertEquals(segni[10], SegnoZodiacale.ACQUARIO);
		assertEquals(segni[11], SegnoZodiacale.PESCI);
	}

	@Test
	public void testGeneraOroscopoCasuale() throws IOException {

		Controller underTest = new MyController(archivio, strategia);

		Oroscopo risultato = underTest.generaOroscopoCasuale(SegnoZodiacale.ARIETE);
		
		assertTrue(List.of("Nuove occasioni per i single", "grande intimità").contains(risultato.getPrevisioneAmore().getPrevisione()));
		assertTrue(List.of("Grandi soddisfazioni", "diffidate di un collega invidioso", "impegnatevi di più").contains(risultato.getPrevisioneLavoro().getPrevisione()));
		assertEquals("attenzione alle piccole infiammazioni", risultato.getPrevisioneSalute().getPrevisione());
		
		assertTrue(List.of(4, 9).contains(risultato.getPrevisioneAmore().getValore()));
		assertTrue(List.of(6, 2).contains(risultato.getPrevisioneLavoro().getValore()));
		assertEquals(1, risultato.getPrevisioneSalute().getValore());

		assertEquals(SegnoZodiacale.ARIETE, risultato.getSegnoZodiacale());
		assertTrue(List.of(4,2,5).contains(risultato.getFortuna()));
	}

	@Test
	public void testGeneraOroscopoAnnuale() throws IOException {
		Controller underTest = new MyController(archivio, strategia);
		Oroscopo[] risultato = underTest.generaOroscopoAnnuale(SegnoZodiacale.ARIETE, 5);

		assertEquals(12, risultato.length);

		int fortuna = 0;
		for (Oroscopo o : risultato) {
			fortuna += o.getFortuna();
		}

		assertEquals(5, fortuna / 12);
	}

}
