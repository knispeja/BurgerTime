
/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * This class handles many things that the world would normally do, but need to
 * be different for the level editor world than for the normal worlds. Basically
 * controls how the level editor works.
 * 
 * @author knispeja. Created Feb 18, 2014.
 */
public class LevelEditor {
	private final JFileChooser chooser;
	private Artist comp;
	private World world;
	private JFrame frame;
	private SimulationPanel panel;
	private Tile[][] tiles;
	private int boxsize = 1;
	private int tileType = 0;

	/**
	 * Creates a new LevelEditor to be contained by the given world. Takes many
	 * of the world's variables as its own.
	 * 
	 * @param world
	 */
	public LevelEditor(World world) {
		this.chooser = new JFileChooser();
		this.world = world;
		this.panel = this.world.getPanel();
		this.frame = this.panel.frame;
		this.tiles = this.world.tilelist;
		this.comp = this.world.getArtist();

		this.frame.addMouseListener(new EditorListener(this));
	}

	/**
	 * This is called by world every time the world is called by its
	 * SimulationPanel. Gets the last keypress and deals with it, and
	 * compensates for any window resizing that occurs.
	 * 
	 * Also calls the artist to redraw the frame.
	 * 
	 */
	public void update() {
		if (!this.panel.getKey().equals("none")) {
			processTile(this.panel.getKey());
			this.panel.resetKey();
		}
		this.frame.repaint();
		this.boxsize = this.comp.getBoxSize();
	}

	/**
	 * This is called when the mouse is clicked on the frame.
	 * 
	 * Converts the click location to a tile location and passes that tile
	 * location on to be processed.
	 * 
	 * @param xClicked
	 * @param yClicked
	 */
	public void mouseClicked(int xClicked, int yClicked) {

		if (!(this.boxsize <= 0)) {
			int rows = this.tiles[0].length;
			int cols = this.tiles.length;

			int xTile = 0;
			for (int x = 0; x < (xClicked - this.boxsize); x += this.boxsize) {
				xTile++;
			}
			// System.out.println("X tile = " + xTile);

			int yTile = 0;
			for (int y = 0; y < (yClicked - this.boxsize * 1.5); y += this.boxsize) {
				yTile++;
			}
			// System.out.println("Y tile = " + yTile);

			if (xTile < cols && yTile - 1 < rows && xTile >= 0
					&& yTile - 1 >= 0) {
				Tile tileToCheck = this.tiles[yTile - 1][xTile];

				if ((this.tileType == 0 && tileToCheck.getType() != 0)
						|| this.tileType != 0) {
					this.world.playOverrideEffect("src/burgertime/Blip.wav");
				}

				tileToCheck.setType(this.tileType);

				this.world.resetEntities();

				this.world.getMobList().clear();
				this.world.getBurgerList().clear();

				this.world.spawnThings(true, false, true);
			}

		} else {
			System.out.println("Boxsize is 0");
		}
	}

	/**
	 * Given a button press, decides what to do with it.
	 * 
	 * @param tileType2
	 */
	public void processTile(String tileType2) {

		// User clicked on a "toolbar" tile

		String string = "";

		if (tileType2.equals("0")) {
			string = "nothing!";
			this.tileType = 0;
		} else if (tileType2.equals("1")) {
			string = "platforms";
			this.tileType = 1;
		} else if (tileType2.equals("2")) {
			string = "land under ladders";
			this.tileType = 2;
		} else if (tileType2.equals("3")) {
			string = "ladders";
			this.tileType = 3;
		} else if (tileType2.equals("4")) {
			string = "mob spawners";
			this.tileType = 4;
		} else if (tileType2.equals("5")) {
			string = "burger spawn platforms";
			this.tileType = 5;
		} else if (tileType2.equals("6")) {
			string = "burger landing sites";
			this.tileType = 6;
		} else if (tileType2.equals("7")) {
			string = "player spawn points";
			this.tileType = 7;
		} else if (tileType2.equals("Escape")) {
			string = "files...";
			save();
		} else if (tileType2.equals("M")) {
			string = "paused music into the playing music thing or something";
			this.world.stopMusic();
		}

		System.out.println("TOOLBAR: Now placing " + string);
	}

	/**
	 * Saves the current tile map to a user-chosen file
	 * 
	 * Can be called by SimulationPanel to include quitting
	 * 
	 */
	public void save() {

		if (this.chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File outputFile = this.chooser.getSelectedFile();

		try {
			PrintWriter writer = new PrintWriter(outputFile);

			writer.println(this.tiles[0].length + " " + this.tiles.length);

			for (Tile[] tilex : this.tiles) {
				String outputString = "";

				for (Tile tile : tilex) {
					outputString += tile.getType() + " ";
				}

				writer.println(outputString);
			}
			writer.close();
			System.out.println("Saved Successfully");
		} catch (FileNotFoundException e) {
			System.out.println("Saving Failed");
			e.printStackTrace();
		}
	}
}
