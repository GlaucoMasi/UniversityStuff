package contocorrente.model;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class MyAnalizzatore implements Analizzatore {
	
	private ContoCorrente cc;
	
	public MyAnalizzatore(ContoCorrente cc) {
		if(cc==null) throw new IllegalArgumentException("CC nullo nel costruttore dell'analizzatore dati");
		this.cc=cc;
	}

	@Override
	public double getSaldo(LocalDate data) {
		double current = 0;
		
		for(Movimento m : cc.getMovimenti()) {
			if(m.getDataContabile().isAfter(data)) continue;
			
			if(m.getTipologia() == Tipologia.SALDO) {
				if(Math.abs(current-m.getImporto()) > 0.01) 
					throw new InconsistentBalanceException("Saldo effettivo: " + current + ", saldo previsto: " + m.getImporto());
			}else current += m.getImporto();
		}
		
		return current;
	}
	
	@Override
	public double getSommaPerTipologia(LocalDate data, Tipologia tipologia) {
		if(tipologia != Tipologia.ACCREDITO && tipologia != Tipologia.ADDEBITO) throw new IllegalArgumentException("Tipologia invalida per la somma");
		return cc.getMovimenti().stream()
				.filter(m -> m.getTipologia() == tipologia && !m.getDataContabile().isAfter(data))
				.collect(Collectors.summingDouble(Movimento::getImporto));
	}
	
}
