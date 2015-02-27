package com.TeamHEC.LocomotionCommotion.Scene;

public class SceneManager{
	
	public StartMenu startScene;
	
	private static SceneManager instance = new SceneManager();
	
	public static SceneManager getInstance()
	{
		return instance;
	}
	
	private SceneManager()
	{
        startScene = new StartMenu();
        startScene.addToStage();
	}
}
