package application;

/**
 * Filename: Person.java 
 * Project: Network Visualizer
 * Authors: Ateam 62
 * 
 * A simple Person class
 */
public class Person {
    private String name; // name of the Person
   
    /**
     * Default no-argument constructor
     */
    Person (){
    }
    
    /**
     * Constructor with name
     */
    Person(String n){
    	name = n;
    }
    
    /**
     * This is the getter method for name
     * 
     * @return name of this Person
     */
    public String getName() {
    	return this.name;
    }
    
    /**
     * This is the setter method for name
     * 
     * @param n - name to set
     */
    public void setName(String n) {
    	name = n;
    }
}
