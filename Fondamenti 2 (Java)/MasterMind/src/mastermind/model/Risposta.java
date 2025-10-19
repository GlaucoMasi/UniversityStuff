package mastermind.model;

import java.util.StringJoiner;

public class Risposta {
	private int dim;
	private PioloRisposta[] risposta;

	public Risposta(int dim){
		this.dim = dim;
		risposta = new PioloRisposta[dim];
		java.util.Arrays.fill(this.risposta, PioloRisposta.VUOTO);
	}
	
	public int dim() {
		return this.dim;
	}
	
	public PioloRisposta getPiolo(int index) {
		return this.risposta[index];
	}
	
	public void setPiolo(int index, PioloRisposta c) {
		this.risposta[index] = c;
	}
	
	public boolean equals(Risposta that) {
		return java.util.Arrays.deepEquals(this.risposta, that.risposta);
	}
	
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(",");
		
		for(int i = 0; i < this.dim(); i++) {
			sj.add(this.getPiolo(i).toString());
		}
		
		return sj.toString();
	}
	
	public boolean vittoria() {
		for(int i = 0; i < this.dim(); i++) {
			if(this.getPiolo(i) != PioloRisposta.NERO) {
				return false;
			}
		}
		
		return true;
	}
}
