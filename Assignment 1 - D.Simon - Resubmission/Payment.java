package homework1;

public class Payment {

			private static String status = "Payment";
			private int id; 
			private int trans_num;
			private double paid;
			private int discount;
			private double credit;
			
			//pay with discount
			public Payment(int id, int trans_num, int discount, double paid) {
				
				this.id = id;
				this.trans_num = trans_num;
				this.discount = discount;
				this.paid = paid; 
				this.credit=paid*(discount)/100; 
			}
			
			//pay without discount
			public Payment(int id, int trans_num, double paid) {
				
				this.id = id;
				this.trans_num = trans_num;
				this.paid =paid;
			}


			public String getStatus() {
				return status;
			}
			
			
			public double getCredit() {
				return credit;
			}

			
			public int getId() {
				return id;
			}
	
			public double getPaid() {
				return paid;
			}
			
			public int getTrans_num() {
				return trans_num;
			}

	
			public int getDiscount() {
				return discount;
			}


			public String toString() { //EDIT

				String asterisk ="";
				
				if (credit>0) {
					asterisk = "*";
				}
				
				return String.format("%d %-10s%18.2f%s",trans_num, status, paid, asterisk);
				
			}	
				
		}
