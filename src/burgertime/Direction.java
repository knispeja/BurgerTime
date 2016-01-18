/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

/**
 * This class describes a cardinal direction
 * 
 * @author knispeja. Created Feb 4, 2014.
 */
public class Direction {
	/**
	 * X component of the direction
	 */
	int x;

	/**
	 * Y component of the direction
	 */
	int y;

	/**
	 * String containing the written representation of the direction
	 */
	String dirString = "none";

	/**
	 * Creates a representation of a direction oriented as dictated by the given
	 * string
	 * 
	 * @param s
	 */
	public Direction(String s) {
		if (s.equals("Up")) {
			this.dirString = s;
			this.x = 0;
			this.y = 1;
		}
		if (s.equals("Down")) {
			this.dirString = s;
			this.x = 0;
			this.y = -1;
		}
		if (s.equals("Left")) {
			this.dirString = s;
			this.x = -1;
			this.y = 0;
		}
		if (s.equals("Right")) {
			this.dirString = s;
			this.x = 1;
			this.y = 0;
		}
		if (this.dirString.equals("none")) {
			this.x = 0;
			this.y = 0;
		}
	}

	/**
	 * Returns the written representation of the direction
	 * 
	 * @return
	 */
	@SuppressWarnings("javadoc")
	public String getString() {
		return this.dirString;
	}

	/**
	 * Returns the x component of the direction
	 * 
	 * @return
	 */
	@SuppressWarnings("javadoc")
	public int getX() {
		return this.x;
	}

	/**
	 * Returns the y component of the direction
	 * 
	 * @return
	 */
	@SuppressWarnings("javadoc")
	public int getY() {
		return this.y;
	}

	/**
	 * Returns a Direction object that is the cardinal opposite of this one.
	 * 
	 * @return opposite direction
	 */
	public Direction getOpposite() {
		if (this.getX() == 1) {
			return new Direction("Left");
		}
		if (this.getX() == -1) {
			return new Direction("Right");
		}
		if (this.getY() == 1) {
			return new Direction("Down");
		}
		if (this.getY() == -1) {
			return new Direction("Up");
		}

		return null;
	}
}
