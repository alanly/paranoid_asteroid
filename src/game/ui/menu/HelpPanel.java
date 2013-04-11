package game.ui.menu;
import game.Colors;
import game.Fonts;
import game.ui.GameCanvas;
import game.ui.GameFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.ScrollBarUI;


public class HelpPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	private static StringBuilder sb = new StringBuilder();
	
	static {
		sb.append("<html><body style=\"font-family: ");
		sb.append(Fonts.BODY_FONT.getFont().getFamily());
		sb.append("; color: #F0F0F0; padding-left: 5px;\">");
		sb.append("<b>Welcome to Paranoid Asteroid</b><br>");
		sb.append("<br>");
		sb.append("You are in command of a space ship somewhere in outerspace.<br>");
		sb.append("You are underattack by hostile aliens and battered by rogue asteroids. ");
		sb.append("Escape is futile, nobody can help you. ");
		sb.append("Your only choice fight to the death ");
		sb.append("and take as many down with you as you can.<br>");
		sb.append("<br>");
		sb.append("<b>You must destroy them.<br>The earth is counting on you. Good Luck!</b><br>");
		sb.append("<br>");
		
		sb.append("<b>Goal</b><br>");
		sb.append("<br>");
		sb.append("Shoot down as many alien spaces ships and asteroids within your lifetime<br>");
		sb.append("Get hit by an asteroid, or an alien's bullet and you die<br>");
		sb.append("To help you, aliens may drop power up when they die. Grab them quickly<br>");
		sb.append("You may jump to hyperspace at any time, but beware you may re-appear anywhere<br>");
		sb.append("<br>");
		
		sb.append("<b>Controls</b><br>");
		sb.append("<br>");
		sb.append("forward - up arrow key (or W)<br>");
		sb.append("left turn - left arrow key (or A)<br>");
		sb.append("right turn - right arrow key (or D)<br>");
		sb.append("shoot - spacebar<br>");
		sb.append("hyperspace - down arrow key (or S)<br>");
		sb.append("<br>");
		sb.append("Pause Game - P key<br>");
		sb.append("Save Game - U key<br>");
		sb.append("<br>");
		
		sb.append("<b>Game Play</b><br>");
		sb.append("<br>");
		sb.append("<u>Single Player</u><br>Try to get as far in the game as possible, accumulate as many points as possible and get to the top of the learder board<br><br>");
		sb.append("<u>Two Player</u><br>Play with a friend, 3 turns each to accumulate as many points and beat the other<br><br>");
		sb.append("<u>Load Game</u><br>Load a previously stored single player game, saved with the U key, the level is restarted<br><br>");
		sb.append("<u>High Scores</u><br>See who has the current high score in the game<br>");
		sb.append("<br>");
		
		sb.append("<b>Power Ups</b><br>");
		sb.append("<br>");
		sb.append("<u>Boost</u><br>Increases ship velocity, yellow circle<br><br>");
		sb.append("<u>Pulse Shot</u><br>Bullets fired in all directions, orange circle<br><br>");
		sb.append("<u>Shield</u><br>Protection against alien bullets, blue circle<br><br>");
		sb.append("<u>Triple Shot</u><br>3 bullets fired at a time at angles, purple circle<br>");

		sb.append("</body></html>");
	}
	
	public HelpPanel(GameFrame frame) {
		this.setLayout(new BorderLayout());
		
		JTextPane helpPane = new JTextPane();
		helpPane.setContentType("text/html");
		helpPane.setOpaque(false);
		helpPane.setCaretPosition(0);
		helpPane.setPreferredSize(new Dimension(GameCanvas.WIDTH, GameCanvas.HEIGHT));
		helpPane.setText(sb.toString());
		helpPane.setCaretPosition(0);
		
		JScrollPane scroller = new JScrollPane(helpPane,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		scroller.setBorder(null);
		scroller.setOpaque(false);
		scroller.getViewport().setOpaque(false);
		scroller.getVerticalScrollBar().setUI(new ScrollBarUI() {
			public void paint(Graphics g, JComponent c) {
				// Breaking paint because scrollbars are ugly
				c.setBackground(Colors.DARK_BLUE.getColor());
			}
		});
		
		this.add(new PageTitleLabel("Help"), BorderLayout.NORTH);
		this.add(scroller, BorderLayout.CENTER);
		this.add(new BackButtonPanel(frame), BorderLayout.SOUTH);
	}

}
