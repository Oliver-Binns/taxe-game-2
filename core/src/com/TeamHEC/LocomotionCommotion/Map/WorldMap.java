package com.TeamHEC.LocomotionCommotion.Map;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.TeamHEC.LocomotionCommotion.GameData;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 */

public class WorldMap {

	private final static WorldMap INSTANCE = new WorldMap();
	
	public static WorldMap getInstance()
	{
		return INSTANCE;
	}
	
	//WorldMap holds a HashMap of MapInstances, allowing the player to choose which map they would like to use.
	public Map<String, MapInstance> mapList;
	
	/**
	 * Populates the mapList with all of the map files it can find.
	 */
	public WorldMap()
	{
		mapList = new HashMap<String, MapInstance>();
		
		//If the map folder/files don't exist, a default file structure is generated and populated with the default map file.
		File mapFolder = new File(GameData.MAP_FOLDER);
		boolean created = false;
		
		if(!mapFolder.exists()) {
			try {
				created = mapFolder.mkdirs();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			if(created) {
				System.out.println("Folder created. Populating with default map file.");
				createDefaultFile();
			} else {
				System.out.println("Folder not created, please check your permissions.");
			}
		}
		
		//Populate mapList with all MapInstances generated from the map files. 
		String[] mapFiles = mapFolder.list();
		for(String mapFile : mapFiles) {
			String mapName = mapFile.substring(0, mapFile.length()-5);
			mapList.put(mapName, new MapInstance(GameData.MAP_FOLDER + System.getProperty("file.separator") + mapFile));
		}
	}
	
	/**
	 * Generates the default map file from the version held in the assets folder
	 */
	private void createDefaultFile() {
		try {
			PrintWriter out = new PrintWriter(GameData.MAP_FOLDER + System.getProperty("file.separator") + "Map1.json");
			out.println(GameData.DEFAULT_MAP_STRING);
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Calls MapInstance.reset() for all objects in mapList, used when player exits to main menu
	 */
	public void resetAll() {
		MapInstance[] maps = mapList.values().toArray(new MapInstance[mapList.size()]);
		
		for(int i=0; i<maps.length; i++) {
			maps[i].reset();
		}
	}
}
