package contocorrente.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ContoCorrente {

	public static final String ANONIMO = "Anonimo";
	
	private List<Movimento> movimenti;
	private String intestatario;
	private long id;
	
	public ContoCorrente(long id) {
		this.id=id;
		this.intestatario=ANONIMO;
		movimenti = new ArrayList<>();
	}

	public ContoCorrente(String intestatario, long id) {
		this.id=id;
		if (intestatario==null) throw new IllegalArgumentException("Intestatario nullo");
		this.intestatario=intestatario;
		movimenti = new ArrayList<>();
	}
	
	public void aggiungi(Movimento m) {
		if (m == null) throw new IllegalArgumentException("Impossibile aggiungere un movimento nullo");
		int idx = Collections.binarySearch(movimenti, m, 
				Comparator.comparing(Movimento::getDataContabile).thenComparing(Movimento::getDataValuta));
		movimenti.add(idx >= 0 ? idx : -idx-1, m);
	}

	public void storna(Movimento m) {
		if (m==null) throw new IllegalArgumentException("Impossibile stornare un movimento nullo");
		movimenti.remove(m);
	}

	public List<Movimento> getMovimenti() {
		return Collections.unmodifiableList(movimenti);
	}

	public String getIntestatario() {
		return intestatario;
	}

	public void setIntestatario(String nome) {
		if (nome==null) throw new IllegalArgumentException("Impossibile stornare un movimento nullo");
		this.intestatario= nome=="" ? ANONIMO : nome;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ContoCorrente [movimenti=" + movimenti + ", intestatario=" + intestatario + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ContoCorrente that) {
			return this.id==that.id;
		}
		else return false;
	}

}
