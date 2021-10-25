import javax.sound.midi.MidiFileFormat;
import java.io.*;
import java.util.Optional;
import java.util.Queue;

public class Main {

	private static int simulationTime = 0;
	private static int activitiesSize = 0;
	private static int totalWaitingTime = 0;
	private static Queue<Activity> activities;
	private static ActivityQueue queue;

	public static void main(String[] args) {
		activities = new ActivityReader().read();
		activitiesSize = activities.size();
		queue = new ActivityQueue();

		BufferedWriter executionLog = createFile("executionLog.txt");
		ActivityQueue queue = new ActivityQueue(3, executionLog);
		
		int time = 0;

		while (activities.size() > 0) {
			
			if (activities.peek().getArrivalTime() == time) {
				queue.add(activities.poll());

				checkNextItemQueue();
				time = 0;
			}
			else {
				time++;
			}
			
			queue.process();
		}

		//process the remaining activities in the queue
		while(queue.hasEndedProcessing() == false) {
			queue.process();
		}
		simulationTime = queue.getSimulationTime();
		totalWaitingTime = queue.getTotalWaitingTime();

		try {
			executionLog.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		generateResultMetric();
	}

	private static void checkNextItemQueue() {
		if (Optional.ofNullable(activities.peek()).map(Activity::getArrivalTime).map(i -> i == 0).orElse(false)) {
			queue.add(activities.poll());
			checkNextItemQueue();
		}
	}

	private static void generateResultMetric() {
		BufferedWriter resultMetric = createFile("resultMetric.txt");
		String result =
				"********************** Métricas ********************" +
				"\n" +
				"Tempo total para atender todas as demandas: " + simulationTime +
				"\n" +
				"Tempo médio de espera na fila: " + (totalWaitingTime/activitiesSize);

		try {
			resultMetric.write(result);
			resultMetric.newLine();
			resultMetric.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedWriter createFile(String fileName) {
		BufferedWriter writer = null;
		try {
			File resultFile =  new File(fileName) ;
			FileOutputStream fos = new FileOutputStream(resultFile) ;
			writer = new BufferedWriter(new OutputStreamWriter(fos)) ;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return writer;
	}

}
