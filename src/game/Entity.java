package game;

import game.Point;

public abstract class Entity {
	public final static double FULL_CIRCLE_DEG = 360.0;
	public final static double FULL_CIRCLE_RAD = 2 * Math.PI;
	
	private Point center;
	
	public abstract void update(long delta);
	
	public double getX() {
		return center.x;
	}
	
	public double getY() {
		return center.y;
	}
	
	public Point getCenter() {
		return center;
	}
	
	public void setX(double x) {
		center.x = x;
	}
	
	public void setY(double y) {
		center.y = y;
	}
	
	public void setCenter(Point center) {
		this.center = center;
	}
}
