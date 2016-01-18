/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * TODO The button that allows people to save
 *
 * @author militeja.
 *         Created Feb 12, 2014.
 */
public class SaveButton {

	private Loader load;
	private Player thePlayer;
	private JButton save;
	private SimulationPanel panel;
	
	/**
	 * 
	 * TODO Constructs the button
	 *
	 * @param l - the loader object
	 * @param p - the player
	 * @param panel - the simulation panel
	 */
	public SaveButton(Loader l, Player p, SimulationPanel panel){
		
		this.panel = panel;
		
		String buttonText;
		
		if(p.getName().equals("editor"))
			buttonText = "Save World";
		else
			buttonText = "Save and Quit";
		
		this.save = new JButton(buttonText);
		
		this.load = l;
		this.thePlayer = p;
	}
	
	/**
	 * 
	 * TODO What the panel should look like
	 *
	 * @return a JPanel
	 */
	public JPanel getPanel(){
		JPanel j = new JPanel();
		j.add(this.save);
		return j;
	}
	
	/**
	 * 
	 * TODO Saves the player stats
	 *
	 */
	 
	public void save(){
		this.panel.currentWorld.pauseResume();
		if(!this.save.getText().equals("Save World"))
		{
			this.load.saveGame(this.thePlayer);
			System.exit(0);
		}
		else
		{
			this.panel.saveWorld();
			System.exit(0);
		}
		
	}
	
	/**
	 * 
	 * TODO Sets the action listener for the button
	 *
	 * @param action - an Action listener
	 */
	public void setAction(ActionListener action){
		this.save.addActionListener(action);
	}
}
