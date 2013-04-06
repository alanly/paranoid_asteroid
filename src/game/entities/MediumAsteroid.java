package game.entities;

import game.Point;

public class MediumAsteroid extends Asteroid {
	public MediumAsteroid(Point center) {
		super(center);
	}
	protected void initializeVertices() {
		double x = this.getX(), y = this.getY();
		this.vertices[0] = new Point(x - 6, y - 18);
		this.vertices[1] = new Point(x + 10, y - 9);
		this.vertices[2] = new Point(x + 23, y - 3);
		this.vertices[3] = new Point(x + 15, y + 15);
		this.vertices[4] = new Point(x - 3, y + 17);
		this.vertices[5] = new Point(x - 12, y + 6);
		this.vertices[6] = new Point(x - 23, y);
		this.vertices[7] = new Point(x - 20, y - 12);
		this.vertices[8] = new Point(x - 9, y - 15);
	}
}