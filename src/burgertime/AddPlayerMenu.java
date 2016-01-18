/**
*
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 *
 * 
 */
package burgertime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Creates a window for the user to type in and enter in a new name
 *
 * @author militeja.
 *         Created Feb 9, 2014.
 */
public class AddPlayerMenu {
	private JButton acceptName= new JButton("OK");
	private JTextField textField = new JTextField(); 
	private boolean isOn;
	private Loader load;
	private JFrame theFrame;
	private boolean soundOn;
	private boolean hardMode;
	
	
	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 * @param load - the loading object that is created in the main program
	 * @param soundOn - boolean to see if the sound is on
	 * @param hardMode - boolean to see if hard mode is on
	 */
	public AddPlayerMenu(Loader load,boolean soundOn, boolean hardMode){
	
		//Setting prefered demension size
		 Dimension dim = new Dimension(100,30);  
		 	this.soundOn = soundOn;
		 	this.textField.setPreferredSize(dim); 
	        this.theFrame = new JFrame("New Player");
	        this.isOn=true;
	        this.load = load;
	        this.hardMode = hardMode;
	}
	
	
	/**
	 * 
	 * TODO Sets the action listener for the ok button
	 *
	 * @param action - Action Listener for ok button
	 */
	public void setAction(ActionListener action){
		this.acceptName.addActionListener(action);
	}
	
	/**
	 * 
	 * TODO Returns what the panel should look like
	 *
	 * @return the panel that should be put on the frame
	 */
	public JPanel getPanel(){
		
		JPanel thePanel = new JPanel();
		
        thePanel.add(this.textField); 
        thePanel.add(this.acceptName);
      
		return thePanel;
	}
	
	/**
	 * 
	 * TODO Create a player at level 1 with the given name in the text box
	 *
	 */
	public void hitOk(){
		
	//	System.out.println(this.textField.getText());
		this.theFrame.dispose();
		
		
		try{
			//System.out.println("THIS CODE IS REACHED" + this.hardMode);
			Player aPlayer = new Player(this.textField.getText(), this.hardMode);
		//	aPlayer.getInfo();
			
			SimulationPanel thePanel = new SimulationPanel(this.load,aPlayer,this.soundOn, false);
			
			thePanel.load();
			}catch(Exception e){
				System.out.println("Loading error: " + e.getMessage());
			}
		
		
		
		this.isOn = false;
	}
	
	/**
	 * 
	 * TODO sees if window is visible
	 *
	 * @return boolean if window is visible
	 */
	public boolean isVisible(){
		//return true;
		return this.isOn;
	}
	
	/**
	 * 
	 * TODO Creates a frame for the new Player window
	 *
	 */
	public void createFrame(){
		
		JLabel aLabel = new JLabel();
		
		aLabel.setText("Please enter in your name");
		this.theFrame.add(aLabel, BorderLayout.NORTH);
		this.theFrame.add(this.getPanel(), BorderLayout.SOUTH);
		this.theFrame.setBackground(Color.black);
		this.theFrame.pack();
		this.theFrame.setVisible(true);
	}
	
	/*
	 * JTextField textField = new JTextField();  
        Dimension dim = new Dimension(20,30);  
        textField.setPreferredSize(dim); 
	 */
}
