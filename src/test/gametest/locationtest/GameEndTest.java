package test.gametest.locationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Location;
import game.GameEnvironment;
import game.Team;
import game.location.GameEnd;
import userinterface.graphical.GUIEnvironment;

class GameEndTest {
	private GameEnvironment ge;
	private Team tm;
	private GameEnd gameEnd;

	@BeforeEach
	void setUp() {
		ge = new GameEnvironment(false);
		ge.setSeed(0);
		tm = ge.getPlayer().getTeam();
		gameEnd = (GameEnd) ge.getGameLocation(Location.END);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		gameEnd = new GameEnd(ge);
	}

	@Test
	void updateTest() {
		for (int i = 0; i <= 15; ++i)
			gameEnd.update(i);
	}

	@Test
	void scoreTest() {
		assertEquals(ge.getPlayer().getScore(), gameEnd.getScore());
		ge.getPlayer().givePoints(123);
		assertEquals(ge.getPlayer().getScore(), gameEnd.getScore());
		ge.getPlayer().givePoints(2000);
		assertEquals(ge.getPlayer().getScore(), gameEnd.getScore());
	}

	@Test
	void moneyTest() {
		assertEquals(ge.getPlayer().getMoney(), gameEnd.getMoney());
		ge.getPlayer().giveMoney(100);
		assertEquals(ge.getPlayer().getMoney(), gameEnd.getMoney());
		ge.getPlayer().chargeMoney(100);
		assertEquals(ge.getPlayer().getMoney(), gameEnd.getMoney());
	}

	@Test
	void teamNameTest() {
		ge.getPlayer().getTeam().setName("test name");
		assertEquals(ge.getPlayer().getTeam().getName(), gameEnd.getTeamName());
		ge.getPlayer().getTeam().setName("123");
		assertEquals(ge.getPlayer().getTeam().getName(), gameEnd.getTeamName());
	}

	@Test
	void seasonLengthTest() {
		ge.setSeasonLength(5);
		assertEquals(ge.getSeasonLength(), gameEnd.getSeasonLength());
		ge.setSeasonLength(15);
		assertEquals(ge.getSeasonLength(), gameEnd.getSeasonLength());
	}

	@Test
	void weekTest() {
		assertEquals(ge.getWeek(), gameEnd.getWeek());
		ge.setSeasonLength(5);
		ge.progressWeek();
		assertEquals(ge.getWeek(), gameEnd.getWeek());
	}
}
