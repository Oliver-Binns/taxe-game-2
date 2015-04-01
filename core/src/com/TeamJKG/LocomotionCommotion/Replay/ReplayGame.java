package com.TeamJKG.LocomotionCommotion.Replay;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.TeamHEC.LocomotionCommotion.Game.CoreGame;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.Station;

public class ReplayGame extends CoreGame {

	private JSONObject gameData;
	
	public ReplayGame(String Player1Name, String Player2Name, JSONObject gameData) {
		super(Player1Name, Player2Name, 150);
		this.gameData = gameData;
		
		JSONObject turn = (JSONObject) gameData.get("0");
		JSONArray players = (JSONArray) turn.get("players");
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
	
	/**
	 * Starts a players turn. It will check for the end game condition.
	 */
	@Override
	public void StartTurn() {
		 // Proceed with the turn:
        playerTurn.lineBonuses();
        playerTurn.stationRewards();
        playerTurn.obstacles(OBSTACLE_PROBABILITY);

        //Increment all player's goals by one turn in duration
        for ( Goal goal : playerTurn.getGoals()){
            goal.incrementCurrentGoalDuration();
        }
	}
}
