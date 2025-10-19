package contocorrente.persistence.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import contocorrente.model.Movimento;
import contocorrente.model.Tipologia;
import contocorrente.persistence.BadFileFormatException;
import contocorrente.persistence.CCReader;
import contocorrente.persistence.MyCCReader;

class MyCCReaderTest {

	@Test
	void testOK() throws FileNotFoundException, IOException {
		String fakeFile = "CC N.123456789\r\n"
				+ "31/01/22	31/01/22	10.317,81 	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		var cc = ccRdr.readCC(new BufferedReader(new StringReader(fakeFile)));
		var  movimenti = cc.getMovimenti();
		assertEquals(new Movimento(LocalDate.of(2022,3,2),LocalDate.of(2022,3,2), Tipologia.ADDEBITO, -2.62, "imp.bollo cc"),
				movimenti.get(movimenti.size()-1));
		assertEquals(new Movimento(LocalDate.of(2022,2,28),LocalDate.of(2022,2,28), Tipologia.SALDO, 11855.30, "SALDO FINALE AL 28/02/2022"),
				movimenti.get(movimenti.size()-2));
		assertEquals(new Movimento(LocalDate.of(2022,2,21),LocalDate.of(2022,2,21), Tipologia.ADDEBITO, -24.90, "PAGAMENTO POS TECNOLOGIA REGGIO EMILIA"),
				movimenti.get(movimenti.size()-6));
	}
	
	@Test
	void testOK_ConCCMinuscolo() throws FileNotFoundException, IOException {
		String fakeFile = "cc N.123456789\r\n"
				+ "31/01/22	31/01/22	10.317,81 	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		var cc = ccRdr.readCC(new BufferedReader(new StringReader(fakeFile)));
		var  movimenti = cc.getMovimenti();
		assertEquals(new Movimento(LocalDate.of(2022,3,2),LocalDate.of(2022,3,2), Tipologia.ADDEBITO, -2.62, "imp.bollo cc"),
				movimenti.get(movimenti.size()-1));
		assertEquals(new Movimento(LocalDate.of(2022,2,28),LocalDate.of(2022,2,28), Tipologia.SALDO, 11855.30, "SALDO FINALE AL 28/02/2022"),
				movimenti.get(movimenti.size()-2));
		assertEquals(new Movimento(LocalDate.of(2022,2,21),LocalDate.of(2022,2,21), Tipologia.ADDEBITO, -24.90, "PAGAMENTO POS TECNOLOGIA REGGIO EMILIA"),
				movimenti.get(movimenti.size()-6));
	}
	
	@Test
	void testOK_ConNMinuscolo() throws FileNotFoundException, IOException {
		String fakeFile = "CC n.123456789\r\n"
				+ "31/01/22	31/01/22	10.317,81 	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		var cc = ccRdr.readCC(new BufferedReader(new StringReader(fakeFile)));
		var  movimenti = cc.getMovimenti();
		assertEquals(new Movimento(LocalDate.of(2022,3,2),LocalDate.of(2022,3,2), Tipologia.ADDEBITO, -2.62, "imp.bollo cc"),
				movimenti.get(movimenti.size()-1));
		assertEquals(new Movimento(LocalDate.of(2022,2,28),LocalDate.of(2022,2,28), Tipologia.SALDO, 11855.30, "SALDO FINALE AL 28/02/2022"),
				movimenti.get(movimenti.size()-2));
		assertEquals(new Movimento(LocalDate.of(2022,2,21),LocalDate.of(2022,2,21), Tipologia.ADDEBITO, -24.90, "PAGAMENTO POS TECNOLOGIA REGGIO EMILIA"),
				movimenti.get(movimenti.size()-6));
	}
	
	@Test
	void testOK_ConSpazioDopoN() throws FileNotFoundException, IOException {
		String fakeFile = "CC N. 123456789\r\n"
				+ "31/01/22	31/01/22	10.317,81 	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		var cc = ccRdr.readCC(new BufferedReader(new StringReader(fakeFile)));
		var  movimenti = cc.getMovimenti();
		assertEquals(new Movimento(LocalDate.of(2022,3,2),LocalDate.of(2022,3,2), Tipologia.ADDEBITO, -2.62, "imp.bollo cc"),
				movimenti.get(movimenti.size()-1));
		assertEquals(new Movimento(LocalDate.of(2022,2,28),LocalDate.of(2022,2,28), Tipologia.SALDO, 11855.30, "SALDO FINALE AL 28/02/2022"),
				movimenti.get(movimenti.size()-2));
		assertEquals(new Movimento(LocalDate.of(2022,2,21),LocalDate.of(2022,2,21), Tipologia.ADDEBITO, -24.90, "PAGAMENTO POS TECNOLOGIA REGGIO EMILIA"),
				movimenti.get(movimenti.size()-6));
	}
	
	@Test
	void testOK_ConSpazioDopoNMinuscolo() throws FileNotFoundException, IOException {
		String fakeFile = "CC n. 123456789\r\n"
				+ "31/01/22	31/01/22	10.317,81 	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		var cc = ccRdr.readCC(new BufferedReader(new StringReader(fakeFile)));
		var  movimenti = cc.getMovimenti();
		assertEquals(new Movimento(LocalDate.of(2022,3,2),LocalDate.of(2022,3,2), Tipologia.ADDEBITO, -2.62, "imp.bollo cc"),
				movimenti.get(movimenti.size()-1));
		assertEquals(new Movimento(LocalDate.of(2022,2,28),LocalDate.of(2022,2,28), Tipologia.SALDO, 11855.30, "SALDO FINALE AL 28/02/2022"),
				movimenti.get(movimenti.size()-2));
		assertEquals(new Movimento(LocalDate.of(2022,2,21),LocalDate.of(2022,2,21), Tipologia.ADDEBITO, -24.90, "PAGAMENTO POS TECNOLOGIA REGGIO EMILIA"),
				movimenti.get(movimenti.size()-6));
	}
	
	@Test
	void testKO_DataContabileErrata1() throws FileNotFoundException, IOException {
		String fakeFile = "CC N.123456789\r\n"
				+ "31/13/22	31/01/22	10.317,81 	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_DataContabileErrata2() throws FileNotFoundException, IOException {
		String fakeFile = "CC N.123456789\r\n"
				+ "32/01/22	31/01/22	10.317,81 	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_DataValutaErrata1() throws FileNotFoundException, IOException {
		String fakeFile = "CC N.123456789\r\n"
				+ "31/01/22	-3/01/22	10.317,81 	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_DataValutaErrata2() throws FileNotFoundException, IOException {
		String fakeFile = "CC N.123456789\r\n"
				+ "31/01/22	37/01/22	10.317,81 	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_SpazioAnzicheTabFraDate() throws FileNotFoundException, IOException {
		String fakeFile = "CC N.123456789\r\n"
				+ "31/01/22 31/01/22	10.317,81 	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}	

	@Test
	void testKO_SpazioAnzicheTabFraDataEImporto() throws FileNotFoundException, IOException {
		String fakeFile = "CC N.123456789\r\n"
				+ "31/01/22	31/01/22  10.317,81 	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_SpazioAnzicheTabFraImportoECausale() throws FileNotFoundException, IOException {
		String fakeFile = "CC N.123456789\r\n"
				+ "31/01/22	31/01/22	10.317,81  SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}

	@Test
	void testKO_ValoriPositiviNonDevonoAvereSegno() throws FileNotFoundException, IOException {
		String fakeFile = "CC N.123456789\r\n"
				+ "31/01/22	31/01/22	10.317,81	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	+91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_PrimaRigaMancaCC() throws FileNotFoundException, IOException {
		String fakeFile = "CD N.123456789\r\n"
				+ "31/01/22	31/01/22	10.317,81	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_PrimaRigaMancaPuntinoDopoN() throws FileNotFoundException, IOException {
		String fakeFile = "CC N 123456789\r\n"
				+ "31/01/22	31/01/22	10.317,81	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_PrimaRigaMancaN() throws FileNotFoundException, IOException {
		String fakeFile = "CC .123456789\r\n"
				+ "31/01/22	31/01/22	10.317,81	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_PrimaRigaNumeroCCNegativo() throws FileNotFoundException, IOException {
		String fakeFile = "CC N.-123456789\r\n"
				+ "31/01/22	31/01/22	10.317,81	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}
	
	@Test
	void testKO_PrimaRigaSpazioExtraNelNumeroCCQuindiTroppiArgomenti() throws FileNotFoundException, IOException {
		String fakeFile = "CC N.1234 56789\r\n"
				+ "31/01/22	31/01/22	10.317,81	SALDO INIZIALE A VOSTRO CREDITO\r\n"
				+ "02/02/22	02/02/22	- 2,90 		IMP.BOLLO CC\r\n"
				+ "07/02/22	07/02/22 	- 61,59 	SPESE GESTIONE\r\n"
				+ "09/02/22	08/02/22 	- 29,14 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "14/02/22	11/02/22 	- 41,04 	PAGAMENTO POS CARBURANTI MODENA\r\n"
				+ "15/02/22	15/02/22 	- 328,99 	SPESA CON CARTA DI CREDITO\r\n"
				+ "16/02/22	16/02/22 	91,28 		CEDOLA SU INVESTIMENTI\r\n"
				+ "18/02/22	17/02/22 	- 89,50 	PAGAMENTO POS FARMACIA BOLOGNA\r\n"
				+ "21/02/22	19/02/22 	- 39,07 	PAGAMENTO POS CARBURANTI REGGIO EMILIA\r\n"
				+ "21/02/22	20/02/22 	- 31,57 	PAGAMENTO POS SUPERMARKET BOLOGNA\r\n"
				+ "21/02/22	21/02/22 	- 24,90 	PAGAMENTO POS TECNOLOGIA REGGIO EMILIA\r\n"
				+ "22/02/22	21/02/22 	- 28,56 	PAGAMENTO POS CARBURANTI BOLOGNA\r\n"
				+ "25/02/22	25/02/22 	2.144,58 	RETRIBUZIONE\r\n"
				+ "28/02/22	27/02/22 	- 21,11 	UTENZE\r\n"
				+ "28/02/22	28/02/22	11.855,30 	SALDO FINALE AL 28/02/2022\r\n"
				+ "02/03/22	02/03/22	- 2,62 		IMP.BOLLO CC\r\n";
		CCReader ccRdr = new MyCCReader();
		assertThrows(BadFileFormatException.class, () ->  ccRdr.readCC(new BufferedReader(new StringReader(fakeFile))));
	}

}
