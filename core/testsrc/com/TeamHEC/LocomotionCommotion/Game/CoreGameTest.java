package com.TeamHEC.LocomotionCommotion.Game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Goal.Goal;
import com.TeamHEC.LocomotionCommotion.Goal.GoalFactory;
import com.TeamHEC.LocomotionCommotion.Map.Line;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Resource;

/**
 * 
 * @author Callum Hewitt <ch1194@york.ac.uk>
 *
 */

@RunWith(GdxTestRunner.class)
public class CoreGameTest {
	
	String player1Name;
	String player2Name;
	Station Player1Start;
	Station Player2Start;	
	ArrayList<Station> player1StationList;
	ArrayList<Station> player2StationList;	
	CoreGame tester;
	int turnLimit;	
	int baseGold;
	int baseCarriage;	
	int baseCoal;
	int baseOil;
	int baseElectric;
	int baseNuclear;
	
	@Before
	public void setUp()	{		
		Line[] line1 = new Line[3];
		Line[] line2 = new Line[3];
		line1[0] = Line.Red;
		line1[1] = Line.Blue;
		line1[2] = Line.Black;
		line2[0] = Line.Green;
		line2[1] = Line.Yellow;
		line2[2] = Line.Orange;
		
		player1Name = "Alice";
		player2Name = "Ben";
		Player1Start = WorldMap.getInstance().ATHENS;
		Player2Start = WorldMap.getInstance().BERLIN;	

		turnLimit = 50;	

		baseGold = 1000;
		baseCarriage = 200;
		baseCoal = 200;
		baseOil = 200;
		baseElectric = 200;
		baseNuclear = 200;
		
		tester = new CoreGame(player1Name, player2Name, Player1Start, Player2Start, turnLimit);
	}
	
	@After
	public void tearDown() {
		WorldMap.getInstance().ATHENS.setOwner(null);
		WorldMap.getInstance().BERLIN.setOwner(null);
	}
	
	//Private Accessors
	/**
	 * Gets the field value from an instance.  The field we wish to retrieve is
	 * specified by passing the name.  The value will be returned, even if the
	 * field would have private or protected access.
	 */
	@SuppressWarnings("rawtypes")
	private Object getField( Object instance, String name ) throws Exception
	{
		Class c = instance.getClass();

		// Retrieve the field with the specified name
		Field f = c.getDeclaredField( name );

		// Make sure the field is accessible, even if it
		// would be private or protected
		f.setAccessible( true );

		// Return the value of the field for the instance
		return f.get( instance );
	}

	/**
	 * Executes a method on an object instance.  The name and parameters of
	 * the method are specified.  The method will be executed and the value
	 * of it returned, even if the method would have private or protected access.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object executeMethod( Object instance, String name, Object[] params ) throws Exception
	{
		Class c 	= instance.getClass();

		// Fetch the Class types of all method parameters
		Class[] types 	= new Class[params.length];

		for ( int i = 0; i < params.length; i++ )
			types[i] = params[i].getClass();

		Method m        = c.getDeclaredMethod( name, types );

		// Make sure the method is accessible
		m.setAccessible( true );

		return m.invoke( instance, params );
	}
	
	@Test
	public void testCoreGame() throws Exception {			
		assertTrue("player1Name was incorrectly set", tester.getPlayer1().getName() == player1Name);
		assertTrue("player2Name was incorrectly set", tester.getPlayer2().getName() == player2Name);
		
		assertTrue("player1's Gold was incorrectly set", tester.getPlayer1().getGold() == baseGold - Player1Start.getBaseValue());	
		assertTrue("player2's Gold was incorrectly set", tester.getPlayer2().getGold() == baseGold - Player2Start.getBaseValue());	
		if(tester.getPlayerTurn() == tester.getPlayer1())		
			assertTrue("player1's Coal was incorrectly set", tester.getPlayer1().getFuel("Coal") == baseCoal + Player1Start.getTotalResourceOut());
		else
			assertTrue("player1's Coal was incorrectly set", tester.getPlayer1().getFuel("Coal") == baseCoal);			
		assertTrue("player2's Coal was incorrectly set", tester.getPlayer2().getFuel("Coal") == baseCoal);
		assertTrue("player1's Oil was incorrectly set", tester.getPlayer1().getFuel("Oil") == baseOil);
		assertTrue("player2's Oil was incorrectly set", tester.getPlayer2().getFuel("Oil") == baseOil);
		assertTrue("player1's Electric was incorrectly set", tester.getPlayer1().getFuel("Electric") == baseElectric);
		assertTrue("player2's Electric was incorrectly set", tester.getPlayer2().getFuel("Electric") == baseElectric);
		assertTrue("player1's Nuclear was incorrectly set", tester.getPlayer1().getFuel("Nuclear") == baseNuclear);
		if(tester.getPlayerTurn() == tester.getPlayer2())		
			assertTrue("player2's Nuclear was incorrectly set", tester.getPlayer2().getFuel("Nuclear") == baseNuclear + Player2Start.getTotalResourceOut());
		else
			assertTrue("player2's Nuclear was incorrectly set", tester.getPlayer2().getFuel("Nuclear") == baseNuclear);
				
		assertTrue("player1's Station list was incorrectly set", tester.getPlayer1().getStations().get(0) == Player1Start);
		assertTrue("player2's Station list was incorrectly set", tester.getPlayer2().getStations().get(0) == Player2Start);
		assertTrue("player1's Goal list was incorrectly set", tester.getPlayer1().getGoals().equals(new ArrayList<Goal>()));
		assertTrue("player2's Goal list was incorrectly set", tester.getPlayer2().getGoals().equals(new ArrayList<Goal>()));
		assertTrue("player1's Train list was incorrectly set", tester.getPlayer1().getTrains().size() == 1);
		assertTrue("player2's Train list was incorrectly set", tester.getPlayer2().getTrains().size() == 1);
		
		assertTrue("turnCount was not zero", tester.getTurnCount() == 0);
		assertTrue("turnLimit was not equal to " + turnLimit, tester.getTurnLimit() == 50);
		assertTrue("playerTurn was not null", tester.getPlayerTurn() != null);
		assertTrue("gameMap was not initialsed", (WorldMap) getField(tester, "gameMap") != null);		
	}
	
	@Test
	public void testFlipCoin() throws Exception {
		for(int i=0; i<10000; i++)
		{
			int x = (Integer) executeMethod(tester, "flipCoin", new Object[] {} );
			assertTrue(x == 0 || x == 1);					
		}
	}

	@Test
	public void testEndTurn() {
		//Setup
		Player initialPlayer = tester.getPlayerTurn();
		int initialCount = tester.getTurnCount();
		
		//Execute
		tester.EndTurn();
		assertTrue(initialPlayer.getName() != tester.getPlayerTurn().getName());
		assertTrue(tester.getTurnCount() == initialCount + 1);
		tester.EndTurn();
		assertTrue(initialPlayer.getName() == tester.getPlayerTurn().getName());
		assertTrue(tester.getTurnCount() == initialCount + 2);			
	}

	@Test
	public void testStartTurn() {
		//Setup		
		if(tester.getPlayerTurn() != tester.getPlayer1())
			tester.EndTurn();
		int coal = tester.getPlayer1().getFuel("Coal");
		
		//Execution
		tester.StartTurn();
		assertTrue("Player1's coal was not the value expected after StartTurn() executed once", tester.getPlayer1().getFuel("Coal") == coal + Player1Start.getTotalResourceOut());
		tester.StartTurn();
		assertTrue("Player1's coal was not the value expected after StartTurn() executed twice", tester.getPlayer1().getFuel("Coal") == coal + 2*Player1Start.getTotalResourceOut());
		tester.StartTurn();
		assertTrue("Player1's coal was not the value expected after StartTurn() executed three times", tester.getPlayer1().getFuel("Coal") == coal + 3*Player1Start.getTotalResourceOut());
	}

	@Test
	public void testGetBaseResources() {
		HashMap<String, Resource> checker = tester.getBaseResources(Player1Start);
		
		assertTrue("Gold was incorrectly set", checker.get("gold").getValue() == baseGold);	
		assertTrue("Coal was incorrectly set", checker.get("coal").getValue() == baseCoal);
		assertTrue("Oil was incorrectly set", checker.get("oil").getValue() == baseOil);
		assertTrue("Electric was incorrectly set", checker.get("electric").getValue() == baseElectric);
		assertTrue("Nuclear was incorrectly set", checker.get("nuclear").getValue() == baseNuclear);
	}

	@Test
	public void testGetGameMap() throws Exception {
		assertTrue(tester.getGameMap() == (WorldMap) getField(tester, "gameMap"));
	}
	
	@Test
	public void testGetPlayer1() throws Exception {
		assertTrue(tester.getPlayer1() == (Player) getField(tester, "player1"));
	}

	@Test
	public void testGetPlayer2() throws Exception {
		assertTrue(tester.getPlayer2() == (Player) getField(tester, "player2"));
	}
	
	@Test
	public void testGetTurnCount() throws Exception {
		assertTrue(tester.getTurnCount() == (Integer) getField(tester, "turnCount"));
	}

	@Test
	public void testGetTurnLimit() throws Exception {
		assertTrue(tester.getTurnLimit() == (Integer) getField(tester, "turnLimit"));
	}
	
	@Test
	public void testGetPlayerTurn() throws Exception {
		assertTrue(tester.getPlayerTurn() == (Player) getField(tester, "playerTurn"));
	}

	@Test
	public void testSaveGameJSON() throws Exception {
		//Setup
		GoalFactory gF = new GoalFactory(1);
		Goal testGoal1 = gF.CreateRandomGoal();
		Goal testGoal2 = gF.CreateRandomGoal();
		
		tester.getPlayer1().addGold(5000);
		tester.getPlayer2().addGold(5000);
		tester.getPlayer1().getShop().buyCard(true);
		tester.getPlayer2().getShop().buyCard(true);
		tester.getPlayer1().getTrains().get(0).route.addConnection(tester.getPlayer1().getTrains().get(0).route.getAdjacentConnections().get(0));
		tester.getPlayer2().getTrains().get(0).route.addConnection(tester.getPlayer2().getTrains().get(0).route.getAdjacentConnections().get(0));
		
		tester.getPlayer1().getGoals().add(testGoal1);
		tester.getPlayer2().getGoals().add(testGoal2);
		
		String error = "";
		boolean success = false;
		try
		{
			tester.saveGameJSON("myGame");
			success = true;
		}
		catch (Exception ex)
		{
			success = false;
			error = ex.getMessage();
		}		
		assertTrue("saveGameJSON did not execute successfully. " + error, success);
		
		File filePath = new File(
				System.getProperty("user.home") +
				System.getProperty("file.separator") + 
				"LocomotionCommotion" + 
				System.getProperty("file.separator") + 
				"myGame.json");
		
		assertTrue("saveGameJSON did not create the necessary json file.", filePath.exists());
		
		FileReader reader = new FileReader(filePath);
		JSONParser jsonParser = new JSONParser();		
		JSONObject game = (JSONObject) jsonParser.parse(reader);
		
		JSONObject player1 = (JSONObject) game.get("player1");
		JSONObject player1Resources = (JSONObject) player1.get("resources");
		JSONArray player1Cards = (JSONArray) player1.get("cards");
		JSONArray player1Trains = (JSONArray) player1.get("trains");
		JSONObject player1Route = (JSONObject) ((JSONObject) player1Trains.get(0)).get("route");
		JSONArray player1Connections = (JSONArray) player1Route.get("connections");
		JSONObject player1StartMapObj = (JSONObject) ((JSONObject) player1Connections.get(0)).get("startMapObj");
		JSONObject player1EndMapObj = (JSONObject) ((JSONObject) player1Connections.get(0)).get("endMapObj");
		JSONArray player1Stations = (JSONArray) player1.get("stations");
		JSONArray player1Goals = (JSONArray) player1.get("goals");
		JSONObject player2 = (JSONObject) game.get("player2");	
		JSONObject player2Resources = (JSONObject) player2.get("resources");
		JSONArray player2Cards = (JSONArray) player2.get("cards");
		JSONArray player2Trains = (JSONArray) player2.get("trains");
		JSONObject player2Route = (JSONObject) ((JSONObject) player2Trains.get(0)).get("route");
		JSONArray player2Connections = (JSONArray) player2Route.get("connections");
		JSONObject player2StartMapObj = (JSONObject) ((JSONObject) player2Connections.get(0)).get("startMapObj");
		JSONObject player2EndMapObj = (JSONObject) ((JSONObject) player2Connections.get(0)).get("endMapObj");
		JSONArray player2Stations = (JSONArray) player2.get("stations");
		JSONArray player2Goals = (JSONArray) player2.get("goals");
		String playerTurn = (String) game.get("playerTurn");
		Long turnCountL = (Long) game.get("turnCount");
		Long turnLimitL = (Long) game.get("turnLimit");
		
		int turnCount;
		int turnLimit;
		
		if(turnCountL < Long.MAX_VALUE && turnCountL > Long.MIN_VALUE)
			turnCount = turnCountL.intValue();
		else
			throw new IllegalArgumentException("turnCount was too large to be cast to an int.");
		
		if(turnLimitL < Long.MAX_VALUE && turnLimitL > Long.MIN_VALUE)
			turnLimit = turnLimitL.intValue();
		else
			throw new IllegalArgumentException("turnLimit was too large to be cast to an int.");
		
		assertTrue("Player turn was not saved correctly", tester.getPlayerTurn().getName().equals(playerTurn));
		assertTrue("turnCount was not saved correctly", tester.getTurnCount() == turnCount);
		assertTrue("turnLimit was not saved correctly", tester.getTurnLimit() == turnLimit);		
		
		//Assertions
		//Resources 1
		assertTrue("Player 1 Gold value was not saved correctly", tester.getPlayer1().getGold() == (Long) player1Resources.get("gold"));
		assertTrue("Player 1 Coal value was not saved correctly", tester.getPlayer1().getFuel("Coal") == (Long) player1Resources.get("coal"));
		assertTrue("Player 1 Oil value was not saved correctly", tester.getPlayer1().getFuel("Oil") == (Long) player1Resources.get("oil"));
		assertTrue("Player 1 Electric value was not saved correctly", tester.getPlayer1().getFuel("Electric") == (Long) player1Resources.get("electric"));
		assertTrue("Player 1 Nuclear value was not saved correctly", tester.getPlayer1().getFuel("Nuclear") == (Long) player1Resources.get("nuclear"));
		
		//Cards 1
		assertTrue("Player 1 Cards list was not saved correctly", tester.getPlayer1().getCards().get(0).getName().equals(((JSONObject) player1Cards.get(0)).get("cardType")));
		
		//Trains 1
		assertTrue(
				"Player 1 Trains type was not saved correctly", 
				tester.getPlayer1().getTrains().get(0).getFuelType().equals(((JSONObject) player1Trains.get(0)).get("type")));
		assertTrue(
				"Player 1 Trains isInStation was not saved correctly",
				tester.getPlayer1().getTrains().get(0).isInStation() == (Boolean) ((JSONObject) player1Trains.get(0)).get("inStation"));
		assertTrue(
				"Player 1 Trains speedMod was not saved correctly",
				tester.getPlayer1().getTrains().get(0).getSpeedMod() == (Long) ((JSONObject) player1Trains.get(0)).get("speedMod"));
		
		//Route 1
		assertTrue(
				"Player 1 Trains routeIndex was not saved correctly",
				tester.getPlayer1().getTrains().get(0).getRoute().getRouteIndex() == (Long) player1Route.get("routeIndex"));
		assertTrue(
				"Player 1 Trains connectionsTravelled was not saved correctly",
				tester.getPlayer1().getTrains().get(0).getRoute().getConnectionTravelled() == (Double) player1Route.get("connectionTravelled"));
		assertTrue(
				"Player 1 Route Connections startMapObj x co-ordinate was not saved correctly",
				tester.getPlayer1().getTrains().get(0).route.getRoute().get(0).getStartMapObj().actor.actorX == (Double) player1StartMapObj.get("x"));
		assertTrue(
				"Player 1 Route Connections startMapObj y co-ordinate was not saved correctly",
				tester.getPlayer1().getTrains().get(0).route.getRoute().get(0).getStartMapObj().actor.actorY == (Double) player1StartMapObj.get("y"));
		assertTrue(
				"Player 1 Route Connections endMapObj x co-ordinate was not saved correctly",
				tester.getPlayer1().getTrains().get(0).route.getRoute().get(0).getDestination().actor.actorX == (Double) player1EndMapObj.get("x"));
		assertTrue(
				"Player 1 Route Connections endMapObj y co-ordinate was not saved correctly",
				tester.getPlayer1().getTrains().get(0).route.getRoute().get(0).getDestination().actor.actorY == (Double) player1EndMapObj.get("y"));
		
		//Stations 1
		assertTrue(
				"Player 1 stationName was not saved correctly",
				tester.getPlayer1().getStations().get(0).getName().equals(((JSONObject) player1Stations.get(0)).get("stationName")));
		assertTrue(
				"Player 1 resourceOutMod was not saved correctly",
				tester.getPlayer1().getStations().get(0).getResourceOutMod() == (Long) ((JSONObject) player1Stations.get(0)).get("resourceOutMod"));
		assertTrue(
				"Player 1 rentValueMod was not saved correctly",
				tester.getPlayer1().getStations().get(0).getRentValueMod() == (Long) ((JSONObject) player1Stations.get(0)).get("rentValueMod"));
		assertTrue(
				"Player 1 valueMod was not saved correctly",
				tester.getPlayer1().getStations().get(0).getValueMod() == (Long) ((JSONObject) player1Stations.get(0)).get("valueMod"));
		
		
		//Goals 1
		assertTrue(
				"Player 1 start station was not saved correctly",
				tester.getPlayer1().getGoals().get(0).getSStation().equals(((JSONObject) player1Goals.get(0)).get("SStation")));
		assertTrue(
				"Player 1 final station was not saved correctly",
				tester.getPlayer1().getGoals().get(0).getFStation().equals(((JSONObject) player1Goals.get(0)).get("FStation")));
		assertTrue(
				"Player 1 via station was not saved correctly",
				tester.getPlayer1().getGoals().get(0).getTimeConstraintString().equals(((JSONObject) player1Goals.get(0)).get("stationVia")));
		assertTrue(
				"Player 1 special bool was not saved correctly",
				tester.getPlayer1().getGoals().get(0).isSpecial() == (Boolean) ((JSONObject) player1Goals.get(0)).get("special"));
		assertTrue(
				"Player 1 reward was not saved correctly",
				tester.getPlayer1().getGoals().get(0).getReward() == (Long) ((JSONObject) player1Goals.get(0)).get("reward"));
		assertTrue(
				"Player 1 cargo was not saved correctly",
				tester.getPlayer1().getGoals().get(0).getCargo().equals(((JSONObject) player1Goals.get(0)).get("cargo")));
		
		//Assertions
		//Resources 2
		assertTrue("Player 2 Gold value was not saved correctly", tester.getPlayer2().getGold() == (Long) player2Resources.get("gold"));
		assertTrue("Player 2 Coal value was not saved correctly", tester.getPlayer2().getFuel("Coal") == (Long) player2Resources.get("coal"));
		assertTrue("Player 2 Oil value was not saved correctly", tester.getPlayer2().getFuel("Oil") == (Long) player2Resources.get("oil"));
		assertTrue("Player 2 Electric value was not saved correctly", tester.getPlayer2().getFuel("Electric") == (Long) player2Resources.get("electric"));
		assertTrue("Player 2 Nuclear value was not saved correctly", tester.getPlayer2().getFuel("Nuclear") == (Long) player2Resources.get("nuclear"));
		
		//Cards 2
		assertTrue("Player 2 Cards list was not saved correctly", tester.getPlayer2().getCards().get(0).getName().equals(((JSONObject) player2Cards.get(0)).get("cardType")));
				
		//Trains 2
		assertTrue(
				"Player 2 Trains type was not saved correctly", 
				tester.getPlayer2().getTrains().get(0).getFuelType().equals(((JSONObject) player2Trains.get(0)).get("type")));
		assertTrue(
				"Player 2 Trains isInStation was not saved correctly",
				tester.getPlayer2().getTrains().get(0).isInStation() == (Boolean) ((JSONObject) player2Trains.get(0)).get("inStation"));
		assertTrue(
				"Player 2 Trains speedMod was not saved correctly",
				tester.getPlayer2().getTrains().get(0).getSpeedMod() == (Long) ((JSONObject) player2Trains.get(0)).get("speedMod"));		
		
		//Route 2
		assertTrue(
				"Player 2 Trains routeIndex was not saved correctly",
				tester.getPlayer2().getTrains().get(0).getRoute().getRouteIndex() == (Long) player2Route.get("routeIndex"));
		assertTrue(
				"Player 2 Trains connectionsTravelled was not saved correctly",
				tester.getPlayer2().getTrains().get(0).getRoute().getConnectionTravelled() == (Double) player2Route.get("connectionTravelled"));
		assertTrue(
				"Player 2 Route Connections startMapObj x co-ordinate was not saved correctly",
				tester.getPlayer2().getTrains().get(0).route.getRoute().get(0).getStartMapObj().actor.actorX == (Double) player2StartMapObj.get("x"));
		assertTrue(
				"Player 2 Route Connections startMapObj y co-ordinate was not saved correctly",
				tester.getPlayer2().getTrains().get(0).route.getRoute().get(0).getStartMapObj().actor.actorY == (Double) player2StartMapObj.get("y"));
		assertTrue(
				"Player 2 Route Connections endMapObj x co-ordinate was not saved correctly",
				tester.getPlayer2().getTrains().get(0).route.getRoute().get(0).getDestination().actor.actorX == (Double) player2EndMapObj.get("x"));
		assertTrue(
				"Player 2 Route Connections endMapObj y co-ordinate was not saved correctly",
				tester.getPlayer2().getTrains().get(0).route.getRoute().get(0).getDestination().actor.actorY == (Double) player2EndMapObj.get("y"));
		
		//Stations 2
		assertTrue(
				"Player 2 stationName was not saved correctly",
				tester.getPlayer2().getStations().get(0).getName().equals(((JSONObject) player2Stations.get(0)).get("stationName")));
		assertTrue(
				"Player 2 resourceOutMod was not saved correctly",
				tester.getPlayer2().getStations().get(0).getResourceOutMod() == (Long) ((JSONObject) player2Stations.get(0)).get("resourceOutMod"));
		assertTrue(
				"Player 2 rentValueMod was not saved correctly",
				tester.getPlayer2().getStations().get(0).getRentValueMod() == (Long) ((JSONObject) player2Stations.get(0)).get("rentValueMod"));
		assertTrue(
				"Player 2 valueMod was not saved correctly",
				tester.getPlayer2().getStations().get(0).getValueMod() == (Long) ((JSONObject) player2Stations.get(0)).get("valueMod"));
		
		
		//Goals 2
		assertTrue(
				"Player 2 start station was not saved correctly",
				tester.getPlayer2().getGoals().get(0).getSStation().equals(((JSONObject) player2Goals.get(0)).get("SStation")));
		assertTrue(
				"Player 2 final station was not saved correctly",
				tester.getPlayer2().getGoals().get(0).getFStation().equals(((JSONObject) player2Goals.get(0)).get("FStation")));
		assertTrue(
				"Player 2 via station was not saved correctly",
				tester.getPlayer2().getGoals().get(0).getTimeConstraintString().equals(((JSONObject) player2Goals.get(0)).get("stationVia")));
		assertTrue(
				"Player 2 special bool was not saved correctly",
				tester.getPlayer2().getGoals().get(0).isSpecial() == (Boolean) ((JSONObject) player2Goals.get(0)).get("special"));
		assertTrue(
				"Player 2 reward was not saved correctly",
				tester.getPlayer2().getGoals().get(0).getReward() == (Long) ((JSONObject) player2Goals.get(0)).get("reward"));
		assertTrue(
				"Player 2 cargo was not saved correctly",
				tester.getPlayer2().getGoals().get(0).getCargo().equals(((JSONObject) player2Goals.get(0)).get("cargo")));			
	}


    //Team EEP tests for EndGame:

    @Test
    public void testPlayerOneWinGame(){

        //Start first turn
        tester.StartTurn();

        assertTrue("Player one should be playing", tester.getPlayer1().isStillPlaying());

        assertTrue("Player two should be playing", tester.getPlayer2().isStillPlaying());

        //Give player one some points for sake of test so they win
        tester.getPlayer1().incrementPoints(100);

        //Increment turns until last turn is reached
        while (tester.getTurnCount() < tester.getTurnLimit()){
            tester.EndTurn();
        }

        //End last turn
        tester.EndTurn();

        assertEquals("Turn count should equal turn limit", tester.getTurnLimit(), tester.getTurnCount());

        assertTrue("Player one should be winner", tester.getPlayer1().hasWon());

        assertTrue("Player two should be loser", tester.getPlayer2().hasLost());

        //Try ending turn again even though game has ended
        tester.EndTurn();

        assertEquals("Turn count should still equal turn limit", tester.getTurnLimit(), tester.getTurnCount());


    }

    @Test
    public void testPlayerTwoWinGame(){
        //Start first turn
        tester.StartTurn();

        assertTrue("Player one should still be playing", tester.getPlayer1().isStillPlaying());

        assertTrue("Player two should still be playing", tester.getPlayer2().isStillPlaying());

        //Give player one some points for sake of test so they win
        tester.getPlayer2().incrementPoints(100);

        //Increment turns until last turn is reached
        while (tester.getTurnCount() < tester.getTurnLimit()){
            tester.EndTurn();
        }

        //End last turn
        tester.EndTurn();

        assertEquals("Turn count should equal turn limit", tester.getTurnLimit(), tester.getTurnCount());

        assertTrue("Player two should be winner", tester.getPlayer2().hasWon());

        assertTrue("Player one should be loser", tester.getPlayer1().hasLost());

        //Try ending turn again even though game has ended
        tester.EndTurn();

        assertEquals("Turn count should still equal turn limit", tester.getTurnLimit(), tester.getTurnCount());

    }

    @Test
    public void testPlayerDraw(){

        int expectedTurnCount;

        //Start first turn
        tester.StartTurn();

        assertTrue("Player one should still be playing", tester.getPlayer1().isStillPlaying());

        assertTrue("Player two should still be playing", tester.getPlayer2().isStillPlaying());

        //Should keep going for over 50 turns as player's scores are equal
        for (expectedTurnCount = 0; expectedTurnCount < 1000; expectedTurnCount++){
            tester.EndTurn();
        }

        assertEquals("Turn count is not expected value", expectedTurnCount, tester.getTurnCount());

        assertTrue("Player one should still be playing", tester.getPlayer1().isStillPlaying());

        assertTrue("Player two should still be playing", tester.getPlayer2().isStillPlaying());
    }
}













