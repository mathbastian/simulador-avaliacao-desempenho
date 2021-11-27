import org.apache.commons.math3.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static java.lang.Float.parseFloat;
import static java.lang.Math.round;

public class ActivityReader {


	public Queue<Activity> read(InputDataType inputDataType){
		return switch (inputDataType) {
			case STATIC_VALUES -> staticValueReader();
			case DISTRIBUITION -> distribuitonValueReader();
		};
	}

	private Queue<Activity> distribuitonValueReader() {
		Queue<Activity> activities = new LinkedList<>();
		try {
			// Generate ARRIVAL TIME FILE
			List<Integer> arrivalTimeList = readValuesByDistribuitionFile(ApplicationParameters.INPUT_ARRIVAL_TIME_FILE);

			// Generate TIME IT TAKES FILE
			List<Integer> timeItTakeList = readValuesByDistribuitionFile(ApplicationParameters.INPUT_TIME_IT_TAKES_FILE);

			IntStream.range(0, ApplicationParameters.NUMBER_OF_GENERATIONS).forEach(id -> {
				Activity activity = new Activity(
						id,
						round(arrivalTimeList.get(id)),
						round(timeItTakeList.get(id)));
				activities.add(activity);

			});

		} catch (Exception ioException) {
			ioException.printStackTrace();
			return null;
		}

		return activities;
	}

	private List<Integer> readValuesByDistribuitionFile(String fileName) throws IOException {
		BufferedReader reader = generateReader(fileName);
		HashMap<Pair<Integer, Integer>, Integer> distribuition = new HashMap<>();
		String line;

		while ((line = reader.readLine()) != null) {
			String[] values = line.replace(",",".").split(";");
			distribuition.put(new Pair(round(parseFloat(values[0])), round(parseFloat(values[1]))), Integer.valueOf(values[2]));
		}

		AtomicReference<Integer> minRangeValue = new AtomicReference<>(0);
		AtomicReference<Integer> maxRangeValue = new AtomicReference<>(0);

		ArrayList<Integer> resultList = new ArrayList<>();
		distribuition.forEach((range, ocorrences) -> {
			if (range.getFirst() < minRangeValue.get()) {
				minRangeValue.set(range.getFirst());
			}
			if (range.getSecond() > maxRangeValue.get()) {
				maxRangeValue.set(range.getSecond());
			}

			IntStream.range(0, ocorrences)
					.forEach(value -> resultList.add(new ValueGenerator().randonInt(range.getFirst(), range.getSecond())));
		});

			if (resultList.size() < ApplicationParameters.NUMBER_OF_GENERATIONS) {
				IntStream.range(0, ApplicationParameters.NUMBER_OF_GENERATIONS - resultList.size())
						.forEach(value -> resultList.add(new ValueGenerator().randonInt(minRangeValue.get(), maxRangeValue.get())));
			}

			return resultList;
	}

	private Queue<Activity> staticValueReader() {
		Queue<Activity> activities = new LinkedList<>();
		try {
			try (BufferedReader bufferedReader =
						 new BufferedReader(new FileReader(FileSystems.getDefault().getPath("").toAbsolutePath() + "/" + ApplicationParameters.INPUT_STATIC_FILE))) {
				String line;
				int id = 1;
				while ((line = bufferedReader.readLine()) != null) {
					String[] values = line.replace(",",".").split(";");
					Activity activity = new Activity(
							id,
							round(parseFloat(values[0])),
							round(parseFloat(values[1])));
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

	private BufferedReader generateReader(String fileName) throws FileNotFoundException {
		return new BufferedReader(new FileReader(FileSystems.getDefault().getPath("").toAbsolutePath() + "/" + fileName));
	}

}
