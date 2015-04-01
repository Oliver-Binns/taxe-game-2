package com.TeamHEC.LocomotionCommotion.MapActors;

import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.StationListener;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Game_Map_Station extends Game_Map_MapObj implements StationListener {

	public boolean owned;
	private Station station;
	private Label nameLabel;

	public float offset = 0;

	public Game_Map_Station(Station station, float actorX, float actorY)
	{
		super(actorX, actorY, Game_Map_TextureManager.getInstance().station, Game_Map_TextureManager.getInstance().stationx2);

		this.station = station;
		//this.nameWidth = this.station.getName().length() * 20;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/UbuntuMono-R.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 25;

		BitmapFont font = generator.generateFont(parameter);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		LabelStyle style = new LabelStyle();
		style.font = font;
		
		style.fontColor = Color.BLACK;
		nameLabel = new Label(station.getName().toUpperCase(), style);
		
		this.owned = false;
		station.register(this);
	}

	@Override
	public MapObj getMapObj()
	{
		return station;
	}
	
	@Override
	public Label getLabel() {
		return nameLabel;
	}
	
	/**
	 * 
	 * @param station the station that this is the button for
	 * @param player
	 */
	public void updateButton(Station station, Player player){
		if(station.isFaulty()){
			if(player == null)
			{
				texture = Game_Map_TextureManager.getInstance().stationFaulty;
				toggleTexture1 = Game_Map_TextureManager.getInstance().stationFaulty;
				toggleTexture2 = Game_Map_TextureManager.getInstance().stationx2Faulty;
			}
	
			else if(player.isPlayer1)
			{
				texture = Game_Map_TextureManager.getInstance().p1StationFaulty;
				toggleTexture1 = Game_Map_TextureManager.getInstance().p1StationFaulty;
				toggleTexture2 = Game_Map_TextureManager.getInstance().p1Stationx2Faulty;
			}
			else
			{
				texture = Game_Map_TextureManager.getInstance().p2StationFaulty;
				toggleTexture1 = Game_Map_TextureManager.getInstance().p2StationFaulty;
				toggleTexture2 = Game_Map_TextureManager.getInstance().p2Stationx2Faulty;
			}
		}
		else{
			if(player == null)
			{
				texture = Game_Map_TextureManager.getInstance().station;
				toggleTexture1 = Game_Map_TextureManager.getInstance().station;
				toggleTexture2 = Game_Map_TextureManager.getInstance().stationx2;
			}
	
			else if(player.isPlayer1)
			{
				texture = Game_Map_TextureManager.getInstance().p1Station;
				toggleTexture1 = Game_Map_TextureManager.getInstance().p1Station;
				toggleTexture2 = Game_Map_TextureManager.getInstance().p1Stationx2;
			}
			else
			{
				texture = Game_Map_TextureManager.getInstance().p2Station;
				toggleTexture1 = Game_Map_TextureManager.getInstance().p2Station;
				toggleTexture2 = Game_Map_TextureManager.getInstance().p2Stationx2;
			}
		}
	}
	/**
	 * @deprecated replaced with updateButton();
	 */
	@Override
	public void ownerChanged(Station station, Player player)
	{
		if(player == null)
		{
			texture = Game_Map_TextureManager.getInstance().station;
			toggleTexture1 = Game_Map_TextureManager.getInstance().station;
			toggleTexture2 = Game_Map_TextureManager.getInstance().stationx2;
		}

		else if(player.isPlayer1)
		{
			texture = Game_Map_TextureManager.getInstance().p1Station;
			toggleTexture1 = Game_Map_TextureManager.getInstance().p1Station;
			toggleTexture2 = Game_Map_TextureManager.getInstance().p1Stationx2;
		}
		else
		{
			texture = Game_Map_TextureManager.getInstance().p2Station;
			toggleTexture1 = Game_Map_TextureManager.getInstance().p2Station;
			toggleTexture2 = Game_Map_TextureManager.getInstance().p2Stationx2;
		}
	}

	@Override
	protected void onClicked()
	{
		super.onClicked();
		Game_Map_StationBtn.selectedStation = this;
		if(!highlighted)
		{	
			highlighted = true;
			if(!GameScreenUI.routingModeWindow.isVisible())
				showInfoBox();
		}
		else
		{
			highlighted = false;
			hideInfoBox();
		}
	}

	public void showInfoBox()
	{
		
		for(int i = Game_Map_Manager.stagestart;i <= Game_Map_Manager.stagestart + Game_Map_Manager.mapActors-1; i++)	
		{ 	
			if (i > GameScreen.getMapStage().getActors().size-1){
			}
			else
			{
				GameScreen.getMapStage().getActors().get(i).setVisible(true);
			}
		}
		// Sets the labels to info from each station:
		Game_Map_Manager.stationLabelName.setText(station.getName());
		Game_Map_Manager.stationLabelCost.setText(String.format("%d", station.getBaseValue() ));
		Game_Map_Manager.stationLabelFuel.setText(String.format("%d * %s", station.getResourceType().getValue(), station.getResourceString()));

		Game_Map_Manager.moveInfoBox(this.actorX-180, this.actorY-80);


	}
	public void hideInfoBox(){
		for(int i=Game_Map_Manager.stagestart; i<=Game_Map_Manager.stagestart +Game_Map_Manager.mapActors-1;i++)	
		{ 	
			if (i > GameScreen.getMapStage().getActors().size-1){
			}
			else
			{
				GameScreen.getMapStage().getActors().get(i).setVisible(false);
			}
		}		
	}

	public void setOwned(Boolean b)
	{
		this.owned =b;
	}
}
