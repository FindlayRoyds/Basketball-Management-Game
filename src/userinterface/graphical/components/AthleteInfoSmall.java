package userinterface.graphical.components;

import java.util.Map;

import javax.swing.JLabel;

import enumeration.Position;
import game.Athlete;
import game.Purchasable;

@SuppressWarnings("serial")
public class AthleteInfoSmall extends PurchasableInfoSmall {
	String getPositionName(Athlete athlete) {
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
