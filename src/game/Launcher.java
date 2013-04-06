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
		SwingUtilities.invokeLater(new Launcher());
	}
}
