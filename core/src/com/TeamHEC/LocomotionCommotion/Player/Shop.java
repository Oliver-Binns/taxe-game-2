package com.TeamHEC.LocomotionCommotion.Player;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.LocomotionCommotion;
import com.TeamHEC.LocomotionCommotion.Card.CardFactory;
import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Train.CoalTrain;
import com.TeamHEC.LocomotionCommotion.Train.ElectricTrain;
import com.TeamHEC.LocomotionCommotion.Train.NuclearTrain;
import com.TeamHEC.LocomotionCommotion.Train.OilTrain;
import com.TeamHEC.LocomotionCommotion.Train.Route;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.TeamJKG.LocomotionCommotion.Game.NewGame;

/**
 * 
 * @author Callum Hewitt <ch1194@york.ac.uk>
 * @author Sam Watkins <sw1308@york.ac.uk>
 * The shop object used by a player to buy and sell fuel and cards.
 *
 */

public class Shop {

	private Player customer;
	private CardFactory cardFactory;
	
	//Sell price is 70% of the buy price
	public final static int coalPrice = 1;
	public final static float coalSellPrice = 0.7f;
	public final static int oilPrice = 2;
	public final static float oilSellPrice = 1.4f;
	public final static int electricPrice = 3;
	public final static float electricSellPrice = 2.1f;
	public final static int nuclearPrice = 4;
	public final static float nuclearSellPrice = 2.8f;
	public final static int cardPrice = 1000;
	public final static float cardSellPrice = 700f;
	
	/**
	 * The initialiser for shop. Creates cardFactory and assigns customer.
	 * @param customer The player who will be buying and selling things from this shop object.
	 */
	public Shop(Player customer)
	{
		this.customer = customer;	
		cardFactory = new CardFactory(customer);
	}
	
	/**
	 * Purchases fuel from the shop using the customer's money. If the player doesn't have enough gold it will display a warning window (unless testing)
	 * @param fuelType The type of fuel as string the player will obtain: "Coal", "Oil", "Electric", "Nuclear"
	 * @param quantity The amount of fuel the player will purchase
	 * @param testCase Determines if the run is a testCase or not.
	 */
	public void buyFuel(String fuelType, int quantity, boolean testCase)
	{		
		if(fuelType == "Coal" && customer.getGold() >= (quantity*coalPrice)) {
			customer.addFuel(fuelType, quantity);
			customer.subGold(quantity * coalPrice);
		}
		else if(fuelType == "Oil" && customer.getGold() >= (quantity*oilPrice)) {
			customer.addFuel(fuelType, quantity);
			customer.subGold(quantity * oilPrice);
		}
		else if(fuelType == "Electric" && customer.getGold() >= (quantity*electricPrice)) {
			customer.addFuel(fuelType, quantity);
			customer.subGold(quantity * electricPrice);
		}
		else if(fuelType == "Nuclear" && customer.getGold() >= (quantity*nuclearPrice)) {
			customer.addFuel(fuelType, quantity);
			customer.subGold(quantity * nuclearPrice);
		}	
		else
		{
			if(!testCase)
				WarningMessage.fireWarningWindow("SORRY", "You don't have enough gold!");
		}
	}
		
	public void sellFuel(String fuelType, int quantity, boolean testCase)
	{
		
		if(fuelType == "Coal" && customer.getFuel(fuelType) >= quantity) {
			customer.subFuel(fuelType, quantity);
			customer.addGold((int)(Math.ceil(quantity * coalSellPrice)));
		}
		
		
		else if(fuelType == "Oil" && customer.getFuel(fuelType) >= quantity) {
			customer.subFuel(fuelType, quantity);
			customer.addGold((int)(Math.ceil(quantity * oilSellPrice)));
		}
		
		
		else if(fuelType == "Electric" && customer.getFuel(fuelType) >= quantity) {
			customer.subFuel(fuelType, quantity);
			customer.addGold((int)(Math.ceil(quantity * electricSellPrice))); //DO NOT REMOVE MATH.CEIL IT ROUNDS WIERDLY
		}
		
		
		else if(fuelType == "Nuclear" && customer.getFuel(fuelType) >= quantity) {
			customer.subFuel(fuelType, quantity);
			customer.addGold((int)(Math.ceil(quantity * nuclearSellPrice)));
		}
		else
		{
			if(!testCase)
				WarningMessage.fireWarningWindow("SORRY", "You don't have enough "+fuelType+"!");
		}
	}

	/**
	 * Purchases a card for the player
	 * @param testCase A boolean deciding if this is a testCase run or not.
	 */
	public void buyCard(boolean testCase)
	{
		if (customer.getCards().size() < 7 && customer.getGold() >= 1000)
		{			
			// Sets the owner to the card and subtract gold from player
			customer.addCard(cardFactory.createAnyCard());
			customer.subGold(1000);		
		}
		else
		{
			if(!testCase)
			{
				if(customer.getGold() < 1000)
				{
					WarningMessage.fireWarningWindow("SORRY", "You don't have enough gold!");
				}
				else 
				{
					WarningMessage.fireWarningWindow("SORRY", "You have too many cards already");
				}
			}
		}
	}
	
	public void repairStation(Station station, boolean testCase)
	{
		if(customer.getGold() >= 300)
		{
			station.fixFaulty();
			customer.subGold(300);
		}
		else
		{
			if(!testCase)
			{
				WarningMessage.fireWarningWindow("SORRY", "You don't have enough gold!");
			}
		}
	}

	public void unlockStation(MapObj mapObj, boolean testCase) {
		if(customer.getGold() >= 1000) {
			mapObj.lock(false);
			if(!LocomotionCommotion.isReplay && !GameData.EDITING){
				NewGame game = (NewGame) GameScreen.game;
				game.unlockMapObj(mapObj);
			}
			customer.subGold(1000);
		} else {
			if(!testCase) {
				WarningMessage.fireWarningWindow("SORRY", "You don't have enough gold!");
			}
		}
	}
	
	public void buyNewTrain(Station station){
		if(LocomotionCommotion.isReplay){
			//buy train...
			String fuelType = station.getResourceString();
			Train train = null;
			if (fuelType.equals("Coal"))
				train = new CoalTrain(0, true, new Route(station), customer);
			else if (fuelType.equals("Nuclear"))
				train = new NuclearTrain(0, true, new Route(station),customer);
			else if (fuelType.equals("Electric"))
				train = new ElectricTrain(0, true, new Route(station),customer);
			else if (fuelType.equals("Oil"))
				train = new OilTrain(0, true, new Route(station),customer);
			else
				train = new OilTrain(0, true, new Route(station),customer);
			customer.getTrains().add(train);
		}
		else if(customer.getGold() >= 1500){
			//buy train...
			String fuelType = station.getResourceString();
			Train train = null;
			if (fuelType.equals("Coal"))
				train = new CoalTrain(0, true, new Route(station), customer);
			else if (fuelType.equals("Nuclear"))
				train = new NuclearTrain(0, true, new Route(station),customer);
			else if (fuelType.equals("Electric"))
				train = new ElectricTrain(0, true, new Route(station),customer);
			else if (fuelType.equals("Oil"))
				train = new OilTrain(0, true, new Route(station),customer);
			else
				train = new OilTrain(0, true, new Route(station),customer);
			customer.getTrains().add(train);
			customer.subGold(1500);
		}
		else{
			WarningMessage.fireWarningWindow("SORRY", "You don't have enough gold!");
		}
	}

	public void lockStation(MapObj mapObj, boolean testCase) {
		if(customer.getGold() >= 1000) {
			mapObj.lock(true);
			if(!LocomotionCommotion.isReplay && !GameData.EDITING){
				NewGame game = (NewGame) GameScreen.game;
				game.lockMapObj(mapObj);
			}
			customer.subGold(1000);
		} else {
			if(!testCase) {
				WarningMessage.fireWarningWindow("SORRY", "You don't have enough gold!");
			}
		}
	}
}