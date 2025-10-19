package oroscopo.model;

import java.util.Objects;

public class OroscopoMensile implements Oroscopo, Comparable<Oroscopo> {
	private Previsione amore, lavoro, salute;
	private SegnoZodiacale segnoZodiacale;
	
	public Previsione getPrevisioneAmore() {
		return amore;
	}
	
	public Previsione getPrevisioneLavoro() {
		return lavoro;
	}
	
	public Previsione getPrevisioneSalute() {
		return salute;
	}
	
	public SegnoZodiacale getSegnoZodiacale() {
		return segnoZodiacale;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amore, lavoro, salute, segnoZodiacale);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OroscopoMensile other = (OroscopoMensile) obj;
		return Objects.equals(amore, other.amore) && Objects.equals(lavoro, other.lavoro)
				&& Objects.equals(salute, other.salute) && segnoZodiacale == other.segnoZodiacale;
	}
	
	public int compareTo(Oroscopo other) {
		Objects.requireNonNull(other, "L'altro oroscopo non può essere null");
		return this.getSegnoZodiacale().compareTo(other.getSegnoZodiacale());
	}
	
	public OroscopoMensile(SegnoZodiacale segnoZodiacale, Previsione amore, Previsione lavoro, Previsione salute) {
		Objects.requireNonNull(amore, "La previsione amore non può essere null");
		Objects.requireNonNull(lavoro, "La previsione lavoro non può essere null");
		Objects.requireNonNull(salute, "La previsione salute non può essere null");
		if(!amore.validaPerSegno(segnoZodiacale)) throw new IllegalArgumentException("La previsione amore non è valida per questo segno zodiacale");
		if(!lavoro.validaPerSegno(segnoZodiacale)) throw new IllegalArgumentException("La previsione lavoro non è valida per questo segno zodiacale");
		if(!salute.validaPerSegno(segnoZodiacale)) throw new IllegalArgumentException("La previsione salute non è valida per questo segno zodiacale");
		this.segnoZodiacale = segnoZodiacale;
		this.amore = amore;
		this.lavoro = lavoro;
		this.salute = salute;

	}

	public OroscopoMensile(String segnoZodicale, Previsione amore, Previsione lavoro, Previsione salute) {
		this(SegnoZodiacale.valueOf(segnoZodicale), amore, lavoro, salute);
	}
	
	public int getFortuna() {
		return (int) Math.round((getPrevisioneAmore().getValore()+getPrevisioneLavoro().getValore()+getPrevisioneSalute().getValore())/3.0);
	}
	
	public String toString() {
		return "Amore: " + getPrevisioneAmore().getPrevisione() + System.lineSeparator() + "Lavoro: " + getPrevisioneLavoro().getPrevisione() +
				System.lineSeparator() + "Salute: " + getPrevisioneSalute().getPrevisione() + System.lineSeparator();
	}
}
