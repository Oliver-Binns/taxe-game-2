package com.TeamHEC.LocomotionCommotion.Game;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Pair;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.MapActors.Game_Map_Manager;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MapInputProcessor implements InputProcessor {
	private int currentX, currentY, touchDist, offsetX, offsetY;
	private double scaleVX, scaleVY, cameraZoom;
	private Viewport VP = GameScreen.getMapStage().getViewport();
	
	public MapInputProcessor() {
		resize();
		
		currentX = (int) (GameScreen.getMapStage().getCamera().position.x);
		currentY = (int) (GameScreen.getMapStage().getCamera().position.y);
		touchDist = 0;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Pair<Integer, Integer> newCoords = convert2Viewport(screenX, screenY);
		
		currentX = newCoords.first;
		currentY = newCoords.second;
		
		Game_Map_Manager.deselectAll();
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(GameData.EDITING && touchDist <= 10) {
			Pair<Integer, Integer> newCoords = convert2World(screenX, screenY);
						
			screenX = newCoords.first;
			screenY = newCoords.second;
			
			if(Game_Map_Manager.getTool() == "None") {
				Connection[] cs = WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).connectionList();
								
				for(int i=0; i < cs.length; i++) {
					Connection c = cs[i];
					
					int x1, x2, y1, y2, tolerance;
					double m, a, predictedY;
					
					tolerance = 15;
					x1 = (int) c.getStartMapObj().x + 20;
					x2 = (int) c.getDestination().x + 20;
					y1 = (int) c.getStartMapObj().y + 20;
					y2 = (int) c.getDestination().y + 20;
					
					if(x2 != x1) {
						m = (y2 - y1)/(x2 - x1);
						
						a = y1 - (m * x1);
						
						predictedY = (m * screenX) + a;
						
						if((screenY + tolerance) > predictedY && predictedY > (screenY - tolerance)) {
							if(Math.max(x1, x2) > screenX && screenX > Math.min(x1, x2)) {
								if(Math.max(y1, y2) + tolerance > screenY && screenY > Math.min(y1, y2) - tolerance) {
									Game_Map_Manager.showEditConnection(c);
									touchDist = 0;
									return false;
								}
							}
						}
					} else {
						if((x1 + tolerance) > screenX && screenX > (x1 - tolerance)) {
							if(Math.max(y1, y2) > screenY && screenY > Math.min(y1, y2)) {
								Game_Map_Manager.showEditConnection(c);
								touchDist = 0;
								return false;
							}
						}
					} 
				}
			} else if(Game_Map_Manager.getTool() == "station") {
				Game_Map_Manager.addNewStation(screenX-20, screenY-20);
			} else if(Game_Map_Manager.getTool() == "junction") {
				Game_Map_Manager.addNewJunction(screenX-20, screenY-20);
			} else if(Game_Map_Manager.getTool() == "connection") {
				WarningMessage.fireWarningWindow("Tool", "Connection tool selected, registered location X:" + screenX + " Y:" + screenY);
			}
		}
		touchDist = 0;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Pair<Integer, Integer> newCoords = convert2Viewport(screenX, screenY);
		
		screenX = newCoords.first;
		screenY = newCoords.second;
		
		float dX = screenX - currentX;
		float dY = screenY - currentY;
		
		currentX = screenX;
		currentY = screenY;
		
		touchDist += Math.abs(dX);
		touchDist += Math.abs(dY);
		
		GameScreen.getMapStage().getCamera().position.x -= dX * cameraZoom;
		GameScreen.getMapStage().getCamera().position.y -= dY * cameraZoom;
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		((OrthographicCamera) GameScreen.getMapStage().getCamera()).zoom += ((float) amount)/10;
		cameraZoom += ((float) amount)/10;
		if(cameraZoom < 0.2f) {
			((OrthographicCamera) GameScreen.getMapStage().getCamera()).zoom = 0.2f;
			cameraZoom = 0.2f;
		} else if(cameraZoom > 3.0f) { 
			((OrthographicCamera) GameScreen.getMapStage().getCamera()).zoom = 3.0f;
			cameraZoom = 3.0f;
		}
		
		return false;
	}
	
	public void resize() {
		offsetX = VP.getLeftGutterWidth();
		offsetY = VP.getTopGutterHeight();
		scaleVX = VP.getWorldWidth() / VP.getScreenWidth();
		scaleVY = VP.getWorldHeight() / VP.getScreenHeight();
		cameraZoom = ((OrthographicCamera) GameScreen.getMapStage().getCamera()).zoom;
	}
	
	public Pair<Integer, Integer> convert2World(int x, int y) {		
		Vector3 coords = ((OrthographicCamera) GameScreen.getMapStage().getCamera()).unproject(new Vector3(x, y, 1), VP.getScreenX(), VP.getScreenY(), VP.getScreenWidth(), VP.getScreenHeight());
		
		return new Pair<Integer, Integer>((int) coords.x, (int) coords.y);
	}
	
	public Pair<Integer, Integer> convert2Viewport(int x, int y) {
		x -= offsetX;
		y -= offsetY;
		
		y = VP.getScreenHeight() - y;
		
		x *= scaleVX;
		y *= scaleVY;
		
		Integer viewX = Integer.valueOf(x);
		Integer viewY = Integer.valueOf(y);
		
		return new Pair<Integer, Integer>(viewX, viewY);
	}
}
