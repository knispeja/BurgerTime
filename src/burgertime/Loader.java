/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;

/**
 * TODO This class loads player and tile files
 * 
 * @author militeja. Created Jan 31, 2014.
 */

public class Loader {
	private final JFileChooser chooser;
	private Loader load;

	/**
	 * 
	 * TODO Sets up the loader object
	 *
	 */
	public Loader() {
		this.chooser = new JFileChooser();
	}

	/**
	 * 
	 * TODO Let's the user choose a world to load
	 *
	 * @return a double int array representing the world
	 */
	public int[][] loadTiles()  {
		
		/*
		 * Example File
		 * 4 7
		4 1 1 1 1 1 4
		0 0 0 3 0 0 0
		0 0 0 3 0 0 0
		1 1 1 2 1 1 1 
		 */

		int[][] coordinate = null;
		int row;
		int colums;

		if (this.chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			// return;
		}

		File inputFile = this.chooser.getSelectedFile();
		

		
			Scanner input;
			try {
				input = new Scanner(inputFile);
				while (input.hasNextInt()) {
					row = input.nextInt();
					colums = input.nextInt();
			//		System.out.println(row);
			//		System.out.println(colums);

					coordinate = new int[row][colums];

					for (int c1 = 0; c1 < row; c1++) {
						for (int c2 = 0; c2 < colums; c2++) {
							int x = input.nextInt();
							// Prints out the read input. Can delete next line
							//System.out.println("Input: " + x);
							coordinate[c1][c2] = x;

						}
						// Can delete this line also
					//	System.out.println("Break ");

					}

				}
			
				input.close();
			} catch (FileNotFoundException e) {
				System.out.println("ERROR! Can't open file. Will open defualt");
				coordinate = new int[1][1];
				coordinate[0][0] = 0;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		

		return coordinate;
	}
	
	/**
	 * 
	 * TODO Loads a world based on the players level
	 *
	 * @param thePlayer - which contains the level that is to be loaded 
	 * @return a double int array representing the world
	 */
public int[][] loadLevel(Player thePlayer)  {
		
		/*
		 * Example File
		 * 4 7
		1 1 1 1 1 1 1
		0 0 0 3 0 0 0
		0 0 0 3 0 0 0
		1 1 1 2 1 1 1 
		 */

		int[][] coordinate = null;
		int row;
		int colums;
		String fileName="";
		if(thePlayer.getLevel()==1){
			//System.out.println("src/burgertime/Level1.txt");
			fileName=("src/burgertime/Level1.txt");

		}else if(thePlayer.getLevel()==2){
			//System.out.println("src/burgertime/Level2.txt");
			fileName=("src/burgertime/Level2.txt");
		}else if(thePlayer.getLevel()==3){
			//System.out.println("src/burgertime/Level3.txt");
			fileName=("src/burgertime/Level3.txt");
		}else if(thePlayer.getLevel()==4){
			//System.out.println("src/burgertime/Level4.txt");
			fileName=("src/burgertime/Level4.txt");
		}else if(thePlayer.getLevel()==5){
			//System.out.println("src/burgertime/Level5.txt");
			fileName=("src/burgertime/Level5.txt");
		}else if(thePlayer.getLevel()==6){
			//System.out.println("src/burgertime/Level6.txt");
			fileName=("src/burgertime/Level6.txt");
		}else if(thePlayer.getLevel()==7){
			//System.out.println("src/burgertime/Level7.txt");
			fileName=("src/burgertime/Level7.txt");
		}else if(thePlayer.getLevel() == -1){
			//System.out.println("Loading editor...");
			fileName=("src/burgertime/Editor.txt");
		}else if(thePlayer.getLevel() == 9){
			fileName = ("src/burgertime/Level9.txt");
		}else if(thePlayer.getLevel() == 8){
			//System.out.println("src/burgertime/FinalLevel.txt");
			fileName=("src/burgertime/FinalLevel.txt");
		}
		else{
			fileName = ("src/burgertime/Level1.txt");
		}
		Scanner input;
		try {
			input = new Scanner(new File(fileName));
			while (input.hasNextInt()) {
				//System.out.println("Can get Data");
				row = input.nextInt();
				colums = input.nextInt();
				//System.out.println(row);
				//System.out.println(colums);

				coordinate = new int[row][colums];

				for (int c1 = 0; c1 < row; c1++) {
					for (int c2 = 0; c2 < colums; c2++) {
						int x = input.nextInt();
						// Prints out the read input. Can delete next line
						//System.out.println("Input: " + x);
						coordinate[c1][c2] = x;

					}
					// Can delete this line also
					//System.out.println("Break ");

				}

			}
		
			input.close();
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR! Can't open file. Will open defualt");
			coordinate = new int[1][1];
			coordinate[0][0] = 7;
			e.printStackTrace();
		}
		
		
			

		return coordinate;
	}
	
	
	
	
	/**
	 * 
	 * TODO Loads a previously saved file
	 *
	 * @return the loaded player
	 */
	public Player loadPlayer() {
		/*
		 * Example Player file
		 * Jacob
		   5
		   2
		 */
		String name="Defult Player";
		
		int level=1;
		int lives =0;
		boolean difficulty = false;
		int score = 0;

		if (this.chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			// return;
		}

		File inputFile = this.chooser.getSelectedFile();
		try {
			Scanner input = new Scanner(inputFile);
		//	System.out.println("Loading stuff");
			while (input.hasNext()) {
				name = input.nextLine();
				//highScore  = input.nextInt();
				level= input.nextInt();
				lives= input.nextInt();
				difficulty = input.nextBoolean();
				score = input.nextInt();
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not read this file. Will make default player");
			name = "This loser cannot open the right type of file";
			level = 1;
			//highScore = 1;
			e.printStackTrace();
		}

		
			//System.out.println("Made it here");
		
		// Should also be made to load score (highscore can be used to to this) and hardMode
		Player aPlayer= new Player(name,level,lives, difficulty,score);
		
		return aPlayer; 
		
	}
	
	/**
	 * 
	 * TODO Saves a player file
	 *
	 * @param thePlayer - the player that is to be saved
	 */
	public void saveGame(Player thePlayer){
	
	//	System.out.println(thePlayer.getName()+" is saving");
	//.out.println(thePlayer.getLevel()+" is saving");
		//System.out.println(thePlayer.getHighScore()+" is saving");
		if (this.chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File outputFile = this.chooser.getSelectedFile();
	//	System.out.println(outputFile.getName() +" is being saved to");
		
		try {
			PrintWriter writer = new PrintWriter(outputFile);
			writer.println(thePlayer.getName());
		//	writer.println(thePlayer.getHighScore());
			writer.println(thePlayer.getLevel());
			writer.println(thePlayer.getLives());
			writer.println(thePlayer.getDifficult());
			writer.println(thePlayer.getScore());
			writer.close();
			//System.out.println("Saved Successfully");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Saving Failed");
			e.printStackTrace();
		}
		
		
		
		
	}

}
