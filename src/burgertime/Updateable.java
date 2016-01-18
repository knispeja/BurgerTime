/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 * 
 */
package burgertime;

/**
 * TODO Put here a description of what this class does.
 *
 * @author knispeja.
 *         Created Feb 4, 2014.
 */
public interface Updateable {
	/**
	 * Function to be called with each update of World
	 */
	public void update();
	
	/**
	 * Removes the current object
	 */
	public void remove();
	
	/**
	 * Pauses the object if it is unpaused and resumes if it is not
	 */
	public void pauseResume();
	
	/**
	 * Gets the tile at which this object is located
	 * 
	 * @return
	 * Returns the tile the object is located at
	 */
	public Tile getTile();
	
	/**
	 * Gets the direction this object is facing
	 *
	 * @return
	 * Returns the direction of the object
	 */
	public Direction getDirection();
	
	/**
	 * Moves the object to the given tile
	 *
	 * @param tile
	 */
	public void moveTo(Tile tile);
}
