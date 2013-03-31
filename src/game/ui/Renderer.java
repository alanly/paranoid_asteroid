package game.ui;

import game.entities.Asteroid;
import game.entities.Bullet;
import game.entities.Entity;
import game.entities.Ship;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * Responsible for rendering entities.
 *
 */
public class Renderer {
	private static Color ENTITY_COLOR = new Color(0xF0F0F0);
	private static Font POINTS_FONT = new Font("Consolas", Font.PLAIN, 14);
	
	/**
	 * Delegates rendering the entity to its proper method if it exists.
	 * @param e the entity
	 * @param g the graphics object
	 */
	public static void render(Entity e, Graphics2D g) {
		if (e instanceof Ship) {
			renderShip((Ship) e, g);
		} else if (e instanceof Bullet) {
			renderBullet((Bullet) e, g);
		} else if (e instanceof Asteroid) {
			renderAsteroid((Asteroid) e, g);
		}
	}
	
	/**
	 * Renders the HUD (points, level).
	 * @param points the points to render
	 * @param points the level to render
	 * @param g the graphics object
	 */
	public static void renderHUD(long points, int level, Graphics2D g) {
		Font oldFont = g.getFont();
		Color oldColor = g.getColor();
		g.setFont(POINTS_FONT);
		g.setColor(ENTITY_COLOR);
		g.drawString("Points: " + points, 4, 16);
		g.drawString(" Level: " + level, 4, 32);
		g.setFont(oldFont);
		g.setColor(oldColor);
	}
	
	/**
	 * Renders a ship entity.
	 * @param e the ship
	 * @param g the graphics object
	 */
	private static void renderShip(Ship e, Graphics2D g) {
		Color oldColor = g.getColor();
		g.setColor(ENTITY_COLOR);
		g.drawPolygon((Polygon)e.getBounds());
		g.setColor(oldColor);
	}
	
	/**
	 * Renders a bullet entity.
	 * @param e the bullet
	 * @param g the graphics object
	 */
	private static void renderBullet(Bullet e, Graphics2D g) {
		Color oldColor = g.getColor();
		g.setColor(ENTITY_COLOR);
		g.fill((Rectangle)e.getBounds());
		g.draw((Rectangle)e.getBounds());
		g.setColor(oldColor);
	}
	
	/**
	 * Renders an asteroid entity.
	 * @param e the asteroid
	 * @param g the graphics object
	 */
	private static void renderAsteroid(Asteroid e, Graphics2D g) {
		Color oldColor = g.getColor();
		g.setColor(ENTITY_COLOR);
		g.fill((Polygon)e.getBounds());
		g.drawPolygon((Polygon)e.getBounds());
		g.setColor(oldColor);
	}
}
