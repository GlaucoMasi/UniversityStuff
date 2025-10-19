package zannotaxi.model;

import java.util.Optional;

public interface ITariffaTaxi {
	public String getNome();
	public Optional<Scatto> getScattoCorrente(int tempoTrascorsoDaUltimoScatto, double spazioPercorsoDaUltimoScatto, double costoCorrente);
	public double getValoreScatto();
}
