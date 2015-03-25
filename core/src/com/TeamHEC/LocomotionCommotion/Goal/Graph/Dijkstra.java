package com.TeamHEC.LocomotionCommotion.Goal.Graph;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.Junction;
import com.TeamHEC.LocomotionCommotion.Map.MapInstance;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
/**
 * 
 * @author Sam Watkins <sw1308@york.ac.uk>
 * @author sa902
 *
 */
public class Dijkstra {
	private MapInstance map;
	public static ArrayList<Station> stations;
	public static ArrayList<Junction> junctions;
	public Node[] nodeList;

	public Dijkstra(){
		map = WorldMap.getInstance().mapList.get(GameData.CURRENT_MAP);
		stations = new ArrayList<Station>(Arrays.asList(map.stationList()));
		junctions = new ArrayList<Junction>(Arrays.asList(map.junctionList()));
		
		initialiseGraph();
	}
/**
 * Compute paths takes a source node and updates the Tentative distance of all other nodes
 * with the an integer value that represents the length between the source node and that
 * particular nodes.
 * @param source: the source node
 *  
 */
	public void computePaths(Node source){
		source.minDistance = 0;
		PriorityQueue<Node> NodeQueue = new PriorityQueue<Node>();
		NodeQueue.add(source);

		while (!NodeQueue.isEmpty()) {
			Node currentnode = NodeQueue.poll();

			// Visit each edge exiting 
			for (Edge e : currentnode.edges)
			{
				Node nextnode = e.target;
				double weight = e.weight;
				double distanceThroughU = currentnode.minDistance + weight;
				if (distanceThroughU < nextnode.minDistance) {
					NodeQueue.remove(nextnode);
					nextnode.minDistance = distanceThroughU ;
					nextnode.next = currentnode;
					NodeQueue.add(nextnode);
				}
			}
		}
	}
/**
 * getShortestPathTo is a function that takes in a target node and gets the shortest path to that node to another node, then returns the path in a list of nodes. 
 * @param target 
 * @return List<Node>
 */
	public List<Node> getShortestPathTo(Node target)
	{
		List<Node> path = new ArrayList<Node>();
		for (Node Node = target; Node != null; Node = Node.next)
			path.add(Node);
		Collections.reverse(path);
		return path;
	}
/**
 * a lookup table that returns a Node instance for a given map obj, it should be noted that nodes are just extended mapobj's. In theory, all stations/junctions will be inside nodeList
 * so this should not return an illegal argument exception. 
 * @param mapObj
 * @return Node
 */
	public Node lookUpNode(MapObj mapObj)
	{
		for (Node n : nodeList){
			if (mapObj.getName() == n.mapobj.getName())
				return n;		
		}
		
		throw new IllegalArgumentException("The given mapObj does not exist in the nodeList");
	}
/**
 * everytime Dijkstra is called a graph is created specifically for that instance. This function populates nodeList by creating as many null nodes as there are stations/junctions
 * it then steps through this list of null nodes and populates it with a station from the worldmap.stationList. Once done it then adds edges to each newly created node in correspondence 
 * to the connections of that station. 
 */
	public void initialiseGraph()
	{
		ArrayList<MapObj> fullList = new ArrayList<MapObj>();
		fullList.addAll(stations);
		fullList.addAll(junctions);
		
		nodeList = new Node[map.stationList().length + map.junctionList().length];
	     
		for(int i = 0; i < fullList.size(); i++){         //populates empty nodes with a new station
			nodeList[i] = new Node(fullList.get(i));  //stepping through each node in array and assigning station to it.      
		}      

		for(Node n : nodeList){
			for(Connection c : n.mapobj.connections){                            //adds each connection to each node
				n.edges.add(new Edge(lookUpNode(c.getDestination()),(c.getLength()))); } } 



	}
}

