package lambaexpression;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.*;

public class LambaExpression {
	public void lambaExpression() {
		IntUnaryOperator f1 = x -> x+3;
		f1.applyAsInt(10);
		
		DoubleSupplier f2 = () -> Math.sqrt(2);
		f2.getAsDouble();
		
		Consumer<String> f3 = s -> System.out.println(s);
		f3.accept("ciao");
	}
	
	public void methodReference() {
		Function<Persona, String> f = Persona::getCognome;
		f.apply(new Persona("Glauco", "Masi"));
	}
	
	public void comparator() {
		Persona[] persone = { new Persona("Glauco", "Masi"), new Persona("Glauco", "Masi") };
		Arrays.sort(persone, Comparator.comparing(Persona::getCognome).thenComparing(Persona::getNome));
	}
	
	public void constructor() {
		BiFunction<String, String, Persona> f = Persona::new;
		f.apply("Glauco", "Masi");
	}
	
	public void iterazioneInterna() {
		Persona[] persone = { new Persona("Glauco", "Masi"), new Persona("Glauco", "Masi") };
		Consumer<Persona> printIfNotMasi = p -> { if(p.getCognome().compareTo("Masi") != 0) System.out.println(p.getNome()); };
		Arrays.asList(persone).forEach(printIfNotMasi::accept);
	}
}
