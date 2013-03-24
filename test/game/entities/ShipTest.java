package game.entities;

import static org.junit.Assert.assertEquals;
import game.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShipTest {
	double DELTA = 1e-15;
	double x = 4.0;
	double y = 5.0;
	Ship ship;
	Ship shipPoint;
	Point center;
	
	@Before
	public void setUp() {
		center = new Point(x, y);
		ship = new Ship(x, y);
		shipPoint = new Ship(center);
	}
	
	@After
	public void tearDown() {
		center = null;
		ship = null;
		shipPoint = null;
	}
	
	@Test
	public void testConstructorSetsDoubleArgs() {
		assertEquals(x, ship.getCenter().getX(), DELTA);
		assertEquals(y, ship.getCenter().getY(), DELTA);
	}
	
	@Test
	public void testConstructorSetsPointArg() {
		assertEquals(x, shipPoint.getCenter().getX(), DELTA);
		assertEquals(y, shipPoint.getCenter().getY(), DELTA);
	}
}
