package game.menu;
import java.awt.CardLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Highscores extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JButton okButton;
	
	/**
	 * Highscore panel. Show current highscores or insert new highscore.
	 * 
	 * @param cl
	 * @param parentPanel
	 * @param highscoreH
	 */
	public Highscores(final CardLayout cl, final JPanel parentPanel, HSHandler highscoreH) {

		this.add(new JLabel("Highscore information goes here"));
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if WinGame need to save to DataHandler
				cl.show(parentPanel, "Main");
			}
		});
		
		this.add(okButton);
	}

	/**
	 * Update Highscore Panel before displaying it
	 */
	public void update() {
		System.out.println("Update HS");
		
	}
}
