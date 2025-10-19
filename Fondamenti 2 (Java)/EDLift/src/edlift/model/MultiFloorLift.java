package edlift.model;

import edlift.model.util.Queue;

public class MultiFloorLift extends Lift {
	private Queue queue;
	
	public MultiFloorLift(int minFloor, int maxFloor, int initialFloor, double speed) {
		super(minFloor, maxFloor, initialFloor, speed);
		queue = new Queue(4);
	}
	
	public String getMode() {
		return "Multi";
	}
	
	public RequestResult goToFloor(int floor) {
		super.checkArrivalFloor(floor);
		
		LiftState currentState = super.getCurrentState();
		
		if(currentState == LiftState.REST) {
			super.setRequestedFloor(floor);
			return RequestResult.ACCEPTED;
		}else {
			boolean insertion = queue.insert(floor);
			
			if(insertion == true) {
				return RequestResult.ACCEPTED;
			}else {
				RequestResult ans = RequestResult.REJECTED;
				ans.setFloor(Integer.MIN_VALUE);
				ans.setMsg("Aggiunta nella coda fallita");
				return ans;
			}
		}
	}
	
	public boolean hasPendingFloors() {
		return queue.hasItems();
	}
	
	public int nextPendingFloor(LiftState state) {
		return queue.extract();
	}
}
