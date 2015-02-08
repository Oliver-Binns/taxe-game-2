package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
	@Before
	public void setUp() throws Exception {
		wm = WorldMap.getInstance();
		GoalFactory gf = new GoalFactory(1);
		goal = gf.CreateRandomGoal();
		
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
		
		
		train = new OilTrain(0, true, new Route(WorldMap.getInstance().AMSTERDAM), player);

		goal.assignTrain(train);
		
	}

	@Test
	public void testGoal() {
		assertTrue(compareStations(goal.getSStation()));
		assertTrue(compareStations(goal.getFStation()));
//		assertTrue(goal.stationPassed(ss, train);
		
	}
 public boolean compareStations(String Sname){
	 for (int i = 0; i < wm.stationsList.size(); i++){
		 if (Sname == wm.stationsList.get(i).getName()){
			 return true;
		 }
 	 }
	 return false;
	 
 }
	@Test
	public void testAssignTrain() {
		assertTrue("", goal.getTrain() == train);
	}
	
	@Test
   public void testisSpecial(){
	   assertTrue(goal.isSpecial() == false);
	   
   }
	
   public void testgetReward(){
	   assertTrue(goal.getReward() > 0);
	  
   }
	@Test 
	public void testgetStartDate(){
		assertTrue( goal.getStartDate() != null);
	}

    @Test
    public void testgetCurrentGoalDuration() {
        assertTrue( goal.getCurrentGoalDuration() == 0); //Goal should be at turn 0

        goal.incrementCurrentGoalDuration();

        assertTrue( goal.getCurrentGoalDuration() == 1); //Goal should not be at turn 1

    }
}
