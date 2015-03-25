package com.TeamHEC.LocomotionCommotion.Card;

import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
/**
 * 
 * @author Robert Precious <rp825@york.ac.uk>
 * This is the Class for the card actor, It functions the same way as Sprite/SpriteButton. 
 * @param started - This is used to tell the renderer that the listener has been triggered
 * @param expanded - This is used to tell whether the card is raise above the overs (expanded)
 * @param empty - A card can be empty and not have a texture
 *
 */
public class CardActor extends Actor {
		boolean started = false;
		private boolean expanded =false;
		private Texture texture;
		private float actorX;
		private float actorY;
		private boolean empty;
		private int slot;
		private Card card;

		public  CardActor(Texture texture, int actorX, int actorY, boolean empty, int slot){
			this.slot = slot;
			this.empty= empty;
			if (texture==null){
				this.texture = Game_TextureManager.getInstance().game_card_cardtoggle;//just a filler to avoid errors
			}else
				this.texture = texture; 
			this.actorX = actorX;
			this.actorY = actorY;


			setBounds(this.actorX,this.actorY,this.texture.getWidth(),this.texture.getHeight());

			addListener(new InputListener(){
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					((CardActor)event.getTarget()).started = true;
					return true;
				}
			});	

			/*	UNCOMMENT THIS TO ALLOW MOUSE OVER (VERY BUGGY)
			addListener(new InputListener(){
				public void enter(InputEvent event, float x, float y, int pointer, Actor ScreenCard) {
					((ScreenCard)event.getTarget()).started = true;
				}

			});
			addListener(new InputListener(){
				public void exit(InputEvent event, float x, float y, int pointer, Actor ScreenCard) {
					((ScreenCard)event.getTarget()).started = true;
				}

			});
			 */
		}


		public void refreshBounds() {
			setBounds(this.actorX,this.actorY,this.texture.getWidth(),this.texture.getHeight());

		}


		@Override
		public void draw(Batch batch, float alpha){
			if (empty)
				this.setVisible(false); //if empty we do not want to draw the actor.
			else
				batch.draw(this.texture,actorX,actorY);

		}

		@Override
		public void act(float delta){
			if(started){
				if (isExpanded()) //if expanded, we run cardCollapse which puts the card back in position.
				{
					this.cardCollapse();
					Game_CardHand.actorManager.usecardbtn.setVisible(false);	// hides the use card button
					Game_CardHand.actorManager.selectedCard=0; 				//sets to no card selected

				}
				else
				{
					Game_CardHand.actorManager.selectedCard=this.getSlot();		//sets the this card to be selected
					Game_CardHand.actorManager.usecardbtn.setVisible(true);		//makes the use card button to visible
					this.cardExpand();										//raises the card
					Game_CardHand.actorManager.usecardbtn.setX(this.actorX+40);				//moves the usecardbutton to above it
					Game_CardHand.actorManager.usecardbtn.setY(this.actorY+350);			//moves the usecardbutton to above it

				}
				Game_CardHand.actorManager.usecardbtn.refreshBounds();		//refreshes the use card button action area
				started = false; 	//ends action
			}
		}


		//Getter and Setter for Expanded
		public boolean isExpanded() {
			return expanded;
		}
		public boolean setexpanded(boolean open) {
			this.expanded = open;
			return open;
		}

		//Getter and Setter for Card
		public Card getCard(){
			return this.card;
		}
		public void setCard(Card card){
			this.card = card;
		}

		//Getter and Setter for Texture
		public void setTexture(Texture texture){
			this.texture = texture;
		}
		public Texture getTexture(){
			return texture;
		}

		//Getter and Setter for Slot
		public int getSlot(){
			return this.slot;
		}
		public void setSlot(int slot) {
			this.slot= slot;
		}

		//Setter for ActorY
		public void setActorY(float y){
			this.actorY=y;
			setBounds(actorX,actorY,texture.getWidth(),texture.getHeight());				
		}

		//Setter for Empty
		public void setEmpty(boolean b) {
			this.empty=b;
		}

		//cardExpand- Calls Organize deck, moves the card up, updates the expanded boolean, and refreshes bounds.
		public void cardExpand(){
			Game_CardHand.actorManager.organiseHand();
			this.actorY+=200;
			setexpanded(true);
			setBounds(this.actorX,this.actorY,this.texture.getWidth(),this.texture.getHeight());
		}

		/*cardCollapse- if the card is expanded: resets the height of the card depending on whether or not the resource bar is expanded
			sets the expanded boolean, refreshes the action area and calls organiseDeck OR just resets the height when the resource bar changes.*/
		public void cardCollapse(){
			if (expanded){
				if (GameScreenUI.resourcebarexpanded)
					this.actorY=80;
				else
					this.actorY=-100;
				setexpanded(false);
				setBounds(this.actorX,this.actorY,this.texture.getWidth(),this.texture.getHeight());
				Game_CardHand.actorManager.organiseHand();
			}
			else
			{if (GameScreenUI.resourcebarexpanded)
				this.actorY=80;
			else
				this.actorY=-100;}

		}		
	}