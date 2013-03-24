package game.entities;

import game.GameField;
import game.Point;

import java.awt.Color;

public class Bullet extends Entity {
	private static final double MAX_TIME_TO_LIVE = 2.0e9;
	private static final double MIN_LINEAR_SPEED = 1.0e-7;
	private static final double MAX_LINEAR_SPEED = 4.0e-7;
	private static final double DRAG_COEFFICIENT = 2.0e-3;

	private boolean expired;
	private Entity source;
	private double angle;
	private double linearSpeed = MAX_LINEAR_SPEED;
	private double timeToLive = MAX_TIME_TO_LIVE;

	private Color color = Color.RED;

	public Bullet(Entity source, Point center, double angle) {
		setCenter(center);
		
		this.expired = false;
		this.source = source;
		this.angle = angle;
	}

	public void update(long delta) {
		if (expired) {
			return;
		}
		
		timeToLive -= delta;

		updateSpeed(delta);
		updateColor();
		updateCenter(delta);

		if (timeToLive < 0) {
			this.expired = true;
		}
	}

	private void updateSpeed(long delta) {
		// Imitates the effect of drag without all the fancy real math, it's good enough
		linearSpeed = Math.max(MIN_LINEAR_SPEED, linearSpeed - linearSpeed
				* linearSpeed * delta * DRAG_COEFFICIENT);
	}

	private void updateColor() {
		int red = (int) (256.0 * timeToLive / MAX_TIME_TO_LIVE);

		red = Math.max(0, Math.min(red, 255));

		color = new Color(red, color.getGreen(), color.getGreen());
	}

	private void updateCenter(long delta) {
		double distance = linearSpeed * delta;

		// Move center
		getCenter().move(distance * Math.cos(angle),
				-distance * Math.sin(angle));

		// Wrap around
		getCenter().wrapAround(GameField.WIDTH, GameField.HEIGHT);
	}

	public Entity getSource() {
		return source;
	}
	
	public boolean isExpired() {
		return this.expired;
	}
}
