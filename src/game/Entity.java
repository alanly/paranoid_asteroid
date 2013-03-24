package game;

import game.Point;

public abstract class Entity {
	public final static double FULL_CIRCLE_DEG = 360.0;
	public final static double FULL_CIRCLE_RAD = 2 * Math.PI;
	
	private Point center;
	
	/**
	 * Updates the entity's state based on how much time has passed.
	 * @param delta time since last update
	 */
	public abstract void update(long delta);
	
	/**
	 * Gets the x-coordinate of the entity's center Point.
	 * @return the x-coordinate of the entity's center Point
	 */
	public double getX() {
		return center.x;
	}
	
	/**
	 * Gets the y-coordinate of the entity's center Point.
	 * @return the y-coordinate of the entity's center Point
	 */
	public double getY() {
		return center.y;
	}
	
	/**
	 * Gets the the entity's center Point.
	 * @return the entity's center Point
	 */
	public Point getCenter() {
		return center;
	}
	
	/**
	 * Sets the entity's center Point x-coordinate.
	 * @param x the entity's center Point x-coordinate
	 */
	public void setX(double x) {
		center.x = x;
	}
	
	/**
	 * Sets the entity's center Point y-coordinate.
	 * @param y the entity's center Point y-coordinate
	 */
	public void setY(double y) {
		center.y = y;
	}
	
	/**
	 * Sets the entity's center Point.
	 * @param center the entity's center Point
	 */
	public void setCenter(Point center) {
		this.center = center;
	}
}
