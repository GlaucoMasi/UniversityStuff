package bussy.model;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;

public abstract class Linea {
	private String id;
	private Map<Integer, Fermata> orariPassaggioAlleFermate;
	
	public Linea(String id, Map<Integer, Fermata> orariPassaggioAlleFermate) {
		if(orariPassaggioAlleFermate == null) throw new NullPointerException("La mappa non può essere null");
		if(id == null) throw new NullPointerException("La stringa non può essere null");
		if(orariPassaggioAlleFermate.isEmpty()) throw new IllegalArgumentException("La mappa non può essere vuota");
		if(id.isBlank()) throw new IllegalArgumentException("La stringa non può essere vuota");
		this.id = id;
		this.orariPassaggioAlleFermate = orariPassaggioAlleFermate;
	}

	public Entry<Integer, Fermata> getCapolineaIniziale(){
		Optional<Entry<Integer, Fermata>> iniziale = Optional.empty();
		
		for(Entry<Integer, Fermata> entry : getOrariPassaggioAlleFermate().entrySet()) {
			if(iniziale.isEmpty() || iniziale.get().getKey() > entry.getKey()) {
				iniziale = Optional.of(entry);
			}
		}
		
		if(!iniziale.isPresent() || !iniziale.get().getKey().equals(Integer.valueOf(0))) throw new IllegalArgumentException("Non esiste capolinea iniziale");
		return iniziale.get();
	}
	
	public Entry<Integer, Fermata> getCapolineaFinale(){
		Optional<Entry<Integer, Fermata>> finale = Optional.empty();
		
		for(Entry<Integer, Fermata> entry : getOrariPassaggioAlleFermate().entrySet()) {
			if(finale.isEmpty() || finale.get().getKey() < entry.getKey()) {
				finale = Optional.of(entry);
			}
		}
		
		if(!finale.isPresent()) throw new IllegalArgumentException("Non esiste capolinea finale");
		return finale.get();
	}
	
	public int getOrarioPassaggioAllaFermata(String nomeFermata) {
		Optional<Entry<Integer, Fermata>> ans = Optional.empty();
		
		for(Entry<Integer, Fermata> entry : getOrariPassaggioAlleFermate().entrySet()) {
			if(ans.isEmpty() && entry.getValue().getNome().equals(nomeFermata)) {
				ans = Optional.of(entry);
			}
		}
		
		if(!ans.isPresent()) throw new IllegalArgumentException("La linea non fa questa fermata");
		return ans.get().getKey().intValue();
	}
	
	public boolean isCapolineaIniziale(String fermata) {
		return getCapolineaIniziale().getValue().getNome().equals(fermata);
	}
	
	public boolean isCapolineaFinale(String fermata) {
		return getCapolineaFinale().getValue().getNome().equals(fermata);
	}
	
	public boolean isCircolare() {
		return getCapolineaIniziale().getValue().equals(getCapolineaFinale().getValue());
	}
	
	public String getId() {
		return id;
	}
	
	public Map<Integer, Fermata> getOrariPassaggioAlleFermate() {
		return orariPassaggioAlleFermate;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, orariPassaggioAlleFermate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Linea other = (Linea) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(orariPassaggioAlleFermate, other.orariPassaggioAlleFermate);
	}

	@Override
	public String toString() {
		return "Linea [id=" + getId() + ", orariPassaggioAlleFermate=" + getOrariPassaggioAlleFermate() + "]";
	}
	
	public abstract Optional<Percorso> getPercorso(String fermataDa, String fermataA);
}
