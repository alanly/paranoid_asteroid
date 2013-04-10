package game;

import game.entities.Alien;
import game.entities.Asteroid;
import game.entities.Asteroid.Size;
import game.entities.Bullet;
import game.entities.Entity;
import game.entities.Powerup;
import game.entities.Ship;
import game.events.BulletFiredEvent;
import game.events.BulletFiredListener;
import game.ui.GameCanvas;
import io.InputHandler;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Game implements BulletFiredListener, SaveHandler {
	// Time constants
	private static final double FPS = 30;
	private static final double UPS = FPS * 1.25;
	
	private static final double NANOS_PER_SECOND = 1e9;
	private static final double NANOS_PER_RENDER = NANOS_PER_SECOND / FPS;
	private static final double NANOS_PER_UPDATE = NANOS_PER_SECOND / UPS;
	
	private static final double NANOS_BETWEEN_ALIEN = NANOS_PER_SECOND * 10;
	private static final long NANOS_PER_LEVEL_START_WAIT = (long) (NANOS_PER_SECOND);
	
	private static final int SAFE_RADIUS = 100;
	
	// Points constants
	private static final long POINTS_ALIEN = 250;
	private static final long POINTS_ASTEROID = 100;
	private static final long POINTS_CLEAR_LEVEL = 200;
	
	// Game state
	private int level;
	private long points;
	private long lastLevelPoints;
	private double multiplier;
	private boolean paused = false;
	private boolean saved = false;
	private boolean levelEnded = false;
	private long levelStartWait = 0;
	
	// Game components
	private List<Alien> aliens;
	private List<Asteroid> asteroids;
	private List<Bullet> bullets;
	private List<Powerup> powerups;
	private Ship ship;
	private GameCanvas canvas;
	
	public Game() {
		this(new BasicGameState());
	}
	
	public Game(BasicGameState state) {
		this.level = state.getLevel();
		this.points = state.getPoints();
		this.lastLevelPoints = state.getPoints();
		this.multiplier = state.getMultiplier();
		this.aliens = new LinkedList<Alien>();
		this.asteroids = new LinkedList<Asteroid>();
		this.bullets = new LinkedList<Bullet>();
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
	
	public List<Asteroid> getAsteroids() {
		return this.asteroids;
	}
	
	public List<Alien> getAliens() {
		return this.aliens;
	}
	
	public List<Powerup> getPowerups() {
		return this.powerups;
	}
	
	public void setGameCanvas(GameCanvas canvas) {
		this.canvas = canvas;
	}
	
	public boolean isWaitingForLevel() {
		return levelStartWait > 0;
	}
	
	public BasicGameState extractState() {
		return new BasicGameState(this.level, this.lastLevelPoints, this.multiplier);
	}
	
	public void handleSave() {
		extractState().save();
		saved = true;
	}
	
	private void populateField() {
		int asteroidCount = getNumAsteroids();
		Size asteroidSize = getAsteroidSize();
		Point center;
		
		for (int i = 0; i < asteroidCount; i++) {
			center = Point.getRandom(GameCanvas.WIDTH, GameCanvas.HEIGHT, ship.getCenter(), SAFE_RADIUS);
			asteroids.add(Asteroid.buildAsteroid(asteroidSize, center));
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
		
		while(ship.isAlive() && !saved) {
			// Calculate delta
			lastTime = currentTime;
			currentTime = System.nanoTime();
			delta = currentTime - lastTime;
			
			// Bail if paused
			if (paused) {
				continue;
			}
			
			timeSinceLastUpdate += delta;
			
			// Go to next level, skip update and render
			if (levelEnded) {
				if (isWaitingForLevel()) {
					levelStartWait -= delta;
					
					if (timeSinceLastUpdate > NANOS_PER_UPDATE) {
						canvas.renderGame(this);
						update(timeSinceLastUpdate);
						checkShipPowerupCollisions();
						
						// Reset timer
						timeSinceLastUpdate = 0;
					}
				} else {
					nextLevel();
				}
				
				continue;
			}
			
			levelStartWait = 0;
			
			// Update times since
			timeSinceLastRender += delta;
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
		for (Asteroid asteroid : asteroids) {
			asteroid.update(delta);
		}
		
		for (Alien alien : aliens) {
			alien.update(delta);
		}
	}
	
	private void collisionCheck() {
		checkShipEntityCollisions();
		checkShipPowerupCollisions();
		checkBulletEntityCollisions();
		
		if (asteroids.size() + aliens.size() == 0) {
			level++;
			levelEnded = true;
			levelStartWait = NANOS_PER_LEVEL_START_WAIT;
			
			allocatePoints(null);
		}
	}
	
	private void checkShipEntityCollisions() {
		Iterator<Asteroid> asteroidIterator = asteroids.iterator();
		
		// Loop through all asteroids
		while(asteroidIterator.hasNext()) {
			Asteroid a = asteroidIterator.next();
			
			// Construct areas and intersect them
			Area area = new Area(ship.getBounds());
			area.intersect(new Area(a.getBounds()));
			
			// If the intersection area isn't empty, then the two shapes have overlapped
			if (!area.isEmpty()) {
				// Kill player if collided
				ship.die();
				SoundEffect.SHIP_CRASH.play();
			}
		}
		
		Iterator<Alien> alienIterator = aliens.iterator();
		
		// Loop through all asteroids
		while(alienIterator.hasNext()) {
			Alien a = alienIterator.next();
			
			// Construct areas and intersect them
			Area area = new Area(ship.getBounds());
			area.intersect(new Area(a.getBounds()));
			
			// If the intersection area isn't empty, then the two shapes have overlapped
			if (!area.isEmpty()) {
				// Kill player if collided
				ship.die();
				SoundEffect.SHIP_CRASH.play();
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
		
		// Check for bullet-asteroid collision
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
				Iterator<Asteroid> asteroidIterator = asteroids.iterator();
				boolean bulletCollided = false;
				
				// Iterate over all asteroids
				while (asteroidIterator.hasNext()) {
					Asteroid asteroid = asteroidIterator.next();
					
					// Try, in case bullet intersects two asteroids at once (concurrent modification
					try {
						// If the asteroid intersects the bullet
						if (asteroid.getBounds().intersects((Rectangle)b.getBounds())) {
							// Remove the asteroid and bullet
							asteroidIterator.remove();
							bulletIterator.remove();
							
							bulletCollided = true;
							
							SoundEffect.ASTEROID_BREAK.play();
							
							// Try to break up the asteroid
							Asteroid.Size aSize = asteroid.getSize();
								
							// Only if it's not already the smallest
							if (aSize != Asteroid.Size.SMALL) {
								newAsteroids.add(Asteroid.buildAsteroid(aSize.getSmaller(), new Point(asteroid.getCenter())));
								newAsteroids.add(Asteroid.buildAsteroid(aSize.getSmaller(), new Point(asteroid.getCenter())));
							}
							
							// If the bullet came from the player, allocate the player points based on the entity they hit
							if (b.getSource() == ship) {
								allocatePoints(asteroid);
							}
						}
					} catch (IllegalStateException e) {
						// Carry on
						continue;
					}
				}
				
				// Skip if bullet already collided with something
				if (bulletCollided) {
					continue;
				}
				
				Iterator<Alien> alienIterator = aliens.iterator();
				
				// Iterate over all aliens
				while (alienIterator.hasNext()) {
					Alien alien = alienIterator.next();
					
					// Try, in case bullet intersects two asteroids at once (concurrent modification)
					try {
						// If the alien intersects the bullet
						if (b.getSource() != alien && alien.getBounds().intersects((Rectangle)b.getBounds())) {
							// Remove the alien and bullet
							alienIterator.remove();
							bulletIterator.remove();
							
							SoundEffect.ALIEN_DIE.play();
							
							dropPowerup(alien.getCenter(), newPowerups);
							
							// If the bullet came from the player, allocate the player points based on the entity they hit
							if (b.getSource() == ship) {
								allocatePoints(alien);
							}
						}
					} catch (IllegalStateException e) {
						// Carry on
						continue;
					}
				}
			}
		}
		
		// Add new asteroid fragments
		for (Asteroid a : newAsteroids) {
			asteroids.add(a);
		}
		
		// Add powerups
		for (Powerup p : newPowerups) {
			powerups.add(p);
		}
	}
	
	private void applyPowerup(Powerup p) {
		Powerup.Power type = p.getType();
		
		if (type == Powerup.Power.BOOST) {
			ship.boost();
		} else if (type == Powerup.Power.TRIPLE_SHOT) {
			ship.arm();
		}
	}
	
	private void nextLevel() {
		if (levelEnded) {
			levelEnded = false;
			lastLevelPoints = this.points;
			bullets.clear();
			populateField();
		}
	}
	
	private Size getAsteroidSize() {
		if (this.level < 3) {
			return Size.SMALL;
		} else if (this.level < 7) {
			return Size.MEDIUM;
		} else {
			return Size.LARGE;
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
			this.aliens.add(alien);
		}
	}
	
	private void dropPowerup(Point p, List<Powerup> newPowerups) {
		// Chance of powerup dropping when alien is destroyed
		if (Math.random() < 0.5) {
			try {
				newPowerups.add(new Powerup(p));
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
}
