/**
 * Highscore Object
 * contains score and other information
 */
public class Highscore {
	private int score, day, month, year;
	private String name;
	
	Highscore(int score, String name, int day, int month, int year){
		this.score = score;
		this.day = day;
		this.month = month;
		this.year = year;
		this.name = name;
	}
	
	public int getScore(){
		return score;
	}
	
	public int[] getDate(){
		 int[] date = {day, month, year};
		 return date;
	}
	
	public String getName(){
		return name;
	}
	
}
