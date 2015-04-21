package com.TeamHEC.LocomotionCommotion.MapActors;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.LocomotionCommotion;
import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.Junction;
import com.TeamHEC.LocomotionCommotion.Map.Line;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Resource.*;
import com.TeamHEC.LocomotionCommotion.Train.TrainInfoUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_StartingSequence;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Sprite;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
/**
 * @author Sam Watkins <sw1308@york.ac.uk>
 * @author Robert Precious/ Matthew Taylor <rp825@york.ac.uk>
 * Map Manager is used to 'manage' the map. It creates the map actors for the map. Handles routing UI and map/station information.
 */
public class Game_Map_Manager {

	private final static Array<Actor> actors = new Array<Actor>();
	private final static Array<Actor> infoactors = new Array<Actor>();
	private final static Array<Actor> trainInfoActors = new Array<Actor>();

	public static Sprite map;
	public static Sprite mapInfo;
	public static Sprite stationInfo;
	public static Sprite junctionInfo;
	
	public static Game_Map_StationBtn stationSelect, stationUnlock;
	public static MapObj selectedObj;
	private static String currentTool;

    // Checks if a train is moving or not.
    public static boolean isMoving = false;
	
	public static TrainInfoUI trainInfo;

	public static boolean infoVisible= false;
	public static int  stagestart, mapActors, stationTracker, numberOfStations, junctionTracker, numberOfJunctions = 2;
	public static Label stationLabelFuel,stationLabelName, stationLabelCost;
	public LabelStyle style;

	/*public static Sprite planBackground;*/
	public static Array<Game_Map_Train> trainBlips = new Array<Game_Map_Train>();

	public Game_Map_Manager(){	}

	public void create(Stage stage){
	
		actors.clear();
		infoactors.clear();
		resetMap();
		stagestart =0;
		mapActors=0;
		stationTracker=0;
		numberOfStations=0;
		currentTool = "None";
	
		stationTracker = stage.getActors().size;
		for(Station s : WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).stationList()) {
			actors.add(s.getActor());
			numberOfStations++;
			
			Label nameLabel = s.getActor().getLabel();
			nameLabel.setX(s.x - (s.getActor().labelWidth/2));
			nameLabel.setY(s.y + 45);
			nameLabel.setVisible(true);
			actors.add(nameLabel);
		}

		junctionTracker = stage.getActors().size;
		for(Junction j : WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).junctionList()) {
			actors.add(j.getActor());
		}
		
		// Creates UI Train blips for 6 trains:
		for(int i = 0; i < 6; i++)
		{
			trainBlips.add(new Game_Map_Train());
		}
		actors.addAll(trainBlips);
		
		// Add train stuff

		stationInfo = new Sprite(0, 0, Game_Map_TextureManager.getInstance().stationInfo);
		infoactors.add(stationInfo);
		
		junctionInfo = new Sprite(0, 0, Game_Map_TextureManager.getInstance().junctionInfo);
		infoactors.add(junctionInfo);
		
		trainInfo = new TrainInfoUI();		
		trainInfoActors.add(trainInfo);
		trainInfoActors.addAll(trainInfo.getActors());

		stationSelect = new Game_Map_StationBtn(0, 0, Game_Map_TextureManager.getInstance().stationSelect, false);
		stationUnlock = new Game_Map_StationBtn(0, 0, Game_Map_TextureManager.getInstance().stationLock, true);
		infoactors.add(stationSelect);
		infoactors.add(stationUnlock);

		//Stuff for Labels
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gillsans.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 23;

		BitmapFont font = generator.generateFont(parameter); 
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		style = new LabelStyle();
		style.font = font;
		//end

		stationLabelName = new Label(null, style);
		stationLabelFuel = new Label(null, style);
		stationLabelCost = new Label(null, style);
			
		stationLabelName.setText("LONDON");
		stationLabelName.setAlignment(Align.center);		
		stationLabelName.setColor(1,1,1,1);
		stationLabelName.setX(stationInfo.getX()+100);
		stationLabelName.setY(stationInfo.getY()+142);

		stationLabelFuel.setText("Type x 100");
		stationLabelFuel.setAlignment(Align.center);		
		stationLabelFuel.setColor(0,0,0,1);
		stationLabelFuel.setX(stationInfo.getX()+100);
		stationLabelFuel.setY(stationInfo.getY()+100);

		stationLabelCost.setText("");
		stationLabelCost.setAlignment(Align.center);		
		stationLabelCost.setColor(0,0,0,1);
		stationLabelCost.setX(stationInfo.getX()+100);
		stationLabelCost.setY(stationInfo.getY()+60);

		infoactors.add(stationLabelName);
		infoactors.add(stationLabelFuel);
		infoactors.add(stationLabelCost);
		
		for(Actor a : actors)
		{
			a.setTouchable(Touchable.enabled);
			stage.addActor(a);
		}
		
		stagestart = stage.getActors().size;
		
		for (Actor a : infoactors){
			a.setTouchable(Touchable.enabled);
			a.setVisible(false);
			stage.addActor(a);
			mapActors ++;
		}

		for(Actor a : trainInfoActors)
		{
			a.setTouchable(Touchable.enabled);
			a.setVisible(false);
			stage.addActor(a);
		}
		
		mapInfo = new Sprite(500, 100, Game_TextureManager.getInstance().mapInfo);

		mapInfo.setVisible(infoVisible);
		stage.addActor(mapInfo);
	}

	public static void moveInfoBox(float x,float y){
		Game_Map_Manager.hideInfoBox();
		Game_Map_Manager.hideJunctionInfo();
		stationInfo.setX(x);
		stationInfo.setY(y);
		showInfoBox();
		stationInfo.refreshBounds();
		Game_Map_Manager.stationSelect.setX(x+20);
		Game_Map_Manager.stationSelect.setY(y+10);
		Game_Map_Manager.stationSelect.refreshBounds();
		
		Game_Map_Manager.stationUnlock.setX(x+20);
		Game_Map_Manager.stationUnlock.setY(y-23);
		Game_Map_Manager.stationUnlock.refreshBounds();

		stationLabelName.setX(x+100);
		stationLabelName.setY(y+142);

		stationLabelFuel.setX(x+100);
		stationLabelFuel.setY(y+100);

		stationLabelCost.setX(x+100);
		stationLabelCost.setY(y+60);
	}

	public static void hideInfoBox(){
		stationInfo.setVisible(false);
		junctionInfo.setVisible(false);
		Game_Map_Manager.stationSelect.setVisible(false);
		Game_Map_Manager.stationUnlock.setVisible(false);

		stationLabelName.setVisible(false);
		stationLabelFuel.setVisible(false);
		stationLabelCost.setVisible(false);
	}

	public static void showInfoBox(){
		stationInfo.setVisible(true);
		stationInfo.setTexture(Game_Map_TextureManager.getInstance().stationInfo);
		Game_Map_Manager.stationSelect.setVisible(true);
		Game_Map_Manager.stationUnlock.setVisible(false);
		
		if(LocomotionCommotion.isReplay){
			stationSelect.setVisible(false); //don't show StationSelect if we're on Replay! We don't want users editing our existing game!
		} else if(((Station) Game_Map_StationBtn.selectedStation.getMapObj()).isLocked()){
			Game_Map_Manager.stationSelect.setTexture(Game_Map_TextureManager.getInstance().stationUnlock);
		} else if(((Station) Game_Map_StationBtn.selectedStation.getMapObj()).isFaulty()) {
			Game_Map_Manager.stationSelect.setTexture(Game_Map_TextureManager.getInstance().stationRepair);
		} else if(Game_StartingSequence.inProgress) {
			Game_Map_Manager.stationSelect.setTexture(Game_Map_TextureManager.getInstance().stationSelect);
		} else if(Game_Map_StationBtn.selectedStation.getMapObj().getStation().getOwner() == GameScreen.game.getPlayerTurn()){
			//Can buy a train at a station that the current player owns
			Game_Map_Manager.stationSelect.setTexture(Game_Map_TextureManager.getInstance().trainBuy);
		} else if(Game_Map_StationBtn.selectedStation.owned){
			stationSelect.setVisible(false); //can't lock a station that is owned!
		} else {
			stationInfo.setTexture(Game_Map_TextureManager.getInstance().stationInfoLong);
			stationInfo.setY(stationInfo.getY() - 32);
			Game_Map_Manager.stationSelect.setTexture(Game_Map_TextureManager.getInstance().stationBuy);
			Game_Map_Manager.stationUnlock.setVisible(true);
		}

		stationLabelName.setVisible(true);
		stationLabelFuel.setVisible(true);
		stationLabelCost.setVisible(true);
	}
	
	public static void moveJunctionInfo(Junction junction) {
		float x, y;
		x = junction.x - 195;
		y = junction.y - 80;
		
		Game_Map_Manager.hideInfoBox();
		Game_Map_Manager.hideJunctionInfo();
		junctionInfo.setX(x);
		junctionInfo.setY(y);
		
		showJunctionInfo(junction);
		junctionInfo.refreshBounds();
		
		Game_Map_Manager.stationSelect.setX(x + 20);
		Game_Map_Manager.stationSelect.setY(y + 10);
		Game_Map_Manager.stationUnlock.setX(x + 20);
		Game_Map_Manager.stationUnlock.setY(y + 10);
		Game_Map_Manager.stationSelect.refreshBounds();
		Game_Map_Manager.stationUnlock.refreshBounds();
		
		stationLabelName.setText(junction.getName());
		stationLabelName.setX(x + 100);
		stationLabelName.setY(y + 90);
	}
	
	public static void hideJunctionInfo() {
		junctionInfo.setVisible(false);
		Game_Map_Manager.stationSelect.setVisible(false);
		Game_Map_Manager.stationUnlock.setVisible(false);
		
		stationLabelName.setVisible(false);
	}
	
	public static void showJunctionInfo(Junction junction) {
		if(junctionInfo.isVisible()){
			hideJunctionInfo();
		}
		else{
			junctionInfo.setVisible(true);
			if(LocomotionCommotion.isReplay){
				Game_Map_Manager.stationSelect.setVisible(false);
				Game_Map_Manager.stationUnlock.setVisible(false);
			}
			else{
				if(junction.isLocked()) {
					Game_Map_Manager.stationSelect.setTexture(Game_Map_TextureManager.getInstance().stationUnlock);
					Game_Map_Manager.stationSelect.setVisible(true);
				} else {
					Game_Map_Manager.stationUnlock.setVisible(true);
				}
			}
			stationLabelName.setVisible(true);
		}
	}
	
	public static void showEditJunction(Junction junction) {
		selectedObj = junction;
		currentTool = "None";
		
		GameScreenUI.editStationNameLabel.setVisible(true);
		GameScreenUI.editStationLockedLabel.setVisible(true);
		GameScreenUI.editPositionXLabel.setVisible(true);
		GameScreenUI.editPositionYLabel.setVisible(true);
		GameScreenUI.editStationFuelLabel.setVisible(false);
		GameScreenUI.editStationRentLabel.setVisible(false);
		GameScreenUI.editStationValueLabel.setVisible(false);
		GameScreenUI.editStationResourceLabel.setVisible(false);
		
		GameScreenUI.editStationResource.setVisible(false);
		GameScreenUI.editStationFuel.setVisible(false);
		GameScreenUI.editStationLocked.setVisible(true);
		GameScreenUI.editStationName.setVisible(true);
		GameScreenUI.editStationRent.setVisible(false);
		GameScreenUI.editPositionX.setVisible(true);
		GameScreenUI.editPositionY.setVisible(true);
		GameScreenUI.editStationValue.setVisible(false);
		
		GameScreenUI.editStationName.setText(junction.getName());
		GameScreenUI.editPositionX.setText(Integer.toString((int) junction.x));
		GameScreenUI.editPositionY.setText(Integer.toString((int) junction.y));
		GameScreenUI.editStationLocked.setChecked(junction.isLocked());
	}
	
	public static void showEditStation(Station station) {
		selectedObj = station;
		currentTool = "None";
		
		GameScreenUI.editStationNameLabel.setVisible(true);
		GameScreenUI.editStationLockedLabel.setVisible(true);
		GameScreenUI.editPositionXLabel.setVisible(true);
		GameScreenUI.editPositionYLabel.setVisible(true);
		GameScreenUI.editStationFuelLabel.setVisible(true);
		GameScreenUI.editStationRentLabel.setVisible(true);
		GameScreenUI.editStationValueLabel.setVisible(true);
		GameScreenUI.editStationResourceLabel.setVisible(true);
		
		GameScreenUI.editStationResource.setVisible(true);
		GameScreenUI.editStationFuel.setVisible(true);
		GameScreenUI.editStationLocked.setVisible(true);
		GameScreenUI.editStationName.setVisible(true);
		GameScreenUI.editStationRent.setVisible(true);
		GameScreenUI.editPositionX.setVisible(true);
		GameScreenUI.editPositionY.setVisible(true);
		GameScreenUI.editStationValue.setVisible(true);
		
		GameScreenUI.editStationFuel.setText(Integer.toString(station.getBaseResourceOut()));
		GameScreenUI.editStationName.setText(station.getName());
		GameScreenUI.editStationRent.setText(Integer.toString(station.getBaseRentValue()));
		GameScreenUI.editStationValue.setText(Integer.toString(station.getBaseValue()));
		GameScreenUI.editStationResource.setSelected(station.getResourceString());
		GameScreenUI.editStationLocked.setChecked(station.isLocked());
		GameScreenUI.editPositionX.setText(Integer.toString((int) station.x));
		GameScreenUI.editPositionY.setText(Integer.toString((int) station.y));
	}
	
	public static void addNewStation(int x, int y) {
		Station s = new Station("Unnamed", 0, new Oil(0), 0, new Line[0], 0, x, y);
		WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).addMapObj(s);
		actors.add(s.getActor());
	}
	
	public static void addNewJunction(int x, int y) {
		Junction j = new Junction(x, y, "Unnamed");
		WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).addMapObj(j);
		actors.add(j.getActor());
	}
	
	public static void addNewConnection(MapObj startPoint, MapObj endPoint) {
		Connection c = new Connection(startPoint, endPoint, Line.Black);
		WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).addConnection(c);
		
		if(startPoint instanceof Station) {
			WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).getStationWithName(startPoint.getName()).addLine(Line.Black);
		}
		
		if(endPoint instanceof Station) {
			WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).getStationWithName(startPoint.getName()).addLine(Line.Black);
		}
	}
	
	public static void saveStation() {
		currentTool = "None";
		
		Station s = (Station) selectedObj;
	
		s.x = Integer.parseInt(GameScreenUI.editPositionX.getText());
		s.y = Integer.parseInt(GameScreenUI.editPositionY.getText());
		s.lock(GameScreenUI.editStationLocked.isChecked());
		s.setName(GameScreenUI.editStationName.getText());
		s.setBaseResourceOut(Integer.parseInt(GameScreenUI.editStationFuel.getText()));
		s.setBaseRentValue(Integer.parseInt(GameScreenUI.editStationRent.getText()));
		s.setBaseValue(Integer.parseInt(GameScreenUI.editStationValue.getText()));
		
		if(GameScreenUI.editStationResource.getSelected().equals("Coal")) {
			s.setResourceType(new Coal(s.getResourceType().getValue()));
		} else if(GameScreenUI.editStationResource.getSelected().equals("Electric")) {
			s.setResourceType(new Electric(s.getResourceType().getValue()));
		} else if(GameScreenUI.editStationResource.getSelected().equals("Nuclear")) {
			s.setResourceType(new Nuclear(s.getResourceType().getValue()));
		} else {
			s.setResourceType(new Oil(s.getResourceType().getValue()));
		}
	}
	
	public static void saveJunction() {
		currentTool = "None";
		
		Junction j = (Junction) selectedObj;
		
		j.x = Integer.parseInt(GameScreenUI.editPositionX.getText());
		j.y = Integer.parseInt(GameScreenUI.editPositionY.getText());
		j.lock(GameScreenUI.editStationLocked.isChecked());
		j.setName(GameScreenUI.editStationName.getText());
	}
	
	public static void deleteStation() {
		currentTool = "None";
		Station s = (Station) selectedObj;
		
		WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).removeStation(s.getName());
	}
	
	public static void deleteJunction() {
		currentTool = "None";
		Junction j = (Junction) selectedObj;
		
		WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).removeJunction(j.getName());
	}

	public static void resetMap(){
		for(int i=Game_Map_Manager.stationTracker; i<=Game_Map_Manager.stationTracker +Game_Map_Manager.numberOfStations-1;i++)	//All the stations on the stage
		{ 	
			if (i > GameScreen.getStage().getActors().size-1)
			{//This is just to avoid range errors
			}
			else{
				if (GameScreen.getStage().getActors().get(i).getClass() == Game_Map_Station.class)
				{
					((Game_Map_Station) GameScreen.getStage().getActors().get(i)).setOwned(false);
				}
			}
		}
	}
	
	public static void deselectAll() {
		for(MapObj m : WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).mapObjList()) {
			m.getActor().highlighted = false;
		}
	}

	public static void showEditConnection(Connection connection) {
		currentTool = "None";
		WarningMessage.fireWarningWindow("Warning", "Connection clicked " + connection.getStartMapObj().getName() + " :- " + connection.getDestination().getName());		
	}
	
	public static void setTool(String tool) {
		currentTool = tool;
	}
	
	public static String getTool() {
		return currentTool;
	}
}