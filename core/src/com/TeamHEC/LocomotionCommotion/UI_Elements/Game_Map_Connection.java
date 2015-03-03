package com.TeamHEC.LocomotionCommotion.UI_Elements;

import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Goal.PlayerGoals;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class Game_Map_Connection extends Actor {
	
	public boolean highlighted = false;
	private ShapeRenderer shapeRend = new ShapeRenderer();
	private MapObj startPoint, endPoint;
	
	public Game_Map_Connection() {	}
	
	public void create(Stage stage) {		
		
	}
	
	public void draw() {
		shapeRend.setAutoShapeType(true);
		shapeRend.begin();
		
		for(int i=0; i<WorldMap.getInstance().stationsList.size(); i++) {
			startPoint = WorldMap.getInstance().stationsList.get(i);
			
			shapeRend.set(ShapeRenderer.ShapeType.Filled);
			for(Connection line : startPoint.connections) {
				endPoint = line.getDestination();
				switch(line.getColour()) {
				case Yellow:
					shapeRend.setColor(1, 1, 0, 1);
					break;
				case Red:
					shapeRend.setColor(1, 0, 0, 1);
					break;
				case Brown:
					shapeRend.setColor(0.54f, 0.27f, 0.07f, 1);
					break;
				case Black:
					shapeRend.setColor(0, 0, 0, 1);
					break;
				case Blue:
					shapeRend.setColor(0, 0, 1, 1);
					break;
				case Purple:
					shapeRend.setColor(0.5f, 0, 0.5f, 1);
					break;
				case Green:
					shapeRend.setColor(0, 1, 0, 1);
					break;
				case Orange:
					shapeRend.setColor(1, 0.64f, 0, 1);
					break;
				}
				shapeRend.rectLine(startPoint.x + 20, startPoint.y + 20, endPoint.x + 20, endPoint.y + 20, 5);
			}
		}
		
		shapeRend.end();
	}

}
