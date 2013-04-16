package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import game.Point;
import game.entities.Powerup;

import java.awt.geom.Ellipse2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PowerupTest {
	private Point center;
	private Powerup powerup;
	
	@Before
	public void setUp() {
		center = mock(Point.class);
		doNothing().when(center).move(anyDouble(), anyDouble());
		when(center.clone()).thenReturn(center);
		powerup = new Powerup(center);
	}
	
	@After
	public void tearDown() {
		center = null;
		powerup = null;
	}
	
	@Test
	public void testBounds() {
		assertTrue(powerup.getBounds() instanceof Ellipse2D);
	}
	
	@Test
	public void testPowerupDoesNotMove() {
		powerup.update(1000);
		
		// Move should never be called
		verify(center, times(0)).move(anyDouble(), anyDouble());
	}
	
	@Test
	public void testIsExpired() {
		// Not expired, just created
		assertFalse(powerup.isExpired());
		
		// Reached half of lifetime, not yet expired
		powerup.update((long) Powerup.MAX_TIME_TO_LIVE / 2);
		assertFalse(powerup.isExpired());
		
		// 1 lifetime, not yet expired (must pass one full lifetime)
		powerup.update((long) Powerup.MAX_TIME_TO_LIVE / 2);
		assertFalse(powerup.isExpired());
		
		// Over 1 lifetime, must be expired
		powerup.update(1);
		assertTrue(powerup.isExpired());
		
		// Much later
		powerup.update((long) Powerup.MAX_TIME_TO_LIVE * 10);
		assertTrue(powerup.isExpired());
	}
	
	@Test
	public void getType() {
		Powerup boost = new Powerup(center, Powerup.Power.BOOST);
		Powerup pulse = new Powerup(center, Powerup.Power.PULSE);
		Powerup shield = new Powerup(center, Powerup.Power.SHIELD);
		Powerup tripleShot = new Powerup(center, Powerup.Power.TRIPLE_SHOT);
		
		assertTrue(Powerup.Power.BOOST == boost.getType());
		assertTrue(Powerup.Power.PULSE == pulse.getType());
		assertTrue(Powerup.Power.SHIELD == shield.getType());
		assertTrue(Powerup.Power.TRIPLE_SHOT == tripleShot.getType());
	}
}
