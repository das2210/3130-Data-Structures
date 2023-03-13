package sitter;

/**
 * Workhours represents the start and end time of an employees's work session.
 * @author dacia
 *
 */
public class WorkHours {

	private long startTime;
	private long endTime;
	
	//time in minutes after 12pm for range of times (in minutes) when pay rate changes:
	//6pm - 9pm
	private static WorkHours before9 = new WorkHours(360,540); 
	//9pm - 12am
	private static WorkHours from9to12 = new WorkHours(540,720);
	//12am - 6am
	private static WorkHours post12 = new WorkHours(720,1080);
	
	
	public WorkHours(long startTime, long endTime) {
		
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public String toString() {
		return "Start Time: " + startTime + "End Time: " + endTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public static WorkHours getBefore9() {
		return before9;
	}

	public static WorkHours getFrom9to12() {
		return from9to12;
	}

	public static WorkHours getPost12() {
		return post12;
	}
	
	
}
