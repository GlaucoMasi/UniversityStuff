package fondt2.tlc;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.LocalDateTime;

import fondt2.tlc.util.DayOfWeekHelper;

public class Rate {
	private String name;
	private Band[] bands;
	private int intervalInMillis;
	private double startCallCost;
	private String numberRoot;
	
	public Rate(String name, Band[] bands, int intervalInMillis, double startCallCost, String numberRoot) {
		this.name = name;
		this.bands = bands;
		this.intervalInMillis = intervalInMillis;
		this.startCallCost = startCallCost;
		this.numberRoot = numberRoot;
	}

	public String getName() {
		return name;
	}
	
	public double getCallCost(PhoneCall call) {
		Duration duration = Duration.between(call.getStart(), call.getEnd());
		long numeroDiIntervalli = (duration.toMillis()+intervalInMillis-1)/intervalInMillis;
		return startCallCost + getCostPerInterval(call.getStart())*numeroDiIntervalli;
	}
	
	public Band[] getBands() {
		return bands;
	}
	
	private double getCostPerInterval(LocalDateTime dateTime) {
		for(int i = 0; i < bands.length; i++) {
			if(bands[i].isInBand(dateTime)) {
				return bands[i].getCostPerInterval();
			}
		}
		
		return -1;
	}
	
	public boolean isApplicableTo(String destinationNumber) {
		return destinationNumber.startsWith(numberRoot);
	}
	
	public boolean isValid() {
		for(int i = 0; i < bands.length; i++) {
			if(!bands[i].isValid()) {
				return false;
			}
		}
		
		DayOfWeek[] days = DayOfWeek.values();
		for(int i = 0; i < days.length; i++) {
			if(!validateDay(days[i])) {
				return false;
			}
		}
		
		return true;
	}
	
	private Band[] selectBandsInDay(DayOfWeek day) {
		int num = 0;
		
		for(int i = 0; i < bands.length; i++) {
			if(DayOfWeekHelper.isDayIn(day, bands[i].getCombinedDays())) {
				num++;
			}
		}
		
		int idx = 0;
		Band[] ans = new Band[num];
		for(int i = 0; i < bands.length; i++) {
			if(DayOfWeekHelper.isDayIn(day, bands[i].getCombinedDays())) {
				ans[idx++] = bands[i];
			}
		}
		
		return ans;
	}
	
	private void sortBandsByStartTime(Band[] bands) {
		boolean swapped = false;
		do {
			for(int i = 0; i < bands.length-1; i++) {
				if(!bands[i].getEndTime().isBefore(bands[i+1].getStartTime())){
					Band temp = bands[i];
					bands[i] = bands[i+1];
					bands[i+1] = temp;
					swapped = true;
				}
			}
		}while(swapped == true);
	}
	
	private boolean validateBandsInDay(Band[] bandsInDay) {
		if(bandsInDay[0].getStartTime() != LocalTime.MIN || bandsInDay[bandsInDay.length-1].getEndTime() != LocalTime.MAX) {
			return false;
		}
		
		for(int i = 0; i < bandsInDay.length-1; i++) {
			if(!bandsInDay[i].getEndTime().plusNanos(1).equals(bandsInDay[i+1].getStartTime())) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean validateDay(DayOfWeek day) {
		Band[] bandsInDay = selectBandsInDay(day);
		sortBandsByStartTime(bandsInDay);
		return validateBandsInDay(bandsInDay);
	}
}
