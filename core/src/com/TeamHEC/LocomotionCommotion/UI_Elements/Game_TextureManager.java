package com.TeamHEC.LocomotionCommotion.UI_Elements;

import com.TeamHEC.LocomotionCommotion.Scene.CustomTexture;
import com.badlogic.gdx.Gdx;
/**
 * 
 * @author Robert Precious <rp825@york.ac.uk>
 * 
 * This is the centre for all the CustomTexture's used in the game except for Map CustomTextures.
 * When fetching a CustomTexture use : Game_CustomTextureManager.getInstance().<desired CustomTexture name>
 *
 */
public class Game_TextureManager {
	private static Game_TextureManager instance;
	
	
	protected Game_TextureManager()
	{}
	
	public static Game_TextureManager getInstance() {
		if(instance == null)
			instance = new Game_TextureManager();
		return instance;
	}	
	
	//Top Bar
	public  CustomTexture game_menuobject_topbar = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/TopBar.png"));
	public CustomTexture game_menuobject_menubtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/menubtn.png"));
	
	//Resources
	public CustomTexture game_menuobject_resourcesbar = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/resourcesbar.png"));
	//Bottom right corner
	public CustomTexture game_menuobject_endturnbutton = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/endTurnBtn.png"));
	public CustomTexture game_menuobject_cornerframe = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/cornerframe.png"));
	public CustomTexture game_menuobject_infobutton = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/infobutton.png"));
		
	//Map
	public CustomTexture map = new CustomTexture(Gdx.files.internal("gameScreen/game_map/map.png"));
	public CustomTexture mapInfo = new CustomTexture(Gdx.files.internal("gameScreen/game_map/mapinfo.png"));
	
	//Pause Menu
	public CustomTexture game_pause_blackoutscreen = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_Pausemenu/screen.png"));
	public CustomTexture game_pause_pauselogo = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_Pausemenu/pauselogo.png"));
	public CustomTexture game_pause_resumegame = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_Pausemenu/resumegamebtn.png"));
	public CustomTexture game_pause_savegame = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_Pausemenu/savegamebtn.png"));
	public CustomTexture game_pause_settings = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_Pausemenu/settingsbtn.png"));
	public CustomTexture game_pause_mainmenu = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_Pausemenu/mainmenubtn.png"));
	public CustomTexture game_pause_background = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_Pausemenu/pausebackground.png"));
	
	//shop
	public CustomTexture game_shop_backdrop = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/shopbackdrop.png"));
	public CustomTexture game_shop_backbtn = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/backbtn.png"));
	public CustomTexture game_shop_shopbtn = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/shopbtn.png"));
	public CustomTexture game_shop_title = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/title.png"));
	//train depot
	public CustomTexture game_traindepot_title = new CustomTexture(Gdx.files.internal("gameScreen/game_traindepot/title.png"));
	public CustomTexture game_traindepot_traindepotbtn = new CustomTexture(Gdx.files.internal("gameScreen/game_traindepot/traindepotbtn.png"));

	//Goals
	public CustomTexture game_menuobject_ticketbtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/ticketbtn.png"));
	public CustomTexture game_menuobject_ticket = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/ticket.png"));
	public CustomTexture game_menuobject_emptyticket = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/emptyticket.png"));
	public CustomTexture game_menuobject_ticketenclosure = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/ticketenclosure.png"));
	public CustomTexture game_menuobject_removegoalbtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/removebtn.png"));
	public CustomTexture game_menuobject_redobtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/redobtn.png"));
	public CustomTexture game_menuobject_addgoalbtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/addgoalbtn.png"));
	//Routing
	public CustomTexture game_menuobject_planroutebtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/planroutebtn.png"));
	public CustomTexture game_menuobject_editroutebtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/editroutebtn.png"));
	public CustomTexture routingModeWindow = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/routingModeWindow.png")); 
	public CustomTexture confirmroutingModebtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/confirmRoute.png"));
	public CustomTexture undoRouteBtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/undoRoute.png")); 
	public CustomTexture abortRouteBtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/abortRoute.png")); 
	public CustomTexture cancelRouteBtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/game_tickets/routeCancel.png"));
	
	public CustomTexture game_goals_goalscreenbtn = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/goalScreenBtn.png"));
	public CustomTexture game_goals_newgoals= new CustomTexture(Gdx.files.internal("gameScreen/game_goalScreen/newgoals.png"));
	public CustomTexture game_goals_backdrop = new CustomTexture(Gdx.files.internal("gameScreen/game_goalScreen/screen.png"));

	//Cards
	public CustomTexture game_card_gofasterstripescard = new CustomTexture(Gdx.files.internal("gameScreen/game_cards/gofasterstripecard.png"));
	public CustomTexture game_card_teleportcard = new CustomTexture(Gdx.files.internal("gameScreen/game_cards/teleportCard.png"));
	public CustomTexture game_card_goldcard = new CustomTexture(Gdx.files.internal("gameScreen/game_cards/goldCard.png"));
	public CustomTexture game_card_coalcard = new CustomTexture(Gdx.files.internal("gameScreen/game_cards/coalCard.png"));
	public CustomTexture game_card_oilcard = new CustomTexture(Gdx.files.internal("gameScreen/game_cards/oilCard.png"));
	public CustomTexture game_card_electriccard = new CustomTexture(Gdx.files.internal("gameScreen/game_cards/electricCard.png"));
	public CustomTexture game_card_nuclearcard = new CustomTexture(Gdx.files.internal("gameScreen/game_cards/nuclearCard.png"));
	public CustomTexture game_card_cardtoggle = new CustomTexture(Gdx.files.internal("gameScreen/game_cards/Cardbtn-1.png"));
	public CustomTexture game_card_usecardbtn = new CustomTexture(Gdx.files.internal("gameScreen/game_cards/usecardbtn.png"));
		
	//SHOP
	public CustomTexture game_shop_startscreen = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/startpage.png"));
	public CustomTexture game_shop_startbuy = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/startBuy.png"));
	public CustomTexture game_shop_startsell = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/startSell.png"));
	public CustomTexture game_shop_starttrain = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/startTrains.png"));
	
	public CustomTexture game_shop_coalitem = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/item_coal.png"));
	public CustomTexture game_shop_oilitem = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/item_oil.png"));
	public CustomTexture game_shop_electricityitem = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/item_electricity.png"));
	public CustomTexture game_shop_nuclearitem = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/item_nuclear.png"));
	public CustomTexture game_shop_carditem = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/item_card.png"));
	public CustomTexture game_shop_trainitem = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/item_train.png"));
	
	public CustomTexture game_shop_addbtn = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/addbutton.png"));
	public CustomTexture game_shop_minusbtn = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/minusbutton.png"));
	public CustomTexture game_shop_buybtn = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/buybtn.png"));
	public CustomTexture game_shop_sellbtn = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/sellbtn.png"));
	public CustomTexture game_shop_blankbuybtn = new CustomTexture(Gdx.files.internal("gameScreen/game_shop/blankbuybtn.png"));
	
	//StartGame
	public CustomTexture game_start_getstartedwindow = new CustomTexture(Gdx.files.internal("gameScreen/game_startsequence/getstartedwindow.png"));
	public CustomTexture game_start_getstartedwindow2 = new CustomTexture(Gdx.files.internal("gameScreen/game_startsequence/getstartedwindow-witharrow.png"));
	
	//Warning Window
	public CustomTexture game_warningwindow = new CustomTexture(Gdx.files.internal("gameScreen/game_MenuObjects/warningwindow.png"));
	

	}
