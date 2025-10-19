package pianodistudi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class PianoDiStudi implements Piano {	
	private Map<Tipologia, List<AttivitaFormativa>> piano;
	private StringBuilder logger;
	
	public PianoDiStudi() {
		this.piano=new HashMap<>();
		this.logger = new StringBuilder();
		for (Tipologia t : Tipologia.values())
			piano.put(t, new ArrayList<>());
	}
	
	public String logVerifica() {
		return logger.toString();
	}
	
	public void inserisci(AttivitaFormativa af, Tipologia t) {
		if(piano.values().stream().anyMatch(list -> list.contains(af)))
			throw new IllegalArgumentException("Attivita formativa già presente nel piano");
		
		piano.get(t).add(af);
	}

	public void rimuovi(AttivitaFormativa af, Tipologia t) {
		System.out.println(af + " " + t + " " + piano.get(t));
		if(!piano.get(t).contains(af)) throw new IllegalArgumentException("Attivita formativa non presente per la tipologia scelta");
		
		piano.get(t).remove(af);
	}

	@Override
	public String toString() {
		return "PianoDiStudi [piano=" + piano + "]";
	}

	@Override
	public int getCfu(Tipologia t) {
		return piano.get(t).stream().mapToInt(af -> af.getCfu()).reduce(0, (a,b) -> a+b);
	}
	
	@Override
	public int getCfuTot() {
		return Stream.of(Tipologia.values()).mapToInt(this::getCfu).reduce(0, (a,b) -> a+b);
	}
	
	@Override
	public List<AttivitaFormativa> getAttivitaFormative(Tipologia t) {
		return piano.get(t);
	}
	
	//------------
	
	public boolean verificaOrdinamento(Ordinamento ord) {
		if(ord == null) throw new IllegalArgumentException("Ordinamento nullo");
		logger.delete(0, logger.length());
		
		boolean esito = true;
		
		for(Tipologia t : piano.keySet()) {
			String dataCfu = getCfu(t) + ", range: " + ord.getRange(t) + System.lineSeparator();
			if(!ord.getRange(t).contains(getCfu(t))) {
				esito = false;
				logger.append("Crediti incosistenti in " + t + ": " + dataCfu);
			}else logger.append("Crediti corretti in " + t + ": " + dataCfu);
			
			Optional<List<Ssd>> settori = ord.getSettori(t);
			if(!settori.get().contains(Ssd.QUALSIASI) && piano.get(t).stream().anyMatch(af -> !settori.get().contains(af.getSsd()))) {
				esito = false;
				logger.append("Una o più attività formative con ssd invalido della tipologia " + t + System.lineSeparator());
			}else logger.append("Ssd validi per le attività formative della tipologia " + t + System.lineSeparator());
		}
		
		if(getCfuTot() >= Piano.CFULT) logger.append("Cfu totali: " + getCfuTot() + System.lineSeparator());
		else {
			esito = false;
			logger.append("Numero di crediti troppo basso: " + getCfuTot() + " < " + Piano.CFULT + System.lineSeparator());
		}
		
		return esito;
	}

}
