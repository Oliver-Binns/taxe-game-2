	package com.TeamHEC.LocomotionCommotion.Map;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Player.PlayerListener;
import com.TeamHEC.LocomotionCommotion.Resource.Fuel;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.Map.Line;
/**
 * 
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 *
 */

public class Station extends MapObj implements PlayerListener{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private Player owner;
	private int baseValue;
	private int valueMod;
	private Fuel fuelType;
	private int baseFuelOut;
	private int fuelOutMod;
	private Line[] line = null;//max number of lines on one station is 3, alter if this changes
	private int rentValue;
	private int rentValueMod;
	protected ArrayList<StationListener> listeners = new ArrayList<StationListener>();
	protected Player player1;//the players station will listen too
	protected Player player2;//will need name changes later, not sure this listener stuff is still gonna be used
	
	public Station(String name, int baseValue, Fuel fuelType, int baseFuelOut, Line[] line, int rentValue, float x, float y)
	{
		super(x, y);
		
		this.trains = new Train[5];
		this.name = name;
		this.owner = null;
		this.baseValue = baseValue;
		this.valueMod = 0;
		this.fuelType = fuelType;
		this.baseFuelOut = baseFuelOut;
		this.fuelOutMod = 0;
		this.line = line;
		this.rentValue = rentValue;
		this.rentValueMod = 0;
		//player1.addListener(this);
		//player2.addListener(this);
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String getFuelString()
	{
		return fuelType.getType();
	}
	
	public int getBaseValue()
	{
		return baseValue;
	}
	public int getValueMod()
	{
		return valueMod;
	}	
	public void addValueMod(int add)
	{
		valueMod += add;
	}
	public void subValueMod(int sub)
	{
		valueMod -= sub;
	}	
	public int getTotalValue()
	{
		return baseValue + valueMod;
	}
	
	public Fuel getFuelType()
	{
		return fuelType;
	}
	public int getBaseFuelOut()
	{
		return baseFuelOut;
	}
	public int getFuelOutMod()
	{
		return fuelOutMod;
	}
	public void setFuelOutMod(int mod)
	{
		fuelOutMod = mod;
	}
	public void addFuelOutMod(int add)
	{
		fuelOutMod += add;
	}
	public void subFuelOutMod(int sub)
	{
		fuelOutMod -= sub;
	}	
	public int getTotalFuelOut()
	{
		return baseFuelOut + fuelOutMod;
	}
	
	public int getRentValue()
	{
		return rentValue;
	}
	public int getRentValueMod()
	{
		return rentValueMod;
	}	
	public void addRentValueMod(int add)
	{
		rentValueMod += add;
	}	
	public void subRentValueMod(int sub)
	{
		rentValueMod-= sub;
	}
	
	@Override
	public int getTotalRent()
	{
		return rentValue + rentValueMod;
	}
	
	public Player getOwner()
	{
		return owner;
	}
	public void setOwner(Player newOwner)
	{
		owner = newOwner;
	}
	
	public Line[] getLineType()
	{
			return line;
	}
	
	/*public void purchaseStation(Player player)
	{		
		//needs conditions to check station is purchasable
		//either added here or where purchase station will be called
		player.subGold(this.getTotalValue());
		owner = player;
		for (StationListener listener : listeners) 
		{
			listener.ownerChanged(player.getPlayerName());
		}
	}*/
	
	public void addListener(StationListener listener)
	{
		listeners.add(listener);
	}
	@Override
	public void stationPurchased(Station station, Player player) 
	{
		if (station==this)
		{
			this.setOwner(player);
		}
	}
}

