package com.TeamHEC.LocomotionCommotion.Goal;

import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * @author Robert Precious <rp825@york.ac.uk>
 * The Goal Actor is used in the goal menu and in playergoal's meaning it needs to keep track of which one it is. It can also be empty which mean it 
 * shows a blank texture and does nothing. 
 * 
 * This is an Actor- meaning it's given texture is displayed on the stage and actions (acts) can be performed.
 * @param texture	The image used for the Actor pulled in from SM_TextureManager (see documentation)
 * @param actorX	The x coordinate of the bottom left corner of the image
 * @param actorY	The y coordinate of the bottom left corner of the image
 * @param started	Boolean used to show if an Actor has been moused over. Used to stop and start interactions.
 * @param touchDown	Boolean used to show if an Actor has been clicked. Used to stop and start interactions.
 * @param empty		Boolean for if the goal is empty (blank- without goal)
 * @param addGoalButtonVisible Boolean for if the addGoalButton is visible 
 * @param planRouteButtonVisible Boolean for if the planRouteButton is visible 
 * @param goal		Goal is the goal object that the ticket represents.
 * @param index		index is a marker for which ticket it is in the grid.
 * @param ownedGoal the boolean for whether the goal is a player goal or one from the goal menu.
 * 
 * setBounds	This is the bounds for the interaction, we make it the whole image.
 * addListener	This adds a listener for a particular interaction in this case touchDown (click)
 * draw			Actor is drawn
 * act			The action taken if the listener detects interaction
 * 				Action- None
 */
public class GoalActor extends Actor {
	boolean started = false;
	boolean touchedDown = false;

	private Texture texture;
	private float actorX;
	private float actorY;

	private boolean empty, addGoalButtonVisible;
	private boolean ownedgoal, planRouteButtonVisible;

	private Goal goal;
	public int index;

	public GoalActor( int actorY, int actorX, boolean empty, Goal goal){
		this.goal = goal;
		this.empty= empty;
		this.index =0;

		//This block decides what type of ticket to display empty, standard or special
		if (this.empty)
			this.texture = Game_TextureManager.getInstance().game_menuobject_emptyticket;
		else
		{
			if (this.goal.isSpecial()==false)
				this.texture = Game_TextureManager.getInstance().game_menuobject_ticket;
			else
				this.texture = Game_TextureManager.getInstance().game_menuobject_ticket;

		}

		this.actorX = actorX;
		this.actorY = actorY;
		this.addGoalButtonVisible=false;
		this.setPlanRouteButtonVisible(false);

		setBounds(actorX,actorY,texture.getWidth(),texture.getHeight());
		//Mouse click listener - not used yet
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				((GoalActor)event.getTarget()).touchedDown = true;
				return true;
			}
		});
		//Mouse enter listener
		addListener(new InputListener(){
			public void enter(InputEvent event, float x, float y, int pointer, Actor ScreenCard) {
				((GoalActor)event.getTarget()).started = true;
			}

		});
		//Mouse exit listener
		addListener(new InputListener(){
			public void exit(InputEvent event, float x, float y, int pointer, Actor ScreenCard) {
				((GoalActor)event.getTarget()).started = true;
			}

		});

	}


	@Override
	public void draw(Batch batch, float alpha){
		batch.draw(this.texture,actorX,actorY);
	}

	@Override
	public void act(float delta){
		if(started){
			if(this.isOwnedgoal()){ //First Checks if Owned (A Player Goal)

				if(!this.isEmpty()) //Checks that the goalActor has a Goal assigned to it and isn't a blank.
				{
					if(this.isPlanRouteButtonVisible()){           // FOR ON EXIT: Hides the plan route button
						PlayerGoals.planRouteBtn.setVisible(false);
						this.setPlanRouteButtonVisible(false);
					}
					else {
						if(!this.isEmpty()&& !GoalMenu.open){
							{	
								PlayerGoals.selectedGoal = goal;		//FOR ON ENTER: Shows the plan route button
								PlayerGoals.selectedGoalActor = this;
								PlayerGoals.planRouteBtn.setVisible(true);
								PlayerGoals.planRouteBtn.setX(this.getX()+60);
								PlayerGoals.planRouteBtn.setY(this.getY()+75);
								PlayerGoals.planRouteBtn.refreshBounds();
								this.setPlanRouteButtonVisible(true);
							}
						}
					}
					started=false;
				}
			}
			else //If a Goal Menu Goal- Shows the Add goal button instead.
			{
				if(!this.isEmpty()) 
				{
					if  (this.addGoalButtonVisible){
						GoalMenu.addGoalBtn.setVisible(false);
						this.addGoalButtonVisible=false;
					}
					else
					{	//Makes the addgoalbtn visible to user on the selected goal
						GoalMenu.selectedGoal=this;
						GoalMenu.addGoalBtn.setX(actorX+60);
						GoalMenu.addGoalBtn.setY(actorY+75);
						GoalMenu.addGoalBtn.setVisible(true);
						GoalMenu.addGoalBtn.refreshBounds();
						this.addGoalButtonVisible=true;
					}
				}
				started = false;
			}

		}


	}


	public boolean isEmpty(){
		return this.empty;
	}

	/**
	 * When we set empty we set the value AND change the texture to the relevant texture;
	 * @param empty - Boolean for whether or not the Goal Actor has an assigned goal.
	 */
	public void setEmpty(Boolean empty){
		this.empty=empty;

		//Change the ticket type
		if (this.empty)
			this.texture = Game_TextureManager.getInstance().game_menuobject_emptyticket;
		else
		{
			if (this.goal.isSpecial()==false)
				this.texture = Game_TextureManager.getInstance().game_menuobject_ticket;
			else
				this.texture = Game_TextureManager.getInstance().game_menuobject_ticket;

		}
	}


	//Getters and Setters for goal, actor x, actor y and index
	public void setGoal(Goal goal){
		this.goal= goal;
	}
	public  Goal getGoal(){
		return this.goal;
	}

	public void setX(float x){
		this.actorX = x;
	}
	public void setY(float y){
		this.actorY = y;
	}
	public float getX(){
		return this.actorX;
	}
	public float getY(){
		return this.actorY;
	}
	public int getIndex(){
		return this.index;
	}
	public void setIndex(int i){
		this.index=i;
	}


	public boolean isPlanRouteButtonVisible() {
		return planRouteButtonVisible;
	}


	public void setPlanRouteButtonVisible(boolean planRouteButtonVisible) {
		this.planRouteButtonVisible = planRouteButtonVisible;
	}


	public boolean isOwnedgoal() {
		return ownedgoal;
	}


	public void setOwnedgoal(boolean ownedgoal) {
		this.ownedgoal = ownedgoal;
	}
	public void refreshBounds(){
		setBounds(actorX,actorY,texture.getWidth(),texture.getHeight());
	}
}
