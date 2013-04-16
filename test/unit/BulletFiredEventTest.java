package unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import game.Point;
import game.entities.Entity;
import game.events.BulletFiredEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BulletFiredEventTest {
	private static final double DELTA = 1e-15;
	
	private Entity source;
	private Point origin;
	private double angle;
	private BulletFiredEvent e;
	
	@Before
	public void setUp() {
		source = mock(Entity.class);
		origin = mock(Point.class);
		angle = 0.0;
		
		e = new BulletFiredEvent(source, origin, angle);
	}
	
	@After
	public void tearDown() {
		source = null;
		origin = null;
	}
	
	
	@Test
	public void testConstructor() {
		assertSame(source, e.getSource());
		assertSame(origin, e.getOrigin());
		assertEquals(angle, e.getAngle(), DELTA);
	}
}
