package deliveryRouter;

public class Rider {
	private int mId;
	private int mSpeed;
	private int mX;
	private int mY;

	public Rider(int id, int speed, int x, int y) {
		super();
		
		this.mId = id;
		this.mSpeed = speed;
		this.mX = x;
		this.mY = y;
	}

	public int getId() {
		return mId;
	}

	public int getSpeed() {
		return mSpeed;
	}

	public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}
}
