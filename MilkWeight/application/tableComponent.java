//////////////////// ALL ASSIGNMENTS INCLUDE THIS alphabet /////////////////////
//
// Project Title: a3 ATEAM Project Milestone 3 GUI
// Files: MilkWeightManager.java / Data_Storage.java / TableComponent.java
// Semester: CS 400 Spring 2020
// Due Date: April 30, 2020
//
// Author: Hyukjoon Yang, Jeijun Lee, Minseok Gang, Seungju Lee
// Email: hyang348@wisc.ede, lee783@wisc.edu, gang3@wisc.edu, slee887@wisc.edu
// CS Login: hyukjoon, jeijun, minseok, seungju
// Lecturer's Name: Debra Deppeler
// Lecture section: lec 001
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
 * This class helps to set a table in MilkWeightManager class.
 * 
 * @author Hyukjoon Yang, Jeijun Lee, Minseok Gang, Seungju Lee
 */
public class TableComponent {
  private String col1;
  private String weight;
  private String percent;

  /**
   * This method is a constructor that is creating new table component with
   * given inputs which are col1, weight, and percent.
   * 
   * @param col1    a column to put in the table
   * @param weight  a weight to put in the table
   * @param percent a percentage to put in the table
   */
  public TableComponent(String col1, String weight, String percent) {
    this.col1 = col1;
    this.weight = weight;
    this.percent = percent;
  }

  /**
   * This method returns the column in the table.
   *
   * @return col1 a column to put in the table
   */
  public String getCol1() {
    return col1;
  }

  /**
   * This method returns the weight in the table.
   *
   * @return weight to put in the table
   */
  public String getWeight() {
    return weight;
  }

  /**
   * This method returns the percentage in the table.
   *
   * @return percent to put in the table
   */
  public String getPercent() {
    return percent;
  }
}
