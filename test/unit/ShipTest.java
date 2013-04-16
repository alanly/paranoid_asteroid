package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import game.Point;
import game.entities.Ship;
import game.events.BulletFiredEvent;
import game.events.BulletFiredListener;
import game.events.HyperspaceListener;
import io.InputHandler;

import java.awt.Shape;
import java.awt.event.KeyEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShipTest {
	private BulletFiredListener bulletListener;
	private HyperspaceListener hyperspaceListener;
	private Point center;
	private Ship ship;
	
	@Before
	public void setUp() {
		bulletListener = mock(BulletFiredListener.class);
		doNothing().when(bulletListener).bulletFired((BulletFiredEvent) anyObject());
		
		hyperspaceListener = mock(HyperspaceListener.class);
		doNothing().when(hyperspaceListener).hyperspaceEntered();
		
		center = mock(Point.class);
		ship = new Ship(center);
		
		ship.addBulletFiredListener(bulletListener);
		ship.addHyperspaceListener(hyperspaceListener);
	}
	
	@After
	public void tearDown() {
		InputHandler.getInstance().reset();
		
		bulletListener = null;
		hyperspaceListener = null;
		
		center = null;
		ship = null;
	}
	
	@Test
	public void testBulletListener() {
		ship.fireBullet();
		verify(bulletListener, times(1)).bulletFired((BulletFiredEvent) anyObject());
	}
	
	@Test
	public void testBulletListenerWhenArmed() {
		ship.arm();
		ship.fireBullet();
		verify(bulletListener, times(3)).bulletFired((BulletFiredEvent) anyObject());
	}
	
	@Test
	public void testBulletListenerWhenPulseOn() {
		ship.pulseOn();
		ship.fireBullet();
		verify(bulletListener, times(5)).bulletFired((BulletFiredEvent) anyObject());
	}
	
	@Test
	public void testBulletListenerWhenArmedAndPulseOn() {
		ship.arm();
		ship.pulseOn();
		ship.fireBullet();
		
		// Pulse takes priority
		verify(bulletListener, times(5)).bulletFired((BulletFiredEvent) anyObject());
	}
	
	@Test
	public void testArm() {
		assertFalse(ship.hasTripleShot());
		ship.arm();
		assertTrue(ship.hasTripleShot());
		ship.update(Ship.MAX_POWERUP_TTL);
		assertFalse(ship.hasTripleShot());
	}
	
	@Test
	public void testBoost() {
		assertFalse(ship.hasBoost());
		ship.boost();
		assertTrue(ship.hasBoost());
		ship.update(Ship.MAX_POWERUP_TTL);
		assertFalse(ship.hasBoost());
	}
	
	@Test
	public void testCanEnterHyperSpace() {
		assertFalse(ship.canEnterHyperspace());
		
		ship.update(Ship.MAX_HYPER_WAIT);
		assertTrue(ship.canEnterHyperspace());
		
		ship.enterHyperspace();
		assertFalse(ship.canEnterHyperspace());
	}
	
	@Test
	public void testCanFire() {
		assertFalse(ship.canFire());
		
		// Must wait longer (greater) than the wait time
		ship.update(Ship.MAX_BULLET_FIRED_WAIT);
		assertFalse(ship.canFire());
		
		ship.update(1);
		assertTrue(ship.canFire());
		
		ship.fireBullet();
		assertFalse(ship.canFire());
	}
	
	@Test
	public void testCanFireWithBoost() {
		ship.boost();
		assertFalse(ship.canFire());
		
		// Can fire sooner when on boost
		ship.update(Ship.MAX_BULLET_FIRED_WAIT);
		assertTrue(ship.canFire());
		
		ship.fireBullet();
		assertFalse(ship.canFire());
	}
	
	@Test
	public void testDie() {
		assertTrue(ship.isAlive());
		ship.die();
		assertFalse(ship.isAlive());
	}
	
	@Test
	public void testDieWhileShieldedShield() {
		assertTrue(ship.isAlive());
		ship.shield();
		ship.die();
		assertTrue(ship.isAlive());
		ship.die();
		assertFalse(ship.isAlive());
	}
	
	@Test
	public void testDisarm() {
		assertFalse(ship.hasTripleShot());
		ship.arm();
		assertTrue(ship.hasTripleShot());
		ship.disarm();
		assertFalse(ship.hasTripleShot());
	}
	
	@Test
	public void testEnterHyperspace() {
		Point oldCenter = ship.getCenter();
		ship.enterHyperspace();
		
		assertNotSame(oldCenter, ship.getCenter());
	}
	
	@Test
	public void testGetBulletFiredWait() {
		double noBoostWait = ship.getBulletFiredWait();
		ship.boost();
		double boostWait = ship.getBulletFiredWait();
		
		// Less wait when on boost
		assertTrue(boostWait < noBoostWait);
	}
	
	@Test
	public void testGetMaxLinearSpeed() {
		double noBoostSpeed = ship.getMaxLinearSpeed();
		ship.boost();
		double boostSpeed = ship.getMaxLinearSpeed();
		
		// Ship has a higher max speed with boost
		assertTrue(boostSpeed > noBoostSpeed);
	}
	
	@Test
	public void testHasBoost() {
		assertFalse(ship.hasBoost());
		ship.boost();
		assertTrue(ship.hasBoost());
	}
	
	@Test
	public void testHasTripleShot() {
		assertFalse(ship.hasTripleShot());
		ship.arm();
		assertTrue(ship.hasTripleShot());
	}
	
	@Test
	public void testHasShield() {
		assertFalse(ship.hasShield());
		ship.shield();
		assertTrue(ship.hasShield());
		ship.die();
		assertFalse(ship.hasShield());
	}
	
	@Test
	public void testHasPulse() {
		assertFalse(ship.hasPulse());
		ship.pulseOn();
		assertTrue(ship.hasPulse());
	}
	
	@Test
	public void testIsAccelerating() {
		assertFalse(ship.isAccelerating());
		accelerateShip();
		assertTrue(ship.isAccelerating());
	}
	
	@Test
	public void testShipMoves() {
		Shape oldBounds = ship.getBounds();
		ship.update(1000);
		
		// Move should be called once per update
		verify(center, times(1)).move(anyDouble(), anyDouble());
		
		// New bounds are created after the update
		assertNotSame(oldBounds, ship.getBounds());
	}
	
	public void accelerateShip() {
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_UP);
		InputHandler.getInstance().keyPressed(e);
	}
}
