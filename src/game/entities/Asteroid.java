package game.entities;

import game.Point;
import game.ui.GameCanvas;
import game.enums.Size;
import java.awt.Polygon;
/**
 * 
 * Asteroid is a subclass of entity and a superclass of smallAsteroid, mediumAsteroid and largeAsteroid.
 * The class represents an object which travels in a monotone direction and speed across the game field.
 * 
 */
public abstract class Asteroid extends Entity {

	
	private static double LINEAR_SPEED_VARIANCE = 0.3;
	private static double MIN_LINEAR_SPEED = 3e-8;
	
	protected Point[] vertices;
	protected Size size = Size.SMALL;
	
	private double angle;
	private double speed = MIN_LINEAR_SPEED;
	

	/**
	 * This constructs an Asteroid centered at <tt>center</tt>
	 * @param center center point of the Asteroid entity.
	 */
	public Asteroid(Point center) {
		this.setCenter(center);
		
		// Give random angle
		angle = Math.random() * FULL_CIRCLE_RAD;
	
		// Vary speed between 1 and (1 + LINEAR_SPEED_VARIANCE) times
		speed *= 1 + Math.random() * LINEAR_SPEED_VARIANCE;
		
		initializeVertices();
		
		double rotation = FULL_CIRCLE_RAD * Math.random();
		
		for (Point vertex : vertices) {
			vertex.rotate(getCenter(), rotation);
		}
		
		updateBounds();
	}
	/**
	 * Updates the vertices and bounds of the Asteroid object
	 * @param delta time elapsed since last update.
	 */
	public void update(long delta) {
		updateVertices(delta);
		updateBounds();
	}



	/**
	 * Returns the size of the Asteroid object
	 * @return size of the Asteroid object
	 */
	public Size getSize() {
		return this.size;
	}
	/**
	 * initializes the vertices 
	 */
	protected abstract void initializeVertices();
	/**
	 * Updates the vertices of the Asteroid object using its <tt>speed</tt> and <tt>angle</tt>.
	 * @param delta time elapsed since last update
	 */
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
	/**
	 * Updates the position of the bounds of the Asteroid shape according to the Asteroid's vertices
	 * 
	 **/
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
