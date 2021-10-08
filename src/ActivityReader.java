import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ActivityReader {
	public Queue<Activity> read(){
		Queue<Activity> activities = new LinkedList<Activity>();
		
		try {
			try (BufferedReader bufferedReader = 
					new BufferedReader(new FileReader(FileSystems.getDefault().getPath("").toAbsolutePath() + "\\dados.csv" ))) {
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
		
		return activities;
	}
}
