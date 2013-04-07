package game.ui.menu;

import game.ui.GameFrame;

import java.awt.BorderLayout;

public class MainPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	private TitlePanel titlePanel;
	private ButtonPanel buttonPanel;
	
	public MainPanel(GameFrame gameFrame) {
		this.setLayout(new BorderLayout());
		
		titlePanel = new TitlePanel();
		buttonPanel = new ButtonPanel(gameFrame);
		
		this.add(titlePanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		this.add(titlePanel);
	}
}
