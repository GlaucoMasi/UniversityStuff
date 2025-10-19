package media;

import media.filters.*;

public class Song extends Media implements HasDuration, HasGenre, HasType {
	private static final long serialVersionUID = 1L;
	String singer = null;
	int duration = -1;
	String genre = null;
	

	public Song(String titolo, int anno, String singer, int duration, String genre) {
		super(anno, titolo);
		this.duration = duration;
		this.genre = genre;
		this.singer = singer;
	}

	public String getSinger() {
		return this.singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getGenre() {
		return this.genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public Type getType() {
		return Type.SONG;
	}

	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Song that) {
			return this.getYear() == that.getYear() && this.getTitle().equals(that.getTitle()) && this.getSinger().equals(that.getSinger())
					&& this.getDuration() == that.getDuration() && this.getGenre() == that.getGenre();
		}
		
		return false;
	}

	@Override
	public String toString() {
		return this.getType().toString() + ", anno: " + this.getYear() + ", titolo: " + this.getTitle() +  ", cantante: " + this.getSinger() +
				", duration: " + this.getDuration() + ", genere: " + this.getGenre() + System.lineSeparator();
	}
}
