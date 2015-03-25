package com.TeamHEC.LocomotionCommotion.Card;

import java.util.Random;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;

/**
 * @author Sam Watkins <sw1308@york.ac.uk>
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 * Teleports a train (currently to London should be changed and worked in with UI so it teleports to a specified location).
 */

public class TeleportCard extends Card{
	
	/**
	 * Initialises the card
	 * @param player The owner of the card.
	 */
	public TeleportCard(Player player)
	{
		super(player, Game_TextureManager.getInstance().game_card_teleportcard, "Teleport");
	}
	
	@Override
	/**
	 * Takes the owner's first train in their trains list and moves it to London.
	 * Should be changed in Assessment 3 to teleport either random trains to random locations or a specified train to a specified location.
	 */
	public void implementCard()
	{
		Random rnd = new Random();
		// Need a way to choose the train, currently selects one at random:
		Train train = getOwner().getTrains().get(rnd.nextInt(getOwner().getTrains().size()));
		
		// Need a way to choose station, currently selects one at random:
		MapObj chosenLocation = WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).stationList()[rnd.nextInt(WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).stationList().length)];
		
		train.route.getRoute().clear();
		train.route.setRouteIndex(0);
		train.route.setConnectionTravelled(0);
		
		train.route.setCurrentMapObj(chosenLocation);
	}
}
