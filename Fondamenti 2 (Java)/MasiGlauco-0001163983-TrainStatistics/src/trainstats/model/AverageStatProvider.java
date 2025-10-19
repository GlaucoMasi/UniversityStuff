package trainstats.model;

public class AverageStatProvider implements StatProvider {
	@Override
	public long getStatistics(Train train) {
		return Math.round(train.getDelayMap().values().stream().mapToLong(Long::longValue).average().getAsDouble());
	}

	@Override
	public String toString() {
		return "Media ritardi";
	}

	@Override
	public String getStatsMessage(Train train) {
		return "Media ritardi: " + getStatistics(train) + " min.";
	}

	public boolean hasThreshold() { 
		return false;
	}
	
	public void setThreshold(int threshold) {
		throw new UnsupportedOperationException("No threshold in FinalDestinationStatProvider");
	}
	
	public int getThreshold() {
		throw new UnsupportedOperationException("No threshold in FinalDestinationStatProvider");
	}

}
