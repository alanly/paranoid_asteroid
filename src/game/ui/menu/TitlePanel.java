package game.ui.menu;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel titleLabel;
	
	public TitlePanel() {
		titleLabel = new JLabel("<html>Paranoid<br>Asteroid</html>");
		titleLabel.setFont(new Font("Helvetica", Font.BOLD, 72));
		titleLabel.setAlignmentY(LEFT_ALIGNMENT);
		
		this.add(titleLabel);
	}
}
