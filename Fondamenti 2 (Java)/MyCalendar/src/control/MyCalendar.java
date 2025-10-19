package control;

import model.*;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class MyCalendar implements CalendarController {
	private AppointmentCollection allAppointments;
	private CalendarPersister persister;
	
	public MyCalendar(CalendarPersister persister) {
		this.persister = persister;
		
		try {			
			allAppointments = this.persister.load();
		}catch(Exception e) {
			e.printStackTrace();
			allAppointments = new AppointmentCollection();
		}
	}
	
	public void add(Appointment app) {
		allAppointments.add(app);
		saveAll();
	}
	
	public boolean remove(Appointment app) {
		int index = allAppointments.indexOf(app);
		boolean ans;
		
		if(index == -1) {
			ans = false;
		}else {
			allAppointments.remove(index);
			ans = true;
		}
		
		saveAll();
		return ans;
	}
	
	public AppointmentCollection getAllAppointments() {
		return allAppointments;
	}
	
	private boolean isOverlapped(LocalDateTime start, LocalDateTime end, LocalDateTime refStart, LocalDateTime refEnd) {
		return !(!end.isAfter(refStart) || !start.isBefore(refEnd));
	}
	
	private AppointmentCollection getAppointmentsIn(LocalDateTime start, LocalDateTime end) {
		int size = getAllAppointments().size();
		AppointmentCollection ans = new AppointmentCollection();
				
		for(int i = 0; i < size; i++) {
			Appointment current = getAllAppointments().get(i);
			if(isOverlapped(current.getFrom(), current.getTo(), start, end)) {
				ans.add(current);
			}
		}
		
		return ans;
	}
	
	public AppointmentCollection getDayAppointments(LocalDate date) {
		LocalDateTime from = LocalDateTime.of(date, LocalTime.of(0, 0, 0));
		LocalDateTime to = from.plusDays(1);
		return getAppointmentsIn(from, to);
	}
	
	public AppointmentCollection getWeekAppointments(LocalDate date) {
		int day = date.getDayOfWeek().getValue();
		LocalDateTime from = LocalDateTime.of(date.minusDays(day-1), LocalTime.of(0, 0, 0));
		LocalDateTime to = from.plusMonths(1);
		return getAppointmentsIn(from, to);
	}

	public AppointmentCollection getMonthAppointments(LocalDate date) {
		LocalDateTime from = LocalDateTime.of(date.withDayOfMonth(1), LocalTime.of(0, 0, 0));
		LocalDateTime to = from.plusMonths(1);
		return getAppointmentsIn(from, to);
	}
	
	private void saveAll() {
		try {
			this.persister.save(allAppointments);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
