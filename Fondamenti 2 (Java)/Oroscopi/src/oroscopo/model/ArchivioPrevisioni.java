package oroscopo.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ArchivioPrevisioni {
	private Map<String, List<Previsione>> mappaPrevisioni;

	public ArchivioPrevisioni(Map<String, List<Previsione>> mappaPrevisioni) {
		Objects.requireNonNull(mappaPrevisioni, "La mappa delle previsioni non può essere null");
		if(mappaPrevisioni.isEmpty()) {
			throw new IllegalArgumentException("La mappa non può essere vuota");
		}
		this.mappaPrevisioni = mappaPrevisioni;
	}

	public ArchivioPrevisioni() {
		this.mappaPrevisioni = Map.of();
	}

	public Map<String, List<Previsione>> getMappaPrevisioni(){
		return mappaPrevisioni;
	}

	public List<Previsione> getPrevisioni(String sezione) {
		Objects.requireNonNull(sezione, "La sezione non può essere null");
		if(sezione.isBlank()) {
			throw new IllegalArgumentException("La sezione non può essere vuota");
		}
		if(!getMappaPrevisioni().containsKey(sezione.toUpperCase())) {
			return List.of();
		} else {
			return getMappaPrevisioni().get(sezione.toUpperCase());
		}
	}

	public Set<String> getSettori(){
		return getMappaPrevisioni().keySet();
	}
}
