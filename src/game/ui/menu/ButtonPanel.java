package game.ui.menu;

import game.ui.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JButton singlePlayerButton;
	private JButton twoPlayerButton;
	private JButton highScoresButton;
	private JButton creditsButton;
	private GameFrame frame;
	
	public ButtonPanel(GameFrame frame) {
		this.frame = frame;
		this.setOpaque(false);
		
		initButtons();
	}
	
	public void add(JButton button) {
		button.addActionListener(this);
		super.add(button);
	}
	
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == singlePlayerButton) {
			frame.playSinglePlayer();
		} else if (source == twoPlayerButton) {
			System.out.println(twoPlayerButton.getName());
		} else if (source == highScoresButton) {
			frame.showHighScores();
		} else if (source == creditsButton) {
			frame.showCreditsPanel();
		}
	}
	
	private void initButtons() {
		singlePlayerButton = new JButton("Single Player");
		this.add(singlePlayerButton);
		
		twoPlayerButton = new JButton("Two Player");
		this.add(twoPlayerButton);
		
		highScoresButton = new JButton("High Scores");
		this.add(highScoresButton);
		
		creditsButton = new JButton("Credits");
		this.add(creditsButton);
	}
}
