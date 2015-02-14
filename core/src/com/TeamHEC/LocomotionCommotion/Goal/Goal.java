package com.TeamHEC.LocomotionCommotion.Goal;

import com.TeamHEC.LocomotionCommotion.Goal.Graph.Dijkstra;
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
	protected Station stationVia;
	private String cargo;
	protected boolean special;
	private int reward;
	private String startDate;

	// Variables used to track Goal completion:
	private Train train;	
	private boolean startStationPassed;
	private boolean stationViaPassed;
	private boolean finalStationPassed;

    private int currentGoalDuration;
	
	public static GoalActor goalActor;
	
	/**
	 * Initialises the goal.
	 * @param startStation The Station the goal starts from
	 * @param finalStation The Station the goal ends at
	 * @param stationVia The Station the goal wants you to travel via
	 * @param cargo The type of cargo the train is carrying.
	 * @param reward The reward (currently Gold) you get for completing the Goal
	 */
	public Goal(Station startStation, Station finalStation, Station stationVia, String cargo, int reward)
	{
		this.sStation = startStation;
		this.fStation = finalStation;
		this.stationVia = stationVia;
		this.setSpecial(false); 
		this.reward = reward;  
		this.cargo = cargo;
		
        startDate = "1"; //initialized to 1, not yet implemented.
        currentGoalDuration = 0;
		
		// Initiliase goal completion variables to false
		startStationPassed = false;
		if(stationVia == null)
		stationViaPassed = true; //does not exist hence always passed 
		else
		stationViaPassed = false;
		finalStationPassed = false;
	}

	public boolean isSpecial()
	{
		return special;
	}

	public String getSStation()
	{
		return this.sStation.getName();
	}

    public Station getSStationObject() { return this.sStation; }
	
	public String getFStation()
	{
		return this.fStation.getName();
	}

    public Station getFStationObject() { return this.fStation; }

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
	 * Returns the name of the viaStation. Returns "Any" if StationVia is null.
	 * @return The name of the viaStation. Returns "Any" if StationVia is null.
	 */
	public String getVia()
	{
		if(stationVia == null)
			return "Any";
		else
			return stationVia.getName();
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
     *  @return False if goal is not ready to complete
	 */	
	public Boolean goalComplete()
	{
        if ( ! (startStationPassed && finalStationPassed && stationViaPassed)){
            return false;
        }

		WarningMessage.fireWarningWindow("GOAL COMPLETE!", "You've successfully complete the route: " + getSStation()
				+ " to " + getFStation() + "\n you've won " + getReward());
		
		train.getOwner().addGold(getReward());
        train.getOwner().incrementPoints(calculatePoints()); //Added by Team EEP

        train.route.unregister(this);
		train.getOwner().getGoals().remove(this);
		
		//if(goalActor != null)
		//{
		//	goalActor.setPlanRouteButtonVisible(false);
		//}
		
		startStationPassed = false;
		stationViaPassed = false;
		finalStationPassed = false;

        return true;
	}
	
	/**
	 * Listener trigger when a train passes a station
	 */
	@Override
	public void stationPassed(Station station, Train train)
	{
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
			if(stationVia == null || (startStationPassed && station.equals(stationVia)))
			{
				stationViaPassed = true;
				System.out.println("via passed");
			}
						
			if(startStationPassed && finalStationPassed && stationViaPassed)
				goalComplete();
		}
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}


    //New methods by Team EEP:

    public int getCurrentGoalDuration(){ return currentGoalDuration; }

    /**
     * Should be called at the end of each turn
     */
    public void incrementCurrentGoalDuration(){ currentGoalDuration++; }

    /**
     * Used to calculate how many points to add to a player's score upon completion of goal
     * EstimateOptimalDuration = how many turns it would take train to travel goal's optimal route at base speed
     * CurrentGoalDuration = how many turns it actually took the train
     * reward = length of optimal journey
     * @return 0 if goal is not yet completed, otherwise number of points
     */
    public int calculatePoints(){
        if (! finalStationPassed || currentGoalDuration == 0){
            return 0;
        }

        return (estimateOptimalDuration() * reward / currentGoalDuration );
    }


    /**
     * Used to estimate how many turns it would take the train (assigned to the goal) to travel the goal's optimal route at its base speed
     * @return 0 if no train has been assigned, otherwise it will return "optimal" number of turns for completion
     */
    public int estimateOptimalDuration() {

        if (train == null){
            return 0;
        }

        Dijkstra d = new Dijkstra(); //implements dijkstra
        d.computePaths(d.lookUpNode(sStation)); //uses the loopup function to get instance of a
        //station and compute paths
        int minDistance = (int) d.lookUpNode(fStation).minDistance; //

        return (minDistance / train.getBaseSpeed());
    }


}

