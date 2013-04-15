package game.ui.menu;

import game.ui.GameFrame;

import java.awt.BorderLayout;
/**
 * ManelPanel is a subclass of BasePanel and represents the panel containing the main menu and all buttons and nested panels associated with it.
 */
public class MainPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	private TitlePanel titlePanel;
	private ButtonPanel buttonPanel;
	private InsertCoinLabel coinLabel;
	
	/**
	 * Creates a new MainPanel 
	 * @param gameFrame The GameFrame used
	 */
	public MainPanel(GameFrame gameFrame) {
		this.setLayout(new BorderLayout());
		
		titlePanel = new TitlePanel();
		buttonPanel = new ButtonPanel(gameFrame);
		coinLabel = new InsertCoinLabel();
		
		this.add(coinLabel, BorderLayout.NORTH);
		this.add(titlePanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		this.add(titlePanel);
	}
}
