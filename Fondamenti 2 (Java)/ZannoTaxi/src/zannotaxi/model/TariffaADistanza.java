package zannotaxi.model;

import java.util.Optional;

public class TariffaADistanza implements ITariffaTaxi {
	private String nome;
	private double velocitaMinima, velocitaMassima, costoMinimo, costoMassimo, valoreScatto, distanzaDiScatto;
	
	public TariffaADistanza(String nome, double velocitaMinima, double velocitaMassima, double costoMinimo, double costoMassimo, double valoreScatto, double distanzaDiScatto) {
		this.nome = nome;
		this.velocitaMinima = velocitaMinima;
		this.velocitaMassima = velocitaMassima;
		this.costoMinimo = costoMinimo;
		this.costoMassimo = costoMassimo;
		this.valoreScatto = valoreScatto;
		this.distanzaDiScatto = distanzaDiScatto;
	}

	public String getNome() {
		return nome;
	}

	public double getVelocitaMinima() {
		return velocitaMinima;
	}

	public double getVelocitaMassima() {
		return velocitaMassima;
	}

	public double getCostoMinimo() {
		return costoMinimo;
	}

	public double getCostoMassimo() {
		return costoMassimo;
	}

	public double getValoreScatto() {
		return valoreScatto;
	}

	public double getDistanzaDiScatto() {
		return distanzaDiScatto;
	}
	
	public Optional<Scatto> getScattoCorrente(int tempoTrascorsoDaUltimoScatto, double spazioPercorsoDaUltimoScatto, double costoCorrente){
		double velocita = spazioPercorsoDaUltimoScatto/1000/(tempoTrascorsoDaUltimoScatto/3600.0);
		
		if(velocita < velocitaMinima || velocita >= velocitaMassima || costoCorrente < costoMinimo || costoCorrente >= costoMassimo || Math.round(spazioPercorsoDaUltimoScatto) < distanzaDiScatto) return Optional.empty();
		
		int scatti = (int) (Math.round(spazioPercorsoDaUltimoScatto)/distanzaDiScatto);
		return Optional.of(new Scatto((int) (distanzaDiScatto*scatti/spazioPercorsoDaUltimoScatto*tempoTrascorsoDaUltimoScatto), scatti*distanzaDiScatto, scatti*valoreScatto));
	}
}
