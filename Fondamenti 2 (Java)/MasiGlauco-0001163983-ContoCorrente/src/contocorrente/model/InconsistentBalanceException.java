package contocorrente.model;

public class InconsistentBalanceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InconsistentBalanceException() {
	}

	public InconsistentBalanceException(String message) {
		super(message);
	}

	public InconsistentBalanceException(Throwable cause) {
		super(cause);
	}

	public InconsistentBalanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InconsistentBalanceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
