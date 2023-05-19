package userinterface.commandline;

import enumeration.Location;
import game.location.GameLocation;
import game.location.GameStart;

/**
 * choose team name, season length [5, 15], seed, difficulty, starting athletes
 * 
 * @author Jake van Keulen
 * @version 1.0, May 2023.
 */
public class CLIStart extends CLILocation {
	/**
	 * The GameStart game location the cli end location is linked to.
	 */
	private GameStart gameLocation;

	/**
	 * @param gameLocation The end screen game location
	 */
	public CLIStart(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameStart) gameLocation;
	}

	private void chooseTeamName() {
		System.out.println("Choose a team name:");
		gameLocation.setTeamName(cliEnvironment.getStringInput(30));
	}

	private void chooseSeasonLength() {
		System.out.println("Choose a season length:");
		gameLocation.setSeasonLength(cliEnvironment.getIntInput(5, 15));
	}

	private void chooseSeed() {
		System.out.println("Choose a seed:");
		gameLocation.setSeed(cliEnvironment.getIntInput(0, 1000000));
	}

	private void chooseDifficulty() {
		System.out.println("Choose a difficulty:");
		String[] options = { "Easy", "Medium", "Hard" };
		int selection = cliEnvironment.displayOptions(options);
		gameLocation.setDifficulty(selection);
		System.out.println("\nDifficulty set to " + options[selection] + "\n");
	}

	private void chooseStartingAthletes() {
		// TODO
	}

	@Override
	public Location display() {
		chooseTeamName();
		chooseSeasonLength();
		chooseSeed();
		chooseDifficulty();
		return Location.MAP;
	}
}