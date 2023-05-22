package userinterface.graphical;

import javax.swing.JPanel;

/**
 * An abstract class for a GUI location. Declares the necessary methods and
 * properties to be swapped in and out of the GUIEnvironment's user-facing
 * frame.
 * 
 * @author Findlay Royds, Jake van Keulen
 * 
 * @version 1.0, May 2023.
 */
@SuppressWarnings("serial")
public abstract class GUILocation extends JPanel {
	/**
	 * The GUIEnvironment that the GUILocation belongs to.
	 */
	protected GUIEnvironment guiEnvironment;

	/**
	 * The constructor for GUILocation.
	 * 
	 * @param guiEnvironment The GUIEnvironment that the GUILocation belongs to.
	 */
	public GUILocation(GUIEnvironment guiEnvironment) {
		this.guiEnvironment = guiEnvironment;
		setLayout(null);
	}

	/**
	 * Refreshes the content to display on the screen. Exists to be overridden by
	 * derived classes, defining how to refresh their content.
	 */
	public void refresh() {
		// do nothing
	}
}
