package userinterface.graphical;

import javax.swing.JPanel;

import enumeration.Location;

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
	}

	/**
	 * Refreshes the content to display on the screen
	 * 
	 * @return An ordered List of options to be displayed on the gui.
	 */
	public abstract Location refresh();
}
