package com.TeamHEC.LocomotionCommotion.Game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MapInputProcessor implements InputProcessor {
	private int currentX, currentY;
	private float scaleX, scaleY, scaleZ;
	
	public MapInputProcessor() {
		scaleX = 1680 / GameScreen.getMapStage().getViewport().getScreenWidth();
		scaleY = 1050 / GameScreen.getMapStage().getViewport().getScreenHeight();
		scaleZ = ((OrthographicCamera) GameScreen.getMapStage().getCamera()).zoom;
		
		currentX = (int) (GameScreen.getMapStage().getCamera().position.x * scaleX);
		currentY = (int) (GameScreen.getMapStage().getCamera().position.y * scaleY);
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
		// TODO Auto-generated method stub
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
