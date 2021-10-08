import java.util.LinkedList;
import java.util.Queue;

public class ActivityQueue {

	private Queue<Activity> activityQueue;
	private Activity currentActivity;
	private int timeProcessed = 0;
	private int simulationTime = 0;
	
	public boolean process() {

		printQueueStatus();
		
		if ( activityQueue.isEmpty() && currentActivity == null )
			return true;
		
		if (currentActivity == null && activityQueue.size() != 0) {
			currentActivity = activityQueue.poll();
			consumeActivityTime();
		}
		else if(currentActivity != null){
			consumeActivityTime();
		}
		
		return false;
	}

	private void consumeActivityTime() {
		if( timeProcessed >= currentActivity.getTimeItTakes() ) {
			currentActivity = null;
			timeProcessed = 0;
		}
		else {
			timeProcessed++;
		}
	}
	
	private void printQueueStatus() {
		simulationTime++;
		
		System.out.println("******************************************");
		
		if(currentActivity != null) 
			System.out.println("Status do Servidor: OCUPADO -> " + currentActivity.getId());
		else
			System.out.println("Status do Servidor: LIVRE");
		
		if(activityQueue.size() == 0)
			System.out.println("FILA: VAZIA");
		else
			System.out.println("FILA: " + activityQueue.toString());
		
		System.out.println("Tempo de Simulação: " + simulationTime);
	}
	
	public void add(Activity activity) {
		activityQueue.add(activity);
	}
	
	public ActivityQueue() {
		activityQueue = new LinkedList<Activity>();
	}
}
