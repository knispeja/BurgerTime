/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

/**
 * TODO This class is the Judge class
 * 
 * @author knispeja. Created Feb 4, 2014.
 */
public class Judge {

	private Tile[][] tilelist;
	private Tile lastSuggestedTile;

	/**
	 * TODO Cunstructs the world
	 * 
	 * @param world
	 */
	public Judge(World world) {
		this.tilelist = world.tilelist;
	}

	/*
	 * Type example
	 * 
	 * 0=Nothing Black space 1=Platform 2=PlatformLadder 3=Ladder
	 */

	/**
	 * Given a tile and a direction, determines if movement in that direction is
	 * legal in terms of the current board state
	 * 
	 * @param startingTile
	 * @param direction
	 * @return whether or not this movement is legal
	 */
	public boolean isLegal(Tile startingTile, Direction direction) {
		if (startingTile == null || direction == null) {
			return false;
		}

		Tile suggestedTile;

		int directx = direction.getX();
		int directy = direction.getY();

		if (directx == 0 && directy == 0) {
			return false;
		}

		int x = startingTile.getX();
		int y = startingTile.getY();

		// If the coordinate system is changed, j will need to be altered
		int i = x + directx;
		int j = y - directy;

		int yLength = this.tilelist.length;
		int xLength = this.tilelist[0].length;

		if (i < xLength && i >= 0 && j < yLength && j >= 0) {
			suggestedTile = this.tilelist[j][i];
			this.lastSuggestedTile = suggestedTile;
		} else {
			return false;
		}

		int tileType = suggestedTile.getType();

		if (tileType != 0) {
			return true;
		}

		return false;
	}

	/**
	 * This is for character objects to get the tile they most recently checked
	 * for legality. Probably shouldn't use this for mob AI or anything besides
	 * character movement as it's pretty specific, but it allows for isLegal to
	 * be used for the AI as well as the character.
	 * 
	 * @return Returns the last tile checked
	 */
	public Tile getLastTile() {
		return this.lastSuggestedTile;
	}
}
