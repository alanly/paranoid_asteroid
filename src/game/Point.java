package game;

import java.awt.geom.Point2D;

public class Point extends Point2D.Double {
	private static final long serialVersionUID = 1L;
	
	public Point(double x, double y) {
		super(x, y);
	}
	
	public Point(Point2D.Double p) {
		this(p.x, p.y);
	}

	public void move(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	public void move(double x, double y, double xMax, double yMax) {
		this.move(x, y);
		this.wrapAround(xMax, yMax);
	}

	public void wrapAround(double xMax, double yMax) {
		if (this.x > xMax) {
			this.x = this.x % xMax;
		}
		
		if (this.y > yMax) {
			this.y = this.y % yMax;
		}
	}
}
