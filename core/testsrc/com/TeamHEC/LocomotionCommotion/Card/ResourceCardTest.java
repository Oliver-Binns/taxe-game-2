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
import com.badlogic.gdx.graphics.Texture;

@RunWith(GdxTestRunner.class)
public class ResourceCardTest {
	
	Card card;
	Player player;
	String cardName;
	Texture cardTexture;
	int baseFuel;
	
	@Before
	public void setUp() throws Exception {				
		player = new Player(
				"Alice",
				0,
				new Gold(baseFuel),
				new Coal(baseFuel),
				new Electric(baseFuel),
				new Nuclear(baseFuel),
				new Oil(baseFuel),
				new ArrayList<Card>(),
				new ArrayList<Goal>(),
				new ArrayList<Train>()
				);		
		
		cardName = "Coal";
		cardTexture = Game_TextureManager.getInstance().game_card_coalcard;	
		card = new CoalCard(player); //Using CoalCard to test as ResourceCard is abstract
	}

	@Test
	public void testImplementCard() {		
		card.implementCard();
		assertTrue(
				"Fuel value did not increase after card was implemented",
				player.getFuel("Coal") > baseFuel);		
		assertTrue("Oil value changed unexpectedly", 
				player.getFuel("Oil") == baseFuel);
		assertTrue("Electric value changed unexpectedly", 
				player.getFuel("Electric") == baseFuel);
		assertTrue("Nuclear value changed unexpectedly", 
				player.getFuel("Nuclear") == baseFuel);
		
		
	}

	@Test
	public void testResourceCard() {
		assertTrue("Card's owner was not set correctly", card.getOwner() == player);
		assertTrue("Card's texutre was not set correctly", card.getImage() == cardTexture);
		assertTrue("Card's name was not set correctly", card.getName() == cardName);
	}

}
