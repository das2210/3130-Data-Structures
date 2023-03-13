package sitter;

import java.util.ArrayList;

/**
 * Employee's payroll data detailing the rate based on the hour, how many days worked, and the specific hours worked.
 * @author dacia
 */

public class Payroll {

	private int days_worked;
	private double rate_pre9;
	private double rate_9to12;
	private double rate_post12;
	//all of the employee's time worked 
	private ArrayList<WorkHours> hoursWorked = new ArrayList<WorkHours>();
	private double totalPay;
	
	private long start; // to represent start times.
	private long end; // to represent end times.

	public Payroll(double rate_pre9, double rate_9to12, double rate_post12) {

		this.rate_pre9 = rate_pre9 / 60.0; //rate by the minute from 6pm-9pm
		this.rate_9to12 = rate_9to12 / 60.0; //rate by the minute from 9pm-12am
		this.rate_post12 = rate_post12 / 60.0; //rate by the minute from 12am-6am

	}

	public int getDays_worked() {
		return days_worked;
	}

	public double getRate_pre9() {
		return rate_pre9;
	}

	public double getRate_9to12() {
		return rate_9to12;
	}

	public double getRate_post12() {
		return rate_post12;
	}

	@Override
	public String toString() {

		return String.format("%s%.2f", "Total Pay: $",totalPay);
		
		/* valid but hours format is questionable.
		StringBuilder sentence = new StringBuilder();

		for (int i = 0; i < days_worked; i++) {
			sentence.append("day " + (i + 1) + " worked from " + hoursWorked.get(i).getStartTime() + " to "
					+ hoursWorked.get(i).getEndTime() + "\n");
		}

		return "*work hours in minutes after 12pm:\n" + String.format("%s%s%.2f", sentence.toString(), "Total Pay: $", totalPay);
		 */
	}

	/**
	 * Adds times worked to the Workhours arraylist in minutes.  
	 * @param startTime
	 * @param endTime
	 */
	public void setHoursWorked(long startTime, long endTime) {
		
		//Assesses if the time is AM or PM, and therefore how many minutes elapsed after 12pm
		
		if (startTime < endTime && startTime >= WorkHours.getBefore9().getStartTime()) {
			hoursWorked.add(new WorkHours(startTime, endTime));
			
		} else if (startTime > endTime && startTime >= WorkHours.getBefore9().getStartTime()) {
			hoursWorked.add(new WorkHours(startTime, endTime + (12 * 60)));
			
		} else if (startTime < WorkHours.getBefore9().getStartTime()) {
			hoursWorked.add(new WorkHours(startTime + (12 * 60), endTime + (12 * 60)));
		}

		try {
			calculatePay();
			days_worked++; 
		}
		catch(Exception e) {
			System.out.println("Invalid work hours.Start time is outside of 6pm - 6am");
			System.exit(0);
		}

	}
	
	/**
	 * Starts to calculate employees pay based on what time the employee starts working. 
	 * Calls appropriate methods based on start time.
	 * @throws Exception
	 */
	private void calculatePay() throws Exception {
		
		start = hoursWorked.get(days_worked).getStartTime(); //in minutes
		end = hoursWorked.get(days_worked).getEndTime(); //in minutes
		
		//start hours are between 6pm - 9pm
		if(start >= WorkHours.getBefore9().getStartTime()
		   && start < WorkHours.getBefore9().getEndTime()) {
			
			pay_Pre9_Start();
			
		}
		//start hours are between 9pm - 12am
		else if(start >= WorkHours.getFrom9to12().getStartTime()
				&& start < WorkHours.getFrom9to12().getEndTime()) {
					
			pay_from9_Start();
					
		}
		//start hours are between 12am - 6am
		else if (start>=WorkHours.getPost12().getStartTime()
				&& start < WorkHours.getPost12().getEndTime()) { 
			
			/*no method call since if the employee starts work after 12am, they
			 * won't work outside of that pay rate.
			 */
			totalPay+= (end-start)* rate_post12; 
		}
		
		else {
			throw new Exception("start time is outside of 6pm - 6am");
		}
		
	}
	
	/**
	 * Calculates pay, given a start time between 6pm - 9pm and a variable end time. 
	 */
	
	private void pay_Pre9_Start() {
	
		//start and ends btw 6-9
		if(end <= WorkHours.getBefore9().getEndTime()) {
			
			totalPay += (end-start)* rate_pre9; 
		}
		
		//start btw 6-9 ends btw 9-12
		else if(end <= WorkHours.getFrom9to12().getEndTime()) {
			
			totalPay += (WorkHours.getBefore9().getEndTime() - start)* rate_pre9 
					    + (end - WorkHours.getFrom9to12().getStartTime()) * rate_9to12;
		}
		
		//start btw 6-9 ends btw 12 - 6am;         
		
		else if(end <= WorkHours.getPost12().getEndTime()) {
			
			totalPay += (WorkHours.getBefore9().getEndTime() - start)* rate_pre9 
					    + (WorkHours.getFrom9to12().getEndTime() - WorkHours.getFrom9to12().getStartTime()) * rate_9to12
					    + (end - WorkHours.getPost12().getStartTime()) * rate_post12;
		}
		
	}
	
	/**
	 * Calculates pay, given a start time between 9pm-12am
	 */
	
	private void pay_from9_Start() {
	
		//start and ends btw 9-12
		if(end <= WorkHours.getFrom9to12().getEndTime()) {
			
			totalPay += (end-start)* rate_9to12; 
		}
		
		//start btw 9-12 ends btw 12-6am
		else if(end <= WorkHours.getPost12().getEndTime()) { 
			
			totalPay += (WorkHours.getFrom9to12().getEndTime() - start)* rate_9to12
					    + (end - WorkHours.getPost12().getStartTime()) *  rate_post12;
		}    
		
	}

}
