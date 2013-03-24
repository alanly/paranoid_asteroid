package game;

import game.entities.Entity;

import java.awt.Canvas;
import java.util.ArrayList;
import java.util.List;

public class GameField extends Canvas {
	private static final long serialVersionUID = 1L;
	private static final long NANOS_IN_SECOND = 1000000000;
	private static final double FPS = 30.0;
	private static final double NSPF = NANOS_IN_SECOND / FPS;
	
	private boolean alive;
	private boolean paused;
	private List<Entity> entities;
	
	public GameField() {
		alive = true;
		paused = false;
		entities = new ArrayList<Entity>();
		
		this.addKeyListener(InputHandler.getInstance());
	}
	
	public void start() {
		long delta, now, lastLoop = System.nanoTime();
		long lastFrame = 0;
		long lastSecond = 0;
		
		while(alive && !paused) {
			// Adjust counters
			now = System.nanoTime();
			delta = now - lastLoop;
			lastLoop = now;
			
			lastFrame += delta;
			lastSecond += delta;
			
			// Process every loop
			update(delta);
			
			// Process once for every frame in a second
			if (lastFrame >= NSPF) {
				lastFrame = 0;
			}
			
			// Process once per second
			if (lastSecond >= NANOS_IN_SECOND) {
				lastSecond = 0;
			}
		}
	}
	
	private void update(long delta) {
		for (Entity e : entities) {
			e.update(delta);
		}
	}
}
