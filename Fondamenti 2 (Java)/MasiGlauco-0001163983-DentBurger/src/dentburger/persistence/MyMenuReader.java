package dentburger.persistence;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.Objects;
import java.io.BufferedReader;
import dentburger.model.Prodotto;
import dentburger.model.Categoria;
import dentburger.model.Formatters;
import dentburger.model.Menu;


public class MyMenuReader implements MenuReader {
	
	private static final int    TOKEN_COUNT = 4;
	private static final String SEPARATOR = "\t";
	
	/* ESEMPIO DI FILE
	  	Piatto	Burger	Basic		4,90 €
		Piatto	Burger	Formaggio	7,20 €
		Piatto	Toast	Classico	6,50 €
		...
		Piatto	Pollo	6 Crocchette	5,80 €
		Piatto	Pollo	8 Crocchette	7,00 €
		...
		Contorno	Patatine	Medie	2,70 €
		...
		Bevanda	Cola	Media		3,50 €
		Bevanda	Cola	Grande		4,50 €
		Bevanda	Acqua	Media		1,50 €
		...
		Dessert	Gelato	Panna e cioccolato	1,80 €
		Dessert	Gelato	Panna e fragola		1,80 €
		...
	 */
	
	private Prodotto leggiProdotto(String s) throws BadFileFormatException {
		String[] tokens = s.split(SEPARATOR+"+");
		if(tokens.length != TOKEN_COUNT) throw new BadFileFormatException("Prodotto mal formato: " + s);

		Categoria c;
		try {
			c = Categoria.valueOf(tokens[0].trim().toUpperCase());
		} catch (IllegalArgumentException e) {			
			throw new BadFileFormatException("Categoria inesistente o malformata: " + tokens[0].trim());
		}
		
		String genere = tokens[1].trim();
		if(genere.isBlank()) throw new BadFileFormatException("Il genere è vuoto");
		String specifica = tokens[2].trim();
		if(specifica.isBlank()) throw new BadFileFormatException("La specifica è vuota");
		
		double prezzo;
		try {
			prezzo = Formatters.priceFormatter.parse(tokens[3].trim()).doubleValue();
		} catch (ParseException e) {
			throw new BadFileFormatException("Prezzo malformato: " + tokens[3].trim());
		}
		
		if(prezzo <= 0 || tokens[3].contains(".")) throw new BadFileFormatException("Prezzo malformato: " + prezzo);
		
		return new Prodotto(c, genere, specifica, prezzo);
	}
	
	@Override
	public Menu leggiProdotti(Reader reader) throws IOException, BadFileFormatException {
		// carica da un apposito Reader (già aperto) i dati necessari, restituendo un’istanza di Menu 
		// opportunamente popolata. Il metodo lancia IllegalArgumentException con opportuno messaggio 
		// d’errore in caso di argomento (reader) nullo, BadFileFormatException con messaggio d’errore 
		// appropriato in caso di problemi nel formato del file (mancanza/eccesso di elementi, categorie 
		// inesistenti, errori nel formato dei prezzi, etc.); in particolare, è richiesto di controllare 
		// con cura il formato del prezzo, verificando che non siano presenti punti decimali e che il 
		// numero sia letto interamente e non vi siano anomalie di sorta. Il metodo lancia infine 
		// IOException in caso di altri problemi di I/O.
		//
		// ***** DA FARE *****
		
		Objects.requireNonNull(reader, "Reader fornito nullo");
		
		String in;
		Menu menu = new Menu();
		BufferedReader br = new BufferedReader(reader);
		
		while((in = br.readLine()) != null) {
			menu.add(leggiProdotto(in));
		}
		
		return menu;
	}

}
