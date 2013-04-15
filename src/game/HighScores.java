package game;


import io.Loader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Highscores implements the Serializable interface and represents the list of top scores achieved in the game, which can be loaded and unloaded on a memory file.
 * It implements the singleton design pattern.
 *
 */
public class HighScores implements Serializable, Iterable<HighScores.Score> {
	private static final long serialVersionUID = 1L;
	
	public static final int MAX_SCORES = 15;
	
	private static final String LOAD_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + ".pascores";
	private static HighScores instance;
	
	private List<Score> highScores;
	/**
	 * Creates and returns new Highscores if there is no instance of it. Returns the <tt>instance</tt> otherwise. 
	 * @return singleton instance of Highscores 
	 */
	public synchronized static HighScores getInstance() {
		if (instance == null) {
			instance = HighScores.load();
		}
		
		return instance;
	}
	/**
	 * checks if the score is a highscore 
	 * @param score score to be checked
	 * @return <tt>true</tt> if it is a highscore. False otherwise.
	 */
	public boolean isHighScore(long score) {
		return score > 0 && (highScores.size() < MAX_SCORES || (score > highScores.get(highScores.size() - 1).getScore()));
	}
	/**
	 * Adds ascore to <tt>highScores</tt> if it is a highscore.
	 * @param score score to be submitted the list 
	 * @param name name associated with the score.
	 */
	public void submit(long score, String name) {
		int i = 0;
		
		while(i < highScores.size() && score < highScores.get(i).getScore()) {
			i++;
		}
		
		highScores.add(i, new Score(score, name));
		trim();
		save();
	}
	/**
	 * Unloads the object to the load file 
	 * @return <tt>true</tt> if the unloading is successful
	 */
	public boolean save() {
		return Loader.unload(this, LOAD_PATH);
	}
	/**
	 * Loads the highscores from the load file and assignes them to <tt>highScores</tt>
	 */
	public void reload() {
		HighScores newScores = load();
		this.highScores = newScores.highScores;
	}
	/**
	 * @return an Iterator for <tt>highScores</tt> 
	 */
	public Iterator<HighScores.Score> iterator() {
		return highScores.iterator();
	}
	/**
	 * @return String representation of the object
	 */
	public String toString() {
		return highScores.toString();
	}
	/**
	 * Removes the last highscore from <tt>highscores</tt> until the size of the ArrayList is <tt>MAX_SCORES</tt>
	 */
	private void trim() {
		while (highScores.size() > MAX_SCORES) {
			highScores.remove(highScores.size() - 1);
		}
	}
	/**
	 * 
	 * @return <tt>Highscores</tt> object loaded from the file at <tt>LOAD_PATH</tt>
	 */
	private static HighScores load() {
		HighScores h = Loader.load(HighScores.class, LOAD_PATH);
		
		if (h == null) {
			h = new HighScores();
		}
		
		return h;
	}
	/**
	 * creates a new <tt>Score</tt> ArrayList of size <tt>MAX_SCORES + 1</tt> and assigns it to <tt>highScores</tt> 
	 */
	private HighScores() {
		highScores = new ArrayList<Score>(MAX_SCORES + 1);
	}
	/**
	 * 
	 * Score is a Nested Class which implements the Serializable interface and has consists of a name and a score.
	 *
	 */
	public class Score implements Serializable {
		private static final long serialVersionUID = 1L;
		private long score;
		private String name;
		/**
		 * Creates a new score
		 * @param score <tt>score</tt> of the new Score
		 * @param name <tt>name</tt> of the new Score
 		 */
		Score(long score, String name) {
			this.score = score;
			this.name = name;
		}
		/**
		 * 
		 * @return the <tt>score</tt> of the object
		 */
		public long getScore(){
			return score;
		}
		/**
		 * 
		 * @return the <tt>name</tt> of the object
		 */
		public String getName(){
			return name;
		}
	}
}
