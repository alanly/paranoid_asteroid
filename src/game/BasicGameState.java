package game;

import io.Loader;

import java.io.Serializable;
/**
 * BasicGameState implements the Serializable interface and represents the state of a game.
 * 
 *
 */
public class BasicGameState implements Serializable {
	private static final long serialVersionUID = 2L;
	
	private static final String LOAD_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + ".pastate";
	
	private int level;
	private long points;
	private double multiplier;
	/**
	 * Creates a new BasicGameState corresponding to the intial state of the game
	 */
	public BasicGameState() {
		this(1, 0, 1);
	}
	/**
	 * Creates a new BasicGameState
	 * @param level level of the game state
	 * @param points points of the game state
	 * @param multiplier score multiplier of the game state
	 */
	public BasicGameState(int level, long points, double multiplier) {
		this.level = level;
		this.points = points;
		this.multiplier = multiplier;
	}
	/**
	 * 
	 * @return the <tt>level</tt> of the object
	 */
	public int getLevel() {
		return this.level;
	}
	/**
	 * 
	 * @return  the <tt>points</tt> of the object
	 */
	public long getPoints() {
		return this.points;
	}
	/**
	 * 
	 * @return the <tt>multiplier</tt> of the object
	 */
	public double getMultiplier() {
		return this.multiplier;
	}
	/**
	 * Unloads the game state on the file at <tt>LOAD_PATH</tt> through the Loader
	 */
	public void save() {
		Loader.unload(this, LOAD_PATH);
	}
	/**
	 * Loads a game state from the file at <tt>LOAD_PATH</tt> through the Loader
	 * @return loaded BasicGameState
	 */
	public static BasicGameState load() {
		return Loader.load(BasicGameState.class, LOAD_PATH);
	}
}
