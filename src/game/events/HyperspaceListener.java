package game.events;
/**
 * An interface for classes which listen for the event of a Ship object entering hyperspace.
 */
public interface HyperspaceListener {
	/**
	 * A method called when a Ship object enters hyperspace
	 */
	public void hyperspaceEntered();
}
