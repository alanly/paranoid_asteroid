package game.entities;

import java.awt.Rectangle;

import game.Point;
import game.ui.GameCanvas;

public class Particle extends Entity {
	public static final long MAX_TIME_TO_LIVE = (long) 1.3e9;
	private double linearSpeed = 3.0e-7;
	private double angle;
	private long ttl = MAX_TIME_TO_LIVE;
	private double decelleration = 2e-16;
	
	/**
	 * Creates a Particle centered at <tt>center</tt> with a random <tt>angle</tt>
	 * @param center Initial center of the Particle
	 */
	public Particle(Point center) {
		setCenter((Point) center.clone());
		
		angle = Math.random() * FULL_CIRCLE_RAD;
		setBounds(new Rectangle((int)center.x, (int)center.y, 1, 1));
	}
	
	/**
	 * Indicates whether or not the Particle is expired. 
	 * @return <tt>true</tt> if the Particle is expired.
	 */
	public boolean isExpired() {
		System.out.println(ttl);
		return ttl < 0;
	}
	
	/**
	 * Updates the state of the Particle
	 * @param delta time elapsed since last update
	 */
	public void update(long delta) {
		updateSpeed(delta);
		updateCenter(delta);
		updateBounds();
		
		ttl -= delta;
	}
	
	/**
	 * Updates the speed of the Particle
	 * @param delta time elapsed since last update
	 */
	public void updateSpeed(long delta) {
		linearSpeed = Math.max(0, linearSpeed - (decelleration * delta));
	}
	
	/**
	 * Updates the center of the Particle
	 * @param delta time elapsed since last update
	 */
	public void updateCenter(long delta) {
		double distance = linearSpeed * delta;
		double dx = distance * Math.cos(angle);
		double dy = -distance * Math.sin(angle);
		
		// Move center
		getCenter().move(dx, dy);

		// Wrap around
		getCenter().wrapAround(GameCanvas.WIDTH, GameCanvas.HEIGHT);
	}
	
	/**
	 * Updates the bounds of the Particle
	 */
	private void updateBounds() {
		Rectangle bounds = (Rectangle) getBounds();
		bounds.x = (int) getCenter().x;
		
		bounds.y = (int) getCenter().y;
	}
}
