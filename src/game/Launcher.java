package game;

import game.ui.GameFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Launcher implements Runnable {
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
	
	private static void playBackgroundMusic() {
		new Thread(new Runnable() {
			public void run() {
				new BackgroundPlayer().play();
			}
		}).start();
	}
}
