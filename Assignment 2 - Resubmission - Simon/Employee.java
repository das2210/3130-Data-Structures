package sitter;

/**
 * @author dacia
 * Represents employee personnel data. 
 * Instances of each employee's name, id, address, and a reference to the employee's payroll data
 * 
 */
public class Employee {
	
	private String name;
	private String id;
	private String address;
	private Payroll payroll;
	
	
	/**
	 * Constructor creates a payroll object and assigns id, name, and address.
	 * @param id
	 * @param name
	 * @param address
	 * @param rate_pre9
	 * @param rate_9to12
	 * @param rate_post12
	 */
	public Employee(String id, String name, String address,
					double rate_pre9, double rate_9to12, double rate_post12) {
		
		this.id = id; 
		this.name = name; 
		this.address = address;	
		
		//passes to Payroll class employee's specific pay rate. 
		//Employee + payroll "has-a" relationship
		payroll = new Payroll(rate_pre9, rate_9to12, rate_post12); 
	}

	
	public String getName() {
		return name;
	}

	
	public String getId() {
		return id;
	}

	
	public String getAddress() {
		return address;
	}
	
	
	public String toString() {
		return "Employee " + id + "| Name: " + name + "| Address: " + address + "\n" + payroll;
	}
	
	public Payroll getPayroll() {
		return payroll;
	}
	
	
}

