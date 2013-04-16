package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import game.Point;
import game.entities.Alien;
import game.entities.Ship;
import game.events.BulletFiredEvent;
import game.events.BulletFiredListener;

import java.awt.Shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AlienTest {
	private BulletFiredListener bulletListener;
	private Point center;
	private Ship target;
	private Alien alien;
	
	@Before
	public void setUp() {
		bulletListener = mock(BulletFiredListener.class);
		doNothing().when(bulletListener).bulletFired((BulletFiredEvent) anyObject());
		
		center = mock(Point.class);
		target = mock(Ship.class);
		
		alien = new Alien(center, target);
		alien.addBulletFiredListener(bulletListener);
	}
	
	@After
	public void tearDown() {
		bulletListener = null;
		center = null;
		target = null;
		alien = null;
	}
	
	@Test public void testCanFire() {
		assertFalse(alien.canFire());
		
		// Must wait longer (greater) than the wait time
		alien.update(Alien.BULLET_FIRED_WAIT);
		assertFalse(alien.canFire());
		
		alien.update(1);
		// Bullet is fired immediately on update
		assertFalse(alien.canFire());
	}
	
	@Test
	public void testFireBullet() {
		alien.fireBullet();
		verify(bulletListener, times(1)).bulletFired((BulletFiredEvent) anyObject());
	}
	
	@Test
	public void testAlienMoves() {
		Shape oldBounds = alien.getBounds();
		alien.update(1000);
		
		// Move should be called once per update
		verify(center, times(1)).move(anyDouble(), anyDouble());
		
		// New bounds are created after the update
		assertNotSame(oldBounds, alien.getBounds());
	}
}
