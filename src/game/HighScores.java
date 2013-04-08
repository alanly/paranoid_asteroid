package game;

import game.io.Loader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HighScores implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String LOAD_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + ".pascores";
	private static final int MAX_SCORES = 5;
	
	private List<Long> highScores;
	
	public HighScores() {
		highScores = new ArrayList<Long>(MAX_SCORES + 1);
	}
	
	public boolean isHighScore(long score) {
		return highScores.isEmpty() || (score > highScores.get(0));
	}
	
	public void submit(long score) {
		int i = 0;
		
		while(i < highScores.size() && score < highScores.get(i)) {
			i++;
		}
		
		highScores.add(i, score);
		
		while (highScores.size() > MAX_SCORES) {
			highScores.remove(highScores.size() - 1);
		}
	}
	
	public boolean save() {
		return Loader.unload(this, LOAD_PATH);
	}
	
	public String toString() {
		return highScores.toString();
	}
	
	public static HighScores load() {
		HighScores h = Loader.load(HighScores.class, LOAD_PATH);
		
		if (h == null) {
			h = new HighScores();
		}
		
		return h;
	}
}
