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
	
	protected static final long MAX_POWERUP_TTL = (long) 3e9;
	
	private Power type;
	private double ttl = MAX_POWERUP_TTL;
	
	public Powerup(Point center) {
		this.setCenter(center);
		this.setBounds(new Ellipse2D.Double(center.x - 10, center.y - 10, 20, 20));
		
		Power[] powers = Power.values();
		this.type = powers[(int)(Math.random() * powers.length)];
	}
	
	public void update(long delta) {
		if (isExpired()) {
			ttl = 0;
		} else {
			ttl -= delta;
		}
	}
	
	public boolean isExpired() {
		return ttl <= 0;
	}
	
	public Power getType() {
		return this.type;
	}
}
