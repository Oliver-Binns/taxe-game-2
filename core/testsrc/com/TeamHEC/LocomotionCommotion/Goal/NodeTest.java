package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Goal.Graph.Node;
import com.TeamHEC.LocomotionCommotion.Map.MapInstance;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class NodeTest {
	MapInstance wm;
	Station yo;
	Node n;
	
	   
	@Before
	public void setUp() throws Exception {
		GameData.TEST_CASE = true;
		wm = WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP);
		yo = wm.stationList()[0];
		n = new Node(yo);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testNode() {
		assertTrue(n.mapobj == wm.stationList()[0]);
		assertTrue(n.edges.size() == 0);
		assertTrue(n.minDistance == Double.POSITIVE_INFINITY);
		assertTrue(n.next == null);
	}
}
