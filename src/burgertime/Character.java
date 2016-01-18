
/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

/**
 * This class describes the object which the player controls in the game itself
 * 
 * @author knispeja. Created Feb 4, 2014.
 */
public class Character implements Updateable {

	// This is how many iterations of the clock pepper spraying lasts
	private final int PEPPER_DURATION = 8;
	// ------------------>
	
	private boolean isPaused = false;
	private int lifePoints;
	private Tile tileLocation;
	private World world;
	private Judge judge;
	private Direction direction;
	private int subX;
	private int subY;
	private int pepperCounter = this.PEPPER_DURATION;
	private boolean falling = false;

	/**
	 * Used by this class and the artist to determine movement speed
	 */
	final public int subScale;

	/**
	 * Half of the subscale variable
	 */
	final public int halfSubScale;

	/**
	 * Creates a character object in the given world on the given tile
	 * 
	 * @param tile
	 * @param world
	 * 
	 * @param atThisTile
	 */
	public Character(Tile tile, World world) {
		this.tileLocation = tile;
		this.world = world;
		this.judge = this.world.getJudge();
		this.subScale = 8;
		this.halfSubScale = this.subScale / 2;
		this.subX = this.halfSubScale;
		this.subY = this.halfSubScale;
		this.lifePoints = this.world.getInitialLives();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#update()
	 */
	@Override
	public void update() {

		if (this.pepperCounter < this.PEPPER_DURATION)
		{
			this.pepperCounter++;
			if(this.pepperCounter == this.PEPPER_DURATION - 1)
				this.world.togglePepperGoing();
		}
		
		Direction directionToMove = this.world.getCommand();

		if (!this.isPaused) {
			if (!directionToMove.getString().equals("none") || this.isFalling()) {
				if(!this.isFalling())
					this.direction = directionToMove;
				else
				{
					this.direction = new Direction("Down");
					directionToMove = this.direction;
				}
				
				boolean isLegal = this.judge.isLegal(this.tileLocation,
						directionToMove);

				if (directionToMove.getX() == 1
						&& (isLegal || this.subX < this.halfSubScale)) {
					this.subX += 1;
				} else if (directionToMove.getX() == -1
						&& (isLegal || this.subX > this.halfSubScale)) {
					this.subX -= 1;
				} else if (directionToMove.getY() == 1
						&& (isLegal || this.subY > this.halfSubScale)) {
					this.subY -= 1;
				} else if ((directionToMove.getY() == -1
						&& (isLegal || this.subY < this.halfSubScale)) || this.isFalling()) {
					this.subY += 1;
				} else {
					this.world.playEffect("src/burgertime/Collision.wav");
				}

				if (this.subX > this.subScale) {
					this.tileLocation = this.judge.getLastTile();
					this.subX = 0;
					this.subY = this.halfSubScale;
				} else if (this.subX < 0) {
					this.tileLocation = this.judge.getLastTile();
					this.subX = this.subScale;
					this.subY = this.halfSubScale;
				} else if (this.subY > this.subScale) {
					
					this.tileLocation = this.judge.getLastTile();
					this.subY = 0;
					this.subX = this.halfSubScale;
					
					if(this.tileLocation.getType() == 6 && this.isFalling())
					{
						this.subY = this.halfSubScale;
						toggleFall();
					}
					
				} else if (this.subY < 0) {
						this.tileLocation = this.judge.getLastTile();
					this.subY = this.subScale;
					this.subX = this.halfSubScale;
				}
				
				if(!this.isFalling())
					this.world.checkBurger();

			}
		}
	}

	/**
	 * Sprays pepper into the adjacent tile or two and stuns any mobs there
	 * 
	 */
	public void sprayPepper() {
		if (this.pepperCounter == this.PEPPER_DURATION && this.world.getPepper() > 0) {
			this.world.playEffect("src/burgertime/Death.wav");
			this.world.togglePepperGoing();
			if (this.judge.isLegal(this.tileLocation, this.direction)) {
				Tile pepperedTile = this.judge.getLastTile();
				Tile pepperedTile2 = null;
				
				if(this.judge.isLegal(pepperedTile, this.direction))
				{
					pepperedTile2 = this.judge.getLastTile();
				}
				
				for (Mob mob : this.world.getMobList()) {
					Tile mobTile = mob.getTile();
					if (mobTile == pepperedTile || mobTile == pepperedTile2)
						mob.stun();
				}
			}

			this.pepperCounter = 0;
			this.world.setPepper(this.world.getPepper() - 1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#remove()
	 */
	@Override
	public void remove() {
		this.world.removeCharacter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#getTile()
	 */
	@Override
	public Tile getTile() {
		return this.tileLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#moveTo(burgertime.Tile)
	 */
	@Override
	public void moveTo(Tile tile) {
		this.tileLocation = tile;
	}

	/**
	 * Gets the subordinate x-coordinate of this object
	 * 
	 * @return subX
	 */
	public int getSubX() {
		return this.subX;
	}

	/**
	 * Gets the suborinate y-coordinate of this object
	 * 
	 * @return subY
	 */
	public int getSubY() {
		return this.subY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#getDirection()
	 */
	@Override
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Gets this object's tile x-coordinate
	 * 
	 * @return tileX
	 */
	public int getX() {
		return this.tileLocation.getX();
	}

	/**
	 * Gets this object's tile y-coordinate
	 * 
	 * @return tileY
	 */
	public int getY() {
		return this.tileLocation.getY();
	}

	/**
	 * Gets this character's number of lives
	 * 
	 * @return lives
	 */
	public int getLife() {
		return this.lifePoints;
	}

	/**
	 * Removes one of this character's lives
	 * 
	 */
	public void takeLife() {
		this.lifePoints--;
	}

	/**
	 * Gives this character one life
	 * 
	 */
	public void giveLife() {
		this.lifePoints++;
	}

	/**
	 * Checks if this object is falling
	 *
	 * @return is falling?
	 */
	public boolean isFalling(){
		return this.falling;
	}
	
	/**
	 * Toggles falling on and off
	 *
	 */
	public void toggleFall(){
		this.falling = !this.falling;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#pauseResume()
	 */
	@Override
	public void pauseResume() {
		this.isPaused = !this.isPaused;
	}
}
