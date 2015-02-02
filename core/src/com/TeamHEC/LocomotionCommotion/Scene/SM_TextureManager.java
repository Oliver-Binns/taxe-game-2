package com.TeamHEC.LocomotionCommotion.Scene;

import com.badlogic.gdx.Gdx;
/*
 * Holds all the CustomTextures and file paths for all actors in StartMenu
 * This means if we need to change a file path you come here.
 */
public class SM_TextureManager {
	private static SM_TextureManager instance;
	
	protected SM_TextureManager()
	{}
	
	public static SM_TextureManager getInstance() {
		if(instance == null)
			instance = new SM_TextureManager();
		return instance;
	}	
	
	//Start Menu
	//Start Menu Main Page
	public CustomTexture sm_main_title = new CustomTexture(Gdx.files.internal("startMenu/sm_main/smTitle.png"));
	public CustomTexture sm_main_newgamebtn = new CustomTexture(Gdx.files.internal("startMenu/sm_main/sm_newgame.png"));
	public CustomTexture sm_main_loadgamebtn = new CustomTexture(Gdx.files.internal("startMenu/sm_main/sm_loadgame.png"));
	public CustomTexture sm_main_preferencesbtn = new CustomTexture(Gdx.files.internal("startMenu/sm_main/sm_preferences.png"));
	public CustomTexture sm_main_howtoplaybtn = new CustomTexture(Gdx.files.internal("startMenu/sm_main/sm_howtoplay.png"));
	public CustomTexture sm_main_exitButton = new CustomTexture(Gdx.files.internal("startMenu/sm_main/sm_exitgame.png"));
	public CustomTexture sm_main_linesimg = new CustomTexture(Gdx.files.internal("startMenu/sm_main/lines.png"));
	
	//Start Menu NewGame Page
	public CustomTexture sm_newgame_MenuText = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/newgamescreen.png"));
	public CustomTexture sm_newgame_BackBtn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/backButton.png"));
	public CustomTexture sm_newgame_GoBtn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/goBtn.png"));
	public CustomTexture sm_newgame_TempTextBox = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/tempTextBox.png"));
	public CustomTexture sm_newgame_TurnTimeOutBtn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/turnTimeoutBtn.png"));
	public CustomTexture sm_newgameTurnTimeOut_unselected_Btn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/turnTimeout_unselected_Btn.png"));
	public CustomTexture sm_newgame_StationDomBtn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/stationDominationBtn.png"));
	public CustomTexture sm_newgame_StationDom_unselected_Btn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/stationDomination_unselected_Btn.png"));
	public CustomTexture sm_newgame_Turn50Btn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/turn50.png"));
	public CustomTexture sm_newgame_Turn100Btn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/turn100.png"));
	public CustomTexture sm_newgame_Turn150Btn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/turn150.png"));
	public CustomTexture sm_newgame_Turn50_unselected_Btn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/turn50_unselected.png"));
	public CustomTexture sm_newgame_Turn100_unselected_Btn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/turn100_unselected.png"));
	public CustomTexture sm_newgame_Turn150_unselected_Btn = new CustomTexture(Gdx.files.internal("startMenu/sm_newgame/turn150_unselected.png"));
	
	//StartMenu LoadGame Page
	public CustomTexture sm_loadgame_Title = new CustomTexture(Gdx.files.internal("startMenu/sm_loadgame/loadgametitle.png"));
	public CustomTexture sm_loadgame_Examples = new CustomTexture(Gdx.files.internal("startMenu/sm_loadgame/loadgameexamples.png"));
	
	//StartMenu Preferences Page
	public CustomTexture sm_preferences_VertLine = new CustomTexture(Gdx.files.internal("startMenu/sm_preferences/vertline.png"));
	public CustomTexture sm_preferences_Title = new CustomTexture(Gdx.files.internal("startMenu/sm_preferences/preferencestitle.png"));
	public CustomTexture sm_preferences_GameSettingsBtn = new CustomTexture(Gdx.files.internal("startMenu/sm_preferences/gamesettingsbtn.png"));
	public CustomTexture sm_preferences_DisplaySettingsBtn = new CustomTexture(Gdx.files.internal("startMenu/sm_preferences/displaysettingsbtn.png"));
	public CustomTexture sm_preferences_SoundSettingsBtn = new CustomTexture(Gdx.files.internal("startMenu/sm_preferences/soundsettingsbtn.png"));
	public CustomTexture sm_preferences_ControlSettingsBtn = new CustomTexture(Gdx.files.internal("startMenu/sm_preferences/controlsettingsbtn.png"));
	
	
	//StartMenu HowtoPlayPage
	public CustomTexture sm_howtoplay_line = new CustomTexture(Gdx.files.internal("startMenu/sm_howtoplay/howtoplayline.png"));
	public CustomTexture sm_howtoplay_frame = new CustomTexture(Gdx.files.internal("startMenu/sm_howtoplay/howtoplayframe.png"));
	public CustomTexture sm_howtoplay_title = new CustomTexture(Gdx.files.internal("startMenu/sm_howtoplay/howtoplaytitle.png"));
	public CustomTexture sm_howtoplay_nextbtn = new CustomTexture(Gdx.files.internal("startMenu/sm_howtoplay/nextbtn.png"));
	public CustomTexture sm_howtoplay_previousbtn = new CustomTexture(Gdx.files.internal("startMenu/sm_howtoplay/previousbtn.png"));
	public CustomTexture sm_howtoplay_homebtn = new CustomTexture(Gdx.files.internal("startMenu/sm_howtoplay/homebtn.png"));
	
}
