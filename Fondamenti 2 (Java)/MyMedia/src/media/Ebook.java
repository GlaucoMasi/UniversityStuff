package media;

import media.filters.*;

public class Ebook extends Media implements HasGenre, HasType {
	private static final long serialVersionUID = 1L;
	String[] authors = null;
	String genre = null;

	public Ebook(String titolo, int anno, String[] authors, String genre) {
		super(anno, titolo);
		this.authors = java.util.Arrays.copyOf(authors, authors.length);
		this.genre = genre;
	}

	public String[] getAuthors() {
		return this.authors;
	}
	
	public String getGenre() {
		return this.genre;
	}

	public void setAuthors(String[] authors) {
		this.authors = java.util.Arrays.copyOf(authors, authors.length);
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public Type getType() {
		return Type.EBOOK;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Ebook that) {
			return this.getYear() == that.getYear() && this.getTitle().equals(that.getTitle()) && this.getGenre() == that.getGenre()
					&& utils.StringUtils.areEquivalent(this.getAuthors(), that.getAuthors());
		}
		
		return false;
	}

	@Override
	public String toString() {
		return this.getType().toString() + ", anno: " + this.getYear() + ", titolo: " + this.getTitle() + ", genere: " + this.getGenre() +
				", autori: " + java.util.Arrays.deepToString(this.getAuthors()) + System.lineSeparator();
	}
}
