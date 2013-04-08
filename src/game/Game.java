package game;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import events.BulletFiredEvent;
import events.BulletFiredListener;
import game.entities.Alien;
import game.entities.Asteroid;
import game.entities.Asteroid.AsteroidSize;
import game.entities.Bullet;
import game.entities.Entity;
import game.entities.Ship;
import game.ui.GameCanvas;

public class Game implements BulletFiredListener, KeyListener {
	// Time constants
	private static final double FPS = 30;
	private static final double UPS = FPS * 1.25;
	
	private static final double NANOS_PER_SECOND = 1e9;
	private static final double NANOS_PER_RENDER = NANOS_PER_SECOND / FPS;
	private static final double NANOS_PER_UPDATE = NANOS_PER_SECOND / UPS;
	
	private static final double NANOS_BETWEEN_ALIEN = NANOS_PER_SECOND * 10;
	
	private static final int SAFE_RADIUS = 100;
	
	// Points constants
	private static final long POINTS_ALIEN = 250;
	private static final long POINTS_ASTEROID = 100;
	private static final long POINTS_CLEAR_LEVEL = 200;
	
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
		this.bullets = new LinkedList<Bullet>();
		this.entities = new LinkedList<Entity>();
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
		
		if (e.getSource() instanceof Alien) {
			SoundEffect.FIRE_BULLET_ALIEN.play();
		} else {
			SoundEffect.FIRE_BULLET.play();
		}
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
		int asteroidCount = getNumAsteroids();
		AsteroidSize asteroidSize = getAsteroidSize();
		Point center;
		
		for (int i = 0; i < asteroidCount; i++) {
			center = Point.getRandom(GameCanvas.WIDTH, GameCanvas.HEIGHT, ship.getCenter(), SAFE_RADIUS);
			entities.add(Asteroid.buildAsteroid(asteroidSize, center));
		}
	}
	
	private void loop() {
		SoundEffect.GAME_START.play();
		
		long currentTime = System.nanoTime();
		long lastTime = currentTime;
		
		long delta;
		long timeSinceLastRender = 0;
		long timeSinceLastUpdate = 0;
		long timeSinceLastAlien = 0;
		
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
			timeSinceLastAlien += delta;
			
			// Try to render and check collision
			if (timeSinceLastRender > NANOS_PER_RENDER) {
				if (canvas != null) {
					canvas.renderGame(this);
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
			
			if (timeSinceLastAlien > NANOS_BETWEEN_ALIEN) {
				generateAlien();
				timeSinceLastAlien = 0;
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
		List<Asteroid> newAsteroids = new LinkedList<Asteroid>();
		
		// Check for entity-bullet collision
		Iterator<Bullet> bulletIterator = bullets.iterator();
		
		// Loop through bullets on outer, it's faster when there are no bullets!
		while(bulletIterator.hasNext()) {
			Bullet b = bulletIterator.next();
			
			// Player cannot shoot self
			if (ship != b.getSource() && ship.getBounds().intersects((Rectangle)b.getBounds())) {
				// Check collision with player
				bulletIterator.remove();
				ship.die();
				SoundEffect.SHIP_CRASH.play();
			} else {
				// No player collision, check other entities
				Iterator<Entity> entityIterator = entities.iterator();
				
				while (entityIterator.hasNext()) {
					Entity e = entityIterator.next();
					
					try {
						if (e != b.getSource() && e.getBounds().intersects((Rectangle)b.getBounds())) {
							bulletIterator.remove();
							entityIterator.remove();
							
							if (e instanceof Asteroid) {
								SoundEffect.ASTEROID_BREAK.play();
								
								Asteroid a = (Asteroid) e;
								AsteroidSize aSize = a.getSize();
								
								if (aSize != AsteroidSize.SMALL) {
									newAsteroids.add(Asteroid.buildAsteroid(aSize.getSmaller(), new Point(a.getCenter())));
									newAsteroids.add(Asteroid.buildAsteroid(aSize.getSmaller(), new Point(a.getCenter())));
								}
							} else if (e instanceof Alien) {
								SoundEffect.ALIEN_DIE.play();
							}
							
							if (b.getSource() == ship) {
								allocatePoints(e);
							}
						}
					} catch (IllegalStateException ex) {
						continue;
					}
				}
			}
		}
		
		// Check for ship-asteroid collision
		Iterator<Entity> entityIterator = entities.iterator();
		
		while(entityIterator.hasNext()) {
			Entity e = entityIterator.next();
			
			Area area = new Area(ship.getBounds());
			Area entityArea = new Area(e.getBounds());
			area.intersect(entityArea);
			
			if (!area.isEmpty()) {
				if (e instanceof Asteroid || e instanceof Alien) {
					ship.die();
					SoundEffect.SHIP_CRASH.play();
				}
			}
		}
		
		// Add new asteroid fragments
		for (Asteroid a : newAsteroids) {
			entities.add(a);
		}
		
		if (entities.size() == 0) {
			level++;
			levelEnded = true;
			
			allocatePoints(null);
		}
	}
	
	private void nextLevel() {
		if (levelEnded) {
			bullets.clear();
			populateField();
			
			levelEnded = false;
		}
	}
	
	private AsteroidSize getAsteroidSize() {
		if (this.level < 3) {
			return AsteroidSize.SMALL;
		} else if (this.level < 7) {
			return AsteroidSize.MEDIUM;
		} else {
			return AsteroidSize.LARGE;
		}
	}
	
	private int getNumAsteroids() {
		return this.level / 2 + this.level % 2 + 1;
	}
	
	private void generateAlien() {
		// Chance of an alien appearing
		if (Math.random() < this.level / 10.0) {
			SoundEffect.ALIEN_APPEAR.play();
			Alien alien = new Alien(Point.getRandom(GameCanvas.WIDTH, GameCanvas.HEIGHT, ship.getCenter(), SAFE_RADIUS), ship);
			alien.addBulletFiredListener(this);
			this.entities.add(alien);
		}
	}
	
	private void allocatePoints(Entity destroyed) {
		if (destroyed == null) {
			// Not an interaction with an entity, level end
			points += multiplier * POINTS_CLEAR_LEVEL;
			multiplier += 0.5;
		} else {
			// Allocate points based on what entity was destroyed
			if (destroyed instanceof Asteroid) {
				points += multiplier * POINTS_ASTEROID;
			} else if (destroyed instanceof Alien) {
				points += multiplier * POINTS_ALIEN;
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
	}
	
	public void keyTyped(KeyEvent e) {
	}
}
