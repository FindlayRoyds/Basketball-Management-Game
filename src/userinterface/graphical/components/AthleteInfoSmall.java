package userinterface.graphical.components;

import java.util.Map;

import javax.swing.JLabel;

import enumeration.Position;
import game.Athlete;
import game.Purchasable;

/**
 * A class extending the PurchasableInfoSmall component to also display the
 * current position of each athlete on the player's team.
 * 
 * @author Jake van Keulen
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AthleteInfoSmall extends PurchasableInfoSmall {
	/**
	 * Gets the Position that the given athlete occupies on the player's team.
	 * 
	 * @param athlete The athlete whose position is to be returned.
	 * @return The position of the given athlete.
	 */
	private String getPositionName(Athlete athlete) {
		String positionName = "Reserve";

		Map<Position, Athlete> activeAthletesOnTeam = athlete.getTeam().getActiveAthletes();
		for (Position position : Position.values()) {
			if (activeAthletesOnTeam.get(position) == athlete) {
				positionName = position.name().toLowerCase().replace("_", " ");
				break;
			}
		}

		return positionName;
	}

	/**
	 * Constructor for AthleteInfoSmall. Calls the super class constructor and adds
	 * the extra data of the given athlete's position.
	 * 
	 * @param athlete   The athlete whose info is to be displayed in the info panel.
	 * @param showPrice Whether or not the price of the given athlete should be
	 *                  displayed.
	 */
	public AthleteInfoSmall(Athlete athlete, boolean showPrice) {
		super((Purchasable) athlete, showPrice);

		JLabel positionDescriptionLabel = new JLabel("Position:");
		positionDescriptionLabel.setBounds(6, 16, 129, 48);
		add(positionDescriptionLabel);

		JLabel positionLabel = new JLabel(getPositionName(athlete));
		positionLabel.setBounds(65, 16, 129, 48);
		add(positionLabel);
	}

}
