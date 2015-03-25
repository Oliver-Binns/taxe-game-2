package com.TeamHEC.LocomotionCommotion.Player;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Card.CardFactory;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Train.Train;

/**
 * 
 * @author Callum Hewitt <ch1194@york.ac.uk>
 *
 */
@RunWith(GdxTestRunner.class)
public class ShopTest {

	int baseFuelValue;
	int baseGoldValue;
	
	String customerName;
	int customerPoints;
	Gold customerGold;
	Coal customerCoal;
	Electric customerElectric;
	Nuclear customerNuclear;
	Oil customerOil;
	ArrayList<Card> customerCards;
	ArrayList<Goal> customerGoals;
	ArrayList<Train> customerTrains;
	
	Player testCustomer;
	Shop testShop;
	
	
	@Before
	public void setUp() throws Exception {
		baseFuelValue = 20000;
		baseGoldValue = 50000000;
		
		customerName = "Alice";
		customerPoints = 100;
		customerGold = new Gold(baseGoldValue);
		customerCoal = new Coal(baseFuelValue);
		customerElectric = new Electric(baseFuelValue);
		customerNuclear = new Nuclear(baseFuelValue);
		customerOil = new Oil(baseFuelValue);
		customerCards = new ArrayList<Card>();
		customerGoals = new ArrayList<Goal>();
		customerTrains = new ArrayList<Train>();
		
		testCustomer = new Player(
				customerName,
				customerPoints,
				customerGold,
				customerCoal,
				customerElectric,
				customerNuclear,
				customerOil,
				customerCards,
				customerGoals,
				customerTrains
				);
		
		testShop = new Shop(testCustomer);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Gets the field value from an instance.  The field we wish to retrieve is
	 * specified by passing the name.  The value will be returned, even if the
	 * field would have private or protected access.
	 */
	@SuppressWarnings("rawtypes")
	private Object getField( Object instance, String name ) throws Exception {
		Class c = instance.getClass();

		// Retrieve the field with the specified name
		Field f = c.getDeclaredField( name );

		// Make sure the field is accessible, even if it
		// would be private or protected
		f.setAccessible( true );

		// Return the value of the field for the instance
		return f.get( instance );
	}
	
	@Test
	public void testShop() throws Exception {
		assertTrue("testShop's customer was not set correctly", (Player) getField(testShop, "customer") == testCustomer);
		assertTrue("testShop did not initialise an instance of CardFactory",((CardFactory) getField(testShop, "cardFactory")) != null);
	}

	@Test
	public void testBuyFuel() {
		//Setup
		int currentGold = testCustomer.getGold();
		
		//Execute coal buy
		testShop.buyFuel("Coal", 500, true);		
		//Test
		assertTrue(
				"testCustomer's Coal was not incremented by 500",
				testCustomer.getFuel("Coal") == baseFuelValue + 500);
		assertTrue(
				"testCustomer's Gold was not decremented by 500 * Shop.coalPrice",
				testCustomer.getGold() == currentGold - 500 * Shop.coalPrice);
		
		//Execute oil buy
		currentGold = testCustomer.getGold();
		testShop.buyFuel("Oil", 500, true);		
		//Test
		assertTrue(
				"testCustomer's Oil was not incremented by 500",
				testCustomer.getFuel("Oil") == baseFuelValue + 500);
		assertTrue(
				"testCustomer's Gold was not decremented by 500 * Shop.oilPrice",
				testCustomer.getGold() == currentGold - 500 * Shop.oilPrice);
		
		//Execute electric buy
		currentGold = testCustomer.getGold();
		testShop.buyFuel("Electric", 500, true);		
		//Test
		assertTrue(
				"testCustomer's Electric was not incremented by 500",
				testCustomer.getFuel("Electric") == baseFuelValue + 500);
		assertTrue(
				"testCustomer's Gold was not decremented by 500 * Shop.electricPrice",
				testCustomer.getGold() == currentGold - 500 * Shop.electricPrice);
				
		//Execute nuclear buy
		currentGold = testCustomer.getGold();
		testShop.buyFuel("Nuclear", 500, true);		
		//Test
		assertTrue(
				"testCustomer's Nuclear was not incremented by 500",
				testCustomer.getFuel("Nuclear") == baseFuelValue + 500);
		assertTrue(
				"testCustomer's Gold was not decremented by 500 * Shop.nuclearPrice",
				testCustomer.getGold() == currentGold - 500 * Shop.nuclearPrice);		
		
		//Try buys with no gold.
		//Setup
		testCustomer.subGold(testCustomer.getGold());
		assertTrue("Customer gold was not set to 0 in setup", testCustomer.getGold() == 0);
		
		//Coal
		int preAttemptCoal = testCustomer.getFuel("Coal");
		testShop.buyFuel("Coal", 500, true);
		assertTrue(
				"testCustomer's coal was changed after an attempt to purchase coal with no gold.",
				testCustomer.getFuel("Coal") == preAttemptCoal);
		assertTrue(
				"testCustomer's gold was changed after an attempt to purchase coal with no gold.",
				testCustomer.getGold() == 0);
		
		//Oil
		int preAttemptOil = testCustomer.getFuel("Oil");
		testShop.buyFuel("Oil", 500, true);
		assertTrue(
				"testCustomer's oil was changed after an attempt to purchase oil with no gold.",
				testCustomer.getFuel("Oil") == preAttemptOil);
		assertTrue(
				"testCustomer's gold was changed after an attempt to purchase oil with no gold.",
				testCustomer.getGold() == 0);
		
		//Electric
		int preAttemptElectric = testCustomer.getFuel("Electric");
		testShop.buyFuel("Electric", 500, true);
		assertTrue(
				"testCustomer's electric was changed after an attempt to purchase electric with no gold.",
				testCustomer.getFuel("Electric") == preAttemptElectric);
		assertTrue(
				"testCustomer's gold was changed after an attempt to purchase electric with no gold.",
				testCustomer.getGold() == 0);
		
		//Nuclear
		int preAttemptNuclear = testCustomer.getFuel("Nuclear");
		testShop.buyFuel("Nuclear", 500, true);
		assertTrue(
				"testCustomer's nuclear was changed after an attempt to purchase nuclear with no gold.",
				testCustomer.getFuel("Nuclear") == preAttemptNuclear);
		assertTrue(
				"testCustomer's gold was changed after an attempt to purchase nuclear with no gold.",
				testCustomer.getGold() == 0);
	}

	@Test
	public void testSellFuel() {
		//Setup
		int currentGold = testCustomer.getGold();
				
		//Execute coal sell
		testShop.sellFuel("Coal", 500, true);		
		//Test
		assertTrue(
				"testCustomer's Coal was not decremented by 500",
				testCustomer.getFuel("Coal") == baseFuelValue - 500);
		assertTrue(
				"testCustomer's Gold was not incremented by 500 * Shop.coalSellPrice",
				testCustomer.getGold() == currentGold + Math.ceil(500 * Shop.coalSellPrice));
		
		//Execute oil sell
		currentGold = testCustomer.getGold();
		testShop.sellFuel("Oil", 500, true);		
		//Test
		assertTrue(
				"testCustomer's Oil was not decremented by 500",
				testCustomer.getFuel("Oil") == baseFuelValue - 500);
		assertTrue(
				"testCustomer's Gold was not incremented by 500 * Shop.oilSellPrice",
				testCustomer.getGold() == currentGold + Math.ceil(500 * Shop.oilSellPrice));
		
		//Execute electric sell
		currentGold = testCustomer.getGold();
		testShop.sellFuel("Electric", 500, true);		
		//Test
		assertTrue(
				"testCustomer's Electric was not decremented by 500",
				testCustomer.getFuel("Electric") == baseFuelValue - 500);
		assertTrue(
				"testCustomer's Gold was not incremented by 500 * Shop.electricSellPrice",
				testCustomer.getGold() == currentGold + Math.ceil(500 * Shop.electricSellPrice));
				
		//Execute nuclear sell
		currentGold = testCustomer.getGold();
		testShop.sellFuel("Nuclear", 500, true);		
		//Test
		assertTrue(
				"testCustomer's Nuclear was not decremented by 500",
				testCustomer.getFuel("Nuclear") == baseFuelValue - 500);
		assertTrue(
				"testCustomer's Gold was not incremented by 500 * Shop.nuclearSellPrice",
				testCustomer.getGold() == currentGold + 500 * Shop.nuclearSellPrice);		
		
		//Try sells with no gold.
		//Setup
		testCustomer.subFuel("Coal", testCustomer.getFuel("Coal"));
		testCustomer.subFuel("Oil", testCustomer.getFuel("Oil"));
		testCustomer.subFuel("Electric", testCustomer.getFuel("Electric"));
		testCustomer.subFuel("Nuclear", testCustomer.getFuel("Nuclear"));
		assertTrue("Customer coal was not set to 0 in setup", testCustomer.getFuel("Coal") == 0);
		assertTrue("Customer oil was not set to 0 in setup", testCustomer.getFuel("Oil") == 0);
		assertTrue("Customer electric was not set to 0 in setup", testCustomer.getFuel("Electric") == 0);
		assertTrue("Customer nuclear was not set to 0 in setup", testCustomer.getFuel("Nuclear") == 0);
		
		currentGold = testCustomer.getGold();
			
		//Coal		
		testShop.sellFuel("Coal", 500, true);
		assertTrue(
				"testCustomer's coal was changed after an attempt to sell coal with no coal.",
				testCustomer.getFuel("Coal") == 0);
		assertTrue(
				"testCustomer's gold was changed after an attempt to sell coal with no coal.",
				testCustomer.getGold() == currentGold);
		
		//Oil
		testShop.sellFuel("Oil", 500, true);
		assertTrue(
				"testCustomer's oil was changed after an attempt to sell oil with no oil.",
				testCustomer.getFuel("Oil") == 0);
		assertTrue(
				"testCustomer's gold was changed after an attempt to sell oil with no oil.",
				testCustomer.getGold() == currentGold);
		
		//Electric
		testShop.sellFuel("Electric", 500, true);
		assertTrue(
				"testCustomer's electric was changed after an attempt to sell electric with no electric.",
				testCustomer.getFuel("Electric") == 0);
		assertTrue(
				"testCustomer's gold was changed after an attempt to sell electric with no electric.",
				testCustomer.getGold() == currentGold);
		
		//Nuclear
		testShop.sellFuel("Nuclear", 500, true);
		assertTrue(
				"testCustomer's nuclear was changed after an attempt to sell nuclear with no nuclear.",
				testCustomer.getFuel("Nuclear") == 0);
		assertTrue(
				"testCustomer's gold was changed after an attempt to sell nuclear with no nuclear.",
				testCustomer.getGold() == currentGold);
	}

	@Test
	public void testBuyCard() {
		//Setup
		assertTrue("testCustomer cards was not empty after initialisation", testCustomer.getCards().isEmpty());
		int currentGold = testCustomer.getGold();
		//1
		testShop.buyCard(true);
		assertTrue("testCustomer cards did not have 1 card after 1st purchase", testCustomer.getCards().size() == 1);
		assertTrue(
				"testCustomer's gold was not decremented by Shop.cardPrice after buying 1 card", 
				currentGold - Shop.cardPrice == testCustomer.getGold());
		currentGold = testCustomer.getGold();
		
		//2
		testShop.buyCard(true);
		assertTrue("testCustomer cards did not have 2 cards after 2nd purchase", testCustomer.getCards().size() == 2);
		assertTrue(
				"testCustomer's gold was not decremented by Shop.cardPrice after buying 2 cards", 
				currentGold - Shop.cardPrice == testCustomer.getGold());
		currentGold = testCustomer.getGold();
		
		//3
		testShop.buyCard(true);
		assertTrue("testCustomer cards did not have 3 cards after 3rd purchase", testCustomer.getCards().size() == 3);
		assertTrue(
				"testCustomer's gold was not decremented by Shop.cardPrice after buying 3 cards", 
				currentGold - Shop.cardPrice == testCustomer.getGold());
		currentGold = testCustomer.getGold();
				
		//4
		testShop.buyCard(true);
		assertTrue("testCustomer cards did not have 4 cards after 4th purchase", testCustomer.getCards().size() == 4);
		assertTrue(
				"testCustomer's gold was not decremented by Shop.cardPrice after buying 4 cards", 
				currentGold - Shop.cardPrice == testCustomer.getGold());
		currentGold = testCustomer.getGold();
		
		//5
		testShop.buyCard(true);
		assertTrue("testCustomer cards did not have 5 cards after 5th purchase", testCustomer.getCards().size() == 5);
		assertTrue(
				"testCustomer's gold was not decremented by Shop.cardPrice after buying 5 cards", 
				currentGold - Shop.cardPrice == testCustomer.getGold());
		currentGold = testCustomer.getGold();
		
		//6
		testShop.buyCard(true);
		assertTrue("testCustomer cards did not have 6 cards after 6th purchase", testCustomer.getCards().size() == 6);
		assertTrue(
				"testCustomer's gold was not decremented by Shop.cardPrice after buying 6 cards", 
				currentGold - Shop.cardPrice == testCustomer.getGold());
		currentGold = testCustomer.getGold();
				
		//7
		testShop.buyCard(true);
		assertTrue("testCustomer cards did not have 7 cards after 7th purchase", testCustomer.getCards().size() == 7);
		assertTrue(
				"testCustomer's gold was not decremented by Shop.cardPrice after buying 7 cards", 
				currentGold - Shop.cardPrice == testCustomer.getGold());
		currentGold = testCustomer.getGold();	
		
		//No more purchases should be possible.
		testShop.buyCard(true);
		assertTrue("testCustomer cards did not remain at 7 after an attempted 8th purchase", testCustomer.getCards().size() == 7);
		assertTrue(
				"testCustomer's gold was changed after an 8th purchase was attempted",
				currentGold == testCustomer.getGold());
		
		//Customer has no money
		//Setup
		testCustomer.subGold(testCustomer.getGold());
		testCustomer.getCards().clear();
		assertTrue("testCustomer still had cards after an attempt to clear them", testCustomer.getCards().size() == 0);
		assertTrue("testCustomer still had gold after an attempt to remove it", testCustomer.getGold() == 0);
		
		//Execute
		testShop.buyCard(true);
		assertTrue(
				"testCustomer's gold changed after an attempt to buy a card with no gold",
				testCustomer.getGold() == 0);
		assertTrue(
				"testCustomer's card collection size changed after an attempt to buy a card with no gold",
				testCustomer.getCards().size() == 0);
		
	}

}
