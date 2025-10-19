package persistence;

import java.io.IOException;

import model.AppointmentCollection;

public interface CalendarPersister {
	public AppointmentCollection load() throws IOException, ClassNotFoundException;
	public void save(AppointmentCollection ac) throws IOException ;
}
