package com.TeamHEC.LocomotionCommotion.MapActors;

import com.TeamHEC.LocomotionCommotion.Scene.CustomTexture;
import com.badlogic.gdx.Gdx;
/*
 * Holds all the CustomTextures and file paths for all actors in StartMenu
 * This means if we need to change a file path you come here.
 */
public class Game_Map_TextureManager{
	private static Game_Map_TextureManager instance = null;
	
	protected Game_Map_TextureManager()
	{}
	
	public static Game_Map_TextureManager getInstance() {
		if(instance == null)
			instance = new Game_Map_TextureManager();
		return instance;
	}	
	
	//Map
	//public CustomTexture map = new CustomTexture(Gdx.files.internal("gameScreen/game_map/map.png"));
	public CustomTexture mapInfo = new CustomTexture(Gdx.files.internal("gameScreen/game_map/mapinfo.png"));
	public CustomTexture station = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction-trans.png"));
	public CustomTexture stationFaulty = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction-trans.png"));
	public CustomTexture stationx2 = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction2-trans.png"));
	public CustomTexture stationx2Faulty = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction2-trans.png"));
	
	public CustomTexture stationInfo = new CustomTexture(Gdx.files.internal("gameScreen/game_map/stationInfoframe.png"));
	public CustomTexture junctionInfo = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junctionInfoframe.png"));
	public CustomTexture stationInfoLong = new CustomTexture(Gdx.files.internal("gameScreen/game_map/stationInfoframeLong.png"));
	public CustomTexture stationSelect = new CustomTexture(Gdx.files.internal("gameScreen/game_map/stationSelectBtn.png"));
	public CustomTexture stationRepair = new CustomTexture(Gdx.files.internal("gameScreen/game_map/stationRepairBtn.png"));
	public CustomTexture stationUnlock = new CustomTexture(Gdx.files.internal("gameScreen/game_map/unlockStationBtn.png"));
	public CustomTexture stationLock = new CustomTexture(Gdx.files.internal("gameScreen/game_map/lockStationBtn.png"));
	public CustomTexture trainBuy = new CustomTexture(Gdx.files.internal("gameScreen/game_map/TrainBuyBtn.png"));
	public CustomTexture stationBuy = new CustomTexture(Gdx.files.internal("gameScreen/game_map/stationBuyBtn.png"));
	public CustomTexture trainInfo = new CustomTexture(Gdx.files.internal("gameScreen/game_map/trainInfo.png"));
	public CustomTexture trainInfoPlanRoute = new CustomTexture(Gdx.files.internal("gameScreen/game_map/trainInfoPlanRoute.png"));
	
	public CustomTexture junction = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction-trans.png"));
	public CustomTexture junctionx2 = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction2-trans.png"));
	
	public CustomTexture p1Station = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction-trans.png"));
	public CustomTexture p1StationFaulty = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction-trans.png"));
	public CustomTexture p1Stationx2 = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction2-trans.png"));
	public CustomTexture p1Stationx2Faulty = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction2-trans.png"));
	public CustomTexture p1Train = new CustomTexture(Gdx.files.internal("gameScreen/game_map/p1train.png"));
	public CustomTexture p1Trainx2 = new CustomTexture(Gdx.files.internal("gameScreen/game_map/p1train2.png"));
	
	public CustomTexture p2Station = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction-trans.png"));
	public CustomTexture p2StationFaulty = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction-trans.png"));
	public CustomTexture p2Stationx2 = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction2-trans.png"));
	public CustomTexture p2Stationx2Faulty = new CustomTexture(Gdx.files.internal("gameScreen/game_map/junction2-trans.png"));
	public CustomTexture p2Train = new CustomTexture(Gdx.files.internal("gameScreen/game_map/p2train.png"));
	public CustomTexture p2Trainx2 = new CustomTexture(Gdx.files.internal("gameScreen/game_map/p2train2.png"));
	
	public CustomTexture routeBlip = new CustomTexture(Gdx.files.internal("gameScreen/game_map/routeBlip.png"));
	public CustomTexture redRouteBlip = new CustomTexture(Gdx.files.internal("gameScreen/game_map/routeBlip2.png"));
	
}
