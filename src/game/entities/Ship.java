package game.entities;

import events.BulletFiredEvent;
import events.BulletFiredListener;
import game.InputHandler;
import game.Point;
import game.ui.GameCanvas;

import java.awt.Polygon;
import java.util.LinkedList;
import java.util.List;

public class Ship extends Entity {
	private static final double BULLET_FIRE_DELAY = 5e8;
	private static final double MAX_LINEAR_SPEED = 3.4e-7;
	private static final double MIN_ANGULAR_SPEED = 5e-9;
	private static final double ACCELERATION = 1.0e-15;

	private Point[] vertices;
	private List<BulletFiredListener> bulletFiredListeners;

	private double linearSpeed = 0;
	private double angle = Math.PI / 2;
	private long lastFired = 0;
	private boolean alive = true;

	/**
	 * Constructs a new ship.
	 * @param center the center of the ship
	 */
	public Ship(Point center) {
		this.setCenter(center);
		bulletFiredListeners = new LinkedList<BulletFiredListener>();
		initializeVertices();
	}

	/**
	 * Updates the state of the ship and fires bullets.
	 * @param delta the time since the last update
	 */
	public void update(long delta) {
		updateSpeed(delta);
		updateAngle(delta);
		updateVertices(delta);
		updateBounds();
		
		long now = System.nanoTime();
		
		if (InputHandler.getInstance().getSpaceKey().isPressed() && (now - lastFired) > BULLET_FIRE_DELAY) {
			lastFired = now;
			
			for (BulletFiredListener listener : bulletFiredListeners) {
				// Origin is the tip of the ship, the first vertex
				listener.bulletFired(new BulletFiredEvent(this, (Point) vertices[0].clone(), angle));
			}
		}
	}

	/**
	 * Updates the speed of the ship.
	 * @param delta the time since the last update
	 */
	public void updateSpeed(long delta) {
		if (InputHandler.getInstance().getUpKey().isPressed()) {
			// Accelerate
			linearSpeed += ACCELERATION * delta;
			linearSpeed = Math.min(MAX_LINEAR_SPEED, linearSpeed);
		} else {
			// Decelerate
			linearSpeed -= ACCELERATION * delta;
			linearSpeed = Math.max(0, linearSpeed);
		}
	}
	
	/**
	 * Adds a bullet fired listener to the list of listeners to notify when a bullet is fired.
	 * @param l bullet fired listener to notify when the bullet is fired
	 */
	public void addBulletFiredListener(BulletFiredListener l) {
		this.bulletFiredListeners.add(l);
	}
	
	/**
	 * Returns true if the ship is alive, false otherwise.
	 * @return true if the ship is alive, false otherwise
	 */
	public boolean isAlive() {
		return this.alive;
	}
	
	/**
	 * Kills the ship.
	 */
	public void die() {
		this.alive = false;
	}

	/**
	 * Updates the angle of the ship.
	 * @param delta the time since the last update
	 */
	private void updateAngle(long delta) {
		double deltaAngle = 0;
		double angularSpeed = MIN_ANGULAR_SPEED
				* (1 - linearSpeed / (2 * MAX_LINEAR_SPEED));

		if (InputHandler.getInstance().getLeftKey().isPressed()) {
			// Turn CCW
			deltaAngle = -angularSpeed * delta;
		} else if (InputHandler.getInstance().getRightKey().isPressed()) {
			// Turn CW
			deltaAngle = angularSpeed * delta;
		}

		// Rotate each point by the change in the angle
		for (Point vertex : vertices) {
			vertex.rotate(getCenter(), deltaAngle);
		}

		// Update the angle
		angle = (angle - deltaAngle) % FULL_CIRCLE_RAD;
	}

	/**
	 * Updates the position of each vertex of the ship.
	 * @param delta the time since the last update
	 */
	private void updateVertices(long delta) {
		double distance = linearSpeed * delta;
		double dx = distance * Math.cos(angle);
		double dy = distance * Math.sin(angle);

		// Move center
		getCenter().move(dx, -dy);

		// Move vertices
		for (Point vertex : vertices) {
			vertex.move(dx, -dy);
		}
		
		getCenter().wrapAround(GameCanvas.WIDTH, GameCanvas.HEIGHT, vertices);
	}

	/**
	 * Initializes the vertices of the ship.
	 */
	private void initializeVertices() {
		this.vertices = new Point[3];
		this.vertices[0] = new Point(getX(), getY() - 10);
		this.vertices[1] = new Point(getX() - 5, getY() + 5);
		this.vertices[2] = new Point(getX() + 5, getY() + 5);
		
		updateBounds();
	}
	
	/**
	 * Updates the bounding shape of the ship
	 */
	private void updateBounds() {
		int[] x = new int[this.vertices.length];
		int[] y = new int[this.vertices.length];
		
		for (int i = 0; i < this.vertices.length; i++) {
			x[i] = (int) this.vertices[i].getX();
			y[i] = (int) this.vertices[i].getY();
		}
		
		setBounds(new Polygon(x, y, x.length));
	}
}
