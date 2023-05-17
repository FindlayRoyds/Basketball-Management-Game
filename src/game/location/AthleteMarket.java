package game.location;

import java.util.Set;

import enumeration.Position;

import java.util.List;
import java.util.Map;

import game.Athlete;
import game.Purchasable;
import game.Team;

/**
 * A class that extends the Market game location to allow for the sale of Athletes.
 * Adds the functionality for adding and removing Athletes from the Player's Team,
 * to complete their purchase or sale.
 * Also provides access to view the active and reserve athletes on the Player's Team.
 * 
 * @author Jake van Keulen
 * @version 1.0
 *
 */
public class AthleteMarket extends Market {
	/**
	 * Removes an Athlete from the Player's Team.
	 * 
	 * @param purchasable		The Athlete to be removed.
	 */
	@Override
	protected void removePurchasableFromPlayer(Purchasable purchasable) {
		getGameEnvironment().getPlayer().getTeam().removeAthlete((Athlete)purchasable);
	}

	/**
	 * Adds an Athlete to the Player's Team.
	 * First the user is prompted through the UI to choose a position to add them to.
	 * Then the Athlete is added to the chosen position.
	 * 
	 * @param purchasable		The Athlete to be added.
	 */
	@Override
	protected void givePurchasableToPlayer(Purchasable purchasable) {
		final String popupMessage = "In what position would you like to place the player in the team?";

		// TODO: Fill popupOptions with String values from Position enum.
		List<String> popupOptions = null;
		int selectedIndex = getGameEnvironment().getUIEnvironment().displayPopup(popupMessage, popupOptions);
		Team team = getGameEnvironment().getPlayer().getTeam();
		if (selectedIndex == popupOptions.size()-1) {
			team.addAthleteToReserve((Athlete)purchasable);
		}
		else {
			team.addAthleteToActive((Athlete)purchasable, Position.values()[selectedIndex]);
		}
	}
	
	/**
	 * @return		The Set of reserve Athletes in the Player's Team.
	 */
	public Set<Athlete> getReserveAthletes() {
		return getGameEnvironment().getPlayer().getTeam().getReserveAthletes();
	}
	
	/**
	 * @return		A Map of Position enum values to the Athletes in those Positions on the Player's Team.
	 */
	public Map<Position, Athlete> getActiveAthletes() {
		return getGameEnvironment().getPlayer().getTeam().getActiveAthletes();
		
	}
}
