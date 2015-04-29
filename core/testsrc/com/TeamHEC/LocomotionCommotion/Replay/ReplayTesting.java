package com.TeamHEC.LocomotionCommotion.Replay;

import static org.junit.Assert.*;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Map.Junction;
import com.TeamHEC.LocomotionCommotion.Map.Line;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Card.CoalCard;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Obstacle.Obstacle;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Train.OilTrain;
import com.TeamHEC.LocomotionCommotion.Train.Route;
import com.TeamHEC.LocomotionCommotion.Train.Train;
import com.TeamHEC.LocomotionCommotion.Map.Connection;

@RunWith(GdxTestRunner.class)
public class ReplayTesting {

	/**
	 * 
	 * @author Oliver Binns <ob601@york.ac.uk>
	 *
	 */

	Player testPlayer;
	
	
	MapObj mapObj;
	Station station;
	Station station2;
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
	
	@Before
	public void setUp() throws Exception {
		GameData.TEST_CASE = true;
		
		trains = new ArrayList<Train>();
		goals = new ArrayList<Goal>();
		cards = new ArrayList<Card>();
		testPlayer = new Player("Name", 0, new Gold(0), new Coal(0), new Electric(0), new Nuclear(0), new Oil(0), cards, goals, trains);
		
		mapObj = new MapObj(0.5f, 1.0f, "Test Object", false);
		station = new Station("Helsinki", 100, new Oil(0), 100, new Line[]{ Line.Red, Line.Blue}, 100, 1.0f, 1.5f, false);
		junction = new Junction(1.6f, 1.9f, "Junction 1", false);

		testCard = new CoalCard(testPlayer);
		
		station2 = new Station("Helsinki", 100, new Oil(0), 100,  new Line[]{ Line.Red, Line.Blue}, 100, 1.0f, 1.5f, false);
		testGoal = new Goal(station, station2, 2, "Bob", 3);

		testRoute = new Route(station);
		
		testConnection = new Connection(station, junction, Line.Green);

		testTrain = new OilTrain(0, false, testRoute, testPlayer);
		testRoute.addConnection(testConnection);

		testObstacle = new Obstacle("Stop!", "Your train thinks it's a cow. Stop immediately.", 0.0, 5);
		
		testTrain.setObstacle(testObstacle);
		trains.add(testTrain);
		cards.add(testCard);
		goals.add(testGoal);
	}

	@Test
	public void testPlayer() {
		assertTrue("testPlayer JSON not output correctly.", testPlayer.toJSON().equals("{\"name\":\"Name\",\"points\":0,\"Gold\":0,\"Coal\":0,\"Electric\":0,\"Nuclear\":0,\"Oil\":0,\"Cards\":[{\"name\":\"Coal\"}],\"Goals\":[{\"sStation\":{\"name\":\"Helsinki\",\"type\":\"station\"},\"fStation\":{\"name\":\"Helsinki\",\"type\":\"station\"},\"timeConstraint\":2,\"cargo\":\"Bob\",\"reward\":3,\"startDate\":\"1\",\"startStationPassed\":false,\"isAbsolute\":false,\"finalStationPassed\":false,\"currentTime\":0,\"currentGoalDuration\":0}],\"Trains\":[{\"name\":\"Diesel Weasel\",\"baseSpeed\":80,\"speedMod\":0,\"Fuel\":\"Oil\",\"fuelPerTurn\":15,\"obstacle\":{\"name\":\"Stop!\",\"description\":\"Stop!\",\"speedFactor\":0.0,\"turnsElapsed\":0,\"totalTurns\":5},\"route\":{\"station\":\"Helsinki\",\"connections\":[{\"start\":{\"name\":\"Helsinki\",\"type\":\"station\"},\"end\":{\"name\":\"Junction 1\",\"type\":\"junction\"}}]}}],\"Stations\":[]}"));
	}

	
	@Test
	public void testMapObjs() {
		assertTrue("mapObj JSON not output correctly.", mapObj.toJSON().equals("{\"name\":\"Test Object\",\"type\":\"mapObj\"}"));
		assertTrue("station JSON not output correctly.", station.toJSON().equals("{\"name\":\"Helsinki\",\"type\":\"station\"}"));
		assertTrue("junction JSON not output correctly.", junction.toJSON().equals("{\"name\":\"Junction 1\",\"type\":\"junction\"}"));
	}

	@Test
	public void testCard() {
		assertTrue("card JSON not output correctly.", testCard.toJSON().equals("{\"name\":\"Coal\"}"));
	}
	
	@Test
	public void testGoal() {
		assertTrue("Goal JSON not output correctly.", testGoal.toJSON().equals("{\"sStation\":{\"name\":\"Helsinki\",\"type\":\"station\"},\"fStation\":{\"name\":\"Helsinki\",\"type\":\"station\"},\"timeConstraint\":2,\"cargo\":\"Bob\",\"reward\":3,\"startDate\":\"1\",\"startStationPassed\":false,\"isAbsolute\":false,\"finalStationPassed\":false,\"currentTime\":0,\"currentGoalDuration\":0}"));
	}

	@Test
	public void testRoute(){
		assertTrue("testRoute JSON not output correctly.", testRoute.toJSON().equals("{\"station\":\"Helsinki\",\"connections\":[{\"start\":{\"name\":\"Helsinki\",\"type\":\"station\"},\"end\":{\"name\":\"Junction 1\",\"type\":\"junction\"}}]}"));
	}
	
	@Test
	public void testConnection(){
		assertTrue("testPlayer JSON not output correctly.", testConnection.toJSON().equals("{\"start\":{\"name\":\"Helsinki\",\"type\":\"station\"},\"end\":{\"name\":\"Junction 1\",\"type\":\"junction\"}}"));
	}
	
	@Test
	public void testTrain(){
		assertTrue("train JSON not output correctly.", testTrain.toJSON().equals("{\"name\":\"Diesel Weasel\",\"baseSpeed\":80,\"speedMod\":0,\"Fuel\":\"Oil\",\"fuelPerTurn\":15,\"obstacle\":{\"name\":\"Stop!\",\"description\":\"Stop!\",\"speedFactor\":0.0,\"turnsElapsed\":0,\"totalTurns\":5},\"route\":{\"station\":\"Helsinki\",\"connections\":[{\"start\":{\"name\":\"Helsinki\",\"type\":\"station\"},\"end\":{\"name\":\"Junction 1\",\"type\":\"junction\"}}]}}"));
	}
	
	@Test
	public void testObstacle(){
		assertTrue("testObstacle JSON not output correctly.", testObstacle.toJSON().equals("{\"name\":\"Stop!\",\"description\":\"Stop!\",\"speedFactor\":0.0,\"turnsElapsed\":0,\"totalTurns\":5}"));
	}
}
