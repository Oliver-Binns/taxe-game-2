/* In Initiazer error which is breaking all of the tests need to be fixed first before this

package com.TeamHEC.LocomotionCommotion.Map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class WorldMapTest {
	WorldMap tester;

	
	@Before
	public void setUp() {
		tester = new WorldMap();
	}
	
	@Test
	public void testWorldMap() {
		System.out.println(tester.getInstance()); //Tests if the World Map initializes correctly
	}

	@Test //Case 1 : No Default Map File Present //Tests method createDefaultMapFile
	public void testDefaultLoadedCorrectly() {
		//assertTrue("The Default map has not loaded correctly", tester.mapList.get(Map1) == );
	}
	
	@Test //Case 2 : 3 Default Map Files Present 
	public void testDefaultLoadedCorrectly() {
		assertTrue("The Default map has not loaded correctly", tester.mapList.get(Map1));
	}
	
	@Test //Case 2 : 3 Default Map Files Present, but no "Map1"
	public void testfail(){
		;
	}
}
*/
