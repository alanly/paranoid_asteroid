package game.ui;

import game.HighScores;
import game.ui.menu.BasePanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HighScoresPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	private HighScores highScores;
	private JPanel scoresPanel;
	private List<JLabel> labels;
	private JButton backButton;
	
	public HighScoresPanel(final GameFrame frame) {
		this.setLayout(new BorderLayout());
		
		highScores = HighScores.getInstance();
		scoresPanel = new JPanel(new GridLayout(HighScores.MAX_SCORES, 2));
		labels = new LinkedList<JLabel>();
		
		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.showMainMenu();
			}
		});
		
		JLabel titleLabel = new JLabel("High Scores");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Helvetica", Font.BOLD, 36));
		
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(scoresPanel, BorderLayout.CENTER);
		this.add(backButton, BorderLayout.SOUTH);
		
		generateLabels();
	}
	
	public void reload() {
		highScores.reload();
		generateLabels();
	}
	
	private void generateLabels() {
		Font f = new Font("Helvetica", Font.PLAIN, 16);
		
		for (JLabel l : labels) {
			scoresPanel.remove(l);
		}
		
		labels.clear();
		
		for (HighScores.Score s : highScores) {
			JLabel nameLabel = new JLabel(s.getName() + "  ");
			nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			nameLabel.setFont(f);
			
			JLabel scoreLabel = new JLabel("  " + s.getScore());
			scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
			scoreLabel.setFont(f);
			
			labels.add(nameLabel);
			labels.add(scoreLabel);
			
			scoresPanel.add(nameLabel);
			scoresPanel.add(scoreLabel);
		}
	}
}
