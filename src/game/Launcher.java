package game;

import game.ui.GameFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/**
 * The Launcher class implements the Runnable interface, initialises the Fonts and soundEffects, plays the background music
 * and launches the software system in the main method.
 *
 */
public class Launcher implements Runnable {
	/**
	 * Creates a new GameFrame
	 */
	public void run() {
		new GameFrame();
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			// Forget it
		}
		
		SoundEffect.init();
		Fonts.init();
		
		playBackgroundMusic();
		SwingUtilities.invokeLater(new Launcher());
	}
	/**
	 * Plays the background music in a new Thread
	 */
	private static void playBackgroundMusic() {
		new Thread(new Runnable() {
			public void run() {
				new BackgroundPlayer().play();
			}
		}).start();
	}
}
