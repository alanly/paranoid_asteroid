package game;

import java.awt.geom.Point2D;

/**
 * Point extends Point2D.Double to add extra functionality
 *
 */
public class Point extends Point2D.Double {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new point.
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public Point(double x, double y) {
		super(x, y);
	}
	
	/**
	 * Constructs a new point.
	 * @param p the point to use as coordinates
	 */
	public Point(Point2D.Double p) {
		this(p.x, p.y);
	}

	/**
	 * Moves the x-coordinate by x and the y-coordinate by y.
	 * @param x the amount to move the x-coordinate by
	 * @param y the amount to move the y-coordinate by
	 */
	public void move(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	/**
	 * Moves the x-coordinate by x and the y-coordinate by y and performs
	 * a wrap around with xMax and yMax as the max x and y values.
	 * @param x the amount to move the x-coordinate by
	 * @param y the amount to move the y-coordinate by
	 * @param xMax the max x value
	 * @param yMax the max y value
	 */
	public void move(double x, double y, double xMax, double yMax) {
		this.move(x, y);
		this.wrapAround(xMax, yMax);
	}

	/**
	 * Performs a wrap around with xMax and yMax as the max x and y values.
	 * @param xMax the max x value
	 * @param yMax the max y value
	 */
	public void wrapAround(double xMax, double yMax) {
		if (this.x > xMax) {
			this.x = this.x % xMax;
		}
		
		if (this.y > yMax) {
			this.y = this.y % yMax;
		}
	}
}
