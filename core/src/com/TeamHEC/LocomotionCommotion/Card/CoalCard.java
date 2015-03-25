package com.TeamHEC.LocomotionCommotion.Card;

import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;

/**
 *  
 * @author Callum Hewitt <ch1194@york.ac.uk> 
 * A Card object which when used adds an amount of coal equal to:
 *  ((Shop.cardPrice/2) + x where 0 < x < Shop.cardPrice) / Shop.coalPrice
 * to the owner's supply.
 * 
 */
public class CoalCard extends ResourceCard{

	/**
	 * Initialises the CoalCard
	 * @param player The player who will own the generated Card.
	 */
	public CoalCard(Player player)
	{
		super(player, Game_TextureManager.getInstance().game_card_coalcard, "Coal" );
	}
}