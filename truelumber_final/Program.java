package truelumber_final;
import java.util.*;
import java.io.*;

/**
 * Program stores an inventory of True Lumber's wood purchases and stores 
 * separately wood sold based on the available inventory. All storage is 
 * done using linked lists. 
 * 
 * @author Dacia Simon
 *
 */
public class Program {

	public static void main(String[] args) {
		
		//inventory of True Lumber wood purchases.
		LinkedList<Receipt> inventory = new LinkedList<>();
		
		//sales made given True Lumber's inventory.
		LinkedList<Sale> sales = new LinkedList<>();
	
		
		//reads in data of purchases and sales to add to the appropriate list (sales or inventory)
		try {
			Scanner input = new Scanner(new File("trueLumberTrans.txt"));

			char wood;
			int num_wood;
			Promo n = null;
			
			input.nextLine(); //file header skipped
			
			//add R or S transactions to linkedlist
			while(input.hasNext()) {
				
				char type = input.next().charAt(0); // R or S
				
				if(type == 'R') { //True Lumber purchases
					System.out.println();
					
					Purchase(input.next().charAt(0), input.nextInt(), input.next(), inventory);
					
				}
				
				else if(type == 'P') { //Promotional discount for the next sale made
					String percent = input.next();
					//percent (string) -> number
					int numPercent = Integer.parseInt(percent.substring(0,percent.length()-1));
					n = new Promo(numPercent);
				}
				
				else if(type == 'S') { //Sale transaction
					System.out.println();
					Sale(input.next().charAt(0), input.nextInt(), n, sales, inventory);	
				}	
				
				else {
					System.out.println("Incorrect type; Accepted tyes: R, S, or P"); 
				}
			}
	}
		//exits program if file is not found
		catch(IOException e) { 
			System.out.println("input file not found");
			System.exit(0);
		}
		
		//output the inventory left to sell
		System.out.println("\nRemaining Inventory");
		Iterator<Receipt> iterate = inventory.iterator();
		 
		while(iterate.hasNext()) {
			Receipt current = iterate.next();
			System.out.println(current + "\t  Original Total Paid: $" + current.getOriginalCost()); //WRONG*********** +" Original Payment: $"+ Receipt.getOriginalPaid());
		}
	
	}
	
	public static void Purchase(char wood, int num_wood, String price,LinkedList<Receipt> inventory) {
		
		//price (string) -> number
		double numPrice = Double.parseDouble(price.substring(1));
		
		Receipt receipt = new Receipt('R',wood,num_wood,numPrice);
		inventory.add(receipt); //adds receipt
		
		System.out.println("Purchase:\n"+receipt);
	}
	
	
	public static void Sale(char wood, int num_wood, Promo n, LinkedList<Sale> sales, LinkedList<Receipt> inventory) {
		
		Sale sale1 = new Sale('S',wood, num_wood);
		
		if(n!= null) {
			System.out.println(n);
			sale1.setDiscount(n); //apply the discount to the sale that followed the Promo in the file
			n=null; //reset the promo to null until the next promo is encountered to apply to next sale
		}
		
		
		int anyLeft = sale1.available(inventory); //check if all the requested num of wood was puchased.

		System.out.println("Sale: ");
		
		if(anyLeft==num_wood) { //no wood sold
			System.out.println("No " + wood + " type available for purchase");
		}
		else if(anyLeft>0){ // not all wood sold due to inventory limits.
			if(wood=='O') 
				System.out.println("Remainder of " +anyLeft + " pieces of Oak(" +wood+ ") unavailable for this purchase.");
			else
				System.out.println("Remainder of " +anyLeft + " pieces of Cherry Maple(" +wood+ ") unavailable for this purchase.");
			
			sales.add(sale1); // add the sale's linked list of receipts to the sales linkedlist of all previous sales.
		}
		else {
			sales.add(sale1);
		}
		
		//Summary of this Sale that was just added to list of previous sales.
		System.out.println(sale1.quantity + " pieces of wood("+wood+") sold:");
		int totalPaid=0;
		for(int i=0;i<sale1.getSales().size();i++) {
			System.out.println(sale1.getSales().get(i));
			totalPaid+=sale1.getSales().get(i).getCost()*sale1.getSales().get(i).getQuantity();
		}
		System.out.println("\t\t\tTotal Paid: $"+totalPaid*1.00);
	}
}
