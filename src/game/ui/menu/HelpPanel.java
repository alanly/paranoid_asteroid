package game.ui.menu;
import game.Fonts;
import game.ui.GameFrame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;


public class HelpPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	public HelpPanel(GameFrame frame) {
		this.setLayout(new BorderLayout());
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<html>");
		sb.append("<h2><b>Welcome to Paranoid Asteroid</b></h2>");
		sb.append("<br>");
		sb.append("You are in command of a space ship somewhere in outerspace<br>");
		sb.append("You are underattack by hostile aliens and battered by rogue asteroids<br>");
		sb.append("Escape is futile, nobody can help you<br>");
		sb.append("Your only choice fight to the death");
		sb.append("and take as many down with you as you can<br>");
		sb.append("Good Luck<br>");
		sb.append("<br>");
		
		sb.append("<h2><u>Goal</u></h2><br>");
		sb.append("Shoot down as many alien spaces ships and asteroids within your lifetime<br>");
		sb.append("Get hit by an asteroid, or an alien's bullet and you die<br>");
		sb.append("To help you, aliens may drop power up when they die. Grab them quickly<br>");
		//sb.append("You may jump to hyperspace at any time, but beware you may re-appear anywhere<br>");
		sb.append("<br>");
		
		sb.append("<h2><u>Controls</u></h2><br>");
		sb.append("forward - up arrow key (alt - w)<br>");
		sb.append("left turn - left arrow key (alt - a)<br>");
		sb.append("right turn - right arrow key (alt - d)<br>");
		sb.append("shoot - spacebar<br>");
		//sb.append("hyperspace - down arrow key (alt - s)<br>");
		sb.append("<br>");
		sb.append("Pause Game - p key<br>");
		sb.append("Save Game - u key<br>");
		sb.append("<br>");
		
		sb.append("<h2><u>Game Play</u></h2><br>");
		sb.append("<u>Single Player</u> : Try to get as far in the game as possible,<br>");
		sb.append( "accumulate as many points as possible and get to the top of the learder board<br>");
		sb.append("<u>Two Player</u> : Play with a friend, 3 turns each to accumulate as many points and beat the other<br>");
		sb.append("<u>Load Game</u> : Load a previously stored single player game, saved with the s key, the level is restarted<br>");
		sb.append("<u>High Scores</u> : See who has the current high score in the game<br>");
		sb.append("<br>");
		
		sb.append("<h2><u>Power Ups</u></h2><br>");
		sb.append("<u>Boost</u> : Increases ship velocity, yellow circle<br>");
		sb.append("<u>Pulse Shot</u> : Bullets fired in all directions, orange circle<br>");
		sb.append("<u>Shield</u> : Protection against enemmies, blue circle<br>");
		sb.append("<u>Triple Shot</u> : 3 bullets fired at a time at angles, purple circle<br>");

		sb.append("</html>");
		
		
		//JLabel helpLabel = new JLabel(sb.toString());
		
		
		JTextPane helpLabel = new JTextPane(); 
		helpLabel.setOpaque(false);
		helpLabel.setContentType("text/html");;
		helpLabel.setText(sb.toString());		
		helpLabel.setCaretPosition(0);
		

		helpLabel.setFont(Fonts.BODY_FONT.getFont());
		helpLabel.setForeground(new Color(0xF0F0F0));
		
		JScrollPane scroller = new JScrollPane(helpLabel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		scroller.getViewport().setOpaque(true); //false makes background cream color
		scroller.getViewport().setBackground(new Color(0x292b36));
		scroller.setBorder(null);
		
		this.add(new PageTitleLabel("Help"), BorderLayout.NORTH);
		this.add(scroller, BorderLayout.CENTER);
		this.add(new BackButtonPanel(frame), BorderLayout.SOUTH);
	}

}
