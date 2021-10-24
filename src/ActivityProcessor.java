import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ActivityProcessor {
	private Queue<Activity> activityQueue;
	private List<Activity> finishedActivities;
	private Activity currentActivity;
	private int timeProcessed = 0;
	private int simulationTime = 0;
	private int id;

	public ActivityProcessor(int id) {
		activityQueue = new LinkedList<>();
		finishedActivities = new ArrayList<>();
		this.id = id;
	}

	public void process() {
		
		if (isNull(currentActivity) && activityQueue.size() != 0) {
			currentActivity = activityQueue.poll();
			consumeActivityTime();
		}
		else if(nonNull(currentActivity)){
			consumeActivityTime();
		}
		
		activityQueue.forEach(Activity::addTimeWaiting);
	}

	public int getSimulationTime() {
		return simulationTime;
	}
	
	public int getTotalWaitingTime() {
		return finishedActivities.stream()
				.map(Activity::getTimeWaiting)
				.mapToInt(Integer::intValue).sum();
	}
	
	public int getQueueSize() {
		return activityQueue.size();
	}

	public boolean hasEndedProcessing() {
		if ( activityQueue.isEmpty() && currentActivity == null )
			return true;
		
		return false;
	}

	private void consumeActivityTime() {
		if( timeProcessed >= currentActivity.getTimeItTakes() ) {
			finishedActivities.add(currentActivity);
			currentActivity = null;
			timeProcessed = 0;
		}
		else {
			timeProcessed++;
		}
	}

	public void add(Activity activity) {
		activityQueue.add(activity);
	}
	
	public int getId() {
		return this.id;
	}
	
	public Activity getCurrentActivity() {
		return this.currentActivity;
	}
	
	public Queue<Activity> getActivityQueue() {
		return this.activityQueue;
	}
}
