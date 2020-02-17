//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Project name: TestADT
// Files: DS_My.java, TestDS_My.java, DataStructureADTTest.java
// Course: CS 400 Fall 2019
//
// Author: Ye Ji Kim
// Email: ykim762@wisc.edu
// Lecture number: 001
// Lecturer's Name: Debra Deppeler
//
//////////////////////////// 80 columns wide ///////////////////////////////////

/**
 * This program is linked list which is implementing DataStuctureADT This 
 * class has insert, remove, contains, get, size methods to add or remove 
 * the list.
 * 
 * @author Ye Ji Kim
 *
 * @param <K> The key must not be null and must be Comparable.
 * @param <V> The data value associated with a given key.
 */
public class DS_My<K extends Comparable<K>, V> implements DataStructureADT<K, V> {

    /**
     * This is the inner class of DS_My class which is storing key and value 
     * as a pair
     * 
     * contains private fields of class
     * 
     * @author Ye Ji Kim
     */
    private class Node {
        private K key;
        private V value;
        private Node next;
    }

    // Private Fields of the class
    private Node root;
    private int size;

    // constructor of DS_My class
    public DS_My() {
        root = null;
        size = 0;
    }

    /**
     * Add the key,value pair to the data structure and increases size. 
     * If key is null, throws IllegalArgumentException("null key"); 
     * If key is already in data structure, throws 
     * RuntimeException("duplicate key"); 
     * can accept and insert null values
     * 
     * @param <K> The key must not be null and must be Comparable.
     * @param <V> The data value associated with a given key.
     */
    @Override
    public void insert(K k, V v) {
        if (k == null) { // key cannot be null
            throw new IllegalArgumentException("null key");
        } else {
            if (this.root == null) { // list is empty
                this.root = new Node(); // add new node to the list
                root.key = k;
                root.value = v;
                size++;
            } else if (contains(k)) { // check if list have same key
                throw new RuntimeException("duplicate key");
            } else {
                Node newNode = new Node(); //add new node
                newNode.key = k;
                newNode.value = v;
                newNode.next = root; // connect new node to the root
                this.root = newNode; // new node becomes root
                size++;
            }
        }
    }

    /**
     * If key is found, Removes the key from the data structure, decreases size
     * If key is null, throws IllegalArgumentException("null key") without
     * decreasing size If key is not found, returns false.
     * 
     * @param <K> The key must not be null and must be Comparable.
     */
    @Override
    public boolean remove(K k) {
        if (k == null) { // key cannot be null
            throw new IllegalArgumentException("null key");
        } else {
            Node curNode = root;
            if(root == null) {
                return false;
            } else if (root != null && root.key.equals(k)) {
                // when root node is the key to remove
                root = curNode.next; //reference to the next node
                size--;
                return true;
            } else {
                Node preNode = null; // node before curNode
                // finding the node that has same key
                while (curNode != null && !curNode.key.equals(k)) {
                    preNode = curNode;
                    curNode = curNode.next;
                }
                if (curNode == null) { // node with same key not found
                    return false;
                } else { // found the node
                    preNode.next = curNode.next; //skip the node found
                    size--;
                    return true; //TODO
                }
            }            
        }
    }

    /**
     * Returns the value associated with the specified key 
     * get - does not remove key or decrease size. 
     * If key is null, throws IllegalArgumentException("null key")
     * 
     * @param <K> The key must not be null and must be Comparable.
     */
    @Override
    public V get(K k) {
        if (k == null) { // key cannot be null
            throw new IllegalArgumentException("null key");
        } else {
            V out = null; // value to return
            Node curNode = root;
            while (curNode != null) {
                if (curNode.key.equals(k)) { // found node with same key
                    out = curNode.value; // value of that node
                }
                curNode = curNode.next;
            }
            return out;
        }
    }
    
    /**
     * Returns true if the key is in the data structure Returns false if 
     * key is null or not present
     * 
     * @param <K> The key must not be null and must be Comparable.
     */
    @Override
    public boolean contains(K k) {
        Node curNode = root;
        while (curNode != null) { // go through all the list
            if (curNode.key.equals(k)) { // key in the list
                return true;
            }
            curNode = curNode.next; // look the next node
        }
        return false;
    }

    /**
     * Returns the number of elements in the data structure
     */
    @Override
    public int size() {
        return this.size;
    }
    
    public static void main(String[] args) {
        DS_My<Integer, Integer> ds = new DS_My<Integer, Integer>();
        for(int i=0; i<50; i++)
            ds.insert(i, i);
        for(int i=0; i<50; i+=2)
            ds.remove(i);
        System.out.println(ds.size);
        
        for(int i=1; i<50; i+=2)
            System.out.print(ds.get(i) + " ");
        System.out.println();
        
        ds.insert(55, 5);
        System.out.println(ds.contains(55));
        ds.remove(55);
        System.out.println(ds.contains(55));
        ds.insert(55, 5);
        System.out.println(ds.contains(55));
    }
}
