//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Project name: Book Hash Table
// Files: Book.javaBookHashTable.java, BookHashTableTest.java, BookParser.java
// Course: CS 400 Fall 2019
//
// Author: Ye Ji Kim
// Email: ykim762@wisc.edu
// Lecture number: 001
// Lecturer's Name: Debra Deppeler
//
//////////////////////////// 80 columns wide ///////////////////////////////////
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

// TODO: comment and complete your HashTableADT implementation
//
// TODO: implement all required methods
// DO ADD REQUIRED PUBLIC METHODS TO IMPLEMENT interfaces
//
// DO NOT ADD ADDITIONAL PUBLIC MEMBERS TO YOUR CLASS 
// (no public or package methods that are not in implemented interfaces)
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
// if open addressing: describe probe sequence 
// if buckets: describe data structure for each bucket
//      I used ArrayList for hash table and used LinkedList for Buckets
//      Each Buckets has a LinkedList as its bucket
//
// TODO: explain your hashing algorithm here 
//      My hash table is an ArrayList that and my buckets are a LinkedList.
//      When hash table needed to be rehashed, I extends its size to 2 times plus 1.
//      When it has Collision, I added to the end of the LinkedList it the buckets.
//      reSize before inserting the new Pair of book and key.
//
// NOTE: you are not required to design your own algorithm for hashing,
//       since you do not know the type for K,
//       you must use the hashCode provided by the <K key> object

/**
 * HashTable implementation that uses: 
 * chained bucket that uses array list of linked lists
 * 
 * @param <K> unique comparable identifier for each <K,V> pair, may not be null
 * @param <V> associated value with a key, value may be null
 */
public class BookHashTable implements HashTableADT<String, Book> {

    /** The initial capacity that is used if none is specified user */
    static final int DEFAULT_CAPACITY = 101;

    /** The load factor that is used if none is specified by user */
    static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;

    /**
     * This is the inner class that make the key of the Book and
     * the Book itself as a pair
     * 
     * @author Ye Ji Kim
     */
    private class Pair {
        private String key; // key of the Book
        private Book book; // Book itself

        /**
         * Constructor to make the Paif object
         * @param key - key of the Book
         * @param book - book
         */
        Pair(String key, Book book) {
            this.key = key;
            this.book = book;
        }
    }

    private static ArrayList<LinkedList<Pair>> table; // hash table
    private static int numKeys; // number of keys stored in the hash table
    static int capacity; // capacity of the hash table
    static double load_factor_threshold; // load factor threshold

    /**
     * REQUIRED default no-arg constructor Uses default capacity and sets load
     * factor threshold for the newly created hash table.
     */
    public BookHashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR_THRESHOLD);
    }

    /**
     * Creates an empty hash table with the specified capacity and load factor.
     * 
     * @param initialCapacity     number of elements table should hold at start.
     * @param loadFactorThreshold the ratio of items/capacity that causes table to
     *                            resize and rehash
     */
    public BookHashTable(int initialCapacity, double loadFactorThreshold) {
        capacity = initialCapacity;;
        load_factor_threshold = loadFactorThreshold;
        
        // create the table with given capacity and initialize the table
        table = new ArrayList<LinkedList<Pair>>(capacity);
        for (int i = 0; i < capacity; i++)
            table.add(null); // each table elements has null now
        
        numKeys = 0;
    }

    /**
     * This is the method to get the hashed index with given key
     * 
     * @param key - key of the Book
     * @return hash index
     */
    private int hash(String key) {
        // get integer from hashCode method and mod with TS
        int index = Math.abs(key.hashCode()) % capacity;
        return index;
    }

    /**
     * This method resizes the hash table when the load factor of the table
     * is equals or larger than the load factor threshold
     * 
     * @throws IllegalNullKeyException - if key is null
     * @throws DuplicateKeyException - if key is already in the table
     */
    private void reSize() throws IllegalNullKeyException, DuplicateKeyException {
        int oldcap = capacity;
        capacity = oldcap * 2 + 1; // get the new capacity
        
        // ArrayList that will have all pairs from old table
        ArrayList<Pair> pairs = new ArrayList<Pair>();
        for(int i=0; i<oldcap; i++) { // get all of pairs from the old table
            if(table.get(i) != null) { // skip if empty index
                for(int j=0; j<table.get(i).size(); j++) {
                    pairs.add(table.get(i).get(j));
                }
            }
        }
        
        table = new ArrayList<>(capacity); // table with the new capacity 
        for (int i=0; i < capacity; i++) { //initialize the new table
            table.add(null);
        }
        numKeys = 0; // initialize the number of keys
        
        // insert the key and Book pairs to the table
        for(int i=0; i<pairs.size(); i++) {
            insert(pairs.get(i).key, pairs.get(i).book);
        }
        
    }

    /**
     * This method insert the key and Book pair to the hash table 
     * with valid hash index
     * Add the key,value pair to the data structure and 
     * increase the number of keys.
     * 
     * @param key - key of the Book
     * @param value - Book
     * @throws IllegalNullKeyException - if key is null
     * @throws DuplicateKeyException - if key is already in the table
     */
    @Override
    public void insert(String key, Book value)
                    throws IllegalNullKeyException, DuplicateKeyException {
        if (key == null)
            throw new IllegalNullKeyException();
        if (contains(key))
            throw new DuplicateKeyException();

        // check the load factor to decide whether needed to be resize or not
        double loadFactor = (double) (numKeys) / (double) capacity;
        if (loadFactor >= getLoadFactorThreshold())
            reSize(); // call reSize method

        Pair pair = new Pair(key, value); // create the pair of key and Book
        LinkedList<Pair> list; // list with Pair type for referencing
        int index = hash(key); // hash index
        
        if(table.get(index) == null) { // if table with the index is null
            list = new LinkedList<Pair>(); // add the linked list
        } else { // if table with the index is already has the pair
            list = table.get(index); // list is the linked list in that index
        }
        list.add(pair); // add pair to the list
        table.set(index, list); // replace the LinkedList with pair added list
        numKeys++; //increase
    }

    /**
     * This method remove the key and Book pair in the hash table 
     * with valid hash index
     * If key is found, remove the pair from the data structure
     * decrease number of key
     * 
     * @param key - key of the Book
     * @throws IllegalNullKeyException - if key is null
     * @return false if key is not found
     */
    @Override
    public boolean remove(String key) throws IllegalNullKeyException {
        if (key == null)
            throw new IllegalNullKeyException();
        if (!contains(key))
            return false;

        LinkedList<Pair> list = table.get(hash(key));
        for (int i = 0; i < list.size(); i++) { // loop for search
            if (list.get(i).key.equals(key)) {
                list.remove(i);
                BookHashTable.numKeys--;
                return true;
            }
        }
        return false;
    }

    /**
     * This method get the key and Book pair in the hash table 
     * with valid hash index
     * Does not remove key or decrease number of keys
     * If key is found, returns the value associated with the specified key
     * 
     * @param key - key of the Book
     * @throws IllegalNullKeyException - if key is null
     * @throws KeyNotFoundException - if key is not found
     * @return the value associated with the specified key
     */
    @Override
    public Book get(String key) throws IllegalNullKeyException, KeyNotFoundException {
        if (key == null)
            throw new IllegalNullKeyException();
        if (!contains(key))
            throw new KeyNotFoundException();

        Book out = null; // book to return
        
        LinkedList<Pair> list = table.get(hash(key)); // list in that index
        for (int i = 0; i < list.size(); i++) { // loop to find the key
            if (list.get(i).key.equals(key)) {
                out = list.get(i).book;
            }
        }
        return out;
    }
    
    /**
     * Returns the number of key,value pairs in the data structure
     * @return numKeys
     */
    @Override
    public int numKeys() {
        return BookHashTable.numKeys;
    }

    /**
     * This is the method to check if the key is in the table
     * 
     * @param key - key of the Book
     * @return true if key is in the data structure
     */
    private boolean contains(String key) {
        int index = hash(key); // index in table
        
        LinkedList<Pair> pair = table.get(index);
        // table doesn't have anything with the index
        if(table.get(index) == null) 
            return false;
        // table with the index has LinkedList with size 0
        if(pair.size() == 0)
            return false;
        
        for (int i = 0; i < pair.size(); i++) { // loop for searching key 
            if(pair.get(i).key.equals(key))
                return true;
        }
        return false;
    }

    /**
     * Returns the load factor for this hash table that determines 
     * when to increase the capacity of this hash table
     * 
     * @return load_factor_threshold
     */
    @Override
    public double getLoadFactorThreshold() {
        return BookHashTable.load_factor_threshold;
    }

    /**
     * Returns capacity for this hash table
     * 
     * @return current capacity
     */
    @Override
    public int getCapacity() {
        return BookHashTable.capacity;
    }

    /**
     * This method is simply returns the collision resolution scheme
     * used for this hash table
     * 
     * @return the collision resolution scheme used for this hash table.
     */
    @Override
    public int getCollisionResolutionScheme() {
        return 5; // 5 CHAINED BUCKET: array list of linked lists
    }

}
