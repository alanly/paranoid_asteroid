package game.entities;

public abstract class Powerup extends Entity {
	protected static final long MAX_POWERUP_TTL = (long) 3e9;
	
	private double ttl = MAX_POWERUP_TTL;
	
	public void update(long delta) {
		if (isExpired()) {
			ttl = 0;
		} else {
			ttl -= delta;
		}
	}
	
	public boolean isExpired() {
		return ttl <= 0;
	}
}
