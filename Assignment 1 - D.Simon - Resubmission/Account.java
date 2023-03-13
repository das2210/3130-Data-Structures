package homework1;

import java.util.ArrayList;
import java.io.*;


public class Account {   
	
	private String company;
	private int id; 
	private double old_balance;
	private double balance;
	private ArrayList<Order> orders = new ArrayList<>(); 
	private ArrayList<Payment> payments = new ArrayList<>(); 
	private static int numAccts;
	
	
	public Account(int id, String company, double old_balance) {
		this.id=id;
		this.company=company; 
		this.old_balance=old_balance;
		
		balance = old_balance; //holds previous balance
		numAccts++;	
	}
	
	
	public double getOldBalance() {
		return old_balance;
	}
		
	
	//add order to arraylist of orders
	public void addOrder(Order orderTrans) { 
		boolean duplicate = false;
		
		if(orders.size()==0) { // no orders yet in account; no need to check for duplicates.

			orders.add(orderTrans);
			balance+=orderTrans.getTotal();
		}
		
		else {  // check for duplicates before adding order to arraylist.
			
			for(int i=0; i<orders.size(); i++) { 
				                                 
				duplicate = orders.get(i).equals(orderTrans);
				
				if(duplicate) {
					System.out.println("duplicate not added " + orderTrans.toString());
					return;
				}	
			}
				orders.add(orderTrans);
				balance+=orderTrans.getTotal();
			}	
		}
	
	
	//add payment to arraylist of payments 
	public void addPayment(Payment payTrans) { 
		
		boolean duplicate = false;
		
		//check if duplicate account before adding to arraylist.
		for(int i=0; i<payments.size(); i++) {  
				                                 
			duplicate = payments.get(i).equals(payTrans);
				
			if(duplicate) {
				System.out.println("duplicate " + payTrans.toString());
				return;
			}
		}
				payments.add(payTrans);
				balance-=(payTrans.getCredit() + payTrans.getPaid()) ;
	}
			
		
	public static int getNumAccts() {
		return numAccts;
	}
	
	
	public String getCompany() {
		return company;
	}

	
	public int getId() {
		return id;
	}

	
	public double getBalance() {
		return balance;
	}

	
	public ArrayList<Order> getOrders() {
		return orders;
	}


	public ArrayList<Payment> getPayments() {
		return payments;
	}
	
	
	public String toString() {
		return String.format("%d  %-10s  %-3.2f",id, company, balance);
	}

}

