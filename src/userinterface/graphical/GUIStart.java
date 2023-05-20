package userinterface.graphical;

import java.awt.Rectangle;

import enumeration.Location;
import game.location.GameLocation;
import userinterface.graphical.components.PurchasableInfoList;

public class GUIStart extends GUILocation {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public GUIStart(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		setLayout(null);

		PurchasableInfoList infoList = new PurchasableInfoList(5, 100, new Rectangle(50, 50, 250, 400));
		add(infoList);
	}

	@Override
	public Location refresh() {
		// TODO Auto-generated method stub
		return null;
	}
}
