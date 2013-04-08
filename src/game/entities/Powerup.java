package game.entities;

import game.Point;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Powerup extends Entity {
	protected static final long MAX_POWERUP_TTL = (long) 3e9;
	
	private double ttl = MAX_POWERUP_TTL;
	
	public Powerup(Point center) {
		this.setCenter(center);
		
		this.setBounds(new Ellipse2D.Double(center.x, center.y, 20, 20));
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
	
	public static Powerup getRandomPowerup(Point p) throws Exception {
		List<Class<? extends Powerup>> powerups = new ArrayList<Class<? extends Powerup>>();
		
		powerups.add(BoostPowerup.class);
		
		// Metaprogramming voodoo to choose a random class from the collection, get its constructor, and construct a new instance
		return powerups.get((int) (Math.random() * powerups.size())).getConstructor(Point.class).newInstance(p);
	}
}
