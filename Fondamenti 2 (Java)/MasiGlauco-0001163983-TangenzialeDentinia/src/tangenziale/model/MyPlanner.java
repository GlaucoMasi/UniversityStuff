package tangenziale.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class MyPlanner implements Planner {

	private Map<String, Autostrada> rete;

	public MyPlanner(Map<String,Autostrada> rete) {
		Objects.requireNonNull(rete, "La rete non può essere null");
		if(rete.isEmpty()) throw new IllegalArgumentException("La rete non può essere vuota");
		if(!rete.values().stream().anyMatch(s -> s.tipologia() == Tipologia.TANGENZIALE)) 
			throw new IllegalArgumentException("La rete deve contenere almeno la tangenziale");
		
		this.rete = rete;
	}

	@Override
	public Percorso trovaPercorso(String da, String a) {		
		Objects.requireNonNull(da, "Casello di partenza non può essere null");
		Objects.requireNonNull(a, "Casello di arrivo non può essere null");
		
		Optional<Autostrada> start = rete.values().stream().filter(s -> s.profilo().values().contains(da)).findFirst();
		Optional<Autostrada> end = rete.values().stream().filter(s -> s.profilo().values().contains(a)).findFirst();
		
		if(start.isEmpty()) throw new IllegalArgumentException("Il casello di partenza non esiste");
		if(end.isEmpty()) throw new IllegalArgumentException("Il casello di arrivo non esiste");
		
		if(start.get().equals(end.get())) return new Percorso(List.of(new Tratta(da, a, start.get())));
		
		List<Tratta> percorso = new ArrayList<>();
		if(start.get().tipologia() == Tipologia.AUTOSTRADA) {
			percorso.add(new Tratta(da, Autostrada.TANGENZIALE, start.get()));
			if(end.get().tipologia() == Tipologia.AUTOSTRADA) {
				percorso.add(new Tratta(start.get().id(), end.get().id(), rete.get(Autostrada.TANGENZIALE)));
				percorso.add(new Tratta(Autostrada.TANGENZIALE, a, end.get()));
			}else percorso.add(new Tratta(start.get().id(), a, end.get()));
		}else {
			percorso.add(new Tratta(da, end.get().id(), start.get()));
			percorso.add(new Tratta(Autostrada.TANGENZIALE, a, end.get()));
		}
		
		return new Percorso(percorso);
	}

}

