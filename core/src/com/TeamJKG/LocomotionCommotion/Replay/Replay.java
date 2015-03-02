package com.TeamJKG.LocomotionCommotion.Replay;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.google.gson.*;

/**
 * 
 * @author Oliver Binns <ob601@york.ac.uk>
 *
 */

public class Replay {
	private ArrayList<Turn> listOfTurns;
	private Turn currentTurn;
	/**
	 * creates an instance of the replay class, this handles the Replay functionality within the game.
	 * @param turnCount
	 * @param playerCount
	 */
	public Replay(int turnCount, int playerCount){
		listOfTurns = new ArrayList<Turn>();
		currentTurn = new Turn(turnCount, playerCount);
	}
	/**
	 * called at the start of a new turn, initialises a new turn objects
	 * @param turnCount
	 * @param playerCount
	 */
	public void newTurn(int turnCount, int playerCount){
		currentTurn = new Turn(turnCount, playerCount);
	}
	
	/**
	 * called in the generate faults method to save a list of faulty stations for that turn
	 * @param faultyStation
	 */
	public void addFault(Station faultyStation){
		currentTurn.addFaultyStation(faultyStation);
	}
	/**
	 * called when a station is fixed to remove it from the list of faulty stations
	 * @param fixedStation
	 */
	public void removeFault(Station fixedStation){
		currentTurn.removeFaultyStation(fixedStation);
	}
	
	/**
	 * called at the end of each turn to add the current turn to the list of turns
	 * @param listOfPlayers
	 */
	public void endTurn(Player[] listOfPlayers){
		currentTurn.addPlayers(listOfPlayers);
		listOfTurns.add(currentTurn);
		addNewTurn();
	}
	/**
	 * TODO implement save game
	 */
	public void addNewTurn(){
		currentTurn.toJSON();
		
		
		//System.out.println(gson.toJson(currentTurn));
	}
	public void saveGame(){
		
		//lets use gson to convert to a json file because we're not Team HEC. 
	}
}
