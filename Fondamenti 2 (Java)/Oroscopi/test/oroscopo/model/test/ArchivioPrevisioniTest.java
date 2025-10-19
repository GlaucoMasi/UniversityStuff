package oroscopo.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import oroscopo.model.ArchivioPrevisioni;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;


public class ArchivioPrevisioniTest {

	@Test
	void testOK_Ctor1() {
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

		var archivio = new ArchivioPrevisioni(mappaPrevisioni);
		assertSame(mappaPrevisioni, archivio.getMappaPrevisioni());
		
		assertSame(listaPrevisioniAmore, archivio.getPrevisioni("AMORE"));
		assertSame(listaPrevisioniAmore, archivio.getPrevisioni("aMore"));
		assertSame(listaPrevisioniLavoro, archivio.getPrevisioni("lavORO"));
		assertSame(listaPrevisioniLavoro, archivio.getPrevisioni("LAVORO"));
		assertSame(listaPrevisioniSalute, archivio.getPrevisioni("salutE"));
		assertSame(listaPrevisioniSalute, archivio.getPrevisioni("SALUTe"));
		
		assertEquals(Set.of("AMORE", "SALUTE", "LAVORO"), archivio.getSettori());
	}
	
	@Test
	void testOK_Ctor0() {
		var archivio = new ArchivioPrevisioni();
		assertEquals(Map.of(), archivio.getMappaPrevisioni());
		
		assertEquals(List.of(), archivio.getPrevisioni("AMORE"));
		assertEquals(List.of(), archivio.getPrevisioni("aMore"));
		assertEquals(List.of(), archivio.getPrevisioni("lavORO"));
		assertEquals(List.of(), archivio.getPrevisioni("LAVORO"));
		assertEquals(List.of(), archivio.getPrevisioni("salutE"));
		assertEquals(List.of(), archivio.getPrevisioni("SALUTe"));
		assertEquals(List.of(), archivio.getPrevisioni("DENARO"));
		
		assertEquals(Set.of(), archivio.getSettori());
	}
	
	@Test
	void testKO_Ctor1_ArgNull() {
		assertThrows(NullPointerException.class, () -> new ArchivioPrevisioni(null));
	}

	@Test
	void testKO_Ctor1_ArgEmpty() {
		assertThrows(IllegalArgumentException.class, () -> new ArchivioPrevisioni(Map.of()));
	}
	
	@Test
	void testKO_getPrevisioniArgEmpty() {
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

		var archivio = new ArchivioPrevisioni(mappaPrevisioni);
		assertThrows(IllegalArgumentException.class, () -> archivio.getPrevisioni(" "));
	}	
	
	@Test
	void testKO_getPrevisioniArgNull() {
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

		var archivio = new ArchivioPrevisioni(mappaPrevisioni);
		assertThrows(NullPointerException.class, () -> archivio.getPrevisioni(null));
	}	
	
}
