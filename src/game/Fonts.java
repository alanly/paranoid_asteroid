package game;

import java.awt.Font;
import java.io.InputStream;

/**
 * Fonts is an enum type listing the fonts used in by system
 */
public enum Fonts {
	TITLE_FONT("DroidSans.ttf", 72),
	BODY_FONT("DroidSans.ttf", 18),
	PANEL_TITLE_FONT("DroidSans.ttf", 36),
	HUD_FONT("DroidSans.ttf", 14);
	
	private static final String FONTS_PATH = "resources/fonts/";
	
	private Font font;
	
	/**
	 * Creates a new Fonts type from a file containing fonts
	 * @param filename file name in the Fonts folder from which the font is to be created
	 * @param size desired font size
	 */
	Fonts(String filename, float size) {
		try {
			InputStream is = Fonts.class.getClassLoader().getResourceAsStream(FONTS_PATH + filename);
			
			this.font = Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
			this.font = this.font.deriveFont(size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the Font font
	 * @return the Font font
	 */
	public java.awt.Font getFont() {
		return this.font;
	}
	
	/**
	 * Loads the fonts
	 */
	public static void init() {
		values();
	}
}
