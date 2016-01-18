/**
 * Joseph Militello
 * Peter Larson
 * Jacob Knispel
 */
package burgertime;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * TODO This is a mouse listener for the level editor
 *
 * @author knispeja.
 *         Created Feb 18, 2014.
 */
public class EditorListener implements MouseListener{

	LevelEditor editor;
	
	/**
	 * TODO Constructs the levelEditor
	 *
	 * @param levelEditor
	 */
	public EditorListener(LevelEditor levelEditor) {
		this.editor = levelEditor;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.editor.mouseClicked(arg0.getX(), arg0.getY());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
