package unit;

import static org.junit.Assert.assertEquals;
import game.BasicGameState;

import org.junit.Test;

public class BasicGameStateTest {
	private static final double DELTA = 1e-5;
	
	@Test
	public void testDefaultConstructor() {
		BasicGameState state = new BasicGameState();
		
		assertEquals(1, state.getLevel());
		assertEquals(0, state.getPoints());
		assertEquals(1, state.getMultiplier(), DELTA);
	}
	
	@Test
	public void testConstructorWithArgs() {
		int level = 10;
		long points = 1000;
		double multiplier = 2.0;
		
		BasicGameState state = new BasicGameState(level, points, multiplier);
		
		assertEquals(level, state.getLevel());
		assertEquals(points, state.getPoints());
		assertEquals(multiplier, state.getMultiplier(), DELTA);
	}
}
