package game;

/**
 * A bullet fired listener will have its bullet fired method called when a BulletFiredEvent occurs.
 *
 */
public interface BulletFiredListener {
	/**
	 * Action that is performed when bullet fired event is fired.
	 * @param e the bullet fired event
	 */
	public void bulletFired(BulletFiredEvent e);
}
