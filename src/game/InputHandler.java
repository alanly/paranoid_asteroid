package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
	private static InputHandler instance;

	private Key up = new Key();
	private Key down = new Key();
	private Key left = new Key();
	private Key right = new Key();
	private Key space = new Key();

	public static InputHandler getInstance() {
		if (instance == null) {
			instance = new InputHandler();
		}

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

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_UP) {
			up.toggle(true);
		} else if (keyCode == KeyEvent.VK_DOWN) {
			down.toggle(true);
		} else if (keyCode == KeyEvent.VK_LEFT) {
			left.toggle(true);
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			right.toggle(true);
		} else if (keyCode == KeyEvent.VK_SPACE) {
			space.toggle(true);
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_UP) {
			up.toggle(false);
		} else if (keyCode == KeyEvent.VK_DOWN) {
			down.toggle(false);
		} else if (keyCode == KeyEvent.VK_LEFT) {
			left.toggle(false);
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			right.toggle(false);
		} else if (keyCode == KeyEvent.VK_SPACE) {
			space.toggle(false);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	private InputHandler() {
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
