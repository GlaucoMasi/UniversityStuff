package australia.elections.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Scrutinio {

	private Map<String,List<Scheda>> mappa;
	private long totaleVoti;
	private StringBuilder logger;
	
	public Scrutinio(List<Scheda> schedeLette) {
		Objects.requireNonNull(schedeLette, "schede lette non possono essere null");
		if (schedeLette.isEmpty()) throw new IllegalArgumentException("La lista delle schede lette non può essere vuota");
		this.mappa = schedeLette.stream().collect(Collectors.groupingBy(
												scheda -> scheda.candidatiInOrdineDiPreferenza().get(0)
											));
		this.totaleVoti = schedeLette.size();
		this.logger = new StringBuilder();
	}
	
	public Risultato scrutina() {
		Set<String> candidati = mappa.keySet();
		long maxVoti = mappa.get(candidati.stream().max(Comparator.comparing(cand -> mappa.get(cand).size())).get()).size();
		Map<String, Long> situazione = candidati.stream().collect(Collectors.toMap(Function.identity(), cand -> Long.valueOf(mappa.get(cand).size())));
		logger.append("log: " + situazione + ", maxVoti = " + maxVoti + System.lineSeparator());
		
		while(noMaggioranza(maxVoti)) {
			String peggiorCandidato = candidati.stream().min(Comparator.comparing(cand -> mappa.get(cand).size())).get();
			
			for(Scheda scheda : mappa.get(peggiorCandidato)) mappa.get(scheda.successivoFra(peggiorCandidato, candidati).get()).add(scheda);

			logger.append("log: rimozione candidato " + peggiorCandidato + System.lineSeparator());
			
			mappa.get(peggiorCandidato).clear();
			candidati.remove(peggiorCandidato);
			
			situazione = candidati.stream().collect(Collectors.toMap(Function.identity(), cand -> Long.valueOf(mappa.get(cand).size())));
			maxVoti = mappa.get(candidati.stream().max(Comparator.comparing(cand -> mappa.get(cand).size())).get()).size();
			logger.append("log: " + situazione + ", maxVoti = " + maxVoti + System.lineSeparator());
		}
		
		logger.append("risultato " + situazione + System.lineSeparator());
		return new Risultato(situazione);
	}

	private boolean noMaggioranza(long maxVoti) {
		return maxVoti< totaleVoti/2 + 1;
	}
	
	public long getTotaleVoti() {
		return totaleVoti;
	}
	
	public String getLog() {
		return logger.toString();
	}
}
