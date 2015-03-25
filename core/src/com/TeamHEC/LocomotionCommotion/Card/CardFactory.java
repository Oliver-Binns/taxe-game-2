package com.TeamHEC.LocomotionCommotion.Card;

import java.util.ArrayList;
import java.util.Random;

import com.TeamHEC.LocomotionCommotion.Player.Player;

/**
 * 
 * @author Callum Hewitt <ch1194@york.ac.uk>
 * This class generates Cards for the players. Cards can be resource cards which give new resources.
 * Or magic cards which produce a variety of fun effects. These include teleporting trains and increasing train speeds.
 */
public class CardFactory{
	
	private Random random;
	private ArrayList<Card> magicCardList;
	private ArrayList<Card> resourceCardList;
	private Player player;
	
	TeleportCard teleport;
	GoFasterStripesCard goFaster;
	
	CoalCard coal;
	OilCard oil;
	ElectricCard electric;
	NuclearCard nuclear;
	GoldCard gold;

	/**
	 * The initialiser. Creates the lists of magic cards and resource cards. Add new cards here to allow them to be generated.
	 * @param player  The player that you want to generate Cards for.
	 */
	public CardFactory(Player player) //player is null for shop.
	{		
		this.player = player;
		random = new Random();		
		
		teleport = new TeleportCard(this.player);
		goFaster = new GoFasterStripesCard(this.player);
		
		coal = new CoalCard(this.player);
		oil = new OilCard(this.player);
		electric = new ElectricCard(this.player);
		nuclear = new NuclearCard(this.player);
		gold = new GoldCard(this.player);
		
		magicCardList = new ArrayList<Card>();
		magicCardList.add(teleport);
		magicCardList.add(goFaster);
				
		resourceCardList = new ArrayList<Card>();
		resourceCardList.add(coal);
		resourceCardList.add(oil);
		resourceCardList.add(electric);
		resourceCardList.add(nuclear);
		resourceCardList.add(gold);	
	}

	/**
	 * Creates a random card from either the magicCardList or resourceCardList.
	 * @return A Card object from either magicCardList or resourceCardList.
	 */
	public Card createAnyCard()
	{
		ArrayList<Card> cardList = new ArrayList<Card>(magicCardList);
		cardList.addAll(resourceCardList);
		
		return cardList.get(random.nextInt(cardList.size()));		
	}
	
	/**
	 * Creates a random card from the set of resource cards.
	 * @return A resource Card object.
	 */
	public Card createResourceCard()
	{
		return resourceCardList.get(random.nextInt(resourceCardList.size()));
	}
	
	/**
	 * Creates a random card from the set of magic cards.
	 * @return A magic Card object.
	 */	
	public Card createMagicCard()
	{
		return magicCardList.get(random.nextInt(magicCardList.size()));
	}
}
