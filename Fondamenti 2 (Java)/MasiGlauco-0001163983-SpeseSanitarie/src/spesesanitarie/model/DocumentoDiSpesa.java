package spesesanitarie.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentoDiSpesa {
	private LocalDate data;
	private String emittente;
	private double importo;
	private List<VoceDiSpesa> items;
	
	public DocumentoDiSpesa(LocalDate data, String emittente, double importo, List<VoceDiSpesa> items) {
		verificaPrecondizioni(data, emittente, importo, items);
		verificaCongruenzaVoci(importo, items);
		this.data = data;
		this.emittente = emittente;
		this.importo = importo;
		this.items = items;
	}

	private void verificaPrecondizioni(LocalDate data, String emittente, double importo, List<VoceDiSpesa> items) {
		if(data == null) throw new IllegalArgumentException("LocalDate data è nulla");
		if(emittente == null) throw new IllegalArgumentException("String emittente è nulla");
		if(items == null) throw new IllegalArgumentException("Lista items è nulla");
		if(items.isEmpty()) throw new IllegalArgumentException("Lista items è vuota");
		if(!Double.isFinite(importo) || importo < 0) throw new IllegalArgumentException("L'importo non è un numero non negativo");
	}

	private void verificaCongruenzaVoci(double importo, List<VoceDiSpesa> items) {
		double totale = items.stream().collect(Collectors.summingDouble(VoceDiSpesa::getImporto));
		if(Math.abs(importo-totale) > 0.01) throw new IllegalArgumentException("La somma degli importi delle voci non coincide con l'importo");
	}

	public LocalDate getData() {
		return data;
	}

	public String getEmittente() {
		return emittente;
	}

	public double getImporto() {
		return importo;
	}

	public List<VoceDiSpesa> getVoci() {
		return items;
	}

	public boolean contieneVoce(Tipologia t) {
		return items.stream().anyMatch(voce -> voce.getTipologia()==t);
	}
	
	@Override
	public String toString() {
		return Formatters.itDateFormatter.format(getData()) + " "
				+ getEmittente() + " "
				+ Formatters.itPriceFormatter.format(getImporto()) + ", di cui:\n  "
				+ getVoci().stream().map(VoceDiSpesa::toString)
					.collect(Collectors.joining("\n  "));
	}

}
