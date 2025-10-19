package cruciverba.model;

import java.util.*;


public class Schema {
	
	private int numRighe, numColonne;
	private int[][] griglia;
	private Map<Character, Integer> mappa;
	
	public Map<Character, Integer> getAllMappings() {
		return Map.copyOf(mappa);
	}
	
	public OptionalInt getMapping(char c) {
		if (!Character.isUpperCase(c)) throw new IllegalArgumentException("Illegal character in Schema::setMapping: " + c);
		var value = mappa.get(c);
		return (value==null) ? OptionalInt.empty() : OptionalInt.of(value);
	}
	
	public boolean setMapping(char c, int v) {
		if (!Character.isUpperCase(c)) throw new IllegalArgumentException("Illegal character in Schema::setMapping: " + c);
		if (v<1 || v>26) throw new IllegalArgumentException("Illegal value in Schema::setMapping: " + v);
		mappa.putIfAbsent(c, v);
		return true;
	}
	
	public boolean unsetMapping(char c, int v) {
		if (!Character.isUpperCase(c)) throw new IllegalArgumentException("Illegal character in Schema::unsetMapping: " + c);
		if (v<1 || v>26) throw new IllegalArgumentException("Illegal value in Schema::unsetMapping: " + v);
		return mappa.remove(c, v);
	}
	
	public Schema(int numRighe, int numColonne) {
		if (numRighe<1 || numColonne<1) throw new IllegalArgumentException("Illegal size in Schema:: " + numRighe + " x " + numColonne);
		this.numRighe = numRighe;
		this.numColonne = numColonne;
		this.griglia = new int[numRighe][numColonne];
		this.mappa = new HashMap<>();
		mappa.put(Cruciverba.NERA, 0);
	}
	
	public int getNumRighe() {
		return numRighe;
	}

	public int getNumColonne() {
		return numColonne;
	}
	
	private void validaIndiceRiga(int indiceRiga) {
		if (indiceRiga<0 || indiceRiga>=numRighe) throw new IllegalArgumentException("Riga fuori range: " + indiceRiga);
	}
	
	private void validaIndiceColonna(int indiceColonna) {
		if (indiceColonna<0 || indiceColonna>=numColonne) throw new IllegalArgumentException("Colonna fuori range: " + indiceColonna);
	}
	
	public int getCella(int indiceRiga, int indiceColonna) {
		validaIndiceRiga(indiceRiga); validaIndiceColonna(indiceColonna);
		return griglia[indiceRiga][indiceColonna];
	}
	
	public void setCella(int indiceRiga, int indiceColonna, int valore) {
		validaIndiceRiga(indiceRiga); validaIndiceColonna(indiceColonna);
		griglia[indiceRiga][indiceColonna] = valore;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int r=0; r<numRighe; r++) { 
			sb.append("|");
			for(int c=0; c<numColonne; c++) {
				var num = griglia[r][c];
				sb.append( num==0 ? String.format("%2c", Cruciverba.NERA) : String.format("%02d", num));
				sb.append("|"); // se non si vuole il separatore verticale: sb.append(Cruciverba.BLANK);
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString(); // + System.lineSeparator() + mappa; // ******* HERE
	}
	
	public int[] getRiga(int indiceRiga) {
		validaIndiceRiga(indiceRiga);
		return griglia[indiceRiga];
	}

	public int[] getColonna(int indiceColonna) {
		validaIndiceColonna(indiceColonna);
		var colonna = new int[numRighe];
		for (int i=0; i<numRighe; i++)
			colonna[i] = griglia[i][indiceColonna];
		return colonna;
	}
	
}
