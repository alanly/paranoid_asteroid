package game;

import java.awt.Font;
import java.io.InputStream;

public enum Fonts {
	TITLE_FONT("DroidSans-Bold.ttf", 72),
	BODY_FONT("DroidSans.ttf", 14),
	PANEL_TITLE_FONT("DroidSans.ttf", 36),
	HUD_FONT("DroidSans.ttf", 14);
	
	private static final String FONTS_PATH = "resources/fonts/";
	
	private Font font;
	
	Fonts(String filename, float size) {
		try {
			InputStream is = Fonts.class.getClassLoader().getResourceAsStream(FONTS_PATH + filename);
			
			this.font = Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
			this.font = this.font.deriveFont(size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public java.awt.Font getFont() {
		return this.font;
	}
	
	public static void init() {
		// Load the fonts
		values();
	}
}
