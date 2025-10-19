package autonoleggio.model;

import java.util.List;
import java.util.Objects;
import java.time.LocalTime;

public class OpeningTime {
	private List<Slot> slots;
	public static OpeningTime CHIUSO = new OpeningTime();

	private OpeningTime() {
		slots = List.of();
	}
	
	public OpeningTime(Slot slotUnico) {
		Objects.requireNonNull(slotUnico, "Lo slot non può essere null");
		slots = List.of(slotUnico);
	}
	
	public OpeningTime(Slot mattino, Slot pomeriggio) {
		Objects.requireNonNull(mattino, "Lo slot del mattino non può essere nullo");
		Objects.requireNonNull(pomeriggio, "Lo slot del pomeriggio non può essere nullo");
		if(!mattino.to().isBefore(pomeriggio.from())) throw new IllegalArgumentException("Il primo slot deve procedere completamente il secondo");
		slots = List.of(mattino, pomeriggio);
	}
	
	public boolean isOpenAt(LocalTime time) {
		return slots.stream().anyMatch(t -> t.contains(time));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(slots.size() > 0) sb.append(Formatters.timeFormatter.format(slots.get(0).from()) + "-" + Formatters.timeFormatter.format(slots.get(0).to()));
		if(slots.size() > 1) sb.append("/" + Formatters.timeFormatter.format(slots.get(1).from()) + ":" + Formatters.timeFormatter.format(slots.get(1).to()));
		return sb.toString();
	}
}
