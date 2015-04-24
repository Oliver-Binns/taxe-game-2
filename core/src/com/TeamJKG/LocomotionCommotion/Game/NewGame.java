package com.TeamJKG.LocomotionCommotion.Game;

import com.TeamHEC.LocomotionCommotion.Game.CoreGame;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Player.Player;
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
		
		setupPlayers(Player1Name, Player2Name, Player1StationStart, Player2StationStart);

		// Start Game
		StartTurn();
	}
	
	/**
	 * Saves any fixed stations to Replay.
	 */
	public void fixFault(Station station){
		replay.removeFault(station);
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
        	if (turnCount % 2 == 1){
        		player1.addGold(player1.getNumStations() * 50);
        		player2.addGold(player2.getNumStations() * 50);
        	}
        	
            playerTurn.lineBonuses();
            turnCount = (turnCount + 1);
            if (playerTurn == player1)
                playerTurn = player2;
            else{
            	gameMap.generateFaults(replay);
                playerTurn = player1;
            }
            StartTurn();
        }
	}
	
	public void saveTurn(){
		Player[] playerList = {player1, player2};
		replay.endTurn(playerList);
	}
	
	/**
	 * 
	 */
	@Override
	protected void EndGame() {
		replay.saveGame();
		super.EndGame();
	}
	public void forceSave(){
		replay.saveGame();
	}
}
