package lepers;

public class Register {

	private Customer cust;
	
	public Register() {

	}

	public boolean isEmpty() {
		if(cust == null)
			return true;
		else
			return false;
	}

	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

}
