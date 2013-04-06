package game;

import events.BulletFiredEvent;
import events.BulletFiredListener;
import game.entities.Asteroid;
import game.entities.Bullet;
import game.entities.Entity;
import game.entities.Ship;
import game.ui.GameCanvas;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game implements BulletFiredListener, KeyListener {
	// Time constants
	private static final double FPS = 30;
	private static final double UPS = FPS * 1.25;
	
	private static final double NANOS_PER_SECOND = 1e9;
	private static final double NANOS_PER_RENDER = NANOS_PER_SECOND / FPS;
	private static final double NANOS_PER_UPDATE = NANOS_PER_SECOND / UPS;
	
	private static final int SAFE_RADIUS = 100;
	
	// Points constants
	private static final long POINTS_ASTEROID = 1000;
	private static final long POINTS_CLEAR_LEVEL = 2000;
	
	// Game state
	private int level = 1;
	private long points = 0;
	private double multiplier = 1;
	private boolean paused = false;
	private boolean levelEnded = false;
	
	// Game components
	private List<Bullet> bullets;
	private List<Entity> entities;
	private Ship ship;
	private GameCanvas canvas;
		
	public Game() {
		this.bullets = new ArrayList<Bullet>();
		this.entities = new ArrayList<Entity>();
		this.canvas = null;
	}
	
	public void start() {
		// Create player and listen to its bullet fired events
		ship = new Ship(new Point(GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2));
		ship.addBulletFiredListener(this);
		
		populateField();
		loop();
	}
	
	public void togglePause() {
		this.paused = !this.paused;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void bulletFired(BulletFiredEvent e) {
		bullets.add(new Bullet(e.getSource(), e.getOrigin(), e.getAngle()));
		SoundEffect.FIRE_BULLET.play();
	}
	
	public void keyPressed(KeyEvent e) {
		// Check for pause key
		if (e.getKeyCode() == KeyEvent.VK_P) {
			this.paused = !this.paused;
		}
	}
	
	public Ship getShip() {
		return this.ship;
	}
	
	public long getPoints() {
		return points;
	}
	
	public int getLevel() {
		return level;
	}
	
	public double getMultipliter() {
		return multiplier;
	}
	
	public List<Bullet> getBullets() {
		return this.bullets;
	}
	
	public List<Entity> getEntities() {
		return this.entities;
	}
	
	public void setGameCanvas(GameCanvas canvas) {
		this.canvas = canvas;
	}
	
	private void populateField() {
		int asteroidCount = level * 2;
		
		for (int i = 0; i < asteroidCount; i++) {
			entities.add(new Asteroid(Point.getRandom(GameCanvas.WIDTH, GameCanvas.HEIGHT, ship.getCenter(), SAFE_RADIUS)));
		}
	}
	
	private void loop() {
		long currentTime = System.nanoTime();
		long lastTime = currentTime;
		
		long delta;
		long timeSinceLastRender = 0;
		long timeSinceLastUpdate = 0;
		
		while(ship.isAlive()) {
			// Calculate delta
			lastTime = currentTime;
			currentTime = System.nanoTime();
			delta = currentTime - lastTime;
			
			// Bail if paused
			if (paused) {
				continue;
			}
			
			// Go to next level, skip update and render
			if (levelEnded) {
				nextLevel();
				levelEnded = false;
				continue;
			}
			
			// Update times since
			timeSinceLastRender += delta;
			timeSinceLastUpdate += delta;
			
			// Try to render and check collision
			if (timeSinceLastRender > NANOS_PER_RENDER) {
				if (canvas != null) {
					canvas.renderGame(this);
				}
				
				if (!SoundEffect.BACKGROUND.isPlaying()) {
					SoundEffect.BACKGROUND.play();
				}
				
				// Reset timer
				timeSinceLastRender = 0;
			}
			
			// Try to update
			if (timeSinceLastUpdate > NANOS_PER_UPDATE) {
				update(timeSinceLastUpdate);
				collisionCheck();
				
				// Reset timer
				timeSinceLastUpdate = 0;
			}
		}
	}
	
	private void update(long delta) {
		ship.update(delta);
		
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
	
	private void collisionCheck() {
		// Loop through bullets on outer, it's faster when there are no bullets!
		Iterator<Bullet> bulletIterator = bullets.iterator();
		
		while(bulletIterator.hasNext()) {
			Bullet b = bulletIterator.next();
			
			// Player cannot shoot self
			if (ship != b.getSource() && ship.getBounds().intersects((Rectangle)b.getBounds())) {
				// Check collision with player
				bulletIterator.remove();
				ship.die();
				SoundEffect.EXPLOSION.play();
			} else {
				// No player collision, check other entities
				Iterator<Entity> entityIterator = entities.iterator();
				
				while (entityIterator.hasNext()) {
					Entity e = entityIterator.next();
					
					if (e.getBounds().intersects((Rectangle)b.getBounds())) {
						bulletIterator.remove();
						entityIterator.remove();
						SoundEffect.ASTEROID_BREAK.play();
						
						if (b.getSource() == ship) {
							points += multiplier * POINTS_ASTEROID;
						}
					}
				}
				
				if (entities.size() == 0) {
					level++;
					points += multiplier * POINTS_CLEAR_LEVEL;
					multiplier += 0.5;
					levelEnded = true;
				}
			}
		}
		
		Iterator<Entity> entityIterator = entities.iterator();
		
		while(entityIterator.hasNext()) {
			Entity e = entityIterator.next();
			
			if (e instanceof Asteroid) {
				Area area = new Area(ship.getBounds());
				area.intersect(
					new Area(e.getBounds())
				);
				
				if (!area.isEmpty()) {
					ship.die();
					SoundEffect.EXPLOSION.play();
				}
			}
		}
	}
	
	private void nextLevel() {
		if (levelEnded) {
			populateField();
			
			levelEnded = false;
		}
	}
	
	public void keyReleased(KeyEvent e) {
	}
	
	public void keyTyped(KeyEvent e) {
	}
}
