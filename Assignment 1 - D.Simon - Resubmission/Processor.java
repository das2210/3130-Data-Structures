package homework1;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class Processor {

		private static ArrayList<Account> accounts = new ArrayList<>();
		private String masterFile;
		private String transFile;
		private String invoiceFile;
		
		private StringBuilder line; 
		private int id;
		private int trans_num;
		private String transType;
		
		
		private Scanner readMaster;
		private Scanner readTransactions;
		private PrintWriter invoice;
		
		private Scanner sc = new Scanner(System.in); // edit out once comp names added to trans file
		

	public Processor(String masterFile, String transFile, String invoiceFile) throws IOException{
		
		this.masterFile = masterFile;
		this.transFile = transFile;
		this.invoiceFile = invoiceFile;
		
		try {
			readMaster = new Scanner(new File(masterFile));
			readTransactions = new Scanner(new File(transFile));
			invoice = new PrintWriter (new File(invoiceFile));
		}
		catch(Exception e){
			System.out.println("File not found");
		}
				
		loadAccounts();
	}
	
	
	//add unique accounts to array of accounts from master file
	private void loadAccounts() { 
		
		while (readMaster.hasNext()) {
			boolean duplicate = false;
			
			line = new StringBuilder(readMaster.nextLine());
			
			id = Integer.parseInt(line.substring(0, 4));
			String name = line.substring(5,25);
			double balance = Double.parseDouble(line.substring(26));
					
			
			if(accounts.size()==0) {
				accounts.add(new Account(id,name,balance)); 
			}
			
			
			else { 
				
				for(int i=0; i<accounts.size(); i++) {   //check for duplicates
					if(id==accounts.get(i).getId()) { 
					
						System.out.println("No duplicates; Rejected: [Company " +name.trim() + "| id " + id + "| Balance " + balance +"]");
						duplicate = true;
					}
				}
				if(!duplicate) {
					accounts.add(new Account(id,name,balance));	//add unique accounts
				}
			}
	}
		
		loadTransactions();
	}
	
	
	//
	private void loadTransactions() {
		
		while(readTransactions.hasNextLine()) { 
			
			boolean acctFound = false;
			line = new StringBuilder(readTransactions.nextLine());
			
			//all defined as data fields since used in multiple places
			transType = line.substring(0, 1);
			id = Integer.parseInt(line.substring(7, 11));       
			trans_num = Integer.parseInt(line.substring(2, 6));
			
			
			
			for(int i=0; i<accounts.size(); i++) {
				
				
				if (transType.equals("O") && id==accounts.get(i).getId()){
					addOrder(accounts.get(i));
					acctFound=true;
				}

				else if (transType.equals("P") && id==accounts.get(i).getId()){
					addPayment(accounts.get(i));
					acctFound=true;
				}
			}
			
			//an account not in the master file is added as first transaction user makes at ABC Hardware.
			if(!acctFound) {
				StringBuilder sb; 
				do {
					System.out.println("New account found. Enter the company name for id " + id + "(20 char or less)");
					sb = new StringBuilder(sc.next());
					
					if(sb.length()>20) 
						System.out.println("Try Again.");
					
				}while(sb.length()>20);
				
				int len = sb.length();
					for(int i=0; i<20-len;i++) {
						sb.append(" ");
				}	
				
				Account createdAcct = new Account(id, sb.toString(), 0.0); //user enters since company not in file
				accounts.add(createdAcct);								
				
				
				if(transType.equals("O")) {
					addOrder(createdAcct); 
				}
				
				else if((transType.equals("P"))) {	
					addPayment(createdAcct);	
				}		
			}
		}
		
		
		try {
			updateMaster(); 
			
		}catch(IOException ex) {
			System.out.println("File not found");
		}
	 }
	
	
	
	private void addOrder(Account account) {
		
		String item = line.substring(12, 33);
		int quantity = Integer.parseInt(line.substring(33, 37).trim());
		double cost = Double.parseDouble(line.substring(37, 46).trim());
		String discount = line.substring(46).trim();
		
		
		if(discount.length()==0) { 
			account.addOrder(new Order(id, trans_num, item, quantity, cost));			 
		}
		
		else {
			account.addOrder(new Order(id, trans_num, item, quantity, cost, Integer.valueOf(discount)));						 
		}
			
	}
	
	
	private void addPayment(Account account) {
		
		double paid = Double.parseDouble(line.substring(12,22));
		String discount = line.substring(22).trim();
		
		 if(discount.length()==0) { 
			
			account.addPayment(new Payment(id, trans_num,paid));						 
		}
		 
		 else {	 
				account.addPayment(new Payment(id, trans_num,Integer.valueOf(discount), paid));						 
		}
	}
	
	
	private void updateMaster() throws IOException {
		int i=0;
		PrintWriter masterFile = new PrintWriter(getMasterFile());
		
		
		while(i<accounts.size()) {
			
			masterFile.printf("%s\n",accounts.get(i).toString());
			i++;
		}
		
		masterFile.close();
		printInvoice();
	}
	
	
	public String getMasterFile() {
		return masterFile;
	}
	
	
	private void addAccount(Account acount) {
		accounts.add(acount);
	}
	
	
	public ArrayList<Account> getAccounts(){
		return accounts;
	}
	
	
	private void printInvoice() {
		int i=0;
		while(i<accounts.size()) {
			double credit=0;
			invoice.printf("%s\t%d\n", accounts.get(i).getCompany(), accounts.get(i).getId());
			
			invoice.printf("%40s  %.2f\n", "Previous Balance: ", accounts.get(i).getOldBalance());
				
			ArrayList<Order> orders = accounts.get(i).getOrders();
			ArrayList<Payment> payments = accounts.get(i).getPayments();
				for(int j=0;j<orders.size(); j++) {
					invoice.println(orders.get(j));
				}
				for(int k=0;k<payments.size(); k++) {
					invoice.println(payments.get(k));
				}
				
				
				invoice.printf("%40s %.2f", "Current Balance:", accounts.get(i).getBalance());
				invoice.println("\n\n");
			
			i++;		
		}
	
		invoice.printf("* means discount was applied (Payments)");
		invoice.close();
	}
			
}