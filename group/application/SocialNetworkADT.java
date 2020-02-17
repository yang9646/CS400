package application;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Filename: SocialNetworkADT.java 
 * Project: Network Visualizer
 * Authors: Ateam 62
 * 
 * A simple Social Network interface
 */
public interface SocialNetworkADT {

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
    public boolean addFriends(String person1, String person2);

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
    public boolean removeFriends(String person1, String person2);

    /**
     * addUser() - Objective: Adds a NEW USER to the network
     * 
     * Only reason this would return false is if the user is already in the network
     * 
     * @param person
     * @return boolean
     */
    public boolean addUser(String person);

    /**
     * removeUser() - Objective: Removes a CURRENT USER from network
     * 
     * Helpful info: getNode() will return null if the person is not in the network.
     * Only reason this would return false is if the user is not in the network
     * 
     * @param person
     * @return boolean
     */
    public boolean removeUser(String person);

    /**
     * getFriends() - Objective: Retrieves Friends of CURRENT USER in network If
     * user does not exist in the network, return null. Or return a empty set?
     * 
     * Helpful info: getNode() will return null if the person is not in the network.
     * 
     * @param person
     * @return Set<Person>
     */
    public Set<Person> getFriends(String person);

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
    public Set<Person> getMutualFriends(String person1, String person2);

    /**
     * getShortestPath() -
     * Objective: Retrieves the shortest path between two CURRENT USERS
     * person1's friends should know who person2 is. 
     * 
     * Will return null if users are NOT in the network
     * 
     * Helpful info:
     * getNode() will return null if the person is not in the network.
     * 
     * @param person1
     * @param person2
     * @return List<Person>
     */
    public List<Person> getShortestPath(String person1, String person2);

    /**
     * getConnectedComponents() - Objective: Retrieves all components within the network that are connected.
     * 
     * Helpful info: Returns a set of graph objects, each of which represent the connected components within the larger network.
     * @return Set<Graph>
     */
    public Set<Graph> getConnectedComponents();

    /**
     * loadFromFile() - Objective: creates a social network using commands from a text file in the correct format.
     * 
     * Helpful info: will throw an exception for an invalid file/file format
     * @param file
     */
    public void loadFromFile(File file);

    /**
     * saveToFile() - Objective: saves user commands used to create the social network to a text file
     * 
     * @param file
     */
    public void saveToFile(File file);
}
