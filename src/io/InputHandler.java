package io;

import game.events.PauseHandler;
import game.events.SaveHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
/**
 * The InputHandler implements the KeyListener interface and acts as a interface between all other system classes and keystroke inputs. The InputHandler contains Key variables which indicate which are read by the program to determine whether the key is being pressed.
 * This class uses the singleton design pattern.
 *
 */
public class InputHandler implements KeyListener {
	private static InputHandler instance = new InputHandler();

	private Key up = new Key();
	private Key down = new Key();
	private Key left = new Key();
	private Key right = new Key();
	private Key space = new Key();
	private List<PauseHandler> pauseHandlers;
	private List<SaveHandler> saveHandlers;

	/**
	 * Returns the instance of InputHandler which is created at start up.
	 * @return the InputHandler instance
	 */
	public static InputHandler getInstance() {
		return instance;
	}

	/**
	 * Returns the <tt>up</tt> Key
	 * @return the <tt>up</tt> Key
	 */
	public Key getUpKey() {
		return up;
	}

	/**
	 * Returns the <tt>down</tt> Key
	 * @return the <tt>down</tt> Key
	 */
	public Key getDownKey() {
		return down;
	}

	/**
	 * Returns the <tt>left</tt> Key
	 * @return the <tt>left</tt> Key
	 */
	public Key getLeftKey() {
		return left;
	}

	/**
	 * Returns the <tt>right</tt> Key
	 * @return the <tt>right</tt> Key
	 */
	public Key getRightKey() {
		return right;
	}

	/**
	 * Returns the <tt>space</tt> Key
	 * @return the <tt>space</tt> Key
	 */
	public Key getSpaceKey() {
		return space;
	}
	
	/**
	 * resets the state of all Key variables
	 */
	public void reset() {
		up.toggle(false); 
		down.toggle(false); 
		left.toggle(false);
		right.toggle(false);
		space.toggle(false);
	}

	/**
	 * Updates the Key variables when a KeyEvent <tt>e</tt> occurs due to a key being pressed
	 * @param e the KeyEvent
	 */
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

	/**
	 * Updates the Key variables when a KeyEvent <tt>e</tt> occurs due to a key being released
	 * @param e the KeyEvent
	 */
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
	
	/**
	 * 
	 */
	public void keyTyped(KeyEvent e) {
	}
	
	/**
	 * Adds a PauseHandler to <tt>pauseHandler</tt>.
	 * @param h PauseHandler to be added
	 */
	public void addPauseHandler(PauseHandler h) {
		pauseHandlers.add(h);
	}
	
	/**
	 * Adds a SaveHandler to <tt>saveHandlers</tt>
	 * @param h SaveHandler to be added
	 */
	public void addSaveHandler(SaveHandler h) {
		saveHandlers.add(h);
	}
	
	/**
	 * Removes a PauseHandler from <tt>pauseHandlers</tt>
	 * @param h PauseHandler to be removed
	 */
	public void removePauseHandler(PauseHandler h) {
		pauseHandlers.remove(h);
	}
	/**
	 * Removes a SaveHandler from <tt>saveHandlers</tt>
	 * @param h SaveHandler to be removed
	 */
	public void removeSaveHandler(SaveHandler h) {
		saveHandlers.remove(h);
	}

	/**
	 * Creates a new InputHandler
	 */
	private InputHandler() {
		pauseHandlers = new LinkedList<PauseHandler>();
		saveHandlers = new LinkedList<SaveHandler>();
	}
  
	/**
	 * the nested class Key is used to represents the state of keyboard keys.
	 *
	 */
	public class Key {
		private boolean pressed = false;
		/**
		 * 
		 * @return <tt>true</tt> if a key is being pressed. <tt>false</tt> otherwise
		 */
		public boolean isPressed() {
			return pressed;
		}
		/**
		 * Assigns a value to pressed.
		 * @param pressed value to be assigned to the class variable <tt>pressed</tt>
		 */
		public void toggle(boolean pressed) {
			this.pressed = pressed;
		}
	}
}
