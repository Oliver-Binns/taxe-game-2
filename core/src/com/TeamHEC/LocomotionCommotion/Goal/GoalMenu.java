package com.TeamHEC.LocomotionCommotion.Goal;
/**
 * @author Robert Precious <rp825@york.ac.uk>
 * 
 * This class is a Manager for all the Goal Actors (excluding the the side menu of goals which is Managed by PlayerGaals)
 * Calls the goalcreator to turn an array of Goal to the UI goals
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;

import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Sprite;
import com.TeamHEC.LocomotionCommotion.UI_Elements.SpriteButton;
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
import com.badlogic.gdx.utils.Array;
/**
 * 
 * @author Robert Precious
 * 	Goal Menu is where the player can select new goals. 
 *	The create method: Instantiates all goalActors and the corresponding labels. All empty to start with.
 *
 */
public class GoalMenu {
	//Arrays
	private final static Array<Actor> actors = new Array<Actor>();
	private final static Array<Actor> subactors = new Array<Actor>();
	//ArrayLists
	public static ArrayList<GoalActor> createdGoals;
	//HashMaps
	public static HashMap<String,Goal> newgoals ;
	public static HashMap<String, Label> goalLabels ;
	//Actors
	public static GoalActor newgoal1, newgoal2,newgoal3,newgoal4,newgoal5,newgoal6,newgoal7,newgoal8,newgoal9, selectedGoal;
	//Ints
	public static int  stagestart, goalActors;
	public static int row1 = 630, row2 = row1-220, row3 = row2-220;
	public static int col1 = 670, col2 = col1+320, col3 = col2+320;
	//Label and LabelStyle
	public static Label gLabel1,gLabel2,gLabel3,gLabel4,gLabel5,gLabel6,gLabel7,gLabel8,gLabel9;
	public static LabelStyle style;
	//Booleans
	public static boolean open=false;

	public static int numberofGoalsOnScreen;

	public static Sprite backDrop;
	public static SpriteButton backBtn, addGoalBtn, refreshGoalsBtn;


	public GoalMenu(){	}

	public void create(Stage stage){
		//reset Array for newGame
		actors.clear();
		//Reset Actor ranging values
		stagestart =0;
		goalActors=0;
		numberofGoalsOnScreen=0;

		//Actors
		backDrop = new Sprite(-1,-35, Game_TextureManager.getInstance().game_goals_backdrop);
		actors.add(backDrop);

		backBtn = new SpriteButton(1350,880,Game_TextureManager.getInstance().game_shop_backbtn){
			@Override
			protected void onClicked(){
				if (GoalMenu.open== false)
				{
					GoalMenu.open= true;
					PlayerGoals.goalMenuOpen();
					for(int i=GoalMenu.stagestart; i<=GoalMenu.stagestart +GoalMenu.goalActors-1;i++){
						if (i > GameScreen.getStage().getActors().size-1){

						}else
							GameScreen.getStage().getActors().get(i).setVisible(true);

					}			}
				else
				{	GoalMenu.open= false;
				PlayerGoals.goalMenuClose();
				for(int i=GoalMenu.stagestart; i<=GoalMenu.stagestart +GoalMenu.goalActors-1;i++){
					if (i > GameScreen.getStage().getActors().size-1){

					}else
						GameScreen.getStage().getActors().get(i).setVisible(false);

				}

				}
			}
		};
		actors.add(backBtn);

		addGoalBtn = new SpriteButton(720,575,Game_TextureManager.getInstance().game_menuobject_addgoalbtn){
			@Override
			protected void onClicked(){
				if (PlayerGoals.addGoal(GoalMenu.selectedGoal))
				{ 	
					GoalMenu.selectedGoal.setEmpty(true);								//Sets the slot from which the goal is added to empty
					String a = new Integer(GoalMenu.selectedGoal.getIndex()).toString();	//turns the goal index int to a string
					GoalMenu.goalLabels.get(a).setText("");;								//clears the label (text on the ticket)
					this.setVisible(false);	
				}
			}
			@Override
			protected void onMouseEnter(){
				started = true;

			}
			@Override
			public void act(float delta){
				if(started){
					this.setVisible(true);
					started= false;
				}
			}
		};
		subactors.add(addGoalBtn);

		refreshGoalsBtn = new SpriteButton(Gdx.graphics.getWidth()-100,Gdx.graphics.getHeight()-250,Game_TextureManager.getInstance().game_menuobject_redobtn){
			@Override
			protected void onClicked(){
				//Can Refresh Goals
			}
		};
		//actors.add(refreshGoalsBtn);



		createdGoals =createEmpties();	

		//Assign blanks to the actors
		newgoal1 = createdGoals.get(0);
		actors.add(newgoal1);
		newgoal2 = createdGoals.get(1);
		actors.add(newgoal2);
		newgoal3 = createdGoals.get(2);
		actors.add(newgoal3);

		newgoal4 = createdGoals.get(3);
		actors.add(newgoal4);
		newgoal5 = createdGoals.get(4);
		actors.add(newgoal5);
		newgoal6 = createdGoals.get(5);
		actors.add(newgoal6);

		newgoal7 = createdGoals.get(6);
		actors.add(newgoal7);
		newgoal8 = createdGoals.get(7);
		actors.add(newgoal8);
		newgoal9 = createdGoals.get(8);
		actors.add(newgoal9);

		//Stuff for Labels
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gillsans.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 17;

		BitmapFont font = generator.generateFont(parameter); 
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		style = new LabelStyle();
		style.font = font;

		//end
		goalLabels = createLabels();				//createLabels
		addLabelstoStage();							//Call function to add labels to stage

		//Adds the actors to the stage
		stagestart= stage.getActors().size;
		for (Actor a : actors){
			if(open == true){
				a.setTouchable(Touchable.enabled);
				a.setVisible(true);}
			else
				a.setVisible(false);

			stage.addActor(a);
			goalActors ++;
		}
		
		//Used for other actors we don't want to show straight away
		for (Actor a : subactors){
			a.setTouchable(Touchable.enabled);
			a.setVisible(false);
			stage.addActor(a);
		}


	}
	
	//Create Empty Goals
	private ArrayList<GoalActor> createEmpties() {
		ArrayList<GoalActor> 		empties = new ArrayList< GoalActor>();
		HashMap<String, GoalActor> 	goalslots = new HashMap<String, GoalActor>();

		goalslots = createSlots();

		for (int i=0;i<9;i++){
			String a = new Integer(i+1).toString();
			empties.add(goalslots.get(a));
		}
		return empties;
	}
	private HashMap<String, GoalActor> createSlots() {
		int row1 = 550, row2 = row1-220, row3 = row2-220;
		int col1 = 660, col2 = col1+320, col3 = col2+320;
		HashMap<String, GoalActor> goalslots = new HashMap<String, GoalActor>();
		goalslots.put("1", newgoal1= new GoalActor(row1,col1,true,null));
		goalslots.put("2", newgoal2= new GoalActor(row1,col2,true,null));
		goalslots.put("3", newgoal3= new GoalActor(row1,col3,true,null));
		goalslots.put("4", newgoal4= new GoalActor(row2,col1,true,null));
		goalslots.put("5", newgoal5= new GoalActor(row2,col2,true,null));
		goalslots.put("6", newgoal6= new GoalActor(row2,col3,true,null));
		goalslots.put("7", newgoal7= new GoalActor(row3,col1,true,null));
		goalslots.put("8", newgoal8= new GoalActor(row3,col2,true,null));
		goalslots.put("9", newgoal9= new GoalActor(row3,col3,true,null));
		return goalslots;
	}
	
	
	//Creates the blank labels
	public static  HashMap<String, Label> createLabels(){
		//Create the Labels in a Hashmap and run through them
		HashMap<String, Label> goals = new HashMap<String, Label>();

		goals.put("1", gLabel1= new Label(null,style));
		goals.put("2", gLabel2= new Label(null,style));
		goals.put("3", gLabel3= new Label(null,style));
		goals.put("4", gLabel4= new Label(null,style));
		goals.put("5", gLabel5= new Label(null,style));
		goals.put("6", gLabel6= new Label(null,style));
		goals.put("7", gLabel7= new Label(null,style));
		goals.put("8", gLabel8= new Label(null,style));
		goals.put("9", gLabel9= new Label(null,style));
		int row =1;
		int col =1;

		for(int g=0;g<9;g++){
			String a = new Integer(g+1).toString();
			goals.get(a).setColor(0,0,0,1);

			if(col==1)
				goals.get(a).setX(col1);
			if(col==2)
				goals.get(a).setX(col2);
			if(col==3)
				goals.get(a).setX(col3);

			if(row==1)
				goals.get(a).setY(row1);
			if(row==2)
				goals.get(a).setY(row2);
			if(row==3)
				goals.get(a).setY(row3);

			//Next Row
			if (col==3){
				col=1;
				row+=1;
			}
			else
				col+=1;


		}

		return goals;

	}
	//Adds all labels to stage
	public static void addLabelstoStage(){
		actors.add(gLabel1);
		actors.add(gLabel2);
		actors.add(gLabel3);
		actors.add(gLabel4);
		actors.add(gLabel5);
		actors.add(gLabel6);
		actors.add(gLabel7);
		actors.add(gLabel8);
		actors.add(gLabel9);


	}
	//Creates the string that make up the ticket information
	public static String ticketMaker(String type, int reward, String from, String startdate, String dest, String route){
		String output;
		output ="";

		output += type + getSpacing((type.length() + (reward+"").length())) + (reward+""); 
		output += "\n\n";
		output += from + getSpacing((from.length() + startdate.length())) + startdate; 
		output += "\n\n";
		output += dest + getSpacing((dest.length() + route.length())) + route;
		return output;

	}
	//Adds spacing for Labels
	public static String getSpacing(int len){
		String space="";
		for (int i=0; i<41-len; i++){
			space += " ";

		}
		return space;
	}
	
	/**
	 * Runs through the goals given finding them empty slots (by calling findEmptyGoalSlot) and adding them to the GoalMenu.
	 * @param goals -ArrayList of goals to be added to the GoalMenu.
	 */
	public static void AddGoalToScreen(ArrayList<Goal> goals){
		for (int i=0;i<goals.size();i++){
			if (numberofGoalsOnScreen==9){
				break;
			}
			int emptyspace= findEmptyGoalSlot(goalLabels);
			String a = new Integer(emptyspace+1).toString();

			goalLabels.get(a).setText(ticketMaker(	goals.get(i).getCargo(),
					goals.get(i).getReward(),
					goals.get(i).getSStation(),
					goals.get(i).getStartDate(), 
					goals.get(i).getFStation(), 
					goals.get(i).getTimeConstraintString()));
			createdGoals.get(emptyspace).setGoal(goals.get(i));
			createdGoals.get(emptyspace).setEmpty(false);
			createdGoals.get(emptyspace).setIndex(emptyspace+1);
			numberofGoalsOnScreen++;
		}


	}
	/**
	 * 
	 * @param newgoalLabels - the current Goal labels.
	 * @return - An empty GoalMenu slot index
	 */
	public static int findEmptyGoalSlot(HashMap<String, Label> newgoalLabels){
		for (int i=0;i<9;i++){
			String a = new Integer(i+1).toString();
			if( newgoalLabels.get(a).getText().length==0){
				return i;

			}
		}
		return 0;

	}
	
	/**
	 * FillGoalScreen() fills the goal screen by running through 9 createRandomGoal calls and sends the array to Add Goal to Screen.
	 */
	public static void fillGoalScreen(){
		ArrayList<Goal> goals = new ArrayList<Goal>();
		GoalFactory factory = new GoalFactory(GameScreen.game.getTurnCount());
		
		for (int i=0; i<3; i++){
				goals.add(factory.generateGoal(factory.EASY));
				goals.add(factory.generateGoal(factory.MEDIUM));
				goals.add(factory.generateGoal(factory.HARD));
		}
		GoalMenu.AddGoalToScreen(goals);
	}

	/*
	 * Serializes all actors and stores them in an array. This and the Game object
	 * are then saved and stored to be loaded.
	 */
}

