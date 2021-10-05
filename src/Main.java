import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Activity> activities = new ActivityReader().read();
		
		for (int i = 0; i < activities.size(); i++) {
			//processamento
		}
	}

}
