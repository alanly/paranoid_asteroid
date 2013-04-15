package game.entities;

import game.Point;
import game.ui.GameCanvas;

import java.awt.Rectangle;

/**
 * Bullet is a subclass of Entity and represents projectile fired from the Ship object and from Alien objects.
 * Bullets expire after a certain amount of time.
 *
 */
public class Bullet extends Entity {
	public static final double MAX_TIME_TO_LIVE = 0.8e9;
	
	private boolean expired;
	private Entity source;
	private double angle;
	private double linearSpeed = 4.0e-7;
	private double timeToLive = MAX_TIME_TO_LIVE;

	/**
	 * Creates a new Bullet from the firing source, the Bullet's initial location and the angle at which the shot is being fired
	 * @param source <tt>Entity</tt> which fired the Bullet
	 * @param center <tt>Point</tt> at which the Bullet initially appears
	 * @param angle angle at which the Bullet was fired and will be traveling
	 */
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

	/**
	 * Updates the state of the of the Bullet
	 * @param delta time elapsed since last update
	 */
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
	/**
	 * Gets the source of the Bullet
	 * @return <tt>Entity</tt> object  which fired the Bullet
	 */
	public Entity getSource() {
		return source;
	}
	/**
	 * Indicates if the Bullet is expired.
	 * @return <tt>true</tt> if the Bullet is expired.
	 */
	public boolean isExpired() {
		return this.expired;
	}

	/**
	 * Updates the location of the center of the Bullet
	 * @param delta time elapsed since last update.
	 */
	private void updateCenter(long delta) {
		double distance = linearSpeed * delta;
		double dx = distance * Math.cos(angle);
		double dy = -distance * Math.sin(angle);

		// Move center
		getCenter().move(dx, dy);

		// Wrap around
		getCenter().wrapAround(GameCanvas.WIDTH, GameCanvas.HEIGHT);
	}

	/**
	 * Updates the position of the bounds of the Bullet shape according to the Bullet's bounds.
	 * 
	 * */
	private void updateBounds() {
		Rectangle bounds = (Rectangle) getBounds();
		bounds.x = (int) getCenter().x;
		bounds.y = (int) getCenter().y;
	}
}
