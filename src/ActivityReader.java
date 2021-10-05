import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ActivityReader {
	public List<Activity> read(){
		List<Activity> activities = new ArrayList<Activity>();
		
		try {
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader("<<file>>"))) {
			    String line;
			    while ((line = bufferedReader.readLine()) != null) {
			        String[] values = line.split(";");
			        Activity activity = new Activity(Integer.valueOf(values[0]), Integer.valueOf(values[1]));
			        activities.add(activity);
			    }
			}
		} catch (Exception e) {
			return null;
		}
		
		return activities;
	}
}
