import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ActivityQueue {

	private List<ActivityProcessor> servers;
	private ActivityOrganizer organizer;
	private BufferedWriter writer;
	private int simulationTime = 0;

	public ActivityQueue(int queueCount, int serverCount, BufferedWriter writer) {
		servers = new ArrayList<>();
		this.writer = writer;
		int serversCreated = 0;
		int queuesCreated = 0;
		do {
			ActivityProcessor server = null;
			if(queuesCreated < queueCount) {
				server = new ActivityProcessor(serversCreated, true);
				queuesCreated++;
			}
			else {
				server = new ActivityProcessor(serversCreated, false);				
			}
			
			servers.add(server);
			serversCreated++;
		} while (serversCreated < serverCount);
		organizer = new ActivityOrganizer(servers);
	}

	public void process() {
		organizer.occupyIdleServers();
		servers.forEach(ActivityProcessor::process);
		printQueueStatus();
	}

	public int getSimulationTime() {
		return simulationTime;
	}
	
	public int getTotalWaitingTime() {
		int totalWaitingTime = 0;
		
		for (ActivityProcessor server : servers) {
			totalWaitingTime += server.getTotalWaitingTime();
		}
		
		return totalWaitingTime;
	}

	public boolean hasEndedProcessing() {
		for (ActivityProcessor server : servers) {
			if(server.hasEndedProcessing() == false)
				return false;
		}
		return true;
	}
	
	private void printQueueStatus() {
		StringBuilder result = new StringBuilder();
		result.append("******************************************")
			  .append("\n");
		
		for (ActivityProcessor server : servers) {
			if(server.actsAsQueue()) {
				result.append("FILA  " + ( server.getId() + 1 ) + ": ");
				result.append(server.getQueueSize() == 0 ? "VAZIA" : server.getActivityQueue());
				result.append("\n");
			}
		}

		result.append("\n");
		
		for (ActivityProcessor server : servers) {
			result.append("Status do Servidor " + ( server.getId() + 1 ) + ":");
			result.append(nonNull(server.getCurrentActivity())
					?"OCUPADO ->" + server.getCurrentActivity().getId()
					: "LIVRE");

			result.append("\n");
		}
		
		result.append("\n");
		result.append("Tempo de Simulação: " + simulationTime++);

		try {
			writer.write(result.toString());
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add(Activity activity) {
		ActivityProcessor server = getFreeServer();
		server.add(activity);
	}
	
	private ActivityProcessor getFreeServer() {
		//get by current activity
		for (ActivityProcessor server : servers) {
			if(isNull(server.getCurrentActivity())) {
				return server;
			}
		}

		//in case all servers are occupied, get by queue size
		//only from servers that act as a queue
		ActivityProcessor freeServer = null;
		int queueSize = Integer.MAX_VALUE;
		
		for (ActivityProcessor server : servers) {
			if(server.actsAsQueue() && server.getQueueSize() <= queueSize) {
				freeServer = server;
				queueSize = server.getQueueSize();
			}
		}
		return freeServer;
	}

}
