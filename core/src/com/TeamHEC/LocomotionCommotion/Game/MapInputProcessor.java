package com.TeamHEC.LocomotionCommotion.Game;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.MapActors.Game_Map_Manager;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MapInputProcessor implements InputProcessor {
	private int currentX, currentY, touchDist;
	private float scaleX, scaleY, scaleZ;
	int offsetX, offsetY = 0;
	int borderOffsetX, borderOffsetY;
	
	public MapInputProcessor() {
		scaleX = 1680.0f / (float) Gdx.graphics.getWidth();
		scaleY = 1050.0f / (float) Gdx.graphics.getHeight();
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
			Connection[] cs = WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).connectionList();
						
			System.out.println(scaleX);
			System.out.println(scaleY);
			System.out.println(GameScreen.getMapStage().getViewport().getScreenHeight());
			System.out.println(GameScreen.getMapStage().getViewport().getScreenWidth());
			
			screenY = (int) (GameScreen.getMapStage().getViewport().getScreenHeight()) - screenY;
			screenY += borderOffsetY;
			screenX += borderOffsetX;
			screenY *= scaleZ * scaleY;
			screenX *= scaleZ * scaleX;
			screenY += offsetY;
			screenX += offsetX;
			
			//System.out.println(screenX);
			//System.out.println(screenY);
						
			for(int i=0; i < cs.length; i++) {
				Connection c = cs[i];
				
				int x1, x2, y1, y2, tolerance;
				double m, a, predictedY;
				
				tolerance = 15;
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
		
		offsetX -= dX * scaleZ * scaleX;
		offsetY += dY * scaleZ * scaleY;
		
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
	
	public void resize() {
		scaleX = 1680.0f / (float) Gdx.graphics.getWidth();
		scaleY = 1050.0f / (float) Gdx.graphics.getHeight();
		borderOffsetX = (Gdx.graphics.getWidth() - GameScreen.getMapStage().getViewport().getScreenWidth()) / 2;
		borderOffsetY = (Gdx.graphics.getHeight() - GameScreen.getMapStage().getViewport().getScreenHeight()) / 2;
	}

}
