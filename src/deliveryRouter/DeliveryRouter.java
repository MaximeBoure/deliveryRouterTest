package deliveryRouter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryRouter {

	private Map<Integer, Customer> mCustomers;
	private Map<Integer, Restaurant> mRestaurants;
	private Map<Integer, Rider> mRiders;
	private List<Order> mOrders = new ArrayList<>();

	public DeliveryRouter(Map<Integer, Customer> customers, Map<Integer, Restaurant> restaurants,
			Map<Integer, Rider> riders) {
		super();
		this.mCustomers = customers;
		this.mRestaurants = restaurants;
		this.mRiders = riders;
	}

	/**
	 * Add a new order to be processed
	 * 
	 * @param customer
	 *            customer ordering
	 * @param restaurant
	 *            restaurant targeted
	 */
	public void addOrder(Customer customer, Restaurant restaurant) {
		mOrders.add(new Order(customer, restaurant));
	}

	/**
	 * Solves delivery routes and returns rider's route
	 * 
	 * @param rider
	 *            targeted
	 * @return rider's route
	 */
	public Route getRoute(Rider rider) {
		Solver solver = new Solver(new ArrayList<>(mRiders.values()), mOrders);
		RoutesCombination combination = solver.solve();

		return combination.getRiderRoute(rider);
	}

	/**
	 * Solves delivery routes and returns customer's route
	 * 
	 * @param customer
	 *            targeted
	 * @return customer's route
	 */
	public Route getRoute(Customer customer) {
		Solver solver = new Solver(new ArrayList<>(mRiders.values()), mOrders);
		RoutesCombination combination = solver.solve();

		return combination.getCustomerRoute(customer);
	}
}
