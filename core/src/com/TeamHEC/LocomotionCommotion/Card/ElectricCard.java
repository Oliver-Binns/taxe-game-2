package com.TeamHEC.LocomotionCommotion.Card;

import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;

/** 
 *   
 * @author Callum Hewitt <ch1194@york.ac.uk> 
 * A Card object which when used adds an amount of electric equal to: 
 * ((Shop.cardPrice/2) + x where 0 < x < Shop.cardPrice) / Shop.electricPrice  
 * to the owner's supply.
 * 
 */
public class ElectricCard extends ResourceCard {

	/**
	 * Initialises the ElectricCard
	 * @param player The player who will own the generated Card.
	 */
	public ElectricCard(Player player) {
		super(player, Game_TextureManager.getInstance().game_card_electriccard, "Electric");
	}

}
