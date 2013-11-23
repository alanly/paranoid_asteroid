package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import game.BasicGameState;
import game.Game;
import game.Point;
import game.entities.Asteroid;
import game.entities.Ship;
import game.events.BulletFiredEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import game.enums.Size;

public class GameTest {
	private static final double DELTA = 1e-5;
	
	private Game game;
	private BasicGameState defaultState;
	private BasicGameState advancedState;
	private int level;
	private long points;
	private double multiplier;
	private BulletFiredEvent bulletFired;
	private Ship ship;
	private Point origin;
	
	@Before
	public void setUp() {
		level = 4;
		points = 1000;
		multiplier = 1.5;
		
		defaultState = mock(BasicGameState.class);
		when(defaultState.getLevel()).thenReturn(1);
		when(defaultState.getPoints()).thenReturn(0l);
		when(defaultState.getMultiplier()).thenReturn(1d);
		
		advancedState = mock(BasicGameState.class);
		when(advancedState.getLevel()).thenReturn(level);
		when(advancedState.getPoints()).thenReturn(points);
		when(advancedState.getMultiplier()).thenReturn(multiplier);
		
		game = new Game(defaultState);
		
		ship = mock(Ship.class);
		when(ship.getCenter()).thenReturn(mock(Point.class));
		
		origin = mock(Point.class);
		
		bulletFired = mock(BulletFiredEvent.class);
		when(bulletFired.getSource()).thenReturn(ship);
		when(bulletFired.getOrigin()).thenReturn(origin);
		when(bulletFired.getAngle()).thenReturn(0d);
		
	}
	
	@After
	public void tearDown() {
		game = null;
		defaultState = null;
		advancedState = null;
		bulletFired = null;
		ship = null;
		origin = null;
	}
	
	@Test
	public void testConstructor() {
		assertEquals(defaultState.getLevel(), game.getLevel());
		assertEquals(defaultState.getPoints(), game.getPoints());
		assertEquals(defaultState.getMultiplier(), game.getMultipliter(), DELTA);
	}
	
	@Test
	public void testConstructorWithAdvancedState() {
		game = new Game(advancedState);
		
		assertEquals(advancedState.getLevel(), game.getLevel());
		assertEquals(advancedState.getPoints(), game.getPoints());
		assertEquals(advancedState.getMultiplier(), game.getMultipliter(), DELTA);
	}
	
	@Test
	public void testPause() {
		game = new Game();
		assertFalse(game.isPaused());
		
		game.togglePause();
		assertTrue(game.isPaused());
	}
	
	@Test
	public void testBulletFired() {
		int bulletCount = game.getBullets().size();
		game.bulletFired(bulletFired);
		
		// One more bullet is added
		assertEquals(bulletCount + 1, game.getBullets().size());
	}
	
	@Test
	public void testEntityList() {
		assertNull(game.getShip());
		assertTrue(game.getAliens().isEmpty());
		assertTrue(game.getAsteroids().isEmpty());
		assertTrue(game.getBullets().isEmpty());
		assertTrue(game.getParticles().isEmpty());
		assertTrue(game.getPowerups().isEmpty());
	}
	
	@Test
	public void testEntityListAfterPopulating() {
		// Ensure loop doesn't start
		game.handleSave();
		game.start();
		assertFalse(game.getAsteroids().isEmpty());
	}
	
	@Test
	public void testSave() {
		assertFalse(game.isSaved());
		
		game.handleSave();
		assertTrue(game.isSaved());
	}
	
	@Test
	public void testNumAsteroids() {
		assertEquals(2, game.getNumAsteroidsForCurrentLevel());
		
		game = new Game(advancedState);
		assertEquals(3, game.getNumAsteroidsForCurrentLevel());
	}
	
	@Test
	public void testAsteroidSizes() {
		assertEquals(Size.SMALL, game.getAsteroidSizeForCurrentLevel());
		
		game = new Game(advancedState);
		assertEquals(Size.MEDIUM, game.getAsteroidSizeForCurrentLevel());
	}
	
	@Test
	public void testLevelWait() {
		// Game begins with no delay
		assertFalse(game.isWaitingForLevel());
	}
	
	@Test(timeout=1000)
	public void testNoLoopWhenSaved() {
		// Will time out after 1s if game loopp begins
		game.handleSave();
		game.start();
	}
	
	@Test
	public void testExtractState() {
		game = new Game(advancedState);
		BasicGameState extractedState = game.extractState();
		
		assertEquals(advancedState.getLevel(), extractedState.getLevel());
		assertEquals(advancedState.getPoints(), extractedState.getPoints());
		assertEquals(advancedState.getMultiplier(), extractedState.getMultiplier(), DELTA);
	}
}
