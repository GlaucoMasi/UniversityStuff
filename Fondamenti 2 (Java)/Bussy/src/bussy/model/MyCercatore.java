package bussy.model;

import java.util.Map;
import java.util.TreeSet;
import java.util.Optional;
import java.util.SortedSet;
import java.util.Map.Entry;
import java.util.OptionalInt;

public class MyCercatore implements Cercatore {
	private Map<String, Linea> mappaLinee;

	public MyCercatore(Map<String, Linea> mappaLinee) {
		if(mappaLinee == null) throw new NullPointerException("La mappa non può essere null");
		if(mappaLinee.isEmpty()) throw new IllegalArgumentException("La mappa non può essere vuota");
		this.mappaLinee = mappaLinee;
	}
	
	public Map<String, Linea> getMappaLinee(){
		return mappaLinee;
	}
	
	public SortedSet<Percorso> cercaPercorsi(String fermataDa, String fermataA, OptionalInt durataMax){
		if(fermataDa == null) throw new NullPointerException("La fermata di partenza non può essere null");
		if(fermataA == null) throw new NullPointerException("La fermata di arrivo non può essere null");
		if(fermataDa.isBlank()) throw new IllegalArgumentException("La fermata di partenza non può essere vuota");
		if(fermataA.isBlank()) throw new IllegalArgumentException("La fermata di arrivo non può essere vuota");
		if(durataMax == null) throw new NullPointerException("L'optional non può essere null");

		SortedSet<Percorso> ans = new TreeSet<Percorso>();
		
		for(Entry<String, Linea> entry : getMappaLinee().entrySet()) {
			Optional<Percorso> curr = entry.getValue().getPercorso(fermataDa, fermataA);
			if(curr.isPresent() && (durataMax.isEmpty() || curr.get().getDurata() <= durataMax.getAsInt())) {
				ans.add(curr.get());
			}
		}
		
		return ans;
	}
}
