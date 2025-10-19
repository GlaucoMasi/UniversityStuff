package meteodent.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


public class MyGeneratoreBollettino implements GeneratoreBollettino {

	@Override
	public Bollettino generaBollettino(List<Previsione> previsioni, TipoBollettino tipoBollettino) {
		if(previsioni == null || previsioni.isEmpty()) throw new IllegalArgumentException("Lista previsioni nulla o vuota");
		
		if(previsioni.stream().map(Previsione::getLocalita).distinct().count() > 1)
			throw new IllegalArgumentException("Le previsioni non si riferiscono tutte alla stessa localita");		
		
		if(previsioni.stream().map(Previsione::getDataOra).map(LocalDateTime::toLocalDate).distinct().count() > 1)
			throw new IllegalArgumentException("Le previsioni non si riferiscono tutte alla stessa data");

		if(previsioni.stream().map(Previsione::getDataOra).map(LocalDateTime::toLocalTime).distinct().count() < previsioni.size())
			throw new IllegalArgumentException("Più previsioni si riferiscono allo stesso orario");	
		
		Previsione prima = previsioni.getFirst();
		if(!prima.getDataOra().toLocalTime().equals(LocalTime.of(0, 0)))
			previsioni.addFirst(new Previsione(prima.getLocalita(), prima.getDataOra().toLocalDate(),
					LocalTime.of(0, 0), prima.getTemperatura(), prima.getProbabilitaPioggia()));
		
		Previsione ultima = previsioni.getLast();
		if(!ultima.getDataOra().toLocalTime().equals(LocalTime.of(23, 59)))
			previsioni.addLast(new Previsione(ultima.getLocalita(), ultima.getDataOra().toLocalDate(),
					LocalTime.of(23, 59), ultima.getTemperatura(), ultima.getProbabilitaPioggia()));
		
		double mediaPioggia = 0, mediaTemperatura = 0, totale = 0;
		for(int i = 0; i < previsioni.size()-1; i++) {
			Previsione curr = previsioni.get(i), succ = previsioni.get(i+1); 
			
			double lunghezza = Duration.between(curr.getDataOra(), succ.getDataOra()).toMinutes();
			totale += lunghezza;
			
			mediaTemperatura += lunghezza * (curr.getTemperatura().getValue()+succ.getTemperatura().getValue())/2;
			mediaPioggia += lunghezza * (curr.getProbabilitaPioggia().getValue()+succ.getProbabilitaPioggia().getValue())/2;
		}
		
		mediaPioggia /= totale;
		mediaTemperatura /= totale;
		
		int pioggia = (int) Math.round(mediaPioggia), temperatura = (int) Math.round(mediaTemperatura);
		
		String testo = (tipoBollettino == TipoBollettino.DETTAGLIATO
				? previsioni.stream().map(Previsione::toString).collect(Collectors.joining("\n", "", "\n"))
				: "") + SintesiGiornata.getTestoAnnuncio(pioggia) + "\ne temperatura media di " + temperatura + "°C";
	
		return new Bollettino(previsioni.getFirst().getDataOra().toLocalDate(), previsioni.getFirst().getLocalita(), 
				pioggia, temperatura, testo);
	}
	
}
