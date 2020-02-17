//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Project name: Balanced Search Trees
// Files: BALST.java, BALSTTest.java, BSTNode.java
// Course: CS 400 Fall 2019
//
// Author: Ye Ji Kim
// Email: ykim762@wisc.edu
// Lecture number: 001
// Lecturer's Name: Debra Deppeler
//
//////////////////////////// 80 columns wide ///////////////////////////////////
// Students may use and edit this class as they choose
// Students may add or remove or edit fields, methods, constructors for this class
// Students may inherit from an use this class in any way internally in other classes.
// Students are not required to use this class. 
// BUT, IF YOUR CODE USES THIS CLASS, BE SURE TO SUBMIT THIS CLASS
//
// RECOMMENDED: do not use public or private visibility modifiers
// and make this an inner class in your tree implementation.
//
// package level access means that all classes in the package can access directly.
// and accessing the node fields from classes other than your balanced search 
// is bad design as it creates many more chances for bugs to be introduced and not
// caught.
//
// Classes that use this type: BALSTTest.java, BALST.java
/**
 * 
 * @author Ye Ji Kim
 *
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */
class BSTNode<K,V> {
    
    K key; // key of node
    V value; // value of node
    BSTNode<K,V> left; // reference to the left node
    BSTNode<K,V> right; // reference to the right node
    int balanceFactor; // balance factor of this node
    int height; // height of this node
    
    /**
     * This is the constructor that is creating new BSTNode
     * 
     * @param key - key of node
     * @param value - value of node
     * @param leftChild - reference to the left child node
     * @param rightChild - reference to the right child node
     */
    BSTNode(K key, V value, BSTNode<K,V>  leftChild, BSTNode<K,V> rightChild) {
        this.key = key;
        this.value = value;
        this.left = leftChild;
        this.right = rightChild;
        this.height = 0;
        this.balanceFactor = 0;
    }
    
    /**
     * This is the constructor that is creating new BSTNode
     * 
     * @param key - key of node
     * @param value - value of node
     */
    BSTNode(K key, V value) { this(key,value,null,null); }
    
}