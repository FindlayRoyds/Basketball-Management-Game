package userinterface.commandline;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import enumeration.Location;
import game.Athlete;
import game.Purchasable;
import game.location.GameLocation;
import game.location.GameMarket;

/**
 * 
 * 
 * @author Jake van Keulen
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
	 * Displays a list of athlete names for the user to select one from. Returns the
	 * list index of the selected athlete.
	 * 
	 * @param athletes A List of Athletes that can be selected.
	 * @return The index of the selected Athlete in the athletes List.
	 */
	Purchasable getAthleteSelection(Set<Purchasable> givenAthletes) {
		List<Purchasable> athletes = new ArrayList<Purchasable>(givenAthletes);
		if (athletes.isEmpty())
			return null;
		String[] options = new String[athletes.size()];
		for (int i = 0; i < athletes.size(); ++i) {
			Athlete athlete = (Athlete) athletes.get(i);
			options[i] = athlete.getName() + " - $" + athlete.getPrice();
		}
		return athletes.get(cliEnvironment.displayOptions(options));
	}

	@Override
	public Location display() {
		while (true) {
			System.out.println("Athlete Market");
			System.out.println("Your money: $" + gameLocation.getPlayerMoney());
			System.out.println("What would you like to do?");
			String[] options = { "Return to map", "Sell an athlete", "Purchase an athlete" };
			int selectedIndex = cliEnvironment.displayOptions(options);

			if (selectedIndex == 0) {
				return Location.MAP;
			} else if (selectedIndex == 1) {
				if (gameLocation.getOwnedAndAllowed().isEmpty())
					System.out.println("You have no athletes to sell!\n");
				else
					gameLocation.sell(getAthleteSelection(gameLocation.getOwnedAndAllowed()));
			} else if (selectedIndex == 2) {
				if (gameLocation.getAvailablePurchasables().isEmpty())
					System.out.println("You already bought everything!\n");
				else
					gameLocation.purchase(getAthleteSelection(gameLocation.getAvailablePurchasables()));
			}
		}
	}
}
