
/*
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


/**
 * This is a basic class created for resizing image. It works in the Artist class.
 *
 * @author larsonp.
 *         Created Feb 5, 2014.
 */
public class ArtistTools {

/**
 * This is the actual method that does the resizing.It returns a resized image.  
 *
 * @param originalImage
 * @param type
 * @param width
 * @param height
 * @return resized BufferedImage
 */
public static BufferedImage resizeImage(BufferedImage originalImage, int type,
	Integer width, Integer height){

		BufferedImage newImage = new BufferedImage(width, height, type);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
	
		return newImage;
}

}
