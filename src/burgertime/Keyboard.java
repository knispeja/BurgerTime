/**
 Joseph Militello
 * Peter Larson
 * Jacob Knispel
 * 
 */
package burgertime;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/**
 * TODO Listens for keyboard inputs
 *
 * @author militeja.
 *         Created Feb 4, 2014.
 */
public class Keyboard extends JPanel{
	
	/**
	 * 
	 * TODO Constucts a the keyboard listener
	 *
	 * @param SP - The simulation panel
	 */
	public Keyboard(SimulationPanel SP){
		KeyListener listener = new MyKeyListener(SP);
		addKeyListener(listener);
		setFocusable(true);
	}
	
	
	/**
	 * 
	 * TODO The action listener of the keyboard
	 *
	 * @author militeja.
	 *         Created Feb 19, 2014.
	 */
	public class MyKeyListener implements KeyListener{

		private SimulationPanel myPanel;
		public MyKeyListener(SimulationPanel SP){
			this.myPanel= SP;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			//System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
			this.myPanel.whatKey(KeyEvent.getKeyText(e.getKeyCode()));
		}

		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyReleased(KeyEvent arg0) {
			this.myPanel.keyReleased(KeyEvent.getKeyText(arg0.getKeyCode()));
		}

		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
