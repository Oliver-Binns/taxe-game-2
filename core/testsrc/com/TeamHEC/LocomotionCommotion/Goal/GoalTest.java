package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Card.Card;
import com.TeamHEC.LocomotionCommotion.Goal.Graph.Dijkstra;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.Line;
import com.TeamHEC.LocomotionCommotion.Map.MapInstance;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Electric;
import com.TeamHEC.LocomotionCommotion.Resource.Gold;
import com.TeamHEC.LocomotionCommotion.Resource.Nuclear;
import com.TeamHEC.LocomotionCommotion.Resource.Oil;
import com.TeamHEC.LocomotionCommotion.Train.OilTrain;
import com.TeamHEC.LocomotionCommotion.Train.Route;
import com.TeamHEC.LocomotionCommotion.Train.Train;

@RunWith(GdxTestRunner.class)
public class GoalTest {

	Goal goal;
	Train train;
	MapInstance wm;
	Station ss,ms,fs;
    Player player;
    Connection tempConnection1, tempConnection2, tempConnectionShort;

    //Method copied from Goal Factory to use for test Goal
    private int genReward(Station sStation, Station fStation){
        Dijkstra d = new Dijkstra(); //implements dijkstra
        d.computePaths(d.lookUpNode(sStation)); //uses the lookup function to get instance of a
        //station and compute paths
        double rew = d.lookUpNode(fStation).minDistance; //
        return (int) rew; //returns reward casted to integer
    }

    //Method to check station belongs to world
    private boolean compareStations(String Sname){
        for (int i = 0; i < wm.stationList().length; i++){
            if (Sname == wm.stationList()[i].getName()){
                return true;
            }
        }
        return false;
    }


    @Before
	public void setUp() throws Exception {
    	GameData.TEST_CASE = true;
        wm = WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP);
        
        wm.reset();

        //GoalFactory gf = new GoalFactory(1);
		//goal = gf.CreateRandomGoal();    //Random goals are tested in GoalFactoryTest

        //Setup a non-random goal

        ss = wm.stationList()[0];	//Start goal at first station
        ms = wm.stationList()[1];
        fs = wm.stationList()[2];	//End goal at third station
        tempConnection1 = new Connection(ss, ms, Line.Black);
        tempConnection2 = new Connection(ms, fs, Line.Black);
        tempConnectionShort = new Connection(ss, fs, Line.Black);
        wm.addConnection(tempConnection1);
        wm.addConnection(tempConnection2);
        wm.addConnection(tempConnectionShort);

        goal = new Goal(ss, fs, 0, "Passenger", genReward(ss, fs));


        //Set up player and resources
		String name = "Player 1";
		int points = 0;
		Gold gold = new Gold(1000);
		Coal coal = new Coal(2000);
		Oil oil = new Oil(2000);
		Electric electric = new Electric(2000);
		Nuclear nuclear = new Nuclear(2000);
		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<Goal> goals = new ArrayList<Goal>();
		ArrayList<Train> trains = new ArrayList<Train>();
		
		player = new Player(
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
		
		//Setup train and its route to complete goal
		train = new OilTrain(0, true, new Route(wm.stationList()[0]), player);
        goal.assignTrain(train);
	}

	@Test
	public void testGoal() {
		assertTrue(compareStations(goal.getSStation()));
		assertTrue(compareStations(goal.getFStation()));
		wm.removeConnection(ss, ms);
		wm.removeConnection(ms, fs);
		wm.removeConnection(ss, fs);
	}

	@Test
	public void testAssignTrain() {
		assertTrue("", goal.getTrain() == train);
		wm.removeConnection(ss, ms);
		wm.removeConnection(ms, fs);
		wm.removeConnection(ss, fs);
	}


	@Test
     public void testisSpecial(){
	   assertTrue(goal.isSpecial() == false);
	   wm.removeConnection(ss, ms);
       wm.removeConnection(ms, fs);
       wm.removeConnection(ss, fs);
   }

    @Test
    public void testgetReward(){
	   assertTrue(goal.getReward() > 0);
	   wm.removeConnection(ss, ms);
       wm.removeConnection(ms, fs);
       wm.removeConnection(ss, fs);
   }


	@Test 
	public void testgetStartDate(){
		assertTrue(goal.getStartDate() != null);
		wm.removeConnection(ss, ms);
        wm.removeConnection(ms, fs);
        wm.removeConnection(ss, fs);
	}



    //Team EEP Tests for Goal:

    @Test
    public void testgetCurrentGoalDuration() {
        assertEquals("Goal should be at turn #0", goal.getCurrentGoalDuration(), 0);

        goal.incrementCurrentGoalDuration();

        assertEquals(" Goal should be at turn #1", goal.getCurrentGoalDuration(), 1);
        wm.removeConnection(ss, ms);
        wm.removeConnection(ms, fs);
        wm.removeConnection(ss, fs);
    }


    @Test
    public void testCompleteGoalUsingOptimalPath() {
    	
    	train.route.addConnection(tempConnectionShort);
    	
        int startingReward = train.getOwner().getGold();
        int expectedEndingReward = startingReward + goal.getReward();
        assertEquals("Optimal duration is not calculated correctly", (int) (tempConnectionShort.getLength()/train.getBaseSpeed()), goal.estimateOptimalDuration());
        int expectedEndingScore = goal.estimateOptimalDuration() * goal.getReward() / goal.estimateOptimalDuration(); //I.e. as optimal route chosen, Score = Reward
        
        assertFalse("Goal should not be complete", goal.goalComplete());
        
        int turnCount = goal.estimateOptimalDuration() + 1;
        
        //This loop should complete the goal using the optimal path
        for(int i=0; i<turnCount; i++) {
        	train.route.update(train.getSpeed());
        	goal.incrementCurrentGoalDuration();
        }
        
        //Train should have reached goal destination by now
        assertEquals("If goal is completed reward will have been added to gold", expectedEndingReward, train.getOwner().getGold());
        assertEquals("Score should have been added to player", expectedEndingScore, train.getOwner().getPoints());
        wm.removeConnection(ss, ms);
        wm.removeConnection(ms, fs);
        wm.removeConnection(ss, fs);
    }

    @Test
    public void testCompleteGoalUsingSuboptimalPath() {

        int startingReward = train.getOwner().getGold();
        int expectedEndingReward = startingReward + goal.getReward();
        assertEquals("Optimal duration is not calculated correctly", (int) (tempConnectionShort.getLength()/train.getBaseSpeed()), goal.estimateOptimalDuration());

        //Reconstruct train so it has an empty route
        train = new OilTrain(0, true, new Route(WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).stationList()[0]), player);
        goal.assignTrain(train);
        
        int turnCount = (int) ((tempConnection1.getLength() + tempConnection2.getLength()) / train.getBaseSpeed());
        int expectedEndingScore = goal.estimateOptimalDuration() * goal.getReward() / turnCount;

        //Go from stationList()[0] to Dublin to Reykjavik to Oslo to Berlin to Dublin
        train.route.addConnection(tempConnection1);
        train.route.addConnection(tempConnection2);
        
        for (int turnNo = 0; turnNo < turnCount; turnNo++){
            train.route.update(train.getSpeed()); //Train is travelling at 80 per turn
            goal.incrementCurrentGoalDuration();
            assertFalse("Goal should not yet be complete", goal.goalComplete());
        }

        //Last turn
        train.route.update(train.getSpeed()); //Train is travelling at 80 per turn
        goal.incrementCurrentGoalDuration();

        //Train should have reached destination by now

        assertEquals("If goal is completed reward will have been added to gold", expectedEndingReward, train.getOwner().getGold());
        assertEquals("Score should have been added to player", expectedEndingScore, train.getOwner().getPoints());

    }
}