package deliveryRouter;


public class Customer extends Stop {
	
	public Customer(int id, int x, int y) {
		super(id, x, y);
	}

	@Override
	Stop copy() {
		return new Customer(getId(), getX(), getY());
	}
}
