package com.TeamHEC.LocomotionCommotion.Goal;

import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Train.RouteListener;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;

/**
 * 
 * @author Sam Anderson <sa902@york.ac.uk>
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 *
 */
public class Goal implements RouteListener{ 
	//Variables
	protected Station sStation;
	protected Station fStation;
	protected int timeConstraint;
	private String cargo;

	protected boolean special;
	private int reward;
	private String startDate;

	// Variables used to track Goal completion:
	private Train train;	
	private boolean startStationPassed;
	private boolean isAbsolute;
	private boolean finalStationPassed;
	private int currentTime;
	
	public static GoalActor goalActor;
	
	/**
	 * Initialises the goal.
	 * @param startStation The Station the goal starts from
	 * @param finalStation The Station the goal ends at
	 * @param stationVia The Station the goal wants you to travel via
	 * @param cargo The type of cargo the train is carrying.
	 * @param reward The reward (currently Gold) you get for completing the Goal
	 */
	public Goal(Station startStation, Station finalStation, int routhLength, String cargo, int reward)
	{
		this.sStation = startStation;
		this.fStation = finalStation;
		this.timeConstraint = routhLength;
		this.setSpecial(false); 
		this.reward = reward;  
		this.cargo = cargo;
		this.currentTime = 0;
		
        startDate = "1"; //initialized to 1, not yet implemented. 
		
		// Initiliase goal completion variables to false
		startStationPassed = false;
		if(routhLength == 0)
		isAbsolute = true; 
		else
		isAbsolute = false;
		finalStationPassed = false;
	}
	
	public boolean isQuantifiable() {
		return !isAbsolute;
	}

	public boolean isAbsolute() {
		return isAbsolute;
	}

	public boolean isSpecial()
	{
		return special;
	}

	public String getSStation()
	{
		return this.sStation.getName();
	}
	
	public String getFStation()
	{
		return this.fStation.getName();
	}

	public int getReward()
	{
		return reward;
	}
	
	public String getStartDate()
	{
		return startDate;
	}
	
	public void setActor(GoalActor actor)
	{
		goalActor = actor;
	}
	
	/**
	 * Returns the number of stations a player ha to pass through. Returns "Any" if StationVia is null.
	 * @return The number of stations a player ha to pass through. Returns "Any" if StationVia is null.
	 */
	public String getTimeConstraint()
	{
		if(isAbsolute())
			return "Any";
		else
			return "via "+(timeConstraint-1)+" stations";
	}
	
	public String getCargo()
	{
		return cargo;
	}

	/**
	 * Assigns a goal to a train and registers listeners
	 * @param train The train to assign to
	 * 
	 */
	public void assignTrain(Train train)
	{
		this.train = train;
		train.route.register(this);
		
		if(train.route.getStation() == sStation)
			startStationPassed = true;
	}
	
	public Train getTrain()
	{
		return train;
	}
	
	/**
	 * Called when the goal is successfully complete:
	 */	
	public void goalComplete()
	{
		WarningMessage.fireWarningWindow("GOAL COMPLETE!", "You've successfully complete the route: " + getSStation()
				+ " to " + getFStation() + "\n you've won " + getReward());
		
		train.getOwner().addGold(getReward());
		train.route.unregister(this);
		
		train.getOwner().getGoals().remove(this);
		
		startStationPassed = false;
		isAbsolute = false;
		finalStationPassed = false;
		
	}
	
	/**
	 * Called when the goal is failed:
	 */	
	public void goalFailed()
	{
		WarningMessage.fireWarningWindow("GOAL FAILED!", "Sorry! You've failed to cpmplete the route: " + getSStation()
				+ " to " + getFStation() + "\n via "+(timeConstraint-1) + " stations! ");
		
		train.route.unregister(this);
		
		train.getOwner().getGoals().remove(this);
		
		startStationPassed = false;
		isAbsolute = false;
		finalStationPassed = false;
		currentTime = 0;
	}
	
	/**
	 * Listener trigger when a train passes a station
	 */
	@Override
	public void stationPassed(Station station, Train train)
	{
		
		System.out.println("\nTimeConstraint: " + timeConstraint + "  ");
		System.out.println("CurrentTime: " + currentTime + "\n\n");
		
		
		if(train == this.train)
		{
			
			System.out.println(train.getName() +" passed " + station.getName());
			
			if(station.equals(sStation))
			{
				startStationPassed = true;
				System.out.println("start passed");
			}
			if(startStationPassed && station.equals(fStation))
			{
				finalStationPassed = true;
				System.out.println("final passed");
			}

						
			if(startStationPassed && finalStationPassed && isAbsolute())
				goalComplete();
			
			if(currentTime >= timeConstraint && isQuantifiable()){
				goalFailed();
			}
			
			if(startStationPassed && finalStationPassed && isQuantifiable() && timeConstraint > currentTime){
				goalComplete();
			}
			
			if(startStationPassed){
				currentTime++;
			}
			
		}
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}
}

