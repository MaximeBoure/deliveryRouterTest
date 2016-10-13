package tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import deliveryRouter.Customer;
import deliveryRouter.DeliveryRouter;
import deliveryRouter.Restaurant;
import deliveryRouter.Rider;
import deliveryRouter.Route;
import junit.framework.TestCase;


/**
 * This file is a simple Ruby RSpec test suite describing a DeliveryRouter class.
 * Your goals are to:
 * 1) make those tests pass,
 * 2) add tests for the edge cases.
 * If you need help to setup the project, see: https://gist.github.com/gaeldelalleau/f486f9e5fdcece8eb36e70f23c806184
 * Feel free to ask us any question!
 * 
 * Happy hacking :)
 * --
 * Goal: delight your customers by sending riders to pick and deliver their orders
 * in less than 60 minutes (faster if you can).
 * Hypothesis:
 *   - (x, y) coordinates are used to locate restaurants, customers and riders on a grid
 *   - the distance between x=0 and x=1 is 1000 meters (1 km)
 *   - the distance between two arbitrary (x,y) locations is the euclidian distance (straight line)
 *   - times are expressed in minutes
 *   - speeds are expressed in kilometers per hour (km/h)
 * Note: some other hypothesis you can make to simplify the problem:
 *   - all customer orders are received at the same time (t=0)
 *   - all restaurants start cooking at the same time (t=0)
 *   - all riders start moving at the same time (t=0)
 *   - each restaurant can cook an infinite number of meals in parallel
 *   - each rider can carry an infinite amount of meals at the same time 
 *   - riders are ninjas! They fly over trafic jams and buildings, they never need to take
 *     a break and they know how to solve a NP-complete problem in polynomial time ;)
 */
public class DeliveryRouterTest extends TestCase {
	Map<Integer, Customer> mCustomers;
	Map<Integer, Restaurant> mRestaurants;
	Map<Integer, Rider> mRiders;
	DeliveryRouter mDeliveryRouter;

	@Before
	public void setUp() {
		mCustomers = new HashMap<>();
		mCustomers.put(1, new Customer(1, 1, 1));
		mCustomers.put(2, new Customer(2, 5, 1));
		
		mRestaurants = new HashMap<>();
		mRestaurants.put(3, new Restaurant(3, 15, 0, 0));
		mRestaurants.put(4, new Restaurant(4, 35, 5, 5));

		mRiders = new HashMap<>();
		mRiders.put(1, new Rider(1, 10, 2, 0));
		mRiders.put(2, new Rider(2, 10, 1, 0));

		mDeliveryRouter = new DeliveryRouter(mCustomers, mRestaurants, mRiders);
	}

	@After
	public void tearDown() {
	}
	
	/**
	 * Input Customer 1 orders in restaurant 3
	 */
	private void givenCust1OrdersRest3() {
		mDeliveryRouter.addOrder(mCustomers.get(1), mRestaurants.get(3));
	}

	/**
	 * Input Customer 2 orders in restaurant 4
	 */
	private void givenCust2OrdersRest4() {
		mDeliveryRouter.addOrder(mCustomers.get(2), mRestaurants.get(4));
	}

	/**
	 * 1st test case
	 * */
	@Test
	public void testCase1DoesNotAssignRoute() {
		givenCust1OrdersRest3();
		
		Route route = mDeliveryRouter.getRoute(mRiders.get(1));
		
		assertNotNull(route);
	}

	@Test
	public void testCase1SendsRider() {
		givenCust1OrdersRest3();
		
		Route route = mDeliveryRouter.getRoute(mRiders.get(1));
		
		assertEquals(route.getStops().size(), 2);
		assertEquals(route.getStops().get(0).getId(), 3);
		assertEquals(route.getStops().get(1).getId(), 1);
	}

	@Test
	public void testCase1Delights() {
		givenCust1OrdersRest3();
		
		Route route = mDeliveryRouter.getRoute(mCustomers.get(1));
		
		assertTrue(route.getDeliveryTime() < 60);
	}

	/**
	 * 2nd test case
	 * */
	@Test
	public void testCase2SendsRider1() {
		givenCust1OrdersRest3();
		givenCust2OrdersRest4();
		
		Route route = mDeliveryRouter.getRoute(mRiders.get(1));
		
		assertEquals(route.getStops().size(), 2);
		assertEquals(route.getStops().get(0).getId(), 4);
		assertEquals(route.getStops().get(1).getId(), 2);
	}
	
	@Test
	public void testCase2SendsRider2() {
		givenCust1OrdersRest3();
		givenCust2OrdersRest4();
		
		Route route = mDeliveryRouter.getRoute(mRiders.get(2));
		
		assertEquals(route.getStops().size(), 2);
		assertEquals(route.getStops().get(0).getId(), 3);
		assertEquals(route.getStops().get(1).getId(), 1);
	}
	
	@Test
	public void testCase2DelightsCust1() {
		givenCust1OrdersRest3();
		givenCust2OrdersRest4();
		
		Route route = mDeliveryRouter.getRoute(mCustomers.get(1));
		
		assertTrue(route.getDeliveryTime() < 60);
	}

	@Test
	public void testCase2DelightsCust2() {
		givenCust1OrdersRest3();
		givenCust2OrdersRest4();
		
		Route route = mDeliveryRouter.getRoute(mCustomers.get(2));
		
		assertTrue(route.getDeliveryTime() < 60);
	}
}
