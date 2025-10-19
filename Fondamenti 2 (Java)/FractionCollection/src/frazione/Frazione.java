package frazione;
import util.MyMath;

public class Frazione {
	private int num, den;
	
	public Frazione(int num, int den) {
		if(den < 0) num *= -1;
		this.num = num;
		this.den = Math.abs(den);
	}
	
	public Frazione(int num) {
		this.num = num;
		this.den = 1;
	}
	
	public boolean equals(Frazione that) {
		return this.getNum()*that.getDen() == this.getDen()*that.getNum();
	}
	
	public int getDen() {
		return den;
	}
	
	public int getNum() {
		return num;
	}
	
	public String toString() {
		return Integer.toString(getNum())+'/'+Integer.toString(getDen());
	}
	
	public Frazione minTerm() {
		if(getNum() == 0) return this;
		Frazione ans = new Frazione(getNum()/MyMath.mcd(getNum(), getDen()), getDen()/MyMath.mcd(getNum(), getDen()));
		return ans;
	}
	
	public int compareTo(Frazione that) {
		if(equals(that)) return 0;
		return (this.getDouble() < that.getDouble() ? -1 : 1);
	}
	
	public Frazione sum(Frazione that) {
		int newden = MyMath.mcm(this.getDen(), that.getDen());
		Frazione ans = new Frazione(this.getNum()*newden/this.getDen() + that.getNum()*newden/that.getDen(), newden);
		return ans.minTerm();
	}
	
	public Frazione sumWithMcm(Frazione that) {
		int newden = MyMath.mcm(this.getDen(), that.getDen());
		Frazione ans = new Frazione(this.getNum()*newden/this.getDen() + that.getNum()*newden/that.getDen(), newden);
		return ans.minTerm();
	}
	
	public Frazione sub(Frazione that) {
		int newden = MyMath.mcm(this.getDen(), that.getDen());
		Frazione ans = new Frazione(this.getNum()*newden/this.getDen() - that.getNum()*newden/that.getDen(), newden);
		return ans.minTerm();
	}
	
	public Frazione mul(Frazione that) {
		Frazione ans = new Frazione(this.getNum()*that.getNum(), this.getDen()*that.getDen());
		return ans.minTerm();
	}
	
	public Frazione div(Frazione that) {
		Frazione ans = new Frazione(this.getNum()*that.getDen(), this.getDen()*that.getNum());
		return ans.minTerm();
	}
	
	public double getDouble() {
		return (double)getNum()/getDen();
	}
	
	public Frazione reciprocal() {
		Frazione ans = new Frazione(getDen(), getNum());
		return ans.minTerm();
	}
}
