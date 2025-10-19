package fondt2.tlc;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.LocalDateTime;

import fondt2.tlc.util.DayOfWeekHelper;

public class Band {
	private DayOfWeek[] combinedDays;
	private double costPerInterval;
	private LocalTime startTime, endTime;

	public Band(LocalTime startTime, LocalTime endTime, DayOfWeek[] combinedDays, double costPerInterval) {
		this.combinedDays = combinedDays;
		this.costPerInterval = costPerInterval;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public DayOfWeek[] getCombinedDays() {
		return combinedDays;
	}
	
	public double getCostPerInterval() {
		return costPerInterval;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	public boolean isInBand(LocalDateTime dateTime) {
		LocalTime time = dateTime.toLocalTime();
		DayOfWeek day = dateTime.getDayOfWeek();
		return !time.isAfter(getEndTime()) && !time.isBefore(getStartTime()) && DayOfWeekHelper.isDayIn(day, getCombinedDays());
	}
	
	public boolean isValid() {
		return !getEndTime().isBefore(getStartTime()) && getCostPerInterval() >= 0 && getCombinedDays().length != 0;
	}
	
}
