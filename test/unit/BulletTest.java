package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import game.Point;
import game.entities.Alien;
import game.entities.Bullet;
import game.entities.Ship;

import java.awt.Rectangle;
import java.awt.Shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BulletTest {
	private Ship source;
	private Point center;
	private Bullet bullet;
	
	@Before
	public void setUp() {
		source = mock(Ship.class);
		center = mock(Point.class);
		bullet = new Bullet(source, center, 0.0);
	}
	
	@After
	public void tearDown() {
		source = null;
		center = null;
		bullet = null;
	}

	@Test
	public void testBulletSource() {
		assertSame(source, bullet.getSource());
	}
	
	@Test
	public void testBounds() {
		assertTrue(bullet.getBounds() instanceof Rectangle);
	}
	
	@Test
	public void testBulletMoves() {
		Shape oldBounds = bullet.getBounds();
		bullet.update(1000);
		
		// Move should be called once per update
		verify(center, times(1)).move(anyDouble(), anyDouble());
		
		// The bounds co-ordinates are updated
		assertSame(oldBounds, bullet.getBounds());
	}
	
	@Test
	public void testIsExpired() {
		// Not expired, just created
		assertFalse(bullet.isExpired());
		
		// Reached half of lifetime, not yet expired
		bullet.update((long) Bullet.MAX_TIME_TO_LIVE / 2);
		assertFalse(bullet.isExpired());
		
		// 1 lifetime, not yet expired (must pass one full lifetime)
		bullet.update((long) Bullet.MAX_TIME_TO_LIVE / 2);
		assertFalse(bullet.isExpired());
		
		// Over 1 lifetime, must be expired
		bullet.update(1);
		assertTrue(bullet.isExpired());
		
		// Much later
		bullet.update((long) Bullet.MAX_TIME_TO_LIVE * 10);
		assertTrue(bullet.isExpired());
	}
	
	@Test
	public void testIsExpiredFromAlien() {
		bullet = new Bullet(mock(Alien.class), center, 0.0);
		
		// Not expired, just created
		assertFalse(bullet.isExpired());
		
		// Reached half of lifetime, not yet expired
		bullet.update((long) (Bullet.MAX_TIME_TO_LIVE * Bullet.ALIEN_TIME_TO_LIVE_MULTIPLIER / 2));
		assertFalse(bullet.isExpired());
		
		// 1 lifetime, not yet expired (must pass one full lifetime)
		bullet.update((long) (Bullet.MAX_TIME_TO_LIVE * Bullet.ALIEN_TIME_TO_LIVE_MULTIPLIER / 2));
		assertFalse(bullet.isExpired());
		
		// Over 1 lifetime, must be expired
		bullet.update(1);
		assertTrue(bullet.isExpired());
		
		// Much later
		bullet.update((long) (Bullet.MAX_TIME_TO_LIVE * Bullet.ALIEN_TIME_TO_LIVE_MULTIPLIER * 10));
		assertTrue(bullet.isExpired());
	}
}
