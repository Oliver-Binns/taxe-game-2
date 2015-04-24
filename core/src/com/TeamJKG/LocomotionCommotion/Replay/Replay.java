package com.TeamJKG.LocomotionCommotion.Replay;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Player.Player;

/**
 * 
 * @author Oliver Binns <ob601@york.ac.uk>
 *
 */

public class Replay {
	private ArrayList<Turn> listOfTurns;
	private Turn currentTurn;
	private ArrayList<String> faultyStations;
	private String json;
	/**
	 * creates an instance of the replay class, this handles the Replay functionality within the game.
	 * @param turnCount
	 * @param playerCount
	 */
	public Replay(int turnCount, int playerCount){
		json = "{";
		listOfTurns = new ArrayList<Turn>();
		currentTurn = new Turn(turnCount, playerCount);
		faultyStations = new ArrayList<String>();
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
	 * 
	 * @param a faulty station
	 * adds a station to the faulty station array if it not already present
	 */
	public void addFault(Station station){
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
	/**
	 * called when a station is fixed to remove it from the list of faulty stations
	 * @param fixedStation
	 */
	public void removeFault(Station station){
		faultyStations.remove(station.getName());
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
		json += currentTurn.toJSON(faultyStations) + ",";
	}
	public void saveGame(){
		//Maybe HEC were right.. awks. #YOLO
		json = json.substring(0, json.length()-1);
		json += "}";
		PrintWriter out;
		try {
			out = new PrintWriter(System.getProperty("user.home") + "/save.loco");
			out.print(json);
			out.close();
		} catch (FileNotFoundException e) {
			//File not found exception...
			e.printStackTrace();
		}
	}
}
