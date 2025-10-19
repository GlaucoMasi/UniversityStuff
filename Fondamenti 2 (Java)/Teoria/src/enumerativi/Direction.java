package enumerativi;

public enum Direction {
	NORTH("Nord", 0), SOUTH("Sud", 180),
	EAST("Est", 90), WEST("Ovest", 270);
	
	private String value;
	private int degrees;
	
	private Direction(String value, int degrees) {
		this.value = value; this.degrees = degrees;
	}
	
	// ordinal, values, valueOf

	public Direction getOpposite() {
		return switch(this) {
			case NORTH -> SOUTH;
			case SOUTH -> NORTH;
			case EAST -> WEST;
			case WEST -> EAST;
		};
	}
	
	public String toString() {
		return value + " a " + degrees + "°";
	}
}
