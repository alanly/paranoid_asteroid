package game.ui.menu;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.HashSet;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Options extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JButton okButton;
	private OptionHandler optionHandler;
	private JComboBox[] keysA = new JComboBox[6]; // Order: shootB, leftB, rightB, forwardB, hyperB, pauseB
	private JComboBox[] keysB = new JComboBox[6]; 
	private JComboBox difficulty;
	private JCheckBox sound = new JCheckBox();
	private JTextField shootkeyB, leftkeyB, rightkeyB, forwardkeyB, hyperkeyB, pausekeyB;
	private GridLayout gLayout;
	private int[] selection = new int[14];
	private String[] keys = { "W" , "S", "A", "D", "SPACE", "E", "UP", "DOWN", "LEFT", "RIGHT", "R_SHIFT", "ENTER"};
	private String[] difficultyLvl = { "Easy", "Medium", "Hard", "MAYDAY!"};  
	private HashSet<String> key = new HashSet<String>();
	
	/**
	 * Options Panel. Choose keys and other game options
	 * 
	 * @param cl
	 * @param parentPanel
	 * @param optionH
	 */
	public Options(final CardLayout cl, final JPanel parentPanel, OptionHandler optionH) {

		optionHandler = optionH;
		//get data
		//instatiate JComboBoxes at default values
		//TODO: figure out of to set the ComboBoxes to desired initial index.
		for(int i = 0; i < keysA.length; i++) {
			keysA[i] = new JComboBox(keys);
			keysA[i].setSelectedIndex(i);
		}
		for(int i = 0; i < keysB.length; i++){
			keysB[i] = new JComboBox(keys);
			keysB[i].setSelectedIndex(i + keysA.length);
		}

		difficulty = new JComboBox(difficultyLvl);
		
		gLayout = new GridLayout(0,2);
		this.setLayout(gLayout);

		this.add(new JLabel("Player A"));
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(new JLabel("Shoot Key : "));
		this.add(keysA[0]);
		this.add(new JLabel("Left Key : "));
		this.add(keysA[1]);
		this.add(new JLabel("Right Key : "));
		this.add(keysA[2]);
		this.add(new JLabel("Forward Key : "));
		this.add(keysA[3]);
		this.add(new JLabel("Hyperspace Key : "));
		this.add(keysA[4]);
		this.add(new JLabel("Pause Key : "));
		this.add(keysA[5]);

		this.add(Box.createVerticalGlue());
		this.add(Box.createVerticalGlue());
		
		this.add(new JLabel("Player B"));
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(new JLabel("Shoot Key : "));
		this.add(keysB[0]);
		this.add(new JLabel("Left Key : "));
		this.add(keysB[1]);
		this.add(new JLabel("Right Key : "));
		this.add(keysB[2]);
		this.add(new JLabel("Forward Key : "));
		this.add(keysB[3]);
		this.add(new JLabel("Hyperspace Key : "));
		this.add(keysB[4]);
		this.add(new JLabel("Pause Key : "));
		this.add(keysB[5]);
		
		this.add(Box.createVerticalGlue());
		this.add(Box.createVerticalGlue());

		this.add(new JLabel("Difficulty Level : "));
		this.add(difficulty);
		this.add(new JLabel("Enable Sound : "));
		this.add(sound);
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populate();
				//TODO: Save options to OptionHandler
				boolean valid = true;
				for(int i =0; i < selection.length - 2; i++){
					for(int j=0; j < selection.length - 2; j++){
						if(selection[i] == selection[j] && i !=j ){
							valid = false;
							break;
						}
					}
				}
				if(valid){
					System.out.println("This is a valid key selection. ");
					System.out.println("player A shoots with " + selection[0] + " while B shoots with " + selection[7]);
					System.out.println("Difficulty is at lvl " + selection[12] + ". With sound? " + sound.isSelected());
				}else{
					printError();
					return;
				}
				cl.show(parentPanel, "Main");
			}
		});
		
		this.add(okButton);
	}
	
	/**
	 * Update Options panel before displaying it
	 */
	public void update() {
		//to be called before options panel is shown
		//update labels etc etc
		//for combo boxes : .setSelectedIndex()
		System.out.println("Update Options");
	}
	
	public void populate(){
		for(int i =0; i < keysA.length; i++){
			selection[i] = keysA[i].getSelectedIndex(); 
		}
		for(int i =0; i < keysB.length; i++){
			selection[i + keysA.length] = keysB[i].getSelectedIndex(); 
		}
		selection[keysA.length + keysB.length] = difficulty.getSelectedIndex();
	//	selection[keysA.length + keysB.length + 1] = (int)sound.isSelected();
	}
	
	private void printError(){
		JOptionPane.showMessageDialog(this, "The selection is invalid: duplicate key assignmment!");
	}
}