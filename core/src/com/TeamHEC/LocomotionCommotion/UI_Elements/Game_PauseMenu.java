package com.TeamHEC.LocomotionCommotion.UI_Elements;

import com.TeamHEC.LocomotionCommotion.LocomotionCommotion;
import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
/**
 * 
 * @author Robert Precious <rp825@york.ac.uk>
 * Pause Menu does what it says on the tin. 
 *
 */
public class Game_PauseMenu {

	public static Game_Pause_Manager actorManager;

	public void create(Stage stage){
		actorManager = new Game_Pause_Manager();
		actorManager.create(stage);
	}
	public static class Game_Pause_Manager {


		private final static Array<Actor> actors = new Array<Actor>();

		public  static Sprite game_pause_blackoutscreen, game_pause_background, game_pause_logo;
		public  SpriteButton game_pause_resume,game_pause_save, game_pause_settings, game_pause_mainmenu;



		public boolean open=false;

		public static int  stagestart, stageend;


		public Game_Pause_Manager(){	}

		public void create(Stage stage){
			stagestart =0;
			actors.clear();



			game_pause_blackoutscreen = new Sprite(0, 0, Game_TextureManager.getInstance().game_pause_blackoutscreen);
			actors.add(game_pause_blackoutscreen);
			game_pause_background = new Sprite(550, 100, Game_TextureManager.getInstance().game_pause_background);
			actors.add(game_pause_background);
			game_pause_logo = new Sprite(740, 700, Game_TextureManager.getInstance().game_pause_pauselogo);
			actors.add(game_pause_logo);

			game_pause_resume = new SpriteButton(590,550,Game_TextureManager.getInstance().game_pause_resumegame){
				@Override
				protected void onClicked(){
					if (Game_PauseMenu.actorManager.open== false)
					{
						Game_PauseMenu.actorManager.open= true;
						for(int i=Game_PauseMenu.actorManager.getStageStart(); i<=Game_PauseMenu.actorManager.getStageEnd();i++){
							if (i > GameScreen.getStage().getActors().size-1){

							}else
								GameScreen.getStage().getActors().get(i).setVisible(true);

						}			}
					else
					{	Game_PauseMenu.actorManager.open= false;
					for(int i=Game_PauseMenu.actorManager.getStageStart(); i<=Game_PauseMenu.actorManager.getStageEnd();i++){
						if (i > GameScreen.getStage().getActors().size-1){

						}else
							GameScreen.getStage().getActors().get(i).setVisible(false);

					}

					}

				}
			};
			actors.add(game_pause_resume);
			game_pause_save = new SpriteButton(Game_PauseMenu.actorManager.game_pause_resume.getX(),Game_PauseMenu.actorManager.game_pause_resume.getY()-100,Game_TextureManager.getInstance().game_pause_savegame){
				@Override
				protected void onClicked(){
				}

			};
            // Not yet implemented. Hidden.
            // actors.add(game_pause_save);
			game_pause_settings = new SpriteButton(Game_PauseMenu.actorManager.game_pause_resume.getX(),Game_PauseMenu.actorManager.game_pause_resume.getY()-200,Game_TextureManager.getInstance().game_pause_settings){
				@Override
				protected void onClicked(){
				}
			};
            // Not yet implemented. Hidden.
            // actors.add(game_pause_settings);
			game_pause_mainmenu = new SpriteButton(Game_PauseMenu.actorManager.game_pause_resume.getX(),Game_PauseMenu.actorManager.game_pause_resume.getY()-300,Game_TextureManager.getInstance().game_pause_mainmenu){
				@Override
				protected void onClicked(){
					LocomotionCommotion.getInstance().setMenuScreen();
				}
			};
			actors.add(game_pause_mainmenu);




			stagestart= stage.getActors().size;
			stageend = stagestart+actors.size-1;
			for (Actor a : actors){
				if(open == true){
					a.setTouchable(Touchable.enabled);
					a.setVisible(true);}
				else
					a.setVisible(false);

				stage.addActor(a);
			}



		}

		public int getStageStart(){
			return stagestart;
		}
		public int getStageEnd(){
			return stageend;
		}





	}

}
