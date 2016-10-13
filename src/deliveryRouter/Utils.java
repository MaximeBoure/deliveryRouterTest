package deliveryRouter;

public class Utils {
	private static int GRID_UNIT_METERS = 1000;
	
	/**
	 * Distance calculation between two points on a two dimensions grid
	 * 
	 * @param x1 x coordinate of first point
	 * @param y1 y coordinate of first point
	 * @param x2 x coordinate of second point
	 * @param y2 y coordinate of second point
	 * @return distance in the same unit as coordinate inputs
	 */
	static public double distance(int x1, int y1, int x2, int y2) {
		double x = Math.pow(x1 - x2, 2);
		double y = Math.pow(y1 - y2, 2);

		return Math.sqrt(x * y);
	}
	
	/**
	 * Calculate time to cross a specific distance at a specific speed
	 * 
	 * @param distance distance in grid unit
	 * @param speed speed in km/h
	 * @return time in minutes
	 */
	static public double timeInMin(double distance, double speed) {
		return ((distance * GRID_UNIT_METERS) / speed) / 60;
	}
}