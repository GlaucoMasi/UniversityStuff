package control;

import java.time.LocalDate;

import model.Appointment;
import model.AppointmentCollection;

public interface CalendarController {
	public void add(Appointment app);
	public boolean remove(Appointment app);
	public AppointmentCollection getAllAppointments();
	public AppointmentCollection getDayAppointments(LocalDate date);
	public AppointmentCollection getWeekAppointments(LocalDate date);
	public AppointmentCollection getMonthAppointments(LocalDate date);
}
