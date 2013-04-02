package game;

import game.entities.Bullet;
import game.entities.Entity;
import game.entities.Ship;

import java.util.List;

public interface GameRenderer {
	public void renderGame(Ship ship, List<Bullet> bullets, List<Entity> entities, long pointsFluid, int level);
}
