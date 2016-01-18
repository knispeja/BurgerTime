/*
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * This is the main artist class for the program. It draws all of the components
 * onto a main window in one JComponent.
 * 
 * @author larsonp. Created Feb 4, 2014.
 */
public class Artist extends JComponent {

	private int LAZERTIMER = 30;
	private int FINALBOSSVARIABLE = 100;
	private boolean coordMode = true;
	private boolean mobAnimations = true;
	private int bossDimension = 250;
	private boolean isFinalLevel;
	private int height;
	private int width;
	private int tileDimension;
	private Tile[][] tileList;
	private JFrame frame;
	private Character hero;
	private Graphics2D g2;
	private int boxSize;
	private int oldBoxSize;
	private int popupSize = 180;
	private boolean isHardMode;
	private BufferedImage heroimg;
	private BufferedImage leftMobImage;
	private BufferedImage rightMobImage;
	private BufferedImage tempHeroImg;
	private BufferedImage tempLeftMob;
	private BufferedImage tempRightMob;
	private BufferedImage ladderimg;
	private BufferedImage tempLadderImg;
	private BufferedImage blockimg;
	private BufferedImage tempBlockImage;
	private BufferedImage background;
	private BufferedImage tempbackground;
	private BufferedImage burgerImage;
	private BufferedImage tempBurgerImage;
	private BufferedImage tempSprite1;
	private BufferedImage tempSprite2;
	private BufferedImage tempSprite3;
	private BufferedImage tempSprite4;
	private BufferedImage tempSprite5;
	private BufferedImage tempSprite6;
	private BufferedImage tempSprite7;
	private BufferedImage tempSprite8;
	private BufferedImage sprite1;
	private BufferedImage sprite2;
	private BufferedImage sprite3;
	private BufferedImage sprite4;
	private BufferedImage sprite5;
	private BufferedImage sprite6;
	private BufferedImage sprite7;
	private BufferedImage sprite8;
	private BufferedImage hardBackground;
	private BufferedImage tempHardBackground;
	private BufferedImage tempPepperImage;
	private BufferedImage pepperImage;
	private BufferedImage obsidionImage;
	private BufferedImage tempObsidionImage;
	private BufferedImage angryDog;
	private BufferedImage tempAngryDog;
	private BufferedImage redEye;
	private BufferedImage tempRedEye;
	private int totalTimer = 0;
	/**
	 * Variable for triggering flash animation.
	 */
	public boolean flash;
	private int flashTimer = 100;
	private int flashEnd;
	private int laserTimer;
	private int laserTile;
	private Random randElement;

	private ArrayList<BufferedImage> finalBackgroundList;
	private ArrayList<Mob> mobList;

	private int counter;
	private int backgroundCounter;
	private World currentWorld;

	/**
	 * This is the final constructor. It takes only the world as a variable, and
	 * extracts all of the additional information it needs from the world.
	 * 
	 * @param world
	 */
	public Artist(World world) {
		this.randElement = new Random();
		this.isFinalLevel = world.isFinalLevel();
		this.tileList = world.tilelist;
		this.frame = world.getFrame();
		this.hero = world.getHero();
		this.backgroundCounter = 0;
		this.counter = 0;
		// Fix. Assign burgerpart list to
		// variable---------------------------------------------
		this.currentWorld = world;
		this.mobList = world.getMobList();
		fileLoader();
		this.mobAnimations = this.currentWorld.isHard();
		this.isHardMode = this.currentWorld.isHard();

		if (this.isFinalLevel) {
			this.laserTimer = 2 * this.FINALBOSSVARIABLE;
			backgroundLoader();
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		this.tileDimension = Math.max(this.tileList.length,
				this.tileList[0].length) + 1;

		this.g2 = (Graphics2D) g;

		this.height = this.frame.getHeight();

		this.width = this.frame.getWidth();

		this.boxSize = Math.min(this.height, this.width) / this.tileDimension;

		if (this.laserTimer - 2 * this.LAZERTIMER + this.LAZERTIMER / 2 <= this.totalTimer
				&& this.laserTimer - 2 * this.LAZERTIMER + this.LAZERTIMER > this.totalTimer) {
			this.g2.translate((this.totalTimer % 3 - 1) * 7, 0);
		}
		if (this.totalTimer > this.flashTimer
				&& this.totalTimer < this.flashTimer + 40) {
			this.g2.translate((this.totalTimer % 3 - 1) * 30, 0);
		}

		if (this.coordMode == true) {
			newMode();
		} else {
			oldMode();
		}
	}

	/**
	 * A method that contains most of the features of the final level.
	 *
	 */
	public void finalLevel() {
		if (this.totalTimer < this.FINALBOSSVARIABLE) {
			this.g2.setColor(Color.BLACK);
			this.g2.fillRect(0, 0, this.height + this.boxSize, this.height
					+ this.boxSize);
		}
		if (this.totalTimer == 59) {
			this.g2.setColor(Color.WHITE);
			this.g2.fillRect(0, 0, this.height + this.boxSize, this.height
					+ this.boxSize);
		}
		if (this.totalTimer > 60 && this.totalTimer < this.FINALBOSSVARIABLE) {
			this.g2.drawImage(this.tempRedEye, null, this.height / 2
					- this.boxSize, this.width / 2 - this.boxSize);
		}

		if (this.totalTimer > this.FINALBOSSVARIABLE) {
			this.g2.drawImage(
					this.finalBackgroundList.get(this.backgroundCounter), null,
					0, 0);
			this.backgroundCounter++;
			if (this.backgroundCounter == 15) {
				this.backgroundCounter = 0;
			}
		}

		if (this.totalTimer > this.FINALBOSSVARIABLE) {
			this.g2.drawImage(this.tempAngryDog, null, this.height / 2
					- this.bossDimension / 2, this.width / 2
					- this.bossDimension / 2 - this.bossDimension / 6
					+ this.bossDimension / 12 - this.bossDimension / 6
					- this.bossDimension / 12);
		}

		this.totalTimer++;
	}

	/**
	 * This method loads all of the backgrounds of the final level. 
	 *
	 */
	public void backgroundLoader() {
		this.finalBackgroundList = new ArrayList<BufferedImage>();

		try {
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_000.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_001.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_002.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_003.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_004.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_005.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_006.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_007.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_008.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_009.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_010.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_011.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_012.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_013.png")));
			this.finalBackgroundList.add(ImageIO.read(new File(
					"src/burgertime/Animations/frame_014.png")));
		} catch (Exception e) {
			System.err.println("Didn't load backgrounds.");
		}
	}

	/**
	 * Method used to load all of the images used in the game. Put into one
	 * method for convenient changes.
	 * 
	 */
	public void fileLoader() {
		try {
			this.sprite1 = ImageIO.read(new File(
					"src/burgertime/Animations/1.png"));
			this.sprite2 = ImageIO.read(new File(
					"src/burgertime/Animations/2.png"));
			this.sprite3 = ImageIO.read(new File(
					"src/burgertime/Animations/3.png"));
			this.sprite4 = ImageIO.read(new File(
					"src/burgertime/Animations/4.png"));
			this.sprite5 = ImageIO.read(new File(
					"src/burgertime/Animations/5.png"));
			this.sprite6 = ImageIO.read(new File(
					"src/burgertime/Animations/6.png"));
			this.sprite7 = ImageIO.read(new File(
					"src/burgertime/Animations/7.png"));
			this.sprite8 = ImageIO.read(new File(
					"src/burgertime/Animations/8.png"));
		} catch (IOException e) {
			this.sprite1 = null;
			this.sprite2 = null;
			this.sprite3 = null;
			this.sprite4 = null;
			this.sprite5 = null;
			this.sprite6 = null;
			this.sprite7 = null;
			this.sprite8 = null;
			System.err.println("Problems loading sprites");
		}
		try {
			this.pepperImage = ImageIO.read(new File(
					"src/burgertime/Pepper.png"));
		} catch (IOException e) {
			this.pepperImage = null;
			System.err.println("Problems loading burger image");
		}
		try {
			this.burgerImage = ImageIO.read(new File(
					"src/burgertime/Burger.png"));
		} catch (IOException e) {
			this.burgerImage = null;
			System.err.println("Problems loading burger image");
		}
		try {
			this.heroimg = ImageIO.read(new File(
					"src/burgertime/GrumpyChef.png"));
		} catch (IOException e) {
			this.heroimg = null;
			System.err.println("Problems loading hero");
		}

		try {
			this.leftMobImage = ImageIO.read(new File(
					"src/burgertime/cropped_dog_left.png"));
		} catch (IOException e) {
			this.leftMobImage = null;
			System.err.println("Problems loading mob");
		}
		try {
			this.rightMobImage = ImageIO.read(new File(
					"src/burgertime/cropped_dog_right.png"));
		} catch (IOException e) {
			this.rightMobImage = null;
			System.err.println("Problems loading mob");
		}
		try {
			this.ladderimg = ImageIO
					.read(new File("src/burgertime/Ladders.png"));
		} catch (IOException e) {
			this.ladderimg = null;
			System.err.println("Problems loading ladder");
		}
		try {
			this.blockimg = ImageIO.read(new File(
					"src/burgertime/Grass_Block.jpg"));
		} catch (IOException e) {
			this.blockimg = null;
			System.err.println("Problems loading block");
		}
		try {
			this.background = ImageIO.read(new File(
					"src/burgertime/background1.jpg"));
			this.hardBackground = ImageIO.read(new File(
					"src/burgertime/hard_Background.jpg"));
		} catch (IOException e) {
			this.background = null;
			this.hardBackground = null;
			System.err.println("Problems loading background");
		}
		try {
			this.obsidionImage = ImageIO.read(new File(
					"src/burgertime/Obsidion.png"));
		} catch (IOException e) {
			this.obsidionImage = null;
			System.err.println("Problems loading block");
		}
		try {
			this.redEye = ImageIO.read(new File("src/burgertime/redEye.png"));
		} catch (IOException e) {
			this.redEye = null;
			System.err.println("Problems loading eyes");
		}
		try {
			this.angryDog = ImageIO.read(new File(
					"src/burgertime/angry-doge.png"));
		} catch (IOException e) {
			this.angryDog = null;
			System.err.println("Problems loading block");
		}
	}

	/**
	 * Current display mode. created 2/16/14. This display mode uses cropped
	 * images in classic mode and animated spites in hard mode. It also supports
	 * different backgrounds. The window is fully resizeable,
	 * 
	 * 
	 */
	public void newMode() {

		// This code checks if the images need to be resized, by checking and
		// only running if
		// something has changed or if some images are null, it reduces
		// unneccesary
		// computing by not re-resizing the images.
		if (this.rightMobImage == null || this.oldBoxSize != this.boxSize
				|| this.tempHeroImg == null || this.tempLadderImg == null
				|| this.tempBlockImage == null || this.tempbackground == null
				|| this.leftMobImage == null || this.burgerImage == null) {

			this.oldBoxSize = this.boxSize;

			this.tempObsidionImage = ArtistTools.resizeImage(
					this.obsidionImage, this.obsidionImage.getType(),
					this.boxSize, this.boxSize);

			this.tempBurgerImage = ArtistTools.resizeImage(this.burgerImage,
					this.burgerImage.getType(), this.boxSize, this.boxSize / 2);

			this.tempLadderImg = ArtistTools.resizeImage(this.ladderimg,
					this.ladderimg.getType(), this.boxSize, this.boxSize);

			this.tempBlockImage = ArtistTools.resizeImage(this.blockimg,
					this.blockimg.getType(), this.boxSize, this.boxSize);

			this.tempLeftMob = ArtistTools.resizeImage(this.leftMobImage,
					this.leftMobImage.getType(), this.boxSize, this.boxSize);

			this.tempRightMob = ArtistTools.resizeImage(this.rightMobImage,
					this.rightMobImage.getType(), this.boxSize, this.boxSize);

			this.tempHeroImg = ArtistTools.resizeImage(this.heroimg,
					this.heroimg.getType(), this.boxSize, this.boxSize);

			this.tempbackground = ArtistTools.resizeImage(this.background,
					this.background.getType(), this.height + this.boxSize,
					this.width + this.boxSize);
			this.tempSprite1 = ArtistTools.resizeImage(this.sprite1,
					this.sprite1.getType(), this.boxSize, this.boxSize);
			this.tempSprite2 = ArtistTools.resizeImage(this.sprite2,
					this.sprite2.getType(), this.boxSize, this.boxSize);
			this.tempSprite3 = ArtistTools.resizeImage(this.sprite3,
					this.sprite3.getType(), this.boxSize, this.boxSize);
			this.tempSprite4 = ArtistTools.resizeImage(this.sprite4,
					this.sprite4.getType(), this.boxSize, this.boxSize);
			this.tempSprite5 = ArtistTools.resizeImage(this.sprite5,
					this.sprite5.getType(), this.boxSize, this.boxSize);
			this.tempSprite6 = ArtistTools.resizeImage(this.sprite6,
					this.sprite6.getType(), this.boxSize, this.boxSize);
			this.tempSprite7 = ArtistTools.resizeImage(this.sprite7,
					this.sprite7.getType(), this.boxSize, this.boxSize);
			this.tempSprite8 = ArtistTools.resizeImage(this.sprite8,
					this.sprite8.getType(), this.boxSize, this.boxSize);
			this.tempHardBackground = ArtistTools.resizeImage(
					this.hardBackground, this.hardBackground.getType(),
					this.height, this.width);
			this.tempPepperImage = ArtistTools.resizeImage(this.pepperImage,
					this.pepperImage.getType(), this.boxSize, this.boxSize);
			this.tempAngryDog = ArtistTools.resizeImage(this.angryDog,
					this.angryDog.getType(), this.bossDimension,
					this.bossDimension);
			this.tempRedEye = ArtistTools.resizeImage(this.redEye,
					this.redEye.getType(), this.boxSize, this.boxSize);
		}

		// This section of code draws the background and tiles.
		if (!(this.isFinalLevel && this.totalTimer < this.FINALBOSSVARIABLE)) {
			if (this.isFinalLevel == true) {
				finalLevel();
			} else if (this.isHardMode == false) {
				this.g2.drawImage(this.tempbackground, null, 0, 0);
			} else {
				this.g2.drawImage(this.tempHardBackground, null, 0, 0);
			}
			for (int b = 0; b < this.tileList.length; b++) {
				for (int a = 0; a < this.tileList[0].length; a++) {
					new Rectangle2D.Double(a * this.boxSize, b
							* this.boxSize, this.boxSize, this.boxSize);
					int currenttype = this.tileList[b][a].getType();
					if (currenttype != 0) {
						if (this.isFinalLevel) {
							this.g2.drawImage(this.tempObsidionImage, null, a
									* this.boxSize, (b + 1) * this.boxSize);
						} else {
							this.g2.drawImage(this.tempBlockImage, null, a
									* this.boxSize, (b + 1) * this.boxSize);
						}
					}

				}

			}
			// ladder drawing code. Currently draws to mentioned tile and tile
			// above, so that traveling down to a ladder is easier.
			for (int b = 0; b < this.tileList.length; b++) {
				for (int a = 0; a < this.tileList[0].length; a++) {
					new Rectangle2D.Double(a * this.boxSize, b
							* this.boxSize, this.boxSize, this.boxSize);
					int currenttype = this.tileList[b][a].getType();
					if (currenttype == 3) {

						this.g2.drawImage(this.tempLadderImg, null, a
								* this.boxSize, (b + 1) * this.boxSize);

						this.g2.drawImage(this.tempLadderImg, null, a
								* this.boxSize, (b) * this.boxSize);

					}
				}
			}

			// Coordinate information. These calculations are for displaying the
			// character
			// in its correctly displaced location.
			int displacementX = (int) ((int) (this.hero.getSubX() - this.hero.halfSubScale)
					* this.boxSize / (double) this.hero.subScale);
			int displacementY = (int) ((int) (this.hero.getSubY() - this.hero.halfSubScale)
					* this.boxSize / (double) this.hero.subScale);

			this.g2.drawImage(this.tempHeroImg, null, this.hero.getX()
					* this.boxSize + displacementX, this.hero.getY()
					* this.boxSize + displacementY);

			// Mob drawing code.
			// This code draws mobs with similar displacement if needed for
			// movements within a tile.
			for (Mob m : this.mobList) {
				int x1 = m.getTile().getX()
						* this.boxSize
						+ (int) ((int) (m.getSubX() - m.halfSubScale)
								* this.boxSize / (double) m.subScale)
						+ this.boxSize / 2;
				int y1 = m.getTile().getY()
						* this.boxSize
						+ (int) ((int) (m.getSubY() - m.halfSubScale)
								* this.boxSize / (double) m.subScale)
						+ this.boxSize / 2;
				this.g2.translate(x1, y1);

				if (this.mobAnimations == false) {

					if (m.getDirection() != null) {

						if (m.getDirection().getX() == 1) {

							this.g2.drawImage(this.tempRightMob, null,
									-this.boxSize / 2, -this.boxSize / 2);
						} else {
							this.g2.drawImage(this.tempLeftMob, null,
									-this.boxSize / 2, -this.boxSize / 2);
						}

					} else {
						this.g2.drawImage(this.tempLeftMob, null,
								-this.boxSize / 2, -this.boxSize / 2);
					}

				} else {
					// This section of code animates the dog image with a series
					// of
					// 8 images
					// played back to back.
					BufferedImage imageToDraw;
					AffineTransform ax = AffineTransform
							.getScaleInstance(-1, 1);
					if (this.counter == 0) {
						imageToDraw = this.tempSprite1;
					} else if (this.counter == 1) {
						imageToDraw = this.tempSprite2;
					} else if (this.counter == 2) {
						imageToDraw = this.tempSprite3;
					} else if (this.counter == 3) {
						imageToDraw = this.tempSprite4;
					} else if (this.counter == 4) {
						imageToDraw = this.tempSprite5;
					} else if (this.counter == 5) {
						imageToDraw = this.tempSprite6;
					} else if (this.counter == 6) {
						imageToDraw = this.tempSprite7;
					} else {
						this.counter = 0;
						imageToDraw = this.tempSprite8;
					}
					if (m.getDirection() != null) {
						if (m.getDirection().getX() == 1) {
							this.g2.drawImage(imageToDraw, null,
									-this.boxSize / 2, -this.boxSize / 2);
						} else {
							this.g2.transform(ax);
							this.g2.drawImage(imageToDraw, null,
									-this.boxSize / 2, -this.boxSize / 2);
							this.g2.transform(ax);

						}
					} else {
						this.g2.drawImage(imageToDraw, null, -this.boxSize / 2,
								-this.boxSize / 2);
					}

				}
				this.g2.translate(-x1, -y1);
				if (this.currentWorld.isPepperGoing()) {
					this.g2.drawImage(
							this.tempPepperImage,
							null,
							(this.hero.getX() + this.hero.getDirection().getX())
									* this.boxSize,
							(this.hero.getY() + this.hero.getDirection().getY())
									* this.boxSize);
				}

			}

			for (Burger b : this.currentWorld.getBurgerList()) {
				for (BurgerPart p : b.getParts()) {

					ArrayList<Tile> bParts = p.getParts();

					for (int i = 0; i < bParts.size(); i++) {
						Tile tileAtI = bParts.get(i);
						if (p.getFlags().get(i) == false)
							this.g2.drawImage(this.tempBurgerImage, null,
									tileAtI.getX() * this.boxSize,
									tileAtI.getY() * this.boxSize
											+ this.boxSize / 2);
						else
							this.g2.drawImage(this.tempBurgerImage, null,
									tileAtI.getX() * this.boxSize,
									tileAtI.getY() * this.boxSize
											+ this.boxSize);

					}
				}
			}
			// This code displays the score information at the bottom left of
			// the
			// screen.

			if (!this.currentWorld.isEditor() && !this.isFinalLevel) {
				String pepperString = "Pepper: "
						+ Integer.toString(this.currentWorld.getPepper());
				String lifeString = "Lives: "
						+ Integer.toString(this.hero.getLife());
				String scoreString = "Score: "
						+ Integer.toString(this.currentWorld.getScore());
				this.g2.setColor(Color.GRAY);
				this.g2.fillRect(0, this.height - this.popupSize,
						this.popupSize, this.popupSize);
				this.g2.setColor(Color.BLACK);
				this.g2.drawRect(0, this.height - this.popupSize,
						this.popupSize, this.popupSize);
				this.g2.setFont(new Font("Dialog", Font.PLAIN, 20));
				this.g2.drawString(pepperString, 10,
						(int) (this.height - this.popupSize * (8.5 / 10)));
				this.g2.drawString(lifeString, 10,
						(int) (this.height - (7.0 / 10) * this.popupSize));
				this.g2.drawString(scoreString, 10,
						(int) (this.height - (5.5 / 10) * this.popupSize));

			}
			this.counter++;
		} else {
			finalLevel();
		}
		if (this.flash) {
			this.g2.fillRect(0, 0, this.width + this.boxSize, this.height
					+ this.boxSize);
			this.flash = false;
			this.flashTimer = this.totalTimer;
			this.flashEnd = this.totalTimer + 30;

		}
		if (this.isFinalLevel) {
			drawLazer();
		}
	}

	private void drawLazer() {
		this.g2.setColor(Color.RED);

		if (this.flashTimer < this.totalTimer
				&& this.flashEnd - 15 > this.totalTimer) {
			this.g2.fillRect(0, (this.boxSize / 3) * 2, this.width,
					(this.boxSize / 3) * 2);

		} else if (this.flashTimer < this.totalTimer
				&& this.flashEnd > this.totalTimer) {
			this.g2.fillRect(0, 0, this.width, this.boxSize * 2);
		}
		int timerCompensation = this.currentWorld.totalBossLife() * 30 / 5;

		if (this.laserTimer < this.totalTimer || this.hero.isFalling()) {
			this.laserTimer = this.totalTimer
					+ this.randElement.nextInt(50 - timerCompensation)
					+ this.LAZERTIMER + this.LAZERTIMER;
			this.laserTile = this.randElement.nextInt(4) + this.hero.getX() - 2;
		}
		if (this.laserTimer - 2 * this.LAZERTIMER + this.LAZERTIMER / 2 == this.totalTimer
				|| this.totalTimer == this.flashTimer + 5) {
			this.currentWorld.playEffect("src/burgertime/Laser.wav");
		}
		if (this.laserTimer - 2 * this.LAZERTIMER < this.totalTimer) {

			this.g2.drawRect(this.laserTile * this.boxSize, 0, this.boxSize,
					this.height + this.boxSize);

		}
		if (this.laserTimer - this.LAZERTIMER < this.totalTimer) {
			if (!this.hero.isFalling()) {
				this.g2.fillRect(this.laserTile * this.boxSize, 0,
						this.boxSize, this.height + this.boxSize);
				if (this.hero.getX() == this.laserTile) {

					this.currentWorld.playerDeath();
				}
			}
		}

	}

	/**
	 * Simple method created for getting the drawn box size in other parts of
	 * the project.
	 * 
	 * 
	 * @return value of boxSize
	 */
	public int getBoxSize() {
		return this.boxSize;
	}

	/**
	 * old display mode does not display coordinates of player and mobs. Last
	 * updated before 2/16/16, but kept for refrence.
	 * 
	 */
	public void oldMode() {

		if (this.tempLeftMob == null || this.oldBoxSize != this.boxSize
				|| this.tempHeroImg == null || this.tempLadderImg == null
				|| this.tempBlockImage == null || this.tempbackground == null) {

			this.oldBoxSize = this.boxSize;

			// System.out.println("Scaling");

			this.tempLadderImg = ArtistTools.resizeImage(this.ladderimg,
					this.ladderimg.getType(), this.boxSize, this.boxSize);

			this.tempBlockImage = ArtistTools.resizeImage(this.blockimg,
					this.blockimg.getType(), this.boxSize, this.boxSize);

			this.tempLeftMob = ArtistTools.resizeImage(this.leftMobImage,
					this.leftMobImage.getType(), this.boxSize, this.boxSize);

			this.tempHeroImg = ArtistTools.resizeImage(this.heroimg,
					this.heroimg.getType(), this.boxSize, this.boxSize);

			this.tempbackground = ArtistTools.resizeImage(this.background,
					this.background.getType(), this.height + this.boxSize,
					this.width + this.boxSize);
		}

		// Draws tiles
		this.g2.drawImage(this.tempbackground, null, 0, 0);
		for (int b = 0; b < this.tileList.length; b++) {
			for (int a = 0; a < this.tileList[0].length; a++) {
				new Rectangle2D.Double(a * this.boxSize, b
						* this.boxSize, this.boxSize, this.boxSize);
				int currenttype = this.tileList[b][a].getType();
				if (currenttype != 0) {
					this.g2.drawImage(this.tempBlockImage, null, a
							* this.boxSize, (b + 1) * this.boxSize);
				}

			}

		}
		// Draws Ladders.
		for (int b = 0; b < this.tileList.length; b++) {
			for (int a = 0; a < this.tileList[0].length; a++) {
				new Rectangle2D.Double(a * this.boxSize, b
						* this.boxSize, this.boxSize, this.boxSize);
				int currenttype = this.tileList[b][a].getType();
				if (currenttype == 3) {

					this.g2.drawImage(this.tempLadderImg, null, a
							* this.boxSize, (b + 1) * this.boxSize);
					this.g2.drawImage(this.tempLadderImg, null, a
							* this.boxSize, (b) * this.boxSize);

				}
			}
		}

		this.g2.drawImage(this.tempHeroImg, null, this.hero.getX()
				* this.boxSize, this.hero.getY() * this.boxSize);

		for (Mob m : this.mobList) {
			this.g2.drawImage(this.tempLeftMob, null, m.getTile().getX()
					* this.boxSize, m.getTile().getY() * this.boxSize);

		}

	}

	/**
	 * Older display mode. Uses simple shapes instead of icons. does not have
	 * coordinate display of character and mobs, used before oldMode.
	 * 
	 */
	public void shapeDisplay() {

		// will use rectangles
		for (int b = 0; b < this.tileList.length; b++) {
			for (int a = 0; a < this.tileList[0].length; a++) {
				Rectangle2D r = new Rectangle2D.Double(a * this.boxSize, b
						* this.boxSize, this.boxSize, this.boxSize);
				int currenttype = this.tileList[b][a].getType();
				Color c;
				if (currenttype == 3) {

					c = Color.RED;
				} else {
					c = Color.BLUE;
				}

				this.g2.setColor(c);
				if (currenttype != 0) {
					this.g2.draw(r);
				}

			}

		}
		if (this.hero.getLife() <= 2)
			this.g2.drawString("Wow", 4 * this.boxSize, 2 * this.boxSize);
		if (this.hero.getLife() <= 1)
			this.g2.drawString("Such doge", 0 * this.boxSize, 2 * this.boxSize);
		if (this.hero.getLife() <= 0)
			this.g2.drawString("Much lose", 1 * this.boxSize, 3 * this.boxSize);
		if (this.hero.getLife() <= -1)
			this.g2.drawString("Concern", 3 * this.boxSize, 3 * this.boxSize);

		if (this.tempLeftMob == null || this.oldBoxSize != this.boxSize
				|| this.tempHeroImg == null) {

			this.oldBoxSize = this.boxSize;

			// System.out.println("Scaling");
			this.tempLeftMob = ArtistTools.resizeImage(this.leftMobImage,
					this.leftMobImage.getType(), this.boxSize, this.boxSize);

			this.tempHeroImg = ArtistTools.resizeImage(this.heroimg,
					this.heroimg.getType(), this.boxSize, this.boxSize);
		}
		this.g2.drawImage(this.tempHeroImg, null, this.hero.getX()
				* this.boxSize, this.hero.getY() * this.boxSize);

		for (Mob m : this.mobList) {
			this.g2.drawImage(this.leftMobImage, null, m.getTile().getX()
					* this.boxSize, m.getTile().getY() * this.boxSize);

		}

	}
}
