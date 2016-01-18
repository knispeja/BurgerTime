/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 * 
 */
package burgertime;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * TODO Creates all of the buttons for the main menu
 *
 * @author militeja.
 *         Created Feb 9, 2014.
 */
public class MainMenuButtons {
	private JButton newPlayer = new JButton("New Player");
	private JButton hardMode = new JButton("Hard Mode");
	private JButton loadPlayer = new JButton("Load a Player");
	private JButton audioButton = new JButton("Mute");
	private JButton editorButton = new JButton("Level Editor");
	private JButton guide = new JButton("Guide to Game");
	private JButton custom = new JButton("Load Custom Level");
	Boolean audioOn;
	private Boolean hard = false;
	Loader load = new Loader();

	public MainMenuButtons(){
		this.audioOn = true;
	}
	
	/**
	 * 
	 * TODO Sets the action listeners for each button
	 *
	 * @param newPlayer - new player action Listener
	 * @param load - load player action Listener
	 * @param sound - sound action Listener
	 * @param openEditor - openEditor action Listener
	 * @param hard - difficulty changer action Listener
	 * @param aGuide - open guid action Listener
	 * @param customLev - Costem level loader aciton listener
	 */
	public void setAction(ActionListener newPlayer,ActionListener load, ActionListener sound, ActionListener openEditor, ActionListener hard,ActionListener aGuide, ActionListener customLev){
		this.newPlayer.addActionListener(newPlayer);
		this.loadPlayer.addActionListener(load);
		this.audioButton.addActionListener(sound);
		this.editorButton.addActionListener(openEditor);
		this.hardMode.addActionListener(hard);
		this.guide.addActionListener(aGuide);
		this.custom.addActionListener(customLev);
	}
	

	/**
	 * 
	 * TODO Loads the player
	 *
	 */
	public void loadPlayer(){
		Player aPlayer= null;
		//Commit out these two lines to get rid of loading player 
		
		try{
		aPlayer = load.loadPlayer();
		//aPlayer.getInfo();
		
		SimulationPanel thePanel = new SimulationPanel(load,aPlayer, this.audioOn, false);
		
		thePanel.load();
		}catch(Exception e){
			System.out.println("Can't read this type of file!");
		}
	}
	
	/**
	 * 
	 * TODO What the panel should look like
	 *
	 * @return the top half of the panel
	 */
	public JPanel getPanel(){
		JPanel thePanel = new JPanel();
		thePanel.add(this.newPlayer);
		thePanel.add(this.loadPlayer);
		thePanel.add(this.hardMode);
		return thePanel;
	}
	/**
	 * 
	 * TODO What the panel should look like
	 *
	 * @return return the bottum half of the panel
	 */
	public JPanel getPanel2(){
		JPanel thePanel = new JPanel();
		thePanel.add(this.audioButton);
		thePanel.add(this.editorButton);
		thePanel.add(this.guide);
		thePanel.add(this.custom);
		return thePanel;
	}
	
	/**
	 * 
	 * TODO Adds a new player and opens up the new player window
	 *
	 */
	public void addNewPlayer(){
		//System.out.println(this.audioButton+" Sending this to menu!!");
		final AddPlayerMenu newMenu =new AddPlayerMenu(this.load,this.audioOn,this.hard);
		
		
		class Ok implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				newMenu.hitOk();
				
			}
			
		}
		ActionListener okButton = new Ok();
		newMenu.setAction(okButton);
		newMenu.createFrame();
		
		
	}
	
	/**
	 * Toggles audio on and off
	 *
	 */
	public void changeAudio(){
		this.audioOn=!this.audioOn;
		//System.out.println("Changed Audio to "+ this.audioOn);
		if(this.audioOn){
			this.audioButton.setText("Mute");
		}else{
			this.audioButton.setText("Unmute");
		}
		
	}
	
	/**
	 * Toggles hardmode on and off
	 *
	 */
	public void changeHard(){
		this.hard=!this.hard;
	//	System.out.println("Changed HardMode to "+ this.hard);
		if(this.hard){
			this.hardMode.setText("Classic");
		}else{
			this.hardMode.setText("Hard Mode");
		}
		
		
	}
	
	/**
	 * 
	 * TODO Opens the level Editor
	 *
	 */
	public void openEditor(){
		Player player = new Player("editor", false);
		player.setLevel(-1);
		
		SimulationPanel thePanel = new SimulationPanel(this.load, player,false, true);
		
		try {
			thePanel.load();
		} catch (IOException e) {
			System.out.println("Error occured while loading: " + e.getMessage());
		}
	}
	
	/**
	 * 
	 * TODO Loads the level that the user created
	 *
	 */
	public void loadMyLevel(){
		Player newPlayer = new Player("Custom Map ", this.hard);
		
		SimulationPanel thePanel = new SimulationPanel(this.load, newPlayer, false, false);
		thePanel.loadACustom();
		try {
			thePanel.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot Load this Level" + e.getMessage());
			e.printStackTrace();
			
		}
		
	}
	
	public void openGuide(){
		JPanel myFrame = new JPanel();
		
		
		
		JLabel myLabel = new JLabel();
		myLabel.setText("Welcome to to Burger Time Guide                                                 ");
		myFrame.add(myLabel);
		JLabel myLabel1 = new JLabel();
		myLabel1.setText("                                                                                ");
		myFrame.add(myLabel1);
		JLabel myLabel0 = new JLabel();
		myLabel0.setText("Main Menu Functions                                                              ");
		myFrame.add(myLabel0);
		JLabel myLabel2 = new JLabel();
		myLabel2.setText("New Player - Open a game with a new player                                        ");
		myFrame.add(myLabel2,BorderLayout.WEST);
		JLabel myLabel3 = new JLabel();
		myLabel3.setText("Load Player - Load a previously saved game                                        ");
		myFrame.add(myLabel3);
		JLabel myLabel4 = new JLabel();
		myLabel4.setText("Mute - Mute the sound (or Unmute the sound)                                       ");
		myFrame.add(myLabel4);
		JLabel myLabel5 = new JLabel();
		myLabel5.setText("Hard Mode - Changes the diffuculty of the game to hard                            ");
		myFrame.add(myLabel5);
		
			
		JLabel myLabel6 = new JLabel();
		myLabel6.setText("Classic - Changes the difficulty of the game to classic (easy)                    ");
		myFrame.add(myLabel6);
		JLabel myLabel7 = new JLabel();
		myLabel7.setText("Level Editor - Opens a place where you can create your own level                  ");
		myFrame.add(myLabel7);
		
		JLabel myLabel5a = new JLabel();
		myLabel5a.setText("Load Custom Level - Load a custom level you created                              ");
		myFrame.add(myLabel5a);
		
		JLabel myLabel89 = new JLabel();
		myLabel89.setText("                                                                                 ");
		myFrame.add(myLabel89);
		
		
		JLabel myLabel8 = new JLabel();
		myLabel8.setText("In Game Instructions                                                            ");
		myFrame.add(myLabel8);
		JLabel myLabel9 = new JLabel();
		myLabel9.setText("Move the character using the arrow keys                                          " );
		myFrame.add(myLabel9);
		JLabel myLabel10 = new JLabel();
		myLabel10.setText("Spray pepper on enemies by using space bar (freezes enemies for a period of time)");
		myFrame.add(myLabel10);
		JLabel myLabel11 = new JLabel();
		myLabel11.setText("Walk on top of all the burgers until all of them have dropped to the lower level  ");
		myFrame.add(myLabel11);
		JLabel myLabel12 = new JLabel();
		myLabel12.setText("    to win the game. Avoid all the other moving characters to not die.            ");
		myFrame.add(myLabel12);
		
		JLabel myLabel12a = new JLabel();
		myLabel12a.setText("    Press the U Key to move up a level                                            ");
		myFrame.add(myLabel12a);
		JLabel myLabel12b = new JLabel();
		myLabel12b.setText("     Press the D key to move down a level                                          ");
		myFrame.add(myLabel12b);
		JLabel myLabel12c = new JLabel();
		myLabel12c.setText("     Press the P or ESC key to pause the game                                         ");
		myFrame.add(myLabel12c);
		
		
		JLabel myLabel13 = new JLabel();
		myLabel13.setText("                                                                                   ");
		myFrame.add(myLabel13);
		JLabel myLabel14 = new JLabel();
		myLabel14.setText("Level Editor Guide                                                                 ");
		myFrame.add(myLabel14);
		JLabel myLabel15 = new JLabel();
		myLabel15.setText("Press a number key to select the type of object you want to build                   ");
		myFrame.add(myLabel15);
		
		JLabel myLabel16g = new JLabel();
		myLabel16g.setText("    0 - Add empty space                                                             ");
		myFrame.add(myLabel16g);
		
		JLabel myLabel16 = new JLabel();
		myLabel16.setText("    1 - Normal Platform                                                              ");
		myFrame.add(myLabel16);
		JLabel myLabel16a = new JLabel();
		myLabel16a.setText("    2 - Base (Platform below ladder)                                              ");
		myFrame.add(myLabel16a);
		JLabel myLabel17 = new JLabel();
		myLabel17.setText("    3 - Ladder (even though they cover two tiles place them every tile)               ");
		myFrame.add(myLabel17);
		JLabel myLabel18 = new JLabel();
		myLabel18.setText("    4 - A Platform with a Mob                                                        ");
		myFrame.add(myLabel18);
		JLabel myLabel19 = new JLabel();
		myLabel19.setText("    5 - A Platform with a burger                                                     ");
		myFrame.add(myLabel19);
		JLabel myLabel20 = new JLabel();
		myLabel20.setText("    6 - A Platform for a burger to land on (not ment for player to walk on)           ");
		myFrame.add(myLabel20);
		JLabel myLabel21 = new JLabel();
		myLabel21.setText("    7 - Spawning Point for Player                                                     ");
		myFrame.add(myLabel21);
		JLabel myLabel22 = new JLabel();
		myLabel22.setText("                                                                                       ");
		myFrame.add(myLabel22);
		JLabel myLabel23 = new JLabel();
		myLabel23.setText("After selecting the tile you want, left click on the world to place it.                    ");
	
		myFrame.add(myLabel23);	
		
		
		JFrame myActualFrame = new JFrame("Guide to Burger Time");
		myActualFrame.add(myFrame);
		myActualFrame.setSize(500,720);
		
		myActualFrame.setVisible(true);
	
	}
	
	
}
