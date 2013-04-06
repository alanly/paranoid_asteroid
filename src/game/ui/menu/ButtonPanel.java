package game.ui.menu;

import game.ui.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonPanel extends BasePanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JButton singlePlayerButton;
	private JButton twoPlayerButton;
	private JButton highScoresButton;
	private GameFrame frame;
	
	public ButtonPanel(GameFrame frame) {
		this.frame = frame;
		
		initButtons();
		
		this.add(singlePlayerButton);
		this.add(twoPlayerButton);
		this.add(highScoresButton);
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
			System.out.println(highScoresButton.getName());
		}
	}
	
	private void initButtons() {
		singlePlayerButton = new JButton("Single Player");
		singlePlayerButton.setName("singlePlayer");
		
		twoPlayerButton = new JButton("Two Player");
		twoPlayerButton.setName("twoPlayer");
		
		highScoresButton = new JButton("High Scores");
		highScoresButton.setName("highScores");
	}
}
