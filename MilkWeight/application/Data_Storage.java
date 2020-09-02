//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Project Title: a3 ATEAM Project Milestone 3 GUI
// Files: MilkWeightManager.java / Data_Storage.java / ableComponent.java
// Semester: CS 400 Spring 2020
// Due Date: April 30, 2020
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

/**
 * This class stores data read from files in MilkWeightManager. Data read from
 * MilkWeightManager will be transferred to this class and this class stores
 * each farm as a node in its array. Storing system for this class is HashTable.
 * If there is same farm but has different data in year, it will have different
 * node. Therefore, each node represents the data of a farm in a a year.
 * 
 * @author Hyukjoon Yang, Jeijun Lee, Minseok Gang, Seungju Lee
 */
public class Data_Storage {

  /**
   * A node for each farm storing milk data
   * 
   * @author Hyukjoon Yang, Jeijun Lee, Minseok Gang, Seungju Lee
   */
  class Farm {

    Integer farmID; // ID number for farm
    int year; // Year of data
    int[][] data; // Array that stores weight in each day of each month
    Farm next; // Link to next farm

    /**
     * Default constructor that initializes Farm node
     * 
     * @param farmID is integer that identifies the node
     * @param year   is the year when the milk is delivered
     * @param month  is the month when the milk is delivered
     * @param day    is the day when the milk is delivered
     * @param weight is the amount of milk in the date
     */
    public Farm(Integer farmID, int year, int month, int day, int weight) {
      this.data = new int[12][31];
      this.farmID = farmID;
      this.year = year;
      data[month - 1][day - 1] = weight;
      next = null;
    }
  }

  private Farm[] table; // HashTable in array form
  private double loadFactor; // Loadfactor of the table
  private int numFarm; // Number of farms placed in table
  private int numData; // Number of node stored in table

  /**
   * Default constructor that initializes Data_Storage
   */
  public Data_Storage() {
    table = new Farm[11];
    loadFactor = 0.75;
    numFarm = 0;
    numData = 0;
  }

  /**
   * Adds a farm node into the Data_Storage. It uses chained bucket system to
   * add farm. If the load factor of this data structure exceeds 0.75, it calls
   * resize() method to rehash this data structure.
   * 
   * @param farmID is the unique ID of the farm
   * @param year   is the year when the milk is delivered
   * @param month  is the month when the milk is delivered
   * @param day    is the day when the milk is delivered
   * @param weight is the amount of milk delivered
   */
  protected void addFarm(Integer farmID, int year, int month, int day,
      int weight) {
    // Invalid parameter
    if (farmID < 0 || year < 0 || month < 0 || month > 12 || day < 1 || day > 31
        || weight < 0) {
      return;
    }

    // Check if a node with the farm ID is stored
    boolean exist = false;

    // Calculate hash index
    int hashcode = Math.abs(farmID.hashCode());
    int index = hashcode % table.length;

    // Case if no farm in the index exists
    if (table[index] == null) {
      table[index] = new Farm(farmID, year, month, day, weight);
    }

    // Case if a farm in the index exists
    else {
      Farm current = table[index];
      Farm previous = null;

      // Go through all farms in the index to find duplicate ID
      while (current != null) {

        // Add weight if a farm with same ID in same year exists
        if (current.farmID == farmID) {
          exist = true;
          if (current.year == year) {
            current.data[month - 1][day - 1] += weight;
            return;
          }
        }
        previous = current;
        current = current.next;
      }

      // Case if no farm with the ID exists
      previous.next = new Farm(farmID, year, month, day, weight);

    }
    if (!exist) {
      numFarm++;
    }
    numData++;
    resize();
  }

  /**
   * This method is called only when this data structure requires rehashing. It
   * adds existing farms in this node in the new hash index of newly resized
   * data structure.
   * 
   * If farm is null, return the method.
   * 
   * @param farm is the Farm node to be added into new data structure
   */
  private void addFarm(Farm farm) {
    // Invalid argument
    if (farm == null) {
      return;
    }
    // Calculate hash index
    int hashcode = Math.abs(farm.farmID.hashCode());
    int index = hashcode % table.length;

    // Case if no bucket in the index
    if (table[index] == null) {
      table[index] = farm;
    }

    // Case if a bucket exists in the index
    else {

      // Current farm
      Farm current = table[index];

      // Go to the end of bucket in the index
      while (current.next != null) {
        current = current.next;
      }

      current.next = farm;
    }

    // numFarm++;
    numData++;
  }

  /**
   * When this data structure requires rehashing, it creates newly rehashed
   * table and copies all farms stored in the previous table to the new data
   * structure by calculating new hash index for each farm.
   */
  private void resize() {
    // Condition for resize
    double check = (double) numData / (double) table.length;
    if (check >= 0.75) {

      Farm[] prevTable = table;

      // Resize the table
      table = new Farm[table.length * 2 + 1];
      numData = 0;

      // Current farm in the search
      Farm current;

      // Reassign each element from table to newTable
      for (int i = 0; i < prevTable.length; i++) {

        // Case if a bucket in the index exists
        if (prevTable[i] != null) {
          current = prevTable[i];

          // Reassign each element in the table[i]
          while (current != null) {
            // Next bucket in the index
            Farm next = current.next;

            // Reset link from the current
            current.next = null;

            // Add current into newTable and update current
            addFarm(current);
            current = next;
          }
        }
      }
    }
  }

  /**
   * This method provides Farm Report with associated data.
   * 
   * @param farmID is the ID of farm to be reported
   * @param year   is the year of data to be reported
   * @return report is 2D double array that stores data of the farm. Otherwise,
   *         null for invalid parameter
   */
  protected double[][] farmReport(Integer farmID, int year) {
    // Calculate hash index
    int hashcode = Math.abs(farmID.hashCode());
    int index = hashcode % table.length;
    int month = 1;
    double[][] report = new double[12][3];

    // Assign each month in each index of first row
    for (int i = 0; i < report.length; i++) {
      report[i][0] = month;
      month++;
    }

    // Case if no data in the index is stored
    if (table[index] == null) {
      return null;
    }

    // Case if there is a farm in the index exists
    else {
      Farm current = table[index];
      int monthCount = 0;
      int totalWeight = 0;

      // Go through all farms in the index
      while (current != null) {

        // Target
        if (current.farmID == farmID && current.year == year) {
          // Count by each month
          for (int i = 0; i < current.data.length; i++) {
            // Count by each day
            for (int j = 0; j < current.data[i].length; j++) {
              report[i][1] += current.data[i][j];
              totalWeight += current.data[i][j];

            }
          }
          // Calculate the percentage
          for (int i = 0; i < report.length; i++) {
            report[i][2] = Math.round((report[i][1] / totalWeight) * 10000);
            report[i][2] = report[i][2] / 100;
          }
          return report;
        }
        current = current.next;
      }
      return null;
    }
  }

  /**
   * This method gets all data of farms within the range of date. The data of
   * all farms is stored in a double 2D array. Each row of the array represents
   * the data of each farm. Data to be stored in the array is the farm ID,
   * weight, and the percentage of total weight.
   * 
   * @param year1  is the starting year
   * @param month1 is the starting month
   * @param day1   is the starting day
   * @param year2  is the starting year
   * @param month2 is the starting month
   * @param day2   is the starting day
   * @return report is a double 2D array that stores farm ID, weight, and
   *         percentage of all farms. Or null for any invalid parameter
   */
  protected double[][] rangeReport(int year1, int month1, int day1, int year2,
      int month2, int day2) {

    // Return null for invalid parameter
    if (year1 < 0 || year2 < 0 || month1 < 1 || month2 < 1 || day1 < 1
        || day2 < 1 || month1 > 12 || month2 > 12 || day1 > 31 || day2 > 31) {
      return null;
    }

    if (numFarm == 0) {
      return null;
    }

    double[][] report = new double[numFarm][3]; // Array to store data
    int totalWeight = 0; // Total weight of all farms
    int maxindex = 0; // Largest index of current report that a farm is
                      // stored
    int index = 0; // Index where a data of farm will be stored
    Farm current = null; // Current farm node
    boolean exist = false; // Check if current farm is already stored in the
                           // report

    // Go through all data of farms
    for (int i = 0; i < table.length; i++) {

      current = table[i];

      // Go through all farms in each index
      while (current != null) {

        // Check if the data of current farm is stored in report
        for (int k = 0; k < report.length; k++) {
          if (report[k][0] == current.farmID) {
            index = k;
            exist = true;
            break;
          }
        }

        // Check if the farm is within the range of year
        if (current.year >= year1 && current.year <= year2) {

          // Same year
          if (year1 == year2) {

            // Same month
            if (month1 == month2) {
              for (int m = day1 - 1; m < day2; m++) {
                report[index][1] += current.data[month1 - 1][m];
                totalWeight += current.data[month1 - 1][m];
              }
            }
            // Different month
            else {
              int currMonth = month1;

              // Calculate milk in starting month
              for (int day =
                  day1 - 1; day < current.data[month1 - 1].length; day++) {
                report[index][1] += current.data[month1 - 1][day];
                totalWeight += current.data[month1 - 1][day];
              }
              currMonth++;

              // Calculate milk in months between start month and
              // end month (Excluding)
              while (currMonth < month2) {
                for (int day =
                    0; day < current.data[currMonth - 1].length; day++) {
                  report[index][1] += current.data[currMonth - 1][day];
                  totalWeight += current.data[currMonth - 1][day];
                }
                currMonth++;
              }

              // Calculate milk in last month
              for (int day = 0; day < day2; day++) {
                report[index][1] += current.data[month2 - 1][day];
                totalWeight += current.data[month2 - 1][day];
              }
            }
          }
          // Different year
          else {

            // Starting year
            if (current.year == year1) {
              // Calculate milk in starting month
              for (int day =
                  day1 - 1; day < current.data[month1 - 1].length; day++) {
                report[index][1] += current.data[month1 - 1][day];
                totalWeight += current.data[month1 - 1][day];
              }

              // Calculate milk in the rest of monhts of starting
              // year
              for (int month = month1; month < current.data.length; month++) {
                for (int day = 0; day < current.data[month].length; day++) {
                  report[index][1] += current.data[month][day];
                  totalWeight += current.data[month][day];
                }
              }
            }
            // Calculate milk in years between year1 and year2
            // (Excluding)
            else if (current.year > year1 && current.year < year2) {
              for (int month = 0; month < current.data.length; month++) {
                for (int day = 0; day < current.data[month].length; day++) {
                  report[index][1] += current.data[month][day];
                  totalWeight += current.data[month][day];
                }
              }
            }
            // Ending year
            else {
              // Calculate milk until the month before the ending
              // month
              for (int month = 0; month < month2 - 1; month++) {
                for (int day = 0; day < current.data[month].length; day++) {
                  report[index][1] += current.data[month][day];
                  totalWeight += current.data[month][day];
                }
              }

              // Calculate milk in the ending month
              for (int day = 0; day < day2; day++) {
                report[index][1] += current.data[month2 - 1][day];
                totalWeight += current.data[month2 - 1][day];
              }
            }
          }
        }

        report[index][0] = current.farmID;
        current = current.next;

        // Calculate maxindex of data of farm in current report
        if (report[index][0] == 0 || !exist) {
          maxindex++;
        }
        index = maxindex;
        exist = false;
      }
    }
    // Calculate the percentage
    for (int i = 0; i < report.length; i++) {
      report[i][2] = Math.round((report[i][1] / totalWeight) * 10000);
      report[i][2] = report[i][2] / 100;
    }
    return report;
  }

  /**
   * This method sorts list that searchRange() method returns. It rearranges the
   * list by the farm ID number from lowest to greatest.
   * 
   * @param list is the list of data to be sorted
   * @return sorted is the sorted list by the farm ID. Otherwise, Null for
   *         invalid parameter
   */
  protected String[][] sortFarmWeight(double[][] list) {

    // Return null for invalid argument
    if (list == null) {
      return null;
    }
    // List to be returned
    String[][] sorted = new String[list.length][3];

    int lowest = -1; //
    int current = (int) list[0][0];
    int weight = (int) list[0][1];
    double percent = list[0][2];
    int index = 0;

    // Get the lowest farm ID
    for (int i = 0; i < list.length; i++) {
      if (current > list[i][0]) {
        current = (int) list[i][0];
        weight = (int) list[i][1];
        percent = list[i][2];
      }
    }

    lowest = current;

    // Assign appropriate data into the sorted list
    sorted[index][0] = "Farm " + current;
    sorted[index][1] = weight + "";
    sorted[index][2] = percent + "%";
    index++;

    // Sort the list from lowest farm ID to greatest
    while (index < sorted.length) {
      current = -1;
      // Get lowest of every iteration of the table
      for (int i = 0; i < list.length; i++) {
        // First iteration of the loop
        if (current == -1 && list[i][0] > lowest) {
          current = (int) list[i][0];
          weight = (int) list[i][1];
          percent = list[i][2];
        }
        // Get lowest in current iteration
        else if (list[i][0] < current && list[i][0] > lowest) {
          current = (int) list[i][0];
          weight = (int) list[i][1];
          percent = list[i][2];
        }
      }
      // Assign appropriate data into the sorted list
      sorted[index][0] = "Farm " + current + "";
      sorted[index][1] = weight + "";
      sorted[index][2] = percent + "%";

      // Update new lowest for next iteration
      lowest = current;
      index++;
    }

    return sorted;
  }
}
