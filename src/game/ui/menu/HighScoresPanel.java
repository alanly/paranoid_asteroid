package game.ui.menu;

import game.HighScores;
import game.ui.GameFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class HighScoresPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	private HighScores highScores;
	private JLabel scores;
	private JButton backButton;
	
	public HighScoresPanel(final GameFrame frame) {
		this.setLayout(new BorderLayout());
		
		highScores = HighScores.getInstance();
		scores = new JLabel();
		scores.setForeground(new Color(0xF0F0F0));
		
		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.showMainMenu();
			}
		});
		
		this.add(new PageTitleLabel("High Scores"), BorderLayout.NORTH);
		this.add(scores, BorderLayout.CENTER);
		this.add(backButton, BorderLayout.SOUTH);
		
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
			sb.append(s.getName());
			sb.append(": ");
			sb.append(s.getScore());
			sb.append("<br><br>");
		}
		
		sb.append("</html>");
		
		scores.setText(sb.toString());
	}
}
