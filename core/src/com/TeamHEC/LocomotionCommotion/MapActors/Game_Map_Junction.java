package com.TeamHEC.LocomotionCommotion.MapActors;

import com.TeamHEC.LocomotionCommotion.Map.Junction;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;

public class Game_Map_Junction extends Game_Map_MapObj{
	
	public float offset = 0;
	public Junction junction;
	
	public Game_Map_Junction(Junction junction, float actorX, float actorY)
	{
		super(actorX, actorY, Game_Map_TextureManager.getInstance().junction, Game_Map_TextureManager.getInstance().junctionx2);
		this.junction = junction;
	}
	
	public MapObj getMapObj() {
		return junction;
	}
	
	@Override
	protected void onClicked() {
		super.onClicked();
		
		Game_Map_Manager.showEditJunction(junction);
	}
}
