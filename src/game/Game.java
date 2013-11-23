package game;

import game.entities.*;
import game.events.BulletFiredEvent;
import game.events.BulletFiredListener;
import game.events.HyperspaceListener;
import game.events.SaveHandler;
import game.ui.GameCanvas;
import io.InputHandler;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import game.enums.Size;
/**
 * The Game class implements the BulletFiredListener, SaveHandler and HyperspaceListener interfaces.
 * This class contains all the parameters with which the game is run as well as all the components which make up the gameplay and controles how these objects interact.
 * It also contains variables pertaining to the current state of the game.
 *
 */
public class Game implements BulletFiredListener, SaveHandler, HyperspaceListener {
	// Time constants
	private static final double FPS = 30;
	private static final double UPS = FPS * 1.25;
	
	private static final double NANOS_PER_SECOND = 1e9;
	private static final double NANOS_PER_RENDER = NANOS_PER_SECOND / FPS;
	private static final double NANOS_PER_UPDATE = NANOS_PER_SECOND / UPS;
	
	private static final double NANOS_BETWEEN_ALIEN = NANOS_PER_SECOND * 10;
	private static final long NANOS_PER_LEVEL_START_WAIT = (long) (NANOS_PER_SECOND);
	
	private static final int SAFE_RADIUS = 100;
	private static final int NUM_PARTICLES = 5;
	
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
	private List<Particle> particles;
	private Ship ship;
	private GameCanvas canvas;
	
	/**
	 * Creates a new Game.
	 */
	public Game() {
		this(new BasicGameState());
	}
	
	/**
	 * Creates a Game object from a BasicGameState object and assigns the values of the BasicGameState to <tt>level</tt>, <tt>multiplier</tt> and <tt>point</tt>
	 * @param state BasicGameState from which the new Game will be constructed.
	 */
	public Game(BasicGameState state) {
		this.level = state.getLevel();
		this.points = state.getPoints();
		this.lastLevelPoints = state.getPoints();
		this.multiplier = state.getMultiplier();
		this.aliens = new LinkedList<Alien>();
		this.asteroids = new LinkedList<Asteroid>();
		this.bullets = new LinkedList<Bullet>();
		this.powerups = new LinkedList<Powerup>();
		this.particles = new LinkedList<Particle>();
		this.canvas = null;
	}
	
	/**
	 * Starts the game by creating a new Ship object and adding to it a BulletFireLister object and HyperspaceListener object. Then populates the field and starts the game loop.
	 */
	public void start() {
		// Create player and listen to its bullet fired events
		ship = new Ship(new Point(GameCanvas.WIDTH / 2, GameCanvas.HEIGHT / 2));
		ship.addBulletFiredListener(this);
		ship.addHyperspaceListener(this);
		
		populateField();
		loop();
	}
	
	/**
	 * Toggles the <tt>pause</tt> variable
	 */
	public void togglePause() {
		this.paused = !this.paused;
	}
	
	/**
	 * Indicates if the game is paused
	 * @return <tt>true</tt> if the game is paused
	 */
	public boolean isPaused() {
		return paused;
	}
	
	/**
	 * Returns true if the game is saved.
	 * @return true if the game is saved
	 */
	public boolean isSaved() {
		return saved;
	}
	
	/**
	 * Adds a Bullet to the game field and <tt>bullets</tt> list and plays the approrpiate sound effect
	 * @param e BulletFiredEvent which triggered this method call
	 */
	public void bulletFired(BulletFiredEvent e) {
		bullets.add(new Bullet(e.getSource(), e.getOrigin(), e.getAngle()));
		
		if (e.getSource() instanceof Alien) {
			SoundEffect.FIRE_BULLET_ALIEN.play();
		} else {
			SoundEffect.FIRE_BULLET.play();
		}
	}
	
	/**
	 * Return the Ship object
	 * @return <tt>ship</tt>
	 */
	public Ship getShip() {
		return this.ship;
	}
	
	/**
	 * Returns the point count
	 * @return <tt>points</tt>
	 */
	public long getPoints() {
		return points;
	}
	
	/**
	 * Returns the level
	 * @return <tt>level</tt>
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Returns the multiplier for this level.
	 * @return the multiplier for this level.
	 */
	public double getMultipliter() {
		return multiplier;
	}
	
	/**
	 * Returns the list of Bullet objects in the game.
	 * @return the list of Bullet objects in the game.
	 */
	public List<Bullet> getBullets() {
		return this.bullets;
	}
	
	/**
	 * Returns the list of Asteroid objects in the game.
	 * @return the list of Asteroid objects in the game.
	 */
	public List<Asteroid> getAsteroids() {
		return this.asteroids;
	}
	
	/**
	 * Returns the list of Alien objects in the game.
	 * @return the list of Alien objects in the game.
	 */	
	public List<Alien> getAliens() {
		return this.aliens;
	}
	
	/**
	 * Returns the list of Powerup objects in the game.
	 * @return the list of Powerup object in the game.
	 */
	public List<Powerup> getPowerups() {
		return this.powerups;
	}
	
	/**
	 * Returns the list of Particle objects in the game.
	 * @return the list of Particle objects in the game.
	 */
	public List<Particle> getParticles() {
		return this.particles;
	}
	
	/**
	 * Set the Game's GameCanvas to the parameter value
	 * @param canvas GameCanvas to which </tt>canvas<tt> will be set
	 */
	public void setGameCanvas(GameCanvas canvas) {
		this.canvas = canvas;
	}
	
	/**
	 * Indicates whether the Game is waiting to load the next level
	 * @return <tt>true</tt> if the Game is waiting
	 */
	public boolean isWaitingForLevel() {
		return levelStartWait > 0;
	}
	
	/**
	 * Returns the current state of the game which includes <tt>multiplier</tt>, <tt>level</tt> and the points at the beginning of the level. 
	 * @return BasicGameState which contains the current state of the game which includes <tt>multiplier</tt>, <tt>level</tt> and the points at the beginning of the level.
	 * 
	 */
	public BasicGameState extractState() {
		return new BasicGameState(this.level, this.lastLevelPoints, this.multiplier);
	}
	
	/**
	 * Saves the state of the game and sets <tt>save</tt> to <tt>true</tt>.
	 */
	public void handleSave() {
		extractState().save();
		saved = true;
	}
	
	/**
	 * Plays the sound effect for hyperspace use
	 */
	public void hyperspaceEntered() {
		SoundEffect.HYPERSPACE.play();
	}
	
	/**
	 * Populates the game field with Asteroids at random locations
	 */
	public void populateField() {
		int asteroidCount = getNumAsteroidsForCurrentLevel();
		Size asteroidSize = getAsteroidSizeForCurrentLevel();
		Point center;
		
		for (int i = 0; i < asteroidCount; i++) {
			center = Point.getRandom(GameCanvas.WIDTH, GameCanvas.HEIGHT, ship.getCenter(), SAFE_RADIUS);
			asteroids.add(EntityFactory.getInstance().makeAsteroid(asteroidSize, center));
		}
	}
	
	/**
	 * Returns the initial number of Asteroid objects for this level
	 * @return the initial number of Asteroid objects for this level
	 */
	public int getNumAsteroidsForCurrentLevel() {
		return this.level / 2 + this.level % 2 + 1;
	}
	
	/**
	 * Returns the initial size of the Asteroid objects for this level 
	 * @return Size of the initial Asteroids
	 */
	public Size getAsteroidSizeForCurrentLevel() {
		if (this.level < 3) {
			return Size.SMALL;
		} else if (this.level < 7) {
			return Size.MEDIUM;
		} else {
			return Size.LARGE;
		}
	}
	
	/**
	 * Resets the InputHandler and starts the game loop which runs until the Ship dies or the game is saved.
	 * In the loop the game is updated and rendered, Aliens are generated, and the next level is loaded when the previous level is ended.
	 */
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
			
			// Try to spawn alien
			if (timeSinceLastAlien > NANOS_BETWEEN_ALIEN) {
				generateAlien();
				timeSinceLastAlien = 0;
			}
		}
	}
	
	/**
	 * Update the state of all Entities on the game field
	 * @param delta time elapsed since last update
	 */
	private void update(long delta) {
		ship.update(delta);
		
		updateBullets(delta);
		updatePowerups(delta);
		updateParticles(delta);
		updateEntities(delta);
	}
	
	/** 
	 * Updates the state of the Bullet objects
	 * @param delta time elapsed since last update
	 */
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
	
	/**
	 * Updates the state of the Powerup objects
	 * @param delta time elapsed since last update
	 */
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
	
	/**
	 * Updates the state of the Alien and Asteroid objects
	 * @param delta time elapsed since last update
	 */
	private void updateEntities(long delta) {
		for (Asteroid asteroid : asteroids) {
			asteroid.update(delta);
		}
		
		for (Alien alien : aliens) {
			alien.update(delta);
		}
	}
	
	/**
	 * Updates the state of the Particle objects
	 * @param delta time  elapsed since last update
	 */
	private void updateParticles(long delta) {
		Iterator<Particle> i = particles.iterator();
		Particle p;
		
		while(i.hasNext()) {
			p = i.next();
			
			if (p.isExpired()) {
				i.remove();
			} else {
				p.update(delta);
			}
		}
	}
	
	/**
	 * Checks for collisions and updates the level if all Aliens and
	 * Asteroids have been destroyed
	 */
	private void collisionCheck() {
		checkShipEntityCollisions();
		checkShipPowerupCollisions();
		checkBulletEntityCollisions();
		checkAlienAsteroidCollisions();
		
		if (asteroids.size() + aliens.size() == 0) {
			level++;
			levelEnded = true;
			levelStartWait = NANOS_PER_LEVEL_START_WAIT;
			
			allocatePoints(null);
		}
	}
	
	/**
	 * Checks for collisions with the Ship. Kills the player if a collision is detected. 
	 */
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
	
	/**
	 * Checks for collisions between the Ship and Poweruop objects.
	 * If there is collision, removes the Powerup from the game field and applies it.
	 */
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
	
	/**
	 * Check for collisions between Bullet objects and Alien, Ship and Asteroid objects. If a collision occurs with the Ship, the Ship is killed.
	 * If it occurs with an Asteroid, the Asteroid is broken up or, failing that, removed from the game field and the approriate
	 * points are allocated. If it occurs with an Alien, the Alien is removed from the game field and the approriate
	 * points are allocated.
	 */
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
				generateParticles(ship.getCenter());
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
							generateParticles(asteroid.getCenter());
							
							bulletCollided = true;
							
							asteroidCollided(asteroid, newAsteroids);
							
							// If the bullet came from the player, allocate the player points based on the entity they hit
							if (b.getSource() == ship) {
								allocatePoints(asteroid);
							}
							
							break;
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
							generateParticles(alien.getCenter());
							
							SoundEffect.ALIEN_DIE.play();
							
							dropPowerup(alien.getCenter(), newPowerups);
							
							// If the bullet came from the player, allocate the player points based on the entity they hit
							if (b.getSource() == ship) {
								allocatePoints(alien);
							}
							
							break;
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
	
	/**
	 * Checks for collisions between Alien and Asteroid objects. If a collision is detected the Alien is removed from the playing field and the Asteroid is either broken up,
	 * or failing that, remove from the playing field
	 */
	private void checkAlienAsteroidCollisions() {
		List<Asteroid> newAsteroids = new LinkedList<Asteroid>();
		Iterator<Alien> alienIterator = aliens.iterator();
		
		// Iterate over all aliens
		while (alienIterator.hasNext()) {
			Alien alien = alienIterator.next();
			
			Iterator<Asteroid> asteroidIterator = asteroids.iterator();
			
			// Iterate over all asteroids
			while (asteroidIterator.hasNext()) {
				Asteroid asteroid = asteroidIterator.next();
				
				try {
					// Construct areas and intersect them
					Area area = new Area(alien.getBounds());
					area.intersect(new Area(asteroid.getBounds()));
					
					// If the intersection area isn't empty, then the two shapes have overlapped
					if (!area.isEmpty()) {
						SoundEffect.ALIEN_DIE.play();
						asteroidCollided(asteroid, newAsteroids);
						
						alienIterator.remove();
						asteroidIterator.remove();
						generateParticles(alien.getCenter());
					}
				} catch (IllegalStateException e) {
					continue;
				}
			}
		}
		
		for (Asteroid a : newAsteroids) {
			asteroids.add(a);
		}
	}
	
	/**
	 * Applies the Powerup <tt>p</tt> to the Ship
	 * @param p Powerup to be applied
	 */
	private void applyPowerup(Powerup p) {
		Powerup.Power type = p.getType();
		
		if (type == Powerup.Power.BOOST) {
			ship.boost();
		} else if (type == Powerup.Power.TRIPLE_SHOT) {
			ship.arm();
		} else if (type == Powerup.Power.SHIELD) {
			ship.shield();
		} else if (type == Powerup.Power.PULSE) {
			ship.pulseOn();
		}
	}
	
	/**
	 * Ends the current level, clears the playing field of all Entities other than the ship then populates the field for the next level
	 */
	private void nextLevel() {
		if (levelEnded) {
			levelEnded = false;
			lastLevelPoints = this.points;
			bullets.clear();
			populateField();
		}
	}
	
	/**
	 * Has a probability of creating an Alien object at a random point in the game field
	 */
	private void generateAlien() {
		// Chance of an alien appearing
		if (Math.random() < this.level / 10.0) {
			SoundEffect.ALIEN_APPEAR.play();
			Alien alien = new Alien(Point.getRandom(GameCanvas.WIDTH, GameCanvas.HEIGHT, ship.getCenter(), SAFE_RADIUS), ship);
			alien.addBulletFiredListener(this);
			this.aliens.add(alien);
		}
	}
	
	/**
	 * Has a probability of creating a Powerup object at a point <tt>p</tt> and adding it to a Powerup list
	 * @param p Point at which the Powerup will appear
	 * @param newPowerups list to which the Powerup will be added
	 */
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
	
	/**
	 * Increments the points by an amount depending on depending the Entity destroyed and multiplier 
	 * @param destroyed Entity which has been destroyed or <tt>null</tt> if the point allocation is not a result of Entity destruction
	 */
	private void allocatePoints(Entity destroyed) {
		if (destroyed == null) {
			// Not an interaction with an entity
			points += multiplier * POINTS_CLEAR_LEVEL;
			multiplier += 0.5;
		} else {
			// Tell the ship it hit something
			ship.bulletHit();
			
			// Allocate points based on what entity was destroyed
			if (destroyed instanceof Asteroid) {
				points += multiplier * POINTS_ASTEROID;
			} else if (destroyed instanceof Alien) {
				points += multiplier * POINTS_ALIEN;
			}
		}
	}
	
	/**
	 * Generates a particle at the point <tt>p</tt> and adds its to the list of particles
	 * @param p Point at which particle is to be generated
	 */
	private void generateParticles(Point p) {
		for (int i = 0; i < NUM_PARTICLES; i++) {
			particles.add(new Particle(p));
		}
	}
	
	/**
	 * Plays asteroid break sound and attempts to break asteroids up.
	 * @param asteroid asteroid to break
	 * @param newAsteroids list of new asteroids to add
	 */
	private void asteroidCollided(Asteroid asteroid, List<Asteroid> newAsteroids) {
		SoundEffect.ASTEROID_BREAK.play();
		
		// Try to break up the asteroid
		Size aSize = asteroid.getSize();
			
		// Only if it's not already the smallest
		if (aSize != Size.SMALL) {
			newAsteroids.add(EntityFactory.getInstance().makeAsteroid(aSize.getSmaller(), new Point(asteroid.getCenter())));
			newAsteroids.add(EntityFactory.getInstance().makeAsteroid(aSize.getSmaller(), new Point(asteroid.getCenter())));
		}
	}
}
