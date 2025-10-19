package tangenziale.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record Percorso(List<Tratta> tratte) {
	
	public Percorso {
		Objects.requireNonNull(tratte, "Percorso: tratte non può essere null");
		if(tratte.size()>3) throw new IllegalArgumentException("Percorso non può consistere di più di tre tratte");
		var numeroAutostradeDistinte = tratte.stream().map(Tratta::autostrada).map(Autostrada::id).distinct().count();
		if(tratte.size()>numeroAutostradeDistinte) throw new IllegalArgumentException("Percorso non può prevedere due volte la stessa autostrada");
	}
	
	public final static double COSTO_KM = 0.0755;

	public int lunghezza() {
		return tratte.stream().mapToInt(Tratta::km).sum();
	}
	
	public int lunghezza(Tipologia tipologia) {
		return tratte.stream().filter(tratta -> tratta.autostrada().tipologia()==tipologia).mapToInt(Tratta::km).sum();
	}
	
	@Override public String toString() {
		if (tratte.size()==0) return "Percorso vuoto";
		var inizio = tratte.get(0).inizio();
		var fine   = tratte.get(tratte.size()-1).fine();
		return "Percorso " + (tratte.size()==1 ? "diretto" : "indiretto") + " da " + inizio + " a " + fine + ", km " + lunghezza() + System.lineSeparator() +
				tratte.stream().map(tratta -> "\t"+tratta.toString()).collect(Collectors.joining(System.lineSeparator()));
	}
	
	public double costo() {
		return COSTO_KM * lunghezzaUtile();
	}

	public int lunghezzaUtile() {
		Tipologia start = tratte.getFirst().autostrada().tipologia(), end = tratte.getLast().autostrada().tipologia();
		int tangenziale = lunghezza(Tipologia.TANGENZIALE);
		int lunghezzaTangenziale;
		
		if(start != end) lunghezzaTangenziale = lunghezza(Tipologia.AUTOSTRADA) > 40 ? tangenziale : 0;
		else lunghezzaTangenziale = start == Tipologia.TANGENZIALE ? 0 : tangenziale;
		
		return lunghezzaTangenziale + lunghezza(Tipologia.AUTOSTRADA);
	}
	
}
