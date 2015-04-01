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
import com.TeamJKG.LocomotionCommotion.Replay.Replay;

/**
 * 
 * @author Callum Hewitt <ch1194@york.ac.uk>
 * @author Oliver Binns <ob601@york.ac.uk>
 * The core game object. Contains all information necessary for the backend of a single game.
 *
 */

public class CoreGame {

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
	public void EndTurn() {
		//If turn limit is exceeded
        //New move if draw, else end game
        if (turnCount >= turnLimit && player1.getPoints() != player2.getPoints()){
            EndGame();
        }

        else {
        	//adds players to replay at end of each turn
        	Player[] playerList = {player1, player2};
        	
            playerTurn.lineBonuses();
            turnCount = (turnCount + 1);
            if (playerTurn == player1)
                playerTurn = player2;
            else{
            	//gameMap.generateFaults(replay);
                playerTurn = player1;
            }
            
            //replay.endTurn(playerList);
            
            StartTurn();
        }

	}

	/**
	 * Starts a players turn. It will check for the end game condition.
	 */
	public void StartTurn() {
        //replay.newTurn(turnCount, 2);
        
		Player[] listOfPlayers = {player1, player2};
        //replay.endTurn(listOfPlayers);
        //replay.newTurn(turnCount, 2);
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

        WarningMessage.fireWarningWindow("End of Game", "Congratulations to " + winner.getName() + " you have won!");

		//replay.saveGame();
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