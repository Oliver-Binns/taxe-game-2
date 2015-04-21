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

public class MapInputProcessor implements InputProcessor {
	private int currentX, currentY, touchDist, offsetX, offsetY, offsetWX, offsetWY;
	private double scaleY, scaleX, scaleVX, scaleVY, cameraZoom;
	
	public MapInputProcessor() {
		resize();
		
		offsetWX = 0;
		offsetWY = 0;
		currentX = (int) (GameScreen.getMapStage().getCamera().position.x * scaleX);
		currentY = (int) (GameScreen.getMapStage().getCamera().position.y * scaleY);
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
		currentX = screenX;
		currentY = screenY;
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(GameData.EDITING && touchDist <= 10) {
			if(Game_Map_Manager.getTool() == "None") {
				Connection[] cs = WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).connectionList();
				Pair<Integer, Integer> newCoords = convert2World(screenX, screenY);
				
				screenX = newCoords.first;
				screenY = newCoords.second;
				
				for(int i=0; i < cs.length; i++) {
					Connection c = cs[i];
					
					int x1, x2, y1, y2, tolerance;
					double m, a, predictedY;
					
					tolerance = 15;
					x1 = (int) c.getStartMapObj().x;
					x2 = (int) c.getDestination().x;
					y1 = (int) c.getStartMapObj().y;
					y2 = (int) c.getDestination().y;
					
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
				WarningMessage.fireWarningWindow("Tool", "Station tool selected, registered location X:" + screenX + " Y:" + screenY);
			} else if(Game_Map_Manager.getTool() == "junction") {
				Game_Map_Manager.addNewJunction((int) (screenX * scaleX * cameraZoom), (int) (GameData.RESOLUTION_HEIGHT-screenY * scaleY * cameraZoom));
			} else if(Game_Map_Manager.getTool() == "connection") {
				WarningMessage.fireWarningWindow("Tool", "Connection tool selected, registered location X:" + screenX + " Y:" + screenY);
			}
		}
		touchDist = 0;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		float dX = screenX - currentX;
		float dY = screenY - currentY;
		
		currentX = screenX;
		currentY = screenY;
		
		touchDist += Math.abs(dX);
		touchDist += Math.abs(dY);
		
		dX *= (scaleVX * cameraZoom);
		dY *= (scaleVY * cameraZoom);
		
		GameScreen.getMapStage().getCamera().position.x -= dX;
		GameScreen.getMapStage().getCamera().position.y += dY;
		
		offsetWX -= dX;
		offsetWY += dY;
		
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
		offsetX = GameScreen.getMapStage().getViewport().getLeftGutterWidth();
		offsetY = GameScreen.getMapStage().getViewport().getBottomGutterHeight();
		scaleX = (float) GameScreen.getMapStage().getViewport().getScreenWidth() / (Gdx.graphics.getWidth() - (offsetX * 2));
		scaleY = (float) GameScreen.getMapStage().getViewport().getScreenHeight() / (Gdx.graphics.getHeight() - (offsetY * 2));
		scaleVX = GameScreen.getMapStage().getViewport().getWorldWidth() / GameScreen.getMapStage().getViewport().getScreenWidth();
		scaleVY = GameScreen.getMapStage().getViewport().getWorldHeight() / GameScreen.getMapStage().getViewport().getScreenHeight();
		cameraZoom = ((OrthographicCamera) GameScreen.getMapStage().getViewport().getCamera()).zoom;
	}
	
	public Pair<Integer, Integer> convert2World(int x, int y) {
		Vector3 coords = ((OrthographicCamera) GameScreen.getMapStage().getCamera()).unproject(new Vector3(x, y, 1));
		
		return new Pair<Integer, Integer>((int) coords.x, (int) coords.y);
	}
	
	public Pair<Integer, Integer> convert2Viewport(int x, int y) {
		x -= offsetX;
		y -= offsetY;
		
		x *= scaleX;
		y *= scaleY;
		
		y = GameScreen.getMapStage().getViewport().getScreenHeight() - y;
		
		x *= scaleVX;
		y *= scaleVY;
		
		Integer viewX = Integer.valueOf(x);
		Integer viewY = Integer.valueOf(y);
		
		return new Pair<Integer, Integer>(viewX, viewY);
	}
}
