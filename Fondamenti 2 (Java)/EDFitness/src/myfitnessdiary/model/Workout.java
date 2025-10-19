package myfitnessdiary.model;

import java.time.LocalDate;
import java.util.Objects;

public class Workout {
	private Intensity intensity;
	private Activity activity;
	private LocalDate date;
	private int duration;
	
	public Workout(LocalDate date, int duration, Intensity intensity, Activity activity) {
		Objects.requireNonNull(intensity);
		Objects.requireNonNull(activity);
		Objects.requireNonNull(date);
		if(duration <= 0) throw new IllegalArgumentException("La durata di un workout deve essere positiva");
		
		this.intensity = intensity;
		this.activity = activity;
		this.date = date;
		this.duration = duration;
	}

	public Intensity getIntensity() {
		return intensity;
	}

	public Activity getActivity() {
		return activity;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getDuration() {
		return duration;
	}
	
	public int getBurnedCalories() {
		return getDuration()*getActivity().getCalories(getIntensity());
	}
}
