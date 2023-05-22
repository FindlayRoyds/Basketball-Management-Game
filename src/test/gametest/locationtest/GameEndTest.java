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
	private GameEnvironment gameEnvironment;
	private Team team;
	private GameEnd gameEnd;

	@BeforeEach
	void setUp() {
		gameEnvironment = new GameEnvironment(false);
		gameEnvironment.setSeed(0);
		team = gameEnvironment.getPlayer().getTeam();
		gameEnd = (GameEnd) gameEnvironment.getGameLocation(Location.END);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) gameEnvironment.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		gameEnd = new GameEnd(gameEnvironment);
	}

	@Test
	void updateTest() {
		for (int i = 0; i <= 15; ++i)
			gameEnd.update(i);
	}

	@Test
	void scoreTest() {
		assertEquals(gameEnvironment.getPlayer().getScore(), gameEnd.getScore());
		gameEnvironment.getPlayer().givePoints(123);
		assertEquals(gameEnvironment.getPlayer().getScore(), gameEnd.getScore());
		gameEnvironment.getPlayer().givePoints(2000);
		assertEquals(gameEnvironment.getPlayer().getScore(), gameEnd.getScore());
	}

	@Test
	void moneyTest() {
		assertEquals(gameEnvironment.getPlayer().getMoney(), gameEnd.getMoney());
		gameEnvironment.getPlayer().giveMoney(100);
		assertEquals(gameEnvironment.getPlayer().getMoney(), gameEnd.getMoney());
		gameEnvironment.getPlayer().chargeMoney(100);
		assertEquals(gameEnvironment.getPlayer().getMoney(), gameEnd.getMoney());
	}

	@Test
	void teamNameTest() {
		gameEnvironment.getPlayer().getTeam().setName("test name");
		assertEquals(gameEnvironment.getPlayer().getTeam().getName(), gameEnd.getTeamName());
		gameEnvironment.getPlayer().getTeam().setName("123");
		assertEquals(gameEnvironment.getPlayer().getTeam().getName(), gameEnd.getTeamName());
	}

	@Test
	void seasonLengthTest() {
		gameEnvironment.setSeasonLength(5);
		assertEquals(gameEnvironment.getSeasonLength(), gameEnd.getSeasonLength());
		gameEnvironment.setSeasonLength(15);
		assertEquals(gameEnvironment.getSeasonLength(), gameEnd.getSeasonLength());
	}

	@Test
	void weekTest() {
		assertEquals(gameEnvironment.getWeek(), gameEnd.getWeek());
		gameEnvironment.setSeasonLength(5);
		gameEnvironment.progressWeek();
		assertEquals(gameEnvironment.getWeek(), gameEnd.getWeek());
	}
}
