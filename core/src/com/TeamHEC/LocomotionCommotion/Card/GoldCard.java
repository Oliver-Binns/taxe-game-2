package com.TeamHEC.LocomotionCommotion.Card;

import java.util.Random;

import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Player.Shop;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;

/** 
 *   
 * @author Callum Hewitt <ch1194@york.ac.uk> 
 * A Card object which when used adds an amount of gold equal to: 
 * ((Shop.cardPrice/2) + x where 0 < x < Shop.cardPrice) 
 * to the owner's supply.
 * 
 */
public class GoldCard extends Card{

	public GoldCard(Player player)
	{
		super(player, Game_TextureManager.getInstance().game_card_goldcard, "Gold");
	}
	
	@Override
	public void implementCard()
	{
		Random random = new Random();
		getOwner().addGold(Shop.cardPrice/2 + random.nextInt(Shop.cardPrice));
	}
}