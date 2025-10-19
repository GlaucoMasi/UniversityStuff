package bussy.model;

import java.util.Map;
import java.util.Optional;

public class LineaPaP extends Linea {
	public LineaPaP(String id, Map<Integer, Fermata> orariPassaggioAlleFermate) {
		super(id, orariPassaggioAlleFermate);
		if(super.isCircolare()) throw new IllegalArgumentException("La linea è circolare");
	}

	public Optional<Percorso> getPercorso(String fermataDa, String fermataA){
		Optional<Percorso> ans = Optional.empty();
		
		try {
			int orarioA = super.getOrarioPassaggioAllaFermata(fermataDa);
			int orarioB = super.getOrarioPassaggioAllaFermata(fermataA);
			if(orarioB > orarioA) ans = Optional.of(new Percorso(fermataDa, fermataA, this, orarioB-orarioA));
		} catch(IllegalArgumentException e) {}
	
		return ans;
	}
}
