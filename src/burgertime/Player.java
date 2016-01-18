/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

/**
 * TODO The class for the player
 *
 * @author militeja.
 *         Created Feb 8, 2014.
 */
public class Player {
	private int level;
	//private int highScore;
	private int score;
	private int lives;
	private String name;
	private boolean hardMode;
	
	/**
	 * 
	 * TODO Constructs a player who is just starting
	 *
	 * @param name - the name
	 * @param isHardMode - the difficulty
	 */
	public Player(String name, boolean isHardMode){
		this.hardMode = isHardMode;
		this.lives = 5;
		this.score = 0;
		this.name=name;
		//this.highScore=0;
		this.level=1;
	}
	
	/**
	 * 
	 * TODO Recreates an alread saved player
	 *
	 * @param name - the name
	 * @param level - the level
	 * @param lives - the lives the player has
	 * @param difficulty - the difficulty the player is on
	 * @param score - the players current score
	 */
	public Player(String name,  int level, int lives, boolean difficulty,int score){
		//this.hardMode = isHardMode;
		this.name=name;
		//this.highScore=score;
		this.level=level;
		if(this.level<=0){
			level = 1;
		}
		this.lives = lives;
		this.hardMode = difficulty;
		this.score = score;
	}
	
	/**
	 * 
	 * TODO Sets the lives of the player
	 *
	 * @param lives
	 */
	public void setLives(int lives)
	{
		this.lives = lives;
	}
	
	/**
	 * 
	 * TODO Gets the lives of the player
	 *
	 * @return the number of lives
	 */
	public int getLives()
	{
		return this.lives;
	}
	
	/**
	 * 
	 * TODO Sets the score of the player
	 *
	 * @param score - the player's score
	 */
	public void setScore(int score){
		this.score = score;
	}
	
	/**
	 * 
	 * TODO Gets the player's score
	 *
	 * @return the player's score
	 */
	public int getScore()
	{
		return this.score;
	}
	
	/**
	 * 
	 * TODO Gets the player's level
	 *
	 * @return the level the player is on
	 */
	public int getLevel(){
		return this.level;
	}
	
	//public int getHighScore(){
	//	return this.highScore;
	//}
	
	/**
	 * 
	 * TODO Gets the name of the player
	 *
	 * @return the player's name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * 
	 * TODO Prints out the player info
	 *
	 */
	public  void getInfo(){
		System.out.println(this.name);
		//System.out.println(this.highScore);
		System.out.println(this.level);
	}
	
	/**
	 * 
	 * TODO Sets the player's level
	 *
	 * @param level - the player's level
	 */
	public void setLevel(int level){
		this.level=level;
	}
	
	/**
	 * 
	 * TODO Sees the difficulty of the player
	 *
	 * @return the player's difficulty
	 */
	public boolean isHardMode(){
		return this.hardMode;
	}
	
	/**
	 * 
	 * TODO Sees the difficulty of the player
	 *
	 * @return the player's difficulty
	 */
	public boolean getDifficult(){
		return this.hardMode;
	}
}
