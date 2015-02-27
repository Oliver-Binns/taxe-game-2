package com.TeamHEC.LocomotionCommotion.Card;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
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

@RunWith(GdxTestRunner.class)
public class CardFactoryTest {

	Player player;
	CardFactory cardFactory;
	
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
		
		cardFactory = new CardFactory(player);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateAnyCard() {
		//Try multiple times to ensure all cards can be created.
		HashMap<Integer, Card> cardStore = new HashMap<Integer, Card>();
		
		for(int i = 0; i < 5000; i++){
			Card temp = cardFactory.createAnyCard();
			if (temp.getName() == "Coal")
				cardStore.put(1, temp);
			else if (temp.getName() == "Oil")
				cardStore.put(2, temp);
			else if (temp.getName() == "Electric")
				cardStore.put(3, temp);
			else if (temp.getName() == "Nuclear")
				cardStore.put(4, temp);
			else if (temp.getName() == "Gold")
				cardStore.put(5, temp);
			else if (temp.getName() == "GoFasterStripes")
				cardStore.put(6, temp);
			else if (temp.getName() == "Teleport")
				cardStore.put(7, temp);
		}
		
		assertTrue("After 5000 runs, CardFactory did not create any CoalCards", cardStore.containsKey(1));
		assertTrue("After 5000 runs, CardFactory did not create any OilCards", cardStore.containsKey(2));
		assertTrue("After 5000 runs, CardFactory did not create any ElectricCards", cardStore.containsKey(3));
		assertTrue("After 5000 runs, CardFactory did not create any NuclearCards", cardStore.containsKey(4));
		assertTrue("After 5000 runs, CardFactory did not create any GoldCards", cardStore.containsKey(5));
		assertTrue("After 5000 runs, CardFactory did not create any GoFasterStripesCards", cardStore.containsKey(6));
		assertTrue("After 5000 runs, CardFactory did not create any TeleportCards", cardStore.containsKey(7));	
	}

	@Test
	public void testCreateResourceCard() {
		//Try multiple times to ensure only resource cards can be created.
		HashMap<Integer, Card> cardStore = new HashMap<Integer, Card>();
		
		for(int i = 0; i < 5000; i++){
			Card temp = cardFactory.createResourceCard();
			if (temp.getName() == "Coal")
				cardStore.put(1, temp);
			else if (temp.getName() == "Oil")
				cardStore.put(2, temp);
			else if (temp.getName() == "Electric")
				cardStore.put(3, temp);
			else if (temp.getName() == "Nuclear")
				cardStore.put(4, temp);
			else if (temp.getName() == "Gold")
				cardStore.put(5, temp);
			else if (temp.getName() == "GoFasterStripes")
				cardStore.put(6, temp);
			else if (temp.getName() == "Teleport")
				cardStore.put(7, temp);
		}
		
		assertTrue("After 5000 runs, CardFactory did not create any CoalCards", cardStore.containsKey(1));
		assertTrue("After 5000 runs, CardFactory did not create any OilCards", cardStore.containsKey(2));
		assertTrue("After 5000 runs, CardFactory did not create any ElectricCards", cardStore.containsKey(3));
		assertTrue("After 5000 runs, CardFactory did not create any NuclearCards", cardStore.containsKey(4));
		assertTrue("After 5000 runs, CardFactory did not create any GoldCards", cardStore.containsKey(5));
		assertTrue("After 5000 runs, CardFactory created at least one GoFasterStripesCard", !cardStore.containsKey(6));
		assertTrue("After 5000 runs, CardFactory created at least one TeleportCard", !cardStore.containsKey(7));	
	}

	@Test
	public void testCreateMagicCard() {
		//Try multiple times to ensure only resource cards can be created.
		HashMap<Integer, Card> cardStore = new HashMap<Integer, Card>();
		
		for(int i = 0; i < 5000; i++){
			Card temp = cardFactory.createMagicCard();
			if (temp.getName() == "Coal")
				cardStore.put(1, temp);
			else if (temp.getName() == "Oil")
				cardStore.put(2, temp);
			else if (temp.getName() == "Electric")
				cardStore.put(3, temp);
			else if (temp.getName() == "Nuclear")
				cardStore.put(4, temp);
			else if (temp.getName() == "Gold")
				cardStore.put(5, temp);
			else if (temp.getName() == "GoFasterStripes")
				cardStore.put(6, temp);
			else if (temp.getName() == "Teleport")
				cardStore.put(7, temp);
		}
		
		assertTrue("After 5000 runs, CardFactory created at least one CoalCard", !cardStore.containsKey(1));
		assertTrue("After 5000 runs, CardFactory created at least one OilCard", !cardStore.containsKey(2));
		assertTrue("After 5000 runs, CardFactory created at least one ElectricCard", !cardStore.containsKey(3));
		assertTrue("After 5000 runs, CardFactory created at least one NuclearCard", !cardStore.containsKey(4));
		assertTrue("After 5000 runs, CardFactory created at least one GoldCard", !cardStore.containsKey(5));
		assertTrue("After 5000 runs, CardFactory did not create any GoFasterStripesCards", cardStore.containsKey(6));
		assertTrue("After 5000 runs, CardFactory did not create any TeleportCards", cardStore.containsKey(7));	
	}

}
