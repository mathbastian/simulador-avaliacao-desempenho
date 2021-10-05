import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

public class ActivityReader {
	public List<Activity> read(){
		List<Activity> activities = new ArrayList<Activity>();
		
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
