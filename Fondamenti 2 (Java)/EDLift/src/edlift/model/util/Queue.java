package edlift.model.util;

import java.util.StringJoiner;

public class Queue {
	private int booked;
	private int[] bookings;
	int maxBookings;
	
	public Queue(int maxBookings) {
		this.maxBookings = maxBookings;
		this.bookings = new int[maxBookings];
		java.util.Arrays.fill(bookings, Integer.MIN_VALUE);
		this.booked = 0;
	}
	
	public boolean hasItems() {
		return booked != 0;
	}
	
	public int extract() {
		int ans = this.peek();
		
		if(this.hasItems()) {
			for(int i = 0; i < booked-1; i++) bookings[i] = bookings[i+1];
			bookings[--booked] = Integer.MIN_VALUE;
		}
		
		return ans;
	}
	
	public boolean insert(int n) {
		if(booked == maxBookings) return false;
		bookings[booked++] = n;
		return true;
	}
	
	public int peek() {
		return bookings[0];
	}
	
	public int size() {
		return this.booked;
	}
	
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(", ", "[", "]");
		
		for(int i = 0; i < booked; i++) {
			sj.add(""+bookings[i]);
		}
		
		return sj.toString();
	}
}
