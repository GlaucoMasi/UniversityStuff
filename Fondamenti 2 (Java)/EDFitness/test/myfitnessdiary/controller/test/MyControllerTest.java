package myfitnessdiary.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

import myfitnessdiary.controller.MyController;
import myfitnessdiary.model.Activity;
import myfitnessdiary.model.FitnessDiary;
import myfitnessdiary.model.Intensity;
import myfitnessdiary.model.Workout;
import myfitnessdiary.persistence.ActivityRepository;
import myfitnessdiary.persistence.MyReportWriter;

public class MyControllerTest {

	private static String trimRows(String str) {
		return Arrays.stream(str.split("\n")).map(String::trim).collect(Collectors.joining("\n", "", "\n"));
	}

	@Test
	public void testOK() {
		FitnessDiary diary = new MyDiaryMock();
		ActivityRepository repo = new ActivityRepositoryMock();
		MyController controller = new MyController(diary, repo, new MyReportWriter());

		assertEquals(controller.getActivities(), repo.getActivityNames());
		assertEquals(controller.getActivity("AS1").getCalories(Intensity.HIGH), 3);

		assertEquals(trimRows(controller.getDayWorkout(LocalDate.of(2025, 5, 14))),
				"Allenamento di mercoledì 14 maggio 2025\n" + "AS4 minuti: 20  calorie bruciate: 200\n"
						+ "AS2 minuti: 20  calorie bruciate: 80\n" + "\n" + "Minuti totali allenamento: 40\n"
						+ "Calorie totali bruciate: 280\n");

		assertEquals(trimRows(controller.getDayWorkout(LocalDate.of(2025, 5, 15))),
				"Allenamento di giovedì 15 maggio 2025\n" + "AS1 minuti: 30  calorie bruciate: 60\n"
						+ "AS3 minuti: 20  calorie bruciate: 180\n" + "\n" + "Minuti totali allenamento: 50\n"
						+ "Calorie totali bruciate: 240\n");

		assertEquals(trimRows(controller.getDayWorkout(LocalDate.of(2025, 5, 13))),
				"Allenamento di martedì 13 maggio 2025\n" + "AS5 minuti: 15  calorie bruciate: 240\n"
						+ "AS7 minuti: 10  calorie bruciate: 80\n" + "AS8 minuti: 25  calorie bruciate: 75\n" + "\n"
						+ "Minuti totali allenamento: 50\n" + "Calorie totali bruciate: 395\n");

		var date = LocalDate.of(2025, 5, 13);
		assertEquals(controller.getWeekWorkouts(date), diary.getWeekWorkouts(date));
	}

	@Test
	public void testNoWorkout() {
		FitnessDiary diary = new MyDiaryMock();
		ActivityRepository repo = new ActivityRepositoryMock();
		MyController controller = new MyController(diary, repo, new MyReportWriter());

		assertEquals(trimRows(controller.getDayWorkout(LocalDate.of(2025, 5, 20))),
				"Allenamento di martedì 20 maggio 2025\n" + "\n" + "Minuti totali allenamento: 0\n"
						+ "Calorie totali bruciate: 0\n");

	}

	@Test
	public void testWorkoutAdded() {
		FitnessDiary diary = new MyDiaryMock();
		ActivityRepository repo = new ActivityRepositoryMock();
		MyController controller = new MyController(diary, repo, new MyReportWriter());

		Activity a = new Activity("AS6");
		a.insertCalories(Intensity.LOW, 5);
		a.insertCalories(Intensity.MEDIUM, 7);
		a.insertCalories(Intensity.HIGH, 10);
		controller.addWorkout(new Workout(LocalDate.of(2025, 5, 14), 30, Intensity.HIGH, a));

		assertEquals(trimRows(controller.getDayWorkout(LocalDate.of(2025, 5, 14))),
				"Allenamento di mercoledì 14 maggio 2025\n" + "AS4 minuti: 20  calorie bruciate: 200\n"
						+ "AS2 minuti: 20  calorie bruciate: 80\n" + "AS6 minuti: 30  calorie bruciate: 300\n" + "\n"
						+ "Minuti totali allenamento: 70\n" + "Calorie totali bruciate: 580\n");

	}

	@Test
	public void testKODateNull() {
		FitnessDiary diary = new MyDiaryMock();
		ActivityRepository repo = new ActivityRepositoryMock();
		MyController controller = new MyController(diary, repo, new MyReportWriter());

		assertThrows(NullPointerException.class, () -> controller.getDayWorkout(null));
	}

}
