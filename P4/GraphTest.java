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

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test HashTable class implementation to ensure that required functionality
 * works for all cases.
 */
public class GraphTest {

    protected Graph testGraph;

    /**
     * Initiate graph
     * @throws Exception - unexpected
     */
    @BeforeEach
    public void setUp() throws Exception {
        testGraph = new Graph();
    }

    
    /**
     * Set graph null for future use
     * 
     * @throws Exception when it is thrown
     */
    @AfterEach
    public void tearDown() throws Exception {
        testGraph = null;
    }
    
    /**
     * Test if the graph is empty in the beginning
     */
    @Test
    public void test001_IsEmpty() {
        try {
            if(testGraph.size() != 0) {
                fail("size is not correct");
            }
            assertEquals(0, testGraph.size());
        } catch(Exception e) {
            fail("Unexpected Exception is thrown");
        }
    }

    /**
     * Test if it is possible to add duplicate vertices
     * no exceptions are expected
     */
    @Test
    public void test002_add_duplicated_vertices() {
        try {
            // Try to add duplicate
            testGraph.addVertex("Same");
            testGraph.addVertex("Same");
            // When two vertices there
            if (testGraph.order() != 1) {
                // Fail
                fail("not one");
            }
        } catch(Exception e) {
            fail("Unexpected Exception is thrown");
        }
    }

    /**
     * Test the number of edges is expected by adding edges
     */
    @Test
    public void test003_AddEdge() {
        try {
            // Add 4 vertices
            testGraph.addVertex("A");
            testGraph.addVertex("B");
            testGraph.addVertex("C");
            testGraph.addVertex("D");
            // Add 3 edges
            testGraph.addEdge("A", "C");
            testGraph.addEdge("A", "B");
            testGraph.addEdge("A", "D");
            // Check whether there is three edges
            if (testGraph.size() != 3) {
                fail("not one");
            }
        } catch(Exception e) {
            fail("Unexpected Exception is thrown");
        }
    }

    /**
     * Check the graph allow to have cycle
     */
    @Test
    public void test003_add_edges_with_cycle() {
        try {
            // Add vertices and edges with cycle
            testGraph.addVertex("A");
            testGraph.addVertex("B");
            testGraph.addVertex("C");
            testGraph.addVertex("D");
            testGraph.addEdge("A", "B");
            testGraph.addEdge("B", "C");
            testGraph.addEdge("C", "A");
            // Check whether it works
            if (testGraph.size() != 3) {
                fail("not three");
            }
        } catch (Exception e) {
            fail("Unexpected Exception is thrown");
        }
    }

    /**
     * Check remove edge work correctly
     */
    @Test
    public void test004_add_edges_and_remove() {
        try {
            // Add 3 vertices and 3 edges
            testGraph.addVertex("A");
            testGraph.addVertex("B");
            testGraph.addVertex("C");
            testGraph.addEdge("A", "B");
            testGraph.addEdge("B", "C");
            testGraph.addEdge("C", "A");
            // Remove one edge
            testGraph.removeEdge("A", "B");
            // Check the edge number
            if (testGraph.size() != 2) {
                fail("Not two");
            }
        } catch (Exception e) {
            fail("Unexpected Exception is thrown");
        }
    }

    /**
     * Check remove vertices work correctly
     */
    @Test
    public void test005_remove_vertex() {
        try {
            // Add 3 vertices and 3 edges
            testGraph.addVertex("A");
            testGraph.addVertex("B");
            testGraph.addVertex("C");
            testGraph.addEdge("A", "B");
            testGraph.addEdge("B", "C");
            testGraph.addEdge("C", "A");
            // Remove a vertices
            testGraph.removeVertex("A");
            // Check edge number and vertices number
            if (testGraph.order() != 2) {
                fail("Not two");
            }
            if (testGraph.size() != 1) {
                fail("Not one");
            }
        } catch (Exception e) {
            fail("Unexpected Exception is thrown");
        }
    }

    /**
     * check get adjacent vertices working correctly
     */
    @Test
    public void test006_get_adjacent_vertices() {
        try {
            // Add three vertices and three edges
            testGraph.addVertex("A");
            testGraph.addVertex("B");
            testGraph.addVertex("C");
            testGraph.addEdge("A", "B");
            testGraph.addEdge("B", "C");
            testGraph.addEdge("C", "A");
            // Get adj list
            ArrayList<String> adj = (ArrayList<String>) testGraph.getAdjacentVerticesOf("A");
            // Check list component is correct
            if (adj.get(0).compareTo("B") != 0) {
                fail("Not work");
            }
        } catch (Exception e) {
            fail("Unexpected Exception is thrown");
        }
    }

}
