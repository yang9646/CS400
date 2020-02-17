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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PackageManagerTest {

    public PackageManager testPack; // package manage variable

    /**
     * Initiate graph
     * 
     * @throws Exception - unexpected
     */
    @BeforeEach
    public void setUp() throws Exception {
        testPack = new PackageManager();
    }

    /**
     * Set graph null for future use
     * 
     * @throws Exception when it is thrown
     */
    @AfterEach
    public void tearDown() throws Exception {
        testPack = null;
    }

    /**
     * test whether constructing graph is working without exception
     */
    @Test
    public void test000_consturcGraph() {
        try {
            // construct graph and check whether exception raised
            testPack.constructGraph("shared_dependencies.json");
        } catch (Exception e) {
            fail("Exception happen");
        }
    }

    /**
     * This method test whether topological ordering works Topological order follow
     * CS400 convention
     */
    @Test
    public void test001_topoLogicalOrder() {
        try {
            // Construct graph
            testPack.constructGraph("shared_dependencies.json");

            List<String> allVertices = new ArrayList<String>();
            allVertices.addAll(testPack.getAllPackages());
            
            // Get topological ordering
            List<String> topo = testPack.getInstallationOrderForAllPackages();
            
            // Generate expected order this would work when vertices 
            // in alphabet or increasing order in JSON
            List<String> expected = new ArrayList<String>();
            expected.add("D");
            expected.add("B");
            expected.add("C");
            expected.add("A");
            
            assertEquals(topo, expected);
        } catch (Exception e) {
            fail("Exception happen");
        }
    }

    /**
     * Check whether getallPackage return all list of vertices
     */
    @Test
    public void test002_getAllPackages() {
        try {
            // Construct graph
            testPack.constructGraph("shared_dependencies.json");
            
            // get hash set
            Set<String> allPack = testPack.getAllPackages();
            
            // Make expect list
            Set<String> expected = new HashSet<String>();
            expected.add("A");
            expected.add("B");
            expected.add("C");
            expected.add("D");
            
            assertEquals(allPack, expected);
            
        } catch (Exception e) {
            fail("Exception happen");
        }
    }

    /**
     * Test whether installation order is correct
     */
    @Test
    public void test003_getInstallationOrder() {
        try {
            // construct graph
            testPack.constructGraph("shared_dependencies.json");
            
            // Get installation order
            List<String> installOrder = testPack.getInstallationOrder("A");
            List<String> topo = testPack.getInstallationOrderForAllPackages();
            
            // Installation Order of A should be equal to InstallationOder for all
            assertEquals(installOrder, topo);

        } catch (Exception e) {
            fail("Exception happen");
        }
    }

    /**
     * Check whether toInstall is working properly
     */
    @Test
    public void test004_toInstall() {
        try {
            // Construct graph
            testPack.constructGraph("shared_dependencies.json");
            
            // Get toInstall list
            List<String> toInstall = testPack.toInstall("A", "B");
            List<String> expected = new ArrayList<String>();
            expected.add("C");
            expected.add("A");
            
            assertEquals(toInstall, expected);
            
        } catch (Exception e) {
            fail("Unexpected Exception thrown");
        }
    }

    /**
     * test Whether is return Vertex with max number of dependencies
     */
    @Test
    public void test005_getPackageWithMaxDep() {
        try {
            // Construct graph
            testPack.constructGraph("valid.json");
            // Get max vertex
            String maxDepVertex = testPack.getPackageWithMaxDependencies();
            
            assertEquals(maxDepVertex, "A");
            
        } catch (Exception e) {
            fail("Exception happen");
        }
    }
}
