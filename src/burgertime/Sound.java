/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class describes a sound which can be played and stopped
 *
 * @author knispeja.
 *         Created Feb 19, 2014.
 */
public class Sound {
	
	private Clip soundClip;
	
    /**
     * Creates and plays a sound
     *
     * @param fileName
     */
    public synchronized void play(final String fileName) 
    {
    	final Clip clip;
    	
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileName));
			clip.open(inputStream);
			
			this.soundClip = clip;
			
			clip.start();
			
		} catch (LineUnavailableException e1) {
			System.out.println("Error playing sound: " + e1.getMessage());
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Error playing sound: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error playing sound: " + e.getMessage());
		}
    }
    
    /**
     * Stops this sound's clip from playing
     *
     */
    public synchronized void stop(){
    	this.soundClip.stop();
    }
    
    /**
     * Resumes this sound's clip
     *
     */
    public synchronized void resume(){
    	this.soundClip.start();
    }
}