package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import unit.AlienTest;
import unit.AsteroidTest;
import unit.BasicGameStateTest;
import unit.BulletTest;
import unit.EntityTest;
import unit.ParticleTest;
import unit.PointTest;
import unit.PowerupTest;
import unit.ShipTest;

@RunWith(Suite.class)
@SuiteClasses({ AlienTest.class, AsteroidTest.class, BasicGameStateTest.class,
		BulletTest.class, EntityTest.class, ParticleTest.class,
		PointTest.class, PowerupTest.class, ShipTest.class })
public class UnitTests {

}
