package game;

import java.awt.Color;

/**
 * Colors is an enum type listing the fonts used in by system
 */
public enum Colors {
	WHITE(0xF0F0F0),
	STEEL(0xB0C4DE),
	YELLOW(0xEDD808),
	PURPLE(0x693D99),
	BLUE(0x2D8299),
	DARK_BLUE(0x292b36),
	ORANGE(0x994B1B),
	GREY(0x666666);
	
	private Color color;
	
	/**
	 * Creates a new Color from the color code
	 * @param hex color code in hex
	 */
	Colors(int hex) {
		this.color = new Color(hex);
	}
	
	/**
	 * Returns the color
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}
}
