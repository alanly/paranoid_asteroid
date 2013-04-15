package game.ui.menu;

import game.ui.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * BackButtonPanel is a subclass of JPanel and represent the panel containing the "Back" button which displays the main menu when clicked.
 */
public class BackButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton backButton;
	
	/**
	 * Creates a new BackButtonPanel
	 * @param frame the GameFrame used
	 */
	public BackButtonPanel(final GameFrame frame) {
		this.setOpaque(false);
		this.setFocusable(false);
		
		backButton = new JButton("Back");
		backButton.setFocusable(false);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.showMainMenu();
			}
		});
		
		this.add(backButton);
	}
}
