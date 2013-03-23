package game;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PointTest {
	double DELTA = 1e-15;
	private double x;
	private double y;
	private double xMax = 10;
	private double yMax = 10;
	private Point point;
	private Point pointPoint;
	private Point pointEdge;
	
	@Before
	public void setUp() {
		x = 2.0;
		y = 7.0;
		
		point = new Point(x, y);
		pointPoint = new Point(new Point2D.Double(x, y));
		pointEdge = new Point(xMax, yMax);
	}
	
	@After
	public void tearDown() {
		point = null;
		pointPoint = null;
		pointEdge = null;
	}
	
	@Test
	public void testContructorSetsDoubleArgs() {
		assertEquals(x, point.x, DELTA);
		assertEquals(y, point.y, DELTA);
	}
	
	@Test
	public void testConstructorSetsPointArg() {
		assertEquals(x, pointPoint.x, DELTA);
		assertEquals(y, pointPoint.y, DELTA);
	}
	
	@Test
	public void testMove() {
		point.move(1, 1);
		assertEquals(x + 1, point.x, DELTA);
		assertEquals(y + 1, point.y, DELTA);
	}
	
	@Test
	public void testWrapAround() {
		point.move(1, 1);
		pointEdge.move(1, 1);
		
		point.wrapAround(xMax, yMax);
		pointEdge.wrapAround(xMax, yMax);
		
		assertEquals(x + 1, point.x, DELTA);
		assertEquals(y + 1, point.y, DELTA);
		
		assertEquals(1, pointEdge.x, DELTA);
		assertEquals(1, pointEdge.y, DELTA);
	}
	
	@Test
	public void testMoveWithWrapAround() {
		point.move(1, 1, xMax, yMax);
		pointEdge.move(1, 1, xMax, yMax);
		
		assertEquals(x + 1, point.x, DELTA);
		assertEquals(y + 1, point.y, DELTA);
		
		assertEquals(1, pointEdge.x, DELTA);
		assertEquals(1, pointEdge.y, DELTA);
	}
}
