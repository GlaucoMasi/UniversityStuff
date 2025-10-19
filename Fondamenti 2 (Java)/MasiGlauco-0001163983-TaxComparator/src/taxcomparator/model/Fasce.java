package taxcomparator.model;

import java.util.List;

public class Fasce {
	private List<Fascia> fasce;
	private double noTaxArea;
	private String name;

	public Fasce(String name, List<Fascia> fasce, double noTaxArea) {
		if (name==null || name.isBlank()) this.name = toFullString(); 
		else this.name=name; 
		if (fasce.isEmpty()) throw new IllegalArgumentException("lista fasce di reddito vuota");
		if (noTaxArea < 0) throw new IllegalArgumentException("no tax area negativa");
		if (!isConsistent(fasce)) throw new IllegalArgumentException("lista fasce di reddito inconsistente");
		this.fasce = fasce;
		this.noTaxArea = noTaxArea;
	}

	private boolean isConsistent(List<Fascia> fasce) {
		if(fasce == null || fasce.isEmpty()) return false;
		if(fasce.getFirst().getMin() != 0 || fasce.getLast().getMax() != Double.MAX_VALUE) return false;
		for(int i = 0; i < fasce.size()-1; i++) if(fasce.get(i).getMax() != fasce.get(i+1).getMin()) return false;
		return true;
	}

	public List<Fascia> getFasce() {
		return fasce;
	}

	public double getNoTaxArea() {
		return noTaxArea;
	}

	@Override
	public String toString() {
		return name;
	}

	public String toFullString() {
		return "Fasce [fasce=" + fasce + ", noTaxArea=" + noTaxArea + "]";
	}
	
}
