package game;

import io.Loader;

import java.io.Serializable;

public class BasicGameState implements Serializable {
	private static final long serialVersionUID = 2L;
	
	private static final String LOAD_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + ".pastate";
	
	private int level;
	private long points;
	private double multiplier;
	
	public BasicGameState() {
		this(1, 0, 1);
	}
	
	public BasicGameState(int level, long points, double multiplier) {
		this.level = level;
		this.points = points;
		this.multiplier = multiplier;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public long getPoints() {
		return this.points;
	}
	
	public double getMultiplier() {
		return this.multiplier;
	}
	
	public void save() {
		Loader.unload(this, LOAD_PATH);
	}
	
	public static BasicGameState load() {
		return Loader.load(BasicGameState.class, LOAD_PATH);
	}
}
