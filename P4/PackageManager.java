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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Filename: PackageManager.java Project: p4 Authors:Ye Ji Kim
 * 
 * PackageManager is used to process json package dependency files and provide
 * function that make that information available to other users.
 * 
 * Each package that depends upon other packages has its own entry in the json
 * file.
 * 
 * Package dependencies are important when building software, as you must
 * install packages in an order such that each package is installed after all of
 * the packages that it depends on have been installed.
 * 
 * For example: package A depends upon package B, then package B must be
 * installed before package A.
 * 
 * This program will read package information and provide information about the
 * packages that must be installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with our own Test
 * classes.
 */

public class PackageManager {

    private Graph graph; // graph variable

    /**
     * Package Manager default no-argument constructor.
     */
    public PackageManager() {
        this.graph = new Graph(); // initialize the graph
    }

    /**
     * Takes in a file path for a json file and builds the package dependency graph
     * from it.
     * 
     * @param jsonFilepath the name of json data file with package dependency
     *                     information
     * @throws FileNotFoundException if file path is incorrect
     * @throws IOException           if the give file cannot be read
     * @throws ParseException        if the given json cannot be parsed
     */
    public void constructGraph(String jsonFilepath)
            throws FileNotFoundException, IOException, ParseException {
        JSONParser jsonparser = new JSONParser();
        JSONObject obj = (JSONObject) jsonparser.parse(new FileReader(jsonFilepath));
        JSONArray packages = (JSONArray) obj.get("packages");

        for (int i = 0; i < packages.size(); i++) { // for each json object
            // get name and dependencies
            JSONObject pack = (JSONObject) packages.get(i);
            String name = (String) pack.get("name");
            JSONArray dependencies = (JSONArray) pack.get("dependencies");

            graph.addVertex(name); // add vertex on graph
            for (int j = 0; j < dependencies.size(); j++) { // for each dependencies
                String depend = (String) dependencies.get(j);
                // add edge - automatically add vertex too
                graph.addEdge(name, depend);
            }
        }
    }

    /**
     * Helper method to get all packages in the graph.
     * 
     * @return Set<String> of all the packages
     */
    public Set<String> getAllPackages() {
        return graph.getAllVertices(); // get all packages
    }

    /**
     * Given a package name, returns a list of packages in a valid installation
     * order.
     * 
     * Valid installation order means that each package is listed before any
     * packages that depend upon that package.
     * 
     * @return List<String>, order in which the packages have to be installed
     * 
     * @throws CycleException           if you encounter a cycle in the graph while
     *                                  finding the installation order for a
     *                                  particular package. Tip: Cycles in some
     *                                  other part of the graph that do not affect
     *                                  the installation order for the specified
     *                                  package, should not throw this exception.
     * 
     * @throws PackageNotFoundException if the package passed does not exist in the
     *                                  dependency graph.
     */
    public List<String> getInstallationOrder(String pkg)
            throws CycleException, PackageNotFoundException {

        if(pkg != null && graph.getAllVertices().contains(pkg)) {
            ArrayList<String> visit = new ArrayList<String>();
            
            return getInstallationOrder(pkg, visit);
        } else { // the package passed does not exist
            throw new PackageNotFoundException();
        }
    }

    /**
     * This is the private helper method to getInstallationOrder method
     * 
     * @param pkg - 
     * @param visited - 
     * @return List<String> order in which the packages have to be installed
     * @throws CycleException - when detect cycle
     */
    private List<String> getInstallationOrder(String pkg, ArrayList<String> visited)
            throws CycleException {
        
        List<String> output = new ArrayList<String>(); // list to return

        // the vertex working with should not be visited
        if (visited.contains(pkg)) 
            throw new CycleException();

        visited.add(pkg); // mark cur as visited
        
        // list of adjacent vertices
        List<String> adjacent = graph.getAdjacentVerticesOf(pkg);
        
        for(int i=0; i<adjacent.size(); i++) { // for each adjacent vertices
            List<String> installOrder = getInstallationOrder(adjacent.get(i), visited);
            for(int j=0; j<output.size(); j++) {
                // remove visited vertices
                installOrder.remove(output.get(j));
            }
            output.addAll(installOrder); // add rest of vertices
        }
        
        visited.remove(pkg);
        output.add(pkg);
        return output;
    }

    /**
     * Given two packages - one to be installed and the other installed, return a
     * List of the packages that need to be newly installed.
     * 
     * For example, refer to shared_dependecies.json - toInstall("A","B") If package
     * A needs to be installed and packageB is already installed, return the list
     * ["A", "C"] since D will have been installed when B was previously installed.
     * 
     * @return List<String>, packages that need to be newly installed.
     * 
     * @throws CycleException           if you encounter a cycle in the graph while
     *                                  finding the dependencies of the given
     *                                  packages. If there is a cycle in some other
     *                                  part of the graph that doesn't affect the
     *                                  parsing of these dependencies, cycle
     *                                  exception should not be thrown.
     * 
     * @throws PackageNotFoundException if any of the packages passed do not exist
     *                                  in the dependency graph.
     */
    public List<String> toInstall(String newPkg, String installedPkg)
            throws CycleException, PackageNotFoundException {
        
        // two lists to compare
        List<String> output = getInstallationOrder(newPkg); 
        List<String> installOrder = getInstallationOrder(installedPkg);
        
        // remove the differences between two list
        for(int i=0; i<installOrder.size(); i++) { 
            output.remove(installOrder.get(i));
        }
        
        return output;
    }

    /**
     * Return a valid global installation order of all the packages in the
     * dependency graph.
     * 
     * assumes: no package has been installed and you are required to install all
     * the packages
     * 
     * returns a valid installation order that will not violate any dependencies
     * 
     * @return List<String>, order in which all the packages have to be installed
     * @throws CycleException if you encounter a cycle in the graph
     */
    public List<String> getInstallationOrderForAllPackages() throws CycleException {
        // topological sort
        List<String> allVertices = new ArrayList<String>();
        allVertices.addAll(getAllPackages()); // get all vertices
        
        List<String> output = new ArrayList<String>(); // list to return
        
        for(int i=0; i<allVertices.size(); i++) { // for each vertices
            try {
                // get the list of valid installation order
                List<String> installOrder = getInstallationOrder(allVertices.get(i));
                for(int j=0; j<output.size(); j++) {
                    // remove visited vertices
                    installOrder.remove(output.get(j));
                }
                output.addAll(installOrder); // add rest of vertices
            } catch (PackageNotFoundException e) {
            }
        }
        return output;
    }

    /**
     * Find and return the name of the package with the maximum number of
     * dependencies.
     * 
     * Tip: it's not just the number of dependencies given in the json file. The
     * number of dependencies includes the dependencies of its dependencies. But, if
     * a package is listed in multiple places, it is only counted once.
     * 
     * Example: if A depends on B and C, and B depends on C, and C depends on D.
     * Then, A has 3 dependencies - B,C and D.
     * 
     * @return String, name of the package with most dependencies.
     * @throws CycleException if you encounter a cycle in the graph
     */
    public String getPackageWithMaxDependencies() throws CycleException { 
        String output = ""; // String to return
        int maxDep = -1; // max dependency
        
        // get all vertices in the set
        List<String> allVertices = new ArrayList<String>();
        allVertices.addAll(getAllPackages());
        
        for(int i=0; i<allVertices.size(); i++) { // for each vertex
            try {
                int size = getInstallationOrder(allVertices.get(i)).size();
                // update maximum dependency vertex, if any
                if (size > maxDep) {
                    maxDep = size;
                    output = allVertices.get(i);
                }
            } catch (PackageNotFoundException e) {
            }
        }
        return output;
    }

    /**
     * This is the main method to check if some methods work
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("PackageManager.main()");
    }

}
