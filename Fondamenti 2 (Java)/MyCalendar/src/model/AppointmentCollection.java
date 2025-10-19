package model;

import java.io.Serializable;

public class AppointmentCollection implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_GROWTH_FACTOR = 2;
	private final static int DEFAULT_PHYSICAL_SIZE = 10;
	
	private int size;
	private Appointment[] innerContainer;

	public AppointmentCollection(int capacity) {
		this.size = 0;
		innerContainer = new Appointment[capacity];
	}
	
	public AppointmentCollection() {
		this(DEFAULT_PHYSICAL_SIZE);
	}
	
	public Appointment get(int index) {
		if(index >= size) return null;
		return innerContainer[index];
	}
	
	public int indexOf(Appointment appointment) {
		for(int i = 0; i < size; i++) {
			if(this.get(i).equals(appointment)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public int size() {
		return size;
	}
	
	public void remove(int index) {
		if(index >= size) return;
		
		for(int i = index; i < size-1; i++) {
			innerContainer[i] = innerContainer[i+1];
		}
		
		size--;
	}
	
	public void add(Appointment appointment) {
		if(size == innerContainer.length) {
			Appointment[] nuovo = new Appointment[innerContainer.length*DEFAULT_GROWTH_FACTOR];
			
			for(int i = 0; i < size; i++) {
				nuovo[i] = innerContainer[i];
			}
			
			innerContainer = nuovo;
		}
		
		innerContainer[size++] = appointment;
	}
}
