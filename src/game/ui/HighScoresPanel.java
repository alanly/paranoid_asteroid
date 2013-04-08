package game.ui;

import game.HighScores;
import game.ui.menu.BasePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

public class HighScoresPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	private HighScores highScores;
	private List<JLabel> scoreLabels;
	private JButton backButton;
	
	public HighScoresPanel(final GameFrame frame) {
		highScores = HighScores.getInstance();
		scoreLabels = new LinkedList<JLabel>();
		backButton = new JButton("Back");
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.showMainMenu();
			}
		});
		
		this.add(backButton);
		
		generateLabels();
	}
	
	public void reload() {
		highScores.reload();
		generateLabels();
	}
	
	private void generateLabels() {
		for (JLabel l : scoreLabels) {
			this.remove(l);
		}
		
		scoreLabels.clear();
		
		for (HighScores.Score s : highScores) {
			JLabel scoreLabel = new JLabel(s.getName() + ": " + s.getScore());
			scoreLabels.add(scoreLabel);
			this.add(scoreLabel);
			
		}
	}
}
