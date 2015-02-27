package com.TeamHEC.LocomotionCommotion.Card;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import com.badlogic.gdx.graphics.Texture;

@RunWith(GdxTestRunner.class)
public class TeleportCardTest {

	TeleportCard card;
	Player player;
	String cardName;
	Texture cardTexture;
	
	@Before
	public void setUp() throws Exception {		
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
		
		
		cardName = "Teleport";
		cardTexture = Game_TextureManager.getInstance().game_card_teleportcard;	
		player.getTrains().add(new CoalTrain(0, true, new Route(WorldMap.getInstance().AMSTERDAM), player));
		card = new TeleportCard(player);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testImplementCard() {
		card.implementCard();
		assertTrue("Train route index was not set correctly", player.getTrains().get(0).getRoute().getRouteIndex() == 0);
		assertTrue("Train connection travelled was not set correctly", player.getTrains().get(0).getRoute().getConnectionTravelled() == 0);
		assertTrue("Train currentMapObj was not set correctly", player.getTrains().get(0).getRoute().getStation() == WorldMap.getInstance().stationsList.get(0));
	}

	@Test
	public void testTeleportCard() {
		assertTrue("TeleportCard's owner was not set correctly", card.getOwner() == player);
		assertTrue("TeleportCard's texutre was not set correctly", card.getImage() == cardTexture);
		assertTrue("TeleportCard's name was not set correctly", card.getName() == cardName);
	}

}
