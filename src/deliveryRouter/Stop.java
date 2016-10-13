package deliveryRouter;

/**
 * This class represents a stop in a rider's route
 */
public abstract class Stop {
	private int mId;
	private int mX;
	private int mY;
	
	public Stop(int id, int x, int y) {
		super();
		this.mId = id;
		this.mX = x;
		this.mY = y;
	}
	
	abstract Stop copy();
	
	public int getId() {
		return mId;
	}

	public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}
}
