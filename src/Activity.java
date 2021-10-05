
public class Activity {
	private int id;
	private int arrivalTime;
	private int timeItTakes;
	
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
	
	@Override
	public String toString() {
		return "Atividade " + this.id + ": chegada aos " + arrivalTime + " e tempo de serviço de " + timeItTakes;
	}
		
}
