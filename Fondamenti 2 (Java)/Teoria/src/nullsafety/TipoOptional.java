package nullsafety;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.OptionalDouble;

record Persona(String nome, String cognome) {}

public class TipoOptional {
	public void tipoOptional() {
		Optional<Persona> opt = Optional.ofNullable(new Persona("Glauco", "Masi"));
		opt = Optional.empty();
		
		opt.isEmpty(); opt.isPresent();
		opt.get(); opt.orElse(new Persona("xxxx", "xxxx"));
	}
	
	public void optionalTipiPrimitivi() {
		OptionalInt optInt = OptionalInt.of(10);
		OptionalLong optLong = OptionalLong.of(10);
		OptionalDouble optDouble = OptionalDouble.of(10.10);
		optInt.getAsInt(); optLong.getAsLong(); optDouble.getAsDouble();
	}
}
