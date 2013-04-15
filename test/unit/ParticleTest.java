package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import game.Point;
import game.entities.Particle;

import java.awt.Rectangle;
import java.awt.Shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParticleTest {
	private Point center;
	private Particle particle;
	
	@Before
	public void setUp() {
		center = mock(Point.class);
		doNothing().when(center).move(anyDouble(), anyDouble());
		when(center.clone()).thenReturn(center);
		particle = new Particle(center);
	}
	
	@After
	public void tearDown() {
		center = null;
		particle = null;
	}
	
	@Test
	public void testBounds() {
		assertTrue(particle.getBounds() instanceof Rectangle);
	}
	
	@Test
	public void testBulletMoves() {
		Shape oldBounds = particle.getBounds();
		particle.update(1000);
		
		// Move should be called once per update
		verify(center, times(1)).move(anyDouble(), anyDouble());
		
		// The bounds co-ordinates are updated
		assertSame(oldBounds, particle.getBounds());
	}
	
	@Test
	public void testIsExpired() {
		// Not expired, just created
		assertFalse(particle.isExpired());
		
		// Reached half of lifetime, not yet expired
		particle.update((long) Particle.MAX_TIME_TO_LIVE / 2);
		assertFalse(particle.isExpired());
		
		// 1 lifetime, not yet expired (must pass one full lifetime)
		particle.update((long) Particle.MAX_TIME_TO_LIVE / 2);
		assertFalse(particle.isExpired());
		
		// Over 1 lifetime, must be expired
		particle.update(1);
		assertTrue(particle.isExpired());
		
		// Much later
		particle.update((long) Particle.MAX_TIME_TO_LIVE * 10);
		assertTrue(particle.isExpired());
	}
}
