package com.TeamHEC.LocomotionCommotion.Goal.Graph;

import java.util.ArrayList;
import java.util.Random;

import com.TeamHEC.LocomotionCommotion.Map.Connection;
import com.TeamHEC.LocomotionCommotion.Map.MapObj;
import com.TeamHEC.LocomotionCommotion.Map.Station;
import com.TeamHEC.LocomotionCommotion.Map.WorldMap;
import com.TeamHEC.LocomotionCommotion.Player.Player;
import com.TeamHEC.LocomotionCommotion.Train.Train;


/**
 * @author Stefan Kokov
 * GoalGenerator class is used to generate new goals. It creates a graph of the stations in the game,
 * traverses it and finds a goal which is achieveable according to a players resources and trains 
 *
 */
public class GoalGenerationAlgorithm {
	private WorldMap map;
	private Train train;
	private int costSoFar;
	private int costLimit;
	private ArrayList<Station> goalPath = new ArrayList<Station>();
	public static ArrayList<Station> stations;
	public Node[] nodeList;
	
	
	public GoalGenerationAlgorithm(Player player, int costLimit) {
			//Get random train from a players trains, which will be the train which is 100% able to achieve a goal
			Random rand = new Random();
			int n = rand.nextInt(player.getTrains().size());
			train = player.getTrains().get(n);
			
			//intialize cost so far, cost limit
			costSoFar = 0;
			this.costLimit = costLimit;
			
			//intialize world map graph
			map = WorldMap.getInstance();
			stations = map.stationsList;  
			initialiseGraph();
	}
	
	/**This method returns the starting station of the goal being generated
	 * @return startingNode
	 */
	private Node getStartingNode(){
		Node startNode;
		if(train.getRoute().getStation() == null){
			Connection connection = train.getRoute().getRoute().get(train.getRoute().getRoute().size());
			startNode = lookUpNode(connection.getDestination());
		}else{
			startNode = lookUpNode(train.getRoute().getStation());
		}
		return startNode;
	}
	
	private ArrayList<Node> getGoalPathNodes(){
		//initialize arraylist of nodes through which one of the paths through the rout will be
		ArrayList<Node> goalPath = new ArrayList<Node>();
		
		//Initialize list to keep track of nodes that where already considered
		ArrayList<Node> visitedNodes = new ArrayList<Node>();
		
		//Add starting node to the path
		goalPath.add(getStartingNode());
		
		
		
		while(costSoFar < costLimit){
			Node currentNode = goalPath.get(goalPath.size());
			ArrayList<Edge> edges = currentNode.edges;
			
			if(getNextNode(edges) == null){
				break;
			}else{
				
			}
		}
	}
	
	
	
	/**returns a random target node to one of the edges in edges. The cost to reaching the node via the edge
	 * combined with the cost of the path so far should not exceeed the cost limit of this class
	 * @param edges list of edges from which a random edge is chosen
	 * @return the target node of the randomly chosen edge
	 */
	private Node getNextNode(ArrayList<Edge> edges){
		Random rand = new Random();
		int nextNodeIndex = rand.nextInt(edges.size());
		
		while(costSoFar + edges.get(rand.nextInt(edges.size())).weight > costLimit 
				&& !edges.isEmpty()){
			
			edges.remove(nextNodeIndex);
			nextNodeIndex = rand.nextInt(edges.size());
		}
		
		
		Node nextNode = null;
		if(!edges.isEmpty()){
			nextNode = edges.get(nextNodeIndex).target;
		}
		
		return nextNode;
	}
	
	/**
	 * Everytime GoalGenerator is called a graph is created specifically for that instance. This function populates nodeList 
	 * by creating as many null nodes as there are stations/junctions it then steps through this list of null nodes and 
	 * populates it with a station from the worldmap.stationList. Once done it then adds edges to each newly created 
	 * node in correspondence to the connections of that station. 
	 */
	public void initialiseGraph()
	{
		ArrayList<MapObj> fullList = new ArrayList<MapObj>();
		fullList.addAll(stations);
		fullList.add(WorldMap.getInstance().junction[0]);
		fullList.add(WorldMap.getInstance().junction[1]);
		
		nodeList = new Node[map.stationsList.size() + map.junction.length];
	     
		for(int i = 0; i < fullList.size(); i++){         //populates empty nodes with a new station
			nodeList[i] = new Node(fullList.get(i));  //stepping through each node in array and assigning station to it.      
		}      

		for(Node n : nodeList){
			for(Connection c : n.mapobj.connections){                            //adds each connection to each node
				n.edges.add(new Edge(lookUpNode(c.getDestination()),(c.getLength()))); } } 

	}
	
	/**
	 * a lookup table that returns a Node instance for a given map obj, it should be noted that nodes are just extended 
	 * mapobj's. In theory, all stations/junctions will be inside nodeList 
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

}
