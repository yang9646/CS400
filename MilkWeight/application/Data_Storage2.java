//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Project Title: a2 ATEAM Project Milestone 2 GUI
// Files: MilkWeightManager.java / Data_Storage.java
// Semester: CS 400 Spring 2020
// Due Date: April 21, 2020
//
// Author: Hyukjoon Yang, Jeijun Lee, Minseok Gang, Seungju Lee
// Email: hyang348@wisc.ede, lee783@wisc.edu, gang3@wisc.edu, slee887@wisc.edu
// CS Login: hyukjoon, jeijun, minseok, seungju
// Lecturer's Name: Debra Deppeler
// Lecture Section: lec 001
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: None
// Online Sources: None
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import java.util.ArrayList;

/**
 * This class stores data read from files for Milk Weight. Data read from MilkWeightManager will be
 * transferred to this class and this class stores each farm as a node in its array. Storing system
 * for this class is HashTable.
 * 
 * @author Hyukjoon Yang, Jeijun Lee, Minseok Gang, Seungjoo Lee
 */
public class Data_Storage2 {

  /**
   * A node for each farm storing milk data
   * 
   * @author Hyukjoon Yang, Jeijun Lee, Minseok Gang, Seungjoo Lee
   */
  private class Farm {

    String farmID; // ID of farm
    int[] record; // Record of milk weighti in each day
    Farm next; // Pointer to next Farm

    /**
     * Default constructor that initializes Farm node
     * 
     * @param farmID is String representing the ID
     * @param day    is the day which weight is stored
     * @param weight is the amount of milk in the day
     */
    private Farm(String farmID, int day, int weight) {
      this.farmID = farmID;
      record = new int[31];
      next = null;
    }
  }

  private Farm[] table; // Stores Farm while each index represents a month
  private int numFarm; // Number of different farms stored in this storage
  private ArrayList<String> list; // ArrayList that stores all farm ID stored in this storage

  /**
   * Default constructor that initializes Data_Storage
   */
  public Data_Storage2() {
    table = new Farm[12];
    list = new ArrayList<>();
    numFarm = 0;
  }

  /**
   * 
   * @param farmID
   * @param month
   * @param day
   * @param weight
   */
  protected void addFarm(String farmID, int month, int day, int weight) {

    // Case for invalid argument
    if (farmID == null || month < 1 || month > 12 || day < 1 || day > 31 || weight < 0) {
      return;
    }

    // Check if any data of the farm is stored in this storage
    boolean contains = false;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).equalsIgnoreCase(farmID)) {
        contains = true;
        break;
      }
    }

    if (!contains) {
      numFarm++;
    }

    int index = month - 1; // Index of table that the data for the month is stored
    // Case if no data in the month is stored
    if (table[index] == null) {
      table[index] = new Farm(farmID, day, weight);
    }
    // Case if a data exists in the month
    else {
      Farm current = table[index];
      Farm previous = null;

      // Go through all data in the month
      while (current != null) {

        // Case if data of the farm exists
        if (current.farmID.equalsIgnoreCase(farmID)) {
          current.record[day - 1] += weight;
          return;
        }

        previous = current;
        current = current.next;
      }

      // Case if no data of the farm exists
      previous.next = new Farm(farmID, day, weight);
    }
  }

  /**
   * 
   * @param month1
   * @param month2
   * @return
   */
  protected String[][] searchRange(int month1, int month2) {

    // Return null for invalid argument
    if (month1 < 0 || month1 > 12 || month2 < 0 || month2 > 12) {
      return null;
    }

    // Array to be returned
    // String[][] report = new String[numFarm][2];
    int[] milk = new int[numFarm];
    Farm current = null;

    // Monthly report
    if (month1 == month2) {

      // Get data for all farms
      for (int i = 0; i < list.size(); i++) {
        String farmID = list.get(i);
        current = table[month1 - 1];

        // Get data of each farm in the month
        while (current != null) {
          if (current.farmID.equalsIgnoreCase(farmID)) {
            milk[i] += countWeight(current);
            break;
          }
          current = current.next;
        }
      }
    } else {
      
      // Repeat for the range of months
      for (int i = month1 - 1; i < month2; i++) {
        // Get data for all farms
        for (int j = 0; j < list.size(); j++) {
          String farmID = list.get(j);
          current = table[i];
          
          // Get data of each farm in each month
          while (current != null) {
            if (current.farmID.equalsIgnoreCase(farmID)) {
              milk[i] += countWeight(current);
              break;
            }
            current = current.next;
          }
        }
      }
    }
    
    // Make 2D array of String
    // First index stores FarmID stored in list (ArrayList)
    // Second index stores milk weight stored in milk report
    // Index of list and milk is corresponding with each other
    for (int i = 0; i < list.size(); i++) {
      
    }

    return null;
  }

  private int countWeight(Farm current) {
    int count = 0;
    for (int i = 0; i < current.record.length; i++) {
      count += current.record[i];
    }
    return count;
  }
}
