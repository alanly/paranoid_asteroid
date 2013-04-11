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
	private JButton loadGameButton;
	private JButton highScoresButton;
	private JButton creditsButton;
	private JButton helpButton;
	private GameFrame frame;
	
	public ButtonPanel(GameFrame frame) {
		this.frame = frame;
		this.setOpaque(false);
		this.setFocusable(false);
		
		initButtons();
	}
	
	public void add(JButton button) {
		button.addActionListener(this);
		button.setFocusable(false);
		super.add(button);
	}
	
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == singlePlayerButton) {
			frame.playSinglePlayer(false);
		} else if (source == twoPlayerButton) {
			frame.playTwoPlayer();
		} else if (source == loadGameButton) {
			frame.playSinglePlayer(true);
		} else if (source == highScoresButton) {
			frame.showHighScores();
		} else if (source == creditsButton) {
			frame.showCreditsPanel();
		} else if (source == helpButton) {
			frame.showHelpPanel();
		}
	}
	
	private void initButtons() {
		singlePlayerButton = new JButton("Single Player");
		this.add(singlePlayerButton);
		
		twoPlayerButton = new JButton("Two Player");
		this.add(twoPlayerButton);
		
		loadGameButton = new JButton("Load Game");
		this.add(loadGameButton);
		
		highScoresButton = new JButton("Scores");
		this.add(highScoresButton);
		
		helpButton = new JButton("?");
		this.add(helpButton);
		
		creditsButton = new JButton("Credits");
		this.add(creditsButton);
		
	}
}
