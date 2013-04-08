package game.ui.menu;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Pause extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JButton saveAndExit, resumeGame, exit;

	public Pause(final CardLayout cl, final JPanel parentPanel) {
		
		saveAndExit = new JButton("Save And Exit");
		resumeGame = new JButton("Resume");
		exit = new JButton("Exit");
		
		saveAndExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO : Save game state
				System.out.println("Save game");
				cl.show(parentPanel, "Main");
			}
		});
		
		resumeGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO : Resume Game
			}
		});
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(parentPanel, "Main");
			}
		});
		
		this.add(saveAndExit);
		this.add(resumeGame);
		this.add(exit);
	}

}
