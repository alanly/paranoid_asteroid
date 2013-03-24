package game.entities;

import game.Point;

public class Ship extends Entity {
	public static final double MAX_SPEED = 300.0;
	public static final double ACCELERATION = 100.0;
	
	public Ship(double x, double y) {
		this.setCenter(new Point(x, y));
	}
	
	public Ship(Point center) {
		this.setCenter(new Point(center.x, center.y));
		System.out.println(this.getCenter());
	}
	
	@Override
	public void update(long delta) {
		
	}
}
