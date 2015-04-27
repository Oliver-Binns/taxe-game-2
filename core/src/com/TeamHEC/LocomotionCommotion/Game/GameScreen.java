package com.TeamHEC.LocomotionCommotion.Game;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.LocomotionCommotion;
import com.TeamHEC.LocomotionCommotion.Card.Game_CardHand;
import com.TeamHEC.LocomotionCommotion.Goal.GoalMenu;
import com.TeamHEC.LocomotionCommotion.Goal.PlayerGoals;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.Junction;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.MapActors.Game_Map_Manager;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Train.TrainDepotUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_PauseMenu;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_Shop;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_StartingSequence;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.TeamJKG.LocomotionCommotion.Game.NewGame;
import com.TeamJKG.LocomotionCommotion.Replay.ReplayGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;
/**
 * 
 * @author Robert Precious <rp825@york.ac.uk>
 * @author Sam Watkins <sw1308@york.ac.uk>
 * 
 * Game Screen is the Screen that handles everything in the game screen.
 * First we sort the Camera- create the stage, create the camera and set the dimensions and update
 * Then we create all the managers- these manage the actors and they are split up in to separate menu sections.
 *
 * @param stage - The stage for the actors.
 * @param sb - The spritebatch (needed for textbox's etc)
 * @param camera - the camera
 * 
 * we have methods:
 * create - explained above
 * render - updates the camera, lets the actors act and draws the screen
 * resize - updates the screen size when window is resized
 * show - just calls create
 * dispose - disposes of the stage
 * getStage and setStage - getters and setters for stage
 * resetScreen- used when reentering the screen- it resets all the settings.
 * 
 */
public class GameScreen implements Screen {
	public static CoreGame game;
	private static Stage stage;
	private static Stage mapStage;
	public static SpriteBatch sb;
	public static Game_Map_Manager mapManager;
	private static ShapeRenderer shapeRend = new ShapeRenderer();
	public static MapInputProcessor mapInput;
	
	/**
	 * 
	 */
	public static void create(){
		//Set up stage camera
		stage = new Stage(new FitViewport(GameData.RESOLUTION_WIDTH, GameData.RESOLUTION_HEIGHT));
		mapStage = new Stage(new FitViewport(GameData.RESOLUTION_WIDTH, GameData.RESOLUTION_HEIGHT));
		Camera camera = stage.getCamera();
		camera.update();
		
		Camera mapCamera = mapStage.getCamera();
		mapCamera.update();
		
		//Instantiate the Managers
		mapInput = new MapInputProcessor();
		InputMultiplexer multiInput = new InputMultiplexer();
		
		multiInput.addProcessor(getStage());
		multiInput.addProcessor(getMapStage());
		multiInput.addProcessor(mapInput);
		Gdx.input.setInputProcessor(multiInput);
		
		mapStage.getActors().clear();
		stage.getActors().clear();
		
		mapManager = new Game_Map_Manager();
		mapManager.create(getMapStage());
		
		GameScreenUI actorManager = new GameScreenUI();
		actorManager.create(getStage());
		
		Game_PauseMenu pauseMenu= new Game_PauseMenu();
		pauseMenu.create(getStage());
		
		WarningMessage warningMessage = new WarningMessage();
		warningMessage.create(getStage());
		
		if(!GameData.EDITING) {
			Game_CardHand cardHand = new Game_CardHand();
			cardHand.create(getStage());
	
			TrainDepotUI trainDepot = new TrainDepotUI();
			trainDepot.create(getStage());
	
			GoalMenu goalScreenManager = new GoalMenu();
			goalScreenManager.create(getStage());
			
			PlayerGoals ticketManager = new PlayerGoals();
			ticketManager.create(getStage());	
			
			Game_StartingSequence startgameManager = new Game_StartingSequence();
			startgameManager.create(getStage());
			
			Game_Shop shop = new Game_Shop();
			shop.create(getStage());
		}
	}
	
	public static JSONObject getJSONData()
	{
		try{
			FileReader in = new FileReader(GameData.SAVE_FOLDER + "/save.loco");
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(in);
			JSONObject jsonObject = (JSONObject) obj;
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void createCoreGame(Station p1Station, Station p2Station)
	{
		if(LocomotionCommotion.isReplay){
			JSONObject gameData = (JSONObject)getJSONData().get("turns");
			game = new ReplayGame(LocomotionCommotion.player1name, LocomotionCommotion.player2name, gameData);
		}
		else{
			game = new NewGame(LocomotionCommotion.player1name, LocomotionCommotion.player2name, p1Station, p2Station, LocomotionCommotion.turnChoice);
		}
		GameScreenUI.refreshResources();
	}
	
	@Override
	public void render(float delta) {
		getStage().getCamera().update();
		getMapStage().getCamera().update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,  GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRend.setProjectionMatrix(getMapStage().getCamera().combined);
		
		//Draw coloured track line between each station connection
		shapeRend.begin(ShapeRenderer.ShapeType.Filled);
		
		for(MapObj startPoint : WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).mapObjList()) {
			float alpha = 1.0f;
			
			if(Game_Map_Manager.connectionPlacing) {
				shapeRend.setColor(0,0,0,1);
				
				shapeRend.rectLine(Game_Map_Manager.conObj1.x + 20, Game_Map_Manager.conObj1.y + 20, mapInput.mouseCoords.first, mapInput.mouseCoords.second, 5);
			}
			
			for(Connection line : startPoint.connections) {
				alpha = 1.0f;
				
				//Make connections translucent if they lead to a locked object
				if(startPoint.isLocked() || line.getDestination().isLocked()) {
					alpha = 0.2f;
				}
				
				if(line.selected) {
					shapeRend.setColor(0,0,0,alpha);
					shapeRend.rectLine(startPoint.x + 20, startPoint.y + 20, line.getDestination().x + 20, line.getDestination().y + 20, 10);
				}
				switch(line.getColour()) {
				case Yellow:
					shapeRend.setColor(1, 0.76f, 0.03f, alpha);
					break;
				case Red:
					shapeRend.setColor(1, 0.34f, 0.13f, alpha);
					break;
				case Brown:
					shapeRend.setColor(0.47f, 0.33f, 0.28f, alpha);
					break;
				case Black:
					shapeRend.setColor(0, 0, 0, alpha);
					break;
				case Blue:
					shapeRend.setColor(0, 0.74f, 0.83f, alpha);
					break;
				case Purple:
					shapeRend.setColor(0.61f, 0.15f, 0.69f, alpha);
					break;
				case Green:
					shapeRend.setColor(0.18f, 0.62f, 0.18f, alpha);
					break;
				case Orange:
					shapeRend.setColor(0.96f, 0.49f, 0, alpha);
					break;
				}
				shapeRend.rectLine(startPoint.x + 20, startPoint.y + 20, line.getDestination().x + 20, line.getDestination().y + 20, 5);
			}
		}
		
		for(Station startPoint : WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).stationList()) {
			int nameWidth = startPoint.getActor().labelWidth;
			float alpha = 1.0f;
			
			//Make station translucent if they are locked
			if(startPoint.isLocked()) {
				alpha = 0.2f;
			}
			
			//Draw outlines over stations and labels
			
			if(!(startPoint.getOwner() == null)) {
				Player player = startPoint.getOwner();
				
				if(player.isPlayer1) {
					shapeRend.setColor(0, 0.74f, 0.83f, 0.8f);
				} else {
					shapeRend.setColor(0.96f, 0.49f, 0, 0.8f);
				}
				if(startPoint.getActor().highlighted) {
					shapeRend.circle(startPoint.x + 20, startPoint.y + 20, 19.0f);
				} else {
					shapeRend.circle(startPoint.x + 20, startPoint.y + 20, 16.0f);
				}
			}
			
			shapeRend.setColor(0, 0, 0, alpha);
			shapeRend.rect(startPoint.x - nameWidth/2 - 5, startPoint.y + 42, nameWidth + 10, 46);
			if(startPoint.getActor().highlighted) {
				shapeRend.circle(startPoint.x + 20, startPoint.y + 20, 16.0f);
			} else {
				shapeRend.circle(startPoint.x + 20, startPoint.y + 20, 13.0f);
			}
			
			//Draw label and station placeholders/icons
			shapeRend.setColor(1, 1, 1, alpha);
			shapeRend.rect(startPoint.x - nameWidth/2 - 2, startPoint.y + 45, nameWidth + 4, 40);
			
			if(startPoint.isFaulty()) {
				shapeRend.setColor(1, 0, 0, alpha);
			}
			shapeRend.circle(startPoint.x + 20, startPoint.y + 20, 10.0f);
		}
		
		for(Junction junc : WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).junctionList()) {
			float alpha = 1.0f;
			
			//Make junction translucent if they are locked
			if(junc.isLocked()) {
				alpha = 0.2f;
			}
			
			//Draw outlines over junctions
			shapeRend.setColor(0, 0, 0, alpha);
			if(junc.getActor().highlighted) {
				shapeRend.rect(junc.x + 4, junc.y + 4, 32, 32);
			} else {
				shapeRend.rect(junc.x + 7, junc.y + 7, 26, 26);
			}
			
			//Draw station icons
			shapeRend.setColor(1, 1, 1, alpha);
			shapeRend.rect(junc.x + 10, junc.y + 10, 20, 20);
		}
		
		shapeRend.end();
		
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		getMapStage().act(Gdx.graphics.getDeltaTime());
		getStage().act(Gdx.graphics.getDeltaTime());
		
		getMapStage().draw();
		getStage().draw();
	}

	@Override
	public void resize(int width, int height) {
	    // use true here to center the camera
	    // that's what you probably want in case of a UI
		
	    stage.getViewport().update(width, height, true);
	    mapStage.getViewport().update(width, height, true);
	    mapInput.resize();
	}

	@Override
	public void show() {
		GameScreen.create();

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
	public static void resetStage(){
	}

	@Override
	public  void dispose() {
		getStage().dispose();
		getMapStage().dispose();
		getStage().getActors().clear();
		getMapStage().getActors().clear();
	}

	public static Stage getStage() {
		return stage;
	}
	
	public static Stage getMapStage() {
		return mapStage;
	}
	
	public static ShapeRenderer getRend() {
		return shapeRend;
	}

	public static void setStage(Stage stage) {
		GameScreen.stage = stage;
		stage.setViewport(new FitViewport(1680, 1050));
	}
	/**
	 * Reset Screen - Sets all the boolean to start values and clears actors and resets the map. 
	 */
	public void  resetScreen(){
		Game_Map_Manager.infoVisible= false;
		Game_PauseMenu.actorManager.open = false;
		GameScreenUI.resourcebarexpanded =false;
		
		if(!GameData.EDITING) {
			PlayerGoals.open = false;
			Game_Shop.actorManager.open = false;
			TrainDepotUI.actorManager.open = false;
			GoalMenu.open= false;
			
			//CARDS
			Game_CardHand.actorManager.open=false;
			Game_CardHand.actorManager.cardactors.clear();;
			
			//Map
			Game_StartingSequence.reset();
		}
		
		Game_Map_Manager.resetMap();
	}
}
