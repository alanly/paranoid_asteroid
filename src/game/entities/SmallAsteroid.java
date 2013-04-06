package game.entities;

import game.Point;

public class SmallAsteroid extends Asteroid {

	public SmallAsteroid(Point center) {
		super(center);
	}

	protected void initializeVertices() {
		double x = this.getX(), y = this.getY();
		this.vertices[0] = new Point(x - 4, y - 12);
		this.vertices[1] = new Point(x + 7, y - 6);
		this.vertices[2] = new Point(x + 15, y - 2);
		this.vertices[3] = new Point(x + 10, y + 10);
		this.vertices[4] = new Point(x - 2, y + 11);
		this.vertices[5] = new Point(x - 8, y + 4);
		this.vertices[6] = new Point(x - 15, y);
		this.vertices[7] = new Point(x - 13, y - 8);
		this.vertices[8] = new Point(x - 6, y - 10);
	}
}