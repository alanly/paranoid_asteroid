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
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<html>");
		sb.append("<b>Team 8</b><br>");
		sb.append("<br>");
		sb.append("Alexander Coco<br>");
		sb.append("Alexandre Bourdon<br>");
		sb.append("Payom Meshgin<br>");
		sb.append("Yi Qing Xiao<br>");
		sb.append("Daniel Ranga<br>");
		sb.append("Jad Sayegh<br>");
		sb.append("<br>");
		
		sb.append("<b>Music</b><br>");
		sb.append("<br>");
		sb.append("A Tiny Spaceship's Final Mission<br>");
		sb.append("by FantomenK<br>");
		sb.append("<br>");
		
		sb.append("<b>Font</b><br>");
		sb.append("<br>");
		sb.append("Droid Sans<br>");
		sb.append("<br>");
		
		sb.append("</html>");
		
		JLabel creditsLabel = new JLabel(sb.toString());
		creditsLabel.setFont(Fonts.BODY_FONT.getFont());
		creditsLabel.setForeground(new Color(0xF0F0F0));
		creditsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		creditsLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		this.add(new PageTitleLabel("Credits"), BorderLayout.NORTH);
		this.add(creditsLabel, BorderLayout.CENTER);
		this.add(backButton, BorderLayout.SOUTH);
	}

}
