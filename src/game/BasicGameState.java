package game;

import java.io.Serializable;

public class BasicGameState implements Serializable {
	private static final long serialVersionUID = 1L;
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
}
