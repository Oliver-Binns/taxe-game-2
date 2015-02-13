package com.TeamHEC.LocomotionCommotion.MapActors;

import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Goal.PlayerGoals;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Game_Map_Train extends Actor{
	
	private Train train;
	private Texture texture, toggleTexture1, toggleTexture2;
	private float offset;
		
	public boolean canMove = false;
	public int moveCounter = 0;
	
	private int clickCount = 0;
	
	public Game_Map_Train()
	{
		texture = Game_Map_TextureManager.getInstance().p1Train;
		toggleTexture1 = texture;
		toggleTexture2 = texture;
		
		this.setVisible(false);
		
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				((Game_Map_Train)event.getTarget()).clickedTrain();
				return true;
			}
		});
		
		addListener(new InputListener(){
			public void enter(InputEvent event, float x, float y, int pointer, Actor Game_Map_Station) {
				((Game_Map_Train)event.getTarget()).toggleHighlight(true);
			}

		});
		addListener(new InputListener(){
			public void exit(InputEvent event, float x, float y, int pointer, Actor Game_Map_Station) {
				((Game_Map_Train)event.getTarget()).toggleHighlight(false);
			}
		});
	}
	
	public void createBlip(Train train)
	{
		this.train = train;
		this.setVisible(true);
		
		if(train.getOwner().isPlayer1)
		{
			toggleTexture1 = Game_Map_TextureManager.getInstance().p1Train;
			toggleTexture2 = Game_Map_TextureManager.getInstance().p1Trainx2;
		}
		else
		{
			toggleTexture1 = Game_Map_TextureManager.getInstance().p2Train;
			toggleTexture2 = Game_Map_TextureManager.getInstance().p2Trainx2;
		}
		texture = toggleTexture1;
	}
	
	public void clickedTrain()
	{
		if(clickCount == 0)
		{
			Game_Map_Manager.trainInfo.showLabel(train);
			
			if(Game_Map_Manager.trainInfo.train.route.inStation())
				clickCount = 2;
			else
				clickCount = 1;
		}
		else if(clickCount == 1)
		{
			Game_Map_Manager.trainInfo.makeVisible(false);
			
			if(Game_Map_Manager.trainInfo.train.route.inStation())
				Game_Map_Manager.trainInfo.train.route.getStation().actor.hideInfoBox();
			
			clickCount = 0;
		}
		else if(clickCount == 2)
		{
			Game_Map_Manager.trainInfo.makeVisible(false);
			
			if(Game_Map_Manager.trainInfo.train.route.inStation())
			{
				Game_Map_Manager.trainInfo.train.route.getStation().actor.showInfoBox();
				Game_Map_StationBtn.selectedStation = Game_Map_Manager.trainInfo.train.route.getStation().getStationActor();
			}
			clickCount = 1;
		}
		
		if(PlayerGoals.chooseTrain && GameScreen.game.getPlayerTurn() == train.getOwner())
		{
			PlayerGoals.selectedGoal.assignTrain(train);
			PlayerGoals.selectedGoal.setActor(PlayerGoals.selectedGoalActor);
			WarningMessage.fireWarningWindow("Assigned Goal to Train!", "Plan your route");
			PlayerGoals.chooseTrain = false;
		}
	}
	
	public void toggleHighlight(boolean highlighted)
	{
		if(highlighted)
		{
			texture = toggleTexture2;
			offset = -2.5f;
		}
		else
		{
			texture = toggleTexture1;
			offset = 0;
		}
	}
	
	@Override
	public void act(float delta)
	{	
		if(canMove)
		{
			if(!train.route.isComplete())
			{
				int trainSpeed = train.getSpeed();
				if(moveCounter < trainSpeed)
				{
                    Game_Map_Manager.isMoving = true;
					train.route.update(1);
					moveCounter++;
				}
				else
				{
					canMove = false;
					moveCounter = 0;
                    Game_Map_Manager.isMoving = false;
				}
			}
			else
            {
                moveCounter = 0;
                Game_Map_Manager.isMoving = false;
            }

        }
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		setBounds(train.route.getTrainPos().x + offset, train.route.getTrainPos().y + offset, texture.getWidth(),texture.getHeight());
		batch.draw(texture, train.route.getTrainPos().x + offset, train.route.getTrainPos().y + offset);
	}
	
}
