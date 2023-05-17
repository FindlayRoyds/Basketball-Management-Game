package game;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

import enumeration.Position;
import enumeration.Statistic;
import utils.Utils;

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
	 * How much effect each statistic has in a match based on the athlete's position
	 */
	private final static Map<Position, Map<Statistic, Integer>> MATCH_POSITION_STATISTIC_WEIGHTS = Map.of(
			Position.DEFENDER, Map.of(
					Statistic.DEFENCE, 100,
					Statistic.DRIBBLING, 100,
					Statistic.FITNESS, 100,
					Statistic.HEIGHT, 100,
					Statistic.JUMPING, 100,
					Statistic.OFFENCE, 100,
					Statistic.SHOOTING_ACCURACY, 100,
					Statistic.SHOOTING_POWER, 100
					),
			Position.DRIBBLER, Map.of(
					Statistic.DEFENCE, 100,
					Statistic.DRIBBLING, 100,
					Statistic.FITNESS, 100,
					Statistic.HEIGHT, 100,
					Statistic.JUMPING, 100,
					Statistic.OFFENCE, 100,
					Statistic.SHOOTING_ACCURACY, 100,
					Statistic.SHOOTING_POWER, 100
					),
			Position.DUNKER, Map.of(
					Statistic.DEFENCE, 100,
					Statistic.DRIBBLING, 100,
					Statistic.FITNESS, 100,
					Statistic.HEIGHT, 100,
					Statistic.JUMPING, 100,
					Statistic.OFFENCE, 100,
					Statistic.SHOOTING_ACCURACY, 100,
					Statistic.SHOOTING_POWER, 100
					),
			Position.LONG_SHOOTER, Map.of(
					Statistic.DEFENCE, 100,
					Statistic.DRIBBLING, 100,
					Statistic.FITNESS, 100,
					Statistic.HEIGHT, 100,
					Statistic.JUMPING, 100,
					Statistic.OFFENCE, 100,
					Statistic.SHOOTING_ACCURACY, 100,
					Statistic.SHOOTING_POWER, 100
					),
			Position.SHORT_SHOOTER, Map.of(
					Statistic.DEFENCE, 100,
					Statistic.DRIBBLING, 100,
					Statistic.FITNESS, 100,
					Statistic.HEIGHT, 100,
					Statistic.JUMPING, 100,
					Statistic.OFFENCE, 100,
					Statistic.SHOOTING_ACCURACY, 100,
					Statistic.SHOOTING_POWER, 100
					)
			);
	
	/**
	 * Get a specific statistic from the athlete
	 * 
	 * @param statisticToGet		The statistic to get from the athlete
	 * @return 						The integer value of the specific statistic
	 */
	public Athlete(String name, Position role, int stamina, Team team, GameEnvironment gameEnvironment, int price) {
		super(price);

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
		stamina = Utils.clampValue(value);
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
			List<String> popupOptions = Arrays.asList("Active Team", "Reserve Team");
			boolean addToActive = gameEnvironment.getUIEnvironment().displayPopup(
					popupMessage, popupOptions) == 0;
			
			if (addToActive) {
				// Create a popup with a button for each position
				List<String> positionNames = Utils.getEnumerationNames(Position.class);
				popupMessage = "What role should the athlete be placed into?";
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
	 * Gets the Athlete's skill value for a given statistic.
	 * This value is an integer in the range [0, 100]
	 * 
	 * @param statisticToGet		The statistic to get.
	 * @return						The integer value of the given statistic.
	 */
	public int getStatistic(Statistic statisticToGet) {
		return statistics.get(statisticToGet);
	}
	
	/**
	 * @return		The Team the Athlete is on.
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * determines a value representing the athlete's effectiveness in a certain position
	 * 
	 * @param playedPosition		the position the athlete is playing
	 * @return						a numerical value for the athlete's effectiveness
	 */
	public int getMatchScore(Position playerPosition) {
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
		int price = rng.nextInt(1000);
		
		Athlete resultingAthlete = new Athlete(name, role, stamina, team, gameEnvironment, price);
		for (Statistic statistic: Statistic.values()) {
			resultingAthlete.statistics.put(statistic, rng.nextInt(qualityLevel));
		}
		
		return resultingAthlete;
	}
}
