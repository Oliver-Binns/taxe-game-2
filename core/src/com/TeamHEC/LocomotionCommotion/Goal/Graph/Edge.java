package com.TeamHEC.LocomotionCommotion.Goal.Graph;

public class Edge
{
    public final Node target;
    public final float weight;
    /**
     * 
     * @param tget refers to the target of the edge. The source of the edge being the node that created a new edge
     * @param weight refers to the length of the vector between the two mapobjs as computed inside the connection class.
     */
    public Edge(Node tget, float weight)    //given a target node and a weight we have an edge. 
    
    { 
     
    this.target = tget;
    this.weight = weight;
    
    }
}