package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Goal.GoalFactory;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class GoalFactoryTest {
	GoalFactory tester;
	@Before
	public void setUp() throws Exception {
		tester = new GoalFactory(1);
	}

	@Test
	public void testCreateRandomGoal() {
		
		for(int i = 0; i < 500; i ++)
		{
			Goal goal = tester.CreateRandomGoal();
			
			assertTrue(
					"GoalFactory's goal did not have a valid start station, iteration: " + i,
					checkExistence(goal.getSStation()));
			assertTrue(
					"GoalFactory's goal did not have a valid end station, iteration: " + i,
					checkExistence(goal.getFStation()));
			assertTrue(
					"GoalFactory's goal did not have a valid cargo, iteration: " + i,
					goal.getCargo() == "Passenger" || goal.getCargo() == "Cargo");
			assertTrue(
					"GoalFactory's reward was not set correctly, iteration: " + i,
					goal.getReward() > 0);
		}
	}
	
	@Test
	public void testGenerateAboluteGoal() {
		
		for(int i = 0; i < 500; i ++)
		{
			tester = new GoalFactory(1);
			Goal goal = tester.generateAbsoluteGoal(5);
			Goal goal1 = tester.generateAbsoluteGoal(5);
			
			assertTrue(
					"GoalFactory's goal did not have a valid start station, iteration: " + i,
					checkExistence(goal.getSStation()));
			assertTrue(
					"GoalFactory's goals had the same SSstations, iteration: " + i,
					goal.getSStation() != goal1.getSStation());
			assertTrue(
					"GoalFactory's goals had the same FStation and SStaion, iteration: " + i,
					goal.getFStation() != goal1.getSStation());
			assertTrue(
					"GoalFactory's goals had the same SStation and FStation, iteration: " + i,
					goal.getSStation() != goal1.getFStation());
			assertTrue(
					"GoalFactory's goals had the same FStations, iteration: " + i,
					goal.getFStation() != goal1.getFStation());
			assertTrue(
					"GoalFactory's goal did not have a valid end station, iteration: " + i,
					checkExistence(goal.getFStation()));
			assertTrue(
					"GoalFactory's goal did not have a valid cargo, iteration: " + i,
					goal.getCargo() == "Absolute");
			assertTrue(
					"GoalFactory's reward was not set correctly, iteration: " + i,
					goal.getReward() > 0);
			assertTrue(
					"GoalFactory did not have a valid time constraint, iteration: " + i,
					goal.getTimeConstraint() == 0);
		}
	}
	
	@Test
	public void testGenerateQuantifiableGoal() {
		
		for(int i = 0; i < 500; i ++)
		{
			tester = new GoalFactory(1);
			Goal goal = tester.generateQuantifiableGoal(5);
			Goal goal1 = tester.generateQuantifiableGoal(5);
			
			assertTrue(
					"GoalFactory's goal did not have a valid start station, iteration: " + i,
					checkExistence(goal.getSStation()));
			assertTrue(
					"GoalFactory's goals had the same SSstations, iteration: " + i,
					goal.getSStation() != goal1.getSStation());
			assertTrue(
					"GoalFactory's goals had the same FStation and SStaion, iteration: " + i,
					goal.getFStation() != goal1.getSStation());
			assertTrue(
					"GoalFactory's goals had the same SStation and FStation, iteration: " + i,
					goal.getSStation() != goal1.getFStation());
			assertTrue(
					"GoalFactory's goals had the same FStations, iteration: " + i,
					goal.getFStation() != goal1.getFStation());
			assertTrue(
					"GoalFactory's goal did not have a valid end station, iteration: " + i,
					checkExistence(goal.getFStation()));
			assertTrue(
					"GoalFactory's goal did not have a valid cargo, iteration: " + i,
					goal.getCargo() == "Quantifiable");
			assertTrue(
					"GoalFactory's reward was not set correctly, iteration: " + i,
					goal.getReward() > 0);
			assertTrue(
					"GoalFactory did not have a valid time constraint, iteration: " + i,
					goal.getTimeConstraint() > 0);
		}
	}
	
	private boolean checkExistence(String stationName) {
		for(int i = 0; i < WorldMap.getInstance().stationsList.size(); i++)
		{
			if(WorldMap.getInstance().stationsList.get(i).getName() == stationName)
				return true;
		}
		
		return false;
	}
}
