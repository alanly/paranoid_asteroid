package game.ui.menu;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JPanel;


public class Main extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton loadGBtn, singlePBtn, versusPBtn, contestPBtn, optionsBtn, instructionsBtn, creditsBtn, highscoresBtn, exitBtn;
	private GridLayout gLayout;
	
	/**
	 * Main menu panel, displaying buttons to users and switching to other cards on button press
	 * 
	 * @param cl (Card)Layout that will contain Main Menu Panel
	 * @param parentPanel Panel containing the Main Menu elements
	 * @param game Game instance
	 */
	public Main(final CardLayout cl, final JPanel parentPanel, Game game) {
		
		loadGBtn = new JButton("Load Saved Game");
		loadGBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Load Saved Game!!!!!");
				//TODO: start single game activity
			}
		});
		
		singlePBtn = new JButton("Single Player Game");
		singlePBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("A new Single Game Has Started!!!!!");
				//TODO: start single game activity
			}
		});
		
		versusPBtn = new JButton("Two Player Versus Game");
		versusPBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("A new Versus Game Has Started!!!!!");
				//TODO: start versus game activity (2 players, 1 game field)
			}
		});
		
		contestPBtn = new JButton("Two Player Contest Game");
		contestPBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("A new Contest Game Has Started!!!!!");
				//TODO: start contest game activity
			}
		});
		
		exitBtn = new JButton("Exit");
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		creditsBtn = new JButton("Credits");
		creditsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(parentPanel, "Credits");
			}
		});
		
		instructionsBtn = new JButton("Instructions");
		instructionsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(parentPanel, "Instructions");
			}
		});
		
		optionsBtn = new JButton("Options");
		optionsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO : Update options panel with appropriate info, if necessary
				MainWindow.optionsP.update();
				cl.show(parentPanel, "Options");
			}
		});
		
		highscoresBtn = new JButton("Highscores");
		highscoresBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO : Update highscore panel with appropriate info by calling internal method
				MainWindow.HighscoreP.update();
				cl.show(parentPanel, "Highscores");
			}
		});
		
		gLayout = new GridLayout(0,1);
		this.setLayout(gLayout);
		
		this.add(loadGBtn);
		this.add(singlePBtn);
		this.add(versusPBtn);
		this.add(contestPBtn);
		this.add(instructionsBtn);
		this.add(highscoresBtn);
		this.add(optionsBtn);
		this.add(creditsBtn);
		this.add(exitBtn);
	}

}
