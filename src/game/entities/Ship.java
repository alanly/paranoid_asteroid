package game.entities;

import game.GameField;
import game.InputHandler;
import game.Point;

public class Ship extends Entity {
	private final static double MAX_LINEAR_SPEED = 3.4e-7;
	private final static double MIN_ANGULAR_SPEED = 5e-9;
	private final static double ACCELERATION = 1.0e-15;

	private Point[] vertices;

	private double linearSpeed = 0;
	private double angle = Math.PI / 2;

	/**
	 * Constructs a new ship
	 * @param center the center of the ship
	 */
	public Ship(Point center) {
		this.setCenter(center);
		initializeVertices();
	}

	/**
	 * Updates the state of the ship
	 * @param delta the time since the last update
	 */
	public void update(long delta) {
		updateSpeed(delta);
		updateAngle(delta);
		updateVertices(delta);
	}

	/**
	 * Updates the speed of the ship
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
	 * Updates the angle of the ship
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
		for (Point vertice : vertices) {
			vertice.rotate(getCenter(), deltaAngle);
		}

		// Update the angle
		angle = (angle - deltaAngle) % FULL_CIRCLE_RAD;
	}

	/**
	 * Updates the position of each vertex of the ship
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
		
		getCenter().wrapAround(GameField.WIDTH, GameField.HEIGHT, vertices);
	}
	
	/**
	 * Returns the ship's vertices
	 * @return the ship's vertices
	 */
	public Point[] getVertices() {
		return vertices;
	}

	/**
	 * Initializes the vertices of the ship
	 */
	private void initializeVertices() {
		this.vertices = new Point[] {
				new Point(getX(), getY() - 10),
				new Point(getX() - 5, getY() + 5),
				new Point(getX() + 5, getY() + 5) };
	}
}
