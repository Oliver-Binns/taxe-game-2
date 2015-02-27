package com.TeamHEC.LocomotionCommotion.MapActors;

import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Game_Map_MapObj extends Actor{
	
	public Texture texture, toggleTexture1, toggleTexture2;
	public float actorX, actorY;
	public boolean started = false, highlighted = false;
	
	// Used for adjacent MapObjs in route:
	private boolean routeAvailable = false;
	private Train routeTrain;
	private Connection routeConnection;
	
	public float offset = 0;
	
	public Game_Map_MapObj(float x, float y, Texture texture, Texture toggleTexture2)
	{
		actorX = x;
		actorY = y;
		this.texture = texture;
		toggleTexture1 = texture;
		this.toggleTexture2 = toggleTexture2;
		
		setBounds(actorX, actorY, texture.getWidth(), texture.getHeight());
		 
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				((Game_Map_MapObj)event.getTarget()).started = true;
				((Game_Map_MapObj)event.getTarget()).onClicked();
				return true;
			}
		});
		
		addListener(new InputListener(){
			public void enter(InputEvent event, float x, float y, int pointer, Actor Game_Map_Station) {
				((Game_Map_MapObj)event.getTarget()).toggleHighlight(true);			
			}
		});
		addListener(new InputListener(){
			public void exit(InputEvent event, float x, float y, int pointer, Actor Game_Map_Station) {
				((Game_Map_MapObj)event.getTarget()).toggleHighlight(false);	
			}
		});
	}
	
	// Is overriden in Game_Map_Station
	public void showInfoBox(){}
	public void hideInfoBox(){}
	
	public boolean routeAvailable()
	{
		return routeAvailable;
	}
	
	public void setRouteAvailable(boolean available)
	{
		routeAvailable = available;
	}
	
	public void setRouteAvailable(Train train, Connection connection)
	{
		routeAvailable = true;
		routeTrain = train;
		routeConnection = connection;
	}
	
	public Train getRouteTrain()
	{
		return routeTrain;
	}
	
	public Connection getRouteConnection()
	{
		return routeConnection;
	}
	
	protected void onClicked()
	{
		if(routeAvailable())
		{
			getRouteTrain().route.addConnection(getRouteConnection());			
		}
	}
	
	public void toggleHighlight(boolean highlighted)
	{
		if(highlighted)
		{
			texture = toggleTexture2;
			offset = -2.5f;
		}
		else
		{
			texture = toggleTexture1;
			offset = 0;
		}
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		batch.draw(this.texture, actorX + offset, actorY + offset);
	}
}
