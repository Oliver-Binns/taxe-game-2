package com.TeamHEC.LocomotionCommotion.MapActors;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.LocomotionCommotion;
import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_StartingSequence;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;
import com.TeamHEC.LocomotionCommotion.UI_Elements.SpriteButton;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.TeamJKG.LocomotionCommotion.Game.NewGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Game_Map_StationBtn extends SpriteButton {

	public boolean exit = false;
	private boolean altFunc;

	// Used to hold player1s selection:
	public static Game_Map_MapObj selectedStation, selectedP1;
	public static Station tempP1Station;

	public Game_Map_StationBtn(float x, float y, Texture texture, boolean altButton)
	{
		super(x, y, texture);
		
		altFunc = altButton;
	}
	
	@Override
	protected void onClicked()
	{
		if(!GameData.EDITING) {
			started = true;
		}
	}
		
	@Override
	public void act(float delta){
		if(started) {
			if(!altFunc) {
				function(delta);
			} else {
				altFunction(delta);
			}
			started = false;
		}
	}
	
	private void function(float delta) {
		if(Game_StartingSequence.inProgress)
		{
			if(Game_StartingSequence.player1)
			{
				// Sets texture (could be done via listener?)
				if(selectedStation.getMapObj().isLocked()) {
					WarningMessage.fireWarningWindow("Station Locked!", "Please choose a station that is not locked.");
				} else {
					selectedStation.texture = Game_Map_TextureManager.getInstance().p1Station;
					selectedStation.setOwned(true);
					Game_Map_Manager.hideInfoBox();
					Game_Map_Manager.hideJunctionInfo();
					
					tempP1Station = (Station) selectedStation.getMapObj();
					
					selectedStation.setTouchable(Touchable.disabled);
					selectedP1 = selectedStation;
					selectedStation = null;
					
					Game_StartingSequence.selectLabel.setVisible(true);
					Game_StartingSequence.getStartedWindow.setVisible(true);
					Game_StartingSequence.selectLabel.setText(LocomotionCommotion.player2name + " please select your start station!");
					Game_StartingSequence.player1 = false;
				}
			}
			else	
			{
				if(selectedStation.getMapObj().isLocked()) {
					WarningMessage.fireWarningWindow("Station Locked!", "Please choose a station that is not locked.");
				} else {
					selectedStation.texture=Game_Map_TextureManager.getInstance().p2Station;
					selectedStation.setOwned(true);
					Game_Map_Manager.hideInfoBox();
					Game_Map_Manager.hideJunctionInfo();
					
					selectedP1.setTouchable(Touchable.enabled);
					
					Game_StartingSequence.selectLabel.setVisible(false);
					
					GameScreen.createCoreGame(tempP1Station, (Station) selectedStation.getMapObj());
					Game_StartingSequence.startGame();
					GameScreenUI.refreshResources();
					Game_StartingSequence.inProgress = false;
					
					Game_StartingSequence.selectLabel.setVisible(true);
					Game_StartingSequence.getStartedWindow.setVisible(true);
					Game_StartingSequence.getStartedWindow.setX(130);
					Game_StartingSequence.getStartedWindow.setTexture(Game_TextureManager.getInstance().game_start_getstartedwindow2);
					
					Game_StartingSequence.selectLabel.setText(GameScreen.game.getPlayerTurn().getName()+" select a new Goal from the Goal Screen!");
					Game_StartingSequence.selectLabel.setX(950);
				}
			}
		} else if(selectedStation.getMapObj().isLocked()) {
			GameScreen.game.getPlayerTurn().getShop().unlockStation(selectedStation.getMapObj(), false);
			Game_Map_Manager.hideInfoBox();
			Game_Map_Manager.hideJunctionInfo();
		} else if(((Station) selectedStation.getMapObj()).isFaulty()) {
			GameScreen.game.getPlayerTurn().getShop().repairStation((Station) selectedStation.getMapObj(), false);
			if(GameScreen.game instanceof NewGame){
				NewGame game = (NewGame)GameScreen.game;
				game.fixFault((Station)selectedStation.getMapObj());
			}
			Game_Map_Manager.hideInfoBox();
			Game_Map_Manager.hideJunctionInfo();
		} else if(selectedStation.getMapObj().getStation().getOwner() == GameScreen.game.getPlayerTurn()) {
			//Buy a new train!
			GameScreen.game.getPlayerTurn().getShop().buyNewTrain(selectedStation.getMapObj().getStation());
		}else {
			//Buy Stations in game
			GameScreen.game.getPlayerTurn().purchaseStation((Station) selectedStation.getMapObj());
			Game_Map_Manager.hideInfoBox();
			Game_Map_Manager.hideJunctionInfo();
		}
	}
	
	private void altFunction(float delta) {
		GameScreen.game.getPlayerTurn().getShop().lockStation(selectedStation.getMapObj(), false);
		Game_Map_Manager.hideInfoBox();
		Game_Map_Manager.hideJunctionInfo();
	}
}