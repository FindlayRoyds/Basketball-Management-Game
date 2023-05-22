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
	GameEnvironment ge;

	@BeforeEach
	void setUp() {
		ge = new GameEnvironment(false);
		ge.setSeasonLength(2);
		ge.setSeed(0);
		ge.setDifficulty(3);
		ge.progressWeek();
	}

	/**
	 * Every method in the random events are protected, so the only testing that can
	 * be done is calling the trigger method.
	 */
	@Test
	void testTrigger() {
		RandomEvent re = new DrugTest(ge);
		re.trigger();
		re = new AthleteJoins(ge, ge.getPlayer().getTeam());
		re.trigger();
		Athlete athlete = new Athlete("", Position.DUNKER, 0, ge, 0);
		ge.getPlayer().getTeam().addAthleteToReserve(athlete);
		re = new AthleteQuits(ge, athlete);
		re.trigger();
		re = new StatisticIncrease(ge, athlete);
		re.trigger();
	}

}
