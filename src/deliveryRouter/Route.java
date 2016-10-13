package deliveryRouter;

import java.util.ArrayList;
import java.util.List;

public class Route {

	private Rider mRider;
	private List<Stop> mStops = new ArrayList<>();
	private double mDeliveryTime = 0;

	// Used while constructing the route
	private int mCurrentX;
	private int mCurrentY;

	public Route(Rider rider) {
		super();
		this.mRider = rider;
		initializeRoute(rider);
	}

	public Route copy() {
		Route route = new Route(mRider);
		for (Stop stop : mStops) {
			route.addStop(stop);
		}
		return route;
	}

	public List<Stop> getStops() {
		return mStops;
	}

	public Rider getRider() {
		return mRider;
	}

	public Double getDeliveryTime() {
		return mDeliveryTime;
	}

	/**
	 * Mandatory function to initialize the root coordinate of the route defined
	 * by the rider's first position
	 * 
	 * @param rider
	 *            the rider's route
	 */
	public void initializeRoute(Rider rider) {
		mCurrentX = rider.getX();
		mCurrentY = rider.getY();
	}

	/**
	 * Process adding a stop to a route. Increments route stops, increment
	 * overall delivery time and set new current coordinate for next stop
	 * calculation.
	 * 
	 * @param stop
	 *            the stop added
	 */
	public void addStop(Stop stop) {
		double distance = Utils.distance(mCurrentX, mCurrentY, stop.getX(), stop.getY());
		double time = Utils.timeInMin(distance, mRider.getSpeed());

		// If cooking time is more than the time to get to the restaurant the
		// rider will have to wait the cooking time to be over.
		if (stop instanceof Restaurant && (((Restaurant) stop).getCookingTime() > (mDeliveryTime + time))) {
			mDeliveryTime = ((Restaurant) stop).getCookingTime();
		} else {
			mDeliveryTime += time;
		}

		mCurrentX = stop.getX();
		mCurrentY = stop.getY();

		mStops.add(stop);
	}
}
