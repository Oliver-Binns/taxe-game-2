package com.TeamHEC.LocomotionCommotion;

import java.io.File;
import java.util.Arrays;
import com.badlogic.gdx.Gdx;

public final class GameData {
	public static int RESOLUTION_HEIGHT = 1050;
	public static int RESOLUTION_WIDTH = 1680;
	public static int FOV = 90;
	public static String DEFAULT_MAP_STRING = Gdx.files.internal("Map1.json").readString();
	public static String GAME_FOLDER = System.getProperty("user.home") + 
			System.getProperty("file.separator") + 
			"LocomotionCommotion" + 
			System.getProperty("file.separator") + 
			"Team-JKG";
	public static String MAP_FOLDER = GAME_FOLDER + System.getProperty("file.separator") + "Maps";
	public static String CURRENT_MAP = selectMap();
	
	
	/* Fix for the bug which crashes the game when "Map1" is not available in MapFolder while other maps are. 
	Selects the first map available to the game at the moment. Can scale it to include a choice of maps and different levels*/
	public static String selectMap(){
		File mapFolder = new File(MAP_FOLDER);
		String[] mapFiles = mapFolder.list();
		if (Arrays.asList(mapFiles).contains("Map1.json")){
			return "Map1";}
		
		String mapFile = Arrays.asList(mapFiles).get(0); 
		return mapFile.substring(0, mapFile.length()-5);
		}
}
