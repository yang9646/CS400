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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;

//@SuppressWarnings("rawtypes")
/**
 * This class contains test code to test if BALST class is working
 * appropriately
 * Each test should not affect to each other
 * 
 * @author Ye Ji Kim
 *
 */
public class BALSTTest {

    // instance variables to run methods
    BALST<String,String> balst1;
    BALST<Integer,String> balst2;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        balst1 = createInstance();
        balst2 = createInstance2();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    void tearDown() throws Exception {
        balst1 = null;
        balst2 = null;
    }

    protected BALST<String, String> createInstance() {
        return new BALST<String,String>();
    }

    protected BALST<Integer,String> createInstance2() {
        return new BALST<Integer,String>();
    }

    /** 
     * Insert three values in sorted order and then check 
     * the root, left, and right keys to see if rebalancing 
     * occurred.
     */
    @Test
    void testBALST_001_insert_sorted_order_simple() {
        try {
            balst2.insert(10, "10");
            if (!balst2.getKeyAtRoot().equals(10)) 
                fail("avl insert at root does not work");
            
            balst2.insert(20, "20");
            if (!balst2.getKeyOfRightChildOf(10).equals(20)) 
                fail("avl insert to right child of root does not work");
            
            balst2.insert(30, "30");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) 
                fail("avl rotate does not work");
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), Integer.valueOf(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),Integer.valueOf(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),Integer.valueOf(30));
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }
    }

    /** 
     * Insert three values in reverse sorted order and then check 
     * the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_002_insert_reversed_sorted_order_simple() {
        try {            
            balst2.insert(30, "30");
            if (!balst2.getKeyAtRoot().equals(30)) 
                fail("avl insert at root does not work");
            
            balst2.insert(20, "20");
            if (!balst2.getKeyOfLeftChildOf(30).equals(20)) 
                fail("avl insert to left child of root does not work");
            
            balst2.insert(10, "10");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) 
                fail("avl rotate does not work");
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), Integer.valueOf(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),Integer.valueOf(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),Integer.valueOf(30));
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        } 
    }

    /** 
     * Insert three values so that a right-left rotation is
     * needed to fix the balance.
     * 
     * Example: 10-30-20
     * 
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_003_insert_smallest_largest_middle_order_simple() {
        try {            
            balst2.insert(10, "10");
            if (!balst2.getKeyAtRoot().equals(10)) 
                fail("avl insert at root does not work");
            
            balst2.insert(30, "30");
            if (!balst2.getKeyOfRightChildOf(10).equals(30)) 
                fail("avl insert to right child of root does not work");
            
            balst2.insert(20, "20");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) 
                fail("avl rotate does not work");
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(),Integer.valueOf(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),Integer.valueOf(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),Integer.valueOf(30));
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }

    /** 
     * Insert three values so that a left-right rotation is
     * needed to fix the balance.
     * 
     * Example: 30-10-20
     * 
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_004_insert_largest_smallest_middle_order_simple() {
        try {            
            balst2.insert(30, "30");
            if (!balst2.getKeyAtRoot().equals(30)) 
                fail("avl insert at root does not work");
            
            balst2.insert(10, "10");
            if (!balst2.getKeyOfLeftChildOf(30).equals(10)) 
                fail("avl insert to left child of root does not work");
            
            balst2.insert(20, "20");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) 
                fail("avl rotate does not work");
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), Integer.valueOf(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),Integer.valueOf(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),Integer.valueOf(30));

        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    

    /** 
     * Insert three values in sorted order and then check 
     * the root, left, and right keys to see if rebalancing 
     * occurred.
     */
    @Test
    void testBALST_005_insert_sorted_order_hard() {
        try {
            balst2.insert(10, "10");
            if (!balst2.getKeyAtRoot().equals(10)) 
                fail("avl insert at root does not work");
            
            balst2.insert(20, "20");
            if (!balst2.getKeyOfRightChildOf(10).equals(20)) 
                fail("avl insert to right child of root does not work");
            
            balst2.insert(30, "30");
            if (!balst2.getKeyOfRightChildOf(20).equals(30)) 
                fail("avl insert as binary tree not work");
            
            balst2.insert(40, "40");
            if (!balst2.getKeyOfRightChildOf(30).equals(40)) 
                fail("avl insert as binary tree not work");
            
            balst2.insert(50, "50");
            if (!balst2.getKeyOfRightChildOf(20).equals(40)) 
                fail("avl rotate does not work");
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 40 as its right child and 30 as 40's left

            Assert.assertEquals(balst2.getKeyAtRoot(), Integer.valueOf(20));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),Integer.valueOf(40));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(40),Integer.valueOf(30));
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }
    }

    /** 
     * Insert three values in reverse sorted order and then check 
     * the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_006_insert_reversed_sorted_order_hard() {
        try {            
            balst2.insert(50, "50");
            if (!balst2.getKeyAtRoot().equals(50)) 
                fail("avl insert at root does not work");
            
            balst2.insert(40, "40");
            if (!balst2.getKeyOfLeftChildOf(50).equals(40)) 
                fail("avl insert to right child of root does not work");
            
            balst2.insert(30, "30");
            if (!balst2.getKeyOfLeftChildOf(40).equals(30)) 
                fail("avl insert as binary tree not work");
            
            balst2.insert(20, "20");
            if (!balst2.getKeyOfLeftChildOf(30).equals(20)) 
                fail("avl insert as binary tree not work");
            
            balst2.insert(10, "10");
            if (!balst2.getKeyOfLeftChildOf(40).equals(20)) 
                fail("avl rotate does not work");
            
            // IF rebalancing is working,
            // the tree should have 40 at the root
            // and 20 as its left child and 30 as 20's right child

            Assert.assertEquals(balst2.getKeyAtRoot(), Integer.valueOf(40));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(40),Integer.valueOf(20));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),Integer.valueOf(30));
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        } 
    }

    /** 
     * Insert three values so that a right-left rotation is
     * needed to fix the balance.
     * 
     * Example: 50-40-10-30-20
     * 
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_007_insert_smallest_largest_middle_order_hard() {
        try {            
            balst2.insert(50, "50");
            balst2.insert(40, "40");
            balst2.insert(10, "10");
            balst2.insert(30, "30");
            balst2.insert(20, "20");

            if (!balst2.getKeyAtRoot().equals(40)) 
                fail("tree not storing as binary search tree");
            
            if (!balst2.getKeyOfLeftChildOf(40).equals(20)) 
                fail("AVL rotate is not working");
            
            if (!balst2.getKeyOfRightChildOf(20).equals(30)) 
                fail("AVL rotate is not working");
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), Integer.valueOf(40));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(40),Integer.valueOf(20));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),Integer.valueOf(30));

        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }

    /** 
     * Insert three values so that a left-right rotation is
     * needed to fix the balance.
     * 
     * Example: 10-20-50-30-40
     * 
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_008_insert_largest_smallest_middle_order_hard() {
        try {            
            balst2.insert(10, "10");
            balst2.insert(20, "20");
            balst2.insert(50, "50");
            balst2.insert(30, "30");
            balst2.insert(40, "40");

            if (!balst2.getKeyAtRoot().equals(20)) 
                fail("tree not storing as binary search tree");
            
            if (!balst2.getKeyOfRightChildOf(20).equals(40)) 
                fail("AVL rotate is not working");
            
            if (!balst2.getKeyOfRightChildOf(40).equals(50)) 
                fail("AVL rotate is not working");
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), Integer.valueOf(20));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),Integer.valueOf(40));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(40),Integer.valueOf(50));

        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }

    /** 
     * insert 100 keys and check if get method returns the right
     * value of key pair.
     */
    @Test
    void testBALST_009_insert_100_still_get_back() {
        try {            
            for(int i=0; i<100; i++) {
                balst2.insert(i, Integer.toString(i));
            }

            if (balst2.numKeys() != 100) 
                fail("tree not storing many keys");
            
            if (!balst2.get(50).equals("50")) 
                fail("avl get not working");
            
//            balst2.print();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if insert one key and remove one key is working
     */
    @Test
    void testBALST_010_insert_one_remove_one() {
        try {            
            balst1.insert("20", "20");
            balst1.remove("20");

            if (balst1.contains("20")) 
                fail("tree not removing the key");
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * insert 100 keys and remove all
     */
    @Test
    void testBALST_011_insert_100_remove_100() {
        try {            
            for(int i=0; i<100; i++) {
                balst2.insert(i, Integer.toString(i));
            }
            
            for(int i=0; i<100; i++) {
                balst2.remove(i);
            }

            if (balst2.numKeys() != 0) 
                fail("tree could not remove all nodes");
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if in-order traversal return the right arrayList
     */
    @Test
    void testBALST_012_check_inOrder_for_not_balanced_tree() {
        try {            
            balst2.insert(10, "10");
            balst2.insert(50, "50");
            balst2.insert(30, "30");
            balst2.insert(20, "20");
            balst2.insert(40, "40");

            List<Integer> expected = new ArrayList<Integer>();
            expected.add(10);
            expected.add(20);
            expected.add(30);
            expected.add(40);
            expected.add(50);
            
            List<Integer> actual = balst2.getInOrderTraversal();
            Assert.assertEquals(expected, actual);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if pre-order traversal return the right arrayList
     */
    @Test
    void testBALST_013_check_preOrder_for_not_balanced_tree() {
        try {            
            balst2.insert(10, "10");
            balst2.insert(50, "50");
            balst2.insert(30, "30");
            balst2.insert(20, "20");
            balst2.insert(40, "40");

            List<Integer> expected = new ArrayList<Integer>();
            expected.add(30);
            expected.add(10);
            expected.add(20);
            expected.add(50);
            expected.add(40);
            
            List<Integer> actual = balst2.getPreOrderTraversal();
            Assert.assertEquals(expected, actual);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if in-order traversal return the right arrayList
     */
    @Test
    void testBALST_014_check_postOrder_for_not_balanced_tree() {
        try {            
            balst2.insert(10, "10");
            balst2.insert(50, "50");
            balst2.insert(30, "30");
            balst2.insert(20, "20");
            balst2.insert(40, "40");

            List<Integer> expected = new ArrayList<Integer>();
            expected.add(20);
            expected.add(10);
            expected.add(40);
            expected.add(50);
            expected.add(30);
            
            List<Integer> actual = balst2.getPostOrderTraversal();
            Assert.assertEquals(expected, actual);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if height and balance factor of the tree reflect it's actual height
     */
    @Test
    void testBALST_015_check_heigh_for_not_balanced_tree() {
        try {            
            balst2.insert(10, "10");
            balst2.insert(50, "50");
            balst2.insert(30, "30");
            balst2.insert(20, "20");
            balst2.insert(40, "40");
            
            List<Integer> expected = new ArrayList<Integer>();
            expected.add(30);
            expected.add(10);
            expected.add(50);
            expected.add(20);
            expected.add(40);
            
            if(balst2.getHeight() != 3)
                fail("the expected tree height is 3, but it is " + balst2.getHeight());
            
            List<Integer> actual = balst2.getLevelOrderTraversal();
            Assert.assertEquals(expected, actual);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if removing null key throws IllegalNullKeyException
     * and do not decrease the size
     */
    @Test
    void testBALST_016_remove_null_key_do_not_decrease_the_size_of_numKeys() {
        try {            
            balst2.insert(10, "10");
            balst2.insert(50, "50");
            balst2.insert(30, "30");
            balst2.insert(20, "20");
            balst2.insert(40, "40");
          
            if(balst2.numKeys() != 5)
                fail("5 node have been inserted, but it has " + balst2.numKeys());
            
            balst2.remove(null); // remove null key
            
            fail("IllegalArgumentException is expected, but no Exceptions are thrown.");
            
        } catch (IllegalNullKeyException e) {
            if(balst2.numKeys() != 5)
                fail("the nodes should remain same, but it has " + balst2.numKeys());
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if removing the key that is not on the tree will 
     * throw KeyNotFoundException and check if numKeys decreased
     */
    @Test
    void testBALST_017_remove_key_not_on_the_tree() {
        try {            
            balst2.insert(10, "10");
            balst2.insert(50, "50");
            balst2.insert(30, "30");
            balst2.insert(20, "20");
            balst2.insert(40, "40");
          
            if(balst2.numKeys() != 5)
                fail("5 node have been inserted, but it has " + balst2.numKeys());
            
            balst2.remove(100); // remove 100 - not on tree
            
            fail("KeyNotFoundException is expected, but no Exceptions are thrown.");
            
        } catch (KeyNotFoundException e) {
            if(balst2.numKeys() != 5)
                fail("the nodes should remain same, but it has " + balst2.numKeys());
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if inserting null key throws IllegalNullKeyException
     * and do not increase the size
     */
    @Test
    void testBALST_018_insert_null_key_not_increase_size() {
        try {            
            balst2.insert(10, "10");
            balst2.insert(50, "50");
            balst2.insert(30, "30");
            balst2.insert(20, "20");
            balst2.insert(40, "40");
          
            if(balst2.numKeys() != 5)
                fail("5 node have been inserted, but it has " + balst2.numKeys());
            
            balst2.insert(null,"11"); // insert null key
            
            fail("IllegalArgumentException is expected, but no Exceptions are thrown.");
            
        } catch (IllegalNullKeyException e) {
            if(balst2.numKeys() != 5)
                fail("the nodes should remain same, but it has " + balst2.numKeys());
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if inserting duplicated key throws DuplicateKeyException
     * and do not increase the size
     */
    @Test
    void testBALST_019_insert_duplicated_key_not_increase_size() {
        try {            
            balst2.insert(10, "10");
            balst2.insert(50, "50");
            balst2.insert(30, "30");
            balst2.insert(20, "20");
            balst2.insert(40, "40");
          
            if(balst2.numKeys() != 5)
                fail("5 node have been inserted, but it has " + balst2.numKeys());
            
            balst2.insert(10,"11"); // insert duplicated key
            
            fail("DuplicateKeyException is expected, but no Exceptions are thrown.");
            
        } catch (DuplicateKeyException e) {
            if(balst2.numKeys() != 5)
                fail("the nodes should remain same, but it has " + balst2.numKeys());
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if inserting null key throws IllegalNullKeyException
     * and do not increase the size
     */
    @Test
    void testBALST_020_contains_null_key_throw_exception() {
        try {            
            balst2.insert(10, "10");
            balst2.insert(50, "50");
            balst2.insert(30, "30");
            balst2.insert(20, "20");
            balst2.insert(40, "40");
          
            if(balst2.numKeys() != 5)
                fail("5 node have been inserted, but it has " + balst2.numKeys());
            
            balst2.contains(null); // call contains method with null key
            
            fail("IllegalArgumentException is expected, but no Exceptions are thrown.");
            
        } catch (IllegalNullKeyException e) {
            if(balst2.numKeys() != 5)
                fail("the nodes should remain same, but it has " + balst2.numKeys());
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if calling remove actually remove the node
     */
    @Test
    void testBALST_021_remove_work_appropriately() {
        try {            
            balst2.insert(10, "10");
            balst2.insert(50, "50");
            balst2.insert(30, "30");
            balst2.insert(20, "20");
            balst2.insert(40, "40");
          
            balst2.remove(30); // remove root node
            
            // new root will be 40
            Assert.assertEquals(balst2.getKeyAtRoot(), Integer.valueOf(40));

            if(balst2.numKeys() != 4)
                fail("size should be decreased, but " + balst2.numKeys());
            
        } catch (IllegalNullKeyException e) {
            if(balst2.numKeys() != 5)
                fail("the nodes should remain same, but it has " + balst2.numKeys());
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
    /** 
     * check if in-order traversal return the right arrayList for String keys
     */
    @Test
    void testBALST_012_check_inOrder_for_String_tree() {
        try {            
            balst1.insert("10", "10");
            balst1.insert("50", "10");
            balst1.insert("30", "10");
            balst1.insert("20", "10");
            balst1.insert("40", "10");
            
            List<String> expected = new ArrayList<String>();
            expected.add("10");
            expected.add("20");
            expected.add("30");
            expected.add("40");
            expected.add("50");
            
            List<String> actual = balst1.getInOrderTraversal();
            Assert.assertEquals(expected, actual);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }         
    }
    
}
