package trainstats.model;

public class ThresholdStatProvider implements StatProvider {
	
	private int threshold;
	
	public ThresholdStatProvider() {
		this.threshold=0;
	}

	public ThresholdStatProvider(int threshold) {
		this.threshold=threshold;
	}
	
	@Override
	public long getStatistics(Train train) {
		return Math.round(train.getDelayMap().values().stream().mapToLong(Long::longValue)
				.map(n -> n > threshold ? 1 : 0).average().getAsDouble()*100);
	}

	@Override
	public String toString() {
		return "A soglia";
	}

	@Override
	public String getStatsMessage(Train train) {
		return "Stazioni in cui il treno è giunto con oltre " + threshold + " min. di ritardo: " + getStatistics(train) + "%";
	}

	public boolean hasThreshold() { 
		return true;
	}
	
	public void setThreshold(int threshold) {
		this.threshold=threshold;
	}
	
	public int getThreshold() {
		return threshold; 
	}
}
