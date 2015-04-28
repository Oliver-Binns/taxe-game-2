package com.TeamHEC.LocomotionCommotion.Card;

import java.util.ArrayList;
import java.util.HashMap;

import com.TeamHEC.LocomotionCommotion.Game.GameScreen;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_TextureManager;
import com.TeamHEC.LocomotionCommotion.UI_Elements.SpriteButton;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

public class Game_CardHand {
	public static Game_CardHandManager actorManager;
	/**
	 * @author Robert Precious <rp825@york.ac.uk>
	 * 
	 * This class is a Manager, I use Managers in the UI to handed groups of actors. It means I can hide or show a major group of action from one action.
	 * The Manager handles the creation of the cards in the hand, 
	 * the adding of cards and removing from cards from the players hand.
	 * 
	 * @param actors		Array of actors used to add to stage in one loop
	 * @param cardactors	Array of cards  used for global use
	 * @param currentHand	ArrayList of card_Card that represents the currentHand
	 * @param newcards		ArrayList of card_Card from the HandCreator
	 * @param open			Boolean for if the cards are visible or not
	 * @param card1-7		card_Card actors for the cards
	 * @param stagestart	int that records the position of the first card_Card actor
	 * @param cardActors	int of the number of card_Card actors
	 * @param height		int records the height for the cards on screen
	 * @param selectedCard	Holds the selected card slot number
	 * @param numberofcards	Holds the number of cards from the HandCreator
	 * @param usecardbtn	Actor for the use card button
	 *
	 */
	public void create(Stage stage){
		actorManager = new Game_CardHandManager();
		actorManager.create(stage);
	}
	public static class Game_CardHandManager {
		//Arrays
		private static 	Array<Actor> actors = new Array<Actor>();
		public 	Array<CardActor> cardactors = new Array<CardActor>();
		//ArrayLists
		public 	ArrayList<CardActor> currentHand;
		public static ArrayList<Card> newcards = new ArrayList<Card>();
		//Booleans
		public boolean open=false;
		//Game_card_Cards
		public  static CardActor card1, card2, card3, card4, card5, card6,card7;
		//Ints
		public  int stagestart, cardActors, height=-100;
		public int selectedCard= 0;
		public  int numberofcards;
		//Actors
		public SpriteButton usecardbtn;

		public Game_CardHandManager(){	}

		public void create(Stage stage){
			//Refreshes Lists - Important for creating a new gamescreen
			actors.clear();
			newcards.clear();
			//Refresh actor counters
			stagestart =0;
			cardActors=0;

			numberofcards= 0;

			createEmpties();

			stagestart= stage.getActors().size;  	//Record the start or the Card actors
			for (Actor a : actors){					//add the actors to the stage
				if(open == true){
					a.setTouchable(Touchable.enabled);
					a.setVisible(true);}
				else
					a.setVisible(false);

				stage.addActor(a);
				cardActors ++;					//Increment the number of cardActors
			}
			usecardbtn = new SpriteButton(1170,450,Game_TextureManager.getInstance().game_card_usecardbtn){
				@Override
				protected void onClicked(){
					Game_CardHand.actorManager.useCard((Game_CardHand.actorManager.selectedCard)); //gets the selected card and sends it to the useCard method.
				}
			};//add the the usecardbtn to the stage
			usecardbtn.setVisible(false);
			stage.addActor(usecardbtn);



		}
		/**
		 * Change Player gets the cards from the new player when a turn is ended and puts the player's cards back.
		 * This involves flushing the other players hand of CardActors.
		 * @param player - Current Player.
		 */
		public static void changePlayer(Player player) {
			Game_CardHand.actorManager.numberofcards=0;
			flushHand();
			for (int i=0; i<player.getCards().size();i++){
				int numberofcards=Game_CardHand.actorManager.numberofcards;
				if(numberofcards<7){
					Game_CardHand.actorManager.cardactors.get(numberofcards).setTexture(player.getCards().get(i).getImage());	//sets the image
					Game_CardHand.actorManager.cardactors.get(numberofcards).setSlot(numberofcards+1);			//sets the slot
					Game_CardHand.actorManager.cardactors.get(numberofcards).setEmpty(false);					//sets the empty boolean to false
					Game_CardHand.actorManager.cardactors.get(numberofcards).setCard(player.getCards().get(i));					//give the actor the card object
					Game_CardHand.actorManager.cardactors.get(numberofcards).refreshBounds();		
					Game_CardHand.actorManager.numberofcards+=1;											//increment the number of cards
					GameScreenUI.refreshResources();					//refresh the labels to show the change in resources (the change in card number)
				}
			}
		}
		/**
		 * FlushHand clears all the card Actors and sets the numberofcards to 0.
		 */
		public static void flushHand(){
			for (int i=0;i<7;i++){
				Game_CardHand.actorManager.cardactors.get(i).setVisible(false);	//sets the image
				Game_CardHand.actorManager.cardactors.get(i).setEmpty(true);	
				Game_CardHand.actorManager.usecardbtn.setVisible(false);//sets the empty boolean to false
			}
			GameScreenUI.refreshResources();
			Game_CardHand.actorManager.numberofcards=0;

		}

		/**
		 * useCard is called from the usecard button and implements the card's action and removes it.
		 * @param cardNum
		 */
		public void useCard(int cardNum){						//Method useCard lets the player use their card.
			if (cardNum !=0){												//if the the number of card is not 0
				cardactors.get(cardNum-1).getCard().implementCard();				//Implement the card object
				GameScreenUI.refreshResources();					//refresh the labels showing the resources
				if (cardNum<Game_CardHand.actorManager.numberofcards){									//Shuffle the cards up
					for(int i=cardNum-1;i<Game_CardHand.actorManager.numberofcards-1;i++){
						cardactors.get(i).setTexture(cardactors.get(i+1).getTexture());
						cardactors.get(i).setSlot(i+1);
						cardactors.get(i).setCard(cardactors.get(i+1).getCard());

					}
				}

				GameScreen.game.getPlayerTurn().getCards().remove(selectedCard-1);
				Game_CardHand.actorManager.selectedCard = 0;											//No card is selected
				cardactors.get(Game_CardHand.actorManager.numberofcards-1).setEmpty(true);					//Set the end slot as empty and hidden
				cardactors.get(Game_CardHand.actorManager.numberofcards-1).setVisible(false);
				Game_CardHand.actorManager.numberofcards-=1;											//decrement card number
				GameScreenUI.refreshResources();
				Game_CardHand.actorManager.organiseHand();
				Game_CardHand.actorManager.usecardbtn.setVisible(false);			//hide the use card button
			}


		}
		/**
		 * Adds given card to hand if hand is not full.
		 * @param newCard - new card passed to be put in the hand.
		 */
		public void addCard(Card newCard){
			//Method adds card to the hand if not already full
			int numberofcards=Game_CardHand.actorManager.numberofcards;
			if(numberofcards<7){
				Game_CardHand.actorManager.cardactors.get(numberofcards).setTexture(newCard.getImage());	//sets the image
				Game_CardHand.actorManager.cardactors.get(numberofcards).setSlot(numberofcards+1);			//sets the slot
				Game_CardHand.actorManager.cardactors.get(numberofcards).setEmpty(false);					//sets the empty boolean to false
				Game_CardHand.actorManager.cardactors.get(numberofcards).setCard(newCard);					//give the actor the card object
				Game_CardHand.actorManager.cardactors.get(numberofcards).refreshBounds();		
				Game_CardHand.actorManager.numberofcards+=1;											//increment the number of cards
				GameScreenUI.refreshResources();					//refresh the labels to show the change in resources (the change in card number)
			}
		}
		
		/**
		 * Moves the cards up or down
		 * @param height - the pixel to which you want to increase/decrease the height of the cards 
		 */
		public void changeHeight(float height){  //Method to move all cards up or down at once
			float newheight = card1.getY() +height;
			card1.setActorY(newheight);
			card2.setActorY(newheight);
			card3.setActorY(newheight);
			card4.setActorY(newheight);
			card5.setActorY(newheight);
			card6.setActorY(newheight);
			card7.setActorY(newheight);
		}
		
		/**
		 * organiseHand collapses all cards or all the cards except for the selectedCard.
		 * 
		 */
		public void organiseHand()
		{
			if (Game_CardHand.actorManager.selectedCard == 0){		//resets the expanded boolean for all cards not selected.
				for(CardActor b : cardactors)
					b.setexpanded(false);
				collapseCards();
			}
			else{
				collapseCards();
			}
		}

		/**
		 * CollapseCards- resets any card that is not selected to unexpanded position
		 */
		public static void collapseCards(){
			if (card1.getSlot() != Game_CardHand.actorManager.selectedCard)
				card1.cardCollapse();
			if (card2.getSlot() != Game_CardHand.actorManager.selectedCard)
				card2.cardCollapse();
			if (card3.getSlot() != Game_CardHand.actorManager.selectedCard)
				card3.cardCollapse();
			if (card4.getSlot() != Game_CardHand.actorManager.selectedCard)
				card4.cardCollapse();
			if (card5.getSlot() != Game_CardHand.actorManager.selectedCard)
				card5.cardCollapse();
			if (card6.getSlot() != Game_CardHand.actorManager.selectedCard)
				card6.cardCollapse();
			if (card7.getSlot() != Game_CardHand.actorManager.selectedCard)
				card7.cardCollapse();
		}

		/**
		 * Getter for selectedcard
		 * @return either selected card actor or null (if non are selected)
		 */
		public static CardActor getSelectedCard(){
			for (CardActor c: Game_CardHand.actorManager.cardactors)
			{
				if(c.getSlot()==Game_CardHand.actorManager.selectedCard)
				{return c;
				}
			}
			if (Game_CardHand.actorManager.selectedCard == 0)
			{
				throw new Error("No card selected: This should only be called when use card button is using it! ");
			}
			return null;
		}


		/**
		 * Creates the card objects by calling createEmpties which calls createSlots.
		 */
		private void createEmpties() 
		{
			HashMap<String, CardActor> cardslots = new HashMap<String, CardActor>(); //create an Hashmap of slots
			cardslots = createSlots();		//create slots

			for (int i=0;i<7;i++)				//run through all slots
			{
				String a = new Integer(i+1).toString();		//change the counter+1 to a string for recall in the hashmap 
				cardslots.get(a).setEmpty(true);			//set slot as empty which means it does not get drawn
				cardactors.add(cardslots.get(a));
				actors.add(cardslots.get(a));//add it to the new card list
			}

		}
		/**
		 * Creates blank card objects in a hashmap, returning all the available card slots.
		 * @return - Blank Card Slots.
		 */
		private  HashMap<String, CardActor> createSlots() {
			int heightY = -100;
			int x = 1130;
			HashMap<String, CardActor> cardslots = new HashMap<String, CardActor>();
			cardslots.put("1", card1= new CardActor(null,x,heightY,false,1));
			x-=130;																				 //Move card across to make them overlay on each other
			cardslots.put("2", card2= new CardActor(null,x,heightY,false,2));
			x-=130;
			cardslots.put("3", card3= new CardActor(null,x,heightY,false,3));
			x-=130;
			cardslots.put("4", card4= new CardActor(null,x,heightY,false,4));
			x-=130;
			cardslots.put("5", card5= new CardActor(null,x,heightY,false,5));
			x-=130;
			cardslots.put("6", card6= new CardActor(null,x,heightY,false,6));
			x-=130;
			cardslots.put("7", card7= new CardActor(null,x,heightY,false,7));
			return cardslots;
		}
	}


}