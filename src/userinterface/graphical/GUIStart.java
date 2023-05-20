package userinterface.graphical;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

import enumeration.Location;
import game.location.GameLocation;
import userinterface.graphical.components.ComponentList;
import userinterface.graphical.components.PurchasableInfoSmall;

public class GUIStart extends GUILocation {
	private static final long serialVersionUID = 1L;

	private ComponentList infoList;
	private List<JPanel> componentsToDisplay = Arrays.asList(new PurchasableInfoSmall(false),
			new PurchasableInfoSmall(false), new PurchasableInfoSmall(false), new PurchasableInfoSmall(true));

	private void selectItem(Integer selectedIndex) {
		List<JPanel> componentsToDisplay = Arrays.asList(new PurchasableInfoSmall(false),
				new PurchasableInfoSmall(false), new PurchasableInfoSmall(false));
		infoList.refresh(componentsToDisplay, selectedIndex, (index) -> selectItem(index));
	}

	/**
	 * Create the panel.
	 */
	public GUIStart(GameLocation gameLocation, GUIEnvironment guiEnvironment) {
		super(guiEnvironment);
		setLayout(null);

		infoList = new ComponentList(componentsToDisplay, 100, new Rectangle(50, 50, 250, 400),
				(index) -> selectItem(index));
		add(infoList);
	}

	@Override
	public Location refresh() {
		// TODO Auto-generated method stub
		return null;
	}
}
