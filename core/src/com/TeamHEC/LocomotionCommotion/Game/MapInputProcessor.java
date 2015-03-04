package com.TeamHEC.LocomotionCommotion.Game;

import com.badlogic.gdx.InputProcessor;

public class MapInputProcessor implements InputProcessor {
	private int currentX, currentY;
	private float scaleX, scaleY;
	
	public MapInputProcessor() {
		scaleX = 1680 / GameScreen.getMapStage().getViewport().getScreenWidth();
		scaleY = 1050 / GameScreen.getMapStage().getViewport().getScreenHeight();
		
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
		
		GameScreen.getMapStage().getCamera().position.x -= (dX * scaleX);
		GameScreen.getMapStage().getCamera().position.y += (dY * scaleY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
