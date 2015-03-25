package com.TeamHEC.LocomotionCommotion.Card;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;

@RunWith(GdxTestRunner.class)
public class GoldCardTest {
	Player player;
	GoldCard card;
	int baseGold;
		
	@Before
	public void setUp() throws Exception {
		baseGold = 500;
		player = new Player(
				"Alice",
				0,
				new Gold(baseGold),
				new Coal(500),
				new Electric(500),
				new Nuclear(500),
				new Oil(500),
				new ArrayList<Card>(),
				new ArrayList<Goal>(),
				new ArrayList<Train>()
				);		
		
		card = new GoldCard(player);
	}
	
	@Test
	public void testImplementCard() {
		card.implementCard();
		assertTrue("Player's gold was not increased", card.getOwner().getGold() > baseGold);
	}

	@Test
	public void testGoldCard() {
		assertTrue("GoldCard name was not set correctly", card.getName() == "Gold");
		assertTrue("GoldCard texture was not set correctly", card.getImage() == Game_TextureManager.getInstance().game_card_goldcard);
		assertTrue("GoldCard player was not set correctly", card.getOwner() == player);
	}

}
