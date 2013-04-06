package game.entities;

import game.Point;

public class LargeAsteroid extends Asteroid {

	public LargeAsteroid(Point center) {
		super(center);
		this.size = AsteroidSize.LARGE;
	}

	protected void initializeVertices() {
		double x = this.getX(), y = this.getY();
		this.vertices[0] = new Point(x - 8, y - 24);
		this.vertices[1] = new Point(x + 14, y - 12);
		this.vertices[2] = new Point(x + 30, y - 4);
		this.vertices[3] = new Point(x + 20, y + 20);
		this.vertices[4] = new Point(x - 4, y + 22);
		this.vertices[5] = new Point(x - 16, y + 8);
		this.vertices[6] = new Point(x - 30, y);
		this.vertices[7] = new Point(x - 26, y - 16);
		this.vertices[8] = new Point(x - 12, y - 20);
	}
}