package game;

import io.Loader;

import java.io.Serializable;

public class BasicGameState implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String LOAD_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + ".pastate";
	
	private int level;
	private long points;
	
	public BasicGameState() {
		this(1, 0);
	}
	
	public BasicGameState(int level, long points) {
		this.level = level;
		this.points = points;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public long getPoints() {
		return this.points;
	}
	
	public void save() {
		Loader.unload(this, LOAD_PATH);
	}
	
	public static BasicGameState load() {
		return Loader.load(BasicGameState.class, LOAD_PATH);
		
	}
}
