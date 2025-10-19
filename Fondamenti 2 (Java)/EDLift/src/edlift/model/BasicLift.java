package edlift.model;

public class BasicLift extends Lift{
	public BasicLift(int minFloor, int maxFloor, int initialFloor, double speed) {
		super(minFloor, maxFloor, initialFloor, speed);
	}
	
	public String getMode() {
		return "Basic";
	}
	
	public RequestResult goToFloor(int floor){
		super.checkArrivalFloor(floor);
		
		LiftState currentState = super.getCurrentState();
		
		if(currentState != LiftState.REST) {
			return RequestResult.REJECTED;
		}else {
			super.setRequestedFloor(floor);
			return RequestResult.ACCEPTED;
		}
	}
}
