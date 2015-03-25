package com.TeamHEC.LocomotionCommotion.Card;

import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;

/**
 *   
 * @author Callum Hewitt <ch1194@york.ac.uk> 
 * A Card object which when used adds an amount of oil equal to: 
 * ((Shop.cardPrice/2) + x where 0 < x < Shop.cardPrice) / Shop.oilPrice  
 * to the owner's supply.
 * 
 */
public class OilCard extends ResourceCard{

	public OilCard(Player player)
	{
		super(player, Game_TextureManager.getInstance().game_card_oilcard, "Oil");
	}

}