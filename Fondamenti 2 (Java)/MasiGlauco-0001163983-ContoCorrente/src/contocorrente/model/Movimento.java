package contocorrente.model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class Movimento {
	private LocalDate dataContabile, dataValuta;
	private Tipologia tipologia;
	private double importo;
	private String causale;
	private NumberFormat currencyFormatter;
		
	public Movimento(LocalDate dataContabile, LocalDate dataValuta, Tipologia tipologia, double importo, String causale) {
		if(dataContabile == null) throw new IllegalArgumentException("La data contabile è nulla");
		if(dataValuta == null) throw new IllegalArgumentException("La data valuta è nulla");
		if(tipologia == null) throw new IllegalArgumentException("La tipologia è nulla");
		if(Double.isNaN(importo)) throw new IllegalArgumentException("L'importo non è un numero");
		if(causale == null) throw new IllegalArgumentException("La causale è nulla");
		
		if(causale.isBlank()) throw new IllegalArgumentException("La causale è vuota");
		
		if(switch(tipologia) {
			case ACCREDITO -> importo <= 0;
			case ADDEBITO -> importo >= 0;
			case NULLO -> importo != 0;
			case SALDO -> false;
		}) throw new IllegalArgumentException("Valore dell'importo non valido per il tipo di movimento specificato");
		
		this.dataContabile = dataContabile;
		this.dataValuta = dataValuta;
		this.tipologia = tipologia;
		this.importo = importo;
		this.causale = causale.toUpperCase();
		
		this.currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
	}
	
	public LocalDate getDataContabile() {
		return dataContabile;
	}
	public LocalDate getDataValuta() {
		return dataValuta;
	}
	public Tipologia getTipologia() {
		return tipologia;
	}
	public double getImporto() {
		return importo;
	}
	public String getCausale() {
		return causale;
	}

	@Override
	public int hashCode() {
		return Objects.hash(causale, dataContabile, dataValuta, importo, tipologia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Movimento other = (Movimento) obj;
		return Objects.equals(causale, other.causale) && Objects.equals(dataContabile, other.dataContabile)
				&& Objects.equals(dataValuta, other.dataValuta)
				&& Double.doubleToLongBits(importo) == Double.doubleToLongBits(other.importo)
				&& tipologia == other.tipologia;
	}

	@Override
	public String toString() {
		return dataContabile + " " + tipologia + " di " + currencyFormatter.format(importo) + " per " + causale;
	}
	
}
