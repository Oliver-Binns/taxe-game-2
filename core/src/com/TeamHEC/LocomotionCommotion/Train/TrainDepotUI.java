package com.TeamHEC.LocomotionCommotion.Train;

import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Sprite;
import com.TeamHEC.LocomotionCommotion.UI_Elements.SpriteButton;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

public class TrainDepotUI {
	public static Game_TrainDepotManager actorManager;

	public void create(Stage stage){
		actorManager= new Game_TrainDepotManager();
		actorManager.create(stage);
	}

	public static class Game_TrainDepotManager {

		private final static Array<Actor> actors = new Array<Actor>();

		public static Sprite game_traindepot_backdrop, game_traindepot_title;
		public static SpriteButton game_traindepot_backbtn;


		public boolean open=false;

		public static int  stageStart, stageEnd;


		public Game_TrainDepotManager(){	}

		public void create(Stage stage){
			actors.clear();
			stageStart= 0;
			stageEnd =0;
			game_traindepot_backdrop = new Sprite(-20,-20,Game_TextureManager.getInstance().game_shop_backdrop);
			actors.add(game_traindepot_backdrop);
			game_traindepot_title = new Sprite(170,780,Game_TextureManager.getInstance().game_traindepot_title);
			actors.add(game_traindepot_title);

			game_traindepot_backbtn = new SpriteButton(1350,800,Game_TextureManager.getInstance().game_shop_backbtn){
				@Override
				protected void onClicked(){
					if (TrainDepotUI.actorManager.open== false)
					{
						TrainDepotUI.actorManager.open= true;
						for(int i=TrainDepotUI.actorManager.getStageStart(); i<=TrainDepotUI.actorManager.getStageEnd();i++){
							if (i > GameScreen.getStage().getActors().size-1){

							}else
								GameScreen.getStage().getActors().get(i).setVisible(true);

						}			
					}
					else
					{	TrainDepotUI.actorManager.open= false;
					for(int i=TrainDepotUI.actorManager.getStageStart(); i<=TrainDepotUI.actorManager.getStageEnd();i++){
						if (i > GameScreen.getStage().getActors().size-1){

						}else
							GameScreen.getStage().getActors().get(i).setVisible(false);

					}

					}
				}
			};

			actors.add(game_traindepot_backbtn);




			stageStart= stage.getActors().size;
			stageEnd= stageStart + actors.size-1;
			for (Actor a : actors){
				if(open == true){
					a.setTouchable(Touchable.enabled);
					a.setVisible(true);}
				else
					a.setVisible(false);

				stage.addActor(a);

			}



		}
		public  int getStageStart(){
			return stageStart;
		}
		public  int getStageEnd(){
			return stageEnd;
		}
	}


}
