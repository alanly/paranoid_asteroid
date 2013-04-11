package game.ui.menu;

import game.Colors;
import game.Fonts;
import game.HighScores;
import game.ui.GameFrame;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class HighScoresPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	private HighScores highScores;
	private JLabel scores;
	
	public HighScoresPanel(final GameFrame frame) {
		this.setLayout(new BorderLayout());
		
		highScores = HighScores.getInstance();
		scores = new JLabel();
		scores.setFont(Fonts.BODY_FONT.getFont());
		scores.setForeground(Colors.WHITE.getColor());
		scores.setHorizontalAlignment(SwingConstants.CENTER);
		scores.setVerticalAlignment(SwingConstants.CENTER);
		
		this.add(new PageTitleLabel("High Scores"), BorderLayout.NORTH);
		this.add(scores, BorderLayout.CENTER);
		this.add(new BackButtonPanel(frame), BorderLayout.SOUTH);
		
		generateScores();
	}
	
	public void reload() {
		highScores.reload();
		generateScores();
	}
	
	private void generateScores() {
		StringBuilder sb = new StringBuilder();
		
		//scores.setFont(f);
		
		sb.append("<html>");
		
		for (HighScores.Score s : highScores) {
			sb.append("<b>");
			sb.append(s.getName());
			sb.append("</b> ");
			sb.append(s.getScore());
			sb.append("<br>");
		}
		
		sb.append("</html>");
		
		scores.setText(sb.toString());
	}
}
