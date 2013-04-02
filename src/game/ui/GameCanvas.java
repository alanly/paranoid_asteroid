package game.ui;

import game.InputHandler;
import game.entities.Bullet;
import game.entities.Entity;
import game.entities.Ship;

import java.awt.Canvas;
import java.awt.Color;
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
		this.setBackground(new Color(0x292b36));
		this.addKeyListener(InputHandler.getInstance());
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.requestFocusInWindow();
	}
	
	public void render(Ship player, List<Bullet> bullets, List<Entity> entities, long pointsFluid, int level) {
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
		Renderer.render(player, g);
		
		for (Bullet b : bullets) {
			Renderer.render(b, g);
		}
		
		for (Entity e : entities) {
			Renderer.render(e, g);
		}
		
		// Render HUD
		Renderer.renderHUD(pointsFluid, level, g);
		
		// Clean up and show
		g.dispose();
		bufferStrategy.show();
	}
}
