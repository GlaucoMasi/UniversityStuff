package autonoleggio.model;

public enum CarType {
	A("Mini"), B("Small"), C("Medium"), D("Luxury");
	
	private String description;

	private CarType(String description) {
		this.description=description;	
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return description + " (" + this.name() + ")";
	}
}
