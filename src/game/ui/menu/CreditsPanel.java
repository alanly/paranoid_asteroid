package game.ui.menu;
import game.Fonts;
import game.ui.GameFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
		titleLabel.setFont(Fonts.PANEL_TITLE_FONT.getFont());
		titleLabel.setForeground(new Color(0xF0F0F0));
		
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(creditsLabel, BorderLayout.CENTER);
		this.add(backButton, BorderLayout.SOUTH);
	}

}
