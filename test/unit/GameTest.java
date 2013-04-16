package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import game.BasicGameState;
import game.Game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
	private double DELTA = 1e-5;
	private Game game;
	private BasicGameState state;
	
	@Before
	public void setUp() {
		
		state = mock(BasicGameState.class);
		game = new Game(state);
		
	}
	
	@After
	public void tearDown() {
		game = null;
		state = null;
	}
	
	@Test
	public void pauseTest() {
		assertFalse(game.isPaused());
		game.togglePause();
		assertTrue(game.isPaused());
	}
	
	@Test
	public void stateVariableTest() {
		assertEquals(state.getLevel(),game.getLevel());
		assertEquals(state.getMultiplier(),game.getMultipliter(), DELTA);
		assertEquals(state.getPoints(),game.getPoints());
		
	}
	
	@Test
	public void entityListTest() {
		assertTrue(game.getAliens().isEmpty());
		assertTrue(game.getBullets().isEmpty());
		assertTrue(game.getAsteroids().isEmpty());
		assertTrue(game.getPowerups().isEmpty());
		assertTrue(game.getParticles().isEmpty());
	}
	
	@Test
	public void levelWaitTest() {
		assertFalse(game.isWaitingForLevel());
	}
	
	@Test
	public void shipTest() {
		assertNull(game.getShip());
	}
	
	/* test fails
	@Test
	public void stateTest() {
		assertEquals(state,game.extractState());
	}
	*/

}
