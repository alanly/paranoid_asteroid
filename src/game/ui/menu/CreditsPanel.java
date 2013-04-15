package game.ui.menu;
import game.Colors;
import game.Fonts;
import game.ui.GameFrame;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * 
 * CreditsPanel is a subclass of BasePanel and represents the panel displaying the development credits of the system
 */
public class CreditsPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Creates a new CreditsPanel
	 * @param frame the GameFrame used
	 */
	public CreditsPanel(GameFrame frame) {
		this.setLayout(new BorderLayout());
		
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
		creditsLabel.setForeground(Colors.WHITE.getColor());
		creditsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		creditsLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		this.add(new PageTitleLabel("Credits"), BorderLayout.NORTH);
		this.add(creditsLabel, BorderLayout.CENTER);
		this.add(new BackButtonPanel(frame), BorderLayout.SOUTH);
	}

}
