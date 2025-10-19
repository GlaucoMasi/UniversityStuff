package model;

public class Counter {
	private int val;
	
	public Counter() {
		val = 0;
	}
	
	public void inc() {
		val++;
	}
	
	public void dec() {
		val--;
	}
	
	public void res() {
		val = 0;
	}
	
	public int getValue() {
		return val;
	}
}
