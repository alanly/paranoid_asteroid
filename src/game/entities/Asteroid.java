package game.entities;

import game.Point;
import game.ui.GameCanvas;

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
		vertices = new Point[9];
		
		initializeVertices();
	}

	public void update(long delta) {
		updateVertices(delta);
		updateBounds();
	}
	
	private void initializeVertices() {
		double x = this.getX(), y = this.getY();
		this.vertices[0] = new Point(x - 4, y - 12);
		this.vertices[1] = new Point(x + 7, y - 6);
		this.vertices[2] = new Point(x + 15, y - 2);
		this.vertices[3] = new Point(x + 10, y + 10);
		this.vertices[4] = new Point(x - 2, y + 11);
		this.vertices[5] = new Point(x - 8, y + 4);
		this.vertices[6] = new Point(x - 15, y);
		this.vertices[7] = new Point(x - 13, y - 8);
		this.vertices[8] = new Point(x - 6, y - 10);
		
		double rotation = Math.PI * Math.random();
		
		for (Point vertex : vertices) {
			vertex.rotate(getCenter(), rotation);
		}
		
		updateBounds();
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
