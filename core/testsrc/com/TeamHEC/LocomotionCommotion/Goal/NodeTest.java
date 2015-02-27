package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Goal.Graph.Node;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class NodeTest {
	WorldMap wm;
	Station yo;
	Node n;
	
	   
	@Before
	public void setUp() throws Exception {
		wm = WorldMap.getInstance();
		yo = wm.AMSTERDAM;
		n = new Node(yo);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testNode() {
		assertTrue(n.mapobj == wm.AMSTERDAM);
		assertTrue(n.edges.size() == 0);
		assertTrue(n.minDistance == Double.POSITIVE_INFINITY);
		assertTrue(n.next == null);
	}
}
