package game.ui.menu;

import game.Colors;
import game.Fonts;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TitlePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel titleLabel;
	
	public TitlePanel() {
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		this.setFocusable(false);
		titleLabel = new JLabel("<html><center>Paranoid<br>Asteroid<br>&nbsp;</center></html>");
		titleLabel.setFont(Fonts.TITLE_FONT.getFont());
		titleLabel.setForeground(Colors.WHITE.getColor());
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		this.add(titleLabel);
	}
}
