package homework1;

public class Order {

	private static String status = "Order";
	private int id; 
	private int trans_num;
	private String item;
	private int quantity;
	private double item_cost;
	private int discount=0;
	private double total;
	
			
			
	//charge with discount removed
	public Order(int id, int trans_num, String item, 
				int quantity, double item_cost, int discount) {
				
				this.id = id;
				this.trans_num = trans_num;
				this.item = item; 
				this.quantity=quantity; 
				this.item_cost = item_cost;
				this.discount = discount;
				this.total = quantity*item_cost*((100-discount)/100.0); //correct

	}
			
	//charge without discount
	public Order (int id, int trans_num, String item, 
			int quantity, double item_cost) {
				
				this.id = id;
				this.trans_num = trans_num;
				this.item = item; 
				this.quantity=quantity; 
				this.item_cost = item_cost;
				this.total = quantity*item_cost;
	}
			
		
	public String getStatus() {
				return status;
	}

	
	public int getId() {
				return id;
			}
	
	public double getTotal() {
		return total;
	}

	
	public int getTrans_num() {
				return trans_num;
			}

	
	public String getItem() {
				return item;
			}

	
	public int getQuantity() {
				return quantity;
			}

	
	public double getCost() {
				return item_cost;
			}

	
	public int getDiscount() {
				return discount;
			}

	
	public String toString() { 

				return String.format("%d %-10s %-15.2f",trans_num, item, total);
			}	

	
	public boolean equals(Object other) {  
		
		if(other instanceof Order) {
			return ((Order)other).getId() == id 
					&& ((Order)other).getTrans_num() == trans_num
					&& ((Order)other).getItem() == item;
		}
		
		return false;
	}
			
			

}


