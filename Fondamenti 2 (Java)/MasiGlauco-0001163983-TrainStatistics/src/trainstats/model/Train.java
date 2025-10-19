package trainstats.model;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Train {	
	private List<Recording> recordings;
	private Map<String,Recording> recordingMap; // (Station,Recording)
	private Map<String,Long> delayMap; 			// (Station,Delay) // per ipotesi, esclude la prima stazione
	private String firstStation, lastStation;
	
	public Train(List<Recording> recordings) {	
		Objects.requireNonNull(recordings, "La lista di rilevazioni è nulla");
		if(recordings.size() < 2) throw new IllegalArgumentException("La lista di rilevazioni deve contenere almeno due stazioni");
		
		if(recordings.getFirst().getScheduledArrivalTime().isPresent()) 
			throw new IllegalArgumentException("La stazione di partenza non può presentare orari di arrivo");

		if(recordings.getLast().getScheduledDepartureTime().isPresent()) 
			throw new IllegalArgumentException("La stazione di arrivo non può presentare orari di partenza");
		
		this.recordings = recordings;
		firstStation = recordings.getFirst().getStation();
		lastStation = recordings.getLast().getStation();
		recordingMap = recordings.stream().collect(Collectors.toMap(Recording::getStation, Function.identity()));
		delayMap = recordings.stream().skip(1).collect(Collectors.toMap(
				Recording::getStation,
				rec -> Math.max(0, Duration.between(rec.getScheduledArrivalTime().get(), rec.getActualArrivalTime().get()).toMinutes())
				));
	}

	@Override
	public String toString() {
		return  String.format("%-20s%8s%8s%8s%8s%n", "stazione", "a.p.","a.r.","p.p.","p.r.") + 
				recordings.stream().map(Recording::toString).collect(Collectors.joining(System.lineSeparator()));
	}

	public List<Recording> getRecordings() {
		return recordings;
	}

	public Map<String, Recording> getRecordingMap() {
		return recordingMap;
	}

	public Map<String, Long> getDelayMap() {
		return delayMap;
	}
	
	public String getFirstStation() {
		return firstStation;
	}

	public String getLastStation() {
		return lastStation;
	}
	
	
}
