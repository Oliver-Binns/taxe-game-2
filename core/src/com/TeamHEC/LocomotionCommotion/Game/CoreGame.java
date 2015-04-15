package com.TeamHEC.LocomotionCommotion.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.MapInstance;
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
 * @author Oliver Binns <ob601@york.ac.uk>
 * The core game object. Contains all information necessary for the backend of a single game.
 *
 */

public abstract class CoreGame {

	// Privates
	protected MapInstance gameMap;
	
	//needs to be visible to subclass
	protected Player player1;
	protected Player player2;
	
	protected Player playerTurn;
	protected int turnCount;
	protected int turnLimit;

    // The probability that an obstacle occurs for each train in a turn.
	
	
    protected static final double OBSTACLE_PROBABILITY = 0.15;
	
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
	public CoreGame(String Player1Name, String Player2Name,	int turnLimit) {
		// Initialise Map and other Game Resources
		gameMap = WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP);
		turnCount = 0;
		this.turnLimit = turnLimit;
	}

	/**
	 * Called from the subclass constructor- sets up players and their initial stations
	 * @param Name for Player 1
	 * @param Name for Player 2
	 * @param Start Station for Player 1
	 * @param Start Station for Player 2
	 */
	protected void setupPlayers(String Player1Name, String Player2Name, Station Player1StationStart, Station Player2StationStart){
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
	}
	
	/**
	 * Used during initialisation to assign a player a train based on their startStation fuel type
	 * @param player The player to be assigned a train
	 * @param startStation The player's starting station.
	 */
	protected void createFirstTrain(Player player, Station startStation) {
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
	protected int flipCoin() { 
		Random coin = new Random();
		return coin.nextInt(2);
	}

	/**
	 * Ends the turn of a player.
     * Checks for end game condition : turn count has reached its "limit" and player's points are not equal
     * It will increase the turn count and switch the player's turns.
	 */
	public abstract void EndTurn();

	/**
	 * Starts a players turn. It will check for the end game condition.
	 */
	public abstract void StartTurn();

	/**
	 * Ends the current game.
     * Only call once one player has a higher score than another
	 */
	protected void EndGame() {
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

        //Return to menu - need to fire warning window too?
		//LocomotionCommotion.getInstance().setMenuScreen();
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
	
	public MapInstance getGameMap() {
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
}