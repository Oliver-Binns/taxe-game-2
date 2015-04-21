package com.TeamHEC.LocomotionCommotion.Map;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.MapActors.Game_Map_MapObj;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 */

public class MapObj {
	
	public Game_Map_MapObj actor;
	public ArrayList<Connection> connections = new ArrayList<Connection>();
	public float x, y;
	private boolean locked;
	protected String name;
	
	/**
	 * Every Station and Junction on the map
	 * @param x xPosition on map
	 * @param y yPosition on map
	 */
	public MapObj(float x, float y, String name)
	{
		this(x, y, name, false);
	}
	
	public MapObj(float x, float y, String name, boolean locked) {
		this.x = x;
		this.y = y;
		this.locked = locked;
		this.name = name;
	}
	
	/**
	 * 
	 * @return The name of the MapObj
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return JSON String of this object
	 */
	public String toJSON(){
		return "{\"name\":\"" + name + "\", \"type\":\"mapObj\"}";
	}
	
	/**
	 * Set name of the MapObj
	 * @param newName the new name of the MapObj
	 */
	public boolean setName(String newName) {
		if(WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).changeNameMapping(this, newName)) {
			name = newName;
			return true;
		}
		return false;
	}
	
	/**
	 * @return returns null if not a station, or is overwritten by Station subclass
	 */
	public Station getStation()
	{
		return null;
	}
	/**
	 * 
	 * @return The Actor (UI element) associated with the MapObj
	 */
	public Game_Map_MapObj getActor()
	{
		return actor;
	}
	/**
	 * 
	 * @return Whether or not the connection is currently inaccessible
	 */
	public boolean isLocked() {
		return this.locked;
	}
	
	//Locks connection if the connection is unlocked, unlocks it otherwise
	public void lock(Boolean lock) {
		this.locked = lock;
	}
}