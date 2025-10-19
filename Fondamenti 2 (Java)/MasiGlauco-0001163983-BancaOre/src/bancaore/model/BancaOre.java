package bancaore.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BancaOre {

	private Cedolino cedolino;
	private Duration saldoOrePrecedente, totalePermessiAOre, totalePermessi;
	private List<VoceBancaOre> voci;
	
	public BancaOre(Cedolino cedolino) {
		if (cedolino==null) throw new IllegalArgumentException("cedolino nullo");
		this.cedolino = cedolino;
		this.saldoOrePrecedente = cedolino.getSaldoOrePrecedente();
		this.totalePermessi=Duration.ofHours(0);
		this.totalePermessiAOre=Duration.ofHours(0);
		voci = popolaLista();
	}
	
	private List<VoceBancaOre> popolaLista() {
		List<VoceBancaOre> listaVoci = new ArrayList<>();
		var primoGiornoDelMese  = cedolino.getData().withDayOfMonth(1);
		var ultimoGiornoDelMese = cedolino.getData().with(TemporalAdjusters.lastDayOfMonth());
		for (LocalDate d=primoGiornoDelMese; !d.isAfter(ultimoGiornoDelMese); d=d.plusDays(1)) {
			listaVoci.add(sintetizzaVoceBancaOre(d));
		}
		return listaVoci;
	}
	

	private VoceBancaOre sintetizzaVoceBancaOre(LocalDate d) {		
		Duration oreSvolte = cedolino.getVoci().stream()
				.filter(vc -> vc.getData().equals(d) && vc.getCausale().isEmpty())
				.map(vc -> Duration.between(vc.getOraEntrata(), vc.getOraUscita()))
				.reduce((a, b) -> a.plus(b))
				.orElse(Duration.ZERO);
		
		totalePermessiAOre = totalePermessiAOre.plus(
				cedolino.getVoci().stream()
				.filter(vc -> vc.getData().equals(d) 
						&& vc.getCausale().isPresent() && vc.getCausale().get().equals(Causale.RIPOSO_COMPENSATIVO_A_ORE))
				.map(vc -> Duration.between(vc.getOraEntrata(), vc.getOraUscita()))
				.reduce((a, b) -> a.plus(b))
				.orElse(Duration.ZERO)
				);
		
		totalePermessi = totalePermessi.plus(
				cedolino.getVoci().stream()
				.filter(vc -> vc.getData().equals(d) 
						&& vc.getCausale().isPresent() && vc.getCausale().get().equals(Causale.RIPOSO_COMPENSATIVO))
				.map(vc -> Duration.between(vc.getOraEntrata(), vc.getOraUscita()))
				.reduce((a, b) -> a.plus(b))
				.orElse(Duration.ZERO)
				);
		
		Duration orePreviste = cedolino.getSettimanaLavorativa().getOreLavorative(d.getDayOfWeek());
		Duration differenza = oreSvolte.minus(orePreviste);
		saldoOrePrecedente = saldoOrePrecedente.plus(differenza);
		
		return new VoceBancaOre(d, orePreviste, oreSvolte, saldoOrePrecedente);
	}

	public List<VoceCedolino> getDettagli(LocalDate d) {
		var result = new ArrayList<VoceCedolino>();
		for (VoceCedolino v : cedolino.getVoci())
			if(v.getData().equals(d)) result.add(v);
		return result;
	}
	
	public Duration getSaldoOrePrecedente() {
		return saldoOrePrecedente;
	}

	public Cedolino getCedolino() {
		return cedolino;
	}

	public List<VoceBancaOre> getVoci() {
		return Collections.unmodifiableList(voci);
	}

	@Override
	public String toString() {
		return "BancaOre [cedolino=" + cedolino + ", saldoOre=" + saldoOrePrecedente + ", voci=" + voci + "]";
	}

	public Duration getTotalePermessiAOre() {
		return totalePermessiAOre;
	}

	public Duration getTotalePermessi() {
		return totalePermessi;
	}
		
}


