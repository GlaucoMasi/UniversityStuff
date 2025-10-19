package bussy.model;

import java.util.Map;
import java.util.Optional;

public class LineaCircolare extends Linea {
	public LineaCircolare(String id, Map<Integer, Fermata> orariPassaggioAlleFermate) {
		super(id, orariPassaggioAlleFermate);
		if(!super.isCircolare()) throw new IllegalArgumentException("La linea non è circolare");
	}

	public Optional<Percorso> getPercorso(String fermataDa, String fermataA){
		Optional<Percorso> ans = Optional.empty();
		
		try {
			int orarioA = super.getOrarioPassaggioAllaFermata(fermataDa);
			int orarioB = super.getOrarioPassaggioAllaFermata(fermataA);
			if(orarioB > orarioA) ans = Optional.of(new Percorso(fermataDa, fermataA, this, orarioB-orarioA));
			else ans = Optional.of(new Percorso(fermataDa, fermataA, this, getCapolineaFinale().getKey().intValue()-orarioA+orarioB));
		} catch(IllegalArgumentException e) {}
		
		return ans;
	}
}
