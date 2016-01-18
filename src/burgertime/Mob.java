/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.util.ArrayList;

/**
 * This class describes the enemies which chase the user in the game
 * 
 * @author knispeja. Created Feb 9, 2014.
 */
public class Mob implements Updateable {

	final private int MOB_STUN_TIME = 45;

	private boolean isFast;
	private Direction[] options = new Direction[4];
	private World world;
	private boolean isPaused = false;
	private boolean isStunned = true;
	private Tile tileLocation;
	private Judge judge;
	private Direction direction;
	private int stunCounter = 2;
	private Tile tileToGo;
	private int subX;
	private int subY;
	private boolean isGoing;

	/**
	 * Used by this class and the artist to determine movement speed
	 */
	final public int subScale;

	/**
	 * Half of the subScale variable
	 */
	final public int halfSubScale;

	/**
	 * Creates a new mob in the given world at the given tile
	 * 
	 * @param tile
	 * @param world
	 * @param isFast
	 */
	public Mob(Tile tile, World world, boolean isFast) {

		this.isFast = isFast;

		this.direction = null;

		this.options[0] = new Direction("Up");
		this.options[1] = new Direction("Down");
		this.options[2] = new Direction("Left");
		this.options[3] = new Direction("Right");

		this.world = world;
		this.judge = this.world.getJudge();

		this.tileLocation = tile;

		if (this.isFast) {
			this.subScale = 8;
		} else {
			this.subScale = 10;
		}
		this.halfSubScale = this.subScale / 2;

		this.subX = this.halfSubScale;
		this.subY = this.halfSubScale;
		this.isGoing = false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#update()
	 */
	@Override
	public void update() {
		if (!this.isPaused && !this.isStunned) {
			if (!this.isGoing) {
				ArrayList<Tile> validOptions = new ArrayList<Tile>();
				ArrayList<Direction> validDirections = new ArrayList<Direction>();

				Direction opposite;

				if (this.direction != null) {
					opposite = this.direction.getOpposite();
				} else {
					opposite = null;
				}

				for (Direction direction2 : this.options) {

					String oppString;

					if (opposite != null)
						oppString = opposite.getString();
					else
						oppString = "x";
					if ((this.isFast || (!oppString.equals(direction2
							.getString())))
							&& this.judge
									.isLegal(this.tileLocation, direction2)) {

						validOptions.add(this.judge.getLastTile());
						validDirections.add(direction2);
					}
				}

				float validSize = validOptions.size();
				// Turns around

				if (validSize == 0) {
					if (this.judge.isLegal(this.tileLocation, opposite)) {

						this.tileToGo = this.judge.getLastTile();
						this.direction = opposite;
					}
				} else {

					// Chooses a random movement option and performs it
					// Intersection decision

					int dX = this.world.getHero().getX()
							- this.tileLocation.getX();
					int dY = this.world.getHero().getY()
							- this.tileLocation.getY();

					if (dX != 0) {
						dX = dX / Math.abs(dX);
					}
					if (dY != 0) {
						dY = -dY / Math.abs(dY);
					}
					int result = 0;

					for (int i = 0; i < validDirections.size(); i++) {
						if (dX == validDirections.get(i).getX() && dX != 0) {
							result = i;
							break;
						}
						if (dY == validDirections.get(i).getY() && dY != 0) {
							result = i;
							break;

						}
					}

					this.tileToGo = validOptions.get(result);
					this.direction = validDirections.get(result);

					this.isGoing = true;
				}
			}

			if(this.direction != null)
			{
				if (this.direction.getX() == 1) {
					this.subX += 1;
				} else if (this.direction.getX() == -1) {
					this.subX -= 1;
				} else if (this.direction.getY() == 1) {
					this.subY -= 1;
				} else if (this.direction.getY() == -1) {
					this.subY += 1;
				}
			}

			if (this.subX > this.subScale) {
				this.moveTo(this.tileToGo);
				this.subX = 0;
				this.subY = this.halfSubScale;

			} else if (this.subX < 0) {
				this.moveTo(this.tileToGo);
				this.subX = this.subScale;
				this.subY = this.halfSubScale;

			} else if (this.subY > this.subScale) {
				this.moveTo(this.tileToGo);

				this.subY = 0;
				this.subX = this.halfSubScale;

			} else if (this.subY < 0) {
				this.moveTo(this.tileToGo);
				this.subY = this.subScale;
				this.subX = this.halfSubScale;

			}
			if (this.subX == this.halfSubScale
					&& this.subY == this.halfSubScale) {
				this.isGoing = false;
			}

		} else {
			if (this.isStunned) {
				if (this.stunCounter == this.MOB_STUN_TIME)
					this.isStunned = false;
				else
					this.stunCounter++;
			}
		}
	}

	/**
	 * Stuns this mob
	 * 
	 */
	public void stun() {
		this.isStunned = true;
		this.stunCounter = 0;
		// this.direction = null;
	}

	/**
	 * Checks if this mob is stunned
	 * 
	 * @return is stunned?
	 */
	public boolean isStunned() {
		return this.isStunned;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#remove()
	 */
	@Override
	public void remove() {
		this.world.removeMob(this);
	}

	/**
	 * Gets this mob's subordinate x-coordinate
	 * 
	 * @return subX
	 */
	public int getSubX() {
		return this.subX;

	}

	/**
	 * Gets this mob's subordinate y-coordinate
	 * 
	 * @return subY
	 */
	public int getSubY() {
		return this.subY;
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
	 * @see burgertime.Updateable#getDirection()
	 */
	@Override
	public Direction getDirection() {
		return this.direction;
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
