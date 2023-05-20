package userinterface.graphical;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * 
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public abstract class GUILocation extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * The GUIEnvironment that the GUILocation belongs to.
	 */
	protected GUIEnvironment guiEnvironment;

	/**
	 * The constructor for GUILocation
	 * 
	 * @param guiEnvironment The GUIEnvironment that the GUILocation belongs to.
	 */
	public GUILocation(GUIEnvironment guiEnvironment) {
		this.guiEnvironment = guiEnvironment;
		setLayout(null);
		setPreferredSize(new Dimension(800, 600));
	}

	/**
	 * Refreshes the content to display on the screen
	 */
	public void refresh() {
		// do nothing
	}
}
