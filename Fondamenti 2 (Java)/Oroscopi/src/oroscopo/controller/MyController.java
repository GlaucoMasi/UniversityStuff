package oroscopo.controller;

import java.util.Objects;

import oroscopo.model.ArchivioPrevisioni;
import oroscopo.model.Oroscopo;
import oroscopo.model.OroscopoMensile;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class MyController extends Controller {
	private StrategiaSelezione strategiaSelezione;

	public MyController(ArchivioPrevisioni archivio, StrategiaSelezione strategia) {
		super(archivio);
		Objects.requireNonNull(strategia);
		this.strategiaSelezione = strategia;
	}

	@Override
	public SegnoZodiacale[] getSegni() {
		return SegnoZodiacale.values();
	}

	@Override
	public Oroscopo generaOroscopoCasuale(SegnoZodiacale segno) {
		Objects.requireNonNull(segno, "Il segno non può essere null");
		Previsione previsioneAmore = strategiaSelezione.seleziona(super.getArchivio().getPrevisioni("AMORE"), segno);
		Previsione previsioneLavoro = strategiaSelezione.seleziona(super.getArchivio().getPrevisioni("LAVORO"), segno);
		Previsione previsioneSalute = strategiaSelezione.seleziona(super.getArchivio().getPrevisioni("SALUTE"), segno);
		return new OroscopoMensile(segno, previsioneAmore, previsioneLavoro, previsioneSalute);
	}

	@Override
	public Oroscopo[] generaOroscopoAnnuale(SegnoZodiacale segno, int fortunaMin) {
		int media = 0;
		Oroscopo[] ans = new Oroscopo[12];

		while(media < fortunaMin) {
			media = 0;

			for(int i = 0; i < 12; i++) {
				ans[i] = generaOroscopoCasuale(segno);
				media += ans[i].getFortuna();
			}

			media /= 12;
		}

		return ans;
	}
}
