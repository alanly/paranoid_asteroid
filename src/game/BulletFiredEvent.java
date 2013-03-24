package game;

import game.entities.Entity;

/**
 * This event records the source of a bullet and the time it was fired.
 *
 */
public class BulletFiredEvent {
	private Entity source;
	private long timeFired;
	
	/**
	 * Constructs a new BulletFiredEvent.
	 * @param source the source of the bullet
	 * @param time the time the bullet was fired
	 */
	public BulletFiredEvent(Entity source, long time) {
		this.source = source;
		this.timeFired = time;
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
	 * @return the time the bullet was fired.
	 */
	public long getTimeFired() {
		return timeFired;
	}
}
