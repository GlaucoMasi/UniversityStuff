package media;

import java.io.Serializable;

public abstract class Media implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title = null;
	private int year = -1;
	
	public Media(int year, String title) {
		this.year = year;
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return "Media, anno: " + year + ", titolo: " + title + System.lineSeparator();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Media that) {
			return this.getYear() == that.getYear() && this.getTitle().equals(that.getTitle());
		}
		
		return false;
	}
	
	public abstract Type getType();
}
