package com.TeamHEC.LocomotionCommotion.Card;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Train.CoalTrain;
import com.TeamHEC.LocomotionCommotion.Train.Route;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;

@RunWith(GdxTestRunner.class)
public class GoFasterStripesCardTest {
	Player player;
	GoFasterStripesCard card;
	int currentSpeedUpgrade;
	
	@Before
	public void setUp() throws Exception {
		currentSpeedUpgrade = 10; //Update if it changes
		player = new Player(
				"Alice",
				0,
				new Gold(500),
				new Coal(500),
				new Electric(500),
				new Nuclear(500),
				new Oil(500),
				new ArrayList<Card>(),
				new ArrayList<Goal>(),
				new ArrayList<Train>()
				);		
		
		player.getTrains().add(new CoalTrain(0, true, new Route(WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).stationList()[0]), player));

		card = new GoFasterStripesCard(player);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testImplementCard() {
		card.implementCard();
		assertTrue("Player's train speedMod was not improved", player.getTrains().get(0).getSpeedMod() == currentSpeedUpgrade);
	}

	@Test
	public void testGoFasterStripesCard() {
		assertTrue("Card name was not set correctly", card.getName() == "GoFasterStripes");
		assertTrue("Card texture was not set correctly", card.getImage() == Game_TextureManager.getInstance().game_card_gofasterstripescard);
		assertTrue("Card player was not set correctly", card.getOwner() == player);
	}

}
