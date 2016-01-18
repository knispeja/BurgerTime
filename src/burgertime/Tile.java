/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

/**
 * Describes a tile contained somewhere in the world's tile list
 * 
 * @author militeja. Created Jan 31, 2014.
 */
public class Tile {

	private int xCoord;
	private int yCoord;

	private int type;

	/**
	 * Creates a tile of the given type
	 *
	 * @param type
	 */
	public Tile(int type) {

		this.type = type;
	}

	/**
	 * Creates a tile of the given type with the given coordinates
	 *
	 * @param type
	 * @param xCoord
	 * @param yCoord
	 */
	public Tile(int type, int xCoord, int yCoord) {
		this.type = type;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	/**
	 * Gets the type of this tile
	 *
	 * @return tile type
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * This sets the type of this tile to the given type
	 *
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Returns the x-coordinate of this tile
	 *
	 * @return x-coord
	 */
	public int getX() {
		return this.xCoord;
	}

	/**
	 * Returns the y-coordinate of this tile
	 *
	 * @return y-coord
	 */
	public int getY() {
		return this.yCoord;
	}

}
