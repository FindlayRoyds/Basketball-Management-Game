package test.gametest.locationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Location;
import game.Athlete;
import game.GameEnvironment;
import game.Purchasable;
import game.location.GameMarket;
import userinterface.graphical.GUIEnvironment;

class GameAthleteMarketTest {
	private GameEnvironment ge;
	private GameMarket gameMarket;

	@BeforeEach
	void setUp() {
		ge = new GameEnvironment(false);
		ge.setSeasonLength(5);
		ge.setSeed(0);
		gameMarket = (GameMarket) ge.getGameLocation(Location.ATHLETE_MARKET);
	}

	@AfterEach
	void tearDown() {
		JFrame frame = ((GUIEnvironment) ge.getUIEnvironment()).getFrame();
		frame.setVisible(false);
		frame.dispose();
	}

	@Test
	void constructorTest() {
		new GameMarket(ge, Athlete.generateAthlete, ge.getPlayer().getTeam().getAllPurchasables, false, 8);
	}

	@Test
	void updateTest() {
		for (int i = 0; i <= 15; ++i)
			gameMarket.update(i);
		assertFalse(gameMarket.getAvailablePurchasables().isEmpty());
	}

	@Test
	void getMoneyTest() {
		assertEquals(ge.getPlayer().getMoney(), gameMarket.getPlayerMoney());
		ge.getPlayer().giveMoney(100);
		assertEquals(ge.getPlayer().getMoney(), gameMarket.getPlayerMoney());
		ge.getPlayer().giveMoney(234);
		assertEquals(ge.getPlayer().getMoney(), gameMarket.getPlayerMoney());
	}

	@Test
	void ownedAndAllowedTest() {
		for (Purchasable purchasable : gameMarket.getOwnedAndAllowed()) {
			assertEquals(true, purchasable.getIsLegal());
			assertTrue(ge.getPlayer().getTeam().getAllAthletes().contains(purchasable));
		}
	}

	@Test
	void transactionTest() {
		ge.getPlayer().giveMoney(100000);
		gameMarket.update(1);
		assertFalse(gameMarket.getAvailablePurchasables().isEmpty());

		Purchasable transactionPurchasable = new ArrayList<Purchasable>(gameMarket.getAvailablePurchasables()).get(0);
		gameMarket.purchase(transactionPurchasable);
		assertFalse(gameMarket.getAvailablePurchasables().contains(transactionPurchasable));
		assertEquals(1, ge.getPlayer().getTeam().getAllAthletes().size());
		assertTrue(ge.getPlayer().getTeam().getAllAthletes().contains(transactionPurchasable));

		gameMarket.sell(transactionPurchasable);
		assertTrue(gameMarket.getAvailablePurchasables().contains(transactionPurchasable));
		assertEquals(0, ge.getPlayer().getTeam().getAllAthletes().size());
		assertFalse(ge.getPlayer().getTeam().getAllAthletes().contains(transactionPurchasable));
	}
}
