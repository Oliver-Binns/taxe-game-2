package com.TeamHEC.LocomotionCommotion.Train;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 */

@RunWith(GdxTestRunner.class)
public class RouteTest {
	
	Train train;
	
	@Before
	public void setUp() throws Exception {
				
		String name = "Player 1";
		int points = 0;
		Gold gold = new Gold(1000);
		Coal coal = new Coal(200);
		Oil oil = new Oil(200);
		Electric electric = new Electric(200);
		Nuclear nuclear = new Nuclear(200);
		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<Goal> goals = new ArrayList<Goal>();
		ArrayList<Train> trains = new ArrayList<Train>();
		
		Player player = new Player(
				name,
				points,
				gold,
				coal,
				electric,
				nuclear,
				oil,
				cards,	
				goals,
				trains);
				
		train = new CoalTrain(0, true, new Route(WorldMap.getInstance().REYKJAVIK), player);
		train.route.train = train;
		
		// Add reykjavik > Oslo
		train.route.addConnection(train.route.getAdjacentConnections().get(0));
	}

	@Test
	public void testGetTrainPos()
	{
		// Reyk = 211f, 820f
		// Olso = 731f, 820f
		
		assertTrue("Train coordinates do not match start of route",
				train.route.getTrainPos().x == WorldMap.getInstance().REYKJAVIK.x && train.route.getTrainPos().y == WorldMap.getInstance().REYKJAVIK.y);
		
		// As Oslo and Reky have the same y coordinate, the x coordinate should be reyk + 10:
		train.route.update(10);
		
		assertTrue("Train coordinates do not match REYK.x + 10",
				train.route.getTrainPos().x == WorldMap.getInstance().REYKJAVIK.x + 10 && train.route.getTrainPos().y == WorldMap.getInstance().REYKJAVIK.y);
		
		// Go to the end of the route:
		train.route.update(2000);
		
		assertTrue("Train not in OLSO", train.route.getTrainPos().x == WorldMap.getInstance().OSLO.x &&
				train.route.getTrainPos().y == WorldMap.getInstance().OSLO.y);
	}
	
	@Test
	public void testRouteReloadConstructor()
	{
		ArrayList<Connection> tempRoute = new ArrayList<Connection>();
		
		// Add reky to oslo
		tempRoute.add(WorldMap.getInstance().REYKJAVIK.connections.get(0));
		Route newRoute = new Route(tempRoute, 0, 10f);
		
		// If the route loaded correctly, the train position should be reky.x + 10
		assertTrue("old route loaded incorrectly", newRoute.getTrainPos().x == WorldMap.getInstance().REYKJAVIK.x + 10);

		// Add olso to stock
		tempRoute.add(newRoute.getAdjacentConnections().get(0));
		
		// routeIndex = 1 and connectionTravelled = 0 therefore train in olso
		Route anotherRoute = new Route(tempRoute, 1, 0f);
		assertTrue("", anotherRoute.getTrainPos().x == WorldMap.getInstance().OSLO.x && anotherRoute.getTrainPos().y == WorldMap.getInstance().OSLO.y);
	}

	@Test
	public void testGetTotalLength()
	{
		// Add Oslo > Stockholm
		train.route.addConnection(train.route.getAdjacentConnections().get(0));
		
		assertTrue("RouteLength != reky>oslo + oslo>stockholm", train.route.getTotalLength() == (WorldMap.getInstance().REYKJAVIK.connections.get(0).getLength() +
				WorldMap.getInstance().OSLO.connections.get(0).getLength()));
		
		// Add Stockholm > Warsaw
		train.route.addConnection(train.route.getAdjacentConnections().get(1));
		
		assertTrue("RouteLength != reky>oslo + oslo>stock + stock>warsaw", train.route.getTotalLength() == (WorldMap.getInstance().REYKJAVIK.connections.get(0).getLength() +
				WorldMap.getInstance().OSLO.connections.get(0).getLength() + WorldMap.getInstance().STOCKHOLM.connections.get(1).getLength()));	
	}

	@Test
	public void testGetLengthRemaining()
	{
		// Move the train along the route by it's speed:
		train.route.update(train.getSpeed());
		assertTrue("Length remaining incorrrect", train.route.getLengthRemaining() == (train.route.getTotalLength() - train.getSpeed()));
		train.route.update(3);
		assertTrue("Length remaining incorrrect", train.route.getLengthRemaining() == (train.route.getTotalLength() - train.getSpeed() - 3));
	}

	@Test
	public void testInStation()
	{
		assertTrue("Train not in Station", train.route.inStation());
		// Move train out of station:
		train.route.update(train.getSpeed());
		assertFalse("Train in station", train.route.inStation());
		
		// Reach end of route (OSLO)
		train.route.update(2000);
		assertTrue("Train not in Station", train.route.inStation());
	}

	@Test
	public void testGetStation()
	{
		assertTrue("Train not in Reyk", train.route.getStation() == WorldMap.getInstance().REYKJAVIK);
		
		// Travel length of connection:
		
		float connectionLength = WorldMap.getInstance().REYKJAVIK.connections.get(0).getLength();
		train.route.update(connectionLength);
		
		assertTrue("train not in OSLO", train.route.getStation() == WorldMap.getInstance().OSLO);
		
		// Add Oslo > Stockholm
		train.route.addConnection(train.route.getAdjacentConnections().get(0));
		
		// Move out of station
		train.route.update(2);
		assertTrue("Station returned", train.route.getStation() == null);
	}

	@Test
	public void testUpdate()
	{
		float connectionLength = train.route.getRoute().get(train.route.getRouteIndex()).getLength();
		
		assertTrue("connectionTravelled 1 incorrect", train.route.getConnectionTravelled() == 0);
		
		// Move train it's connectionLength - 1
		train.route.update(connectionLength - 1);
		assertTrue("connectionTravlled 2 incorrect", train.route.getConnectionTravelled() == connectionLength - 1
				&& train.route.getRouteIndex() == 0);
		
		// Add Oslo > Stockholm
		train.route.addConnection(train.route.getAdjacentConnections().get(0));
		
		// Move the train into the next connection so that routeIndex increases by 1
		train.route.update(2);
		assertTrue("connectionTravelled 3 incorrect", train.route.getConnectionTravelled() == 1 && train.route.getRouteIndex() == 1);
		
		// move the train to the end of it's route:
		train.route.update(5000);
		assertTrue("route not complete", train.route.isComplete());
	}

}
