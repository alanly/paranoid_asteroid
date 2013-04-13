package game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasicGameStateTest {
	double DELTA = 1e-5;
	
	@Test
	public void testDefaultConstructor() {
		BasicGameState state = new BasicGameState();
		
		assertEquals(1, state.getLevel());
		assertEquals(0, state.getPoints());
		assertEquals(1, state.getMultiplier(), DELTA);
	}
}
