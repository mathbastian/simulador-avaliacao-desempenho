
public class Activity {
	private int id;
	private int arrivalTime;
	private int timeItTakes;
	private int timeWaiting;
	
	public Activity(int id, int arrivalTime, int timeItTakes) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.timeItTakes = timeItTakes;
	}

	public int getId() {
		return id;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getTimeItTakes() {
		return timeItTakes;
	}
	
	public void addTimeWaiting() {
		timeWaiting++;
	}
	
	public int getTimeWaiting() {
		return timeWaiting;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.id);
	}
		
}
