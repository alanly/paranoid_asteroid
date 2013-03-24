import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JFrame window = new JFrame();
	private CardLayout cLayout;
	private JPanel cardPanel;
	
	//set to protected to be referenced from different panels
	protected static Main mainP;
	protected static Instructions instrP;
	protected static Credits creditP;
	protected static Highscores HighscoreP;
	protected static Options optionsP;
	
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
		
		mainP = new Main(cLayout, cardPanel, game);
		instrP = new Instructions(cLayout, cardPanel);
		creditP = new Credits(cLayout, cardPanel);
		HighscoreP = new Highscores(cLayout,cardPanel, highscoreH);
		optionsP = new Options(cLayout,cardPanel, optionH);
		
		cardPanel.add(HighscoreP,"Highscores");
		cardPanel.add(optionsP,"Options");
		cardPanel.add(instrP,"Instructions");
		cardPanel.add(creditP,"Credits");
		cardPanel.add(mainP,"Main");
		
		cLayout.show(cardPanel, "Main");
		window.add(cardPanel, BorderLayout.CENTER);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
