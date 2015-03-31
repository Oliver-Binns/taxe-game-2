package com.TeamHEC.LocomotionCommotion.Map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class MapObjTest {
	float x;
	float y;
	String mapObjName;
	MapObj tester;
	
	@Before
	public void setUp() throws Exception {
		x = 0.5f;
		y = 1.0f;
		mapObjName = "FooLand";
		
		tester = new MapObj(x, y, mapObjName);
	}

	@Test
	public void testMapObj() {		
		assertTrue("mapObj Name did not initialise correctly", mapObjName == tester.getName());
		assertTrue("Station did not initialise correctly", tester.getStation() == null);
	}
	
	@Test 
	public void testtoggleLocked() {
		boolean temp = tester.isLocked(); //returns current state of Lock attribute of MapObj
		tester.toggleLocked();
		assertTrue("toggleLocked method did not work", tester.isLocked() != temp);
	}
}
