package game;

import game.io.Loader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HighScores implements Serializable, Iterable<HighScores.Score> {
	private static final long serialVersionUID = 1L;
	
	public static final int MAX_SCORES = 10;
	
	private static final String LOAD_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + ".pascores";
	private static HighScores instance;
	
	private List<Score> highScores;
	
	public synchronized static HighScores getInstance() {
		if (instance == null) {
			instance = HighScores.load();
		}
		
		return instance;
	}
	
	public boolean isHighScore(long score) {
		return highScores.isEmpty() || (score > highScores.get(highScores.size() - 1).getScore());
	}
	
	public void submit(long score, String name) {
		int i = 0;
		
		while(i < highScores.size() && score < highScores.get(i).getScore()) {
			i++;
		}
		
		highScores.add(i, new Score(score, name));
		trim();
		save();
	}
	
	public boolean save() {
		return Loader.unload(this, LOAD_PATH);
	}
	
	public void reload() {
		HighScores newScores = load();
		this.highScores = newScores.highScores;
	}
	
	public Iterator<HighScores.Score> iterator() {
		return highScores.iterator();
	}
	
	public String toString() {
		return highScores.toString();
	}
	
	private void trim() {
		while (highScores.size() > MAX_SCORES) {
			highScores.remove(highScores.size() - 1);
		}
	}
	
	private static HighScores load() {
		HighScores h = Loader.load(HighScores.class, LOAD_PATH);
		
		if (h == null) {
			h = new HighScores();
		}
		
		return h;
	}
	
	private HighScores() {
		highScores = new ArrayList<Score>(MAX_SCORES + 1);
	}
	
	public class Score implements Serializable {
		private static final long serialVersionUID = 1L;
		private long score;
		private String name;
		
		Score(long score, String name) {
			this.score = score;
			this.name = name;
		}
		
		public long getScore(){
			return score;
		}

		public String getName(){
			return name;
		}
	}
}
