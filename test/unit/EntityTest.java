package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;
import game.Point;
import game.entities.Entity;

import java.awt.Shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntityTest {
	private double DELTA = 1e-5;
	private Shape bounds;
	private Point center;
	private Entity entity;
	private double x;
	private double y;
	
	@Before
	public void setUp() {
		x = 1;
		y = 2;
		
		bounds = mock(Shape.class);
		
		center = mock(Point.class);
		when(center.getX()).thenReturn(x);
		when(center.getY()).thenReturn(y);
		
		entity = new Entity() {
			public void update(long delta) {}
		};
		
		entity.setBounds(bounds);
		entity.setCenter(center);
	}
	
	@After
	public void tearDown() {
		bounds = null;
		center = null;
		entity = null;
	}
	
	@Test
	public void testGetBounds() {
		assertSame(bounds, entity.getBounds());
	}
	
	@Test
	public void testGetCenter() {
		assertSame(center, entity.getCenter());
	}
	
	@Test
	public void testGetX() {
		assertEquals(x, entity.getX(), DELTA);
		verify(center, times(1)).getX();
	}
	
	@Test
	public void testGetY() {
		assertEquals(y, entity.getY(), DELTA);
		verify(center, times(1)).getY();
	}
}
