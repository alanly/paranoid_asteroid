package game.entities;

import game.Point;
import game.ui.GameCanvas;

import java.awt.Rectangle;

public class Bullet extends Entity {
	private boolean expired;
	private Entity source;
	private double angle;
	private double linearSpeed = 4.0e-7;
	private double maxTimeToLive = 0.8e9;
	private double timeToLive = maxTimeToLive;

	public Bullet(Entity source, Point center, double angle) {
		setCenter(center);
		
		this.expired = false;
		this.source = source;
		this.angle = angle;
		
		if (source instanceof Alien) {
			linearSpeed *= 0.7;
			timeToLive *= 1.7;
		}
		
		setBounds(new Rectangle((int)center.x, (int)center.y, 2, 2));
	}

	public void update(long delta) {
		if (expired) {
			return;
		}
		
		timeToLive -= delta;

		updateCenter(delta);
		updateBounds();

		if (timeToLive < 0) {
			this.expired = true;
		}
	}
	
	public Entity getSource() {
		return source;
	}
	
	public boolean isExpired() {
		return this.expired;
	}

	private void updateCenter(long delta) {
		double distance = linearSpeed * delta;
		double dx = distance * Math.cos(angle);
		double dy = -distance * Math.sin(angle);

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
