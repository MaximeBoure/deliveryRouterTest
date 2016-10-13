package deliveryRouter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RoutesCombination {
	Map<Customer, Route> mCombinations = new HashMap<>();

	public RoutesCombination copy() {
		RoutesCombination combination = new RoutesCombination();
		for (Entry<Customer, Route> entry : mCombinations.entrySet()) {
			combination.addRoute(entry.getKey(), entry.getValue());
		}
		return combination;
	}

	/**
	 * Add a new route to the combination of routes.
	 * 
	 * @param customer
	 *            Customer
	 * @param route
	 *            Route to delivery a customer
	 */
	public void addRoute(Customer customer, Route route) {
		mCombinations.put(customer, route);
	}

	/**
	 * Validates a combination of routes. A combination of routes is valid only
	 * if no stops has been reached more than one time and if only one route is
	 * given to a rider.
	 * 
	 * @return true if the combination is valid, false otherwise.
	 */
	public boolean isValid() {
	
		// Validates that stops are only reach once
		List<Stop> stops = new ArrayList<Stop>();
		for (Route route : mCombinations.values()) {
			for (Stop routeStop : route.getStops()) {
				List<Stop> tmp = new ArrayList<Stop>(stops);
				for (Stop stop : tmp) {
					if (routeStop.getId() == stop.getId())
						return false;
				}
				
				stops.add(routeStop);
			}
		}

		// Validates that only one route is given to a specific rider
		List<Rider> riders = new ArrayList<>();
		for (Route route : mCombinations.values()) {
			for (Rider rider : riders) {
				if (route.getRider().getId() == rider.getId())
					return false;
			}

			riders.add(route.getRider());
		}
		return true;
	}

	/**
	 * Returns the overall delivery time of all routes
	 * 
	 * @return overall delivery time of all routes
	 */
	public int getOverallDeliveryTime() {
		int time = 0;
		for (Route route : mCombinations.values()) {
			time += route.getDeliveryTime();
		}
		return time;
	}

	public Route getRiderRoute(Rider rider) {
		for (Entry<Customer, Route> entry : mCombinations.entrySet()) {
			if (entry.getValue().getRider().equals(rider))
				return entry.getValue();
		}
		return null;
	}

	public Route getCustomerRoute(Customer customer) {
		return mCombinations.get(customer);
	}
}
