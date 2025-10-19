package cruciverba.model;

import java.util.HashMap;
import java.util.Map;


public class SchemaNumerato extends Schema {
	
	private Cruciverba cruciverba;
	private Map<Integer,Character> mappaAssociazioni;
	
	public SchemaNumerato(Cruciverba cruciverba) {
		super(cruciverba.getNumRighe(), cruciverba.getNumColonne());
		this.cruciverba=cruciverba;
		this.mappaAssociazioni= new HashMap<>();
		numeraCelle();
	}
	
	public Cruciverba getCruciverba() {
		return cruciverba;
	}

	private void numeraCelle() {
		int n = 1;
		for(int r=0; r<this.getNumRighe(); r++)
			for(int c=0; c<this.getNumColonne(); c++) {
				var possibleNumber = this.getAllMappings().get(cruciverba.getCella(r,c));
				if (possibleNumber==null) this.setMapping(cruciverba.getCella(r,c), n++);
				int number = this.getAllMappings().get(cruciverba.getCella(r,c));
				this.setCella(r,c, number);
			}
	}
	
	private void checkLetter(char c) {
		if(!Character.isUpperCase(c)) throw new IllegalArgumentException("Accettabili solo caratteri maiuscoli: " + c);		
	}
	
	private void checkNumber(int v) {
		if(v < 1 || v > 26) throw new IllegalArgumentException("Accettabili solo valori numerici tra 1 e 26: " + v);
	}
	
	public char getAssociazione(int v) {
		checkNumber(v);
		return mappaAssociazioni.getOrDefault(v, Cruciverba.BIANCA);
	}
	
	public void associa(int v, char c) {
		checkNumber(v);
		checkLetter(c);
		mappaAssociazioni.put(v, c);
	}
	
	public void disassocia(int v) {
		checkNumber(v);
		mappaAssociazioni.remove(v);
	}
	
	public Map<Integer, Character> associazioni() {
		return mappaAssociazioni;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int r=0; r<this.getNumRighe(); r++) { 
			sb.append("|");
			for(int c=0; c<this.getNumColonne(); c++) {
				var num = this.getCella(r,c);
				sb.append( num==0 ? String.format("%2c", Cruciverba.NERA) : 
					this.getAssociazione(num)==Cruciverba.BIANCA ? String.format("%02d", num) : String.format("%2c", this.getAssociazione(num)));
				sb.append("|"); // se non si vuole il separatore verticale: sb.append(Cruciverba.BLANK);
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
}
