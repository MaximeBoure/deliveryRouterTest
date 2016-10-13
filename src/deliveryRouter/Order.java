package deliveryRouter;

public class Order {
	private Customer mCustomer;
	private Restaurant mRestaurant;

	public Order(Customer customer, Restaurant restaurant) {
		super();
		this.mCustomer = customer;
		this.mRestaurant = restaurant;
	}

	public Customer getCustomer() {
		return mCustomer;
	}

	public Restaurant getRestaurant() {
		return mRestaurant;
	}
}
