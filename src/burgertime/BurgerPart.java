/*
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.util.ArrayList;

/**
 * This class describes the distinct parts of each burger, the chains of burger
 * tiles which appear to be a single entity. Handles the player walking on this
 * object and falling, etc.
 * 
 * @author knispeja. Created Feb 12, 2014.
 */
public class BurgerPart implements Updateable {

	private World world;
	private Tile[][] tilelist;

	private boolean finished = false;
	private boolean paused = false;
	private boolean falling = false;

	private ArrayList<Tile> parts = new ArrayList<Tile>();
	private ArrayList<Boolean> flags = new ArrayList<Boolean>();

	/**
	 * Creates a new burger ingredient to the given world
	 * 
	 * @param world
	 */
	public BurgerPart(World world) {
		this.world = world;
		this.tilelist = this.world.tilelist;
	}

	/**
	 * Adds a new tile to the list of tiles this object contains
	 * 
	 * @param part
	 */
	public void addPart(Tile part) {
		this.parts.add(part);
		this.flags.add(false);
	}

	/**
	 * Flags an individual tile in this object as being "pressed" by the
	 * character
	 * 
	 * @param part
	 */
	public void flagPart(Tile part) {
		for (int i = 0; i < this.parts.size(); i++) {
			if (this.parts.get(i) == part) {
				this.flags.set(i, true);
				break;
			}
		}

		for (boolean flagged : this.flags) {
			if (flagged == false)
				return;
		}

		this.falling = true;
	}

	/**
	 * Returns a list of all the tiles this part is contained in
	 * 
	 * @return the tiles this part is in
	 */
	public ArrayList<Tile> getParts() {
		return this.parts;
	}

	/**
	 * Returns a list of all the flags for the tiles this part is contained in
	 * 
	 * @return flaglist corresponding to the partslist
	 */
	public ArrayList<Boolean> getFlags() {
		return this.flags;
	}

	/**
	 * Returns whether or not this object is falling
	 * 
	 * @return is falling?
	 */
	public boolean isFalling() {
		return this.falling;
	}

	/**
	 * Marks this object as finished (it is a part of the final burger)
	 * 
	 */
	public void finish() {
		this.finished = true;
		stopFalling();
	}

	/**
	 * Checks whether or not this object is finished
	 * 
	 * @return is finished?
	 */
	public boolean isFinished() {
		return this.finished;
	}

	/**
	 * Stops this object from falling
	 * 
	 */
	public void stopFalling() {
		this.falling = false;

		for (int i = 0; i < this.flags.size(); i++) {
			this.flags.set(i, false);
		}
	}

	/**
	 * Causes this object to begin falling
	 * 
	 */
	public void fall() {
		this.falling = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#update()
	 */
	@Override
	public void update() {
		if (!this.paused) {
			for (int i = 0; i < this.parts.size(); i++) {
				Tile oldTile = this.parts.get(i);
				this.parts.set(i,
						this.tilelist[oldTile.getY() + 1][oldTile.getX()]);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#remove()
	 */
	@Override
	public void remove() {
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#pauseResume()
	 */
	@Override
	public void pauseResume() {
		this.paused = !this.paused;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#getTile()
	 */
	@Override
	public Tile getTile() {
		return this.parts.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#getDirection()
	 */
	@Override
	public Direction getDirection() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see burgertime.Updateable#moveTo(burgertime.Tile)
	 */
	@Override
	public void moveTo(Tile tile) {
		for (int i = 0; i < this.parts.size(); i++) {
			this.parts.set(i, this.tilelist[tile.getY()][tile.getX() + i]);
		}
	}
}
