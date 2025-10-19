package myfitnessdiary.model;

import java.util.List;
import java.util.Objects;
import java.time.LocalDate;
import java.util.ArrayList;

public class MyFitnessDiary implements FitnessDiary {
	private List<Workout> workouts;
	
	public MyFitnessDiary() {
		workouts = new ArrayList<Workout>();
	}
	
	public void addWorkout(Workout wo) {
		if(workouts.add(wo) == false) {
			throw new UnsupportedOperationException();
		}
	}
	
	private int calcCalories(List<Workout> woList) {
		int tot = 0;
		for(Workout workout : woList) tot += workout.getBurnedCalories();
		return tot;
	}
	
	private int calcMinutes(List<Workout> woList) {
		int tot = 0;
		for(Workout workout : woList) tot += workout.getDuration();
		return tot;
	}
	
	public int getDayWorkoutCalories(LocalDate date) throws NullPointerException {
		return calcCalories(getDayWorkouts(date));
	}
	
	public int getDayWorkoutMinutes(LocalDate date) throws NullPointerException {
		return calcMinutes(getDayWorkouts(date));

	}
	
	public int getWeekWorkoutCalories(LocalDate date) throws NullPointerException {
		return calcCalories(getWeekWorkouts(date));

	}
	
	public int getWeekWorkoutMinutes(LocalDate date) throws NullPointerException {
		return calcMinutes(getWeekWorkouts(date));

	}
	
	public List<Workout> getDayWorkouts(LocalDate date) {
		Objects.requireNonNull(date);
		
		List<Workout> ans = new ArrayList<Workout>();
		
		for(Workout workout : workouts) {
			if(workout.getDate().equals(date)) {
				ans.add(workout);
			}
		}
		
		return ans;
	}
	
	public List<Workout> getWeekWorkouts(LocalDate date) {
		Objects.requireNonNull(date);
		
		LocalDate start = date.minusDays(date.getDayOfWeek().getValue()-1);
		LocalDate end = start.plusDays(7);
		
		List<Workout> ans = new ArrayList<Workout>();
		
		for(Workout workout : workouts) {
			if(!workout.getDate().isBefore(start) && workout.getDate().isBefore(end)) {
				ans.add(workout);
			}
		}
		
		return ans;
	}
}
