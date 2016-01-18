package burgertime;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * @author Your team number and names here and in all your code files
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args)  {	
		/*
		 * I Added some new stuff. Visual looks will be changed later. 
		 * Just getting stuff to work.
		 * If you have problems let me know.
		 * 
		 */
		
		JFrame mainMenu = new JFrame();
		final MainMenuButtons buttons = new MainMenuButtons();
	
		mainMenu.setTitle("Burger Time Main Menu");
		
		/**
		 * 
		 * TODO Action Listener for new Player function
		 *
		 * @author militeja.
		 *         Created Feb 19, 2014.
		 */
		class Player implements ActionListener{

			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buttons.addNewPlayer();
				
			}
			
		}
		
		/**
		 * 
		 * TODO Action Listener for loading the player
		 *
		 * @author militeja.
		 *         Created Feb 19, 2014.
		 */
		class loadPlayer implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buttons.loadPlayer();
				
			}
		}
		
		/**
		 * 
		 * TODO Action Listener for changing audio
		 *
		 * @author militeja.
		 *         Created Feb 19, 2014.
		 */
		class muteThis implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				buttons.changeAudio();
				
			}
		}
		
		/**
		 * 
		 * TODO Action Listener for opening the editor
		 * @author militeja.
		 *         Created Feb 19, 2014.
		 */
		class openEditor implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				buttons.openEditor();
				
			}
		}
		
		/**
		 * 
		 * TODO Action Listener for changing difficulty
		 *
		 * @author militeja.
		 *         Created Feb 19, 2014.
		 */
		class hardMode implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				buttons.changeHard();
	
			}
		}
		/**
		 * 
		 * TODO Action Listener for opening the guide
		 *
		 * @author militeja.
		 *         Created Feb 19, 2014.
		 */
		class openGuide implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				buttons.openGuide();
	
			}
		}
		
		/**
		 * 
		 * TODO Action Listener for loading a custom level
		 *
		 * @author militeja.
		 *         Created Feb 19, 2014.
		 */
		class loadLevel implements ActionListener{
			public void actionPerformed(ActionEvent arg0){
				buttons.loadMyLevel();
			}
		}
		
		
		ActionListener theGuide = new  openGuide();
		ActionListener aNewPlayer = new Player();
		ActionListener aLoadPlayer = new loadPlayer();
		ActionListener muteTheSound = new muteThis();
		ActionListener openEditor = new openEditor();
		ActionListener hardMode = new hardMode();
		ActionListener custLevel = new loadLevel();
		
		buttons.setAction(aNewPlayer,aLoadPlayer,muteTheSound, openEditor, hardMode,theGuide,custLevel);
		mainMenu.add(buttons.getPanel(),BorderLayout.NORTH);
		mainMenu.add(buttons.getPanel2(),BorderLayout.SOUTH);
	
		
	
		mainMenu.setSize(500,120);
		mainMenu.setVisible(true);
		
	}
}
