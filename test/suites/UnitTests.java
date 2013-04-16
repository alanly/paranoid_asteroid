package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import unit.AlienTest;
import unit.AsteroidTest;
import unit.BasicGameStateTest;
import unit.BulletFiredEventTest;
import unit.BulletTest;
import unit.EntityTest;
import unit.ParticleTest;
import unit.PointTest;
import unit.PowerupTest;
import unit.RendererTest;
import unit.ShipTest;

@RunWith(Suite.class)
@SuiteClasses({ AlienTest.class, AsteroidTest.class, BasicGameStateTest.class,
		BulletFiredEventTest.class, BulletTest.class, EntityTest.class,
		ParticleTest.class, PointTest.class, PowerupTest.class,
		RendererTest.class, ShipTest.class })
public class UnitTests {

}
