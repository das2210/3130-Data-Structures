package homework1;

import java.io.IOException;


/* Dacia Simon - Assignment 1 - CISC3130 
 * Program calculates how much money is owed to the ABC Hardware Company
 */

public class ABCHardwareProgram {

	public static void main(String[] args) {

		//generates an invoice and update input master file based on input transaction file.
		try {
			
			new Processor("Master13123-test.txt", "Transactions20723prt2.txt","homework1-output-Simon.txt");
			System.out.println("Master file updated. Invoices updated. End of Program");
			
		}catch(IOException e) {
			
			System.out.print("File not found.");
			System.exit(0);
		
		}
	}

}
