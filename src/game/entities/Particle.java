package game.entities;

import java.awt.Rectangle;

import game.Point;
import game.ui.GameCanvas;

public class Particle extends Entity {
	private double maxTimeToLive = 1.3e9;
	private double linearSpeed = 3.0e-7;
	private double angle;
	private double ttl = maxTimeToLive;
	private double decelleration = 2e-16;
	
	public Particle(Point center) {
		setCenter((Point) center.clone());
		
		angle = Math.random() * FULL_CIRCLE_RAD;
		setBounds(new Rectangle((int)center.x, (int)center.y, 1, 1));
	}
	
	public boolean isExpired() {
		return ttl <= 0;
	}
	
	public void update(long delta) {
		updateSpeed(delta);
		updateCenter(delta);
		updateBounds();
	}
	
	public void updateSpeed(long delta) {
		linearSpeed = Math.max(0, linearSpeed - (decelleration * delta));
	}
	
	public void updateCenter(long delta) {
		double distance = linearSpeed * delta;
		double dx = distance * Math.cos(angle);
		double dy = -distance * Math.sin(angle);
		
		ttl -= delta;

		// Move center
		getCenter().move(dx, dy);

		// Wrap around
		getCenter().wrapAround(GameCanvas.WIDTH, GameCanvas.HEIGHT);
	}
	
	private void updateBounds() {
		Rectangle bounds = (Rectangle) getBounds();
		bounds.x = (int) getCenter().x;
		bounds.y = (int) getCenter().y;
	}
}
