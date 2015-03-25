package com.TeamJKG.LocomotionCommotion.Replay;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Player.Player;
/**
 * 
 * @author Oliver Binns <ob601@york.ac.uk>
 *
 */
public class Turn {
	private int turnCount;
	private Player[] players;
	//will need a variable to store active lines
	
	/**
	 * Creates an instance of the turn class. This is used in the replay functionality within the game
	 * @param turnCount
	 * @param playerCount
	 */
	public Turn(int turnCount, int playerCount){
		this.turnCount = turnCount;
		players = new Player[playerCount];
	}
	/**
	 * adds the players to the turn class at the end of the turn
	 * @param listOfPlayers
	 */
	public void addPlayers(Player[] listOfPlayers){
		/** 
		 * just in case!
		 */
		for(int i = 0; i < players.length; i++){
			players[i] = listOfPlayers[i];
		}
	}
	
	/**
	 * Recursively generates a JSON of the Turn instance and all its variables
	 * @return JSON of the turn instance
	 */
	public String toJSON(ArrayList<String> faultyStations){
		String json = "\"" + String.valueOf(turnCount) + "\": {";
		json += "\"players\": [";
		//recursively prints array of players
		for(int i = 0; i < players.length; i++){
			json += players[i].toJSON();
			//adds if we are not at the last object in players array
			if((i+1) < players.length){
				json += ",";
			}
		}
		json += "],";
		
		//prints array of stations
		json += "\"faultyStations\": [";
		for(int i = 0; i < faultyStations.size(); i++){
			json += "\"" + faultyStations.get(i) + "\"";
			if((i+1) < faultyStations.size()){
				json += ",";
			}
		}
		
		json += "]}";
		return json;
	}
}
