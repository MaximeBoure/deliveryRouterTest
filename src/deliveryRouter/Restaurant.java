package deliveryRouter;

public class Restaurant extends Stop {
	private int mCookingTime;

	public Restaurant(int id, int cookingTime, int x, int y) {
		super(id, x, y);
		this.mCookingTime = cookingTime;
	}

	public int getCookingTime() {
		return mCookingTime;
	}

	@Override
	Stop copy() {
		return new Restaurant(getId(), mCookingTime, getX(), getY());
	}
}
