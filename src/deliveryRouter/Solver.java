package deliveryRouter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {

	/**
	 * Input of the Solver
	 */
	private List<Rider> mRiders;
	private List<Order> mOrders;

	/**
	 * Variables used for solving processing
	 */
	private Map<Customer, List<Route>> mCustomerRoutes = new HashMap<>();
	private List<RoutesCombination> mCombinations = new ArrayList<>();
	private List<RoutesCombination> mValidCombinations = new ArrayList<>();

	public Solver(List<Rider> riders, List<Order> orders) {
		super();
		this.mRiders = riders;
		this.mOrders = orders;
	}

	/**
	 * This solver works in 3 steps : 1/ Generating all routes that will lead a
	 * rider to a customer 2/ Cleaning all incompatible routes (One rider can
	 * only have one route) 3/ Finding the best routes that will fulfill all
	 * customers
	 * 
	 * @return the best combination of routes to delight customers
	 */
	public RoutesCombination solve() {

		generateRoutes();

		generateRouteCombinations();

		clearInvalidCombinations();

		return getOverallFastestCombination();
	}

	/**
	 * The first step generates all logical possible stops for the riders and as
	 * an output delivers all routes leading to each customer.
	 * 
	 * All stops will take the form of a node which will grow to become a tree.
	 * 
	 * The base of the tree is made of Restaurants stops which are the first
	 * logical stops. When a restaurant stop is reached a new stop is added to
	 * the pending stops, the linked customer stop.
	 * 
	 * When a customer stop is reach we store the route that lead to him as a
	 * possible route.
	 * 
	 * When all stops are reach, all customers have been reached and so all
	 * restaurants.
	 */
	private void generateRoutes() {
		for (Rider rider : mRiders) {
			Route route = new Route(rider);

			// Initialize first stops with the list of restaurants
			List<Stop> stops = new ArrayList<>();
			for (Order order : mOrders) {
				stops.add(order.getRestaurant());
			}

			// Start processing
			processStops(route, stops);
		}
	}

	private void processStops(Route route, List<Stop> stops) {

		// We handle all possible possible stop on the current node
		for (Stop stop : stops) {
			Route newRoute = route.copy();

			// Add this stop to current route
			newRoute.addStop(stop);

			// Store, if needed, a new route for a customer
			if (stop instanceof Customer) {
				addRouteToCustomer((Customer) stop, newRoute.copy());
			}

			List<Stop> pendingStops = new ArrayList<>(stops);

			// If we reached a restaurant we have a new Stop
			if (stop instanceof Restaurant) {
				pendingStops.add(getCustomerFromOrder((Restaurant) stop));
			}

			// Move forward in the tree removing current node from pending
			pendingStops.remove(stop);

			processStops(newRoute, pendingStops);
		}
	}

	private Customer getCustomerFromOrder(Restaurant restaurant) {
		for (Order o : mOrders) {
			if (o.getRestaurant().getId() == restaurant.getId())
				return o.getCustomer();
		}
		return null;
	}

	private void addRouteToCustomer(Customer customer, Route route) {

		List<Route> currentCustomerRoutes = mCustomerRoutes.get(customer);

		if (currentCustomerRoutes == null)
			currentCustomerRoutes = new ArrayList<>();

		currentCustomerRoutes.add(route);
		mCustomerRoutes.put(customer, currentCustomerRoutes);
	}

	/**
	 * Second step, generates all route combinations
	 * 
	 * Process all routes for each customer and generate all possible
	 * combinations of routes to deliver each customer.
	 */
	private void generateRouteCombinations() {
		for (Customer customer : mCustomerRoutes.keySet()) {
			generateCustomerRouteCombinations(customer);
		}
	}

	private void generateCustomerRouteCombinations(Customer customer) {

		if (mCombinations.isEmpty()) {
			for (Route route : mCustomerRoutes.get(customer)) {

				RoutesCombination combination = new RoutesCombination();
				combination.addRoute(customer, route);
				mCombinations.add(combination);
			}
		} else {
			List<RoutesCombination> newCombinations = new ArrayList<>();

			for (RoutesCombination combination : mCombinations) {
				for (Route route : mCustomerRoutes.get(customer)) {

					RoutesCombination newCombination = combination.copy();
					newCombination.addRoute(customer, route);
					newCombinations.add(newCombination);
				}
			}

			mCombinations = newCombinations;
		}
	}

	/**
	 * Third step, cleans invalid combinations
	 */
	private void clearInvalidCombinations() {
		for (RoutesCombination combination : mCombinations) {
			if (combination.isValid()) {
				mValidCombinations.add(combination);
			}
		}
	}

	/**
	 * Find the combination that deliver all customer in the smallest overall
	 * delivery time.
	 * 
	 * @return the best combinations of delivery routes
	 */
	private RoutesCombination getOverallFastestCombination() {
		RoutesCombination fastestCombination = null;

		for (RoutesCombination combination : mValidCombinations) {
			if (fastestCombination == null
					|| fastestCombination.getOverallDeliveryTime() > combination.getOverallDeliveryTime())
				fastestCombination = combination;
		}

		return fastestCombination;
	}
}
