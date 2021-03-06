package com.TeamHEC.LocomotionCommotion.MapActors;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Map.Junction;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.badlogic.gdx.graphics.g2d.Batch;

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
		
		Game_Map_StationBtn.selectedStation = this;
		
		if(GameData.EDITING) {
			if(Game_Map_Manager.getTool().equals("connection")) {
				if(Game_Map_Manager.conObj1 == null) {
					Game_Map_Manager.conObj1 = junction;
					Game_Map_Manager.connectionPlacing = true;
				} else {
					Game_Map_Manager.addNewConnection(Game_Map_Manager.conObj1, junction);
				}
			} else {
				Game_Map_Manager.showEditJunction(junction);
			}
		} else if(!GameScreenUI.routingModeWindow.isVisible()) {
			if(GameData.EDITING) {
				Game_Map_Manager.showEditJunction(junction);
			} else {
				Game_Map_Manager.moveJunctionInfo(junction);
			}
		}
	}
	
	public void draw(Batch batch, float alpha) {
		actorX = junction.x;
		actorY = junction.y;
		
		super.draw(batch, alpha);
	}
}
