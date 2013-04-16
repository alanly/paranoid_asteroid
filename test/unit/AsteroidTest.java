package unit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import game.Point;
import game.entities.Asteroid;
import game.entities.LargeAsteroid;
import game.entities.MediumAsteroid;
import game.entities.SmallAsteroid;

import java.awt.Shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AsteroidTest {
	private Point center;
	private Asteroid asteroid;
	
	@Before
	public void setUp() {
		center = mock(Point.class);
		asteroid = Asteroid.buildAsteroid(Asteroid.Size.SMALL, center);
	}
	
	@After
	public void tearDown() {
		center = null;
		asteroid = null;
	}

	@Test
	public void testAsteroidBuilder() {
		Point center = mock(Point.class);
		
		Asteroid small = Asteroid.buildAsteroid(Asteroid.Size.SMALL, center);
		Asteroid medium = Asteroid.buildAsteroid(Asteroid.Size.MEDIUM, center);
		Asteroid large = Asteroid.buildAsteroid(Asteroid.Size.LARGE, center);
		
		assertTrue(small instanceof SmallAsteroid);
		assertTrue(medium instanceof MediumAsteroid);
		assertTrue(large instanceof LargeAsteroid);
	}
	
	@Test
	public void testGetSmaller() {
		assertTrue(Asteroid.Size.SMALL == Asteroid.Size.SMALL.getSmaller());
		assertTrue(Asteroid.Size.SMALL == Asteroid.Size.MEDIUM.getSmaller());
		assertTrue(Asteroid.Size.MEDIUM == Asteroid.Size.LARGE.getSmaller());
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
		Asteroid small = Asteroid.buildAsteroid(Asteroid.Size.SMALL, center);
		Asteroid medium = Asteroid.buildAsteroid(Asteroid.Size.MEDIUM, center);
		Asteroid large = Asteroid.buildAsteroid(Asteroid.Size.LARGE, center);
		
		assertTrue(small.getSize() == Asteroid.Size.SMALL);
		assertTrue(medium.getSize() == Asteroid.Size.MEDIUM);
		assertTrue(large.getSize() == Asteroid.Size.LARGE);
	}
}
