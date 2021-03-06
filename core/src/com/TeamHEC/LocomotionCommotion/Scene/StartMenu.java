package com.TeamHEC.LocomotionCommotion.Scene;

import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.LocomotionCommotion;
import com.TeamHEC.LocomotionCommotion.Game.MusicThread;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Sprite;
import com.TeamHEC.LocomotionCommotion.UI_Elements.SpriteButton;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class StartMenu extends Scene{

    // (EEP) The minimum length allowed for a player's name
    public static final int MIN_NAME_LENGTH = 1;

	private Sprite sm_main_title, sm_main_lines;
	private SpriteButton newGameButton, loadGameButton, preferencesButton, editMapButton, exitButton;

	//Start Menu NewGame Page
	private Sprite sm_newgame_menutext;
	public static SelectBox<String> mapSelect;
	private Label mapLabel;
	private SpriteButton newGameBackButton, turnTimeOutButton, stationDomButton, newGameGoButton;
	private SpriteButton turn50Button, turn100Button, turn150Button;

	//Start Menu LoadGame Page
	private Sprite sm_loadgame_title, sm_loadgame_examples;

	//Start Menu Preferences Page
	private Sprite sm_preferences_vertline, sm_preferences_titletext;

	//Start Menu MapEdit Page
	private Sprite sm_howtoplay_line, sm_howtoplay_title;
	private Sprite sm_howtoplay_frame;
	private SpriteButton loadGameBckButton, prefBackButton, settingsButton;

	private SpriteButton displayButton, soundButton, controlButton;
	private SpriteButton homeButton, nextButton, prevButton, preferencesBackButton;
	
	// Other stuff

	public static String gameMode, player1name, player2name;
	public static int turnChoice;
	public static TextField textbox1, textbox2;
	
	public static MusicThread mainMusic;

	public StartMenu()
	{
		sm_main_title = new Sprite(6, 650, SM_TextureManager.getInstance().sm_main_title);
		actors.add(sm_main_title);

		sm_main_lines = new Sprite(-229,-145, SM_TextureManager.getInstance().sm_main_linesimg);
		actors.add(sm_main_lines);

		// Start MenuNewGame Page
		sm_newgame_menutext =  new Sprite(80,1150+250, SM_TextureManager.getInstance().sm_newgame_MenuText);
		actors.add(sm_newgame_menutext);
		
		//We can add music to the game with these lines:
		mainMusic = new MusicThread("Sound/elevator.mp3");
		mainMusic.start();
		
		//Add a new warning message?
		WarningMessage warningMessage = new WarningMessage();
		warningMessage.create(this.stage);
		
		newGameButton = new SpriteButton(600, 480, SM_TextureManager.getInstance().sm_main_newgamebtn){

			@Override
			protected void onClicked()
			{
				started = true;
			}

			int animationTracker1, animationTracker2;

			@Override
			public void act(float delta)
			{
				if(started)
				{
					if (animationTracker1<950){
						changeCam(0,15);
						animationTracker1+=15;
					}
					else{
						if(animationTracker2<90){
							changeCam(-15,0);
							animationTracker2+=15;
						}
						else{
							started = false;
							animationTracker1=0;
							animationTracker2=0;
						}
					}
				}
			}
		};
		actors.add(newGameButton);

		loadGameButton = new SpriteButton(590, 406, SM_TextureManager.getInstance().sm_main_loadgamebtn){

			@Override
			public void onClicked()
			{
				File saveFile = new File(GameData.SAVE_FOLDER + "/save.loco");
				if(saveFile.exists()){
					//started = true;
					LocomotionCommotion.isReplay = true;
					LocomotionCommotion.gameMode= gameMode;
					LocomotionCommotion.turnChoice = turnChoice;
					getJSONData();
					resetNewGameScreen();
					LocomotionCommotion.getInstance().setGameScreen();
				}
				else{
					WarningMessage.fireWarningWindow("Error!", "No save file found! Try playing a new game instead!");
				}
				
			}
			/**
			 * this method is called when a previous game is loaded.
			 * it opens the json file from the previous game and adds the values from the new game in.
			 */
			public void getJSONData()
			{
				try{
					FileReader in = new FileReader(GameData.SAVE_FOLDER + "/save.loco");
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(in);
					JSONObject jsonObject = (JSONObject) obj;
					GameData.CURRENT_MAP = (String)jsonObject.get("map");
					jsonObject = (JSONObject)jsonObject.get("turns");
					JSONObject turn = (JSONObject) jsonObject.get("0");
					JSONArray players = (JSONArray) turn.get("players");
					JSONObject player1 = (JSONObject) players.get(0);
					JSONObject player2 = (JSONObject) players.get(1);
					LocomotionCommotion.player1name = (String) player1.get("name");
					LocomotionCommotion.player2name = (String) player2.get("name");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			public void resetNewGameScreen()
			{
				turnTimeOutButton.setTexture(SM_TextureManager.getInstance().sm_newgameTurnTimeOut_unselected_Btn);
				stationDomButton.setTexture(SM_TextureManager.getInstance().sm_newgame_StationDom_unselected_Btn);
				textbox1.setText("");
				textbox2.setText("");
				turn50Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn50_unselected_Btn);
				turn100Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn100_unselected_Btn);
				turn150Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn150_unselected_Btn);
				gameMode=null;
				player1name= null;
				player2name= null;
				turnChoice=0;
			}
		};

		//loadGameButton opens the replay mode
		actors.add(loadGameButton);

		preferencesButton = new SpriteButton(590, 255, SM_TextureManager.getInstance().sm_main_preferencesbtn){

			@Override
			public void onClicked()
			{
			    try {
			        Desktop.getDesktop().browse(new URL("http://sepr-jkg.oliverbinns.co.uk").toURI());
			    } catch (Exception e) {
			        e.printStackTrace();
				}
			}

			/*int animationTracker1, animationTracker2, animationTracker3;

			@Override
			public void act(float delta)
			{
				if(started)
				{
					if (animationTracker1<900){
						changeCam(30,0);
						animationTracker1+=30;
					}
					else{
						if(animationTracker2<1000){
							changeCam(0,-30);
							animationTracker2+=30;
						}
						else
						{
							if(animationTracker3<500)
							{
								changeCam(-30,0);
								animationTracker3 +=30;
							}else{
								started = false;
								animationTracker1=0;
								animationTracker2=0;
								animationTracker3=0;
							}
						}
					}
				}
			}*/

		};
        // Not yet implemented. Hidden.
        actors.add(preferencesButton);

		editMapButton = new SpriteButton(590, 330, SM_TextureManager.getInstance().sm_main_howtoplaybtn){
			@Override
			public void onClicked()
			{
				LocomotionCommotion.isReplay = false;
				LocomotionCommotion.getInstance().setMapEditScreen();
			}
		};
        actors.add(editMapButton);

		exitButton = new SpriteButton(600, 86, SM_TextureManager.getInstance().sm_main_exitButton){

			@Override
			public void onClicked()
			{
				Gdx.app.exit();
			}
		};
		actors.add(exitButton);

		newGameGoButton = new SpriteButton(-100, 1200, SM_TextureManager.getInstance().sm_newgame_GoBtn){

			@Override
			public void onClicked()
			{

                if (        textbox1.getText().length() < MIN_NAME_LENGTH
                        ||  textbox2.getText().length() < MIN_NAME_LENGTH
                        ) {
                    // Name is too short.
                    // You could fire a warning window here, if only fireWarningWindow worked here.
                    // But it does not.
                	WarningMessage.fireWarningWindow("Whoops!", "Names must be longer than " + String.valueOf(MIN_NAME_LENGTH) + " characters long.");
                    return;
                }
                
                GameData.CURRENT_MAP = mapSelect.getSelected();
                LocomotionCommotion.isReplay = false;
				LocomotionCommotion.player1name=textbox1.getText();
				LocomotionCommotion.player2name=textbox2.getText();
				LocomotionCommotion.gameMode= gameMode;
				LocomotionCommotion.turnChoice = turnChoice;
				LocomotionCommotion.getInstance().setGameScreen();

				resetNewGameScreen();

			}
			public void resetNewGameScreen()
			{
				turnTimeOutButton.setTexture(SM_TextureManager.getInstance().sm_newgameTurnTimeOut_unselected_Btn);
				stationDomButton.setTexture(SM_TextureManager.getInstance().sm_newgame_StationDom_unselected_Btn);
				textbox1.setText("");
				textbox2.setText("");
				turn50Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn50_unselected_Btn);
				turn100Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn100_unselected_Btn);
				turn150Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn150_unselected_Btn);
				gameMode=null;
				player1name= null;
				player2name= null;
				turnChoice=0;
			}
		};
		actors.add(newGameGoButton);

		newGameBackButton = new SpriteButton(1150, 1800, SM_TextureManager.getInstance().sm_newgame_BackBtn){

			@Override
			public void onClicked()
			{
				started = true;
			}

			int animationTracker1, animationTracker2;	

			@Override
			public void act(float delta)
			{
				if(started){
					if (animationTracker1<90){
						changeCam(15,0);
						animationTracker1+=15;
					}
					else{
						if(animationTracker2<950){
							changeCam(0,-15);
							animationTracker2+=15;
						}
						else{
							resetNewGameScreen();
							started = false;
							animationTracker1=0;
							animationTracker2=0;
						}
					}
				}
			}

			public void resetNewGameScreen(){
				turnTimeOutButton.setTexture(SM_TextureManager.getInstance().sm_newgameTurnTimeOut_unselected_Btn);
				stationDomButton.setTexture(SM_TextureManager.getInstance().sm_newgame_StationDom_unselected_Btn);
				StartMenu.textbox1.setText("");
				StartMenu.textbox2.setText("");
				turn50Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn50_unselected_Btn);
				turn100Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn100_unselected_Btn);
				turn150Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn150_unselected_Btn);
				StartMenu.gameMode=null;
				StartMenu.player1name= null;
				StartMenu.player2name= null;
				StartMenu.turnChoice=0;
				mapSelect.setSelectedIndex(0);
			}
		};
		actors.add(newGameBackButton);

		turnTimeOutButton = new SpriteButton(400, 1680, SM_TextureManager.getInstance().sm_newgameTurnTimeOut_unselected_Btn){

			@Override
			public void onClicked()
			{
				StartMenu.gameMode = "turntimeout";
				setTexture(SM_TextureManager.getInstance().sm_newgame_TurnTimeOutBtn);
				stationDomButton.setTexture(SM_TextureManager.getInstance().sm_newgame_StationDom_unselected_Btn);
			}

		};
		actors.add(turnTimeOutButton);

		stationDomButton = new SpriteButton(660, 1680, SM_TextureManager.getInstance().sm_newgame_StationDom_unselected_Btn){

			@Override
			public void onClicked()
			{
				StartMenu.gameMode = "stationdomination";
				setTexture(SM_TextureManager.getInstance().sm_newgame_StationDomBtn);
				turnTimeOutButton.setTexture(SM_TextureManager.getInstance().sm_newgameTurnTimeOut_unselected_Btn);
			}

		};
        // Not yet implemented. Hidden.
        // actors.add(stationDomButton);

		turn50Button = new SpriteButton(490, 1400, SM_TextureManager.getInstance().sm_newgame_Turn50_unselected_Btn){

			@Override
			public void onClicked()
			{
				StartMenu.turnChoice = 50;
				setTexture(SM_TextureManager.getInstance().sm_newgame_Turn50Btn);
				turn100Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn100_unselected_Btn);
				turn150Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn150_unselected_Btn);
			}

		};
		actors.add(turn50Button);

		turn100Button = new SpriteButton(590, 1400, SM_TextureManager.getInstance().sm_newgame_Turn100_unselected_Btn){

			@Override
			public void onClicked()
			{
				StartMenu.turnChoice = 100;
				turn50Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn50_unselected_Btn);
				setTexture(SM_TextureManager.getInstance().sm_newgame_Turn100Btn);
				turn150Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn150_unselected_Btn);
			}

		};
		actors.add(turn100Button);

		turn150Button = new SpriteButton(680, 1400, SM_TextureManager.getInstance().sm_newgame_Turn150_unselected_Btn){

			@Override
			public void onClicked()
			{
				turnChoice = 150;
				turn50Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn50_unselected_Btn);
				turn100Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn100_unselected_Btn);
				setTexture(SM_TextureManager.getInstance().sm_newgame_Turn150Btn);
			}

		};
		actors.add(turn150Button);

		sm_loadgame_title = new Sprite(1680+350,665, SM_TextureManager.getInstance().sm_loadgame_Title);
		actors.add(sm_loadgame_title);

		loadGameBckButton = new SpriteButton(1680+150, 850, SM_TextureManager.getInstance().sm_newgame_BackBtn){

			@Override
			public void onClicked()
			{
				started = true;
			}

			int animationTracker1, animationTracker2;	

			@Override
			public void act(float delta)
			{
				if(started)
				{
					if (animationTracker1<50){
						changeCam(0,-10);
						animationTracker1+=15;
					}
					else{
						if(animationTracker2<1680){
							changeCam(-15,0);
							animationTracker2+=15;
						}

						else{
							started = false;
							animationTracker1=0;
							animationTracker2=0;
						}
					}
				}
			}
		};
		actors.add(loadGameBckButton);

		sm_loadgame_examples = new Sprite(1680+350,500, SM_TextureManager.getInstance().sm_loadgame_Examples);
		actors.add(sm_loadgame_examples);

		//Start Menu Preferences Page

		sm_preferences_vertline = new Sprite(1420,-900+72, SM_TextureManager.getInstance().sm_preferences_VertLine);
		actors.add(sm_preferences_vertline);

		prefBackButton = new SpriteButton(1390, -900+ 745, SM_TextureManager.getInstance().sm_newgame_BackBtn){

			@Override
			public void onClicked()
			{
				started = true;
			}

			int animationTracker1, animationTracker2, animationTracker3;	

			@Override
			public void act(float delta)
			{
				if(started)
				{
					if (animationTracker1<510){
						changeCam(30,0);
						animationTracker1+=30;
					}
					else{
						if(animationTracker2<1000){
							changeCam(0,30);
							animationTracker2+=30;
						}
						else
						{
							if(animationTracker3<900)
							{
								changeCam(-30,0);
								animationTracker3 +=30;
							}else{
								started = false;
								animationTracker1=0;
								animationTracker2=0;
								animationTracker3=0;
							}
						}
					}
				}
			}
		};
		actors.add(prefBackButton);

		sm_preferences_titletext = new Sprite(500,-900+720, SM_TextureManager.getInstance().sm_preferences_Title);
		actors.add(sm_preferences_titletext);

		settingsButton = new SpriteButton(890, -900+550, SM_TextureManager.getInstance().sm_preferences_GameSettingsBtn){

			@Override
			public void onClicked()
			{
				changeCam(0, 0);
			}
		};
		actors.add(settingsButton);

		displayButton = new SpriteButton(890-37, -900+450, SM_TextureManager.getInstance().sm_preferences_DisplaySettingsBtn){

			@Override
			public void onClicked()
			{
				changeCam(0, 0);
			}
		};
		actors.add(displayButton);

		soundButton = new SpriteButton(890-37, -900+550-175, SM_TextureManager.getInstance().sm_preferences_SoundSettingsBtn){

			@Override
			public void onClicked()
			{
				changeCam(0, 0);
			}
		};
		actors.add(soundButton);

		controlButton = new SpriteButton(890-80, -900+550-300, SM_TextureManager.getInstance().sm_preferences_ControlSettingsBtn){

			@Override
			public void onClicked()
			{
				changeCam(0, 0);
			}
		};
		actors.add(controlButton);

		//StartMenu HowtoPlay screen

		sm_howtoplay_line = new Sprite(-1700+1300,175, SM_TextureManager.getInstance().sm_howtoplay_line);
		actors.add(sm_howtoplay_line);

		sm_howtoplay_title = new Sprite(-1700+350,650, SM_TextureManager.getInstance().sm_howtoplay_title);
		actors.add(sm_howtoplay_title);

		nextButton = new SpriteButton(-1700+ 590, 150, SM_TextureManager.getInstance().sm_howtoplay_nextbtn){

			@Override
			public void onClicked()
			{
				changeCam(0, 0);
			}
		};
		actors.add(nextButton);

		prevButton = new SpriteButton(-1700+ 460, 150, SM_TextureManager.getInstance().sm_howtoplay_previousbtn){

			@Override
			public void onClicked()
			{
				changeCam(0, 0);
			}
		};
		actors.add(prevButton);


		homeButton = new SpriteButton(-1700+ 570, 160, SM_TextureManager.getInstance().sm_howtoplay_homebtn){

			@Override
			public void onClicked()
			{
				changeCam(0, 0);
			}
		};
		actors.add(homeButton);

		sm_howtoplay_frame = new Sprite(-1700+240,220, SM_TextureManager.getInstance().sm_howtoplay_frame);
		actors.add(sm_howtoplay_frame);

		preferencesBackButton = new SpriteButton(-1700+ 1275, 655, SM_TextureManager.getInstance().sm_newgame_BackBtn){
			@Override
			public void onClicked()
			{
				started = true;
			}

			int animationTracker1, animationTracker2;

			@Override
			public void act(float delta)
			{
				if(started){
					if (animationTracker1<1700){
						changeCam(50,0);
						animationTracker1+=50;
					}
					else{
						if(animationTracker2<45){
							changeCam(0,15);
							animationTracker2+=15;
						}

						else{
							started = false;
							animationTracker1=0;
							animationTracker2=0;
						}
					}
				}
			}

		};
		actors.add(preferencesBackButton);

		//Text boxes for Player 1 and 2 names
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

		textbox1 = new TextField("", skin);
		skin.getFont("default-font").setScale(1.5f, 1.5f);
		textbox1.setX(480);
		textbox1.setY(1150+430);
		textbox1.setSize(430, 60);
		textbox1.setMessageText("Player 1");
		TextFieldListener player1 = new TextFieldListener() {
			public void keyTyped (TextField textbox1, char key) {
				if (key == '\n') textbox1.getOnscreenKeyboard().show(false);
				player1name = textbox1.getText();
			}
		};

		textbox1.setTextFieldListener(player1);

		textbox2 = new TextField("", skin);
		textbox2.setX(480);
		textbox2.setY(1150+350);
		textbox2.setSize(430, 60);
		textbox2.setMessageText("Player 2");
		TextFieldListener player2 = new TextFieldListener() {
			public void keyTyped (TextField textbox2, char key) {
				if (key == '\n') textbox2.getOnscreenKeyboard().show(false);
				player2name = textbox2.getText();
			}
		};
		textbox2.setTextFieldListener(player2);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gillsans.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 32;

		BitmapFont font = generator.generateFont(parameter);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		
		LabelStyle style = new LabelStyle();
		style.font = font;
		style.fontColor = Color.DARK_GRAY;
		
		mapLabel = new Label("Choose Map: ", style);
		mapLabel.setX(550);
		mapLabel.setY(1150 + 155);
		
		mapSelect = new SelectBox<String>(skin);
		mapSelect.setItems(WorldMap.getInstance().mapList.keySet().toArray(new String[WorldMap.getInstance().mapList.keySet().size()]));
		mapSelect.setSelected(GameData.CURRENT_MAP);
		mapSelect.setBounds(760, 1150 + 150, 150, 40);
		
		actors.add(textbox1);
		actors.add(textbox2);
		actors.add(mapLabel);
		actors.add(mapSelect);
		
		//Default to Turn Timeout and 50 Turn Limit, auto-select Player 1's name box 
		StartMenu.gameMode = "turntimeout";
		turnTimeOutButton.setTexture(SM_TextureManager.getInstance().sm_newgame_TurnTimeOutBtn);
		stationDomButton.setTexture(SM_TextureManager.getInstance().sm_newgame_StationDom_unselected_Btn);
		
		StartMenu.turnChoice = 50;
		turn50Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn50Btn);
		turn100Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn100_unselected_Btn);
		turn150Button.setTexture(SM_TextureManager.getInstance().sm_newgame_Turn150_unselected_Btn);
	}
}