package cruciverba.model;

import java.util.*;
import java.util.stream.*;


public class Numeratore {
	
	private Cruciverba schema;
	private int numRighe, numColonne;
	private OptionalInt[][] griglia;
	
	public Numeratore(Cruciverba schema) {
		this.schema=schema;
		this.numRighe = schema.getNumRighe();
		this.numColonne = schema.getNumColonne();
		this.griglia = new OptionalInt[numRighe][numColonne];
		numeraCelle();
	}
	
	
	private void numeraCelle() {
		int idx = 1;
		for(int i = 0; i < getNumRighe(); i++) {
			for(int j = 0; j < getNumColonne(); j++) {
				boolean orizzontale = (schema.getCella(i, j) != Cruciverba.NERA) && (j < getNumColonne()-1)
						&& (schema.getCella(i, j+1) != Cruciverba.NERA) && (j == 0 || schema.getCella(i, j-1) == Cruciverba.NERA);
				boolean verticale = (schema.getCella(i, j) != Cruciverba.NERA) && (i < getNumRighe()-1)
						&& (schema.getCella(i+1, j) != Cruciverba.NERA) && (i == 0 || schema.getCella(i-1, j) == Cruciverba.NERA);
				
				if(orizzontale || verticale) griglia[i][j] = OptionalInt.of(idx++);
				else griglia[i][j] = OptionalInt.empty();
			}
		}
	}
	

	public Cruciverba getSchema() {
		return schema;
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
	
	public OptionalInt getNumeroCella(int indiceRiga, int indiceColonna) {
		validaIndiceRiga(indiceRiga); validaIndiceColonna(indiceColonna);
		return griglia[indiceRiga][indiceColonna];
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int r=0; r<numRighe; r++) { 
			sb.append("|");
			for(int c=0; c<numColonne; c++) {
				var num = griglia[r][c];
				sb.append(num.isEmpty() ? (schema.getCella(r,c)==Cruciverba.NERA ? String.format("%2s", Cruciverba.NERA) : String.format("%2s", Cruciverba.BIANCA)) : 
							String.format("%02d", num.getAsInt()) );
				sb.append("|"); // se non si vuole il separatore verticale: sb.append(Cruciverba.BLANK);
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	
	public OptionalInt[] getRiga(int indiceRiga) {
		validaIndiceRiga(indiceRiga);
		return griglia[indiceRiga];
	}

	public OptionalInt[] getColonna(int indiceColonna) {
		validaIndiceColonna(indiceColonna);
		var colonna = new OptionalInt[numRighe];
		for (int i=0; i<numRighe; i++)
			colonna[i] = griglia[i][indiceColonna];
		return colonna;
	}
	
	public SortedMap<Integer,String> orizzontali(){
		return mappaParole(Orientamento.ORIZZONTALE);
	}
	
	public SortedMap<Integer,String> verticali(){
		return mappaParole(Orientamento.VERTICALE);
	}
	
	public SortedMap<Integer,String> mappaParole(Orientamento or){
		SortedMap<Integer, String> ans = new TreeMap<>();
		int n = switch(or) {
			case ORIZZONTALE -> getNumRighe();
			case VERTICALE -> getNumColonne();
		};	
		
		for(int i = 0; i < n; i++) {
			String[] parole = Stream.of(switch(or) {
				case ORIZZONTALE -> schema.paroleInRiga(i);
				case VERTICALE -> schema.paroleInColonna(i);
			}).filter(s -> s.length() >= 2).toArray(String[]::new);

			int[] numeriUtili = switch(or) {
				case ORIZZONTALE -> numeriUtili(this.getRiga(i), schema.getRiga(i));
				case VERTICALE -> numeriUtili(this.getColonna(i), schema.getColonna(i));
			};
			
			for(int j = 0; j < parole.length; j++) ans.put(numeriUtili[j], parole[j]);
		}
		
		return ans;
	}
	
	private int[] numeriUtili(OptionalInt[] rigaNumeri, char[] rigaCaratteri) {
		var numeri = new ArrayList<Integer>();
		if(rigaNumeri[0].isPresent() && rigaCaratteri[1]!=Cruciverba.NERA ) numeri.add(rigaNumeri[0].getAsInt());
		for(int i=1; i<rigaCaratteri.length-1; i++) {
			if(rigaNumeri[i].isPresent() && rigaCaratteri[i-1]==Cruciverba.NERA && rigaCaratteri[i+1]!=Cruciverba.NERA) numeri.add(rigaNumeri[i].getAsInt());
		}
		return numeri.stream().mapToInt(Integer::intValue).toArray();
	}
	
}
