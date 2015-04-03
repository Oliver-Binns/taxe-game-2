package com.TeamJKG.LocomotionCommotion.Replay;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.TeamHEC.LocomotionCommotion.Game.CoreGame;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.Line;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Train.CoalTrain;
import com.TeamHEC.LocomotionCommotion.Train.ElectricTrain;
import com.TeamHEC.LocomotionCommotion.Train.NuclearTrain;
import com.TeamHEC.LocomotionCommotion.Train.OilTrain;
import com.TeamHEC.LocomotionCommotion.Train.Route;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;

public class ReplayGame extends CoreGame {

	private JSONObject gameData;
	private JSONObject turnData;
	private Player[] playersArray;
	
	public ReplayGame(String Player1Name, String Player2Name, JSONObject gameData) {
		super(Player1Name, Player2Name, gameData.size() - 1);
		this.gameData = gameData;
		
		turnData = (JSONObject) gameData.get("0");
		JSONArray players = (JSONArray) turnData.get("players");
		JSONObject player1json = (JSONObject) players.get(0);
		JSONObject player2json = (JSONObject) players.get(1);
		JSONArray player1Stations = (JSONArray) player1json.get("Stations");
		JSONArray player2Stations = (JSONArray) player2json.get("Stations");

		Station Player1StationStart = gameMap.getStationWithName((String) player1Stations.get(0));
		Station Player2StationStart = gameMap.getStationWithName((String) player2Stations.get(0));
		
		setupPlayers(Player1Name, Player2Name, Player1StationStart, Player2StationStart);

		playersArray = new Player[]{player1, player2};
		// Start Game
		StartTurn();
		
		//TODO Find a way of displaying a warning message here?!
		//WarningMessage.fireWarningWindow("Welcome to Replay!", "Test");
	}
	
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
		//Add any train routings the user created on this turn.
		addNewConnections();
		
		//break any stations that became faulty on this turn.
		addStationFaults();
	
		updatePlayerScores();
		
		 // Proceed with the turn
        playerTurn.lineBonuses();
        playerTurn.stationRewards();

        //Increment all player's goals by one turn in duration
        for ( Goal goal : playerTurn.getGoals()){
            goal.incrementCurrentGoalDuration();
        }
        
        //Add new connections from JSON Data
	}
	
	/**
	 * 
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
	 * checks the current connections are the same as those in the json.
	 */
	public void addNewConnections(){
		JSONArray playersJSON = (JSONArray) turnData.get("players");
		for(int l = 0; l < playersArray.length; l++){
			//add connections for player 1
			JSONObject playerJSON = (JSONObject)playersJSON.get(l);
			JSONArray trainArray = (JSONArray)playerJSON.get("Trains");
			
			for(int i = 0; i < playersArray[l].getTrains().size(); i++){
				Train train = playersArray[l].getTrains().get(i);
				JSONObject trainJSON = (JSONObject) trainArray.get(i);
				JSONObject trainRoute = (JSONObject)trainJSON.get("route");
				JSONArray routeConnections = (JSONArray)trainRoute.get("connections");
				
				int correctConnections = 0;
				boolean stillCorrect = true;
				//lets work forwards through the connections checking they're okay!
				while(stillCorrect){
					//Gets the current connection
					if(correctConnections < train.getRoute().getRoute().size()){ //if connections is less than the number of overall connections...
						if(correctConnections < routeConnections.size()){
							JSONObject connection = (JSONObject) routeConnections.get(correctConnections);
							//Gets the supposed start and end points of this connection
							Station startJSON = gameMap.getStationWithName((String)connection.get("start"));
							Station destJSON = gameMap.getStationWithName((String)connection.get("end"));
							
							Connection currentConnection = train.getRoute().getRoute().get(correctConnections);
							
							//exits the while loop if connection is different
							if(currentConnection.getStartMapObj() != startJSON){
								stillCorrect = false;
							}
							else if(currentConnection.getDestination() != destJSON){
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
				for(int j = correctConnections; j < train.getRoute().getRoute().size(); j++){
					train.getRoute().removeConnection();
				}
				for(int j = correctConnections; j < routeConnections.size(); j++){
					//Gets the connection at this index
					JSONObject connection = (JSONObject) routeConnections.get(j);
					//Gets the supposed start and end points of this connection
					Station startJSON = gameMap.getStationWithName((String)connection.get("start"));
					Station destJSON = gameMap.getStationWithName((String)connection.get("end"));
					train.getRoute().addConnection(new Connection(startJSON, destJSON, Line.Black));
				}
				
				train.getRoute().hideRouteBlips();
			}
		}
	}
	
	/**
	 * Ends the turn of a player.
     * Checks for end game condition : turn count has reached its "limit" and player's points are not equal
     * It will increase the turn count and switch the player's turns.
	 */
	public void EndTurn() {
		//
		//If turn limit is exceeded
        //New move if draw, else end game
        if (turnCount >= turnLimit){
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
}
