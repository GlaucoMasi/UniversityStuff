package mastermind.model;

import java.util.StringJoiner;

public class Combinazione {
	private int dim;
	private PioloDiGioco[] combinazione;

	public Combinazione(int dim){
		this.dim = dim;
		combinazione = new PioloDiGioco[dim];
		java.util.Arrays.fill(this.combinazione, PioloDiGioco.VUOTO);
	}
	
	public int dim() {
		return this.dim;
	}
	
	public PioloDiGioco getPiolo(int index) {
		return this.combinazione[index];
	}
	
	public void setPiolo(int index, PioloDiGioco c) {
		this.combinazione[index] = c;
	}
	
	public boolean equals(Combinazione that) {
		return java.util.Arrays.deepEquals(this.combinazione, that.combinazione);
	}
	
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(",");
		
		for(int i = 0; i < this.dim(); i++) {
			sj.add(this.getPiolo(i).toString());
		}
		
		return sj.toString();
	}
}
