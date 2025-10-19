package media;

import media.filters.*;

public class Photo extends Media implements HasType {
	private static final long serialVersionUID = 1L;
	String[] authors = null;

	public Photo(String titolo, int anno, String[] authors) {
		super(anno, titolo);
		this.authors = java.util.Arrays.copyOf(authors, authors.length);
	}

	public String[] getAuthors() {
		return this.authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = java.util.Arrays.copyOf(authors, authors.length);
	}
	
	public Type getType() {
		return Type.PHOTO;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Photo that) {
			return this.getYear() == that.getYear() && this.getTitle().equals(that.getTitle())
					&& utils.StringUtils.areEquivalent(this.getAuthors(), that.getAuthors());
		}
		
		return false;
	}

	@Override
	public String toString() {
		return this.getType().toString() + ", anno: " + this.getYear() + ", titolo: " + this.getTitle() + 
				", autori: " + java.util.Arrays.deepToString(this.getAuthors()) + System.lineSeparator();
	}
}
