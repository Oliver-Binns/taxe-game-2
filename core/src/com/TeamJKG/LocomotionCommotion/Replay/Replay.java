package com.TeamJKG.LocomotionCommotion.Replay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Player.Player;

/**
 * 
 * @author Oliver Binns <ob601@york.ac.uk>
 *
 */

public class Replay {
	private Turn currentTurn;
	private ArrayList<String> faultyStations;
	private ArrayList<String> lockedStations;
	private ArrayList<String> unlockedStations;
	private String json;
	/**
	 * creates an instance of the replay class, this handles the Replay functionality within the game.
	 * @param turnCount
	 * @param playerCount
	 */
	public Replay(int turnCount, int playerCount){
		json = "{ \"map\": \"" + GameData.CURRENT_MAP + "\",";
		json += "\"turns\": " + "{";
		currentTurn = new Turn(turnCount, playerCount);
		faultyStations = new ArrayList<String>();
		lockedStations = new ArrayList<String>();
		unlockedStations = new ArrayList<String>();
	}
	/**
	 * called at the start of a new turn, initialises a new turn objects
	 * @param turnCount
	 * @param playerCount
	 */
	public void newTurn(int turnCount, int playerCount){
		currentTurn = new Turn(turnCount, playerCount);
		lockedStations.clear();
		unlockedStations.clear();
	}
	
	/**
	 * 
	 * @param a faulty station
	 * adds a station to the faulty station array if it not already present
	 */
	public void addFault(Station station){
		boolean isInArray = faultyStations.contains(station.getName());
		if(!isInArray){
			faultyStations.add(station.getName());
		}
	}
	
	/**
	 * @param a locked station
	 * adds a station to the locked station array if it not already present
	 */
	public void addLock(MapObj obj){
		boolean isInArray = lockedStations.contains(obj.toJSON());
		if(!isInArray){
			lockedStations.add(obj.toJSON());
		}
		unlockedStations.remove(obj.toJSON());
	}
	/**
	 * called when a station is fixed to remove it from the list of faulty stations
	 * @param fixedStation
	 */
	public void removeFault(Station station){
		faultyStations.remove(station);
	}
	
	/**
	 * called when a station is fixed to remove it from the list of locked stations
	 * @param unlockedStation
	 */
	public void removeLock(MapObj obj){
		boolean isInArray = unlockedStations.contains(obj.toJSON());
		if(!isInArray){
			unlockedStations.add(obj.toJSON());
		}
		lockedStations.remove(obj.toJSON());
	}
	
	/**
	 * called at the end of each turn to add the current turn to the list of turns
	 * @param listOfPlayers
	 */
	public void endTurn(Player[] listOfPlayers){
		currentTurn.addPlayers(listOfPlayers);
		if(listOfPlayers[0].isPlayer1){
			addNewTurn();
		}
	}
	/**
	 * TODO implement save game
	 */
	public void addNewTurn(){
		json += currentTurn.toJSON(faultyStations, lockedStations, unlockedStations) + ",";
	}
	
	/**
	 * Saves the game either when the user hits save, or automatically at the end of a game.
	 */
	public void saveGame(){
		//Maybe HEC were right.. awks. #YOLO
		
		//Finalises JSON by closing the turns array
		json = json.substring(0, json.length()-1);
		json += "}}";
		
		File saveFolder = new File(GameData.SAVE_FOLDER);
		boolean created = false;
		if(!saveFolder.exists()) {
			//GAME SAVE FOLDER DOES NOT EXIST
			try {
				created = saveFolder.mkdirs();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			if(created) {
				//SAVE FILE
				PrintWriter out;
				try {
					out = new PrintWriter(GameData.SAVE_FOLDER + "/save.loco");
					out.print(json);
					out.close();
				} catch (FileNotFoundException e) {
					//File not found exception...
					e.printStackTrace();
				}
			} else {
				System.out.println("Game save creation failed, please check your permissions.");
			}
		} else {
			//GAME SAVE FOLDER EXISTS
			PrintWriter out;
			try {
				out = new PrintWriter(GameData.SAVE_FOLDER + "/save.loco");
				out.print(json);
				out.close();
			} catch (FileNotFoundException e) {
				//File not found exception...
				e.printStackTrace();
			}
		}

		//Reopens the turns array incase we need to resave..
		json = json.substring(0, json.length()-2);
		json += ",";
	}
}
