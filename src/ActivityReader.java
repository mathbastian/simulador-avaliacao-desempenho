import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.util.LinkedList;
import java.util.Queue;

public class ActivityReader {

	public Queue<Activity> read(){
		Queue<Activity> activities = new LinkedList<>();
		
		try {
			try (BufferedReader bufferedReader = 
					new BufferedReader(new FileReader(FileSystems.getDefault().getPath("").toAbsolutePath() + "/dados_trabalho.csv" ))) {
			    String line;
			    int id = 1;
			    while ((line = bufferedReader.readLine()) != null) {
			        String[] values = line.replace(",",".").split(";");
			        Activity activity = new Activity(
			        		id, 
			        		Math.round(Float.valueOf(values[0])),
			        		Math.round(Float.valueOf(values[1])));
			        activities.add(activity);
			        id++;
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		/*
		 * for (int i = 0; i < 200; i++) { int min = 1; int max = 100;
		 * 
		 * int arrivalTime = (int) Math.floor(Math.random() * (max - min + 1) + min);
		 * int timeItTakes = (int) Math.floor(Math.random() * (max - min + 1) + min);
		 * 
		 * Activity activity = new Activity(i + 1, arrivalTime, timeItTakes);
		 * 
		 * activities.add(activity); }
		 */
		
		return activities;
	}
}
