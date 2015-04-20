package com.TeamHEC.LocomotionCommotion.Game;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.MapActors.Game_Map_Manager;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MapInputProcessor implements InputProcessor {
	private int currentX, currentY, touchDist;
	private float scaleX, scaleY, scaleZ;
	
	public MapInputProcessor() {
		scaleX = 1680 / GameScreen.getMapStage().getViewport().getScreenWidth();
		scaleY = 1050 / GameScreen.getMapStage().getViewport().getScreenHeight();
		scaleZ = ((OrthographicCamera) GameScreen.getMapStage().getCamera()).zoom;
		
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
				
				for(int i=0; i < cs.length; i++) {
					Connection c = cs[i];
					
					int x1, x2, y1, y2, tolerance;
					double m, a, predictedY;
					
					tolerance = 50;
					x1 = (int) (c.getStartMapObj().x * scaleX * scaleZ);
					x2 = (int) (c.getDestination().x * scaleX * scaleZ);
					y1 = (int) (c.getStartMapObj().y * scaleY * scaleZ);
					y2 = (int) (c.getDestination().y * scaleY * scaleZ);
					
					if(x2 != x1) {
						m = (y2 - y1)/(x2 - x1);
						
						a = y1 - (m * x1);
						
						predictedY = (m * screenX) + a;
						
						if((screenY + tolerance) > predictedY && predictedY > (screenY - tolerance)) {
							if(Math.max(x1, x2) > screenX && screenX > Math.min(x1, x2)) {
								if(Math.max(y1, y2) > screenY && screenY > Math.min(y1, y2)) {
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
				Game_Map_Manager.addNewJunction((int) (screenX * scaleX * scaleZ), (int) (GameData.RESOLUTION_HEIGHT-screenY * scaleY * scaleZ));
			} else if(Game_Map_Manager.getTool() == "connection") {
				WarningMessage.fireWarningWindow("Tool", "Connection tool selected, registered location X:" + screenX + " Y:" + screenY);
			}
		}
		touchDist = 0;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int dX = screenX - currentX;
		int dY = screenY - currentY;
		
		currentX = screenX;
		currentY = screenY;
		
		GameScreen.getMapStage().getCamera().position.x -= (dX * scaleX * scaleZ);
		GameScreen.getMapStage().getCamera().position.y += (dY * scaleY * scaleZ);
		
		touchDist += Math.abs(dX);
		touchDist += Math.abs(dY);
		
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
		scaleZ += ((float) amount)/10;
		if(scaleZ < 0.2f) {
			((OrthographicCamera) GameScreen.getMapStage().getCamera()).zoom = 0.2f;
			scaleZ = 0.2f;
		} else if(scaleZ > 3.0f) { 
			((OrthographicCamera) GameScreen.getMapStage().getCamera()).zoom = 3.0f;
			scaleZ = 3.0f;
		}
		
		return false;
	}

}
