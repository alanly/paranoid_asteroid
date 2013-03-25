package game.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import events.BulletFiredEvent;
import events.BulletFiredListener;
import game.InputHandler;
import game.Point;
import game.entities.Asteroid;
import game.entities.Bullet;
import game.entities.Entity;
import game.entities.Ship;

public class GameField extends Canvas implements KeyListener, BulletFiredListener {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	
	private static final long serialVersionUID = 1L;
	private static final long NANOS_IN_SECOND = 1000000000;
	private static final long FPS = 30;
	private static final double NSPF = NANOS_IN_SECOND / FPS;
	private static final double HALF_RATE = NSPF * 2;
	
	private int level;
	private boolean alive;
	private boolean paused;
	private List<Bullet> bullets;
	private List<Entity> entities;
	private Ship player;
	
	public GameField() {
		level = 1;
		alive = true;
		paused = false;
		bullets = new ArrayList<Bullet>();
		entities = new ArrayList<Entity>();
		
		this.setBackground(new Color(0x292b36));
		this.addKeyListener(InputHandler.getInstance());
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void start() {
		initializeEntities();
		loop();
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_P) {
			this.paused = !this.paused;
		}
	}
	
	public void keyReleased(KeyEvent e) {
	}
	
	public void keyTyped(KeyEvent e) {
	}
	
	public void bulletFired(BulletFiredEvent e) {
		bullets.add(new Bullet(e.getSource(), e.getOrigin(), e.getAngle()));
	}
	
	private void initializeEntities() {
		int asteroidCount = level * 2;
		
		// Create player and listen to its bullet fired events
		player = new Ship(new Point(WIDTH / 2, HEIGHT / 2));
		player.addBulletFiredListener(this);
		
		for (int i = 0; i < asteroidCount; i++) {
			entities.add(new Asteroid(Point.getRandom(WIDTH, HEIGHT)));
		}
	}
	
	private void loop() {
		long delta, now, lastLoop = System.nanoTime();
		long lastRender = 0;
		long lastSecond = 0;
		long lastCollisionCheck = 0;
		
		while(alive) {
			// Adjust counters
			now = System.nanoTime();
			delta = now - lastLoop;
			lastLoop = now;
			
			// Bail if paused
			if (paused) {
				continue;
			}
			
			lastRender += delta;
			lastCollisionCheck += delta;
			lastSecond += delta;
			
			// Process every loop
			update(delta);
			
			// Process once for every frame in a second
			if (lastRender >= NSPF) {
				lastRender = 0;
				render(delta);
			}
			
			if (lastCollisionCheck >= HALF_RATE) {
				lastCollisionCheck = 0;
				collisionCheck();
			}
			
			// Process once per second
			if (lastSecond >= NANOS_IN_SECOND) {
				lastSecond = 0;
			}
		}
	}
	
	private void update(long delta) {
		player.update(delta);
		
		updateBullets(delta);
		updateEntities(delta);
	}
	
	private void updateBullets(long delta) {
		Iterator<Bullet> i = bullets.iterator();
		Bullet b;
		
		while(i.hasNext()) {
			b = i.next();
			
			if (b.isExpired()) {
				i.remove();
			} else {
				b.update(delta);
			}
		}
	}
	
	private void updateEntities(long delta) {
		for (Entity e : entities) {
			e.update(delta);
		}
	}
	
	private void render(long delta) {
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
		
		// Clean up and show
		g.dispose();
		bufferStrategy.show();
	}
	
	private void collisionCheck() {
		for (Bullet b : bullets) {
			// Check collision with player
			
			for (Entity e : entities) {
				// Check collision with other entities
			}
		}
	}
}
