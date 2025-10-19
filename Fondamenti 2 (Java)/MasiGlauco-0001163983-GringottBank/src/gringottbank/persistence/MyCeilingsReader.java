package gringottbank.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gringottbank.model.Ceiling;
import gringottbank.model.Coin;
import gringottbank.model.CoinAmount;

	 /*
		Harry		20 galeoni
		Hermione	19 galeoni, 20 zellini
		Enrico		200 galeoni
		Ambra		100 galeoni, 20 zellini
		Roberta		100 galeoni, 28 zellini
		Ron			12 galeoni, 10 falci, 6 zellini
	 */

public class MyCeilingsReader implements CeilingsReader {

	@Override
	public List<Ceiling> readCeilings(Reader rdr) throws IOException, BadFileFormatException {
		Objects.requireNonNull(rdr, "Reader fornito nullo");
		BufferedReader br = new BufferedReader(rdr);
		
		String s;
		List<Ceiling> list = new ArrayList<>();
		while((s = br.readLine()) != null) {
			if(s.isBlank()) continue;
			String[] tokens = s.split("\\t+");
			if(tokens.length != 2) throw new BadFileFormatException("Riga malformata: " + s);
			if(tokens[0].isBlank()) throw new BadFileFormatException("Nome mancante: " + s);
			list.add(new Ceiling(tokens[0].trim(), extractAmount(tokens[1].trim())));
		}
		
		return list;
	}
	
	private CoinAmount extractAmount(String importoAsString) throws BadFileFormatException {		
		String[] tokens = importoAsString.split("\\s*,\\s*");
		if(tokens.length < 1 || tokens.length > 3) throw new BadFileFormatException("Importo malformato: " + importoAsString);
		
		CoinAmount amount = new CoinAmount();
		
		int idx = 0;
		String[] names = Coin.getNames();
		
		for(int i = 0; i < tokens.length; i++) {
			String[] twoParts = tokens[i].split("\\s+");
			if(twoParts.length != 2) throw new BadFileFormatException("Valuta malformata: " + tokens[i]);
			
			while(idx < names.length && !twoParts[1].equalsIgnoreCase(names[idx])) idx++;
			if(idx == names.length) throw new BadFileFormatException("Nome valuta malformato o ordine sbagliato: " + tokens[i]);
			
			amount = amount.plus(Coin.of(names[idx]), parsePositive(twoParts[0]));
		}
		
		return amount;
	}

	private int parsePositive(String s) throws BadFileFormatException {
		// METODO DI UTILITA' IN REGALO :)
		int importo;
		try {
			importo = Integer.parseInt(s);
		}
		catch(NumberFormatException e) {
			throw new BadFileFormatException("Formato sotto-importo illegale: numero errato\t" + s);
		}
		if (importo<0) throw new BadFileFormatException("Formato sotto-importo illegale: numero negativo\t" + s);
		return importo;
	}
	
}