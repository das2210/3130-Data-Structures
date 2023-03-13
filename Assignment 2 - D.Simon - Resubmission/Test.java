package sitter;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;       
import java.io.PrintWriter;

	/**
	 * @Dacia Simon  Assignment 2
	 * 
	 * Program reads in personnel data and payroll data on employees from two files.
	 * That data is used to calculate the employee's pay.
	 * 
	 */

public class Test {

	public static void main(String[] args) {
		
		ArrayList<Employee> employees = new ArrayList<>();
		Scanner sc = null;
		PrintWriter outputFile = null;
		
		
		try {
			File file = new File("Personnel_data.txt");
			sc = new Scanner(file);
			createEmployees(sc, employees); //create an arraylist of employees
		}
		
		catch(IOException e) {
			System.out.println("Personnel_data.txt File not found");
			System.exit(0); //expected error occurred. 
		}
	

		
		try {
			File file2 = new File("Payroll_data.txt");
			sc = new Scanner(file2);
			getEmployeeHours(sc, employees); 
			
		}
		catch(IOException e) {
			System.out.println("Payroll_data.txt File not found");
			System.exit(0); 
		}
		
		try {
			outputFile = new PrintWriter("Payroll_output.txt");
			sortEmployees(employees); //sort employees by last name.
			printEmployees(employees, outputFile);  //output sorted employees to a file.
		}
		catch(IOException ex) {
			System.out.println("Payroll_output.txt File not found");
			System.exit(0);
		}
		
		sc.close();
		outputFile.close();
	}
	 
	/**
	 * Reads in personnel data from a file to create employee objects that reference the employee's pay rate per hour.
	 * @param sc
	 * @param employees
	 */
	
	public static void createEmployees(Scanner sc, ArrayList<Employee> employees) {
		
		while(sc.hasNext()){
			
			employees.add(new Employee(sc.nextLine(),sc.nextLine(),sc.nextLine()+ ", " +sc.nextLine(),
					     sc.nextDouble(), sc.nextDouble(),sc.nextDouble()));
			
			sc.nextLine();  
			sc.nextLine(); 
				
		}	
	}
	
	/**
	 * Reads in and adds each employee's worked hours to their specific object (if found) in minutes. 
	 * @param sc
	 * @param employees
	 */
	
	public static void getEmployeeHours(Scanner sc, ArrayList<Employee> employees) {
		
		String[] startTime = new String[2];
		String[] endTime = new String[2];
		
		while(sc.hasNext()) {
			String id = sc.nextLine();
			int employeeAt = findEmployee(id, employees); //check if the employee has a personnel file.
			int days_worked = Integer.parseInt(sc.nextLine());
			
			if(employeeAt!=-1) {
								
				for(int i=0; i<days_worked;i++) {
					
					startTime = sc.next().split(":"); //8:30 becomes [0]8, [1]30
					endTime = sc.next().split(":");
					
					//converts hours to minutes
					long start_Minutes = Integer.parseInt(startTime[0])*60 + Integer.parseInt(startTime[1]);
					long end_Minutes = Integer.parseInt(endTime[0])*60 + Integer.parseInt(endTime[1]);
					
					employees.get(employeeAt).getPayroll().setHoursWorked(start_Minutes, end_Minutes);
				}
			}
			
			else {
				//if the employee doesn't already have an object, outputs alert. Program moves on to the next employee.
				System.out.print("Employee does not exist. Update personnel file to add.");
				for(int i=0; i<days_worked;i++);
					sc.next();
					sc.next();
			}
			sc.nextLine();
			sc.nextLine();	
		}
			
	}
	
	/**
	 * Checks employee that the employee id exists in the Employee arraylist to facilitate adding their hours worked.
	 * @param id
	 * @param employees
	 * @return 
	 */
	public static int findEmployee(String id, ArrayList<Employee> employees) {
		
		for(int i=0; i<employees.size(); i++) {
			if(employees.get(i).getId().equals(id)) {
				return i;
			}
		}
		
		return -1;
	}

	/**
	 * Sorts employees by last name (A to Z); Updates original arraylist.
	 * @param employees
	 */
	
	public static void  sortEmployees(ArrayList<Employee> employees) {

		
			for(int i=0; i<employees.size(); i++) {
				
				String min = employees.get(i).getName();
				
				for(int j=i+1; j<employees.size(); j++) {
					
					if(employees.get(j).getName().compareTo(min)<0) {
						
						min = employees.get(j).getName();
						
						//swap objects based on last name order.
						Employee temp = employees.get(i); 
						employees.set(i, employees.get(j));
						employees.set(j, temp);
						
					}
				}
			}		
	}
	
	/**
	 * Outputs the list of employees to the given output file.
	 * @param employees
	 * @param output
	 */
	
	public static void printEmployees(ArrayList<Employee> employees, PrintWriter output) {
		
		
		for(Employee e: employees) {
			
			output.println(e);
			output.println();
		}
		
	}
}
