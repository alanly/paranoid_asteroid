package game;

import javax.swing.JFrame;

public class GameLauncher {
	public static void main(String[] args) {
		(new Runnable() {
			public void run() {
				getClass().getResource("/ship.png");
				
				GameField field = new GameField();
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.add(field);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				
				field.start();
			}
		}).run();
	}
}
