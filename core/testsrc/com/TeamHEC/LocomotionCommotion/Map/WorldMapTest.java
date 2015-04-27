package com.TeamHEC.LocomotionCommotion.Map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;
import com.TeamHEC.LocomotionCommotion.Resource.Coal;
import com.TeamHEC.LocomotionCommotion.Resource.Resource;


@RunWith(GdxTestRunner.class)
public class WorldMapTest {
	MapInstance testMap;
	Station testStation;
	Junction testJunction;
	Connection testConnection;
	
	@Before
	public void setUp() throws Exception {
		GameData.TEST_CASE = true;
		testMap = WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP);
		
		String sName = "Test Station";
		int baseValue = 500;
		Resource resource = new Coal(50);
		Line[] lines = {Line.Black};
		int baseFuelOut = 100;
		boolean locked = false;
		testStation = new Station(sName, baseValue, resource, baseFuelOut, lines, baseFuelOut, 100.0f, 100.0f, locked);
		
		String jName = "Test Junction";
		testJunction = new Junction(150.0f, 150.0f, jName, locked);
		
		testConnection = new Connection(testStation, testJunction, Line.Black);
	}
	
	@Test
	public void AddStationTest() {
		testMap.addMapObj(testStation);
		
		assertTrue("Station has not been added to map", testMap.getStationWithName(testStation.getName()) == testStation);
	}
	
	@Test
	public void AddJunctionTest() {
		testMap.addMapObj(testJunction);
		
		assertTrue("Junction has not been added to map", testMap.getJunctionWithName(testJunction.getName()) == testJunction);		
	}
	
	@Test
	public void AddConnectionTest() {
		boolean containsConnection = false;
		
		testMap.addMapObj(testStation);
		testMap.addMapObj(testJunction);
		testMap.addConnection(testConnection);
		
		for(int i=0; i<testMap.connectionList().length; i++) {
			Connection c = testMap.connectionList()[i];
			
			if(c == testConnection) {
				containsConnection = true;
			}
		}
		
		assertTrue("Connection has not been added to map", containsConnection);
		
		testMap.removeStation(testStation.getName());
		testMap.removeJunction(testJunction.getName());
	}
	
	@Test
	public void RemoveStationTest() {
		testMap.addMapObj(testStation);
		testMap.removeStation(testStation.getName());
		
		assertTrue("Station was not removed from map", testMap.getStationWithName(testStation.getName()) != testStation);
	}
	
	@Test
	public void RemoveJunctionTest() {
		testMap.addMapObj(testJunction);
		testMap.removeJunction(testJunction.getName());
		
		assertTrue("Junction was not removed from map", testMap.getJunctionWithName(testJunction.getName()) != testJunction);
	}
	
	@Test
	public void RemoveConnectionTest() {
		boolean containsConnection = false;
		
		if(testMap.getStationWithName(testStation.getName()) == null) {
			testMap.addMapObj(testStation);
		}
		
		if(testMap.getJunctionWithName(testJunction.getName()) == null) {
			testMap.addMapObj(testJunction);
		}
		
		testMap.addConnection(testConnection);
		testMap.removeConnection(testStation, testJunction);
		
		for(int i=0; i<testMap.connectionList().length; i++) {
			Connection c = testMap.connectionList()[i];
			
			if(c == testConnection) {
				containsConnection = true;
			}
		}
		
		assertFalse("Connection was not removed from map", containsConnection);
	}
}
