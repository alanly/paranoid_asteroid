package game.menu;
import java.awt.CardLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.BoxLayout;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Credits extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton okButton;
	//private JLabel credits;
	private JTextArea credits;
	private BoxLayout bLayout;
	
	/**
	 * Creates Credits panel.
	 * @param cl
	 * @param parentPanel
	 */
	public Credits(final CardLayout cl, final JPanel parentPanel) {
		
		//credits = new JLabel("This game was created by :\nBLABLABLABLABLABLABLABLA\nGNU\nCC-SA-NC\n");
		credits = new JTextArea("This game was created by :\nBLABLABLABLABLABLABLABLA\nGNU\nCC-SA-NC\n");
		credits.setEditable(false);
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(parentPanel, "Main");
			}
		});
		
		bLayout = new BoxLayout(this,BoxLayout.Y_AXIS);
		this.setLayout(bLayout);
		this.add(credits);
		this.add(okButton);
	}

}
