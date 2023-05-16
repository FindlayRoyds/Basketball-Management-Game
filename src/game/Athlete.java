package game;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Arrays;

import enumeration.Position;
import enumeration.Statistic;

/**
 * This class implements an athlete
 * An athlete belongs to a team,
 * And has several statistics to gauge their performance
 * 
 * @author Findlay Royds, Jake van Keulen
 * @version 1.1, May 2023.
 */
public class Athlete extends Purchasable {	
	/**
	 * The name of the athlete
	 */
	private String name;
	
	/**
	 * The position the athlete will get a bonus for playing in
	 */
	private Position role;
	
	/**
	 * Map of statistic types (from the Statistic enum) to an integer,
	 * ranging from 0 to 100 (inclusive)
	 */
	private HashMap<Statistic, Integer> statistics;
	
	/**
	 * The remaining stamina of the athlete
	 * Depletes after playing in a match
	 * Will cause an injury if it reaches zero
	 */
	private int stamina;
	
	/**
	 * The team the athlete plays in matches for
	 */
	private Team team;
	
	/**
	 * The game environment of the current game
	 * Required to show a pop up message when an athlete is purchased
	 */
	private GameEnvironment gameEnvironment;
	
	/**
	 * Get a specific statistic from the athlete
	 * 
	 * @param statisticToGet		The statistic to get from the athlete
	 * @return 						The integer value of the specific statistic
	 */

	public Athlete(String name, Position role, int stamina, Team team, GameEnvironment gameEnvironment) {
		this.name = name;
		this.role = role;
		this.stamina = stamina;
		this.team = team;
		this.gameEnvironment = gameEnvironment;

		// Initialize all statistics to 0;
		for (Statistic statistic: Statistic.values()) {
			statistics.put(statistic, 0);
		}
	}
	
	public int getStatistic(Statistic statisticToGet) {
		return statistics.get(statisticToGet);
	}
	
	/**
	 * Clamp an integer between the values 0 and 100 (inclusive)
	 * Used for setting an athletes statistic or stamina
	 * 
	 * @param value					the integer value to be clamped
	 * @return 						the integer value clamped between 0 and 100
	 */
	
	private int clampValue(int value) {
		return Math.max(0, Math.min(100, value));
	}
	
	/**
	 * Set a specific statistic to an integer value
	 * 
	 * @param statisticToSet		The statistic to set the value of
	 * @param value					The value to set the statistic to
	 */
	public void setStatistic(Statistic statisticToSet, int value) {
		statistics.put(statisticToSet, value);
	}
	
	/**
	 * Set the athlete's stamina to an integer value between 0 and 100
	 * 
	 * @param value					The value to set the athlete's stamina to
	 */
	
	public void setStamina(int value) {
		stamina = clampValue(value);
	}
	
	/**
	 * generates an array of names for an enumeration
	 * created by https://stackoverflow.com/users/256196/bohemian
	 * 
	 * @param e						The class of the enumeration
	 * @return						an array of strings of the names of the enum
	 */
	private static String[] getEnumNames(Class<? extends Enum<?>> e) {
	    return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
	}
	
	/**
	 * Attempts to charge the player the price of the athlete
	 * If the player can afford it, the athlete is added to the player's team
	 * 
	 * @param player				The player attempting to purchase the athlete
	 * @return						Whether or not the purchase was successful
	 */
	public boolean purchase(Player player) {
		boolean chargeSuccess = player.chargeMoney(price);
		
		// Add to player's team if charge successful
		if (chargeSuccess) {
			Team playerTeam = player.getTeam();
			//HashMap<Position, Athlete> activeAthletes = playerTeam.getActiveAthletes();
			
			// Give player option to add athlete to active or reserve team
			String popupMessage = "Please select where to add the athlete";
			String[] popupOptions = {"Active Team", "Reserve Team"};
			boolean addToActive = gameEnvironment.getUIEnvironment().displayPopup(
					popupMessage, popupOptions) == 0;
			if (addToActive) {
				popupMessage = "What role should the athlete be placed into?";
				// Get an array of strings describing each position
				String[] positionNames = getEnumNames(Position.class);
				int selectedIndex = gameEnvironment.getUIEnvironment().displayPopup(
						popupMessage, positionNames);
				Position selectedPosition = Position.values()[selectedIndex];
				playerTeam.addAthleteToActive(this, selectedPosition);
			} else {
				playerTeam.addAthleteToReserve(this);
			}
		}
		return chargeSuccess;
	}
	
	/**
	 * determines a value representing the athlete's effectiveness in a certain position
	 * 
	 * @param playedPosition		the position the athlete is playing
	 * @return						a numerical value for the athlete's effectiveness
	 */
	public float getMatchScore(Position playerPosition) {
		//TODO make this work
		return 0;
	}
	
	/**
	 * gets the name of the athlete
	 * 
	 * @return 						the name of the athlete
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets the position the athlete will get a bonus for playing in
	 * 
	 * @return						the athlete's role
	 */
	public Position getRole() {
		return role;
	}
	
	/**
	 * gets the athlete's remaining stamina
	 * 
	 * @return 						the athlete's remaining stamina
	 */
	public int getStamina() {
		return stamina;
	}
	
	/**
	 * gets whether or not the athlete is injured
	 * an athlete is injured when their stamina is 0
	 * 
	 * @return 						whether or not the athlete is injured
	 */
	public boolean isInjured() {
		return stamina == 0;
	}
	
	/**
	 * Update's the Athlete's team property.
	 * 
	 * @param newTeam		The team to assign the athlete to.
	 */
	public void setTeam(Team newTeam) {
		team = newTeam;
	}
	
	/**
	 * Generates and returns an Athlete with pseudo-random properties.
	 * 
	 * @param qualityLevel			The "quality" of the Athlete's statistics.
	 * 								A higher quality level makes higher statistics more likely to be generated.
	 * @param rng					An instance of Random; Used to generate random stats, roles, and names
	 * @param team					The team to which the Athlete belongs.
	 * @param gameEnvironment		The GameEnvironment object the Athlete belongs to.
	 * @return						A randomly generated Athlete.
	 */
	public static Athlete generateAthlete(int qualityLevel, Random rng, Team team, GameEnvironment gameEnvironment) {
		String name = Utils.generateName("playerFirstNames", "playerLastNames", rng);
		
		Position[] positions = Position.values();
		Position role = positions[rng.nextInt(positions.length)];

		int stamina = rng.nextInt(100);
		
		Athlete resultingAthlete = new Athlete(name, role, stamina, team, gameEnvironment);
		for (Statistic statistic: Statistic.values()) {
			resultingAthlete.statistics.put(statistic, rng.nextInt(qualityLevel));
		}
		
		return resultingAthlete;
	}
}
