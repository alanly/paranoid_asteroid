package game.entities;

import java.awt.Polygon;

import game.Point;
import game.entities.Asteroid.AsteroidSize;
import game.ui.GameCanvas;

public class Alien extends Entity {
	private static double LINEAR_SPEED_VARIANCE = 0.5;
	private static double MIN_LINEAR_SPEED = 3e-8;
	
	protected Point[] vertices;
	protected AsteroidSize size = AsteroidSize.SMALL;
	
	private double angle;
	private double speed = MIN_LINEAR_SPEED;
	
	public Alien(Point center) {
		this.setCenter(center);
		
		// Give random angle
		angle = Math.random() * FULL_CIRCLE_RAD;
		
		// Vary speed between 1 and (1 + LINEAR_SPEED_VARIANCE) times
		speed *= 1 + Math.random() * LINEAR_SPEED_VARIANCE;
		
		initializeVertices();
		updateBounds();
	}
	
	public void update(long delta) {
		updateVertices(delta);
		updateBounds();
	}
	
	private void initializeVertices() {
		this.vertices = new Point[12];
		
		double x = this.getX(), y = this.getY();
		this.vertices[0] = new Point(x - 4, y - 10);
		this.vertices[1] = new Point(x - 1, y - 10);
		this.vertices[2] = new Point(x, y - 12);
		this.vertices[3] = new Point(x + 1, y - 10);
		this.vertices[4] = new Point(x + 4, y - 10);
		this.vertices[5] = new Point(x + 8, y - 5);
		this.vertices[6] = new Point(x + 8, y - 5);
		this.vertices[7] = new Point(x + 20, y);
		this.vertices[8] = new Point(x + 6, y + 8);
		this.vertices[9] = new Point(x - 6, y + 8);
		this.vertices[10] = new Point(x - 20, y);
		this.vertices[11] = new Point(x - 8, y - 6);
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
