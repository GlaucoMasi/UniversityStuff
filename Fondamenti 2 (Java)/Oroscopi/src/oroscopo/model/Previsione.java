package oroscopo.model;

import java.util.Objects;
import java.util.Set;

public class Previsione {
	private String previsione;
	private Set<SegnoZodiacale> segni;
	private int valore;
	
	public String getPrevisione() {
		return previsione;
	}
	
	public int getValore() {
		return valore;
	}

	@Override
	public int hashCode() {
		return Objects.hash(previsione, segni, valore);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Previsione other = (Previsione) obj;
		return Objects.equals(previsione, other.previsione) && Objects.equals(segni, other.segni)
				&& valore == other.valore;
	}
	
	public Previsione(String previsione, int valore, Set<SegnoZodiacale> segni) {
		Objects.requireNonNull(previsione, "Il nome della previsione non può essere null");
		if(previsione.isBlank()) throw new IllegalArgumentException("Il nome della previsione non può essere vuoto");
		Objects.requireNonNull(segni, "Il set delle previsioni non può essere null");
		if(valore < 0) throw new IllegalArgumentException("Il valore della previsione non può essere negativo");
		
		this.previsione = previsione;
		this.valore = valore;
		this.segni = segni;
	}
	
	public Previsione(String previsione, int valore) {
		this(previsione, valore, Set.of(SegnoZodiacale.values()));
	}
	
	public String toString() {
		return "Previsione: " + getPrevisione() + ", valore: " + getValore() + ", per i segni: " + segni.toString();
	}
	
	public boolean validaPerSegno(SegnoZodiacale segno) {
		return segni.contains(segno);
	}
}
