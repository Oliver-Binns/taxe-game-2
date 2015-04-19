package com.TeamHEC.LocomotionCommotion;

import java.io.File;

import com.TeamHEC.LocomotionCommotion.Game.CoreGame;
import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Scene.SceneManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
/**
 * 
 * @author Robert Precious <rp825@york.ac.uk>
 * 
 * This is the main class.
 * We create the the startMenu as a Scene and GameScreen as a Screen. There is no significant difference between the two.
 * 
 */
public class LocomotionCommotion extends Game {

	public GameScreen gameScreen;
	public CoreGame newGame;

	public static int screenX = 1680;
	public static int screenY = 1050;

	//StartMenuOptions
	public static String gameMode, player1name, player2name;
	public static Boolean isReplay;
	public static int turnChoice;

	private static LocomotionCommotion INSTANCE = new LocomotionCommotion();

	public static LocomotionCommotion getInstance()
	{		
		return INSTANCE;
	}

	private LocomotionCommotion(){}
	public static final String TITLE = "LOCOMOTION COMOTION", VERSION = "0.0.0.1";

	/**
	 * Create sets the screen to show the start scene
	 */
	@Override
	public void create()
	{
		setScreen(SceneManager.getInstance().startScene);
		File gameFolder = new File(GameData.GAME_FOLDER);
		boolean created = false;
		
		if(!gameFolder.exists()) {
			try {
				created = gameFolder.mkdirs();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			if(created) {
				System.out.println("New game folder created in home directory.");
			} else {
				System.out.println("Game folder creation failed, please check your permissions.");
			}
		} else {
			System.out.println("Game folder already exists in home directory.");
		}
	}

	/**
	 * This Method sets the screen to the gameScreen, We dispose the startScene, create a new gameScreen reset the screen which resets the booleans to the start 
	 * setting for all the relevant gamescreen objects.
	 * Finally we set the screen to gameScreen.
	 */
	public void setGameScreen()
	{
		GameData.EDITING = false;
		SceneManager.getInstance().startScene.dispose();
		gameScreen = new GameScreen();
		GameScreen.create();
		gameScreen.resetScreen();
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		setScreen(gameScreen);
	}
	/**
	 * This Method changes the screen to startScene. We dispose gameScreen.
	 */
	public void setMenuScreen()
	{
		GameData.EDITING = false;
		gameScreen.dispose();
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

		SceneManager.getInstance().startScene.addToStage();
		setScreen(SceneManager.getInstance().startScene);
	}
	
	/**
	 * This method changes the screen to the map editing UI and disposes of the startScene.
	 */
	public void setMapEditScreen() {
		GameData.EDITING = true;
		SceneManager.getInstance().startScene.dispose();
		gameScreen = new GameScreen();
		GameScreen.create();
		gameScreen.resetScreen();
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		setScreen(gameScreen);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render() {
		Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		super.render();

	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}