package controller;

import java.util.Objects;

import model.Counter;

public class Controller {
	private Counter[] counters;
	private int index, size;
	
	public Controller(int size) {
		this.counters = new Counter[size];
		this.size = size;
		index = 0;
	}
	
	public int getCapacity() {
		return size;
	}
	
	public int newCounter() {
		if(index < size) { this.counters[index++] = new Counter(); return index-1; }
		else throw new IllegalArgumentException("Capacity exceeded");
	}
	
	public void incCounter(int i) {
		Objects.checkIndex(i, size);
		counters[i].inc();
	}
	
	public void decCounter(int i) {
		Objects.checkIndex(i, size);
		counters[i].dec();
	}
	
	public void resCounter(int i) {
		Objects.checkIndex(i, size);
		counters[i].res();
	}
	
	public String getCounterValueAsString(int i) {
		Objects.checkIndex(i, size);
		return String.valueOf(counters[i].getValue());
	}
}
