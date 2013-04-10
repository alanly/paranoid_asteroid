package io;

import game.PauseHandler;
import game.SaveHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

public class InputHandler implements KeyListener {
	private static InputHandler instance = new InputHandler();

	private Key up = new Key();
	private Key down = new Key();
	private Key left = new Key();
	private Key right = new Key();
	private Key space = new Key();
	private List<PauseHandler> pauseHandlers;
	private List<SaveHandler> saveHandlers;

	public static InputHandler getInstance() {
		return instance;
	}

	public Key getUpKey() {
		return up;
	}

	public Key getDownKey() {
		return down;
	}

	public Key getLeftKey() {
		return left;
	}

	public Key getRightKey() {
		return right;
	}

	public Key getSpaceKey() {
		return space;
	}
	
	public void reset() {
		up.toggle(false); 
		down.toggle(false); 
		left.toggle(false);
		right.toggle(false);
		space.toggle(false);
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			up.toggle(true);
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			down.toggle(true);
		} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
			left.toggle(true);
		} else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			right.toggle(true);
		} else if (keyCode == KeyEvent.VK_SPACE) {
			space.toggle(true);
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_UP  || keyCode == KeyEvent.VK_W) {
			up.toggle(false);
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			down.toggle(false);
		} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
			left.toggle(false);
		} else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			right.toggle(false);
		} else if (keyCode == KeyEvent.VK_SPACE) {
			space.toggle(false);
		} else if (keyCode == KeyEvent.VK_P) {
			for (PauseHandler h : pauseHandlers) {
				h.handlePause();
			}
		} else if (keyCode == KeyEvent.VK_U) {
			for (SaveHandler h : saveHandlers) {
				h.handleSave();
			}
		}
	}
	
	public void keyTyped(KeyEvent e) {
	}
	
	public void addPauseHandler(PauseHandler h) {
		pauseHandlers.add(h);
	}
	
	public void addSaveHandler(SaveHandler h) {
		saveHandlers.add(h);
	}
	
	public void removePauseHandler(PauseHandler h) {
		pauseHandlers.remove(h);
	}
	
	public void removeSaveHandler(SaveHandler h) {
		saveHandlers.remove(h);
	}

	private InputHandler() {
		pauseHandlers = new LinkedList<PauseHandler>();
		saveHandlers = new LinkedList<SaveHandler>();
	}

	public class Key {
		private boolean pressed = false;

		public boolean isPressed() {
			return pressed;
		}

		public void toggle(boolean pressed) {
			this.pressed = pressed;
		}
	}
}
