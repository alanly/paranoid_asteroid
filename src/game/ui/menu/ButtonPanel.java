package game.ui.menu;

import game.ui.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * ButtonPanel is a subclass of JPanel and implements the ActionListener interface. It represents the panel containing the main menu button and was made to be used in the MainPanel class.
 */
public class ButtonPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JButton singlePlayerButton;
	private JButton twoPlayerButton;
	private JButton loadGameButton;
	private JButton highScoresButton;
	private JButton creditsButton;
	private JButton helpButton;
	private GameFrame frame;
	
	/**
	 * Creates a new ButtonPanel
	 * @param frame the GameFrame used
	 */
	public ButtonPanel(GameFrame frame) {
		this.frame = frame;
		this.setOpaque(false);
		this.setFocusable(false);
		
		initButtons();
	}
	
	/**
	 * Adds a JButton to the panel
	 * @param button the JButton to be added
	 */
	public void add(JButton button) {
		button.addActionListener(this);
		button.setFocusable(false);
		super.add(button);
	}
	
	/**
	 * Responds to the event where one of the buttons is clicked. Depending on the source of the button click event, either launches the game or switches to the appropriate panel.
	 * @param event the button-click event which triggered the method call
	 */
	
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
	
	/**
	 * Initialized the JButtons and adds them to the panel 
	 */
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
