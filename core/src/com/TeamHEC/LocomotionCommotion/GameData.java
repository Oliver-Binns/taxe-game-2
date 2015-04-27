package com.TeamHEC.LocomotionCommotion;

import com.badlogic.gdx.Gdx;

public final class GameData {
	public static boolean TEST_CASE = false;
	public static int RESOLUTION_HEIGHT = 1050;
	public static int RESOLUTION_WIDTH = 1680;
	public static int FOV = 90;
	public static boolean EDITING = false;
	public static String CURRENT_MAP = "Map1";
	public static String DEFAULT_MAP_STRING = Gdx.files.internal("Map1.json").readString();
	public static String GAME_FOLDER = System.getProperty("user.home") + 
			System.getProperty("file.separator") + 
			"LocomotionCommotion" + 
			System.getProperty("file.separator") + 
			"Team-JKG";
	public static String MAP_FOLDER = GAME_FOLDER + System.getProperty("file.separator") + "Maps";
}