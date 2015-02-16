package com.TeamHEC.LocomotionCommotion.UI_Elements;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.LocomotionCommotion;
import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Goal.GoalFactory;
import com.TeamHEC.LocomotionCommotion.Goal.GoalMenu;
import com.TeamHEC.LocomotionCommotion.Goal.PlayerGoals;
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
 * 
 * @author Robert Precious <rp825@york.ac.uk>
 * 
 *
 */
public class Game_StartingSequence {

	private final static Array<Actor> actors = new Array<Actor>();
	public static int stagestart ;
	public static int startGameActors;
	public static Label selectLabel;
	public static boolean player1 = true, inProgress = true;
	public static SpriteButton getStartedWindow;

	public Game_StartingSequence(){}

	public void create(Stage stage)
	{
		actors.clear();
		stagestart =0;
		startGameActors=0;
		player1= true;
		inProgress = true;

		getStartedWindow= new SpriteButton(300,400,Game_TextureManager.getInstance().game_start_getstartedwindow){
			@Override
			protected void onClicked(){
				Game_StartingSequence.selectLabel.setVisible(false);
				this.setVisible(false);
				
			}
		};
		actors.add(getStartedWindow);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gillsans.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 40;

		BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		LabelStyle style = new LabelStyle();
		style.font = font;

		selectLabel = new Label(null, style);
		selectLabel.setText(LocomotionCommotion.player1name + " please select your start station!");
		selectLabel.setColor(0,0,0,1);
		selectLabel.setAlignment(Align.center);
		selectLabel.setX(790);
		selectLabel.setY(575);

		actors.add(selectLabel);

		stagestart= stage.getActors().size;
		for (Actor a : actors){
			a.setTouchable(Touchable.enabled);
			stage.addActor(a);
			startGameActors ++;
		}

	}

	public static void startGame(){

		for(int i=GameScreenUI.getStageStart(); i<=GameScreenUI.getStageEnd();i++)	
		{ 	
			if (i > GameScreen.getStage().getActors().size-1)
			{//This is just to avoid range errors
			}
			else
				GameScreen.getStage().getActors().get(i).setVisible(true);
		}

		//Handle Text within Game
		//Score and Who's Turn it is
		GameScreenUI.playerScore.setText(GameScreen.game.getPlayer1().getName()+"    " + 0 +
				"     SCORE     "+ 0+"     "+GameScreen.game.getPlayer2().getName());
		GameScreenUI.currentPlayerName.setText(GameScreen.game.getPlayerTurn().getName()+"'s TURN");
		//Resources
		GameScreenUI.goldQuant.setText(""+GameScreen.game.getPlayerTurn().getGold());
		GameScreenUI.coalQuant.setText(""+GameScreen.game.getPlayerTurn().getFuel("Coal"));
		GameScreenUI.oilQuant.setText(""+GameScreen.game.getPlayerTurn().getFuel("Oil"));
		GameScreenUI.electricityQuant.setText(""+GameScreen.game.getPlayerTurn().getFuel("Electric"));
		GameScreenUI.nuclearQuant.setText(""+GameScreen.game.getPlayerTurn().getFuel("Nuclear"));
		GameScreenUI.cardQuant.setText(""+GameScreen.game.getPlayerTurn().getCards().size());

		GameScreenUI.game_card_togglebtn.setVisible(true);
		GameScreenUI.cardQuant.setVisible(true);
		PlayerGoals.changePlayer(GameScreen.game.getPlayerTurn());
		fillGoalScreen();
	}

	public static void fillGoalScreen(){
		ArrayList<Goal> goals = new ArrayList<Goal>();
		GoalFactory factory = new GoalFactory(1);
		
		for (int i=0; i<3; i++){
				goals.add(factory.generateGoal(factory.EASY));
				goals.add(factory.generateGoal(factory.MEDIUM));
				goals.add(factory.generateGoal(factory.HARD));
		}
		GoalMenu.AddGoalToScreen(goals);
	}
	public static void reset(){
		actors.clear();
		stagestart =0;
		startGameActors=0;
		player1= true;
		inProgress = true;
	}

}
