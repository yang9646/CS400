//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Project name: NetworkVisualizer
//
// ATeam 62
//
// Author: Ye Ji Kim
//
// Program Description:
// This program implements GraphADT to work with NetworkVisualizer. This graph
// successfully stores and creates friendships between users in a local network(graph).
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////

package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class SocialNetwork implements SocialNetworkADT {

    private Graph graph; // graph variable
    private String log; // log to save
    String cenUser; // central user

    /**
     * Social Network default no-argument constructor
     */
    SocialNetwork() {
        graph = new Graph();
        log = "";
    }

    /**
     * addFriends() - Objective: Adds FRIENDSHIP between CURRENT USERS Adds NEW
     * USERS if people do not currently exist in the network. Will return false if
     * friendship already exists!
     * 
     * Helpful info : getNode() will return null if the person is NOT in the
     * network.
     * 
     * Worst case, addEdge() can handle users that currently don't exist in the
     * network, it will still add the user and the friendship but the addition of
     * the new user wont be logged.
     * 
     * @param person1
     * @param person2
     * @return boolean
     */
    @Override
    public boolean addFriends(String person1, String person2) {

        // Checks if person1 exists in the network
        if (graph.getNode(person1) == null) {
            this.addUser(person1);
        }

        // Checks if person2 exists in the network
        if (graph.getNode(person2) == null) {
            this.addUser(person2);
        }

        Person p1 = graph.getNode(person1);
        Person p2 = graph.getNode(person2);

        // If friendship was added, log the addition of the friendship, otherwise, don't
        // log.
        if (graph.addEdge(p1, p2) == true) {
            log += "a " + person1 + " " + person2 + "\n";
            return true;
        }
        // if friendship already exists
        System.out.println("^ Friendship probably already exist");
        System.out.println("Or you can't friend yourself");
        return false;
    }

    /**
     * removeFriends() - Objective: Removes a CURRENT FRIENDSHIP between CURRENT
     * USERS in the network. Will return false if friendship or users do NOT exist
     * in the network.
     * 
     * Helpful info : getNode() will return null if the person is NOT in the
     * network.
     * 
     * @param person1
     * @param person2
     * @return boolean
     */
    @Override
    public boolean removeFriends(String person1, String person2) {

        if (graph.getNode(person1) != null && graph.getNode(person2) != null) {
            Person p1 = graph.getNode(person1);
            Person p2 = graph.getNode(person2);

            // if there is friendship, log the removal, otherwise no log is written.
            if (graph.removeEdge(p1, p2) == true) {
                log += "r " + person1 + " " + person2 + "\n";
                return true; // need to explicitly return true due to the use of remove edge already
            }

            // Will return false if friendship does not exist.
            System.out.println("Friendship does not exist.");
            return false;
        } else {
            // Will return false if one of the users does not exist in the network
            System.out.println("One of the specified users is not in the network or ");
            System.out.println("Friendship does not exist.");
            return false;
        }
    }

    /**
     * addUser() - Objective: Adds a NEW USER to the network
     * 
     * Only reason this would return false is if the user is already in the network
     * 
     * @param person
     * @return boolean
     */
    @Override
    public boolean addUser(String person) {
        Person p = new Person(person);

        // Logs only if user is added successfully
        if (graph.addNode(p) == true) {
            log += "a " + person + "\n";
            return true;
        }

        // Will return false if user is already in network
        System.out.println("User is already in the network.");
        return false;
    }

    /**
     * removeUser() - Objective: Removes a CURRENT USER from network
     * 
     * Helpful info: getNode() will return null if the person is not in the network.
     * Only reason this would return false is if the user is not in the network
     * 
     * @param person
     * @return boolean
     */
    @Override
    public boolean removeUser(String person) {

        // if user is in the network perform the removal
        // logs only if user is removed, in this case, it will always be.
        if (graph.getNode(person) != null) {
            Person p = graph.getNode(person);

            // Removes friends and logs the changes in the log
            for (Person person1 : this.getFriends(person)) {
                this.removeFriends(person, person1.getName());
            }

            log += "r " + person + "\n";
            return graph.removeNode(p);
        } else {
            System.out.println("The specified user is not in the network");
            System.out.println("User does not exist.");
            return false;
        }
    }
    
    /**
     * selectCentUser() - Objective: allows user to select a central user in the network.
     * 
     * Helpful info: Will return true if user selects a valid user in the network.
     * Will return false in all other cases.
     * 
     * @param person
     * @return boolean
     */
    public boolean selectCentUser(String person) {

        // if user is in the network perform the removal
        // logs only if user is removed, in this case, it will always be.
        if (graph.getNode(person) != null) {
        	
        	// set person as central user
            cenUser = person;
            
            log += "s " + person + "\n";
            return true;
        } else {
            System.out.println("The specified user is not in the network");
            System.out.println("User does not exist.");
            return false;
        }
    }

    /**
     * getFriends() - Objective: Retrieves Friends of CURRENT USER in network If
     * user does not exist in the network, return null. Or return a empty set?
     * 
     * Helpful info: getNode() will return null if the person is not in the network.
     * 
     * @param person
     * @return Set<Person>
     */
    @Override
    public Set<Person> getFriends(String person) {
        if (graph.getNode(person) == null) {
            System.out.println(person + " is not a user in the network!");
            return null;
            // return new HashSet<Person>();
        }
        return graph.getNeighbors(graph.getNode(person));
    }

    /**
     * getMutualFriends() - Objective: Retrieve mutual friends between two CURRENT
     * USERS Only reason this will return null is if a specified user does not exist
     * in the network.
     * 
     * Helpful info: getNode() will return null if the person is not in the network.
     * getNeighbors() will return null if user is not in network.
     * 
     * @param person1
     * @param person2
     * @return Set<Person>
     */
    @Override
    public Set<Person> getMutualFriends(String person1, String person2) {

        List<Person> p1friends = new ArrayList<Person>();
        List<Person> p2friends = new ArrayList<Person>();

        // If users exist in network
        if (graph.getNode(person1) != null && graph.getNode(person2) != null) {

            Person p1 = graph.getNode(person1);
            Person p2 = graph.getNode(person2);

            // Add friends of each person
            p1friends.addAll(graph.getNeighbors(p1));
            p2friends.addAll(graph.getNeighbors(p2));

            Set<Person> output = new HashSet<Person>();

            // Find mutual friends
            for (int i = 0; i < p1friends.size(); i++) {
                if (p2friends.contains(p1friends.get(i))) {
                    output.add(p1friends.get(i));
                }
            }

            return output;
        } else {
            // Can return null, or new empty Set<People>
            System.out.println("One of the specified users is not in the network.");
            System.out.println("Unable to retrieve Mutual Friends lists");
            return null;
        }
    }

    /**
     * getShortestPath() -
     * Objective:
     * Retrieves the shortest path between two CURRENT USERS
     * person1's friends should know who person2 is. 
     * 
     * Will return empty list if users are NOT in the network
     * 
     * Helpful info:
     * getNode() will return null if the person is not in the network.
     * 
     * @param person1 - person is looking to be friends with person2
     * @param person2 - person, person1 is looking for amongst everyones friends.
     * @return List<Person> - List of people to "talk" to in order to find person2
     */
    @Override
    public List<Person> getShortestPath(String person1, String person2) {
    	
    	List<Person> output = new ArrayList<Person>();
    	Person storingPerson = graph.getNode(person1);
    	
    	if(graph.getNode(person1)!= null && graph.getNode(person2)!= null) {
    		
    		System.out.println("Currently looking for " + person2 + " for " + person1);
    		System.out.println();
    	
    	// Friends of user
        Set<Person> friendsOfPerson = this.getFriends(person1);
        
        // If user is already in person1's friends list
        if(friendsOfPerson.contains(graph.getNode(person2))) {
        	System.out.println("Your are friends with " + person2 + "!!");
        	return output;
        }
        else {
        	boolean notFound = false;
        	
        	while(!notFound) {
        		
        		// If someone has person1 as a friend, skip over person1 in the list
        	for(Person person:friendsOfPerson) {
        		if(person.getName().equals(person1)) {
        			continue;
        		}
        		// if person2 is friends with person, exit loop
        		if(friendsOfPerson.contains(graph.getNode(person2))) {
        			System.out.println(storingPerson.getName() + " has " + person2 + " as a friend!");
        			System.out.println("Found your shortest path to " + person2 + "!!");
        			notFound = true;
        			break;
        		}
        		// Saves person and associated friendslist for next iteration
        		else {
        			System.out.println("Looks like " + storingPerson.getName() + " doesnt have " + person2 + " as a friend. Continuing to search..");
        			System.out.println("Looks like " + person1 + " is friends with " + person.getName());
        			System.out.println("Checking to see if " + person.getName() + " is friends with "+ person2 + " ...");
        			storingPerson = person;
        			output.add(storingPerson); // Building shortestPath list
        			friendsOfPerson = this.getFriends(person.getName());
        			continue;
        		}
        	}
        	}
        }
    	}

        	

		return output;
    }
    

    /**
     * getInstallationOrder() - Objective: Given a valid user name, calls on a helper method and returns
     * a list of people in a valid installation order.  
     * 
     * @param person
     * @param visited
     * @return List<Person>
     */
    private List<Person> getInstallationOrder(String person) {
    	// if the user is in the network & argument is not null
        if (person != null && graph.getNode(person) != null) {
            ArrayList<String> visited = new ArrayList<String>();
            return getInstallationOrderHelper(person, visited);
        } else {
            return null; // TODO : handle null return type
        }
    }
    
    /**
     * getInstallationOrder() - Objective: Returns a list of people in a valid installation order.  
     * 
     * @param person
     * @param visited
     * @return List<Person>
     */
    private List<Person> getInstallationOrderHelper(String person, ArrayList<String> visited) {
        Person p = graph.getNode(person);
        List<Person> output = new ArrayList<Person>(); // list to return

        // the vertex working with should not be visited
        if (visited.contains(person)) {
            // throw new CycleException();
        }

        visited.add(person); // mark cur as visited

        // list of adjacent vertices
        Set<Person> adjacent = graph.getNeighbors(p);
        List<Person> adjList = new ArrayList<Person>();

        for (int i = 0; i < adjList.size(); i++) { // for each adjacent vertices
            List<Person> installOrder =
                    getInstallationOrderHelper(adjList.get(i).getName(), visited);
            for (int j = 0; j < output.size(); j++) {
                // remove visited vertices
                installOrder.remove(output.get(j));
            }
            output.addAll(installOrder); // add rest of vertices
        }

        visited.remove(person);
        output.add(p);
        return output;
    }
    
    /**
     * getConnectedComponents() - 
     * Objective:
     * Retrieve graphs that are connected via a friendship
     * Helpful info:
     * Returns a set of graph objects, each of which represent the connected components within the larger network.
     * @return Set<Graph>
     */
    @Override
    public Set<Graph> getConnectedComponents() {
    	// Needs to return graphs(people) that are friends with each other. 
    	
        List<Graph> list = new ArrayList<Graph>();
        //create list to track visited/unvisited nodes
        boolean[] visited = new boolean[graph.order()];
        Set<Person> people = graph.getAllNodes();
        // transfer people to a list for iteration
        List<Person> peopleList = new ArrayList<Person>();
        peopleList.addAll(people);
        
        // transfer nodes relating to each person object to a new list
        List<String> nodesList = new ArrayList<String>();
        for (int i = 0; i < peopleList.size(); i++) {
            nodesList.add(peopleList.get(i).getName());
        }

        Queue<String> queue = new LinkedList<String>();
        String currentUser;
        
        for (int i = 0; i < nodesList.size(); ++i) {
        	//if node has not been visited
            if (!visited[i]) {
                Graph currentGraph = new Graph();
                list.add(currentGraph);
                // add the node to the current graph
                currentGraph.addNode(graph.getNode(nodesList.get(i)));
                // mark as visited
                visited[i] = true;
                // add node to the queue
                queue.add(nodesList.get(i));

                Set<Person> friendSet;
                List<Person> friendsList = new ArrayList<Person>();
                while (!queue.isEmpty()) {
                	// set node at head of queue as currentUser
                    currentUser = queue.poll();
                    friendSet = graph.getNeighbors(graph.getNode(currentUser));
                    friendsList.addAll(friendSet);

                    for (Person friend : friendsList) {
                    	// add all friendships for the currentUser to the currentGraph
                        currentGraph.addEdge(graph.getNode(currentUser),
                                graph.getNode(friend.getName()));
                        // mark newly added friend node as visited
                        if (!visited[nodesList.indexOf(friend.getName())]) {
                            visited[nodesList.indexOf(friend.getName())] = true;
                            // add newly added friend to the queue
                            queue.add(friend.getName());
                        }
                    }
                }
            }
        }
        
        // create a set to transfer list items
        Set<Graph> output = new HashSet<Graph>();
        output.addAll(list);
        
        return output;
    }
    
    /**
     * getAllPeople() - 
     * Objective:
     * Returns a list of strings containing all users within the network
     * 
     * @return List<String>
     */
    public List<String> getAllPeople() {
        List<Person> people = new ArrayList<Person>();
        people.addAll(graph.getAllNodes());
        List<String> output = new ArrayList<String>();
        for(Person person: people) {
            output.add(person.getName());
        }
        return output;
    }
    
    /**
     * loadFromFile() - 
     * Objective: 
     * Creates a social network using commands from a text file in the correct format.
     * 
     * Helpful info: will throw an exception for an invalid file/file format
     * @param file
     */
    @Override
    public void loadFromFile(File file) {
        try {
            Scanner input = new Scanner(file); // scanner to read file

            while (input.hasNextLine()) { 
                String data = input.nextLine().trim().toLowerCase();
                System.out.println(data);
                // separate each component of command into a list
                String[] cmd = data.split(" ");

                switch (cmd[0]) {
                    case "a": // add a user or friendship to the network
                    	// check command format
                        if (!cmd[1].isEmpty() && cmd.length == 3) {
                            addFriends(cmd[1], cmd[2]);
                            continue;
                        } else {
                            addUser(cmd[1]);
                            continue;
                        }
                    case "r": //remove a user or friendship from the network
                        if (!cmd[1].isEmpty() && cmd.length == 3) {
                            removeFriends(cmd[1], cmd[2]);
                            continue;
                        } else {
                            removeUser(cmd[1]);
                            continue;
                        }
                    case "s": // set central user
                        if (!cmd[1].isEmpty() && cmd.length == 2) {
                            // Retrieves the friends of specified user.
                            // graph.getNode(cmd[1]) - retrieves user
                            System.out.println("Setting " + cmd[1] + " as central user!!");
                            selectCentUser(cmd[1]);
                            continue;
                        }
                }
            }
            input.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * saveToFile() - 
     * Objective: 
     * Saves user commands used to create the social network to a text file
     * 
     * @param file
     */
    @Override
    public void saveToFile(File file) {
        try {
            PrintWriter out = new PrintWriter(file);
            out.write(log);
            out.close();
        } catch (FileNotFoundException e) {
            // TODO: handle exception
        }
    }

    /**
     * This is the main method to check if SocialNetwork class works properly
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Internal testing...

        SocialNetwork test = new SocialNetwork();

        test.addUser("Ye Ji");
        test.addUser("Sanat");
        test.addUser("David");
        test.addUser("Riza");
        test.addUser("Jesus");
        
        test.addFriends("Jesus", "Riza");
        test.addFriends("Ye Ji", "Sanat");
        test.addFriends("Ye Ji", "Riza");
        test.addFriends("Ye Ji", "Jesus");
        test.addFriends("David", "Sanat");
        //test.addFriends("David","Jesus");
        
      ArrayList<String> testin = new ArrayList<String>();
      for (Person person : test.getShortestPath("David", "Riza")) {
          testin.add(person.getName());
      }
      System.out.println(testin);

        test.removeFriends("Jesus", "Riza");
        test.removeFriends("Jesus", "Ye Ji");
        test.removeUser("Ye Ji");

        test.addFriends("Jesus", "Ye Ji");
        test.addFriends("Jesus", "David");
        test.addFriends("Sanat", "David");
        test.addFriends("Ye Ji", "David");

        test.removeFriends("Unknown", "Jesus");

        test.removeUser("Unknown");

        test.getFriends("Unknown");

        test.getShortestPath("Unknown", "Jesus");

        test.addFriends("", "");

        ArrayList<String> testing = new ArrayList<String>();
        for (Person person : test.getFriends("Ye Ji")) {
            testing.add(person.getName());
        }
        System.out.println(testing);

        ArrayList<String> testing1 = new ArrayList<String>();
        for (Person person : test.getFriends("Jesus")) {
            testing1.add(person.getName());
        }
        System.out.println(testing1);

        ArrayList<String> testing2 = new ArrayList<String>();
        for (Person person : test.getMutualFriends("Ye Ji", "Jesus")) {
            testing2.add(person.getName());
        }
        System.out.println(testing2 + " are the mutual friend(s) between " + "Ye Ji & Jesus.");

        File readFile = new File("friends_000.txt");
        test.loadFromFile(readFile);

        File writeFile = new File("Testing");
        test.saveToFile(writeFile);
        
        System.out.println(test.getConnectedComponents().size());
    }
}
