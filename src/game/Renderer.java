package game;

import game.entities.Bullet;
import game.entities.Entity;
import game.entities.Ship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Renderer {
	private static Color ENTITY_COLOR = new Color(0xF0F0F0);
	public static void render(Entity e, Graphics2D g) {
		if (e instanceof Ship) {
			renderShip((Ship) e, g);
		} else if (e instanceof Bullet) {
			renderBullet((Bullet) e, g);
		}
	}
	
	private static void renderShip(Ship e, Graphics g) {
		Point[] vertices = e.getVertices();
		Color oldColor = g.getColor();
		
		int[] x = new int[vertices.length];
		int[] y = new int[vertices.length];
		
		for (int i = 0; i < vertices.length; i++) {
			x[i] = (int) vertices[i].getX();
			y[i] = (int) vertices[i].getY();
		}
		
		Polygon p = new Polygon(x, y, x.length);
		
		g.setColor(ENTITY_COLOR);
		g.drawPolygon(p);
		
		g.setColor(oldColor);
	}
	
	private static void renderBullet(Bullet e, Graphics g) {
		Point origin = e.getCenter();
		Color oldColor = g.getColor();
	
		g.setColor(ENTITY_COLOR);
		g.drawRect((int) origin.x, (int) origin.y, 1, 1);
		g.setColor(oldColor);
	}
}
