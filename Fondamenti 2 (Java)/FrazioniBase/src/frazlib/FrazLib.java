package frazlib;
import frazione.Frazione;

public class FrazLib {
	public static Frazione sum(Frazione[] frazioni) {
		Frazione base = new Frazione(0);
		for(Frazione f : frazioni) base = base.sum(f);
		return base;
	}
	
	public static Frazione mul(Frazione[] frazioni) {
		Frazione base = new Frazione(1);
		for(Frazione f : frazioni) base = base.mul(f);
		return base;
	}
}
