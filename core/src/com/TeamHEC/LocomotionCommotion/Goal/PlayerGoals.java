package com.TeamHEC.LocomotionCommotion.Goal;

import java.util.ArrayList;
import java.util.HashMap;

import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;
import com.TeamHEC.LocomotionCommotion.UI_Elements.SpriteButton;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
/**
 * 
 * @author Robert Precious <rp825@york.ac.uk>
 * 
 * PlayerGoals are the (up to) 3 goals that the player owns. We have the actual Goals, the Goal Actors and the Labels (like GoalMenu) with the addition
 * of the removeButtons which can remove goals or undo a goal choice from the goal menu.
 * 
 * 
 *
 */
public class PlayerGoals {

	private final static Array<Actor> actors = new Array<Actor>();
	private final static Array<Actor> subactors = new Array<Actor>();

	public static 	HashMap<String,Goal> playerGoals ;
	public static 	HashMap<String,GoalActor> playerGoalActors;
	private static 	HashMap<String, PlayerGoalRemoveBtn> removebuttons ;
	private static 	HashMap<String, Label> ticketLabels ;

	public static GoalActor newgoal1, newgoal2, newgoal3;

	public Label ticket1, ticket2, ticket3;
	public LabelStyle style;

	public static PlayerGoalRemoveBtn removebtn1,removebtn2,removebtn3;

	public static boolean open=false;

	public static int stagestart, ticketActors, numberofOwnedGoals;

	public static SpriteButton planRouteBtn;

	public static Goal selectedGoal;
	public static GoalActor selectedGoalActor;
	public static boolean chooseTrain = false;

	public PlayerGoals(){	}

	public void create(Stage stage){

		actors.clear();
		subactors.clear();

		stagestart =0;
		ticketActors=0;

		playerGoals = new HashMap<String,Goal>();
		playerGoalActors = new HashMap<String,GoalActor>();

		numberofOwnedGoals=playerGoals.size();

		newgoal1= new GoalActor(1050-290,5,true,null);
		newgoal2= new GoalActor(1050-490,5,true,null);
		newgoal3= new GoalActor(1050-690,5,true,null);

		playerGoalActors.put("1", newgoal1);
		playerGoalActors.put("2", newgoal2);
		playerGoalActors.put("3", newgoal3);

		/**
		 * Plan Route Button
		 * This button is only shown when we move the mouse over a player goal which is not empty, because of the mouse over action for the goal
		 * will end if we enter this button we need to keep this visible. This is what started is for. We separate the touched down function (onClicked Method)
		 * so we can apply a different action entirely.
		 */
		planRouteBtn= new SpriteButton(0,0,Game_TextureManager.getInstance().game_menuobject_planroutebtn){

			boolean touchedDown;
			@Override
			protected void onClicked()
			{
				touchedDown = true;
			}
			protected void onMouseEnter()
			{
				started= true;
			}

			@Override
			public void act(float delta){
				if(started){
					this.setVisible(true);
					started = false;
				}
				if(touchedDown)
				{
					WarningMessage.fireWarningWindow("", "Please Select a Train");
					chooseTrain = true;
					touchedDown=false;
					started =false;
				}
			}
		};
		planRouteBtn.setVisible(false);

		//^^^^^^^^^^^  added to the stage after everything else see end of method




		//Ticket Labels
		style = GameScreenUI.getLabelStyle(17);
		ticketLabels = new HashMap<String, Label>();
		ticketLabels.put("1", ticket1= new Label(null,style));
		ticketLabels.put("2", ticket2= new Label(null,style));
		ticketLabels.put("3", ticket3= new Label(null,style));
		//Remove Buttons
		removebuttons = new HashMap<String, PlayerGoalRemoveBtn>();
		removebuttons.put("1", removebtn1 = new PlayerGoalRemoveBtn(250, 470, Game_TextureManager.getInstance().game_menuobject_removegoalbtn, 1));
		removebuttons.put("2", removebtn2 = new PlayerGoalRemoveBtn(250, 470, Game_TextureManager.getInstance().game_menuobject_removegoalbtn, 2));
		removebuttons.put("3", removebtn3 = new PlayerGoalRemoveBtn(250, 470, Game_TextureManager.getInstance().game_menuobject_removegoalbtn, 3));
		for (int i=0; i<3; i++){
			String a = new Integer(i+1).toString();
			removebuttons.get(a).setVisible(false);

		}

		actors.add(newgoal1);
		actors.add(newgoal2);
		actors.add(newgoal3);

		actors.add(ticket1);
		actors.add(ticket2);
		actors.add(ticket3);
		subactors.add(removebtn1);
		subactors.add(removebtn2);
		subactors.add(removebtn3);





		stagestart= stage.getActors().size;
		for (Actor a : actors){
			if(open == true){
				a.setTouchable(Touchable.enabled);
				a.setVisible(true);}
			else
				a.setVisible(false);
			stage.addActor(a);
			ticketActors ++;
		}
		for (Actor b : subactors){
			b.setTouchable(Touchable.enabled);
			b.setVisible(false);

			stage.addActor(b);
		}
		stage.addActor(planRouteBtn);




	}
	/**
	 * TicketMaker creates the Label using the Goal attributes.
	 */
	public static String ticketMaker(String type, int reward, String from, String startdate, String dest, String route){
		String output;
		output ="";

		output += type + getSpacing(type.length() + (reward+"").length()) + reward; 
		output += "\n\n";
		output += from + getSpacing(from.length() + startdate.length()) + startdate; 
		output += "\n\n";
		output += dest + getSpacing(dest.length() + route.length()) + route;
		return output;

	}
	/**
	 * Finds the spacing needed for formatting the ticket.
	 */
	public static String getSpacing(int len){
		String space="";
		for (int i=0; i<41-len; i++){
			space += " ";

		}
		return space;
	}

	/**
	 * Moves the playerGoals in to place when the goal menu is opened. This is used to avoid having to create more objects.
	 */
	public static void goalMenuOpen() {

		//Move Tickets
		for (Actor a: actors){
			a.setVisible(true);
			a.setX(a.getX()+150);
			a.setY(a.getY()-200);

		}
		//Hide goal side menu
		GameScreenUI.game_menuobject_ticketenclosure.setVisible(false);
		GameScreenUI.game_menuobject_tickettoggle.setVisible(false);
		//Put the remove Buttons in the correct place
		for (int i=0;i<numberofOwnedGoals; i++){
			String a = new Integer(i+1).toString();
			removebuttons.get(a).setVisible(true);
			removebuttons.get(a).refreshBounds();

		}


	}
	/**
	 * Moves the playerGoals in to place when the goal menu is closed. This is used to avoid having to create more objects.
	 */
	public static void goalMenuClose() {
		for (Actor a: actors){
			a.setVisible(false);
			a.setX(a.getX()-150);
			a.setY(a.getY()+200);
		}
		for (int i=0;i<3; i++){
			String a = new Integer(i+1).toString();
			removebuttons.get(a).setVisible(false);
			removebuttons.get(a).setUndo(false);

		}
		GameScreenUI.game_menuobject_ticketenclosure.setVisible(false);
		GameScreenUI.game_menuobject_tickettoggle.setVisible(true);



	}
/**
 * RemoveGoal takes a goal index, removes that goal and shuffles the others up (if needed)
 * @param goalIndex - the index of the goal you want to remove from PlayerGoals.
 */
	public static void removeGoal(int goalIndex){		
		if(goalIndex==1){
			ticketLabels.get("1").setText(ticketLabels.get("2").getText());
			playerGoalActors.get("1").setGoal(playerGoalActors.get("2").getGoal());
			removebuttons.get("1").setUndo(removebuttons.get("2").getUndo());
			removebuttons.get("1").setnewgoalindex(removebuttons.get("2").getnewgoalindex());

			ticketLabels.get("2").setText(ticketLabels.get("3").getText());
			playerGoalActors.get("2").setGoal(playerGoalActors.get("3").getGoal());
			removebuttons.get("2").setUndo(removebuttons.get("3").getUndo());
			removebuttons.get("2").setnewgoalindex(removebuttons.get("3").getnewgoalindex());

			if (playerGoalActors.get("2").isEmpty()){
				ticketLabels.get("1").setText("");
				playerGoalActors.get("1").setEmpty(true);
				removebuttons.get("1").setVisible(false);
				removebuttons.get("1").resetButtons();
			}

			if (playerGoalActors.get("3").isEmpty()){
				ticketLabels.get("2").setText("");
				playerGoalActors.get("2").setEmpty(true);
				removebuttons.get("2").setVisible(false);
			}


		}
		if (goalIndex ==2){
			playerGoalActors.get("2").setGoal(playerGoalActors.get("3").getGoal());
			ticketLabels.get("2").setText(ticketLabels.get("3").getText());
			removebuttons.get("2").setUndo(removebuttons.get("3").getUndo());
			removebuttons.get("2").setUndo(removebuttons.get("3").getUndo());
			removebuttons.get("2").setnewgoalindex(removebuttons.get("3").getnewgoalindex());

			if (playerGoalActors.get("3").isEmpty()){
				ticketLabels.get("2").setText("");
				playerGoalActors.get("2").setEmpty(true);
				removebuttons.get("2").setVisible(false);
				removebuttons.get("2").resetButtons();
			}
		}
		ticketLabels.get("3").setText("");
		playerGoalActors.get("3").setEmpty(true);
		removebuttons.get("3").setVisible(false);
		removebuttons.get("3").resetButtons();

		numberofOwnedGoals-=1;
		GameScreen.game.getPlayerTurn().getGoals().remove(goalIndex-1);


		if (numberofOwnedGoals==0)
			numberofOwnedGoals=0;
	}
	
	/**
	 * AddGoal takes a new Goal Actor and adds it to player goals by taking its goal attributes and adding it to the bottom of the 
	 * current player goals.
	 * @param newgoal the GoalMenu GoalActor which you want to add to the playerGoals
	 * @return Returns true if successful and false if not.
	 */
	public static boolean addGoal(GoalActor newgoal){
		if (numberofOwnedGoals>2){
			return false;
		}
		else
		{
			String a = new Integer(numberofOwnedGoals+1).toString();
			ticketLabels.get(a).setText(ticketMaker(newgoal.getGoal().getCargo(),
					newgoal.getGoal().getReward(),
					newgoal.getGoal().getSStation(),
					newgoal.getGoal().getStartDate(), 
					newgoal.getGoal().getFStation(), 
					newgoal.getGoal().getTimeConstraintString())
					);
			ticketLabels.get(a).setColor(0,0,0,1);

			playerGoalActors.get(a).setGoal(newgoal.getGoal());
			playerGoalActors.get(a).setEmpty(false);
			playerGoalActors.get(a).setOwnedgoal(true);
			removebuttons.get(a).setVisible(true);
			removebuttons.get(a).setRedoBtn();
			removebuttons.get(a).refreshBounds();
			removebuttons.get(a).setnewgoalindex(newgoal.getIndex());


			numberofOwnedGoals+=1;
			GameScreen.game.getPlayerTurn().getGoals().add(newgoal.getGoal());

			GoalMenu.numberofGoalsOnScreen--;
			return true;
		}
	}
	
	/**
	 * undoGoalSelection puts back the goal you just picked from the goal menu. You cannot do this once you leave the GoalMenu. 
	 * @param index -The index of the player goal the player wants to put back.
	 */
	public static void undoGoalSelection(int index) {
		String a = new Integer(index).toString();
		String b = new Integer(removebuttons.get(a).getnewgoalindex()).toString();
		GoalMenu.goalLabels.get(b).setText(ticketLabels.get(a).getText());
		GoalMenu.createdGoals.get(removebuttons.get(a).getnewgoalindex()-1).setGoal(playerGoalActors.get(a).getGoal());
		GoalMenu.createdGoals.get(removebuttons.get(a).getnewgoalindex()-1).setEmpty(false);
		removeGoal(removebuttons.get(a).index);
		GoalMenu.numberofGoalsOnScreen++;


	}

	/**
	 * Change Player is used when we end turn to get the goals of the player whose turn it is.
	 * @param player -current Player.
	 */
	public static void changePlayer(Player player) {
		//x and y values for the top ticket and button
		float tickety= 845, buttony = 725;
		//The current player's goals.
		ArrayList<Goal> playerGoals = player.getGoals();
		//The Number of goals the current player has.
		numberofOwnedGoals=playerGoals.size();
		//Run through the goal the player OWNS creating the player goal actors.
		for (int i=0; i<playerGoals.size();i++){
			String a = new Integer(i+1).toString();

			playerGoalActors.get(a).setGoal(playerGoals.get(i));
			playerGoalActors.get(a).setEmpty(false);
			playerGoalActors.get(a).setIndex(i+1);

			ticketLabels.get(a).setColor(0,0,0,1);
			ticketLabels.get(a).setX(15);
			ticketLabels.get(a).setY(tickety);
			tickety-=200;
			ticketLabels.get(a).setText(ticketMaker(	playerGoalActors.get(a).getGoal().getCargo(),
					playerGoalActors.get(a).getGoal().getReward(),
					playerGoalActors.get(a).getGoal().getSStation(),
					playerGoalActors.get(a).getGoal().getStartDate(), 
					playerGoalActors.get(a).getGoal().getFStation(), 
					playerGoalActors.get(a).getGoal().getTimeConstraintString())
					);
			removebuttons.get(a).setX(400);
			removebuttons.get(a).setY(buttony);
			buttony-=200;
			removebuttons.get(a).setVisible(false);
		}
		//Run through the empties. Slots not filled by the player goals.
		for (int i=playerGoals.size();i<3;i++){
			String a = new Integer(i+1).toString();
			playerGoalActors.get(a).setEmpty(true);
			ticketLabels.get(a).setColor(0,0,0,1);
			ticketLabels.get(a).setX(15);
			ticketLabels.get(a).setY(tickety);
			tickety-=200;
			ticketLabels.get(a).setText("");
			removebuttons.get(a).setX(400);
			removebuttons.get(a).setY(buttony);
			removebuttons.get(a).setVisible(false);
			buttony-=200;
		}

	}




}
/*
 * Serializes all actors and stores them in an array. This and the Game object
 * are then saved and stored to be loaded.
 */