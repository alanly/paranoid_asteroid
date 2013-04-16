package unit;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import game.entities.Alien;
import game.entities.Asteroid;
import game.entities.Bullet;
import game.entities.Particle;
import game.entities.Powerup;
import game.entities.Ship;
import game.ui.Renderer;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RendererTest {
	private Graphics2D g;
	private Alien alien;
	private Asteroid asteroid;
	private Bullet bullet;
	private Particle particle;
	private Powerup powerup;
	private Ship ship;
	
	@Before
	public void setUp() {
		g = mock(Graphics2D.class);
		alien = mock(Alien.class);
		asteroid = mock(Asteroid.class);
		bullet = mock(Bullet.class);
		particle = mock(Particle.class);
		powerup = mock(Powerup.class);
		ship = mock(Ship.class);
	}
	
	@After
	public void tearDown() {
		g = null;
		alien = null;
		asteroid = null;
		bullet = null;
		particle = null;
		powerup = null;
		ship = null;
	}
	
	@Test
	public void testRenderAlien() {
		Renderer.render(alien, g);
		
		verify(g, times(1)).fill((Shape) anyObject());
		verify(g, times(1)).drawPolygon((Polygon) anyObject());
	}
	
	@Test
	public void testRenderAsteroid() {
		Renderer.render(asteroid, g);
		
		verify(g, times(1)).fill((Shape) anyObject());
		verify(g, times(1)).drawPolygon((Polygon) anyObject());
	}
	
	@Test
	public void testRenderBullet() {
		Renderer.render(bullet, g);
		
		verify(g, times(1)).fill((Shape) anyObject());
		verify(g, times(0)).drawPolygon((Polygon) anyObject());
	}
	
	@Test
	public void testRenderParticle() {
		Renderer.render(particle, g);
		
		verify(g, times(0)).fill((Shape) anyObject());
		verify(g, times(0)).drawPolygon((Polygon) anyObject());
		verify(g, times(1)).draw((Shape) anyObject());
	}
	
	@Test
	public void testRenderPowerup() {
		Renderer.render(powerup, g);
		
		verify(g, times(0)).fill((Shape) anyObject());
		verify(g, times(0)).drawPolygon((Polygon) anyObject());
		verify(g, times(1)).draw((Shape) anyObject());
	}
	
	@Test
	public void testShipColor() {
		Renderer.render(ship, g);
		
		verify(g, times(1)).setColor(Renderer.SHIP_COLOR);
	}
	
	@Test
	public void testShipColorWithPulse() {
		when(ship.hasPulse()).thenReturn(true);
		Renderer.render(ship, g);
		
		verify(g, times(1)).setColor(Renderer.PULSE_COLOR);
	}
	
	@Test
	public void testShipColorWithTripleShot() {
		when(ship.hasTripleShot()).thenReturn(true);
		Renderer.render(ship, g);
		
		verify(g, times(1)).setColor(Renderer.TRIPLE_SHOT_COLOR);
	}
	
	@Test
	public void testShipColorWithBoost() {
		when(ship.hasBoost()).thenReturn(true);
		Renderer.render(ship, g);
		
		verify(g, times(1)).setColor(Renderer.BOOST_COLOR);
	}
	

	@Test
	public void testShipColorWithShield() {
		when(ship.hasShield()).thenReturn(true);
		Renderer.render(ship, g);
		
		// Color is set once for the ship and once for the shield
		verify(g, times(2)).setColor(Renderer.SHIELD_COLOR);
	}
	
	@Test
	public void testShipColorWithShieldAndPulse() {
		when(ship.hasPulse()).thenReturn(true);
		when(ship.hasShield()).thenReturn(true);
		Renderer.render(ship, g);
		
		// Color is set ship
		verify(g, times(1)).setColor(Renderer.PULSE_COLOR);
		
		// Color is set for shield
		verify(g, times(1)).setColor(Renderer.SHIELD_COLOR);
	}
	
	@Test
	public void testShipColorWithShieldAndTripleShot() {
		when(ship.hasTripleShot()).thenReturn(true);
		when(ship.hasShield()).thenReturn(true);
		Renderer.render(ship, g);
		
		// Color is set ship
		verify(g, times(1)).setColor(Renderer.TRIPLE_SHOT_COLOR);
		
		// Color is set for shield
		verify(g, times(1)).setColor(Renderer.SHIELD_COLOR);
	}
	
	@Test
	public void testShipColorWithShieldAndBoost() {
		when(ship.hasBoost()).thenReturn(true);
		when(ship.hasShield()).thenReturn(true);
		Renderer.render(ship, g);
		
		// Color is set ship
		verify(g, times(1)).setColor(Renderer.BOOST_COLOR);
		
		// Color is set for shield
		verify(g, times(1)).setColor(Renderer.SHIELD_COLOR);
	}
	
	@Test
	public void testShieldPowerupColor() {
		when(powerup.getType()).thenReturn(Powerup.Power.SHIELD);
		Renderer.render(powerup, g);
		
		verify(g, times(1)).setColor(Renderer.SHIELD_COLOR);
	}
	
	@Test
	public void testPulsePowerupColor() {
		when(powerup.getType()).thenReturn(Powerup.Power.PULSE);
		Renderer.render(powerup, g);
		
		verify(g, times(1)).setColor(Renderer.PULSE_COLOR);
	}
	
	@Test
	public void testTripleShotPowerupColor() {
		when(powerup.getType()).thenReturn(Powerup.Power.TRIPLE_SHOT);
		Renderer.render(powerup, g);
		
		verify(g, times(1)).setColor(Renderer.TRIPLE_SHOT_COLOR);
	}
	
	@Test
	public void testBoostPowerupColor() {
		when(powerup.getType()).thenReturn(Powerup.Power.BOOST);
		Renderer.render(powerup, g);
		
		verify(g, times(1)).setColor(Renderer.BOOST_COLOR);
	}
}
