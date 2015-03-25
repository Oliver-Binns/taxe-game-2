package com.TeamHEC.LocomotionCommotion.Goal.Graph;

import java.util.ArrayList;

import com.TeamHEC.LocomotionCommotion.Map.MapObj;
public class Node implements Comparable<Node>
{
  /**
   * @param mapobj refers to the instance of the station attached to this node
   * @param edges the connections to a station are stored in array list of edges
   * @param minDistance the tentative distance, set to infinity all the time according to dijkstras 
   * @param next refers to the next node. 
   */
public final MapObj mapobj;
public ArrayList<Edge> edges; 
public double minDistance = Double.POSITIVE_INFINITY;
public Node next;   

 /**
  * creates a new node for a given instance of a station/junction
  * @param st
  */
 public Node(MapObj st) 
    {
     this.mapobj = st;  //sets node to the station/junction passed
     this.edges = new ArrayList<Edge>(); 
    } 
   
 	
    /**neccassary to compare in the compute paths class 
     * 
     */
   public int compareTo(Node other)
    {
        return Double.compare(this.minDistance, other.minDistance); //compares this distance to anotdher node
    }
 


	
  
 
 
}
