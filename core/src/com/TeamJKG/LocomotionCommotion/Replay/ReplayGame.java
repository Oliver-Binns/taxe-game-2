package com.TeamJKG.LocomotionCommotion.Replay;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Game.CoreGame;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Resource.Resource;
import com.TeamHEC.LocomotionCommotion.Train.Train;

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
		
		System.out.println(Player1StationStart);
		
		HashMap<String, Resource> player1Resources = getBaseResources(Player1StationStart);
		HashMap<String, Resource> player2Resources = getBaseResources(Player2StationStart);

		player1 = new Player(Player1Name, 0,
				(Gold) player1Resources.get("gold"),
				(Coal) player1Resources.get("coal"),
				(Electric) player1Resources.get("electric"),
				(Nuclear) player1Resources.get("nuclear"),
				(Oil) player1Resources.get("oil"),
				new ArrayList<Card>(), new ArrayList<Goal>(),
				new ArrayList<Train>());

		player2 = new Player(Player2Name, 0,
				(Gold) player2Resources.get("gold"),
				(Coal) player2Resources.get("coal"),
				(Electric) player2Resources.get("electric"),
				(Nuclear) player2Resources.get("nuclear"),
				(Oil) player2Resources.get("oil"),
				new ArrayList<Card>(), new ArrayList<Goal>(),
				new ArrayList<Train>());

		player1.isPlayer1 = true;
		player2.isPlayer1 = false;

		// Create players First Train depending on the station selected:
		createFirstTrain(player1, Player1StationStart);
		createFirstTrain(player2, Player2StationStart);
		
		player1.purchaseStation(Player1StationStart);
		player2.purchaseStation(Player2StationStart);

		// Make decision on who goes first

		if (flipCoin() == 1)
			playerTurn = player2;
		else
			playerTurn = player1;

		// Start Game
		StartTurn();
		// TODO Auto-generated constructor stub
	}

}
