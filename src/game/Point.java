package game;

import java.awt.geom.Point2D;

/**
 * Point extends Point2D.Double to add extra functionality.
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
		if (this.x < 0) {
			this.x = (this.x % xMax) + xMax;
		}
		
		if (this.y < 0) {
			this.y = (this.y % yMax) + yMax;
		}
		
		if (this.x > xMax) {
			this.x = this.x % xMax;
		}
		
		if (this.y > yMax) {
			this.y = this.y % yMax;
		}
	}
	
	/**
	 * Rotates this point about the origin through the number of radians in angle
	 * @param origin the center of rotation
	 * @param angle the angle of rotation
	 */
	public void rotate(Point2D.Double origin, double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

		this.x -= origin.getX();
		this.y -= origin.getY();

		double x = this.x * cos - this.y * sin;
		double y = this.x * sin + this.y * cos;

		this.x = x + origin.getX();
		this.y = y + origin.getY();
	}
	
	/**
	 * Will wrap this around and bring every point with it
	 * @param xMax the max x value
	 * @param yMax the max y value
	 * @param points the points that will be carried over
	 */
	public void wrapAround(double xMax, double yMax, Point... points) {
		if (this.x < 0) {
			this.x += xMax;
			
			for (Point p : points) {
				p.x += xMax;
			}
		} else if (this.x > xMax) {
			this.x -= xMax;
			
			for (Point p : points) {
				p.x -= xMax;
			}
		}
		
		if (this.y < 0) {
			this.y += yMax;
			
			for (Point p : points) {
				p.y += yMax;
			}
		} else if (this.y > yMax) {
			this.y -= yMax;
			
			for (Point p : points) {
				p.y -= yMax;
			}
		}
	}
	
	public static Point getRandom(int xMax, int yMax, Point p, int radius) {
		int x;
		int y;
		int distance;
		
		do {
			x = (int)((xMax + 1) * Math.random());
			y = (int)((yMax + 1) * Math.random());
			
			// Calculate distance d = sqrt(dx^2 + dy^2)
			distance = (int)Math.sqrt((p.getX() - x)*(p.getX() - x) + (p.getY() - y)*(p.getY() - y));
		} while (distance < radius);
		
		return new Point(x, y);
	}
}
