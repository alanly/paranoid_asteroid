package game.ui;

import game.Colors;
import game.Game;
import game.entities.Alien;
import game.entities.Asteroid;
import game.entities.Bullet;
import game.entities.Powerup;
import game.entities.Ship;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.List;

public class GameCanvas extends Canvas {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	
	private static final long serialVersionUID = 1L;
	
	public GameCanvas() {
		this.setBackground(Colors.DARK_BLUE.getColor());
		this.setFocusable(false);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void setGame(Game game) {
		game.setGameCanvas(this);
	}
	
	public void renderGame(Game game) {
		Ship ship = game.getShip();
		List<Bullet> bullets = game.getBullets();
		List<Asteroid> asteroids = game.getAsteroids();
		List<Alien> aliens = game.getAliens();
		List<Powerup> powerups = game.getPowerups();
		long points = game.getPoints();
		int level = game.getLevel();
		// Prepare buffer strategy and graphics
		BufferStrategy bufferStrategy = getBufferStrategy();

		if (bufferStrategy == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics2D g = (Graphics2D)bufferStrategy.getDrawGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g);
		
		// Render entities
		Renderer.render(ship, g);
		
		for (Bullet b : bullets) {
			Renderer.render(b, g);
		}
		
		for (Asteroid asteroid : asteroids) {
			Renderer.render(asteroid, g);
		}
		
		for (Alien alien : aliens) {
			Renderer.render(alien, g);
		}
		
		for (Powerup p : powerups) {
			Renderer.render(p, g);
		}
		
		// Render HUD
		Renderer.renderHUD(points, level, g);
		
		// Clean up and show
		g.dispose();
		bufferStrategy.show();
	}
}
