package com.TeamHEC.LocomotionCommotion.MapActors;

import com.TeamHEC.LocomotionCommotion.LocomotionCommotion;
import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_StartingSequence;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;
import com.TeamHEC.LocomotionCommotion.UI_Elements.SpriteButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Game_Map_StationBtn extends SpriteButton {

	public boolean exit = false;

	// Used to hold player1s selection:
	public static Game_Map_Station selectedStation, selectedP1;
	public static Station tempP1Station;

	public Game_Map_StationBtn(float x, float y, Texture texture)
	{
		super(x, y, texture);	
	}
	
	@Override
	protected void onClicked()
	{
		started = true;
	}
		
	@Override
	public void act(float delta){
		if(started){
			if(Game_StartingSequence.inProgress)
			{
				if(Game_StartingSequence.player1)
				{
					// Sets texture (could be done via listener?)
					
					selectedStation.texture = Game_Map_TextureManager.getInstance().p1Station;
					selectedStation.setOwned(true);
					Game_Map_Manager.hideInfoBox();
					
					tempP1Station = selectedStation.getStation();
					
					selectedStation.setTouchable(Touchable.disabled);
					selectedP1 = selectedStation;
					selectedStation = null;
					
					Game_StartingSequence.selectLabel.setVisible(true);
					Game_StartingSequence.getStartedWindow.setVisible(true);
					Game_StartingSequence.selectLabel.setText(LocomotionCommotion.player2name + " please select your start station!");
					Game_StartingSequence.player1 = false;
				}
				else	
				{
					selectedStation.texture=Game_Map_TextureManager.getInstance().p2Station;
					selectedStation.setOwned(true);
					Game_Map_Manager.hideInfoBox();
					
					selectedP1.setTouchable(Touchable.enabled);
					
					Game_StartingSequence.selectLabel.setVisible(false);
					
					GameScreen.createCoreGame(tempP1Station, selectedStation.getStation());
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
			else if(selectedStation.getStation().isFaulty())
			{
				GameScreen.game.getPlayerTurn().getShop().repairStation(selectedStation.getStation(), false);
				Game_Map_Manager.hideInfoBox();
			}
			else
			{
				//Buy Stations in game
				GameScreen.game.getPlayerTurn().purchaseStation(selectedStation.getStation());
				Game_Map_Manager.hideInfoBox();
			}
		}
		started = false;
	}
}