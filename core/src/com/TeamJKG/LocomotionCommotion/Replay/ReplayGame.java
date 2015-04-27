package com.TeamJKG.LocomotionCommotion.Replay;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.TeamHEC.LocomotionCommotion.Game.CoreGame;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.Line;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Obstacle.Obstacle;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Train.CoalTrain;
import com.TeamHEC.LocomotionCommotion.Train.ElectricTrain;
import com.TeamHEC.LocomotionCommotion.Train.NuclearTrain;
import com.TeamHEC.LocomotionCommotion.Train.OilTrain;
import com.TeamHEC.LocomotionCommotion.Train.Route;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;

public class ReplayGame extends CoreGame {

	private JSONObject gameData;
	private JSONObject turnData;
	private Player[] playersArray;
	private boolean paused;
	/**
	 * Creates an instance of ReplayGame, this game mode loads a saved mode from JSON and allows the user to watch it back at their own pace.
	 * @param Player1Name
	 * @param Player2Name
	 * @param gameData
	 */
	public ReplayGame(String Player1Name, String Player2Name, JSONObject gameData) {
		super(Player1Name, Player2Name, gameData.size() - 1);
		this.gameData = gameData;
		paused = true;
		turnData = (JSONObject) gameData.get("0");
		JSONArray players = (JSONArray) turnData.get("players");
		JSONObject player1json = (JSONObject) players.get(0);
		JSONObject player2json = (JSONObject) players.get(1);
		JSONArray player1Stations = (JSONArray) player1json.get("Stations");
		JSONArray player2Stations = (JSONArray) player2json.get("Stations");
		
		JSONObject Station1JSON = (JSONObject)player1Stations.get(0);
		JSONObject Station2JSON = (JSONObject)player2Stations.get(0);
		
		Station Player1StationStart = gameMap.getStationWithName((String)Station1JSON.get("name"));
		Station Player2StationStart = gameMap.getStationWithName((String)Station2JSON.get("name"));
		
		setupPlayers(Player1Name, Player2Name, Player1StationStart, Player2StationStart);
		
		playersArray = new Player[]{player1, player2};
		// Start Game
		StartTurn();
		
		//TODO Find a way of displaying a warning message here?!
		WarningMessage.fireWarningWindow("Welcome to Replay!", "Test");
	}
	
	/**
	 * sets up the player by creating the first train
	 */
	@Override
	protected void createFirstTrain(Player player, Station startStation) {
		String fuelType = startStation.getResourceString();
		Train train = null;
		
		JSONArray players = (JSONArray)turnData.get("players");
		JSONObject playerJSON;
		
		if(player.isPlayer1){
			playerJSON = (JSONObject)players.get(0);
		}
		else{
			playerJSON = (JSONObject)players.get(1);
		}
		
		JSONArray trainArray = (JSONArray)playerJSON.get("Trains");
				
		if (fuelType.equals("Coal"))
			train = new CoalTrain((JSONObject)trainArray.get(0), new Route(startStation), player);
		else if (fuelType.equals("Nuclear"))
			train = new NuclearTrain((JSONObject)trainArray.get(0), new Route(startStation), player);
		else if (fuelType.equals("Electric"))
			train = new ElectricTrain((JSONObject)trainArray.get(0), new Route(startStation), player);
		else if (fuelType.equals("Oil"))
			train = new OilTrain((JSONObject)trainArray.get(0), new Route(startStation), player);
		else
			train = new OilTrain((JSONObject)trainArray.get(0), new Route(startStation), player);

		player.getTrains().add(train);
	}
	
	/**
	 * Starts a players turn. It will check for the end game condition.
	 */
	@Override
	public void StartTurn() {
		//New Turn Data
		turnData = (JSONObject) gameData.get(String.valueOf(this.turnCount));
		//TODO add any cards that have been acquired this turn.
      	
		// Add new trains
		addNewTrains();
		
      	// Proceed with the turn
        playerTurn.lineBonuses();
        playerTurn.stationRewards();
		 
		//break any stations that became faulty on this turn.
		addStationFaults();
		//lock/unlock any stations that 
		addStationLocks();
		
		//Updates the player scores at the top of the screen
		updatePlayerScores();
		updatePlayerCards();
		updateGoals();
		
		//updates the player resources at the bottom of the screen
      	updateResources();

        //Increment all player's goals by one turn in duration
        for ( Goal goal : playerTurn.getGoals()){
            goal.incrementCurrentGoalDuration();
        }
        
        //Add any train routings the user created on this turn.
      	addNewConnections();
	}
	
	/**
	 * Adds any new trains this turn that the player has bought!
	 */
	public void addNewTrains(){
		//Can't buy trains on the first turn- not enough cash
		//Solves issue of checking if a train is new by looking at previous turn data
		if(turnCount > 0){
			JSONArray prevTurn = (JSONArray)((JSONObject)gameData.get(String.valueOf(this.turnCount-1))).get("players");
			JSONArray currTurn = (JSONArray)turnData.get("players");
			JSONArray prevTrains;
			JSONArray currTrains;
			if(this.playerTurn.isPlayer1){
				prevTrains = (JSONArray)((JSONObject)prevTurn.get(0)).get("Trains");
				currTrains = (JSONArray)((JSONObject)currTurn.get(0)).get("Trains");
			}
			else{
				prevTrains = (JSONArray)((JSONObject)prevTurn.get(1)).get("Trains");
				currTrains = (JSONArray)((JSONObject)currTurn.get(1)).get("Trains");
			}
			for(int i = prevTrains.size(); i < currTrains.size(); i++){
				JSONObject train = (JSONObject)currTrains.get(i);
				playerTurn.getShop().buyNewTrain(gameMap.getStationWithName((String)train.get("station")));
			}
		}
	}
	
	/**
	 * @return returns the JSON object of the current player
	 */
	public JSONObject currentPlayerJSON(){
		//Get the player object for the current player
		JSONArray playersJSON = (JSONArray) turnData.get("players");
		JSONObject playerJSON;
		if(this.playerTurn.isPlayer1){
			playerJSON = (JSONObject) playersJSON.get(0);
		}
		else{
			playerJSON = (JSONObject) playersJSON.get(1);
		}
		return playerJSON;
	}
	
	/**
	 * updates the players resources each turn to match the json
	 */
	public void updateResources(){
		JSONObject playerJSON = currentPlayerJSON();
		
		//updates player gold
		this.playerTurn.addGold(((Long)playerJSON.get("Gold")).intValue() - this.playerTurn.getGold());
		
		String[] fuels = {"Coal", "Electric", "Nuclear", "Oil"};
		for(int i = 0; i < fuels.length; i++){
			this.playerTurn.addFuel(fuels[i], ((Long)playerJSON.get(fuels[i])).intValue() - this.playerTurn.getFuel(fuels[i]));
		}
	}
	
	/**
	 * updates goals from the json each turn, removes any completed/deleted goals
	 */
	public void updateGoals(){
		ArrayList<Goal> goals = this.playerTurn.getGoals();
		
		while(!goals.isEmpty()){
			goals.remove(0);
		}
		
		JSONObject playerJSON = currentPlayerJSON();
		JSONArray playerGoals = (JSONArray) playerJSON.get("Goals");
		
		for(int i = 0; i < playerGoals.size(); i++){
			JSONObject goal = (JSONObject)playerGoals.get(i);
			JSONObject sStation = (JSONObject) goal.get("sStation");
			JSONObject fStation = (JSONObject) goal.get("fStation");
			
			Goal newGoal = new Goal(gameMap.getStationWithName((String)sStation.get("name")), gameMap.getStationWithName((String)fStation.get("name")), ((Long)goal.get("timeConstraint")).intValue(), (String)goal.get("cargo"), ((Long)goal.get("reward")).intValue());
			goals.add(newGoal);
		}
	}
	
	/**
	 * Player scores are updated from the JSON file every turn so that they stay correct.
	 */
	public void updatePlayerScores(){
		JSONArray playersJSON = (JSONArray) turnData.get("players");
		for(int i = 0; i < playersArray.length; i++){
			JSONObject playerJSON = (JSONObject)playersJSON.get(i);
			int newPoints = ((Long)playerJSON.get("points")).intValue() - playersArray[i].getPoints();
			playersArray[i].incrementPoints(newPoints);
		}
	}
	
	/**
	 * runs through the array of station faults in our JSON - removing any that have been fixed and adding any that have become faulty this turn
	 */
	public void addStationFaults(){
		JSONArray faultyStations = (JSONArray)turnData.get("faultyStations");
		if(turnCount > 0){
			JSONObject prevTurn = (JSONObject) gameData.get(String.valueOf(this.turnCount-1));
			JSONArray oldFaults = (JSONArray) prevTurn.get("faultyStations");
			for(int i = 0; i < oldFaults.size(); i++){
				gameMap.getStationWithName((String)oldFaults.get(i)).fixFaulty();
			}
		}
		for(int i = 0; i < faultyStations.size(); i++){
			gameMap.getStationWithName((String)faultyStations.get(i)).makeFaulty();
		}
	}
	
	/**
	 *	Lock and unlock stations from this turn
	 */
	public void addStationLocks(){
		JSONArray lockedStations = (JSONArray)turnData.get("lockedStations");
		JSONArray unlockedStations = (JSONArray)turnData.get("unlockedStations");
		for(int i = 0; i < lockedStations.size(); i++){
			JSONObject mapObj = (JSONObject)lockedStations.get(i);
			if(((String)mapObj.get("type")).equals("station")){
				gameMap.getStationWithName((String)mapObj.get("name")).lock(true);
			}
			else{
				gameMap.getJunctionWithName((String)mapObj.get("name")).lock(true);
			}
		}
		for(int i = 0; i < unlockedStations.size(); i++){
			JSONObject mapObj = (JSONObject)unlockedStations.get(i);
			if(((String)mapObj.get("type")).equals("station")){
				gameMap.getStationWithName((String)mapObj.get("name")).lock(false);
			}
			else{
				gameMap.getJunctionWithName((String)mapObj.get("name")).lock(false);
			}
		}
	}
	
	/**
	 * checks the current connections are the same as those in the json.
	 */
	public void addNewConnections(){
		JSONArray playersJSON = (JSONArray) turnData.get("players");
		JSONObject playerJSON;
		if(playerTurn.isPlayer1){
			playerJSON = (JSONObject)playersJSON.get(0);
		}
		else{
			playerJSON = (JSONObject)playersJSON.get(1);
		}
		//add connections for player 1
		JSONArray trainArray = (JSONArray)playerJSON.get("Trains");
		
		for(int i = 0; i < playerTurn.getTrains().size(); i++){
			Train train = playerTurn.getTrains().get(i);
			
			//Update trains with obstacles and speed changes
			JSONObject trainJSON = (JSONObject) trainArray.get(i);
			train.setSpeedMod(((Long)trainJSON.get("speedMod")).intValue());
			if(trainJSON.containsKey("obstacle")){
				addObstacle((JSONObject)trainJSON.get("obstacle"), train);
			}
			else if(!trainJSON.containsKey("obstacle")){
				train.setObstacle(null);
			}
			
			//Update Train Route...
			JSONObject trainRoute = (JSONObject)trainJSON.get("route");
			//Get a list of connections for this train!
			JSONArray routeConnections = (JSONArray)trainRoute.get("connections");
			
			if(routeConnections.size() == 0){
				//WarningMessage.fireWarningWindow("No Route", "Your " + train.getName() + " made no moves this turn.");
			}
			
			int correctConnections = 0;
			boolean stillCorrect = true;
			//lets work forwards through the connections checking they're okay!
			while(stillCorrect){
				//Gets the current connection
				if(correctConnections < train.getRoute().getRoute().size()){ //if connections is less than the number of overall connections...
					if(correctConnections < routeConnections.size()){
						JSONObject connection = (JSONObject) routeConnections.get(correctConnections);
						//Gets the supposed start and end points of this connection
	
						
						//TODO Fix this to work with the updated version of connection object
						JSONObject startJSON = (JSONObject)connection.get("start");
						JSONObject destJSON = (JSONObject)connection.get("end");
						
						MapObj startObj = null;
						MapObj destObj = null;
						
						if(((String)startJSON.get("type")).equals("junction")){
							startObj = gameMap.getJunctionWithName((String)startJSON.get("name"));
							destObj = gameMap.getJunctionWithName((String)destJSON.get("name"));
						}
						else if(((String)startJSON.get("type")).equals("station"))
						{
							startObj = gameMap.getStationWithName((String)startJSON.get("name"));
							destObj = gameMap.getStationWithName((String)destJSON.get("name"));
						}
						
						Connection currentConnection = train.getRoute().getRoute().get(correctConnections);
						
						//exits the while loop if connection is different
						if(currentConnection.getStartMapObj() != startObj){
							stillCorrect = false;
						}
						else if(currentConnection.getDestination() != destObj){
							stillCorrect = false;
						}
						else{ //increments correctConnection and re-runs while loop if connection is the same.
							correctConnections++;
						}
					}
					else{
						stillCorrect = false;
					}
				}
				else{
					stillCorrect = false;
				}
			}
			//The next two for statements correct our route...
			//Remove any connections after the first incorrect connection
			if(correctConnections != train.getRoute().getRoute().size()){
				train.getRoute().abortRoute();
			}
			if(correctConnections == 0){
				train.getRoute().cancelRoute();
			}
			for(int j = correctConnections; j < routeConnections.size(); j++){
				//Gets the connection at this index
				JSONObject connection = (JSONObject) routeConnections.get(j);
				//Gets the supposed start and end points of this connection
				JSONObject startJSON = (JSONObject)connection.get("start");
				JSONObject destJSON = (JSONObject)connection.get("end");
				
				MapObj startObj = null;
				MapObj destObj = null;
				
				if(((String)startJSON.get("type")).equals("junction")){
					startObj = gameMap.getJunctionWithName((String)startJSON.get("name"));
				}
				else if(((String)startJSON.get("type")).equals("station"))
				{
					startObj = gameMap.getStationWithName((String)startJSON.get("name"));
				}
				
				if(((String)destJSON.get("type")).equals("junction")){
					destObj = gameMap.getJunctionWithName((String)destJSON.get("name"));
				}
				else if(((String)destJSON.get("type")).equals("station"))
				{
					destObj = gameMap.getStationWithName((String)destJSON.get("name"));
				}
				
				System.out.println(startObj.getName());
				System.out.println(destObj.getName());
				train.getRoute().addConnection(new Connection(startObj, destObj, Line.Black));
			}
			train.getRoute().hideRouteBlips();
			GameScreenUI.exitRoutingMode();
		}
	}
	
	public boolean togglePaused(){
		if(paused){
			paused = false;
		}
		else{
			paused = true;
		}
		return paused;
	}
	
	public void animationComplete(){
		if(!paused){
			if(turnCount < turnLimit){
				GameScreenUI.EndTurn();
			}
		}
	}
	/**
	 * Updates the cards belonging to each player to match the way they were in the saved game.
	 */
	public void updatePlayerCards(){
		//JSONArray playersJSON = (JSONArray) turnData.get("players");
		/*JSONObject playerJSON;
		if(playerTurn.isPlayer1){
			playerJSON = (JSONObject)playersJSON.get(0);
		}
		else{
			playerJSON = (JSONObject)playersJSON.get(1);
		}
		//playerJSON.get("")*/
	}
	
	/**
	 * Ends the turn of a player.
     * Checks for end game condition : turn count has reached its "limit" and player's points are not equal
     * It will increase the turn count and switch the player's turns.
	 */
	public void EndTurn() {
		//If turn limit is exceeded
        //New move if draw, else end game
        if (hasFinished()){
        	WarningMessage.fireWarningWindow("Game Over", "The game ended here!");
            EndGame();
        }

        else {
            playerTurn.lineBonuses();
            turnCount = (turnCount + 1);
            if (playerTurn == player1)
                playerTurn = player2;
            else{
                playerTurn = player1;
            }
            StartTurn();
        }
	}
	/**
	 * Update trains method.
	 * This makes trains mimic those of the json. 
	 */
	public void addObstacle(JSONObject obstacle, Train train){
		@SuppressWarnings("unused")
		Obstacle o = new Obstacle((String)obstacle.get("name"), (String)obstacle.get("description"), (Double)obstacle.get("speedFactor"), ((Long)obstacle.get("totalTurns")).intValue(), train);
	}
}
