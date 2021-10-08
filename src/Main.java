import java.util.Queue;

public class Main {

	public static void main(String[] args) {
		Queue<Activity> activities = new ActivityReader().read();
		ActivityQueue queue = new ActivityQueue();
		boolean thereAreActivitiesToCome = true;
		int time = 0;

		while (thereAreActivitiesToCome) {
			
			if (activities.size() <= 0) {
				thereAreActivitiesToCome = false;
				continue;
			}
			
			if ( activities.peek().getArrivalTime() == time ) {
				queue.add(activities.poll());
				time = 0;
			}
			else {
				time++;
			}
			
			queue.process();
		}
	}

}
