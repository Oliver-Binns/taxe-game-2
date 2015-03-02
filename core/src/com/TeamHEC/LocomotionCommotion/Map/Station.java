package com.TeamHEC.LocomotionCommotion.Map;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Resource;
import com.TeamHEC.LocomotionCommotion.Map.Line;
import com.TeamHEC.LocomotionCommotion.MapActors.Game_Map_Station;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 * @author Elliot Bray<eb1033@york.ac.uk>
 */

public class Station extends MapObj{

	private String name;
	private Player owner;
	private int baseValue;
	private int valueMod;
	private Resource resourceType;
	private int baseResourceOut;
	private int resourceOutMod;
	private Line[] line = null;//max number of lines on one station is 3, alter if this changes
	private int rentValue;
	private int rentValueMod;
	public double mindistance = Double.POSITIVE_INFINITY;
	private boolean hasFault;
	
	protected ArrayList<StationListener> listeners = new ArrayList<StationListener>();
	
	private Game_Map_Station gameMapStation;
	
	/**
	 * @param name
	 * @param baseValue The value of the station
	 * @param resourceType The type of resource the station produces
	 * @param baseFuelOut The amount of resource the station produces each turn, without up line bonuses
	 * @param line Array of lines the station belongs to
	 * @param rentValue How much it costs to rent
	 * @param x Coordinate of position x on map
	 * @param y Coordinate of position y on map 
	 */
	
	public Station(String name, int baseValue, Resource resourceType, int baseFuelOut, Line[] line, int rentValue, float x, float y)
	{
		super(x, y, name);
		
		// Creates a map blip for this station
		gameMapStation = new Game_Map_Station(this, x, y);
		actor = gameMapStation;
		
		this.name = name;
		this.owner = null;
		this.baseValue = baseValue;
		this.valueMod = 0;
		this.resourceType = resourceType;
		this.baseResourceOut = baseFuelOut;
		this.resourceOutMod = 0;
		this.line = line;
		this.rentValue = rentValue;
		this.rentValueMod = 0;
	}
	
	/**
	 *@return station instance, used in MapObj and overwritten here
	 */
	public Station getStation()
	{
		return this;
	}
	
	public Game_Map_Station getStationActor()
	{
		return gameMapStation;
	}
	
	/**
	 * @return  the name of the station
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return The string of the fuelType used, can be used in player to deduct resources
	 */
	public String getResourceString()
	{
		return resourceType.getType();
	}
	/** 
	 * @return the value of the station without mods
	 */
	public int getBaseValue()
	{
		return baseValue;
	}
	/**
	 * @return the value added onto baseValue from bonuses
	 */
	public int getValueMod() //Currently has no affect on the game, but is ready for later implementation
	{
		return valueMod;
	}
	public void setValueMod(int value) //Currently has no affect on the game, but is ready for later implementation
	{
		valueMod = value;
	}
	public void addValueMod(int add) //Currently has no affect on the game, but is ready for later implementation
	{
		valueMod += add;
	}
	public void subValueMod(int sub) //Currently has no affect on the game, but is ready for later implementation
	{
		valueMod -= sub;
	}
	/**
	 * @return the total value of the station with mods (Base + Mod)
	 */
	public int getTotalValue()
	{
		return baseValue + valueMod;
	}
	/**
	 * @return returns the resource Type as a type
	 */
	public Resource getResourceType()
	{
		return resourceType;
	}
	/**
	 * @return returns the base amount of resources produced by the station
	 */
	public int getBaseResourceOut()
	{
		return baseResourceOut;
	}
	/**
	 * @return the amount of resources added to base from bonuses
	 */
	public int getResourceOutMod()
	{
		return resourceOutMod;
	}
	/**
	 * @param mod the amount resourceOut will be increased by, Total = Base + Mod
	 */
	public void setResourceOutMod(int mod)
	{
		resourceOutMod = mod;
	}
	
	public void addResourceOutMod(int add) //Not currently necessary but is here if need in later implementation
	{
		resourceOutMod += add;
	}
	public void subResourceOutMod(int sub) //Not currently necessary but is here if need in later implementation
	{
		resourceOutMod -= sub;
	}
	/**
	 * @return the total output of fuel or gold resources the station produces
	 */
	public int getTotalResourceOut()
	{
		return baseResourceOut + resourceOutMod;
	}
	
	/**
	 * @return the base amount of rent a player will be charged for using an opponents station
	 */
	public int getBaseRentValue() //Not currently implemented but is ready if needed later
	{
		return rentValue;
	}
	/**
	 * @return the amount the base rent value is increased by
	 */
	public int getRentValueMod() //Not currently implemented but is ready if needed later
	{
		return rentValueMod;
	}	
	public void setRentValueMod(int value) //Not currently implemented but is ready if needed later
	{
		rentValueMod = value;
	}
	public void addRentValueMod(int add) //Not currently implemented but is ready if needed later
	{
		rentValueMod += add;
	}	
	public void subRentValueMod(int sub) //Not currently implemented but is ready if needed later
	{
		rentValueMod-= sub;
	}
	/**
	 * @return the total rent value of the station with bonuses
	 */
	public int getTotalRent() //Not currently implemented but is ready if needed later
	{
		return rentValue + rentValueMod ;
	}
	
	/**
	 * @return The current owner of the station
	 */
	public Player getOwner()
	{
		return owner;
	}
	/**
	 * Changes the owner of this station and notifies listeners
	 * @param newOwner
	 */
	public void setOwner(Player newOwner)
	{
		owner = newOwner;
		notifyStationPurchased(this, newOwner);
	}
	/**
	 * @return the array of lines the station is on
	 */
	public Line[] getLineType()
	{
		return line;
	}
		
	/**
	 * Registers an object implementing the StationListener by adding it to the listeners array
	 * @param newListener the object to be added
	 */
	public void register(StationListener newListener)
	{
		if(newListener != null)
			listeners.add(newListener);
	}
	
	/**
	 * Removes object implementing StationListener so it no longer recieves updates
	 * @param s the object to be removed
	 */
	public void unregister(StationListener s)
	{
		listeners.remove(listeners.indexOf(s));
	}
	/**
	 * Called when you want to notify listeners that a station has changed ownership, such as 
	 * changing the texture on the map or setting the text of the players gold 
	 * @param station
	 * @param player
	 */
	public void notifyStationPurchased(Station station, Player player) 
	{
		for(StationListener listener: listeners)
		{
			listener.ownerChanged(station, player);
		}
	}
	/**
	 * @return true if station is broken
	 */
	public boolean isFaulty(){
		return hasFault;
	}
	/**
	 * makes the station faulty
	 */
	public void makeFaulty(){
		hasFault = true;
		gameMapStation.updateButton(this, owner);
	}
	/**
	 * fix the station
	 */
	public void fixFaulty(){
		hasFault = false;
		gameMapStation.updateButton(this, owner);
	}
	/**
	 * @return fault rate for the station
	 * fault rate is the chance that a particular station will go faulty in a particular move
	 */
	public double getFaultRate(){
		return 0.01;
	}
	/**
	 * 
	 * @param different station
	 * @return true if station parameter is equal to station instance
	 */
	public boolean equals(Station station){
		if(station.getName() == getName()){
			return true;
		}
		return false;
	}
}
