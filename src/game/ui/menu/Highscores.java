package game.ui.menu;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Highscores extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JButton okButton;
	private JTextArea[][] table = new JTextArea[10][3] ;
	private ArrayList<Highscore> HSlist = new ArrayList<Highscore>();
	private JLabel name, score, deaths, date;
	private GridLayout gLayout = new GridLayout(0,3);
	
	/**
	 * Highscore panel. Show current highscores or insert new highscore.
	 * 
	 * @param cl
	 * @param parentPanel
	 * @param highscoreH
	 */
	public Highscores(final CardLayout cl, final JPanel parentPanel, HSHandler highscoreH) {

		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 3; j++){
					table[i][j] = new JTextArea();
					table[i][j].setEditable(false);
			}
		}
		
		HSlist = highscoreH.getHS();
		
		//Test Code
		/*HSlist.add(new Highscore(22342, "jon", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "jef", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "jill", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "jad", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "jeb", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "joel", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "joan", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "joe", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "jim", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "jose", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "juan", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "john", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "jim", 23, 05, 1992));
		HSlist.add(new Highscore(22342, "jam", 23, 05, 1992));*/
		//End of test code
		
		this.setLayout(gLayout);
		
		this.add(new JLabel("NAME "));
		this.add(new JLabel(" SCORE "));
		this.add(new JLabel(" DATE"));
		
		for(int i = 0; i < 10; i++){
			if(i < HSlist.size()){
					for(int j = 0; j < 3; j++){
					this.add(table[i][j]);			
				}
			}
		}
		
		this.populate();
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if WinGame need to save to DataHandler
				cl.show(parentPanel, "Main");
			}
		});
		
		this.add(okButton);
	}
	
	/**
	 * Populates the TextArea with highscore information
	 */
	public void populate(){
		for(int i = 0; i < table.length; i++){
				if(i < HSlist.size()){
				table[i][0].insert("" + HSlist.get(i).getName(), 0);
				table[i][1].insert("" + HSlist.get(i).getScore(), 0);
				table[i][2].insert("" + HSlist.get(i).getDate()[0] + "/" + HSlist.get(i).getDate()[1] + "/" + HSlist.get(i).getDate()[2], 0);
			}
		}
	}

	/**
	 * Update Highscore Panel before displaying it
	 */
	public void update() {
		System.out.println("Update HS");
		
	}
}
