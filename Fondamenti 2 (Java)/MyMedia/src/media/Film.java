package media;

import media.filters.*;

public class Film extends Media implements HasGenre, HasDuration, HasType{
	private static final long serialVersionUID = 1L;
	String[] actors = null;
	String director = null;
	int duration = -1;
	String genre = null;
	
	public Film(String titolo, int anno, String director, int duration, String[] actors, String genre) {
		super(anno, titolo);
		this.director = director;
		this.duration = duration;
		this.genre = genre;
		this.actors = java.util.Arrays.copyOf(actors, actors.length);
	}
	
	public String[] getActors() {
		return this.actors;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public String getDirector() {
		return this.director;
	}
	
	public String getGenre() {
		return this.genre;
	}
	
	public Type getType() {
		return Type.FILM;
	}
	
	public void setActors(String[] actors) {
		this.actors = java.util.Arrays.copyOf(actors, actors.length);
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Film that) {
			return this.getYear() == that.getYear() && this.getTitle().equals(that.getTitle()) && this.getDirector().equals(that.getDirector())
					&& this.getDuration() == that.getDuration() && utils.StringUtils.areEquivalent(this.getActors(), that.getActors())
					&& this.getGenre() == that.getGenre();
		}
		
		return false;
	}

	@Override
	public String toString() {
		return this.getType().toString() + ", anno: " + this.getYear() + ", titolo: " + this.getTitle() +  ", direttore: " + this.getDirector() +
				", duration: " + this.getDuration() + ", genere: " + this.getGenre() + ", attori: " + java.util.Arrays.deepToString(this.getActors()) + System.lineSeparator();
	}
}
