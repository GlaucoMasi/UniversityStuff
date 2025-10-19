package contocorrente.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

import contocorrente.model.Movimento;
import contocorrente.model.ContoCorrente;
import contocorrente.model.Tipologia;


public class MyCCReader implements CCReader {

	/*  CC N.123456789
	 * 	31/01/22 31/01/22	10.317,81 	SALDO INIZIALE A VOSTRO CREDITO
		02/02/22 02/02/22	- 2,90 		IMP.BOLLO CC
		07/02/22 07/02/22 	- 61,59 	SPESE GESTIONE
		...
	 * */
	
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);
	private NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.ITALY);

	@Override
	public ContoCorrente readCC(Reader rdr) throws IOException {
		if(rdr == null) throw new BadFileFormatException("Reader nullo");
		
		BufferedReader br = new BufferedReader(rdr);
		String s = br.readLine();
		
		String[] tokens = s.split("\s+");
		
		if(tokens.length < 2 || tokens.length > 3) throw new BadFileFormatException("Prima riga malformata: " + s);
		if(!tokens[0].trim().equalsIgnoreCase("CC")) throw new BadFileFormatException("Dicitura CC mancante: " + s);
		
		if(!switch(tokens.length) {
			case 2 -> tokens[1].toUpperCase().startsWith("N.");
			case 3 -> tokens[1].equalsIgnoreCase("N.");
			default -> true;
		}) throw new BadFileFormatException("Dicitura N. mancante: " + s);
		
		long numero;
		String numeroStringa = (tokens.length == 2 ? tokens[1].substring("N.".length()) : tokens[2]);
		try {
			numero = Long.parseLong(numeroStringa);
			if(numero < 0) throw new BadFileFormatException("Il numero del conto non può essere negativo");
		} catch(NumberFormatException e) {
			throw new BadFileFormatException("Numero del conto malformato: " + numeroStringa);
		}
		
		ContoCorrente cc = new ContoCorrente(numero);
		
		while((s = br.readLine()) != null) {
			if(s.isBlank()) continue;
			
			tokens = s.split("\t+");
			if(tokens.length != 4) throw new BadFileFormatException("Numero sbagliato di parti: " + s);
						
			try {
				LocalDate dataContabile = LocalDate.parse(tokens[0].trim(), dateFormatter);
				LocalDate dataValuta = LocalDate.parse(tokens[1].trim(), dateFormatter);
				
				double importo = numberFormatter.parse(tokens[2].replace(" ", "")).doubleValue();
				
				Tipologia tipologia;
				if(tokens[3].toUpperCase().contains("SALDO")){
					if(tokens[3].toUpperCase().contains("SALDO INIZIALE")) tipologia = Tipologia.ACCREDITO;
					else tipologia = Tipologia.SALDO;
				}else {
					if(importo > 0) tipologia = Tipologia.ACCREDITO;
					else if(importo == 0) tipologia = Tipologia.NULLO;
					else tipologia = Tipologia.ADDEBITO;
				}
				
				cc.aggiungi(new Movimento(dataContabile, dataValuta, tipologia, importo, tokens[3]));
			} catch(DateTimeParseException e) {
				throw new BadFileFormatException("Data invalida: " + e.getMessage());
			} catch(ParseException e) {
				throw new BadFileFormatException("Importo invalido: " + e.getMessage());
			} catch(IllegalArgumentException e) {
				throw new BadFileFormatException(e);
			}
		}
		
		return cc;
	}
}