package com.TeamJKG.LocomotionCommotion.Game;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.TeamJKG.LocomotionCommotion.Replay.Replay;

public class NewGame extends CoreGame {
	private Replay replay;
	
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
	public NewGame(String Player1Name, String Player2Name,	Station Player1StationStart, Station Player2StationStart, int turnLimit) {
		super(Player1Name, Player2Name, turnLimit);
		
		replay = new Replay(turnLimit, 2);
		
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
	}
	
	/**
	 * Starts a players turn. It will check for the end game condition.
	 */
	@Override
	public void StartTurn() {
        replay.newTurn(turnCount, 2);
        
		Player[] listOfPlayers = {player1, player2};
        replay.endTurn(listOfPlayers);
        replay.newTurn(turnCount, 2);
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
	 * Ends the turn of a player.
     * Checks for end game condition : turn count has reached its "limit" and player's points are not equal
     * It will increase the turn count and switch the player's turns.
	 */
	@Override
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
            	gameMap.generateFaults(replay);
                playerTurn = player1;
            }
            
            replay.endTurn(playerList);
            
            StartTurn();
        }

	}
	
	/**
	 * Ends the current game.
     * Only call once one player has a higher score than another
	 */
	@Override
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

		replay.saveGame();
	}
}
