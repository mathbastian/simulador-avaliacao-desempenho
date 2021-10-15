import java.io.*;
import java.util.Queue;

public class Main {

	public static BufferedWriter writer;

	private static int simulationTime = 0;

	private static int activitiesSize = 0;

	public static void main(String[] args) {
		Queue<Activity> activities = new ActivityReader().read();
		activitiesSize = activities.size();

		ActivityQueue queue = new ActivityQueue();
		createResultFile();

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
			
			queue.process(writer);
		}

		//process the remaining activities in the queue
		while(queue.hasEndedProcessing() == false) {
			queue.process(writer);
			simulationTime = queue.getSimulationTime();
		}

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		printResultMetric();
	}

	private static void printResultMetric() {
		String result =
				"********************** Métricas ********************" +
				"\n" +
				"Tempo total para atender todas as demandas: " + simulationTime +
				"\n" +
				"Tempo médio de espera na fila: " + (simulationTime/activitiesSize);

		System.out.println(result);
	}

	public static void createResultFile() {
		try {
			File resultFile =  new File( "resultLog.txt" ) ;
			FileOutputStream fos = new FileOutputStream(resultFile) ;
			writer = new BufferedWriter(new OutputStreamWriter(fos)) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
