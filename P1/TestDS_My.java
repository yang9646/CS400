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
//
// TO TEST A DATA STRUCTURE CLASS:
//
// for each data structure class file you wish to test:
//     1. create a test class (like this one) 
//     2. edit the actual type being created (line 16)
//     3. run this test class 
//     4. OR, configure Eclipse project to run all tests
//        Eclipse: Run->Run Configurations->"Run All Tests..."

/**
 * This is the class to call JUnit test classes
 * 
 * @author Ye Ji Kim
 */
@SuppressWarnings("rawtypes")
public class TestDS_My extends DataStructureADTTest {

    // the return type must be the name of the data structure class you are testing
    @Override
    protected DataStructureADT createInstance() {
        return new DS_My();
    }

}
