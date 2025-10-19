package battleship.persistence;

import java.io.Serial;

public class BadFileFormatException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public BadFileFormatException() {
	}

	public BadFileFormatException(String message) {
		super(message);
	}

	public BadFileFormatException(Throwable throwable) {
		super(throwable);
	}

	public BadFileFormatException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
