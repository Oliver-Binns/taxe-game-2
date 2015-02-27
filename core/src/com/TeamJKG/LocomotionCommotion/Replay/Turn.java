package com.TeamJKG.LocomotionCommotion.Replay;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Map.Station;
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
	private ArrayList<String> faultyStations;

	/**
	 * Creates an instance of the turn class. This is used in the replay functionality within the game
	 * @param turnCount
	 * @param playerCount
	 */
	public Turn(int turnCount, int playerCount){
		this.turnCount = turnCount;
		players = new Player[playerCount];
		faultyStations = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @param a faulty station
	 * adds a station to the faulty station array if it not already present
	 */
	public void addFaultyStation(Station station){
		boolean isNotInArray = true;
		for(int i = 0; i < faultyStations.size(); i++){
			if(faultyStations.get(i) == station.getName()){
				isNotInArray = false;
			}
		}
		if(isNotInArray){
			faultyStations.add(station.getName());
		}
	}
	public void removeFaultyStation(Station station){
		for(int i = 0; i < faultyStations.size(); i++){
			if(faultyStations.get(i) == station.getName()){
				faultyStations.remove(i);
				break;
			}
		}
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
	
}
