package spesesanitarie.model;

import java.util.List;
import java.util.stream.Collectors;


public class AnalizzatoreSpese {

	private List<DocumentoDiSpesa> listaSpese;

	public AnalizzatoreSpese(List<DocumentoDiSpesa> listaSpese) {
		if (listaSpese==null || listaSpese.isEmpty()) throw new IllegalArgumentException("la lista spese non può essere nulla né vuota");
		this.listaSpese = listaSpese;
	}

	public List<DocumentoDiSpesa> getListaSpese() {
		return listaSpese;
	}

	@Override
	public String toString() {
		return "AnalizzatoreSpese [listaSpese=" + listaSpese + "]";
	}
	
	public double somma(Tipologia t){
		return filtraPer(t).stream()
				.map(DocumentoDiSpesa::getVoci)
				.flatMap(List::stream)
				.filter(voce -> voce.getTipologia().equals(t))
				.collect(Collectors.summingDouble(VoceDiSpesa::getImporto));
	}
	
	public List<DocumentoDiSpesa> filtraPer(Tipologia t){
		return getListaSpese().stream()
				.filter(documento -> documento.contieneVoce(t))
				.toList();
	}

}
