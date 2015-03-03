package com.TeamHEC.LocomotionCommotion.Player;

import java.util.ArrayList;
import java.util.HashMap;

import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Obstacle.Obstacle;
import com.TeamHEC.LocomotionCommotion.Obstacle.ObstacleFactory;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Fuel;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Train.RouteListener;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.UI_Elements.GameScreenUI;
import com.TeamHEC.LocomotionCommotion.UI_Elements.Game_StartingSequence;
import com.TeamHEC.LocomotionCommotion.UI_Elements.WarningMessage;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 * @author Callum Hewitt <ch1194@york.ac.uk>
 * @author Elliot Bray <eb1033@york.ac.uk>
 */

public class Player implements RouteListener{

	private String name;
	private int points;

    private enum playerStates { PLAYING, WON, LOST };
    private playerStates currentState;

	private Gold gold;
	private Coal coal;
	private Oil oil;
	private Electric electric;
	private Nuclear nuclear;
	private ArrayList<Card> cards;
	private Shop shop;
	private ArrayList<Goal> goals;
	private ArrayList<Train> trains;
	private ArrayList<Station> stations = new ArrayList<Station>();
	private int[] lines = new int[8];

	private HashMap<String, Fuel> playerFuel;

	public boolean isPlayer1;

	public Player(String name, int points, Gold gold, Coal coal, Electric electric, Nuclear nuclear, Oil oil, ArrayList<Card> cards, ArrayList<Goal> goals, ArrayList<Train> trains)
	{
		this.name = name;
		this.points = points;
        this.currentState = playerStates.PLAYING;
		this.gold = gold;
		this.coal = coal;
		this.oil = oil;
		this.electric = electric;
		this.nuclear = nuclear;
		this.cards = cards;
		this.shop = new Shop(this);
		this.goals = goals;
		this.trains = trains;

		//this.stations = stations;
		for (int i = 0; i<6;i++)
		{
			this.lines[i] = 0;
		}

		playerFuel = new HashMap<String, Fuel>();

		playerFuel.put("Coal", this.coal);
		playerFuel.put("Electric", this.electric);
		playerFuel.put("Nuclear", this.nuclear);
		playerFuel.put("Oil", this.oil);
	}

	/**
	 * @return returns the players name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return returns the players points
	 */
	public int getPoints()
	{
		return points;
	}

    /**
     * @param newPoints  new points to be added to a player's current score
     */
    public void incrementPoints(int newPoints) { points += newPoints; }


    /**
     * Get the current state of a player
     * @return Current state a player is in from enum Player.playerStates { PLAYING, WON, LOST}
     */
    public playerStates getCurrentState() { return currentState; }

    public void setAsWinner() { currentState = playerStates.WON; }

    /**
     * @return True if player has won the game
     */
    public Boolean hasWon() { return currentState == playerStates.WON;  }

    public void setAsLoser() { currentState = playerStates.LOST;  }

    /**
     * @return True if player has lost the game
     */
    public Boolean hasLost() { return currentState == playerStates.LOST; }

    /**
     * @return True if player has not won or lost yet
     */
    public Boolean isStillPlaying() { return currentState == playerStates.PLAYING; }

	//Shop
	public Shop getShop(){
		return shop;
	}

	public void buyCoal(int quantity)
	{
		shop.buyFuel("Coal", quantity, false);
	}

	public void buyOil(int quantity)
	{
		shop.buyFuel("Oil", quantity, false);
	}

	public void buyElectric(int quantity)
	{
		shop.buyFuel("Electric", quantity, false);
	}

	public void buyNuclear(int quantity)
	{
		shop.buyFuel("Nuclear", quantity, false);
	}

	public void buyCard(int quantity)
	{
		shop.buyCard(false);
	}

	//Fuel	
	public int getFuel(String fuelType)
	{
		return playerFuel.get(fuelType).getValue();
	}

	public void addFuel(String fuelType, int quantity)
	{
		playerFuel.get(fuelType).addValue(quantity);
		if(!Game_StartingSequence.inProgress)
			GameScreenUI.refreshResources();
	}

	public void subFuel(String fuelType, int quantity)
	{
		playerFuel.get(fuelType).subValue(quantity);
		if(!Game_StartingSequence.inProgress)
			GameScreenUI.refreshResources();
	}

	//Gold
	public int getGold()
	{
		return gold.getValue();
	}

	public void addGold(int value)
	{
		gold.setValue(gold.getValue() + value);
		if(!Game_StartingSequence.inProgress)
			GameScreenUI.refreshResources();
	}

	public void subGold(int value)
	{
		gold.setValue(gold.getValue() - value);
		if(!Game_StartingSequence.inProgress)
			GameScreenUI.refreshResources();
	}

	//Cards
	public void addCard(Card card)
	{
		cards.add(card);
	}

	public void discardCard(Card card)
	{
		cards.remove(card);
	}

	public int getNumCards()
	{
		return cards.size();
	}

	public ArrayList<Card> getCards()
	{
		return cards;
	}

	//Stations
	/**
	 * @return returns the number of stations owned by the player
	 */
	public int getNumStations()
	{
		return stations.size();
	}	
	/**
	 * @return returns the array list of stations
	 */
	public ArrayList<Station> getStations()
	{
		return stations;
	}
	/**
	 * @return returns the array containing how much of each line a player owns
	 */
	public int[] getLines()
	{
		return lines;
	}

	/**
	 * @param station purchases the station for the player and keeps track of lines owned
	 */
	public void purchaseStation(Station station)
	//If the player doesn't have enough gold or if the station is owned by a player or if the player does not 
	//have a train in that station then nothing will happen
	//There is space to add some sort of message for the player
	{
		boolean trainInStation = false;
		
		for (int j=0; j < trains.size(); j ++)
		{
			if(station.getOwner() == null)
			{
				if(trains.get(j).getRoute().getStation() == station)
				{
					if(getGold() >= station.getBaseValue())
					{
						stations.add(station);
						this.subGold(station.getBaseValue());
						for (int i=0; i<station.getLineType().length; i++)
						{	
							if (((i > 0) && (station.getLineType()[i] != station.getLineType()[i-1])) || (i==0))
								//Line is an array of 3 line colours, this loop will add the first line colour
								//then if a station is on another line of a different colour it will add that
								//hence when i > 0 (checking the second colour) AND is a different colour to the previous colour
								//add that colour to the players lines
							{
								switch(station.getLineType()[i])
								{ //keeps track of how much of a line the player owns
								case Red:
									lines[0] += 1;	
									break;
								case Blue:
									lines[1] += 1;
									break;
								case Green:
									lines[2] += 1;
									break;
								case Yellow:
									lines[3] += 1;
									break;
								case Purple: 
									lines[4] += 1;
									break;
								case Black:
									lines[5] += 1;
									break;
								case Brown:
									lines[6] += 1;
									break;
								case Orange:
									lines[7] += 1;
									break;
								default:
									throw new IllegalArgumentException("Could not find line for Station: " + station.getName() + " owned by Player " + station.getOwner().name);
								}
							}
						}
						station.setOwner(this);
						this.lineBonuses();	
						trainInStation = true;
					}
					else
					{
						WarningMessage.fireWarningWindow("SORRY", "You don't have enough gold!");
						trainInStation = true;
					}
				}
			}
			else
			{
				WarningMessage.fireWarningWindow("SORRY", "This station is already owned!");
			}
		}
		if(!trainInStation)
		{
			WarningMessage.fireWarningWindow("SORRY", "You must have a train at a station to buy it!");
		}
	}

	public void sellStation(Station station)
	{
		//UI DOES NOT CURRENTLY SUPPORT SELLING OF STATIONS
		//but sell station has been tested and does work
		/*
		if (this.stations.contains(station))
		{
			for (int i=0; i<station.getLineType().length; i++)
			{	
				if (((i > 0) && (station.getLineType()[i] != station.getLineType()[i-1])) || (i==0))
				{
					//same method as in purchase station
					switch(station.getLineType()[i])
					{ //keeps track of how many of a line the player owns
					case Red:
						lines[0] -= 1;			
						break;
					case Blue:
						lines[1] -= 1;
						break;
					case Green:
						lines[2] -= 1;
						break;
					case Yellow:
						lines[3] -= 1;
						break;
					case Purple: 
						lines[4] -= 1;
						break;
					case Black:
						lines[5] -= 1;
						break;
					case Brown:
						lines[6] -= 1;
						break;
					case Orange:
						lines[7] -= 1;
						break;
					default:
						throw new IllegalArgumentException("Could not find line for Station: " + station.getName() + " owned by Player " + station.getOwner().name);
					}
				}
			}
			this.addGold((int)(station.getBaseValue() * 0.7));
			station.setOwner(null);
			stations.remove(station);
			station.setOwner(null);
			this.lineBonuses();
		}
		 */
	}

	@Override
	public void stationPassed(Station station, Train train) {
		// TODO Auto-generated method stub

		System.out.println("Train passed " + station.getName());

		// STATION TAX:
		// RENT IS CURRENTLY NOT IMPLEMENTED AS UI DOES NOT CURRENTLY SUPPORT
		/*
		if(station.getOwner() != this && station.getOwner() != null)
		{
			this.subGold(station.getTotalRent());
		}
		 */

	}

	/**
	 * calculates the bonus for each individual station based on how many other stations on the same line are owned
	 */
	public void lineBonuses() //MUST BE CALLED BEFORE YOU ACCESS A STATIONS VALUE, RENT OR RESOURCE AMOUNTS IF STATIONS OWNED HAS CHANGED
	{
		for (int i = 0; i<stations.size(); i++)
		{
			Station currentStation = stations.get(i);
			int red = 0;
			int blue = 0;
			int green = 0;
			int yellow = 0;
			int purple = 0;
			int black = 0;
			int brown = 0;
			int orange = 0;

			for (int j = 0; j < currentStation.getLineType().length; j++) //line type length should be 3, so for 0 to 2		
			{
				if (j == 0 || ((j > 0) && (currentStation.getLineType()[j] != currentStation.getLineType()[j-1])))
					//if 0 check line type 
					//if 1 check if it is the same as 0 if not check line type
					//if 2 check if it is the same as 1 if not check line type
					//always configure your line type array to have repeats in 1 and 2 e.g. (Black, Red, Red) NOT (Black, Red, Black) else this will not work
				{
					switch(currentStation.getLineType()[j])
					{	
					case Red:
						red = lines[0];
						if (red == 5)
						{
							red += 3;
						}
						//5 stations on red line
						break;
					case Blue:
						blue = lines[1];
						if (blue == 5)
						{
							blue += 3;
						}
						//5 stations on blue line
						break;
					case Green:
						green = lines[2];
						if (green == 3)
						{
							green += 1;
						}
						//3 stations on green line
						break;
					case Yellow:
						yellow = lines[3];
						if (yellow == 4)
						{
							yellow += 2;
						}
						//4 stations on yellow line
						break;
					case Purple:
						purple = lines[4];
						if (purple == 4)
						{
							purple += 2;
						}
						//4 stations on purple line
						break;
					case Black:
						black = lines[5];
						if (black == 5)
						{
							black += 3;
						}
						//5 stations on black line
						break;
					case Brown:
						brown = lines[6];
						if (brown == 5)
						{
							brown += 3;
						}
						//5 stations on brown line
						break;
					case Orange:
						orange = lines[7];
						if (orange == 6)
						{
							orange += 4;
						}
						//6 stations on orange line
						break;
					default:
						throw new IllegalArgumentException("Could not find line associated with value");
					}
				}
			}
			//owning an entire line is worth an additional reward (3 stations 5%, 4 stations 10%, 5 stations 15%, 6 stations 20%)
			//increase rent, resource and value by 5% per line you have a station connected too, this may be adjusted to due scaling at larger values
			currentStation.setResourceOutMod(((red + blue + green + yellow + purple + black + brown + orange) * (int)(currentStation.getBaseResourceOut() * 0.05)));

			//Rent not currently increased but is increased for later use anyway
			currentStation.setRentValueMod(((red + blue + green + yellow + purple + black + brown + orange) * (int)(currentStation.getBaseRentValue() * 0.05)));
			//Increasing value has no affect as stations cannot be currently be sold but if increased for later use anyway
			currentStation.setValueMod(((red + blue + green + yellow + purple + black + brown + orange) * (int)(currentStation.getValueMod() * 0.05)));
		}
	}

	/**
	 * Generates fuel from stations.
	 */
	public void stationRewards()
	{

		for (int i = 0; i < stations.size(); i++)
		{
			Station currentStation = stations.get(i);
			if (currentStation.getResourceString() == "Gold") //Special case for Monaco which gives gold
			{
				this.addGold(currentStation.getBaseResourceOut());
			}
			else
			{
				this.addFuel(currentStation.getResourceString(), currentStation.getTotalResourceOut());
			}
		}
	}

    /*
        Compute obstacles
        - Increment obstacle turn counters
        - Randomly make s**t happen
     */
    public void obstacles(final double PROBABILITY) {

        ObstacleFactory f = new ObstacleFactory();
        f.setProbability(PROBABILITY);

        for ( Train t: this.getTrains() ) {

			// Only assign obstacles to moving trains
			if ( t.getRoute().inStation() ) {
				continue;
			}

			// Do not assign obstacles to already unlucky trains
            if ( t.hasObstacle() ) {
                t.getObstacle().startTurn();

            } else {
                Obstacle o = f.getObstacle(this);
                if ( o != null ) {
                    o.applyTo(t);
                    WarningMessage.fireWarningWindow(
                            o.getName(),
                            "Your " + t.getName() + " will " + o.getDescription()
                    );
                }

            }
        }
    }

	//Goals
	public ArrayList<Goal> getGoals()
	{
		return goals;
	}

	//Trains
	public ArrayList<Train> getTrains()
	{
		return trains;
	}
	
	/**
	 * Recursively generates a JSON of the Player instance and all its variables
	 * @return JSON of the Player instance
	 */
	public String toJSON(){
		String json = "{";
		json += "\"name\": \"" + name + "\"";
		json += ",\"points\": " + String.valueOf(points);
		json += ",\"Gold\": " + getGold();
		json += ",\"Coal\": " + getFuel("Coal");
		json += ",\"Electric\": " + getFuel("Electric");
		json += ",\"Nuclear\": " + getFuel("Nuclear");
		json += ",\"Oil\": " + getFuel("Oil");
		
		//TODO does shop need to be saved?!
		
		json += ",\"Goals\":[";
		for(int i = 0; i < goals.size(); i++){
			json += goals.get(i).toJSON();
			if((i+1) != goals.size()){
				json += ",";
			}
		}
		json += "]";
		
		json += ",\"Trains\":[";
		for(int i = 0; i < trains.size(); i++){
			json += trains.get(i).toJSON();
			if((i+1) != trains.size()){
				json += ",";
			}
		}
		json += "]";
		
		json += ",\"Stations\":[";
		for(int i = 0; i < stations.size(); i++){
			json += stations.get(i).toJSON();
			if((i+1) != stations.size()){
				json += ",";
			}
		}
		json += "]}";
		return json;
	}
	
}