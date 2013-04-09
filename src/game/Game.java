package game;

import io.InputHandler;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import game.entities.Alien;
import game.entities.Asteroid;
import game.entities.Asteroid.AsteroidSize;
import game.entities.BoostPowerup;
import game.entities.Bullet;
import game.entities.Entity;
import game.entities.Powerup;
import game.entities.Ship;
import game.entities.TripleShotPowerup;
import game.events.BulletFiredEvent;
import game.events.BulletFiredListener;
import game.ui.GameCanvas;

public class Game implements BulletFiredListener, KeyListener {
	// Time constants
	private static final double FPS = 30;
	private static final double UPS = FPS * 1.25;
	
	private static final double NANOS_PER_SECOND = 1e9;
	private static final double NANOS_PER_RENDER = NANOS_PER_SECOND / FPS;
	private static final double NANOS_PER_UPDATE = NANOS_PER_SECOND / UPS;
	
	private static final double NANOS_BETWEEN_ALIEN = NANOS_PER_SECOND * 10;
	private static final long NANOS_PER_LEVEL_START_WAIT = (long) (NANOS_PER_SECOND * 1.5);
	
	private static final int SAFE_RADIUS = 100;
	
	// Points constants
	private static final long POINTS_ALIEN = 250;
	private static final long POINTS_ASTEROID = 100;
	private static final long POINTS_CLEAR_LEVEL = 200;
	
	// Game state
	private int level;
	private long points;
	private long lastLevelPoints;
	private double multiplier = 1;
	private boolean paused = false;
	private boolean levelEnded = false;
	private long levelStartWait = 0;
	
	// Game components
	private List<Bullet> bullets;
	private List<Entity> entities;
	private List<Powerup> powerups;
	private Ship ship;
	private GameCanvas canvas;
	
	public Game() {
		this(new BasicGameState(1, 0));
	}
	
	public Game(BasicGameState state) {
		this.level = state.getLevel();
		this.points = state.getPoints();
		this.lastLevelPoints = state.getPoints(); 
		this.bullets = new LinkedList<Bullet>();
		this.entities = new LinkedList<Entity>();
		this.powerups = new LinkedList<Powerup>();
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
	
	public List<Powerup> getPowerups() {
		return this.powerups;
	}
	
	public void setGameCanvas(GameCanvas canvas) {
		this.canvas = canvas;
	}
	
	public BasicGameState extractState() {
		return new BasicGameState(this.level, this.lastLevelPoints);
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
		InputHandler.getInstance().reset();
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
			
			// Bail if waiting for level to start
			if (levelStartWait > 0) {
				levelStartWait -= delta;
				canvas.renderGame(this);
				continue;
			}
			
			levelStartWait = 0;
			
			// Go to next level, skip update and render
			if (levelEnded) {
				nextLevel();
				lastLevelPoints = this.points;
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
		updatePowerups(delta);
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
	
	private void updatePowerups(long delta) {
		Iterator<Powerup> i = powerups.iterator();
		Powerup p;
		
		while(i.hasNext()) {
			p = i.next();
			
			if (p.isExpired()) {
				i.remove();
			} else {
				p.update(delta);
			}
		}
	}
	
	private void updateEntities(long delta) {
		for (Entity e : entities) {
			e.update(delta);
		}
	}
	
	private void collisionCheck() {
		checkShipEntityCollisions();
		checkShipPowerupCollisions();
		checkBulletEntityCollisions();
		
		if (entities.size() == 0) {
			level++;
			levelEnded = true;
			
			allocatePoints(null);
		}
	}
	
	private void checkShipEntityCollisions() {
		// Check for ship-entity collision
		Iterator<Entity> entityIterator = entities.iterator();
		
		// Loop through all entities
		while(entityIterator.hasNext()) {
			Entity e = entityIterator.next();
			
			// Construct areas and intersect them
			Area area = new Area(ship.getBounds());
			Area entityArea = new Area(e.getBounds());
			area.intersect(entityArea);
			
			// If the intersection area isn't empty, then the two shapes have overlapped
			if (!area.isEmpty()) {
				// Kill player if collision was with asteroid or alien
				if (e instanceof Asteroid || e instanceof Alien) {
					ship.die();
					SoundEffect.SHIP_CRASH.play();
				}
			}
		}
	}
	
	private void checkShipPowerupCollisions() {
		// Check for ship-powerup collision
		Iterator<Powerup> powerupIterator = powerups.iterator();
		
		// Loop through all powerups
		while(powerupIterator.hasNext()) {
			Powerup p = powerupIterator.next();
			
			// Construct areas and intersect them
			Area area = new Area(ship.getBounds());
			Area powerupArea = new Area(p.getBounds());
			area.intersect(powerupArea);
			
			// If the intersection area isn't empty, then the two shapes have overlapped
			if (!area.isEmpty()) {
				SoundEffect.POWER_UP.play();
				powerupIterator.remove();
				applyPowerup(p);
			}
		}
	}
	
	private void checkBulletEntityCollisions() {
		List<Asteroid> newAsteroids = new LinkedList<Asteroid>();
		List<Powerup> newPowerups = new LinkedList<Powerup>();
		
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
				
				// Iterate over all entities
				while (entityIterator.hasNext()) {
					Entity e = entityIterator.next();
					
					// In try in case bullet intersects two entities at once (cannot be removed twice)
					try {
						// If the current entity is not the source and the bullet intersects the current entity
						if (e != b.getSource() && e.getBounds().intersects((Rectangle)b.getBounds())) {
							// Don't let aliens destroy asteroids
							if ((e instanceof Asteroid || e instanceof Alien) && b.getSource() instanceof Alien) {
								continue;
							}
							
							// Remove entities that have collided
							bulletIterator.remove();
							entityIterator.remove();
							
							if (e instanceof Asteroid) {
								// Entity was an asteroid
								SoundEffect.ASTEROID_BREAK.play();
								
								// Try to break up the asteroid
								Asteroid a = (Asteroid) e;
								AsteroidSize aSize = a.getSize();
								
								// Only if it's not already the smallest
								if (aSize != AsteroidSize.SMALL) {
									newAsteroids.add(Asteroid.buildAsteroid(aSize.getSmaller(), new Point(a.getCenter())));
									newAsteroids.add(Asteroid.buildAsteroid(aSize.getSmaller(), new Point(a.getCenter())));
								}
							} else if (e instanceof Alien) {
								// Entity was an alien
								SoundEffect.ALIEN_DIE.play();
								
								dropPowerup(e.getCenter(), newPowerups);
							}
							
							// If the bullet came from the player, allocate the player points based on the entity they hit
							if (b.getSource() == ship) {
								allocatePoints(e);
							}
						}
					} catch (IllegalStateException ex) {
						// Carry on
						continue;
					}
				}
			}
		}
		
		// Add new asteroid fragments
		for (Asteroid a : newAsteroids) {
			entities.add(a);
		}
		
		// Add powerups
		for (Powerup p : newPowerups) {
			powerups.add(p);
		}
	}
	
	private void applyPowerup(Powerup p) {
		if (p instanceof BoostPowerup) {
			ship.boost();
		} else if (p instanceof TripleShotPowerup) {
			ship.arm();
		}
	}
	
	private void nextLevel() {
		if (levelEnded) {
			bullets.clear();
			populateField();
			
			levelStartWait = NANOS_PER_LEVEL_START_WAIT;
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
	
	private void dropPowerup(Point p, List<Powerup> newPowerups) {
		// Chance of powerup dropping when alien is destroyed
		if (Math.random() < 0.5) {
			try {
				newPowerups.add(Powerup.getRandomPowerup(p));
			} catch (Exception e) {
				// Something crazy happened
				e.printStackTrace();
			}
		}
	}
	
	private void allocatePoints(Entity destroyed) {
		if (destroyed == null) {
			// Not an interaction with an entity
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
