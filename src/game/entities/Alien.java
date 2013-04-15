package game.entities;

import java.awt.Polygon;
import java.util.LinkedList;
import java.util.List;


import game.Point;
import game.entities.Asteroid.Size;
import game.events.BulletFiredEvent;
import game.events.BulletFiredListener;
import game.ui.GameCanvas;

/**
 * Alien is a subclass of Entity which moves on the game field and fires
 * at the playable character which is the Ship object.
 * 
 **/
public class Alien extends Entity {
	private static double LINEAR_SPEED_VARIANCE = 0.5;
	private static double MIN_LINEAR_SPEED = 3e-8;
	private static double NANOS_BEFORE_BULLET_FIRED = 2e9;
	
	protected Point[] vertices;
	protected Size size = Size.SMALL;
	
	private double angle;
	private double speed = MIN_LINEAR_SPEED;
	private double bulletTimer = 0;
	private List<BulletFiredListener> bulletFiredListeners;
	private Entity target;
	
	/**
	 * Alien object constructor
	 * @param center the center point  of the Alien object
	 * @param target the target at which the Alien will be firing
	 */
	public Alien(Point center, Entity target) {
		this.setCenter(center);
		this.target = target;
		this.bulletFiredListeners = new LinkedList<BulletFiredListener>();
		
		// Give random angle
		angle = Math.random() * FULL_CIRCLE_RAD;
		
		// Vary speed between 1 and (1 + LINEAR_SPEED_VARIANCE) times
		speed *= 1 + Math.random() * LINEAR_SPEED_VARIANCE;
		
		initializeVertices();
		updateBounds();
	}
	
	/**
	 * Updates the state of the Alien and causes the Alien to fire.
	 * @param delta time since last update. 
	 */
	public void update(long delta) {
		bulletTimer += delta;
		
		updateVertices(delta);
		updateBounds();
		fireBullet();
	}
	
	/**
	 * Adds a BulletFiredListener <tt>listener</tt> to the <tt>bulletFiredListener</tt> list.
	 * @param listener the listener to be added to the list.
	 */
	public void addBulletFiredListener(BulletFiredListener listener) {
		this.bulletFiredListeners.add(listener);
	}
	
	/**
	 * Initializes the vertices array containing the vertices of the Alien shape according to its location
	 */
	private void initializeVertices() {
		this.vertices = new Point[13];
		
		double x = this.getX(), y = this.getY();
		this.vertices[0] = new Point(x - 4, y - 10);
		this.vertices[1] = new Point(x - 1, y - 10);
		this.vertices[2] = new Point(x, y - 12);
		this.vertices[3] = new Point(x + 1, y - 10);
		this.vertices[4] = new Point(x + 4, y - 10);
		this.vertices[5] = new Point(x + 8, y - 5);
		this.vertices[6] = new Point(x - 8, y - 5);
		this.vertices[7] = new Point(x + 8, y - 5);
		this.vertices[8] = new Point(x + 20, y);
		this.vertices[9] = new Point(x + 6, y + 8);
		this.vertices[10] = new Point(x - 6, y + 8);
		this.vertices[11] = new Point(x - 20, y);
		this.vertices[12] = new Point(x - 8, y - 6);
	}
	
	/**
	 * Updates the position of the vertices according to the Alien's speed and the elapsed time since last update, <tt>delta</tt> 
	 * @param delta time elapsed since last update.
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
	 * Updates the position of the bounds of the Alien shape according to the Alien's vertices
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
	
	/**
	 * Causes the Alien to fire towards target.
	 */
	private void fireBullet() {
		if (bulletTimer > NANOS_BEFORE_BULLET_FIRED) {
			bulletTimer = 0;
			
			for (BulletFiredListener listener : bulletFiredListeners) {
				double shipAngle = 3.0 / 2.0 * Math.PI - Math.atan2(getX() - target.getX(), target.getY() - getY());
				double aimVariance = ((Math.random() > 0.5) ? -1 : 1) * FULL_CIRCLE_RAD * Math.random() / 18;
				shipAngle += aimVariance;
				
				listener.bulletFired(new BulletFiredEvent(this, new Point(getX(), getY()+9), shipAngle));
			}
		}
	}
}
