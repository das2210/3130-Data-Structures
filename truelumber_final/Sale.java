package truelumber_final;

import java.util.*;
import java.util.Iterator;

/*
 * Sale class: inherts from the receipt class to create receipts for sales made by the True Lumber Company.
 * Promo class: represents the promotion (discounted prices) a customer can get.
 */


public class Sale extends Receipt{
	
	private LinkedList<Receipt> sales=new LinkedList<>();
	private char type = 'S';
	private Promo discount; // if any
	
	/**
	 * Initializes a sale object containing the items being requested. The initial quantity 
	 * may change depending on the amount of inventory True Lumber has. 
	 * @param type - Sale
	 * @param wood - Oak or Cherry
	 * @param quantity - Num of wood seeking to purchase
	 */
	public Sale(char type, char wood, int quantity) {
		//cost parameter is initialized to 0 since we have to use inventory to determine cost
		super(type, wood,quantity,0); 
	}
	
	/**
	 * The inventory is traversed to see if the sale can actually be completed based on the 
	 * available inventory. 
	 * @param inventory
	 * @return int of the amount of items that could not be sold due to limited inventory
	 */
	public int available(LinkedList<Receipt> inventory) {
		
		int amount = quantity; //amount of wood needed
		
		for(int i=0; i<inventory.size(); i++) {
			double upcharge = inventory.get(i).getCost()*(140/100.0); //40% cost increase
			
			if(discount!=null) {
				upcharge*=(100-discount.getDiscount())/100.0;
			}
			
			if(inventory.get(i).getWood()== wood) { //check if the wood in inventory is the same as the wood being requested
				
				if(inventory.get(i).getQuantity()>= amount) {
					
					//update purchase history after total items found in inventory
					update(wood,amount,upcharge);
					
					//decrease the amount of wood at this node in the inventory based on amount sold.
					inventory.set(i,new Receipt('R',wood,inventory.get(i).getQuantity()-amount,inventory.get(i).getCost(),
							      inventory.get(i).getOriginalCost()));
					amount = 0;
					return amount;  // all requested wood was acquired so amount=0;                                 
				}
				
				else{ //inventory.get(i).getQuantity()<amount
					update(wood, inventory.get(i).getQuantity(), upcharge); 
					amount -=inventory.get(i).getQuantity();
					inventory.remove(i);
					i--;//the node was removed, when we continue we want to iterate from the point where the node was taken.
				}
			
			}
		}
		
		if(amount>0) {
			quantity-=amount; //the original quantity data field changes; this is the new total quantity purchased 
		}                     //the amount not purchased is subtracted.
		
		return amount;//tells the main how many pieces of wood we couldn't purchase.
	}
	

	public void setQuantity(int amount) {
		super.quantity=amount;
	}
	
	/**
	 * The cost of each piece of wood is decreased if the discount data field is not null. 
	 * @param discount from the Promo class
	 */
	public void setDiscount(Promo discount) {
		this.discount = discount;
	}
	
	/**
	 * available() calls update if there is enough wood of the same requesting type in inventory.
	 * A new sales object is added to the linkedlist containing all other sales for one transaction. 
	 * The sales may need to be split up if there are different costs for x pieces of available wood 
	 * in a given inventory node. 
	 * 
	 * @param wood
	 * @param quantity
	 * @param cost
	 */
	private void update(char wood, int quantity, double cost) {
		sales.add(new Receipt('S',wood,quantity, cost));
	}
	
	/**
	 * 
	 * @return LinkedList<Receipt> of sales.
	 */
	public LinkedList<Receipt> getSales(){
		return sales;
	}


	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Total Quantity "+ quantity + "\n");
		
		for(int i=0;i<sales.size();i++) {
			
			sb.append(sales.get(i)+"\n");
		}
		
		return sb.toString();
		
	}


}

/**
 * Promo class creates discount objects that are applied within the sales class.
 * All sales do not have an applied discount.
 * @author Dacia Simon
 *
 */
class Promo {
	private char type = 'P';  //type of transaction: Promo
	private int discount; 
	
	public Promo(int discount) {
		this.discount = discount;
	}
	public int getDiscount(){
		return discount;
	}
	
	public String toString() {
		return getDiscount() + "% discount";
	}
	
}



