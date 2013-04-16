package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import game.Point;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PointTest {
	private static final double DELTA = 1e-15;
	
	private final double xMax = 10;
	private final double yMax = 10;
	private final double x = 2.0;
	private final double y = 7.0;
	private Point point;
	private Point pointPoint;
	private Point pointEdge;
	
	@Before
	public void setUp() {
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
	
	@Test
	public void testWrapAroundTopLeft() {
		point.move(-3, -8);
		point.wrapAround(xMax, yMax);
		
		assertEquals(xMax - 1, point.x, DELTA);
		assertEquals(yMax - 1, point.y, DELTA);
	}
	
	@Test
	public void testRotate() {
		Point center = mock(Point.class);
		when(center.getX()).thenReturn(0d);
		when(center.getY()).thenReturn(0d);
		
		// Rotate 180 deg around (0, 0)
		point.rotate(center, Math.PI);
		
		assertEquals(-x, point.x, DELTA);
		assertEquals(-y, point.y, DELTA);
	}
	
	@Test
	public void testRotateNegativeAngle() {
		Point center = mock(Point.class);
		when(center.getX()).thenReturn(0d);
		when(center.getY()).thenReturn(0d);
		
		// Rotate -90 deg around (0, 0)
		point.rotate(center, - Math.PI / 2);
		
		assertEquals(y, point.x, DELTA);
		assertEquals(-x, point.y, DELTA);
	}
	
	@Test
	public void testRotateZero() {
		Point center = mock(Point.class);
		when(center.getX()).thenReturn(0d);
		when(center.getY()).thenReturn(0d);
		
		// Rotate 0 deg around (0, 0)
		point.rotate(center, 0);
		
		assertEquals(x, point.x, DELTA);
		assertEquals(y, point.y, DELTA);
	}
	
	@Test
	public void testWrapAroundWithPoints() {
		Point[] points = new Point[3];
		
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point(x + i, y + i);
		}
		
		point.move(0, 4);
		point.wrapAround(xMax, yMax, points);
		
		for (int i = 0; i < points.length; i++) {
			assertEquals(x + i, points[i].x, DELTA);
			assertEquals(y + i - 10, points[i].y, DELTA);
		}
	}
	
	@Test
	public void testRandomPoint() {
		Point p = mock(Point.class);
		when(p.getX()).thenReturn(5d);
		when(p.getY()).thenReturn(5d);
		
		Point.getRandom((int)xMax, (int)yMax, p, 1);
		
		assertTrue(Math.sqrt((p.getX() - x)*(p.getX() - x) + (p.getY() - y)*(p.getY() - y)) > 1);
	}
}
