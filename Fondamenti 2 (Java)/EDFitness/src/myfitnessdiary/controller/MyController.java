package myfitnessdiary.controller;

import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

import myfitnessdiary.model.Workout;
import myfitnessdiary.model.FitnessDiary;
import myfitnessdiary.persistence.ReportWriter;
import myfitnessdiary.persistence.ActivityRepository;

public class MyController extends Controller {
	private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.ITALY);
	
	public MyController(FitnessDiary diary, ActivityRepository activityRepository, ReportWriter reportWriter) {
		super(diary, activityRepository, reportWriter);
	}
	
	public String getDayWorkout(LocalDate date) {
		StringBuilder sb = new StringBuilder("Allenamento di ");
		sb.append(formatter.format(date) + System.lineSeparator());
		
		int calorie = 0, durata = 0;
		List<Workout> workouts = super.getDayWorkouts(date);
		
		for(Workout workout : workouts) {
			sb.append(workout.getActivity().getName() + " minuti: " + workout.getDuration() 
			+ " calorie bruciate: " + workout.getBurnedCalories() + System.lineSeparator());
			calorie += workout.getBurnedCalories();
			durata += workout.getDuration();
		}
		
		sb.append(System.lineSeparator());
		sb.append("Minuti totali allenamento: " + durata + System.lineSeparator());
		sb.append("Calorie totali bruciate: " + calorie);
		
		return sb.toString();
	}
}
