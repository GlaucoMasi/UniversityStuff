package eccezioni;

public class NuovaEccezione extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public NuovaEccezione() { super(); }
	public NuovaEccezione(String s) { super(s); }
}
