package com.TeamHEC.LocomotionCommotion.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 */

public class Scene implements Screen{
	
	public Stage stage;
	public static Camera camera;
	public static int screenX = 1680;
	public static int screenY = 1050;
	
	public static Array<Actor> actors;
	
	public Scene()
	{
		stage = new Stage(new StretchViewport(screenX, screenY));
		camera = stage.getCamera();
		camera.update();
       
		actors = new Array<Actor>();
	}
	
	public void addToStage()
	{
		for (Actor a : actors)
		{
			a.setTouchable(Touchable.enabled);
			stage.addActor(a);
		}
		
		Gdx.input.setInputProcessor(stage);
	}
	
	public void removeFromStage()
	{
		for (Actor a : actors)
		{
			stage.getActors().removeValue(a, true);
		}
	}
	
	public void setVisibility(boolean visible)
	{
		for (Actor a : actors)
		{
			a.setVisible(visible);
		}
	}
	
	public void setActorsTouchable(boolean touchable)
	{
		for (Actor a : actors)
		{
			if(touchable)
				a.setTouchable(Touchable.enabled);
			else
				a.setTouchable(Touchable.disabled);
		}
	}

	/**
	 * Can be used to change or animate the cameras position
	 * @param x New x coordinate
	 * @param y New y coordinate
	 */
	public void changeCam(int x,int y)
	{
		stage.getCamera().translate(x, y, 0);
	}

	@Override
	public void render(float delta) {
		stage.getCamera().update();
		
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	    // use true here to center the camera
	    // that's what you probably want in case of a UI
	    stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose()
	{
		stage.clear();
	}

	@Override
	public void show()
	{
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
	}
}
