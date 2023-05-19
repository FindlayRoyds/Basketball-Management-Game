package userinterface.commandline;

import game.location.GameLocation;
import game.location.GameMarket;
import enumeration.Location;
import game.Athlete;
import game.Purchasable;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;

/**
 * 
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class CLIAthleteMarket extends CLILocation {
	/**
	 * The game location this cli location is linked to.
	 */
	private GameMarket gameLocation;

	/**
	 * @param cliEnvironment
	 */
	public CLIAthleteMarket(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameMarket) gameLocation;
	}
	
	/**
	 * Displays a list of athlete names for the user to select one from.
	 * Returns the list index of the selected athlete.
	 * 
	 * @param athletes			A List of Athletes that can be selected.
	 * @return					The index of the selected Athlete in the athletes List.
	 */
	Purchasable getAthleteSelection(Set<Purchasable> givenAthletes) {
		List<Purchasable> athletes = new ArrayList<Purchasable>(givenAthletes);
		String[] athleteNames = new String[athletes.size()];
		for (int i = 0; i < athletes.size(); ++i) {
			athleteNames[i] = ((Athlete)athletes.get(i)).getName();
		}
		return athletes.get(cliEnvironment.displayOptions(athleteNames));
	}
	
	@Override
	public Location display() {
		while (true) {
			System.out.println("Athlete Market");
			System.out.println("What would you like to do?");
			String[] options = {"Purchase an athlete", "Sell an athlete", "Return to map"};
			int selectedIndex = cliEnvironment.displayOptions(options);
			
			if (selectedIndex == 0) {
				gameLocation.purchase(getAthleteSelection(gameLocation.getAvailablePurchasables()));
			}
			else if (selectedIndex == 1) {
				gameLocation.sell(getAthleteSelection(gameLocation.getOwnedAndAllowed()));
			}
			else if (selectedIndex == 2) {
				return Location.MAP;
			}
		}
	}
}
