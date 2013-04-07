package game;

import game.ui.GameFrame;

import javax.swing.SwingUtilities;

public class Launcher implements Runnable {
	public void run() {
		new GameFrame();
	}
	
	public static void main(String[] args) {
		SoundEffect.init();
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
