package com.TeamHEC.LocomotionCommotion.Player;

import java.util.ArrayList;


import java.util.HashMap;

import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Card.CardFactory;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Player.Shop;
import com.TeamHEC.LocomotionCommotion.Resource.Fuel;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Carriage;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Train.Train;

/**
 * 
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 *
 */

public class Player {

	public String playerName;
	public int points;
	public Gold gold;
	public Coal coal;
	public Oil oil;
	public Electric electric;
	public Nuclear nuclear;
	public ArrayList<Card> cards;
	private Shop shop;
	public ArrayList<Goal> goals;
	public ArrayList<Train> trains;
	public Carriage carriages;
	public ArrayList<Station> stations;
	
	private HashMap<String, Fuel> playerFuel;
	
	public Player(String playerName, int points, Gold gold, Coal coal, Electric electric, Nuclear nuclear, Oil oil, 
				Carriage carriage, ArrayList<Card> cards, ArrayList<Goal> goals, ArrayList<Train> trains,
				ArrayList<Station> stations)
	{
		this.playerName = playerName;
		this.points = points;
		this.gold = gold;
		this.coal = coal;
		this.oil = oil;
		this.electric = electric;
		this.nuclear = nuclear;
		this.cards = cards;
		this.shop = new Shop(this);
		this.goals = goals;
		this.trains = trains;
		this.carriages = carriage;
		this.stations = stations;
		
		playerFuel = new HashMap<String, Fuel>();
		
		playerFuel.put("Coal", this.coal);
		playerFuel.put("Electric", this.electric);
		playerFuel.put("Nuclear", this.nuclear);
		playerFuel.put("Oil", this.oil);
	}
	
	public int getFuel(String fuelType)
	{
		return playerFuel.get(fuelType).getValue();
	}
	
	public void setFuel(String fuelType, int value)
	{
		playerFuel.get(fuelType).setValue(value);
	}
	
	public void addFuel(String fuelType, int quantity)
	{
		playerFuel.get(fuelType).addValue(quantity);
	}
	
	public void subFuel(String fuelType, int quantity)
	{
		playerFuel.get(fuelType).subValue(quantity);
	}

	public int getGold()
	{
		return gold.getValue();
	}
	
	public void setGold(int value)
	{
		gold.setValue(value);
	}
	
	public void addGold(int value)
	{
		gold.setValue(gold.getValue() + value);
	}
	
	public void subGold(int value)
	{
		gold.setValue(gold.getValue() - value);
	}
	
	// Specific cards should be purchased in the shop
	// This can be used after completing Goals
	// (Could be implemented in Goal class)
	public void purchaseRandomCard()
	{
		if(getNumCards() < 3)
		{
			Card mCard = CardFactory.getInstance().createRandomCard();
			mCard.setOwner(this);
			cards.add(mCard);
		}
	}
	
	// Called when a card is purchased in the shop
	public void purchaseCard(Card card)
	{
		if(getNumCards() < 3)
		{
			card.setOwner(this); // Card has association with player
			cards.add(card); // Adds card to the players list of owned cards
		}
	}
	
	public int getNumCards()
	{
		return cards.size();
	}
	
	public void accessShop()
	{
		shop.openShop();
	}
	
	public void accessGoals(){}
}