package com.TeamHEC.LocomotionCommotion.Map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class StationTest {

	//Station
	Station tester;
	String stationName;
	int baseValue;
	int valueMod;
	Coal resourceType;
	int baseFuelOut;
	int resourceOutMod;
	Line[] line;
	int rentValue;
	int rentValueMod;
	float x;
	float y;
	boolean locked;
	
	@Before
	public void setUp() throws Exception {
		GameData.TEST_CASE = true;
		stationName = "Amsterdam";
		baseValue = 500;
		resourceType = new Coal(0);
		baseFuelOut = 500;
		line = new Line[] { Line.Red, Line.Blue, Line.Green };
		rentValue = 500;
		x = 0.5f;
		y = 0.5f;
		locked = false;
		
		tester = new Station(stationName, baseValue, resourceType, baseFuelOut, line, rentValue, x, y, false);
	}
	@Test
	public void testStation() {
		assertTrue("Station name was not correctly initialised", tester.getName() == stationName);
		assertTrue("Station owner was not correctly initialised", tester.getOwner() == null);
		assertTrue("Station baseValue was not correctly initialised", tester.getBaseValue() == baseValue);
		assertTrue("Station valueMod was not correctly initialised", tester.getValueMod() == 0);
		assertTrue("Station resourceType was not correctly initialised", tester.getResourceType() == resourceType);
		assertTrue("Station baseResourceOut was not correctly initialised", tester.getBaseResourceOut() == baseFuelOut);
		assertTrue("Station resourceOutMod was not correctly initialised", tester.getResourceOutMod() == 0);
		assertTrue("Station line was not correctly initialised", tester.getLineType() == line);
		assertTrue("Station rentValue was not correctly initialised", tester.getBaseRentValue() == rentValue);
		assertTrue("Station rentValueMod was not correctly initialised", tester.getRentValueMod() == 0);
		assertTrue("Station locked was not correctly initialised", tester.isLocked() == locked);
		//Actors can no longer be accessed through JUnit due to issues with it's interaction with Gdx.graphics;
		//assertTrue("Actor object was not set correctly", tester.getActor().actorX == x && tester.getActor().actorY == y);
	}
	
	@Test
	public void LockStationTest() {
		tester.lock(true);
		
		assertTrue("Station was not correctly locked", tester.isLocked() == true);
	}
	
	@Test
	public void UnlockStationTest() {
		tester.lock(false);
		
		assertTrue("Station was not correctly unlocked", tester.isLocked() == false);
	}
}
