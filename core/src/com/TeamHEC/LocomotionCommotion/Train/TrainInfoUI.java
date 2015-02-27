package com.TeamHEC.LocomotionCommotion.Train;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.MapActors.Game_Map_Manager;
import com.TeamHEC.LocomotionCommotion.MapActors.Game_Map_TextureManager;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Sprite;
import com.TeamHEC.LocomotionCommotion.UI_Elements.SpriteButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;

public class TrainInfoUI extends Sprite{
	
	public static Label name, speed, routeRemaining;
	public LabelStyle style;
	public SpriteButton planRoute;
	
	public Array<Actor> actors = new Array<Actor>();
	
	public Train train;
	public ArrayList<Connection> adjacent;
	
	public TrainInfoUI()
	{
		super(0, 0, Game_Map_TextureManager.getInstance().trainInfo);
		
		//Stuff for Labels
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gillsans.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 23;

		BitmapFont font = generator.generateFont(parameter); 
		generator.dispose();
		style = new LabelStyle();
		style.font = font;
		
		name = new Label(null, style);
		speed = new Label(null, style);
		routeRemaining = new Label(null, style);
		
		planRoute = new SpriteButton(0, 0, Game_Map_TextureManager.getInstance().trainInfoPlanRoute){
			
			@Override
			protected void onClicked()
			{
				if(train != null)
				if(GameScreen.game.getPlayerTurn() == train.getOwner())
				{
					Game_Map_Manager.enterRoutingMode();
					Game_Map_Manager.planBackground.setVisible(true);
					highlightAdjacent();
					makeVisible(false);
				}
			}
		};
		
		actors.add(name);
		actors.add(speed);
		actors.add(routeRemaining);
		actors.add(planRoute);
	}
	
	/**
	 * Makes the adjacent MapObjs to the Train clickable to add to route...
	 */
	public void highlightAdjacent()
	{
		adjacent = train.route.getAdjacentConnections();
		
		for(Connection c : adjacent)
		{
			c.getDestination().getActor().setRouteAvailable(train, c);
			c.getDestination().getActor().toggleHighlight(true);
			
			//train.route.showConnectionBlips(c);
		}
	}
	
	public void unhighlightAdjacent()
	{
		adjacent = train.route.getAdjacentConnections();
		
		for(Connection c : adjacent)
		{
			c.getDestination().getActor().setRouteAvailable(false);
			c.getDestination().getActor().toggleHighlight(false);
		}
	}
	
	public Array<Actor> getActors()
	{
		return actors;
	}
	
	public void showLabel(Train train)
	{
		this.train = train;
		
		float x = train.route.getTrainPos().x + 30;
		float y = train.route.getTrainPos().y - 50;
		
		this.setPosition(x, y);
		planRoute.setPosition(x + 22, y + 15);
		planRoute.refreshBounds();
		
		name.setPosition(x + 32, y + 142, Align.center);
		speed.setPosition(x + 93, y + 97, Align.center);
		routeRemaining.setPosition(x + 85, y + 57, Align.center);
		
		speed.setColor(Color.BLACK);
		routeRemaining.setColor(Color.BLACK);
		
		name.setText(train.getName());
		speed.setText(Integer.toString(train.getSpeed()));
		routeRemaining.setText(String.format("%.2f", train.route.getLengthRemaining()));
		
		train.route.updateRouteText();
		
		makeVisible(true);
	}
	
	public void makeVisible(boolean v)
	{
		this.setVisible(v);
		for(Actor a : actors)
		{
			a.setVisible(v);
		}
		
		if(train.getOwner() != GameScreen.game.getPlayerTurn())
			planRoute.setVisible(false);
	}
}
