package com.TeamHEC.LocomotionCommotion.Train;

import org.json.simple.JSONObject;

import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 */
public class ElectricTrain extends Train{
	
	private static final int BASE_SPEED = 100;
	private static final int VALUE = 500;

	public ElectricTrain(int speedMod, boolean inStation, Route route, Player player)
	{
		// Name, Fuel, baseSpeed, speedMod, baseCarriageLimit, carriageLimitMod, value, inStation
		super("Electrix", new Electric(200), BASE_SPEED, speedMod, VALUE, inStation,
				route, player);
		fuelPerTurn = 20;
	}
	
	/**
	 * Reinstantiates train from JSON Object
	 * @param Train JSON Data
	 * @param route
	 * @param player
	 */
	public ElectricTrain(JSONObject train, Route route, Player player){		
		super((String) train.get("name"), new Electric(200), BASE_SPEED, ((Long)train.get("speedMod")).intValue(), VALUE, true, route, player);
		fuelPerTurn = ((Long)train.get("fuelPerTurn")).intValue();
	}
}
