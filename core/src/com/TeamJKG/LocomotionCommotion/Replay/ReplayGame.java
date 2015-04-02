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

public class ReplayGame extends CoreGame {

	private JSONObject gameData;
	private JSONObject turnData;
	
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

		// Start Game
		StartTurn();
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
		
		 // Proceed with the turn
        playerTurn.lineBonuses();
        playerTurn.stationRewards();
        playerTurn.obstacles(OBSTACLE_PROBABILITY);

        //Increment all player's goals by one turn in duration
        for ( Goal goal : playerTurn.getGoals()){
            goal.incrementCurrentGoalDuration();
        }
        
        //Add new connections from JSON Data
	}
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
		//add connections for player 1
		JSONObject playerJSON = (JSONObject)playersJSON.get(0);
		JSONArray trainArray = (JSONArray)playerJSON.get("Trains");
		
		for(int i = 0; i < player1.getTrains().size(); i++){
			Train train = player1.getTrains().get(i);
			JSONObject trainJSON = (JSONObject) trainArray.get(i);
			JSONObject trainRoute = (JSONObject)trainJSON.get("route");
			JSONArray routeConnections = (JSONArray)trainRoute.get("connections");
			for(int j = 0; j < routeConnections.size(); j++){
				JSONObject connection = (JSONObject) routeConnections.get(j);
				Station startJSON = gameMap.getStationWithName((String)connection.get("start"));
				Station destJSON = gameMap.getStationWithName((String)connection.get("end"));
				if(train.getRoute().getRoute().size() <= j){
					train.getRoute().addConnection(new Connection(startJSON, destJSON, Line.Black)); //TODO fix colour of line here
				}
				else if(train.getRoute().getRoute().get(j).getStartMapObj() != startJSON){
					for(int k = j; k < train.getRoute().getRoute().size(); k++){
						train.getRoute().getRoute().remove(j);
					}
					train.getRoute().addConnection(new Connection(startJSON, destJSON, Line.Black)); //TODO fix colour of line here
				}
				else if(train.getRoute().getRoute().get(j).getDestination() != destJSON){
					for(int k = j; k < train.getRoute().getRoute().size(); k++){
						train.getRoute().getRoute().remove(j);
					}
					train.getRoute().addConnection(new Connection(startJSON, destJSON, Line.Black)); //TODO fix colour of line here
				}
				//else connection is the same
			}
			for(int j = 0; j < (train.getRoute().getRoute().size() - routeConnections.size()); j++){ //if there are more connections than in the json- remove these connections
				train.getRoute().getRoute().remove(j);
			}
		}
		
		//add connections for player 2
		playerJSON = (JSONObject)playersJSON.get(1);
		trainArray = (JSONArray)playerJSON.get("Trains");
		
		for(int i = 0; i < player2.getTrains().size(); i++){
			Train train = player2.getTrains().get(i);
			JSONObject trainJSON = (JSONObject) trainArray.get(i);
			JSONObject trainRoute = (JSONObject)trainJSON.get("route");
			JSONArray routeConnections = (JSONArray)trainRoute.get("connections");
			for(int j = 0; j < routeConnections.size(); j++){
				JSONObject connection = (JSONObject) routeConnections.get(j);
				Station startJSON = gameMap.getStationWithName((String)connection.get("start"));
				Station destJSON = gameMap.getStationWithName((String)connection.get("end"));
				if(train.getRoute().getRoute().size() <= j){
					train.getRoute().addConnection(new Connection(startJSON, destJSON, Line.Black)); //TODO fix colour of line here
				}
				else if(train.getRoute().getRoute().get(j).getStartMapObj() != startJSON){
					for(int k = j; k < train.getRoute().getRoute().size(); k++){
						train.getRoute().getRoute().remove(j);
					}
					train.getRoute().addConnection(new Connection(startJSON, destJSON, Line.Black)); //TODO fix colour of line here
				}
				else if(train.getRoute().getRoute().get(j).getDestination() != destJSON){
					for(int k = j; k < train.getRoute().getRoute().size(); k++){
						train.getRoute().getRoute().remove(j);
					}
					train.getRoute().addConnection(new Connection(startJSON, destJSON, Line.Black)); //TODO fix colour of line here
				}
				//else connection is the same
			}
			for(int j = 0; j < (train.getRoute().getRoute().size() - routeConnections.size()); j++){ //if there are more connections than in the json- remove these connections
				train.getRoute().getRoute().remove(j);
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
