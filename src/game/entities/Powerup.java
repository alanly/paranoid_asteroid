package game.entities;

import game.Point;

import java.awt.geom.Ellipse2D;

public class Powerup extends Entity {
	public enum Power {
		BOOST,
		PULSE,
		SHIELD,
		TRIPLE_SHOT;
	}
	
	public static final long MAX_TIME_TO_LIVE = (long) 6e9;
	
	private Power type;
	private double ttl = MAX_TIME_TO_LIVE;
	
	/**
	 * Creates a new Powerup object centered at <tt>center</tt>
	 * @param center <tt>Point</tt> at which the Powerup is initially centered
	 * @param type <tt>Power</tt> that the powerup gives
	 */
	public Powerup(Point center, Power type) {
		this.setCenter(center);
		this.setBounds(new Ellipse2D.Double(center.x - 10, center.y - 10, 20, 20));
		this.type = type;
	}
	
	public Powerup(Point center) {
		this(center, Power.values()[(int)(Math.random() * Power.values().length)]);
	}
	
	/**
	 * Updates the state of the Powerup
	 * @param delta time elapsed since last update
	 */
	public void update(long delta) {
		if (isExpired()) {
			ttl = -1;
		} else {
			ttl -= delta;
		}
	}
	
	/**
	 * Indicates whether or not the Powerup is expired
	 * @return <tt>true</tt> if the Powerup is expired
	 */
	public boolean isExpired() {
		return ttl < 0;
	}
	
	/**
	 * Gets the type of the Powerup 
	 * @return <tt>Power</tt> type of the Powerup
	 */
	public Power getType() {
		return this.type;
	}
}
