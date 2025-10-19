package oroscopo.controller;

import java.util.List;

import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class MyStrategiaSelezione implements StrategiaSelezione {
	@Override
	public Previsione seleziona(List<Previsione> previsioni, SegnoZodiacale segno) {
		int idx = 0, tot = 0;

		do {
			if(tot++ == previsioni.size()*100) {
				throw new IllegalArgumentException("Impossibile trovare previsione");
			}
			idx = (int) (Math.random()*(previsioni.size()));
		}while(!previsioni.get(idx).validaPerSegno(segno));

		return previsioni.get(idx);
	}
}
