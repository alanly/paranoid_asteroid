package game;

import game.ui.GameFrame;

import javax.swing.SwingUtilities;

public class Launcher implements Runnable {
	static {
		SoundEffect.init();
	}
	
	public void run() {
		new GameFrame();
	}
	
	public static void main(String[] args) {
		playBackgroundMusic();
		SwingUtilities.invokeLater(new Launcher());
	}
	
	private static void playBackgroundMusic() {
		new Thread(new Runnable() {
			public void run() {
				SoundEffect.BACKGROUND.play();
			}
		}).start();
	}
}
