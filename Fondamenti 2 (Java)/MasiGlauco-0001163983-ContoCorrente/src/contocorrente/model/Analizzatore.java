package contocorrente.model;

import java.time.LocalDate;

public interface Analizzatore {
	
	public double getSaldo(LocalDate data);
	public double getSommaPerTipologia(LocalDate data, Tipologia tipologia);
	
}
