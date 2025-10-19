package edlift.model;

public class HealthyLift extends Lift {
	public HealthyLift(int minFloor, int maxFloor, int initialFloor, double speed) {
		super(minFloor, maxFloor, initialFloor, speed);
	}
	
	public String getMode() {
		return "Healthy";
	}
	
	public RequestResult goToFloor(int floor){
		super.checkArrivalFloor(floor);
		
		LiftState currentState = super.getCurrentState();
		int currentFloor = super.getCurrentFloor();
		
		if(currentState != LiftState.REST || Math.abs(floor-currentFloor) <= 1) {
			return RequestResult.REJECTED;
		}else {
			int realFloor = floor + (currentFloor < floor ? -1 : 1);
			super.setRequestedFloor(realFloor);
			RequestResult ans = RequestResult.MODIFIED;
			ans.setFloor(floor + (currentFloor < floor ? -1 : 1));
			ans.setMsg("Piano modificato per garantire che un piano sia fatto a piedi");
			return ans;
		}
	}
}
