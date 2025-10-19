package mastermind.model;

public class MasterMind {
	private Combinazione segreta;
	
	public MasterMind(int lunghezzaCodice){
		this.segreta = new Combinazione(lunghezzaCodice);
		this.sorteggiaCombinazione(this.segreta);
	}
	
	public Risposta calcolaRisposta(Combinazione tentativo) {
		int neri = 0, bianchi = 0;
		for(int i = 0; i < Math.min(this.segreta.dim(), tentativo.dim()); i++) {
			if(this.segreta.getPiolo(i) == tentativo.getPiolo(i)) {
				neri++;
				bianchi--;
			}
		}
		
		int[] occorrenzeCorrette = this.occorrenze(this.segreta);
		int[] occorrenzeTentativo = this.occorrenze(tentativo);
		
		for(int i = 0; i < occorrenzeCorrette.length; i++) {
			bianchi += Math.min(occorrenzeCorrette[i], occorrenzeTentativo[i]);
		}
		
		Risposta ans = new Risposta(tentativo.dim());
		for(int i = 0; i < ans.dim(); i++) {
			if(neri != 0) {
				ans.setPiolo(i, PioloRisposta.NERO);
				neri--;
			}else if(bianchi != 0) {
				ans.setPiolo(i, PioloRisposta.BIANCO);
				bianchi--;
			}else {
				ans.setPiolo(i, PioloRisposta.VUOTO);
			}
		}
		
		return ans;
	}
	
	public String combinazioneSegreta() {
		return segreta.toString();
	}
	
	private int[] occorrenze(Combinazione tentativo) {
		int[] ans = new int[PioloDiGioco.values().length];
		
		for(int i = 0; i < tentativo.dim(); i++) {
			ans[tentativo.getPiolo(i).ordinal()]++;
		}
		
		return ans;
	}
	
	protected void sorteggiaCombinazione(Combinazione segreta) {
		PioloDiGioco[] colori = PioloDiGioco.values();
		
		for(int i = 0; i < segreta.dim(); i++) {
			segreta.setPiolo(i, colori[(int)(Math.random()*(colori.length-1))+1]);
		}
	}
}
