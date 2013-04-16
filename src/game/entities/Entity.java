package game.entities;

import java.awt.Shape;

import game.Point;

/**
 * An entity is any object that has a location on the game field and can be updated.
 *
 */
public abstract class Entity {
	public final static double FULL_CIRCLE_DEG = 360.0;
	public final static double FULL_CIRCLE_RAD = 2 * Math.PI;
	
	private Point center;
	private Shape bounds;
	
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
		return center.getX();
	}
	
	/**
	 * Gets the y-coordinate of the entity's center Point.
	 * @return the y-coordinate of the entity's center Point
	 */
	public double getY() {
		return center.getY();
	}
	
	/**
	 * Gets the the entity's center Point.
	 * @return the entity's center Point
	 */
	public Point getCenter() {
		return center;
	}
	
	/**
	 * Gets the entity's bounds.
	 * @return the entity's bounds
	 */
	public Shape getBounds() {
		return this.bounds;
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
	
	/**
	 * Sets the entity's bounds.
	 * @param bounds the entity's bounds
	 */
	public void setBounds(Shape bounds) {
		this.bounds = bounds;
	}
}
