package game.entities;

import game.Point;
import game.ui.GameField;

import java.awt.Polygon;

public class Asteroid extends Entity {
	private static double LINEAR_SPEED_VARIANCE = 0.3;
	private static double MIN_LINEAR_SPEED = 3e-8;

	private Point[] vertices;
	private double angle;
	private double speed = MIN_LINEAR_SPEED;

	public Asteroid(Point center) {
		this.setCenter(center);
		
		// Give random angle
		angle = Math.random() * FULL_CIRCLE_RAD;
	
		// Vary speed between 1 and (1 + LINEAR_SPEED_VARIANCE) times
		speed *= 1 + Math.random() * LINEAR_SPEED_VARIANCE;
		vertices = new Point[4];
		
		initializeVertices();
	}

	public void update(long delta) {
		updateVertices(delta);
		updateBounds();
	}
	
	public Point[] getVertices() {
		return vertices;
	}

	private void initializeVertices() {
		this.vertices[0] = new Point(this.getX() - 10, this.getY() - 10);
		this.vertices[1] = new Point(this.getX() + 10, this.getY() - 10);
		this.vertices[2] = new Point(this.getX() + 10, this.getY() + 10);
		this.vertices[3] = new Point(this.getX() - 10, this.getY() + 10);
	}
	
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
		getCenter().wrapAround(GameField.WIDTH, GameField.HEIGHT, vertices);
		
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
