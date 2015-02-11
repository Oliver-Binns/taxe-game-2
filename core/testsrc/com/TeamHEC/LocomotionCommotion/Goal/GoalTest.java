package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Goal.Graph.Dijkstra;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Card.Card;
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
	WorldMap wm;
	Station ss,fs;
    Connection connectOne, connectTwo;

    //Method copied from Goal Factory to use for test Goal
    private int genReward(Station sStation, Station fStation){
        Dijkstra d = new Dijkstra(); //implements dijkstra
        d.computePaths(d.lookUpNode(sStation)); //uses the loopup function to get instance of a
        //station and compute paths
        double rew = d.lookUpNode(fStation).minDistance; //
        return (int) rew; //returns reward casted to integer
    }

    //Method to check station belongs to world
    private boolean compareStations(String Sname){
        for (int i = 0; i < wm.stationsList.size(); i++){
            if (Sname == wm.stationsList.get(i).getName()){
                return true;
            }
        }
        return false;
    }


    @Before
	public void setUp() throws Exception {
        wm = WorldMap.getInstance();

        //GoalFactory gf = new GoalFactory(1);
		//goal = gf.CreateRandomGoal();    //Random goals are tested in GoalFactoryTest

        //Setup a non-random goal

        ss = wm.LONDON;      //Start goal at London
        fs = wm.AMSTERDAM;   //End goal at Amsterdam

        goal = new Goal(ss, fs, null, "Passenger", genReward(ss, fs));


        //Set up player and resources
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
		
		//Setup train and its route to complete goal
		train = new OilTrain(0, true, new Route(WorldMap.getInstance().LONDON), player);
        train.route.train = train;
        goal.assignTrain(train);

        connectOne = wm.LONDON.connections.get(0);  //Connection from London to Dublin
        train.route.addConnection(connectOne);

        connectTwo = wm.DUBLIN.connections.get(1);  //Connection from Dublin to Amsterdam
        train.route.addConnection(connectTwo);

	}

	@Test
	public void testGoal() {
		assertTrue(compareStations(goal.getSStation()));
		assertTrue(compareStations(goal.getFStation()));
        //assertTrue(goal.stationPassed(ss, train);
	}

	@Test
	public void testAssignTrain() {
		assertTrue("", goal.getTrain() == train);
	}


	@Test
     public void testisSpecial(){
	   assertTrue(goal.isSpecial() == false);
	   
   }

    @Test
    public void testgetReward(){
	   assertTrue(goal.getReward() > 0);
   }


	@Test 
	public void testgetStartDate(){
		assertTrue( goal.getStartDate() != null);
	}


    //Team EEP Tests for Goal:

    @Test
    public void testgetCurrentGoalDuration() {
        assertEquals("Goal should be at turn #0", goal.getCurrentGoalDuration(), 0);

        goal.incrementCurrentGoalDuration();

        assertEquals(" Goal should be at turn #1", goal.getCurrentGoalDuration(), 1);
    }


    @Test
    public void testCompleteGoal() {

        int startingReward = train.getOwner().getGold();
        int startingScore = train.getOwner().getPoints();

        int expectedEndingReward = startingReward + goal.getReward();
        int expectedEndingScore = 3 * goal.getReward() / 3; //I.e. as optimal route chosen, Score = Reward

        //Distance of route  to complete goal equals 274.0
        float distanceToCompleteGoal = train.getRoute().getLengthRemaining();

        assertFalse("Goal should not yet be complete", goal.goalComplete());  //At 0 so far

        train.route.update(train.getSpeed()); //Train is travelling at 80 per turn
        goal.incrementCurrentGoalDuration(); //Turn #1

        assertFalse("Goal should not yet be complete", goal.goalComplete()); //At 80 so far

        train.route.update(train.getSpeed()); //Train is travelling at 80 per turn
        goal.incrementCurrentGoalDuration(); //Turn #2

        assertFalse("Goal should not yet be complete", goal.goalComplete()); //At 160 so far

        train.route.update(train.getSpeed()); //Train is travelling at 80 per turn
        goal.incrementCurrentGoalDuration(); //Turn #3

        assertFalse("Goal should not yet be complete", goal.goalComplete()); //At 240 so far

        train.route.update(train.getSpeed()); //Train is travelling at 80 per turn
        goal.incrementCurrentGoalDuration(); //Turn #4

        //Train should have reached goal destination by now
        assertEquals("If goal is completed reward will have been added to gold", expectedEndingReward, train.getOwner().getGold());
        assertEquals("Score should have been added to player", expectedEndingScore, train.getOwner().getPoints());


    }
}
