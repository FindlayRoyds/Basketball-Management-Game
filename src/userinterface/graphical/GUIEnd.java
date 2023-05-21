package userinterface.graphical;

import game.location.GameEnd;
import game.location.GameLocation;

public class GUIEnd extends GUILocation {
	private static final long serialVersionUID = 1L;
	GameEnd gameLocation;

	/**
	 * Creates the GUIEnd component.
	 */
	public GUIEnd(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		this.gameLocation = (GameEnd) gameLocation;
		setLayout(null);
	}
}
