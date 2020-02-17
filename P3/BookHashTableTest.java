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

import org.junit.After;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/** 
 * Test HashTable class implementation to ensure that required 
 * functionality works for all cases.
 */
public class BookHashTableTest {

    // Default name of books data file
    public static final String BOOKS = "books.csv";

    // Empty hash tables that can be used by tests
    static BookHashTable bookObject;
    static ArrayList<Book> bookTable;

    static final int INIT_CAPACITY = 2;
    static final double LOAD_FACTOR_THRESHOLD = 0.49;
       
    static Random RNG = new Random(0); //seed to make results repeatable

    /** Create a large array of keys and matching values for use in any test */
    @BeforeAll
    public static void beforeClass() throws Exception{
        bookTable = BookParser.parse(BOOKS);
    }
    
    /** Initialize empty hash table to be used in each test */
    @BeforeEach
    public void setUp() throws Exception {
         bookObject = new BookHashTable(INIT_CAPACITY,LOAD_FACTOR_THRESHOLD);
    }

    /** Not much to do, just make sure that variables are reset     */
    @AfterEach
    public void tearDown() throws Exception {
        bookObject = null;
    }

    /**
     * To insert every key in the Book ArrayList to the hash table
     */
    @Test
    private void insertMany(ArrayList<Book> bookTable) 
        throws IllegalNullKeyException, DuplicateKeyException {
        for (int i=0; i < bookTable.size(); i++ ) {
            bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
        }
    }

    /**
     * Tests that a HashTable is empty upon initialization
     */
    @Test
    public void test000_collision_scheme() {
        if (bookObject == null)
            fail("Gg");
        int scheme = bookObject.getCollisionResolutionScheme();
        if (scheme < 1 || scheme > 9) 
            fail("collision resolution must be indicated with 1-9");
    }


    /** 
     * Tests that a HashTable is empty upon initialization
     */
    @Test
    public void test000_IsEmpty() {
        //"size with 0 entries:"
        assertEquals(0, bookObject.numKeys());
    }

    /**
     * Tests that a HashTable is not empty after adding one (key,book) pair
     * @throws DuplicateKeyException 
     * @throws IllegalNullKeyException 
     */
    @Test
    public void test001_IsNotEmpty() throws IllegalNullKeyException, 
                                    DuplicateKeyException {
        bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
        String expected = ""+1;
        //"size with one entry:"
        assertEquals(expected, ""+bookObject.numKeys());
    }
    
    /** 
    * Test if the hash table  will be resized after adding two (key,book) pairs
    * given the load factor is 0.49 and initial capacity to be 2.
    */    
    @Test 
    public void test002_Resize() throws IllegalNullKeyException, 
                                    DuplicateKeyException {
        bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
        int cap1 = bookObject.getCapacity(); 
        bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
        int cap2 = bookObject.getCapacity();
        
        //"size with one entry:"
        assertTrue(cap2 > cap1 & cap1 ==2);
    }
    
    /**
     * Test if insert every pair of key and book from the bookTable and 
     * get all of it works
     * 
     * @throws IllegalNullKeyException
     * @throws DuplicateKeyException
     * @throws KeyNotFoundException
     */
    @Test 
    public void test003_Insert_Many() throws IllegalNullKeyException, 
                                    DuplicateKeyException, KeyNotFoundException {
        insertMany(bookTable); // call insertMany method
        
        // check if get method returns right values
        for (int i=0; i<bookObject.numKeys(); i++) {      
            assertEquals(bookTable.get(i), bookObject.get(bookTable.get(i).getKey()));
        }
    }
    
    /**
     * Test if insert method throws IllegalNullKeyExcepiton as expected
     */
    @Test 
    public void test004_insert_throws_IllegalNullKeyException() {
        try {
            bookObject.insert(null, bookTable.get(0));
            fail("IllegalNullKeyException should be thrown");  
        }catch(IllegalNullKeyException e) {
            //pass
        }catch(Exception e) {
            fail("Unexpected Exception is thrown");
        }
    }
    
    /**
     * Test if insert method throws DuplicatedKeyException as expected
     */
    @Test 
    public void test005_insert_throws_DuplicateKeyException() {
        try {
            bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
            bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
            bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
            fail("DuplicateKeyException is expected but no exception is thrown");  
        }catch(DuplicateKeyException e) {
            //pass
        }catch (Exception e) {
            fail("DupliacteKeyException is expected but other exception is thrown");
        }
    }
    
    /**
     * Test if remove after insert method doesn't throws DuplicatedKeyException
     */
    @Test 
    public void test006_insert_remove_do_not_throws_DuplicateKeyException() {
        try {
            bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
            bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
            bookObject.remove(bookTable.get(0).getKey());
            bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
            bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
        }catch(DuplicateKeyException e) {
            fail("DuplicateKeyException is unexpected but thrown");              
        }catch (Exception e) {
            fail("No Exception is expected but other exception is thrown");
        }
    }
    
    /**
     * Test if remove method throws IllegalNullKeyException as expected
     */
    @Test 
    public void test007_remove_throws_IllegalNullKeyException() {
        try {
            bookObject.remove(null);
        }catch(IllegalNullKeyException e) {
            //pass
        }catch (Exception e) {
            fail("IllegalNullKeyException is expected but other exception is thrown");
        }
    }
    
    /**
     * Test if remove returns false when key is not found
     */
    @Test 
    public void test008_remove_returns_false() {
        try {
            bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
            boolean test = bookObject.remove(bookTable.get(2).getKey());
            
            if(test) {
                fail("remove should return false if key is not found");
            }
            
        }catch (Exception e) {
            fail("No exception is expected but other exception is thrown");
        }
    }
    
    /**
     * Test if remove method returns true when it removed pair of key and 
     * book appropriately
     */
    @Test 
    public void test009_remove_returns_true() {
        try {
            bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
            boolean test = bookObject.remove(bookTable.get(0).getKey());
            
            if(!test) {
                fail("remove should return true if key is properly removed");
            }
            try {
                //check if the table contains book that is removed
                bookObject.get(bookTable.get(0).getKey());
            }catch (KeyNotFoundException e) {
                // pass
            }catch (Exception e) {
                fail("KeyNotFoundException is expected but other exception is thrown");
            }
        }catch (Exception e) {
            fail("No exception is expected but other exception is thrown");
        }
    }
    
    /**
     * Test if resize(rehash) changes capacity of table correctly
     */
    @Test
    public void test010_check_resize_changes_capacity() {
        try {
            bookObject = new BookHashTable(5, 0.8);
            for(int i=0; i<5; i++)
                bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
            if(bookObject.getCapacity() != 11)
                fail("The hash table should be resized but it is not");
        }catch (Exception e) {
            fail("No exception is expected but other exception is thrown");
        }
        
    }
    
    /**
     * Test if get returns right pair of Book 
     */
    @Test
    public void test011_check_get_returns_right_value() {
        try {
            bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
            bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
            
            assertEquals(bookObject.get(bookTable.get(0).getKey()), bookTable.get(0));
            assertEquals(bookObject.get(bookTable.get(1).getKey()), bookTable.get(1));
        }catch (Exception e) {
            fail("No exception is expected but other exception is thrown");
        }
    }
    
}