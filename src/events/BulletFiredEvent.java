package events;

import game.Point;
import game.entities.Entity;

/**
 * This event records the source of a bullet, the time it was fired, and the origin and angle.
 *
 */
public class BulletFiredEvent {
	private Entity source;
	private long timeFired;
	Point origin;
	double angle;
	
	/**
	 * Constructs a new BulletFiredEvent.
	 * @param source the source of the bullet
	 * @param origin the origin of the bullet
	 * @param angle the angle of the bullet
	 */
	public BulletFiredEvent(Entity source, Point origin, double angle) {
		this.source = source;
		this.timeFired = System.nanoTime();
		this.origin = origin;
		this.angle = angle;
	}
	
	/**
	 * Gets the source of the bullet.
	 * @return the source of the bullet
	 */
	public Entity getSource() {
		return source;
	}
	
	/**
	 * Gets the time the bullet was fired.
	 * @return the time the bullet was fired
	 */
	public long getTimeFired() {
		return timeFired;
	}

	/**
	 * Gets the origin of the bullet.
	 * @return the origin of the bullet
	 */
	public Point getOrigin() {
		return origin;
	}

	/**
	 * Gets the angle of the bullet.
	 * @return the angle of the bullet
	 */
	public double getAngle() {
		return angle;
	}
}
