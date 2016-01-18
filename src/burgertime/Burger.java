
/*
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.util.ArrayList;

/**
 * This class is meant to contain a number of BurgerParts which make up a main
 * burger (described by this very object!)
 * 
 * @author knispeja. Created Feb 12, 2014.
 */
public class Burger {

	private ArrayList<BurgerPart> parts = new ArrayList<BurgerPart>();

	/**
	 * Adds a BurgerPart to this burger
	 * 
	 * @param part
	 */
	public void addPart(BurgerPart part) {
		this.parts.add(part);
	}

	/**
	 * Gets a list of all of the BurgerParts contained by this burger
	 * 
	 * @return this burger's burgerparts
	 */
	public ArrayList<BurgerPart> getParts() {
		return this.parts;
	}
}
