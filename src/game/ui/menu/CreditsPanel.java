package game.ui.menu;
import game.ui.GameFrame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
//import javax.swing.JLabel;


public class CreditsPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	private JButton backButton;
	
	public CreditsPanel(final GameFrame frame) {
		this.setLayout(new BorderLayout());
		
		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.showMainMenu();
			}
		});
		
		JLabel creditsLabel = new JLabel("");
		JLabel titleLabel = new JLabel("Credits");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Helvetica", Font.BOLD, 36));
		
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(creditsLabel, BorderLayout.CENTER);
		this.add(backButton, BorderLayout.SOUTH);
	}

}
