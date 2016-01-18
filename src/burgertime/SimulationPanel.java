/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author militeja. Created Jan 31, 2014.
 */
public class SimulationPanel {
	private static final long UPDATE_INTERVAL_MS = 40;
	private static final long SHORT_UPDATE_INTERVAL_MS = 100;

	private boolean levedit;

	private Thread currentThread;
	private int[][] coordinate;
	private String key = "none";
	private Loader load;
	private Player thePlayer;
	public World currentWorld;
	public JFrame deathFrame;
	public JFrame frame;
	public boolean soundOn;
	public final boolean hardMode;
	public boolean loadCustom;

	public SimulationPanel(Loader load, Player thePlayer, boolean soundOn,
			boolean levedit) {
		this.levedit = levedit;
		this.thePlayer = thePlayer;
		this.hardMode = thePlayer.isHardMode();
		this.load = load;
		this.soundOn = soundOn;
		JFrame frame = new JFrame("TEST");
		Keyboard theKey = new Keyboard(this);
		this.loadCustom = false;
		frame.add(theKey);
		frame.setSize(700, 740);

		this.frame = frame;

		final SaveButton saver = new SaveButton(load, thePlayer, this);

		class SaverClass implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				saver.save();
			}
		}
		SaverClass aSaver = new SaverClass();
		saver.setAction(aSaver);
		this.frame.add(saver.getPanel(), BorderLayout.SOUTH);
	}

	public void loadACustom() {
		this.loadCustom = true;
	}

	/**
	 * Changes what key is stored
	 * 
	 * @param s
	 */
	public void whatKey(String s) {
		this.key = s;
	}

	/**
	 * Deals with what happens when a key is released
	 * 
	 * @param s
	 */
	public void keyReleased(String s) {
		if (this.key == s)
			resetKey();
	}

	/**
	 * Gets what key is currently being pressed from the Keyboard
	 * 
	 * @return what key is pressed
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Resets the stored key being pressed
	 * 
	 */
	public void resetKey() {
		this.key = "none";
	}

	/**
	 * Loads and runs a world depending on what this object's contained Player
	 * object dictates it should load
	 * 
	 * @throws IOException
	 */
	public void load() throws IOException {
		/*
		 * Uncommit the following line and commmit the line below this one.
		 */
		// this.coordinate = load.loadTiles();
		if (this.loadCustom) {
			this.coordinate = this.load.loadTiles();
		} else {
			this.coordinate = this.load.loadLevel(this.thePlayer);
		}

		Tile[][] worldTiles = new Tile[this.coordinate.length][this.coordinate[0].length];

		for (int counter = 0; counter < this.coordinate.length; counter++) {
			for (int lcv = 0; lcv < this.coordinate[0].length; lcv++) {
				worldTiles[counter][lcv] = new Tile(
						this.coordinate[counter][lcv], lcv, counter);
				//System.out.print(worldTiles[counter][lcv].getType());

			}
		//	System.out.println();
		}

		if (this.levedit == false) {

			// Creates and initializes the world
			final World theWorld = new World(worldTiles, this, this.frame,
					false, this.thePlayer.getScore(), this.thePlayer.getLives(), this.thePlayer.getLevel() == 8);
			this.currentWorld = theWorld;

			Runnable tickTock = new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							if (!theWorld.isPaused())
								Thread.sleep(UPDATE_INTERVAL_MS);
							else
								Thread.sleep(SHORT_UPDATE_INTERVAL_MS);

							theWorld.update();

							String keyText = SimulationPanel.this.getKey();

							if (keyText.equals("P") || keyText.equals("Escape")) {
								theWorld.pauseResume();
								resetKey();
							} else if (keyText.equals("U")) {
								resetKey();
								//changePlayerLevel(1);
								win(0, 2, false);
							} else if (keyText.equals("D")) {
								resetKey();
								//changePlayerLevel(-1);
								win(0, 2, true);
							}
						}
					} catch (InterruptedException exception) {
						// Stop when interrupted
					}
				}
			};

			Thread thread = new Thread(tickTock);
			this.currentThread = thread;
			thread.start();

		} else {
			// This is all for the level editor
			final World theWorld = new World(worldTiles, this, this.frame,
					true, 0, 15, false);
			this.currentWorld = theWorld;
			Runnable tickTock = new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {

							Thread.sleep(UPDATE_INTERVAL_MS);

							theWorld.update();
						}
					} catch (InterruptedException exception) {
						// Stop when interrupted
					}
				}
			};

			Thread thread = new Thread(tickTock);
			this.currentThread = thread;
			thread.start();
		}

	}

	/**
	 * Handles what happens if the player has won or has tried to jump to a
	 * different level (even if the player didn't actually win)
	 * 
	 * @param playerScore
	 * @param playerLives
	 * @param down
	 */
	public void win(int playerScore, int playerLives, boolean down) {
		int upOrDown;
		if (down)
			upOrDown = -1;
		else
			upOrDown = 1;

		this.thePlayer.setLevel(this.thePlayer.getLevel() + upOrDown);
		this.thePlayer.setScore(this.thePlayer.getScore() + playerScore);
		this.thePlayer.setLives(playerLives);

	//	System.out.println(this.thePlayer.getName() + " is going to ");
	//	System.out.println("level " + this.thePlayer.getLevel());
		SimulationPanel thePanel = new SimulationPanel(this.load,
				this.thePlayer, this.soundOn, false);
		try {
			thePanel.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		killThread();
	}

	/**
	 * Disposes of the current world thread and frame and displays the game over
	 * frame if necessary
	 * 
	 */
	public void killThread() {
		this.frame.dispose();
		this.currentWorld.stopMusic();
		if (this.currentWorld.getWin() == null) {
			this.deathFrame = new JFrame();

			this.deathFrame.setSize(new Dimension(700, 800));
			this.deathFrame.setTitle("wow disapoint");
			this.deathFrame.add(new deathArtist(this.deathFrame));
			// this.deathFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.deathFrame.setVisible(true);
		}
		try {
			this.currentThread.join();
		} catch (InterruptedException e) {
			System.out.println("Error joining thread: " + e.getMessage());
		}
	}

	/**
	 * Gets this object's stored player object
	 *
	 * @return player object
	 */
	public Player getPlayer() {
		return this.thePlayer;
	}

	/**
	 * Saves the actual world tiles to a file to be loaded later
	 *
	 */
	public void saveWorld() {
		LevelEditor editor = this.currentWorld.getEditor();

		if (editor != null)
			editor.save();
		else
			System.out.println("No editor to save with!");
	}

	/**
	 * Changes this object's player's level by the given number
	 *
	 * @param numToChangeBy
	 */
	public void changePlayerLevel(int numToChangeBy)
	{
		this.thePlayer.setLevel(this.thePlayer.getLevel() + numToChangeBy);
	}
	
	public class deathArtist extends JComponent {
		JFrame frame;
		BufferedImage deathImage;
		BufferedImage tempdeathImage;
		Graphics2D g2;

		public deathArtist(JFrame frame) {
			this.frame = frame;
			try {
				this.deathImage = ImageIO.read(new File(
						"src/burgertime/Upset.jpg"));
			} catch (Exception e) {
				System.err.println("You've failed in every way");
				this.deathImage = null;
			}

		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintChildren(g);
			this.g2 = (Graphics2D) g;
			if (this.tempdeathImage == null) {
				this.tempdeathImage = ArtistTools.resizeImage(this.deathImage,
						this.deathImage.getType(), this.frame.getWidth(),
						this.frame.getHeight() - 50);
			}
			this.g2.drawImage(this.tempdeathImage, null, 0, 0);

		}
	}
}
