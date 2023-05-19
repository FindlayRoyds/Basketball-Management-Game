package userinterface.commandline;

import game.location.GameLocker;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import enumeration.Location;
import enumeration.Position;
import game.Athlete;
import game.location.GameLocation;

/**
 * 
 * 
 * @author Findlay Royds
 * @version 1.0, May 2023.
 */
public class CLILocker extends CLILocation {
	/**
	 * The game location this cli location is linked to.
	 */
	private GameLocker gameLocation;

	/**
	 * @param cliEnvironment
	 */
	public CLILocker(GameLocation gameLocation, CLIEnvironment cliEnvironment) {
		super(cliEnvironment);
		this.gameLocation = (GameLocker) gameLocation;
	}

	/**
	 * Displays a list of all team members and their current roles.
	 */
	private void displayTeam() {
		System.out.println("Active athletes:");
		Map<Position, Athlete> activeAthletes = gameLocation.getActive();
		for (Position position : Position.values()) {
			String positionName = position.name().toLowerCase();
			Athlete athlete = activeAthletes.get(position);
			String athleteName = (athlete == null ? "[No athlete]" : athlete.getName());
			System.out.println(positionName + ": " + athleteName);
		}

		List<Athlete> reserveAthletes = new ArrayList<Athlete>(gameLocation.getReserves());
		for (int i = 1; i <= gameLocation.getMaxNumberOfReserves(); ++i) {
			String athleteName = (i > reserveAthletes.size() ? "[No athlete]" : reserveAthletes.get(i - 1).getName());
			System.out.println("Reserve #" + i + ": " + athleteName);
		}
	}

	/**
	 * Prompts the user to select an athlete, then a position to move them to. Then
	 * the selected athlete is moved to the selected position.
	 */
	private void moveAthlete() {
		System.out.println("Which athlete would you like to move");
		List<Athlete> athletes = new ArrayList<Athlete>(gameLocation.getAllAthletes());
		String[] athleteNames = new String[athletes.size()];
		for (int i = 0; i < athletes.size(); ++i) {
			athleteNames[i] = athletes.get(i).getName();
		}
		Athlete selectedAthlete = athletes.get(cliEnvironment.displayOptions(athleteNames));
		
		System.out.println("Where would you like to move that athlete?");
		String[] options = new String[Position.values().length];
		options[0] = "Reserve";
		int i = 0;
		for (Position position: Position.values()) {
			options[i+1] = position.name();
			++i;
		}
		
		int selection = cliEnvironment.displayOptions(options);
		if (selection == 0) {
			gameLocation.moveToReserve(selectedAthlete);
		}
		else {
			gameLocation.moveToActive(selectedAthlete, Position.values()[selection+1]);
		}
	}

	@Override
	public Location display() {
		while (true) {
			System.out.println("Locker Room");
			displayTeam();

			String[] options = { "Move an athlete", "Exit to map" };
			int selection = cliEnvironment.displayOptions(options);
			if (selection == 0) {
				moveAthlete();
			} else if (selection == 1) {
				return Location.MAP;
			}
		}
	}

}
