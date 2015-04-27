package com.TeamHEC.LocomotionCommotion.Map;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.MapActors.Game_Map_Junction;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 */
public class Junction extends MapObj {

	/**
	 * Creates a Junction using it's coordinates as parameters
	 */
	public Junction(float xPos, float yPos, String name)
	{
		this(xPos, yPos, name, false);
	}
	
	public Junction(float xPos, float yPos, String name, boolean locked) {
		super(xPos, yPos, name, locked);
		
		if(!GameData.TEST_CASE) {
			actor = new Game_Map_Junction(this, x, y); // Creates a map blip for this junction
		}
	}
	
	/**
	 * @return JSON String of this object
	 */
	@Override
	public String toJSON(){
		return "{\"name\":\"" + name + "\",\"type\":\"junction\"}";
	}
}
