import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;

public class ActivityOrganizer {
	
	private List<ActivityProcessor> servers;
	
	public ActivityOrganizer(List<ActivityProcessor> servers) {
		this.servers = servers;
	}

	public void occupyIdleServers(){
		//this is called always after activities are added to the servers
		//but sometimes nothing might be added, so we must rearrange activities
		//to be sent from queued servers to idle servers that do not have a queue
		for (ActivityProcessor server : servers) {
			if(isServerIdle(server)) {
				Activity activity = getActivityFromSmallerQueue();
				if(nonNull(activity))
					server.add(activity);
			}				
		}
		
	}
	
	private boolean isServerIdle(ActivityProcessor server) {
		// removed !server.actsAsQueue() 
		if(server.getQueueSize() == 0 && isNull(server.getCurrentActivity()))
			return true;
		return false;
	}
	
	private Activity getActivityFromSmallerQueue() {
		ActivityProcessor freeServer = null;
		int queueSize = Integer.MAX_VALUE;
		
		for (ActivityProcessor server : servers) {
			if(server.actsAsQueue() && server.getQueueSize() <= queueSize && server.getQueueSize() > 0) {
				freeServer = server;
				queueSize = server.getQueueSize();
			}
		}
		
		if (nonNull(freeServer))
			return freeServer.getActivityQueue().poll();
		else
			return null;
	}

}
