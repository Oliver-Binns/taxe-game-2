package com.TeamHEC.LocomotionCommotion.MapActors;

import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.Junction;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Game_Map_Junction extends Game_Map_MapObj{
	
	public float offset = 0;
	
	public Game_Map_Junction(Junction junction, float actorX, float actorY)
	{
		super(actorX, actorY, Game_Map_TextureManager.getInstance().junction, Game_Map_TextureManager.getInstance().junctionx2);
	}
}
