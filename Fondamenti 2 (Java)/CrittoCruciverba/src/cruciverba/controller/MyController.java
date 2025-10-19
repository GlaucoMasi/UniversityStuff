package cruciverba.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class MyController extends Controller {
	
	public MyController(File file) {
		super(file);
	}
	
	@Override
	public void associa(int v, char c) {
		OptionalInt mapping = super.getSchemaNumerato().getMapping(c);
		if(super.getModalita() == Modalita.AGEVOLATA && (mapping.isEmpty() || mapping.getAsInt() != v)) throw new UnsupportedOperationException("Associazione sbagliata");
		super.associa(v, c);
	}

	@Override
	public String parolaIniziale() {
		List<String> validWords = IntStream.range(0, super.getCruciverba().getNumRighe())
				.mapToObj(i -> super.getCruciverba().paroleInRiga(i))
				.flatMap(Arrays::stream)
				.filter(s -> numeroCaratteriDistinti(s) == super.getModalita().numeroCaratteriDistinti())
				.collect(Collectors.toList());
		
		return validWords.get((new Random()).nextInt(validWords.size()));
	}

	private int numeroCaratteriDistinti(String parola) {
		return (int) Stream.of(parola.split("")).distinct().count();
	}
		
}
