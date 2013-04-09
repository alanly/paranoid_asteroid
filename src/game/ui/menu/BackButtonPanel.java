package game.ui.menu;

import game.ui.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BackButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton backButton;
	
	public BackButtonPanel(final GameFrame frame) {
		this.setOpaque(false);
		
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
