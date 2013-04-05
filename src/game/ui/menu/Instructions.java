package game.ui.menu;
import java.awt.CardLayout;
import java.awt.event.*;

import javax.swing.JButton;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Instructions extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton okButton;
	private JTextArea instructions;
	//TODO:use box layout
	
	/**
	 * Instruction Panel. Shows user how to play game.
	 * TODO : populate with instructions
	 * 
	 * @param cl
	 * @param parentPanel
	 */
	public Instructions(final CardLayout cl, final JPanel parentPanel) {
		
		//TODO:switch this to scrollable JTextPane
		instructions = new JTextArea("Here is how to play Paranoid Asteroid");
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(parentPanel, "Main");
			}
		});
		
		this.add(instructions);
		this.add(okButton);
	}
}
