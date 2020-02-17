////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Project Name: NetworkVisualizer
// 
// ATeam 62
//
// Program Description: This program implements GraphADT to work with 
//              NetworkVisualizer. This graph successfully stores and creates 
//              friendships between users in a local network(graph).
//
// Author: Jesus Vazquez
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Filename: Graph.java
 * 
 * Undirected and unweighted graph implementation
 * 
 */
public class Graph implements GraphADT {

    // Global variables
    private HashMap<Person, List<Person>> networkDirectory;
    private int amountOfUsers;
    private int amountOfFriendships;

    /**
     * Default no-argument constructor
     */
    public Graph() {
        networkDirectory = new HashMap<Person, List<Person>>();
        amountOfUsers = 0;
        amountOfFriendships = 0;
    }

    /**
     * Size() - Returns the number of friendships in this network. 
     * (Amount of links between users)
     * 
     * @return amountOfFriendships - total amount of edges between all users 
     */
    public int size() {
        System.out.println("\tNumber of friendships in network : " + amountOfFriendships);
        return amountOfFriendships;
    }

    /**
     * Order() - Returns the number of people in this network.
     * 
     * @return amountOfUsers - the total amount of people enrolled in the network
     */
    public int order() {
        System.out.println("\tNumber of people in the network : " + amountOfUsers);
        return amountOfUsers;
    }

    /**
     * addEdge() - Add the friendship between person1 and person2 to the network.
     * 
     * The friendship is undirected ( meaning it goes both ways). person1 is friends
     * with person2 and person2 is friends with person1.
     * 
     * If person1 or person2 does not currently exist in the network, add the
     * person(s) and than add the friendship between the two.
     * 
     * If person1 and person2 are already friends, do nothing and display " person1
     * and person2 are already friends!"
     * 
     * Valid argument conditions: 1. neither person is null 2. both people are in
     * the network 3. Both people are not friends
     * 
     * @return true - if friendships was added between person1 and person2
     * @return false - if friendships already exist between person1 and person2
     */
    @Override
    public boolean addEdge(Person person1, Person person2) {

        System.out.println("addEdge() - Attempting to make " + person1.getName() 
                + " and " + person2.getName() + " friends...\n");

        // If a user tries to friend themselves
        if (person1.equals(person2)) {
            System.out.println("You can't friend yourself!! Sorry :'(\n");
            return false;
        }

        // In case two different users with the same name are added. :)
        if (person1.getName().equals(person2.getName())) {
            System.out.println("You have the same name as someone in the network!");
            System.out.println("Lets add you with an abbreviation:)!");
            // Abbreviates the user with a "I" symbol
            person2.setName(person2.getName() + " I");
        }

        // If person1 does not exist in the networkDirectory
        if (networkContains(person1) == false) {
            this.addNode(person1); // add person1 to the networkDirectory
        }

        // If person2 does not exist in the networkDirectory
        if (networkContains(person2) == false) {
            this.addNode(person2); // add person2 to the networkDirectory
        }

        // If no edge exists between person1 & person2, add the edge.
        if (findEdge(person1, person2) == false) {

            // Make person1 and person2 friends
            networkDirectory.get(person2).add(person1);
            System.out.println("\t" + person2.getName() + " and " + person1.getName()
                    + " are now friends!!");

            networkDirectory.get(person1).add(person2);
            System.out.println("\t" + person1.getName() + " and " + person2.getName()
                    + " are now friends!!");

            amountOfFriendships++;

            size();
            System.out.println();

            // Double checking that the friendships are established.
            findEdge(person1, person2);
            findEdge(person2, person1);

            return true;
        }
        System.out.println("	" + person1.getName() + " & " + person2.getName()
                + " are ALREADY FRIENDS!! \n\t No changes being made.\n");
        return false;
    }

    /**
     * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed
     * and unweighted) If either vertex does not exist, or if an edge from vertex1
     * to vertex2 does not exist, no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
     * the graph 3. the edge from vertex1 to vertex2 is in the graph
     * 
     * @return true - if friendships was removed between person1 and person2
     * @return false - if friendships cannot be removed between person1 and person2
     */
    @Override
    public boolean removeEdge(Person person1, Person person2) {
        System.out.println("removeEdge() - Attempting to unfriend " + person1.getName() 
                + " and " + person2.getName() + "...");

        if (size() == 0) {
            System.out.println("\tNo friendships currently exist in the network.");
            return false;
        }
        
        // If a person does not not exist in the network...
        if (networkContains(person1) == false || networkContains(person2) == false) {
            System.out.println("One of the users does not exist.");
            return false;
        }

        if (findEdge(person1, person2) == true) {
            // Add new person to current friend list for both people
            networkDirectory.get(person2).remove(person1);
            networkDirectory.get(person1).remove(person2);
            amountOfFriendships--;

            System.out.println("	Successfully unfriended " + person1.getName() 
                    + " and " + person2.getName() + "\n");
            size();
            System.out.println();

            // Double checking that the friendships are established.
            findEdge(person1, person2);
            findEdge(person2, person1);
            return true;

        }
        System.out.println(person1.getName() + " & " + person2.getName() + " are NOT FRIENDS");
        return false;
    }

    /**
     * Add new node to the graph.
     *
     * If node is null or already exists, method ends without adding a node or
     * throwing an exception.
     * 
     * Valid argument conditions: 1. node is non-null 2. node is not already in
     * the graph
     */
    @Override
    public boolean addNode(Person vertex) {

        System.out.println("addNode() - Attempting to add " + vertex.getName() 
                + " to the network....\n");

        if (vertex.getName() != null && networkContains(vertex) == false) {
            networkDirectory.put(vertex, new ArrayList<Person>());
            amountOfUsers++;
            System.out.println("\t" + vertex.getName() + " has been successfully added "
                    + "to the network!!\n");
            order();
            System.out.println();
            return true;
        }
        System.out.println("User not added because they are already in the network!!\n");
        return false;
    }

    /**
     * Remove a vertex and all associated edges from the graph.
     * 
     * If vertex is null or does not exist, method ends without removing a vertex,
     * edges, or throwing an exception.
     * 
     * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in
     * the graph
     */
    @Override
    public boolean removeNode(Person vertex) {
        System.out.println("removeNode() - Attempting to remove " + vertex.getName() 
                + " from the network...\n");

        // If String vertex is not null and vertex is in the graph, remove vertex
        if (vertex.getName() != null && networkContains(vertex) == true) {
            System.out.println("\t" + vertex.getName() + " currently has "
                    + amountOfFriends(vertex) + " friends!!\n");

            while (amountOfFriends(vertex) > 0) {
                // Before removable, we must unlink friendships.
                this.removeEdge(vertex, networkDirectory.get(vertex));
            }

            networkDirectory.remove(vertex);
            amountOfUsers--;

            System.out.println("\tRemoved " + vertex.getName() + " from the network!!\n");
            order();
            System.out.println();
            return true;
        }
        return false;
    }

    /**
     * This is the private helper method for removing edges
     * 
     * @param person1 - person to remove from the list
     * @param list - list of friends
     */
    private void removeEdge(Person person1, List<Person> list) {

        for (int i = 0; i < list.size(); i++) {
            System.out.println("Successfully removed " + list.get(i).getName() + " from "
                    + person1.getName() + " friends list!\n");
            this.removeEdge(person1, list.get(i));
            System.out.println();
            System.out.println("\t"+person1.getName()+" now has "+list.size()+" friends!!\n");
            if (list.size() == 0) {
                break;
            }
        }
    }

    /**
     * Get all the neighbor (adjacent) vertices of a vertex
     *
     */
    @Override
    public Set<Person> getNeighbors(Person vertex) {

        System.out.println("Retrieving all of " + vertex.getName() + " friendships...");

        if (vertex.getName() != null && networkContains(vertex) == true) {

            Set<Person> friendsList = new HashSet<Person>();

            for (Person person : networkDirectory.get(vertex)) {
                friendsList.add(person);
            }
            return friendsList;

        }
        System.out.println("The specified user " + vertex.getName() + " is not in the network");
        return new HashSet<Person>();
    }

    /**
     * This method get the name of person and return the Person
     * from the networkDirectory
     * 
     * @param nameOfPerson - name of person to get node
     */
    @Override
    public Person getNode(String nameOfPerson) {

        for (Person person : networkDirectory.keySet()) {
            if (person.getName().equals(nameOfPerson)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Returns a Set that contains all the vertices
     * 
     */
    @Override
    public Set<Person> getAllNodes() {

        System.out.println("Exporting all users in the network... ");

        return networkDirectory.keySet();
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
    private boolean networkContains(Person person1) {
        System.out.println("\tChecking if the network currently contain " + person1.getName());

        boolean found = false;

        if (networkDirectory.containsKey(person1)
                || networkDirectory.containsValue(person1.getName())) {
            found = true;
        }

        System.out.println("\t" + found+"\n");
        return found;
    }

    /**
     * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed
     * and unweighted) If either vertex does not exist, or if an edge from vertex1
     * to vertex2 does not exist, no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
     * the graph 3. the edge from vertex1 to vertex2 is in the graph
     */
    private boolean findEdge(Person person1, Person person2) {

        System.out.println("\tAre "+person1.getName()+" & "+person2.getName()+" friends?");
        boolean friendship = false;

        if (networkDirectory.get(person1).contains(person2)
                && networkDirectory.get(person2).contains(person1)) {
            friendship = true;
        }

        System.out.println("\t" + friendship + "\n");

        return friendship;
    }

    /**
     * This method returns the amount of friends that a specific
     * person has.
     * 
     * @param person - specific person
     * @return number of friends
     */
    private int amountOfFriends(Person person) {
        int numFriends = 0;

        if (!networkDirectory.get(person).isEmpty()) {
            numFriends = networkDirectory.get(person).size();
        }
        return numFriends;
    }

    /**
     * This is the main method to test if all methods in this class works
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Internal testing...
        Graph dev = new Graph();

        Person test1 = new Person();
        test1.setName("Person1");

        Person test2 = new Person();
        test2.setName("Person2");

        Person test3 = new Person();
        test3.setName("Person1");

        Person test4 = new Person();
        test4.setName("Person4");

        Person test11 = new Person();
        test11.setName("Person1");

        Person test21 = new Person();
        test21.setName("Person2");

        Person test31 = new Person();
        test31.setName("Person3");

        Person test41 = new Person();
        test41.setName("Person4");

        dev.addNode(test1);

        dev.addNode(test1);

        dev.addEdge(test1, test1);
        dev.addEdge(test1, test3);

        dev.addNode(test2);

        dev.removeNode(test1);

        dev.addEdge(test1, test2);
        dev.addEdge(test2, test1);
        dev.addEdge(test3, test1);
        dev.addEdge(test1, test4);
        dev.addEdge(test1, test41);
        dev.addEdge(test1, test31);
        dev.addEdge(test1, test21);
        dev.addEdge(test11, test1);

        dev.removeNode(test1);

        dev.removeEdge(test2, test1);
        dev.removeEdge(test1, test2);

        System.out.println(dev.getAllNodes());
        System.out.println(dev.getNeighbors(test1));
        System.out.println(dev.getNode("Person3").getName());

        ArrayList<String> testing1 = new ArrayList<String>();
        for (Person person : dev.getNeighbors(test1)) {
            testing1.add(person.getName());
        }
        System.out.println(testing1);
    }

}

