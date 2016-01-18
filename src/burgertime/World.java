/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * Carries all visible entities and handles their updating
 * 
 * @author knispeja. Created Feb 4, 2014.
 */
public class World {

	// These variables dictate how many points different actions are worth
	private final int SCORE_FOR_KILLING_MOBS = 100;
	private final int SCORE_PER_PEPPER_SAVED = 150;
	private final int SCORE_PER_BURGER_PART_FINISHED = 50;
	private final int SCORE_PER_LEVEL_COMPLETE = 500;
	private final int FINAL_LEVEL_BOSS_HP = 5;
	// -------------------------->
	
	// This is makes each action worth double points in hardmode
	private final int HARD_MODE_CONSTANT;
	// -------------------------->
	
	// private final int NEW_LIFE_INTERVAL = 50;

	private String musicSource = "src/burgertime/";
	private boolean hardMode = false;
	private boolean finalLevel;
	private int finalCounter=0;
	private int pepperReserve = 3;
	private int mobCounter = 0;
	private int max_mobs;
	private Boolean win = false;
	private Sound music;
	private boolean justDied = true;
	private boolean paused = false;
	private boolean pepperInAir = false;
	private int score;
	private boolean isEditor;
	private LevelEditor editor;
	private int initialLives;
	private SimulationPanel simPanel;
	private Character player = null;
	private JFrame frame;
	private Artist comp;
	private Judge judge;
	private ArrayList<Burger> burgerList;

	// private boolean musicPaused = false;

	/**
	 * The 2DArray of tiles which describes this world
	 */
	Tile[][] tilelist;
	
	/**
	 * The list of mobs contained in this world
	 */
	private ArrayList<Mob> mobList;

	/**
	 * Creates a world from the given tiles in the given SimulationPanel, on the
	 * given frame. Can be created as a LevelEditor or a regular world, and
	 * should be given score and lives representitive of the current Player
	 * object's score and lives.
	 * 
	 * @param tiles
	 * @param simPanel
	 * @param frame
	 * @param isEditor
	 * @param score
	 * @param lives
	 * @param finalLevel 
	 */
	public World(Tile[][] tiles, SimulationPanel simPanel, JFrame frame,
			boolean isEditor, int score, int lives, boolean finalLevel) {
		
		this.finalLevel = finalLevel;
		this.initialLives = lives;
		this.isEditor = isEditor;
		this.tilelist = tiles;
		this.judge = new Judge(this);
		this.simPanel = simPanel;
		this.frame = frame;
		this.mobList = new ArrayList<Mob>();
		this.burgerList = new ArrayList<Burger>();

		// Initializes the hardmode constant
		if (this.simPanel.getPlayer().isHardMode()) {
			this.hardMode = true;
			if(!this.finalLevel)
			{
				this.musicSource += "Final_Boss.wav";
				this.HARD_MODE_CONSTANT = 2;
			}
			else
			{
				this.musicSource += "True_Final_Boss.wav";
				this.HARD_MODE_CONSTANT = 2;
			}
		} else if(!this.finalLevel && !(this.simPanel.getPlayer().getLevel() == 9)){
			this.HARD_MODE_CONSTANT = 1;
			this.musicSource += "Burgertime.wav";
		}
		else if(this.finalLevel)
		{
			this.HARD_MODE_CONSTANT = 2;
			this.musicSource += "True_Final_Boss.wav";
		}
		else{
			this.HARD_MODE_CONSTANT = 1;
			this.musicSource += "Sanctum.wav";
		}

		// Initializes score
		this.score = this.simPanel.getPlayer().getScore();

		// Initializes mobs and character
		this.max_mobs = 0;
		this.spawnThings(true, true, true);

		this.comp = new Artist(this);
		this.frame.add(this.comp);
		this.frame.setVisible(true);

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Starts music and annoying barking
		if(!this.finalLevel && this.simPanel.getPlayer().getLevel() != 9)
			this.playEffect("src/burgertime/Barking.wav");
		this.startNewMusic(this.musicSource);

		// Initializes an editor if needed
		if (isEditor)
			this.editor = new LevelEditor(this);
	}

	/**
	 * "Respawns" a dead mob at the first mob respawn point that the player is
	 * not occupying
	 * 
	 */
	public void respawnMob() {
		for (Tile[] tileRow : this.tilelist) {
			for (Tile tile : tileRow) {
				if (tile.getType() == 4 && this.player.getTile() != tile) {
					this.mobList.add(new Mob(tile, this, this.hardMode));
					return;
				}
			}
		}
	}

	/**
	 * Can spawn new mobs, a new character, and/or new burgers
	 * 
	 * @param mobs
	 * @param character
	 * @param burgers
	 */
	public void spawnThings(boolean mobs, boolean character, boolean burgers) {
		boolean wasBurger = false;
		BurgerPart currentPart = null;
		ArrayList<BurgerPart> parts = new ArrayList<BurgerPart>();

		for (Tile[] tileRow : this.tilelist) {
			for (Tile tile : tileRow) {
				if (mobs && tile.getType() == 4) {
					this.mobList.add(new Mob(tile, this, this.hardMode));
					this.max_mobs++;
				} else if (character && tile.getType() == 7) {
					if (this.player == null)
						this.addCharacter(tile);
					else
						this.player.moveTo(tile);
				}

				if (burgers && tile.getType() == 5) {
					if (!wasBurger) {
						currentPart = new BurgerPart(this);
						parts.add(currentPart);
					}

					currentPart.addPart(tile);
					wasBurger = true;
				} else
					wasBurger = false;
			}
		}

		if (parts.size() != 0 && burgers) {
			populateBurgerList(parts);
		}

		if (character && this.player == null) {
			this.addCharacter(this.tilelist[0][0]);
		}
	}

	/**
	 * Sorts burger parts into their respective burgers based on location in the
	 * x-direction and size
	 * 
	 * @param allParts
	 */
	public void populateBurgerList(ArrayList<BurgerPart> allParts) {
		while (allParts.size() != 0) {
			int xValue = allParts.get(0).getTile().getX();
			Burger currentBurger = new Burger();

			ArrayList<BurgerPart> stuffToRemove = new ArrayList<BurgerPart>();

			for (BurgerPart part : allParts) {
				if (part.getTile().getX() == xValue) {
					currentBurger.addPart(part);
					stuffToRemove.add(part);
				}
			}

			for (BurgerPart part : stuffToRemove) {
				allParts.remove(part);
			}

			this.burgerList.add(currentBurger);
		}
	}

	/**
	 * Resets mobs and character to their original locations
	 * 
	 */
	public void resetEntities() {
		this.mobList.clear();
		this.max_mobs = 0;
		this.spawnThings(true, true, false);
	}

	/**
	 * Checks if the player is standing on a burger and flags it if necessary
	 * 
	 */
	public void checkBurger() {
		Tile playerTile = this.player.getTile();

		for (Burger burger : this.burgerList) {
			for (BurgerPart part : burger.getParts()) {
				ArrayList<Tile> parts = part.getParts();
				ArrayList<Boolean> flags = part.getFlags();

				for (int i = 0; i < parts.size(); i++) {
					Tile burgerTile = parts.get(i);

					if (burgerTile == playerTile && flags.get(i) != true)
						part.flagPart(burgerTile);
				}
			}
		}
	}

	/**
	 * Checks if all of the burgers have been completed and handles winning if
	 * so
	 * 
	 */
	public void checkWin() {
		boolean success = true;

		for (Burger burger2 : this.burgerList) {
			for (BurgerPart part : burger2.getParts()) {
				if (!part.isFinished())
					success = false;
				break;
			}
		}

		if (success) {
			if(!this.finalLevel)
			{
				this.win = true;
				stopMusic();
				playEffect("src/burgertime/Victory.wav");
			}
			else
			{
				// The boss took damage!!!
				this.finalCounter++;
				playEffect("src/burgertime/Death.wav");
				this.player.toggleFall();
				
				if(this.finalCounter >= this.FINAL_LEVEL_BOSS_HP)
					this.win = true;
				
				this.burgerList.clear();
				spawnThings(false, false, true);
				
				this.comp.flash = true;
				playEffect("src/burgertime/Death2.wav");
			}
		}
	}

	/**
	 * Checks whether or not the player occupies the same place as an unstunned
	 * mob and handles death if so
	 * 
	 */
	public void checkDeath() {
		for (Mob mob : this.mobList) {
			if (!mob.isStunned()) {
				if (mob.getTile() == this.player.getTile()) {
					this.frame.repaint();
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						System.out.println("Sleeping broke~");
					}
					this.playerDeath();
					this.justDied = true;
					return;
				}
			}
		}
	}

	/**
	 * Handles events in case of player death
	 * 
	 */
	public void playerDeath() {
		this.player.takeLife();
		double random = Math.random();

		if (random > .5)
			this.playEffect("src/burgertime/Death.wav");
		else
			this.playEffect("src/burgertime/Death2.wav");

		if (this.player.getLife() == 0) {
			this.win = null;
		}
		this.resetEntities();
		this.simPanel.resetKey();
	}

	/**
	 * The main update function which is called by the SimulationPanel with
	 * every iteration of the clock. Updates the mobs, the player, calls the
	 * Artist to redraw the frame, and checks for special events.
	 * 
	 */
	public void update() {
		if (!this.isEditor) {
			if (!this.paused) {
				if (this.win == null) {
					// The player has lost
					stopMusic();

					try {
						Thread.sleep(20);
						playEffect("src/burgertime/Howl.wav");
						Thread.sleep(100);
					} catch (Exception e) {
						System.out
								.println("Error sleeping and/or playing sound: "
										+ e.getMessage());
					}

					this.simPanel.killThread();
				} else if (this.win == true) {
					// The player has won!
					addToScore(this.SCORE_PER_PEPPER_SAVED * this.pepperReserve
							* this.HARD_MODE_CONSTANT);
					addToScore(this.SCORE_PER_LEVEL_COMPLETE
							* this.HARD_MODE_CONSTANT);

					this.simPanel.win(this.score, this.player.getLife(), false);
				} else {
					// The player is still playing so keep updating

					if (!this.finalLevel && this.mobList.size() < this.max_mobs) {
						this.mobCounter++;
						if (this.mobCounter == 50) {
							respawnMob();
							this.mobCounter = 0;
						}
					}

					String currentKey = this.simPanel.getKey();

					this.justDied = false;

					// Updates the player
					this.player.update();
					this.checkDeath();

					// Sprays pepper if necessary
					if (currentKey == "Space")
						this.player.sprayPepper();

					if (this.justDied == false) {
						// Updates the mobs
						if (this.mobList != null) {
							for (Mob mob : this.mobList) {
								if (mob != null) {
									mob.update();
								}
							}
						}
						this.checkDeath();

						// Updates the falling burgers
						for (Burger burger : this.burgerList) {
							for (BurgerPart burgerPart : burger.getParts()) {
								if (burgerPart.isFalling()) {
									ArrayList<Mob> mobsToRemove = new ArrayList<Mob>();

									for (Mob mob : this.mobList) {
										for (Tile partPart : burgerPart
												.getParts()) {
											if (partPart == mob.getTile()) {
												// The mob is crushed
												mobsToRemove.add(mob);
											}
										}
									}

									burgerPart.update();

									for (Mob mob : this.mobList) {
										for (Tile partPart : burgerPart
												.getParts()) {
											if (partPart == mob.getTile()) {
												// The mob is crushed
												mobsToRemove.add(mob);
											}
										}
									}

									for (Mob mob : mobsToRemove) {
										// Actually removes any crushed mobs (no
										// duped code)
										mob.remove();
										addToScore(this.SCORE_FOR_KILLING_MOBS
												* this.HARD_MODE_CONSTANT);
									}

									Tile partTile = burgerPart.getTile();

									if (partTile.getType() == 5
											|| partTile.getType() == 1) {
										// The part lands on a platform
										burgerPart.stopFalling();
									}

									// Makes sure the part didn't land on
									// another
									for (BurgerPart burgerPart2 : burger
											.getParts()) {
										if (burgerPart != burgerPart2
												&& partTile == burgerPart2
														.getTile()) {
											// The part lands on another part
											burgerPart.stopFalling();

											if (!burgerPart2.isFinished())
												// The part knocks another one
												// down
												burgerPart2.fall();
											else {
												// The part landed on a finished
												// part
												burgerPart
														.moveTo(this.tilelist[burgerPart
																.getTile()
																.getY() - 1][burgerPart
																.getTile()
																.getX()]);
												if (!burgerPart.isFinished()) {
													addToScore(this.SCORE_PER_BURGER_PART_FINISHED
															* this.HARD_MODE_CONSTANT);
												}
												burgerPart.finish();
												checkWin();
											}

										}
									}

									if (partTile.getType() == 6) {
										// The part lands on the final platform
										if (!burgerPart.isFinished()) {
											addToScore(this.SCORE_PER_BURGER_PART_FINISHED
													* this.HARD_MODE_CONSTANT);
										}

										burgerPart.finish();
										checkWin();
									}
								}
							}
						}
					}

					this.frame.repaint();
				}
			}
		} else {
			// Editor code here
			this.editor.update();
		}
	}

	/**
	 * Gets the current key from the SimulationPanel and converts it into a
	 * direction that the player character can use
	 * 
	 * @return the direction the user is pressing
	 */
	public Direction getCommand() {
		String keyString = this.simPanel.getKey();

		// if(!this.simPanel.soundOn && keyString.equals("M")){
		// if(this.musicPaused){
		// resumeMusic();
		// this.musicPaused = false;
		// this.simPanel.resetKey();
		// }
		// else{
		// stopMusic();
		// this.musicPaused = true;
		// }
		// }

		// this.simPanel.resetKey();
		return new Direction(keyString);
	}

	/**
	 * Checks whether or not the world is paused
	 * 
	 * @return if this object is paused
	 */
	public boolean isPaused() {
		return this.paused;
	}

	/**
	 * Pause/resumes the whole world
	 * 
	 */
	public void pauseResume() {
		this.paused = !this.paused;
	}

	/**
	 * Tells whether or not this object is/has a LevelEditor
	 * 
	 * @return if this is an editor
	 */
	public boolean isEditor() {
		return this.isEditor;
	}

	/**
	 * Adds the given value to the score
	 * 
	 * @param numToAdd
	 */
	public void addToScore(int numToAdd) {

		// int cutOff = 0;
		//
		// for (int i = 1; i < 1000; i++) {
		// if (this.score < this.NEW_LIFE_INTERVAL * i) {
		// cutOff = i;
		// break;
		// }
		// }

		this.score += numToAdd;

		// if(this.score > this.NEW_LIFE_INTERVAL*(cutOff))
		// this.player.giveLife();
	}

	/**
	 * Starts a new music track (different from sound effects in that it can be
	 * paused with some stopMusic()
	 * 
	 * @param string
	 */
	public void startNewMusic(String string) {
		if (this.simPanel.soundOn) {
			this.music = new Sound();
			this.music.play(string);
		} else if (this.isEditor) {
			this.music = new Sound();
			this.music.play("src/burgertime/Smooth.wav");
		}
	}

	/**
	 * Starts a new sound effect if the game is not muted
	 * 
	 * @param string
	 */
	public void playEffect(String string) {
		if (this.simPanel.soundOn) {
			new Sound().play(string);
		}
	}

	/**
	 * Starts a new sound effect even if the game is muted
	 * 
	 * @param string
	 */
	public void playOverrideEffect(String string) {
		new Sound().play(string);
	}

	/**
	 * Stops the current music track
	 * 
	 */
	public void stopMusic() {
		if (this.music != null)
			this.music.stop();
	}

	/**
	 * Resumes a paused music track
	 * 
	 */
	public void resumeMusic() {
		this.music.resume();
	}

	/**
	 * Changes the world's stored character to a new character added to the
	 * world at the given tile
	 * 
	 * @param atThisTile
	 */
	public void addCharacter(Tile atThisTile) {
		this.player = new Character(atThisTile, this);
	}

	/**
	 * Gracefully removes the world's player character
	 * 
	 */
	public void removeCharacter() {
		this.player = null;
	}

	/**
	 * Removes the given mob from the world
	 * 
	 * @param mob
	 */
	public void removeMob(Mob mob) {
		Mob mobToRemove = null;
		for (Mob searchMob : this.mobList) {
			if (searchMob == mob) {
				mobToRemove = searchMob;
			}
		}

		this.mobList.remove(mobToRemove);
	}

	/**
	 * Returns the current score stored in this object
	 * 
	 * @return current score
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Returns the containing SimulationPanel
	 * 
	 * @return the SimulationPanel
	 */
	public SimulationPanel getPanel() {
		return this.simPanel;
	}

	/**
	 * Returns the list of tiles describing the world
	 * 
	 * @return the list of tiles desciribing the world
	 */
	public Tile[][] getTileList() {
		return this.tilelist;
	}

	/**
	 * Returns the frame the world is drawn on
	 * 
	 * @return this object's frame
	 */
	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Returns the editor of the world (Will return null if this world is not a
	 * level editor)
	 * 
	 * @return this object's editor
	 */
	public LevelEditor getEditor() {
		return this.editor;
	}

	/**
	 * Returns a boolean describing whether or not this world is in hardmode
	 * 
	 * @return if the world is in hardmode
	 */
	public boolean isHard() {
		return this.hardMode;
	}

	/**
	 * Returns the number of lives the player had when the world initialized
	 * 
	 * @return number of lives
	 */
	public int getInitialLives() {
		return this.initialLives;
	}

	/**
	 * Returns the current player character existing in the world, if any
	 * 
	 * @return current player
	 */
	public Character getHero() {
		return this.player;
	}

	/**
	 * Returns the current player's reserve of pepper
	 * 
	 * @return pepper reserve
	 */
	public int getPepper() {
		return this.pepperReserve;
	}

	/**
	 * Returns the world's artist
	 * 
	 * @return artist
	 */
	public Artist getArtist() {
		return this.comp;
	}
	
	/**
	 * Returns whether or not the player has won
	 *
	 * @return win?
	 */
	public Boolean getWin(){
		return this.win;
	}
	
	/**
	 * Returns this object's judge used for judging movement legality
	 *
	 * @return judge
	 */
	public Judge getJudge(){
		return this.judge;
	}
	
	/**
	 * Gets the list of burgers contained in this world
	 *
	 * @return burger list
	 */
	public ArrayList<Burger> getBurgerList(){
		return this.burgerList;
	}
	
	/**
	 * Gets the list of mobs contained in this world
	 *
	 * @return mob list
	 */
	public ArrayList<Mob> getMobList(){
		return this.mobList;
	}
	
	/**
	 * Tells whether or not this is the final level
	 *
	 * @return is this the final level?
	 */
	public boolean isFinalLevel(){
		return this.finalLevel;
	}
	
	/**
	 * Gets a boolean describing whether or not pepper
	 * is currently being thrown by the player character
	 *
	 * @return whether or not pepper is in the air
	 */
	public boolean isPepperGoing(){
		return this.pepperInAir;
	}
	
	/**
	 * Sets the reserve of pepper to the given number
	 * 
	 * @param number
	 */
	public void setPepper(int number) {
		this.pepperReserve = number;
	}
	
	/**
	 * Toggles whether or not pepper is in the air
	 * between true and false when necessary
	 *
	 */
	public void togglePepperGoing(){
		this.pepperInAir = !this.pepperInAir;
	}
	
	public int totalBossLife(){
		return this.FINAL_LEVEL_BOSS_HP;
	}
}
