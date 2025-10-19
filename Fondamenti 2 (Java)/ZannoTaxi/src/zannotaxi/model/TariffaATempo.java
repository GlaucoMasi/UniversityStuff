package zannotaxi.model;

import java.util.Optional;

public class TariffaATempo implements ITariffaTaxi {
	private String nome;
	private int tempoDiScatto;
	private double velocitaMinima, velocitaMassima, valoreScatto;
	
	public TariffaATempo(String nome, double velocitaMinima, double velocitaMassima, double valoreScatto, int tempoDiScatto) {
		this.nome = nome;
		this.velocitaMinima = velocitaMinima;
		this.velocitaMassima = velocitaMassima;
		this.valoreScatto = valoreScatto;
		this.tempoDiScatto = tempoDiScatto;
	}

	public String getNome() {
		return nome;
	}

	public int getTempoDiScatto() {
		return tempoDiScatto;
	}

	public double getVelocitaMinima() {
		return velocitaMinima;
	}

	public double getVelocitaMassima() {
		return velocitaMassima;
	}

	public double getValoreScatto() {
		return valoreScatto;
	}
	
	public Optional<Scatto> getScattoCorrente(int tempoTrascorsoDaUltimoScatto, double spazioPercorsoDaUltimoScatto, double costoCorrente){
		double velocita = spazioPercorsoDaUltimoScatto/1000/(tempoTrascorsoDaUltimoScatto/3600.0);
		
		if(velocita < velocitaMinima || velocita >= velocitaMassima || tempoTrascorsoDaUltimoScatto < tempoDiScatto) return Optional.empty();
		
		int scatti = tempoTrascorsoDaUltimoScatto/tempoDiScatto;
		return Optional.of(new Scatto(scatti*tempoDiScatto, spazioPercorsoDaUltimoScatto/tempoTrascorsoDaUltimoScatto*tempoDiScatto*scatti, scatti*valoreScatto));
	}
}
