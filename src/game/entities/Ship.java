package game.entities;

import game.Point;
import game.events.BulletFiredEvent;
import game.events.BulletFiredListener;
import game.events.HyperspaceListener;
import game.ui.GameCanvas;
import io.InputHandler;

import java.awt.Polygon;
import java.util.LinkedList;
import java.util.List;

public class Ship extends Entity {
	private static final double MAX_BULLET_FIRED_WAIT = 0.5e9;
	private static final long MAX_HYPER_WAIT = (long) 5e9;
	private static final double MAX_LINEAR_SPEED = 2.8e-7;
	private static final double MIN_ANGULAR_SPEED = 4.5e-9;
	private static final long MAX_POWERUP_TTL = (long) 10e9;
	private static final double ACCELERATION = 6.0e-16;
	private static final double DECELERATION = -0.3e-15;
	private static final double TRIPLE_SHOT_OFFSET_ANGLE = FULL_CIRCLE_RAD / 72;
	private static final double NUM_PULSE_SHOTS = 5;
	private static final double PULSE_SHOTS_ANGLE = FULL_CIRCLE_RAD / NUM_PULSE_SHOTS;

	private Point[] vertices;
	private List<BulletFiredListener> bulletFiredListeners;
	private List<HyperspaceListener> hyperspaceListeners;

	private double linearSpeed = 0;
	private double angle = Math.PI / 2;
	private boolean alive = true;
	private double speedBoost = 1;
	private long boostTTL = 0;
	private long pulseTTL = 0;
	private long shieldTTL = 0;
	private long tripleShotTTL = 0;
	private long timeSinceLastFired = 0;
	private long timeSinceLastHyper = MAX_HYPER_WAIT;

	/**
	 * Constructs a new ship.
	 * @param center the center of the ship
	 */
	public Ship(Point center) {
		this.setCenter(center);
		bulletFiredListeners = new LinkedList<BulletFiredListener>();
		hyperspaceListeners = new LinkedList<HyperspaceListener>();
		initializeVertices();
	}

	/**
	 * Updates the state of the ship and fires bullets.
	 * @param delta the time since the last update
	 */
	public void update(long delta) {
		updateSpeed(delta);
		updatePowerups(delta);
		updateAngle(delta);
		updateVertices(delta);
		updateBounds();
		
		timeSinceLastFired += delta;
		timeSinceLastHyper += delta;
		
		if (InputHandler.getInstance().getSpaceKey().isPressed() && canFire()) {
			fireBullet();
			timeSinceLastFired = 0;
		}
		
		if (InputHandler.getInstance().getDownKey().isPressed() && canEnterHyperspace()) {
			enterHyperspace();
			timeSinceLastHyper = 0;
		}
	}
	
	/**
	 * Returns true if the player can fire.
	 * @return true if the player can fire
	 */
	public boolean canFire() {
		return timeSinceLastFired > getMaxBulletFiredWait();
	}
	
	public boolean canEnterHyperspace() {
		return timeSinceLastHyper > MAX_HYPER_WAIT;
	}

	/**
	 * Adds a bullet fired listener to the list of listeners to notify when a bullet is fired.
	 * @param l bullet fired listener to notify when the bullet is fired
	 */
	public void addBulletFiredListener(BulletFiredListener l) {
		this.bulletFiredListeners.add(l);
	}
	
	/**
	 * Adds a hyperspace listener to the list of listeners to notify when a bullet is fired.
	 * @param l hyperspace listener to notify when the bullet is fired
	 */
	public void addHyperspaceListener(HyperspaceListener l) {
		this.hyperspaceListeners.add(l);
	}
	
	/**
	 * Returns true if the ship is alive, false otherwise.
	 * @return true if the ship is alive, false otherwise
	 */
	public boolean isAlive() {
		return this.alive;
	}
	
	/**
	 * Kills the ship.
	 */
	public void die() {
		if (this.hasShield()) {
			unshield();
		} else {
			this.alive = false;
		}
	}
	
	/**
	 * Gives the ship a speed boost.
	 */
	public void boost() {
		speedBoost = 1.4;
		boostTTL = MAX_POWERUP_TTL;
	}
	
	/**
	 * Removes the ship's a speed boost.
	 */
	public void unboost() {
		speedBoost = 1;
		boostTTL = 0;
	}
	
	/**
	 * Gives the ship a triple shot.
	 */
	public void arm() {
		tripleShotTTL = MAX_POWERUP_TTL;
	}
	
	/**
	 * Disabled the ship's triple shot.
	 */
	public void disarm() {
		tripleShotTTL = 0;
	}
	
	/**
	 * Gives the ship a shield.
	 */
	public void shield() {
		shieldTTL = MAX_POWERUP_TTL;
	}
	
	/**
	 * Disables the ship's shield.
	 */
	public void unshield() {
		shieldTTL = 0;
	}
	
	/**
	 * Gives the ship pulse.
	 */
	public void pulseOn() {
		pulseTTL = MAX_POWERUP_TTL;
	}
	
	/**
	 * Disabled the ship's pulse.
	 */
	public void pulseOff() {
		pulseTTL = 0;
	}
	
	/**
	 * Returns the max linear speed of the ship.
	 * @return the max linear speed of the ship
	 */
	public double getMaxLinearSpeed() {
		return MAX_LINEAR_SPEED * speedBoost;
	}
	
	public double getMaxBulletFiredWait() {
		return MAX_BULLET_FIRED_WAIT / speedBoost;
	}
	
	/**
	 * Returns true if the ship has boost.
	 * @return true if the ship has boost
	 */
	public boolean hasBoost() {
		return boostTTL > 0;
	}
	
	/**
	 * Returns true if the ship has triple shot. 
	 * @return true if the ship has triple shot
	 */
	public boolean hasTripleShot() {
		return tripleShotTTL > 0;
	}
	
	/**
	 * Returns true if the ship has a shield.
	 * @return true if the ship has a shield
	 */
	public boolean hasShield() {
		return shieldTTL > 0;
	}
	
	/**
	 * Returns true if the ship has pulse.
	 * @return true if the ship has pulse
	 */
	public boolean hasPulse() {
		return pulseTTL > 0;
	}
	
	/**
	 * Returns true if the ship is accelerating.
	 * @return true is the ship is accelerating
	 */
	private boolean isAccelerating() {
		return InputHandler.getInstance().getUpKey().isPressed();
	}
	
	/**
	 * Updates the speed of the ship.
	 * @param delta the time since the last update
	 */
	private void updateSpeed(long delta) {
		if (isAccelerating()) {
			// Accelerate
			linearSpeed += ACCELERATION * delta;
			linearSpeed = Math.min(getMaxLinearSpeed(), linearSpeed);
		} else {
			// Decelerate
			linearSpeed += DECELERATION * delta;
			linearSpeed = Math.max(0, linearSpeed);
		}
	}
	
	/**
	 * Updates the powerups the ship has.
	 * @param delta the time since the last update
	 */
	private void updatePowerups(long delta) {
		updateBoost(delta);
		updateTripleShot(delta);
		updateShield(delta);
		updatePulse(delta);
	}
	
	/**
	 * Updates the boost of the ship.
	 * @param delta the time since the last update
	 */
	private void updateBoost(long delta) {
		if (hasBoost()) {
			boostTTL -= delta;
		} else {
			unboost();
		}
	}
	
	/**
	 * Updates the triple shot of the ship.
	 * @param delta the time since the last update
	 */
	private void updateTripleShot(long delta) {
		if (hasTripleShot()) {
			tripleShotTTL -= delta;
		} else {
			disarm();
		}
	}
	
	/**
	 * Updates the shield of the ship.
	 * @param delta the time since the last update
	 */
	private void updateShield(long delta) {
		if (hasShield()) {
			shieldTTL -= delta;
		} else {
			unshield();
		}
	}
	
	/**
	 * Updates the pulse of the ship.
	 * @param delta the time since the last update
	 */
	private void updatePulse(long delta) {
		if (hasPulse()) {
			pulseTTL -= delta;
		} else {
			pulseOff();
		}
	}

	/**
	 * Updates the angle of the ship.
	 * @param delta the time since the last update
	 */
	private void updateAngle(long delta) {
		double deltaAngle = 0;
		double angularSpeed = MIN_ANGULAR_SPEED * (1 - linearSpeed / (1.5 * getMaxLinearSpeed()));
		
		if (InputHandler.getInstance().getLeftKey().isPressed()) {
			// Turn CCW
			deltaAngle = -angularSpeed * delta;
		} else if (InputHandler.getInstance().getRightKey().isPressed()) {
			// Turn CW
			deltaAngle = angularSpeed * delta;
		}

		// Rotate each point by the change in the angle
		for (Point vertex : vertices) {
			vertex.rotate(getCenter(), deltaAngle);
		}

		// Update the angle
		angle = (angle - deltaAngle) % FULL_CIRCLE_RAD;
	}

	/**
	 * Updates the position of each vertex of the ship.
	 * @param delta the time since the last update
	 */
	private void updateVertices(long delta) {
		double distance = linearSpeed * delta;
		double dx = distance * Math.cos(angle);
		double dy = distance * Math.sin(angle);

		// Move center
		getCenter().move(dx, -dy);

		// Move vertices
		for (Point vertex : vertices) {
			vertex.move(dx, -dy);
		}
		
		getCenter().wrapAround(GameCanvas.WIDTH, GameCanvas.HEIGHT, vertices);
	}

	/**
	 * Initializes the vertices of the ship.
	 */
	private void initializeVertices() {
		this.vertices = new Point[3];
		this.vertices[0] = new Point(getX(), getY() - 10);
		this.vertices[1] = new Point(getX() - 5, getY() + 5);
		this.vertices[2] = new Point(getX() + 5, getY() + 5);
		
		updateBounds();
	}
	
	/**
	 * Updates the bounding shape of the ship
	 */
	private void updateBounds() {
		int[] x = new int[this.vertices.length];
		int[] y = new int[this.vertices.length];
		
		for (int i = 0; i < this.vertices.length; i++) {
			x[i] = (int) this.vertices[i].getX();
			y[i] = (int) this.vertices[i].getY();
		}
		
		setBounds(new Polygon(x, y, x.length));
	}
	
	/**
	 * Fires bullets.
	 */
	private void fireBullet() {
		for (BulletFiredListener listener : bulletFiredListeners) {
			if (hasPulse()) {
				for (int i = 0; i < NUM_PULSE_SHOTS; i++) {
					listener.bulletFired(new BulletFiredEvent(this, (Point) vertices[0].clone(), angle + i*PULSE_SHOTS_ANGLE));
				}
			} else {
				// Origin is the tip of the ship, the first vertex
				listener.bulletFired(new BulletFiredEvent(this, (Point) vertices[0].clone(), angle));
				
				if (hasTripleShot()) {
					listener.bulletFired(new BulletFiredEvent(this, (Point) vertices[0].clone(), angle + TRIPLE_SHOT_OFFSET_ANGLE));
					listener.bulletFired(new BulletFiredEvent(this, (Point) vertices[0].clone(), angle - TRIPLE_SHOT_OFFSET_ANGLE));
				}
			}
		}
	}
	
	/**
	 * The ship enters hyperspace and teleports to a random location.
	 */
	private void enterHyperspace() {
		setCenter(Point.getRandom(GameCanvas.WIDTH, GameCanvas.HEIGHT, getCenter(), 0));
		initializeVertices();
		
		for (Point vertex : vertices) {
			vertex.rotate(getCenter(), Math.PI/2 - angle);
		}
		
		for (HyperspaceListener l : hyperspaceListeners) {
			l.hyperspaceEntered();
		}
	}
}
