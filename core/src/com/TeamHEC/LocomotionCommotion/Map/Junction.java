package com.TeamHEC.LocomotionCommotion.Map;

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
		super(xPos, yPos, name);
		actor = new Game_Map_Junction(this, x, y);
	}
}
