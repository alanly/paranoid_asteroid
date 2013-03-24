package game;

import game.entities.Entity;
import game.entities.Ship;

import java.awt.Graphics2D;

public class Renderer {
	public static void render(Entity e, Graphics2D g) {
		if (e instanceof Ship) {
			renderShip((Ship) e, g);
		}
	}
	
	private static void renderShip(Ship e, Graphics2D g) {
		
	}
}
