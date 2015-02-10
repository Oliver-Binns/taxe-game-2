package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.Goal.Graph.GoalGenerationAlgorithm;
import com.TeamHEC.LocomotionCommotion.Goal.Graph.Node;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class GoalGenerationAlgorithmTest {
	WorldMap map = WorldMap.getInstance();
	private int pathLength;
	public static ArrayList<Station> stations;
	public Node[] nodeList;
	GoalGenerationAlgorithm gga;
	
	@Before
	public void setUp() throws Exception {
		this.pathLength = 5;
		stations = map.stationsList; 
		 gga = new GoalGenerationAlgorithm(pathLength);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGenerateGoalPath(){
		gga = new GoalGenerationAlgorithm(5);
		ArrayList<Node> nodeList = gga.getGoalPathNodeList();
		ArrayList<Station> stationList = new ArrayList<Station>();
		assertTrue(nodeList.size() == stationList.size());
		assertTrue(stationList.size() == 5);
	}
	
	@Test
	public void testGetGoalPathNodeList(){
		gga = new GoalGenerationAlgorithm(5);
		ArrayList<Node> list = gga.getGoalPathNodeList();
		assertTrue(list.size() == 5);
		assertTrue(contains(nodeList, list.get(0)));
		assertTrue(contains(nodeList, list.get(1)));
		assertTrue(contains(nodeList, list.get(2)));
		assertTrue(contains(nodeList, list.get(3)));
		assertTrue(contains(nodeList, list.get(4)));
		assertTrue(contains(nodeList, list.get(5)));
	}
	
	@Test
	public void testGoalGenerationAlgorithm(){
		assertTrue(gga.nodeList.length == 22);
		assertTrue(stations == map.stationsList );
	}
	
	@Test
	public void testGetStartingNode(){
		Node n = gga.getStartingNode();
		assertTrue(contains(nodeList, n));
	}
	
	@Test
	public void testInitialiseGraph() {
		gga.initialiseGraph();
		assertTrue(gga.nodeList.length != 0 );  
		assertTrue(gga.nodeList.length == (map.stationsList.size() + 2));
	}
	
	@Test
	public void testLookUpNode() {  
		Node n = gga.lookUpNode(map.BERLIN);
		assertTrue(n!=null); 
		assertTrue(contains(gga.nodeList, n));
	}
	
	
	
	public boolean contains(Node[] nodelist , Node node ) {

		for (int i = 0; i < nodelist.length;i++) {

			if (node == nodelist[i]) {	        	
				return true;
			}
		}
		return false;
	}
	

}
