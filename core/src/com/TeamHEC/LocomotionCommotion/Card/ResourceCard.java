package com.TeamHEC.LocomotionCommotion.Card;

import java.util.Random;

import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Player.Shop;
import com.badlogic.gdx.graphics.Texture;

/**
 * 
 * @author Callum Hewitt <ch1194@york.ac.uk>
 * An abstract class used by all resource cards.
 */
public abstract class ResourceCard extends Card {

	private String fuelType;

	/**
	 * Initialises the card.
	 * @param owner The player who owns the card.
	 * @param cardTexture The image the card will have on the UI.
	 * @param fuelType The type of fuel the card will generate.
	 */
	public ResourceCard(Player owner, Texture cardTexture, String fuelType)
	{
			super(owner, cardTexture, fuelType);			
			this.fuelType = fuelType;
	}
	

	@Override
	/**
	 * Adds an amount of fuel equal to:
	 * ((Shop.cardPrice/2) + random.nextInt(Shop.cardPrice)) / getFuelPrice();
	 * to the owner's supply.
	 */
	public void implementCard()
	{
		Random random = new Random();
		int fuelToAdd = ((Shop.cardPrice/2) + random.nextInt(Shop.cardPrice)) / getFuelPrice();
		getOwner().addFuel(fuelType, fuelToAdd);
	}
	
	/**
	 * Determines the price of the fuel based on fuelType
	 * @return The current Shop price of the fuelType
	 */
	private int getFuelPrice()
	{
		if(fuelType == "Coal")
			return Shop.coalPrice;	
		if(fuelType == "Oil")
			return Shop.oilPrice;		
		if(fuelType == "Electric")
			return Shop.electricPrice;			
		if(fuelType == "Nuclear")
			return Shop.nuclearPrice;	
		return 0;
	}

}