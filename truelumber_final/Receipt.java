package truelumber_final;

import java.util.LinkedList;

/**
 * Program contains one class: Receipt.
 * Receipt class: represents True Lumber's wood purchases and will serve as inventory for future sales.
 * @author Dacia Simon
 *
 */

public class Receipt {
	
	private char type; 
	protected char wood;	
	protected int quantity;  
	protected double cost; 
	
	private final double ORIGINALCOST; 

	
	/**
	 * First creation of a receipt representing payment to True Lumber of payment by True Lumber.
	 * @param type - type of transaction: Receipt/Purchased (R) by True Lumber or a Sale's Receipt/Purchase (S) from True Lumber
	 * @param wood - oak or cherry wood
	 * @param quantity - total pieces of wood 
	 * @param cost - cost per quantity
	 */
	public Receipt(char type, char wood, int quantity, double cost) {
		this.type = type;
		this.wood = wood; 
		this.quantity = quantity;
		this.cost = cost;
		
		this.ORIGINALCOST=cost*quantity;
	}
	
	/**
	 * Constructor is used when there's a quantity deduction from an original receipt and we want to keep track of the original cost 
	 * @param type - type of transaction: Receipt/Purchased (R) by True Lumber or a Sale's Receipt/Purchase (S) from True Lumber
	 * @param wood - oak or cherry wood
	 * @param quantity - total pieces of wood 
	 * @param cost - cost per quantity
	 * @param ORIGINALCOST - retains the original payment amount for the wood that has decreased.
	 */
	
	public Receipt(char type, char wood, int quantity, double cost, double ORIGINALCOST) {
		this.type = type;
		this.wood = wood; 
		this.quantity = quantity;
		this.cost = cost;
		
		this.ORIGINALCOST= ORIGINALCOST;
	}
	
	/**
	 * Returns the type of wood purchased: Oak(O) or Charry(C)
	 * @return char of wood type
	 */
	public char getWood() {
		return wood;
	}

	/**
	 * Returns the amount of wood left after (if any) purchases were made.
	 * @return int of current wood quantity available.
	 */
	
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Returns the OriginalCost paid by True Lumber for this purchase.
	 * @return double value of total cost paid
	 */
	public double getOriginalCost() {
		return ORIGINALCOST;
	}

	/**
	 * Returns the cost per wood amount for this purchase.
	 * @return double value of cost per wood
	 */

	public double getCost() {
		return cost;
	}
	
	/**
	 * Returns a string representation of the wood puchased by True Lumber or the wood sold by True Lumber
	 * @return string 
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(type=='R') {
			return sb.append(" Wood: " + wood + "\t Quantity: " + quantity +"\t Cost/wood: $" +cost + "\t Total: $" + cost*quantity).toString();
		}
		else {
			return sb.append(" "+quantity + "\tat " +cost+ " each\tSale: $" + cost*quantity).toString();
		}
	}	

}



