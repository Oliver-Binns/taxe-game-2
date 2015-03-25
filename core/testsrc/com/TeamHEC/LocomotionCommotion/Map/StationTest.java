package com.TeamHEC.LocomotionCommotion.Map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
	
	@Before
	public void setUp() throws Exception {
		stationName = "Amsterdam";
		baseValue = 500;
		resourceType = new Coal(0);
		baseFuelOut = 500;
		line = new Line[] { Line.Red, Line.Blue, Line.Green };
		rentValue = 500;
		x = 0.5f;
		y = 0.5f;
		
		tester = new Station(stationName, baseValue, resourceType, baseFuelOut, line, rentValue, x, y);
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
		assertTrue("Actor object was not set correctly", tester.getActor().actorX == x && tester.getActor().actorY == y);
	}

}
