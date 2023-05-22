package test.gametest.randomeventtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Position;
import game.Athlete;
import game.GameEnvironment;
import game.randomevent.AthleteJoins;
import game.randomevent.AthleteQuits;
import game.randomevent.DrugTest;
import game.randomevent.RandomEvent;
import game.randomevent.StatisticIncrease;

class RandomEventTest {
	GameEnvironment gameEnvironment;

	@BeforeEach
	void setUp() {
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeasonLength(2);
		gameEnvironment.setSeed(0);
		gameEnvironment.setDifficulty(3);
		gameEnvironment.progressWeek();
	}

	/**
	 * Every method in the random events are protected, so the only testing that can
	 * be done is calling the trigger method.
	 */
	@Test
	void testTrigger() {
		RandomEvent randomEvent = new DrugTest(gameEnvironment);
		randomEvent.trigger();
		randomEvent = new AthleteJoins(gameEnvironment, gameEnvironment.getPlayer().getTeam());
		randomEvent.trigger();
		Athlete athlete = new Athlete("", Position.DUNKER, 0, gameEnvironment, 0);
		gameEnvironment.getPlayer().getTeam().addAthleteToReserve(athlete);
		randomEvent = new AthleteQuits(gameEnvironment, athlete);
		randomEvent.trigger();
		randomEvent = new StatisticIncrease(gameEnvironment, athlete);
		randomEvent.trigger();
	}

}
