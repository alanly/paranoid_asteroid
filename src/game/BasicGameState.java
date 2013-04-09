package game;

public class BasicGameState {
	private int level;
	private long points;
	
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
