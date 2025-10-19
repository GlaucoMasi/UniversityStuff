package persistence;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.AppointmentCollection;

public class MyCalendarPersister implements CalendarPersister {
	private static final String FILE_PATH = "Calendar.bin";
	
	public AppointmentCollection load() throws IOException, ClassNotFoundException {
		try(ObjectInputStream is = new ObjectInputStream(new FileInputStream(FILE_PATH))){
			return (AppointmentCollection) is.readObject();
		}
	}

	public void save(AppointmentCollection ac) throws IOException {
		try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FILE_PATH))){
			os.writeObject(ac);
		}
	}
	
}
