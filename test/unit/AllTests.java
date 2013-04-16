package unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AlienTest.class, AsteroidTest.class, BasicGameStateTest.class,
		BulletFiredEventTest.class, BulletTest.class, EntityTest.class,
		GameTest.class, InputHandlerTest.class, ParticleTest.class,
		PointTest.class, PowerupTest.class, RendererTest.class, ShipTest.class })
public class AllTests {

}
