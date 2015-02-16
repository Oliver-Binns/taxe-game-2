package com.TeamHEC.LocomotionCommotion.Game;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Resource.Resource;
import com.TeamHEC.LocomotionCommotion.Train.CoalTrain;
import com.TeamHEC.LocomotionCommotion.Train.ElectricTrain;
import com.TeamHEC.LocomotionCommotion.Train.NuclearTrain;
import com.TeamHEC.LocomotionCommotion.Train.OilTrain;
import com.TeamHEC.LocomotionCommotion.Train.Route;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;

/**
 * 
 * @author Callum Hewitt <ch1194@york.ac.uk>
 * The core game object. Contains all information necessary for the backend of a single game.
 *
 */

public class CoreGame {

	// Privates
	private WorldMap gameMap;
	private Player player1;
	private Player player2;
	private Player playerTurn;
	private int turnCount;
	private int turnLimit;

    // The probability that an obstacle occurs for each train in a turn.
    private static final double OBSTACLE_PROBABILITY = 0.15;
	
	/**
	 * Initialises a Game object. This represents one instance of a game.
	 * 
	 * @param Player1Name
	 *            The name of Player1
	 * @param Player2Name
	 *            The name of Player2
	 * @param Player1StationStart
	 *            Player1 should have selected a station to buy at the beginning
	 *            of the game. This is their selected Station's object.
	 * @param Player2StationStart
	 *            Player2 should have selected a station to buy at the beginning
	 *            of the game. This is their selected Station's object.
	 * @param turnLimit
	 *            The number of turns before the end of the game.
	 */
	public CoreGame(String Player1Name, String Player2Name,	Station Player1StationStart, Station Player2StationStart, int turnLimit) {
		
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

		// Initialise Map and other Game Resources

		gameMap = WorldMap.getInstance();
		turnCount = 0;
		this.turnLimit = turnLimit;

		// Make decision on who goes first

		if (flipCoin() == 1)
			playerTurn = player2;
		else
			playerTurn = player1;
		
		// Start Game
		StartTurn();
	}

	/**
	 * Used during initialisation to assign a player a train based on their startStation fuel type
	 * @param player The player to be assigned a train
	 * @param startStation The player's starting station.
	 */
	private void createFirstTrain(Player player, Station startStation) {
		String fuelType = startStation.getResourceString();
		Train train = null;

		if (fuelType.equals("Coal"))
			train = new CoalTrain(0, true, new Route(startStation), player);
		else if (fuelType.equals("Nuclear"))
			train = new NuclearTrain(0, true, new Route(startStation),player);
		else if (fuelType.equals("Electric"))
			train = new ElectricTrain(0, true, new Route(startStation),player);
		else if (fuelType.equals("Oil"))
			train = new OilTrain(0, true, new Route(startStation), player);
		else
			train = new OilTrain(0, true, new Route(startStation), player);

		player.getTrains().add(train);
	}

	/**
	 * Randomly returns either 0 or 1. It's used in determining which player
	 * will go first in this game.
	 */
	private int flipCoin() {
		Random coin = new Random();
		return coin.nextInt(2);
	}

	/**
	 * Ends the turn of a player.
     * Checks for end game condition : turn count has reached its "limit" and player's points are not equal
     * It will increase the turn count and switch the player's turns.
	 */
	public void EndTurn() {

        //Adds an extra turn if score is equal
        if (turnCount >= turnLimit && player1.getPoints() != player2.getPoints()){
            EndGame();
        }

        else {
            playerTurn.lineBonuses();
            turnCount = (turnCount + 1);
            if (playerTurn == player1)
                playerTurn = player2;
            else
                playerTurn = player1;
            StartTurn();
        }

	}

	/**
	 * Starts a players turn. It will check for the end game condition.
	 */
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

	/**
	 * Ends the current game.
     * Only call once one player has a higher score than another
	 */
	private void EndGame() {

        Player winner;

        if ( player1.getPoints() > player2.getPoints() ) {
            player1.setAsWinner();
            player2.setAsLoser();
            winner = player1;
        }

        else if ( player1.getPoints() < player2.getPoints()) {
            player1.setAsLoser();
            player2.setAsWinner();
            winner = player2;
        }

        else {
            return; //Game should not end if there is a draw
        }

        WarningMessage.fireWarningWindow("End of Game", "Congratulations to " + winner.getName() + " you have won!");
	}

	/**
	 * Generates the resources a player will start with based on their start
	 * location
	 * 
	 * @param station A player's starting location.
	 * 
	 */
	public HashMap<String, Resource> getBaseResources(Station station) {
		Gold gold = new Gold(1000);
		Coal coal = new Coal(200);
		Oil oil = new Oil(200);
		Electric electric = new Electric(200);
		Nuclear nuclear = new Nuclear(200);

		HashMap<String, Resource> dict = new HashMap<String, Resource>();

		dict.put("gold", gold); // Base gold amount minus the value of the
		dict.put("coal", coal);
		dict.put("oil", oil);
		dict.put("electric", electric);
		dict.put("nuclear", nuclear);

		return dict;
	}
	
	public WorldMap getGameMap() {
		return gameMap;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public int getTurnCount() {
		return turnCount;
	}

	public int getTurnLimit() {
		return turnLimit;
	}

	public Player getPlayerTurn() {
		return playerTurn;
	}

	/**
	 * Saves the game to a .json file in the user's home directory in a folder called LocomotionCommotion.
	 * @param gameName The name of the .json file the game is saved to. (No extension).
	 */
	public void saveGameJSON(String gameName)
	{
		String finalJSON = "{";
		//Save Players
		finalJSON += "\"player1\": " + savePlayerJSON(player1) + ", ";
		finalJSON += "\"player2\": " + savePlayerJSON(player2) + ", ";
		
		//Save Turn - whose turn, turn count, turnLimit
		finalJSON += "\"playerTurn\": \"" + playerTurn.getName() + "\", ";
		finalJSON += "\"turnCount\": " + turnCount + ", ";
		finalJSON += "\"turnLimit\": " + turnLimit;
		
		finalJSON += "}";
		//Write to file
		try
		{
		File saveLocation = new File(System.getProperty("user.home")
				+ System.getProperty("file.separator")
				+ "LocomotionCommotion"
				+ System.getProperty("file.separator") + gameName + ".json");
		saveLocation.getParentFile().mkdirs();
		saveLocation.createNewFile();
		PrintWriter out = new PrintWriter(saveLocation);
		out.println(finalJSON);
		out.close();

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * Returns the player given represented as a JSON string. Called by saveGameJSON.
	 * @param player The player to be represented in JSON.
	 * @return A JSON string representing player.
	 */
	private String savePlayerJSON(Player player)
	{
		String playerJSON = "{";
		//Save Player resources
		playerJSON += "\"resources\" : {";
		playerJSON += "\"gold\" : " 	+ player.getGold() + ",";
		playerJSON += "\"coal\" : " 	+ player.getFuel("Coal") + ",";
		playerJSON += "\"oil\" : " 		+ player.getFuel("Oil") + ",";
		playerJSON += "\"electric\" : " + player.getFuel("Electric") + ",";
		playerJSON += "\"nuclear\" : " 	+ player.getFuel("Nuclear");
		playerJSON += "}, ";
		
		//Save Player cards
		playerJSON += "\"cards\" : [";
		for(int i = 0; i < player.getCards().size(); i++){
			playerJSON += "{";
			playerJSON += "\"cardType\" : \"" + player.getCards().get(i).getName() + "\"}";
			if(i != player.getCards().size() - 1)
				playerJSON += ", ";
		}
		playerJSON += "], ";		
		
		//Save Player trains
		playerJSON += "\"trains\" : [";
		for(int i = 0; i < player.getTrains().size(); i++){
			playerJSON += "{";
			playerJSON += "\"type\" : \"" + player.getTrains().get(i).getFuelType() + "\", ";
			playerJSON += "\"inStation\" : " + player.getTrains().get(i).isInStation() + ", ";
			playerJSON += "\"route\" : " + saveRouteJSON(player.getTrains().get(i).getRoute()) + ", ";
			playerJSON += "\"speedMod\" : " + player.getTrains().get(i).getSpeedMod();
			playerJSON += "}";
			if(i != player.getTrains().size() - 1)
				playerJSON += ", ";
		}
		playerJSON += "], ";
		
		//Save Player stations
		playerJSON += "\"stations\" : [";
		for(int i = 0; i < player.getStations().size(); i++){
			playerJSON += "{";
			playerJSON += "\"stationName\" : \"" + player.getStations().get(i).getName() + "\", ";
			playerJSON += "\"rentValueMod\" : " + player.getStations().get(i).getRentValueMod() + ", ";
			playerJSON += "\"resourceOutMod\" : " + player.getStations().get(i).getResourceOutMod() + ", ";
			playerJSON += "\"valueMod\" : " + player.getStations().get(i).getValueMod();
			playerJSON += "}";
			if(i != player.getTrains().size() - 1)
				playerJSON += ", ";
		}
		playerJSON += "],";
		
		//Save Player goals
		playerJSON += "\"goals\" : [";
		for(int i = 0; i < player.getGoals().size(); i++){
			playerJSON += "{";
			playerJSON += "\"SStation\" : \"" + player.getGoals().get(i).getSStation() + "\", ";
			playerJSON += "\"FStation\" : \"" + player.getGoals().get(i).getFStation() + "\", ";
			playerJSON += "\"stationVia\" : \"" + player.getGoals().get(i).getTimeConstraintString() + "\", ";
			playerJSON += "\"special\" : " + player.getGoals().get(i).isSpecial() + ", ";
			playerJSON += "\"reward\" : " + player.getGoals().get(i).getReward() + ", ";
			playerJSON += "\"cargo\" : \"" + player.getGoals().get(i).getCargo() + "\"";
			playerJSON += "}";
			if(i != player.getGoals().size() - 1)
				playerJSON += ", ";
		}
		playerJSON += "]";
		
		playerJSON += "}";		
		return playerJSON;
	}
	
	/**
	 * Returns the route given represented as a JSON string. Called by savePlayerJSON.
	 * @param route The route to be represented in JSON.
	 * @return A JSON string representing the route.
	 */
	private String saveRouteJSON(Route route)
	{
		String routeJSON = "{";
		routeJSON += "\"routeIndex\" : " + route.getRouteIndex() + ", ";
		routeJSON += "\"connections\" : ["; 		
		for(int i = 0; i < route.getRoute().size(); i++){
			routeJSON += "{";
			//Sets startMapObj and endMapObj. You will have to match these up to the relevant stations and junctions
			//on the load. Or alter this section to write the name instead of the x,y coordinates.
			routeJSON += "\"startMapObj\" : " + saveMapObjJSON(route.getRoute().get(i).getStartMapObj()) + ", ";
			routeJSON += "\"endMapObj\" : " + saveMapObjJSON(route.getRoute().get(i).getDestination());
			routeJSON += "}";
			if(i != route.getRoute().size() - 1)
				routeJSON += ", ";
		}			
		routeJSON += "],";
		routeJSON += "\"connectionTravelled\" : " + route.getConnectionTravelled();
		routeJSON += "}";
		return routeJSON;
	}
	
	/**
	 * Returns the mapObj given represented as a JSON string. Called by saveRouteJSON.
	 * @param mapObj The mapObj to be represented in JSON.
	 * @return A JSON string representing the mapObj
	 */
	private String saveMapObjJSON(MapObj mapObj)
	{
		String mapObjJSON = "{";
		mapObjJSON += "\"x\" : " + mapObj.x + ", ";
		mapObjJSON += "\"y\" : " + mapObj.y;
		mapObjJSON += "}";
		return mapObjJSON;
	}
	
}