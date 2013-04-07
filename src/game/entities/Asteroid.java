package game.entities;

import game.Point;
import game.ui.GameCanvas;

import java.awt.Polygon;

public abstract class Asteroid extends Entity {
	public enum AsteroidSize {
		SMALL,
		MEDIUM,
		LARGE;
		
		public AsteroidSize getSmaller() {
			return (this == SMALL || this == MEDIUM) ? SMALL : MEDIUM;
		}
	}
	
	private static double LINEAR_SPEED_VARIANCE = 0.3;
	private static double MIN_LINEAR_SPEED = 3e-8;

	protected Point[] vertices;
	protected AsteroidSize size = AsteroidSize.SMALL;
	
	private double angle;
	private double speed = MIN_LINEAR_SPEED;

	public static Asteroid buildAsteroid(AsteroidSize size, Point center) {
		if (size == AsteroidSize.SMALL) {
			return new SmallAsteroid(center);
		} else if (size == AsteroidSize.MEDIUM) {
			return new MediumAsteroid(center);
		} else {
			return new LargeAsteroid(center);
		}
	}
	
	public Asteroid(Point center) {
		this.setCenter(center);
		
		// Give random angle
		angle = Math.random() * FULL_CIRCLE_RAD;
	
		// Vary speed between 1 and (1 + LINEAR_SPEED_VARIANCE) times
		speed *= 1 + Math.random() * LINEAR_SPEED_VARIANCE;
		vertices = new Point[9];
		
		initializeVertices();
		
		double rotation = FULL_CIRCLE_RAD * Math.random();
		
		for (Point vertex : vertices) {
			vertex.rotate(getCenter(), rotation);
		}
		
		updateBounds();
	}

	public void update(long delta) {
		updateVertices(delta);
		updateBounds();
	}
	
	public AsteroidSize getSize() {
		return this.size;
	}
	
	protected abstract void initializeVertices();
	
	private void updateVertices(long delta) {
		double distance = speed * delta;
		double dx = distance * Math.cos(angle);
		double dy = distance * Math.sin(angle);

		// Move center
		getCenter().move(dx, -dy);

		// Move vertices
		for (Point vertex : vertices) {
			vertex.move(dx, -dy);
		}

		// Wrap around
		getCenter().wrapAround(GameCanvas.WIDTH, GameCanvas.HEIGHT, vertices);
		
	}
	
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
