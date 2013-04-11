package game;

import java.awt.Color;

public enum Colors {
	WHITE(0xF0F0F0),
	STEEL(0xB0C4DE),
	YELLOW(0xEDD808),
	PURPLE(0x693D99),
	BLUE(0x2D8299),
	DARK_BLUE(0x292b36),
	ORANGE(0x994B1B);
	
	private Color color;
	
	Colors(int hex) {
		this.color = new Color(hex);
	}
	
	public Color getColor() {
		return this.color;
	}
}
