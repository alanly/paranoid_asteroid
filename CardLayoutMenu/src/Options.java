import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Options extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JButton okButton;
	private OptionHandler optionHandler;
	private JComboBox shootB, leftB, rightB, forwardB, hyperB, pauseB;
	private GridLayout gLayout;
	//TODO: Enum may be better
	private String[] keys = { "a", "b", "c", "d", "w", "s", "spacebar", "left", "right"};
	
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
		
		final JComboBox shootB = new JComboBox(keys);
		//set to element in OptionH		
		JComboBox leftB = new JComboBox(keys);
		//set to element in OptionH
		JComboBox rightB = new JComboBox(keys);
		//set to element in OptionH
		JComboBox forwardB = new JComboBox(keys);
		//set to element in OptionH
		JComboBox hyperB = new JComboBox(keys);
		//set to element in OptionH
		JComboBox pauseB = new JComboBox(keys);
		//set to element in OptionH
		
		//this.add(new JLabel("Options Happen Here"));
		
		gLayout = new GridLayout(0,2);
		this.setLayout(gLayout);
		
		this.add(new JLabel("Shoot Key : "));
		this.add(shootB);
		this.add(new JLabel("Left Key : "));
		this.add(leftB);
		this.add(new JLabel("Right Key : "));
		this.add(rightB);
		this.add(new JLabel("Forward Key : "));
		this.add(forwardB);
		this.add(new JLabel("Hyperspace Key : "));
		this.add(hyperB);
		this.add(new JLabel("Pause Key : "));
		this.add(pauseB);
		
		//this.add(new JLabel("Difficulty Level : "));
		//this.add(new JLabel("Enable Sound : "));
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Save options to OptionHandler
				System.out.println(shootB.getSelectedIndex());
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
}