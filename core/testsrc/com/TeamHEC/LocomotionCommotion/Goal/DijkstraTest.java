

package com.TeamHEC.LocomotionCommotion.Goal;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Goal.Graph.Dijkstra;
import com.TeamHEC.LocomotionCommotion.Goal.Graph.Node;
import com.TeamHEC.LocomotionCommotion.Map.MapInstance;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Mocking.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class DijkstraTest {
	
	MapInstance wm;
	Station sstation;
	Station fstation;
	Dijkstra d = new Dijkstra();
	double rew;

	@Before
	public void setUp() throws Exception {
		GameData.TEST_CASE = true;
		wm = WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP);
		sstation = wm.stationList()[0];
		fstation = wm.stationList()[1];
		
		d.initialiseGraph();  

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testDijkstra() {
		
		assertTrue(d.nodeList.length == WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP).mapObjList().length);
		assertTrue(Dijkstra.stations.equals(Arrays.asList(wm.stationList())));
		
	}

	@Test
	public void testComputePaths() {
		d.computePaths(d.lookUpNode(this.sstation));
		rew = d.lookUpNode(fstation).minDistance; 
		assertTrue(rew < 2000);//less than maximum goal, not sure what that is mind. 
		assertTrue(!(rew==0));
		assertTrue(rew > 0);



		//Just check that the value is > 0 and is not infinite.
		//You also need to add error messages like I have in my tests. It helps with debugging.
	}

	
	@Test
	public void testLookUpNode() {  
		Node n = d.lookUpNode(fstation);
		//[1] would be nice to have a test that asserts it is of type Node
		assertTrue(n!=null); 
		assertTrue(contains(d.nodeList, n));

	}
	public boolean contains(Node[] nodelist , Node node ) {

		for (int i = 0; i < nodelist.length;i++) {

			if (node == nodelist[i]) {	        	
				return true;
			}
		}
		return false;
	}

	//Again, you don't need to test it's type. 
	//You should test that lookUpNode works by making sure it returns a value you expect.
	//And add error messages to all of your assertions.

	@Test
	public void testInitialiseGraph() {
		d.initialiseGraph();
		assertTrue(d.nodeList.length != 0 );  
		assertTrue(d.nodeList.length == (wm.stationList().length + wm.junctionList().length));
	}


}
