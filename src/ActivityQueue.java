import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ActivityQueue {

	private Queue<Activity> activityQueue;
	private List<Activity> finishedActivities;
	private Activity currentActivity;
	private int timeProcessed = 0;
	private int simulationTime = 0;

	public ActivityQueue() {
		activityQueue = new LinkedList<>();
		finishedActivities = new ArrayList<>();
	}

	public void process(BufferedWriter writer) {
		
		if (isNull(currentActivity) && activityQueue.size() != 0) {
			currentActivity = activityQueue.poll();
			consumeActivityTime();
		}
		else if(nonNull(currentActivity)){
			consumeActivityTime();
		}
		
		activityQueue.forEach(Activity::addTimeWaiting);
		printQueueStatus(writer);
	}

	public int getSimulationTime() {
		return simulationTime;
	}
	
	public int getTotalWaitingTime() {
		int totalWaitingTime = 0;
		for (Activity activity : finishedActivities) {
			totalWaitingTime += activity.getTimeWaiting();
		}
		return totalWaitingTime;
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
	
	private void printQueueStatus(BufferedWriter writer) {
		StringBuilder result = new StringBuilder()
				.append("******************************************")
				.append("\n")
				.append("Status do Servidor: ")
				.append(nonNull(currentActivity)
						?"OCUPADO ->" + currentActivity.getId()
						: "LIVRE")
				.append("\n")
				.append("FILA: ")
				.append(activityQueue.size() == 0 ? "VAZIA" : activityQueue)
				.append("\n")
				.append("Tempo de Simulação: " + simulationTime++);

		try {
			writer.write(result.toString());
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add(Activity activity) {
		activityQueue.add(activity);
	}

}
