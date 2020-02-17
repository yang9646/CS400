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

import static org.junit.jupiter.api.Assertions.*;
import java.lang.RuntimeException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class which tests DS_<name> classes
 * Each test should not afftect to each other
 * 
 * @author Ye Ji Kim
 *
 * @param <T> the key and value must me String
 */
abstract class DataStructureADTTest<T extends DataStructureADT<String, String>> {

    private T dataStructureInstance; //instance variable to run methods

    protected abstract T createInstance();

    // initialize it again for each test
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        dataStructureInstance = createInstance();
    }

    @AfterEach
    void tearDown() throws Exception {
        dataStructureInstance = null;
    }

    //Testing codes begin
    @Test
    void test00_empty_ds_size() {
        // should be empty in the beginning
        if (dataStructureInstance.size() != 0)
            fail("data structure should be empty, with size=0, but size="
                    + dataStructureInstance.size());
    }

    @Test
    void test01_after_insert_one_size_is_one() {
        String key = "key";
        String value = "value";
        dataStructureInstance.insert(key, value); //insert
        if (dataStructureInstance.size() != 1)
            fail("data structure should not be empty, with size=1, but size="
                    + dataStructureInstance.size());
    }

    @Test
    void test02_after_insert_one_remove_one_size_is_0() {
        String key = "key";
        String value = "value";
        dataStructureInstance.insert(key, value); //insert
        dataStructureInstance.remove(key); //remove
        if (dataStructureInstance.size() != 0)
            fail("data structure should be empty, with size=0, but size="
                    + dataStructureInstance.size());
    }

    @Test
    void test03_duplicate_exception_is_thrown() {
        String key1 = "key1";
        String key2 = "key2";
        String value = "value";
        dataStructureInstance.insert(key1, value);
        dataStructureInstance.insert(key2, value);
        try {
            dataStructureInstance.insert(key1, value); //duplicate
            fail("RuntimeException should be thrown when inserting duplicated "
                    + "keys, but no Exceptions are thrown.");
        } catch (RuntimeException e) {
        } catch (Exception e) {
            fail("RuntimeException should be thrown when inserting duplicated "
                    + "keys, but other Exception "+e.toString()+" is thrown.");
        }
    }

    @Test
    void test04_remove_returns_false_when_key_not_present() {
        String noKey = "noKey";
        String key = "key";
        String value = "value";
        dataStructureInstance.insert(key, value);
        if (dataStructureInstance.remove(noKey)) //remove key not on the list
            fail("remove should return false when key is not present, but "
                    + "returns " + dataStructureInstance.remove(noKey));
    }

    @Test
    void test05_after_insert_one_remove_one_contains_returns_false() {
        String key = "key";
        String value = "value";
        dataStructureInstance.insert(key, value);
        dataStructureInstance.remove(key);
        if (dataStructureInstance.contains(key)) //check if key is on the list
            fail("contains should returns false after key is removed, but "
                    + "returns " + dataStructureInstance.contains(key));
    }

    @Test
    void test06_illegalArgumentException_is_thrown_on_insert() {
        String key = null;
        String value = "value";
        try {
            dataStructureInstance.insert(key, value); //inserting null key
            fail("IllegalArgumentException should be thrown when inserting a "
                    + "null key, but no Exceptions are thrown.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("IllegalArgumentException should be thrown when inserting a "
                    + "null key, but "+ e.toString() +" is thrown.");
        }
    }

    @Test
    void test07_illegalArgumentException_is_thrown_on_remove() {
        String key = "key";
        String value = "value";
        dataStructureInstance.insert(key, value);
        try {
            dataStructureInstance.remove(null); //removing null key
            fail("IllegalArgumentException should be thrown when removing a null"
                    + " key, but no Exceptions are thrown.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("IllegalArgumentException should be thrown when removing a "
                    + "null keys, but " + e.toString() + " is thrown.");
        }
    }

    @Test
    void test08_after_insert_one_get_returns_value() {
        String key = "key";
        String value = "value";
        dataStructureInstance.insert(key, value);
        //check if the key and value is right
        if (!dataStructureInstance.get(key).equals(value))
            fail("get method after inserting one should be equals to the value, "
                    + "but it is" + dataStructureInstance.get(key));
    }

    @Test
    void test09_accept_null_value() {
        String key = "key";
        String value = null;
        try {
            dataStructureInstance.insert(key, value); //insert null value
        } catch (Exception e) {
            fail("insert method should accept and insert null values, "
                    + "but it throws" + e.toString());
        }
    }

    @Test
    void test10_remove_null_key_do_not_decrease_the_size() {
        String key = "key";
        String value = "value";
        dataStructureInstance.insert(key, value);
        try {
            dataStructureInstance.remove(null);
        } catch (IllegalArgumentException e) {
            // check if size remain same
            if (dataStructureInstance.size() != 1) {
                fail("size should not be decreasesd by calling remove with null"
                        + " key, but size is " + dataStructureInstance.size());
            }
        }
    }

    @Test
    void test11_illegalArgumentException_is_thrown_on_get() {
        String key = "key";
        String value = "value";
        dataStructureInstance.insert(key, value);
        try {
            dataStructureInstance.get(null); //get null key
            fail("IllegalArgumentException should be thrown when getting a null "
                    + "key, but no Exceptions are thrown.");
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail("IllegalArgumentException should be thrown when getting a null "
                    + "keys, but  Exception " + e.toString() + " is thrown.");
        }
    }

    @Test
    void test12_get_returns_value_associated_with_specified_key() {
        String key1 = "key1";
        String value1 = "value1";
        String key2 = "key2";
        String value2 = "value2";
        dataStructureInstance.insert(key1, value1);
        dataStructureInstance.insert(key2, value2);
        // check if the value is same value of the passed key
        if (!dataStructureInstance.get(key1).equals(value1)) {
            fail("get should return the value that is assoicated with speified "
                    + "key, but it returns " + dataStructureInstance.get(key1));
        }
    }

    @Test
    void test13_contains_return_true_after_get() {
        String key = "key1";
        String value = "value1";
        dataStructureInstance.insert(key, value);
        dataStructureInstance.get(key);
        // check if get do not remove the key
        if (!dataStructureInstance.contains(key)) {
            fail("get should not remove the key, but contains return "
                    + dataStructureInstance.contains(key));
        }
    }

    @Test
    void test13_size_not_change_after_get() {
        String key = "key1";
        String value = "value1";
        dataStructureInstance.insert(key, value);
        dataStructureInstance.get(key);
        //check if get does not change the size
        if (dataStructureInstance.size() != 1) {
            fail("get does not remove the key so the size should be 1, but size="
                    + dataStructureInstance.size());
        }
    }

    @Test
    void test14_contains_return_false_when_key_is_null() {
        String nullkey = null;
        String key = "key1";
        String value = "value1";
        dataStructureInstance.insert(key, value);
        //contains do not throw Exceptions, just true and false
        if (dataStructureInstance.contains(nullkey)) {
            fail("contains should return false when key is null, but it returns "
                    + dataStructureInstance.contains(nullkey));
        }
    }

    @Test
    void test15_insert_500_items_and_remove_all() {
        try {
            //inserting 500 items
            for (int i = 0; i < 500; i++) {
                dataStructureInstance.insert(Integer.toString(i), "value");
            }
            //deleting 500 items
            for (int i = 0; i < 500; i++) {
                dataStructureInstance.remove(Integer.toString(i));
            }
            // check the size
            if (dataStructureInstance.size() != 0) {
                fail("size should be 0, but " + dataStructureInstance.size());
            }
        } catch (Exception e) {
            fail("should be able to insert and remove 500 items, but "
                    + e.toString() + " is thrown");
        }
    }

    @Test
    void test16_insert_500_items_and_check_the_size() {
        try {
            //inserting 500 items
            for (int i = 0; i < 500; i++) {
                dataStructureInstance.insert(Integer.toString(i), "value");
            }
            // check the size of list
            if (dataStructureInstance.size() != 500) {
                fail("size should be 500 after inserting 500 items, but it is "
                        + dataStructureInstance.size());
            }
        } catch (Exception e) {
            fail("should be able to insert and remove 500 items, but "
                    + e.toString() + " is thrown");
        }
    }

    @Test
    void test17_duplicate_exception_thrown_even_when_it_is_far() {
        try {
            //insert 500 items
            for (int i = 0; i < 500; i++) {
                dataStructureInstance.insert(Integer.toString(i), "value");
            }
            //insert duplicated key but far away 
            dataStructureInstance.insert(Integer.toString(3), "dup");
            fail("RuntimeException should be thrown when inserting duplicated"
                    + " keys, but no Exceptions are thrown.");
        } catch (RuntimeException e) { // pass
        } catch (Exception e) {
            fail("RuntimeException should be thrown when inserting a duplicated"
                    + " keys, but Exception " + e.toString() + " is thrown.");
        }
    }

    @Test
    void test18_key_can_be_re_added_after_removed() {
        try {
            String key = "key";
            dataStructureInstance.insert(key, "value"); // insert 1
            for (int i = 0; i < 400; i++) { //insert 400 items
                dataStructureInstance.insert(Integer.toString(i), "value");
            }
            dataStructureInstance.remove(key); // remove 1
            // check if the list still have the removed key
            if (dataStructureInstance.contains(key)) {
                fail("should not contain the key that is just removed");
            }
            // insert key that is just removed(this is not duplicated)
            dataStructureInstance.insert(key, "value");
        } catch (Exception e) {
            fail("No Exception should be thrown when inserting a key that is"
                    + " removed, but " + e.toString() + " is thrown.");
        }
    }

}
