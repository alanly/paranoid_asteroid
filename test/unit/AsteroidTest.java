package unit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import game.Point;
import game.entities.*;

import java.awt.Shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import game.enums.Size;

public class AsteroidTest {
	private Point center;
	private Asteroid asteroid;
	
	@Before
	public void setUp() {
		center = mock(Point.class);
		asteroid = EntityFactory.getInstance().makeAsteroid(Size.SMALL, center);
	}
	
	@After
	public void tearDown() {
		center = null;
		asteroid = null;
	}

	@Test
	public void testAsteroidBuilder() {
		Point center = mock(Point.class);
		
		Asteroid small = EntityFactory.getInstance().makeAsteroid(Size.SMALL, center);
		Asteroid medium = EntityFactory.getInstance().makeAsteroid(Size.MEDIUM, center);
		Asteroid large = EntityFactory.getInstance().makeAsteroid(Size.LARGE, center);
		
		assertTrue(small instanceof SmallAsteroid);
		assertTrue(medium instanceof MediumAsteroid);
		assertTrue(large instanceof LargeAsteroid);
	}
	
	@Test
	public void testGetSmaller() {
		assertTrue(Size.SMALL == Size.SMALL.getSmaller());
		assertTrue(Size.SMALL == Size.MEDIUM.getSmaller());
		assertTrue(Size.MEDIUM == Size.LARGE.getSmaller());
	}
	
	@Test
	public void testAsteroidMoves() {
		Shape oldBounds = asteroid.getBounds();
		asteroid.update(1000);
		
		// Move should be called once per update
		verify(center, times(1)).move(anyDouble(), anyDouble());
		
		// New bounds are created after the update
		assertNotSame(oldBounds, asteroid.getBounds());
	}
	
	@Test
	public void testGetSize() {
		Asteroid small = EntityFactory.getInstance().makeAsteroid(Size.SMALL, center);
		Asteroid medium = EntityFactory.getInstance().makeAsteroid(Size.MEDIUM, center);
		Asteroid large =EntityFactory.getInstance().makeAsteroid(Size.LARGE, center);
		
		assertTrue(small.getSize() == Size.SMALL);
		assertTrue(medium.getSize() == Size.MEDIUM);
		assertTrue(large.getSize() == Size.LARGE);
	}
}
