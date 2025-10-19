package fractioncollection;
import frazione.Frazione;

public class FractionCollection {
	private final static int DEFAULT_GROWTH_FACTOR = 2;
	private final static int DEFAULT_PHYSICAL_SIZE = 10;
	
	private int size;
	private Frazione[] innerContainer;
	
	public FractionCollection(int size) {
		this.size = 0;
		innerContainer = new Frazione[size];
	}
	
	public FractionCollection() {
		this(DEFAULT_PHYSICAL_SIZE);
	}
	
	public FractionCollection(Frazione[] frazioni) {
		while(size < frazioni.length && frazioni[size] != null) size++;
		innerContainer = new Frazione[size];
		for(int i = 0; i < size; i++) innerContainer[i] = frazioni[i];
	}
	
	public Frazione get(int idx) {
		if(idx < size) return innerContainer[idx];
		return null;
	}
	
	public void put(Frazione f) {
		if(size == innerContainer.length) {
			Frazione[] nuovo = new Frazione[size*DEFAULT_GROWTH_FACTOR];
			for(int i = 0; i < size; i++) nuovo[i] = innerContainer[i];
			innerContainer = nuovo;
		}
		
		innerContainer[size++] = f;
	}
	
	public void remove(int idx) {
		if(idx >= size) return;
		for(int i = idx; i < size; i++) innerContainer[i] = innerContainer[i+1];
		size--;
	}
	
	public int size() {
		return size;
	}
	
	public String toString() {
		return java.util.Arrays.toString(innerContainer);
	}
	
	public FractionCollection sum(FractionCollection that) {
		if(this.size() != that.size()) return null;
		FractionCollection ans = new FractionCollection(this.size());
		for(int i = 0; i < this.size(); i++) ans.put(this.get(i).sum(that.get(i)));
		return ans;
	}
	
	public FractionCollection sub(FractionCollection that) {
		if(this.size() != that.size()) return null;
		FractionCollection ans = new FractionCollection(this.size());
		for(int i = 0; i < this.size(); i++) ans.put(this.get(i).sub(that.get(i)));
		return ans;
	}
	
	public FractionCollection mul(FractionCollection that) {
		if(this.size() != that.size()) return null;
		FractionCollection ans = new FractionCollection(this.size());
		for(int i = 0; i < this.size(); i++) ans.put(this.get(i).mul(that.get(i)));
		return ans;
	}
	
	public FractionCollection div(FractionCollection that) {
		if(this.size() != that.size()) return null;
		FractionCollection ans = new FractionCollection(this.size());
		for(int i = 0; i < this.size(); i++) ans.put(this.get(i).div(that.get(i)));
		return ans;
	}
}
