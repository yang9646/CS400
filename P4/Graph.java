//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Project name: Package Manager
// Files: Graph.java, GraphTest.java, PackageManager.java, PackageManagerTest.java
// Course: CS 400 Fall 2019
//
// Author: Ye Ji Kim
// Email: ykim762@wisc.edu
// Lecture number: 001
// Lecturer's Name: Debra Deppeler
//
//////////////////////////// 80 columns wide ///////////////////////////////////

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Filename: Graph.java Project: p4 Authors: Ye Ji Kim
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {

    private ArrayList<String> vertexList;
    private boolean[][] am; // adjacency matrix
    private int numEdges;
    private int numVertices;

    /*
     * Default no-argument constructor
     */
    public Graph() {
        vertexList = new ArrayList<String>();
        am = new boolean[20][20]; // initial with size 20
        numEdges = 0;
        numVertices = 0;
    }

    public Graph(int numVert) {
        am = new boolean[numVert][numVert];
        numEdges = 0;
        numVertices = 0;
    }

    /**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists, method ends without adding a vertex or
     * throwing an exception.
     * 
     * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in
     * the graph
     */
    public void addVertex(String vertex) {
        if (vertex != null && !getAllVertices().contains(vertex)) { // valid
            // resize am when it is full
            if (vertexList.size() == am.length) {
                boolean[][] newAM = new boolean[am.length * 2][am.length * 2];
                // copy the adjacency matrix
                for (int i = 0; i < am.length; i++) {
                    for (int j = 0; j < am.length; j++) {
                        newAM[i][j] = am[i][j];
                    }
                }
                am = newAM;
            }
            vertexList.add(vertex); // add vertex to the list
            numVertices++; // increment
        }
        return;
    }

    /**
     * Remove a vertex and all associated edges from the graph.
     * 
     * If vertex is null or does not exist, method ends without removing a vertex,
     * edges, or throwing an exception.
     * 
     * Valid argument conditions: 1. vertex is non-null 2. vertex is already in the
     * graph
     */
    public void removeVertex(String vertex) {
        if (vertex != null && getAllVertices().contains(vertex)) {

            int index = vertexList.indexOf(vertex);

            for (int i = index; i < vertexList.size() - 1; i++) {
                am[i] = am[i + 1]; // shift rows
            }
            
            // remove the edges
            for(int i=0; i<vertexList.size(); i++) {
                removeEdge(vertex, vertexList.get(i));
            }
            for(int i=0; i<vertexList.size(); i++) {
                removeEdge(vertexList.get(i), vertex);
            }
            
            vertexList.remove(vertex); // remove it from the list
            
            numVertices--; // decrement
        }
    }

    /**
     * Add the edge from vertex1 to vertex2 to this graph. (edge is directed and
     * unweighted) If either vertex does not exist, add vertex, and add edge, no
     * exception is thrown. If the edge exists in the graph, no edge is added and no
     * exception is thrown.
     * 
     * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
     * the graph 3. the edge is not in the graph
     */
    public void addEdge(String vertex1, String vertex2) {
        // neither vertes is null
        if (vertex1 == null || vertex2 == null)
            return;

        // if vertex not exist, add
        if (!vertexList.contains(vertex1))
            addVertex(vertex1);
        if (!vertexList.contains(vertex2))
            addVertex(vertex2);

        // if the edge exists
        if (am[vertexList.indexOf(vertex1)][vertexList.indexOf(vertex2)]) {
            return;
        } else { // edge does not exist - add
            am[vertexList.indexOf(vertex1)][vertexList.indexOf(vertex2)] = true;
            numEdges++; // increment
        }
    }

    /**
     * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed
     * and unweighted) If either vertex does not exist, or if an edge from vertex1
     * to vertex2 does not exist, no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
     * the graph 3. the edge from vertex1 to vertex2 is in the graph
     */
    public void removeEdge(String vertex1, String vertex2) {
        // neither vertex is null
        if (vertex1 == null || vertex2 == null)
            return;

        // one of vertices are not in the graph
        if (!vertexList.contains(vertex1) || !vertexList.contains(vertex2))
            return;

        // the edge from vertex1 to vertex1 not exist
        if (!am[vertexList.indexOf(vertex1)][vertexList.indexOf(vertex2)]) {
            return;
        } else { // edge exists - remove
            // update the adjacency matrix
            am[vertexList.indexOf(vertex1)][vertexList.indexOf(vertex2)] = false;
            numEdges--; // decrement
        }
    }

    /**
     * Returns a Set that contains all the vertices
     */
    public Set<String> getAllVertices() {
        Set<String> output = new HashSet<String>(); // Set to return
        // add each vertex
        for (int i = 0; i < vertexList.size(); i++) {
            output.add(vertexList.get(i));
        }
        return output;
    }

    /**
     * Get all the neighbor (adjacent) vertices of a vertex
     */
    public List<String> getAdjacentVerticesOf(String vertex) {
        List<String> adjacency = null; // list to return
        // if the vertex is valid
        if (vertex != null && getAllVertices().contains(vertex)) {
            adjacency = new ArrayList<String>();
            
            int index = vertexList.indexOf(vertex);

            // for each vertex
            for (int i = 0; i < vertexList.size(); i++) {
                // check the adjacency list
                if (am[index][i]) { //true
                    adjacency.add(vertexList.get(i)); // add
                }
            }
        }
        return adjacency;
    }

    /**
     * Returns the number of edges in this graph.
     */
    public int size() {
        return this.numEdges;
    }

    /**
     * Returns the number of vertices in this graph.
     */
    public int order() {
        return this.numVertices;
    }
}
