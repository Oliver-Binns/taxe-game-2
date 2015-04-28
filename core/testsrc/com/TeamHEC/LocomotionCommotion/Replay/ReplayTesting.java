package com.TeamHEC.LocomotionCommotion.Replay;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Map.Junction;
import com.TeamHEC.LocomotionCommotion.Map.Line;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Card.CoalCard;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Obstacle.Obstacle;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Fuel;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Train.OilTrain;
import com.TeamHEC.LocomotionCommotion.Train.Route;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.Map.Connection;

public class ReplayTesting {

	/**
	 * 
	 * @author Oliver Binns <ob601@york.ac.uk>
	 *
	 */

	Player testPlayer;
	
	
	MapObj mapObj;
	Station station;
	Junction junction;
	ArrayList<Train> trains;
	ArrayList<Goal> goals;
	ArrayList<Card> cards;
	
	Card testCard;
	
	Goal testGoal;
	
	Train testTrain;
	
	Obstacle testObstacle;
	
	Route testRoute;
	Connection testConnection;
	
	public void main (String[] arg) {
		setUp();
		testPlayer();
		testMapObjs();
		testCard();
		testGoal();
		testRoute();
		testConnection();
		testTrain();
		testObstacle();
		testCombined();
	}
	
	public void setUp(){
		GameData.TEST_CASE = true;
		
		trains = new ArrayList<Train>();
		goals = new ArrayList<Goal>();
		cards = new ArrayList<Card>();
		testPlayer = new Player("Name", 0, new Gold(0), new Coal(0), new Electric(0), new Nuclear(0), new Oil(0), cards, goals, trains);
	}

	public void testPlayer() {
		System.out.println(testPlayer.toJSON());
	}

	public void testMapObjs() {		
		mapObj = new MapObj(0.5f, 1.0f, "Test Object", false);
		station = new Station("Helsinki", 100, new Oil(0), 100,  new Line[]{ Line.Red, Line.Blue}, 100, 1.0f, 1.5f, false);
		junction = new Junction(1.6f, 1.9f, "Junction 1", false);
		
		System.out.println(mapObj.toJSON());
		System.out.println(station.toJSON());
		System.out.println(junction.toJSON());
	}
	
	public void testCard() {
		testCard = new CoalCard(testPlayer);
		
		System.out.println(testCard.toJSON());
	}
	
	public void testGoal() {
		Station station2 = new Station("Helsinki", 100, new Oil(0), 100,  new Line[]{ Line.Red, Line.Blue}, 100, 1.0f, 1.5f, false);
		
		testGoal = new Goal(station, station2, 2, "Bob", 3);

		System.out.println(testGoal.toJSON());
	}

	public void testRoute(){
		testRoute = new Route(station);
		
		System.out.println(testRoute.toJSON());
	}
	
	public void testConnection(){
		testConnection = new Connection(station, junction, Line.Green);
		testRoute.addConnection(testConnection);
		
		System.out.println(testConnection.toJSON());
		System.out.println(testRoute.toJSON());
	}
	
	public void testTrain(){
		testTrain = new OilTrain(0, false, testRoute, testPlayer);
		
		System.out.println(testTrain.toJSON());
	}
	
	public void testObstacle(){
		testObstacle = new Obstacle("Stop!", "Your train thinks it's a cow. Stop immediately.", 0.0, 5);
		
		System.out.println(testObstacle.toJSON());
	}
	
	public void testCombined(){
		testTrain.setObstacle(testObstacle);
		trains.add(testTrain);
		cards.add(testCard);
		goals.add(testGoal);
		
		System.out.println(testPlayer.toJSON());
	}
}
