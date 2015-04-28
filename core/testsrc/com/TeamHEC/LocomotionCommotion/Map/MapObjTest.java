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
		mapObjName = "Test Object";
		
		tester = new MapObj(x, y, mapObjName, false);
	}

	@Test
	public void testMapObj() {		
		assertTrue("mapObj Name did not initialise correctly", mapObjName == tester.getName());
		assertTrue("mapObj locked did not initialise correctly", tester.isLocked() == false);
	}
	
	@Test
	public void LockMapObjTest() {
		tester.lock(true);
		
		assertTrue("mapObj was not correctly locked", tester.isLocked() == true);
	}
	
	@Test
	public void UnlockMapObjTest() {
		tester.lock(false);
		
		assertTrue("mapObj was not correctly unlocked", tester.isLocked() == false);
	}
}
