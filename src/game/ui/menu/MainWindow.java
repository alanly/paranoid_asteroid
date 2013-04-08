package game.ui.menu;
import game.ui.GameCanvas;

import java.awt.*;

import javax.swing.*;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JFrame window = new JFrame();
	private CardLayout cLayout;
	private JPanel cardPanel;
	private JPanel basepanel;
	
	//set to protected to be referenced from different panels
	protected static Main mainP;
	protected static Instructions instrP;
	protected static Credits creditP;
	protected static Highscores HighscoreP;
	protected static Options optionsP;
	protected static Pause pauseP;
	
	/**
	 * Creates Frame (Window) of the game.
	 * Creates different panels, uses CardLayout to show different panel.
	 * First Panel Shown is Main Menu
	 * TODO: Pause window and highscore insertion
	 * 
	 * @param game game instance
	 * @param highscoreH highscore datahandler
	 * @param optionH option datahandler
	 */
	public MainWindow(Game game, HSHandler highscoreH, OptionHandler optionH) {
		
		window.setLayout(new BorderLayout());
		cLayout = new CardLayout();
		cardPanel = new JPanel(cLayout);
		basepanel = new JPanel();
		
		mainP = new Main(cLayout, cardPanel, game);
		instrP = new Instructions(cLayout, cardPanel);
		creditP = new Credits(cLayout, cardPanel);
		HighscoreP = new Highscores(cLayout,cardPanel, highscoreH);
		optionsP = new Options(cLayout,cardPanel, optionH);
		pauseP = new Pause(cLayout, cardPanel,game);
		
		cardPanel.add(HighscoreP,"Highscores");
		cardPanel.add(optionsP,"Options");
		cardPanel.add(instrP,"Instructions");
		cardPanel.add(creditP,"Credits");
		cardPanel.add(mainP,"Main");
		cardPanel.add(pauseP, "Pause");
		
		cLayout.show(cardPanel, "Main");
		cardPanel.setPreferredSize(new Dimension(GameCanvas.WIDTH, GameCanvas.HEIGHT));
		window.add(cardPanel, BorderLayout.CENTER);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
