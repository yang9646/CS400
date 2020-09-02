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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class implements JavaFX to make GUI file, where the user can interact
 * with the program or Chalet Cheese Factory.
 * 
 * @author Hyukjoon Yang, Jeijun Lee, Minseok Gang, Seungju Lee
 */
public class MilkWeightManager extends Application {

  // Fields for the height and Width of the window, and title of my JavaFX
  // program
  private static final int WINDOW_WIDTH = 300;
  private static final int WINDOW_HEIGHT = 200;
  private static final String APP_TITLE =
      "Milk Weights for the\nChalet " + "Cheese factory";
  // declare to call dataStorage, primary Stage, and primary Root
  private Data_Storage dataStorage;
  private static Stage pStage;
  private static BorderPane pRoot;

  /**
   * This method reads csv files from its working directory. Every line read
   * from the file will be distinguished into date. farm and weight. Then it
   * calls addFarm() of Data_storage class to make Data_storage to store the
   * data of the file.
   * 
   * @param fileName is the name of file to read
   */
  private void readFile(String fileName) {
    // Terminate for invalid name format
    if (fileName == null) {
      return;
    }

    try {
      // Read file
      FileReader fr = new FileReader(fileName);
      BufferedReader br = new BufferedReader(fr);

      // Read each line in the file
      String line = br.readLine();
      while (line != null) {
        String[] eachLine = line.split(",");
        int[] sorted = sortLine(eachLine);
        if (sorted != null) {
          dataStorage.addFarm(sorted[3], sorted[0], sorted[1], sorted[2],
              sorted[4]);
        }
        line = br.readLine();
      }
    } catch (IOException e) {
      System.out.println("Cannot find the file");
      return;
    }
  }

  /**
   * This method is helper method for readFile(). It will distinguish month from
   * date, ID from farm and milk weight stored in the parameter. If the
   * parameter is invalid format, it will return null. Otherwise, it will
   * returns filtered data in an array format.
   * 
   * @param line is an array of String that contains date, farm and milk weight
   * @return sorted is an array of int type that stores month of date, farm ID
   *         and weight in different index. Otherwise, returns null for invalid
   *         parameter.
   */
  private int[] sortLine(String[] line) {

    // Invalid line
    if (line.length != 3) {
      return null;
    }

    // List to be returned
    int[] sorted = new int[5];

    try {
      // Get month
      String[] date = line[0].split("-");
      if (date.length != 3) {
        return null;
      }

      // Convert month from date array into int type
      String yearStr = date[0];
      String monthStr = date[1];
      String dayStr = date[2];

      int year = Integer.parseInt(yearStr);
      int month = Integer.parseInt(monthStr);
      int day = Integer.parseInt(dayStr);

      // Case for Invalid month
      if (year < 0 || month < 0 || month > 12 || day < 1 || day > 31) {
        return null;
      }
      sorted[0] = year;
      sorted[1] = month;
      sorted[2] = day;

      // Get farm ID
      String[] farm = line[1].split(" ");
      if (farm.length != 2) {
        return null;
      }
      int farmID = Integer.parseInt(farm[1]);
      sorted[3] = farmID;

      // Get Weight
      int weight = Integer.parseInt(line[2]);
      sorted[4] = weight;

      return sorted;
    }
    // Case for invalid format
    catch (Exception e) {
      return null;
    }
  }

  /**
   * This method formats and styles the Label.
   * 
   * @param root - The passed in border pane
   * 
   * @return userLabel updated Label
   */
  private Label getTopLabel(BorderPane pRoot) {
    Label userLabel = new Label(APP_TITLE);
    userLabel.setFont(new Font(20));
    userLabel.prefWidthProperty().bind(pRoot.widthProperty());
    userLabel.setAlignment(Pos.TOP_CENTER);
    return userLabel;
  }

  /**
   * This method is an accessor method to get the primary stage.
   * 
   * @return pStage a primary Stage
   */
  private static Stage getPrimaryStage() {
    return pStage;
  }

  /**
   * This method is a mutator method to set the primary stage.
   * 
   * @param pStage a primary Stage to update
   */
  private void setPrimaryStage(Stage pStage) {
    MilkWeightManager.pStage = pStage;
  }

  /**
   * This method is an accessor method to get the primary BorderPane.
   * 
   * @return pRoot a primary BorderPane
   */
  private static BorderPane getPrimaryBorderPane() {
    return pRoot;
  }

  /**
   * This method is a mutator method to set the primary BorderPane.
   * 
   * @param pRoot a primary BorderPane to update
   */
  private void setPrimaryBorderPane(BorderPane pRoot) {
    MilkWeightManager.pRoot = pRoot;
  }

  /**
   * This method is a helper method to display and set all the related functions
   * when the button1 is clicked in primary Stage.
   */
  private void FarmReport() {
    // create a new BorderPane
    BorderPane root2 = new BorderPane();
    // create a new grid
    GridPane grid = new GridPane();
    // create a horizontal box
    HBox horizontalMenu1 = new HBox();
    HBox horizontalMenu2 = new HBox();
    // create a vertical box
    VBox verticalMenu1 = new VBox();
    // create labels and sets the grid point
    Label farmID = new Label("Farm ID:");
    Label enterYear = new Label("Year:");
    grid.add(farmID, 0, 1);
    grid.add(enterYear, 1, 1);

    // create TextAreas and textfields to instruct a user
    final TextArea farm = new TextArea();
    TextField farm_info = new TextField();
    farm_info.setText("Please enter the Farm ID");
    final TextArea year = new TextArea();
    TextField year_info = new TextField();
    year_info.setText("Please enter a valid year");

    // create a button to submit
    Button button = new Button("Submit");
    // create and forms characteristics of a button to go back to the
    // previous window
    Button backButton = new Button("Back");
    backButton.setPrefHeight(30);
    backButton.prefWidthProperty().bind(pRoot.widthProperty());

    // add elements to HBox
    horizontalMenu1.getChildren().addAll(farmID, farm_info);
    horizontalMenu2.getChildren().addAll(enterYear, year_info, button);

    // add elements to VBox
    verticalMenu1.getChildren().addAll(horizontalMenu1, horizontalMenu2);
    // Calls the methods to get the Top, Center, Bottom components with
    // specified variables
    root2.setTop(getTopLabel(pRoot));
    root2.setCenter(verticalMenu1);
    root2.setBottom(backButton);

    // create a scene and a stage to display
    Scene secondScene = new Scene(root2, WINDOW_WIDTH, WINDOW_HEIGHT);
    Stage secondStage = new Stage();

    // set the title and scene on the window, and display the components
    secondStage.setTitle(APP_TITLE);
    secondStage.setScene(secondScene);

    // when submit button is clicked
    button.setOnAction(submit -> {

      try {
        // user input from the text field, indicating Farm ID
        int userInput = Integer.parseInt(farm_info.getText());
        // user input from the text field, indicating year
        int yearInput = Integer.parseInt(year_info.getText());

        // check whether the year input is valid
        // if the input is null or a negative number, prompt an alert
        // message
        if (year_info.getText() == null || yearInput < 0) {
          Alert alert = new Alert(AlertType.WARNING,
              "Please enter a valid year format (positive number!).");
          alert.setHeaderText("Error occured while generating report!");
          alert.showAndWait();
          return;
        }

        // 2D array saving the farm ID and its weight
        double[][] list = dataStorage.farmReport(userInput, yearInput);

        // Create new window panes
        BorderPane resultContent = new BorderPane();
        ScrollPane result = new ScrollPane();

        // Alert if list is null (no data found)
        if (list == null) {
          Alert alert = new Alert(AlertType.WARNING,
              "Please enter a valid year! (No data of the farm is stored)");
          alert.setHeaderText("Error occured while generating report!");
          alert.showAndWait();
          return;
        } else {
          // Create table view
          TableView table = new TableView();

          // Create columns for table
          TableColumn monthCol = new TableColumn("Month");
          TableColumn milkWeight = new TableColumn("Weight");
          TableColumn percentage = new TableColumn("% of total weight");

          // Create collection for row data
          ObservableList<TableComponent> componentList =
              FXCollections.observableArrayList();

          // Add data for each month
          for (int i = 0; i < list.length; i++) {
            componentList.add(new TableComponent((int) list[i][0] + "",
                list[i][1] + "", list[i][2] + ""));
          }

          // col1 is the month
          monthCol.setCellValueFactory(new PropertyValueFactory<>("col1"));
          milkWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
          percentage.setCellValueFactory(new PropertyValueFactory<>("percent"));

          // Link list(rows) to table
          table.setItems(componentList);

          // add columns
          table.getColumns().addAll(monthCol, milkWeight, percentage);
          table.setEditable(false);

          // Creating a string for save file
          String listText = "";
          for (int i = 0; i < list.length; i++) {
            listText += "Month " + (int) list[i][0] + " : " + list[i][1] + ", "
                + list[i][2] + "%";
            if ((int) list[i][0] < 12) {
              listText += "\n";
            }
          }

          // creating a horizontal box for save buttons
          HBox hbox = saveGUI(listText);

          // locating table
          resultContent.setCenter(table);

          // locating the box
          resultContent.setBottom(hbox);

          // Creating Scrollable Window
          result.setContent(resultContent);

          // put on show
          Scene resultScene = new Scene(result, WINDOW_WIDTH, 430);
          Stage resultStage = new Stage();

          resultStage.setTitle("Milk Weights for Farm " + userInput);
          resultStage
              .setX(getPrimaryStage().getX() + getPrimaryStage().getWidth());
          resultStage.setY(getPrimaryStage().getY());
          resultStage.setScene(resultScene);
          resultStage.show();
        }

      }
      // catch whether the input is invalid and prompt an alert message
      catch (NumberFormatException e) {
        Alert alert = new Alert(AlertType.WARNING,
            "Please enter a vaild Farm ID or year number!");
        alert.setHeaderText("Error occured while generating report!");
        alert.showAndWait();
        return;
      }
    });

    // when back button is clicked, go back to the previous window and
    // close the current window
    backButton.setOnAction(e1 -> {
      getPrimaryStage().show();
      secondStage.close();
    });
    // show the secondStage and close the primary Stage
    secondStage.show();
    getPrimaryStage().close();
  }

  /**
   * This method is a helper method to display and set all the related functions
   * when the button2 is clicked in primary Stage.
   */
  public void MonthlyReport() {
    // create a new BorderPane to display
    BorderPane root2 = new BorderPane();
    // create a grid to locate elements of second Border Pane
    GridPane grid = new GridPane();
    // create a vertical box to arrange elements of second Border Pane
    VBox centerMenu = new VBox();
    // create a horizontal box to arrange elements of second Border Pane
    HBox horizontalMenu1 = new HBox();
    HBox horizontalMenu2 = new HBox();

    // create Labels to instruct a user
    Label enterMonth = new Label("Enter Month:");
    Label enterYear = new Label("Year:");
    // set locations of the label
    grid.add(enterMonth, 0, 1);
    grid.add(enterYear, 1, 1);

    // create a TextArea and textfield to instruct a user
    final TextArea year = new TextArea();
    TextField year_info = new TextField();
    year_info.setText("Please enter a valid year");

    // receives the data from comboBox Helper method
    ComboBox<String> userPick = monthBox();
    // add all elements to HBox
    horizontalMenu1.getChildren().addAll(enterMonth, userPick);
    horizontalMenu2.getChildren().addAll(enterYear, year_info);

    // create and form characteristics of a button to generate a report
    Button secondButton2 = new Button("Generate Report");
    secondButton2.setPrefHeight(30);
    secondButton2.prefWidthProperty().bind(pRoot.widthProperty());
    grid.add(secondButton2, 2, 1);
    // add the elements to the VBox
    centerMenu.getChildren().addAll(horizontalMenu1, horizontalMenu2,
        secondButton2);

    // create and form characteristics of a button to go back to the
    // previous window
    Button backButton = new Button("Back");
    backButton.setPrefHeight(30);
    backButton.prefWidthProperty().bind(pRoot.widthProperty());

    // Calls the methods to get the Top, Center, Bottom components with
    // specified variables
    root2.setTop(getTopLabel(pRoot));
    root2.setCenter(centerMenu);
    root2.setBottom(backButton);

    // create a scene and a stage to display
    Scene secondScene = new Scene(root2, WINDOW_WIDTH, WINDOW_HEIGHT);
    Stage secondStage = new Stage();

    // set the title and scene on the window, and display the components
    secondStage.setTitle(APP_TITLE);
    secondStage.setScene(secondScene);

    // when the backButton is clicked, go to the previous window and
    // close the current window
    backButton.setOnAction(e1 -> {
      getPrimaryStage().show();
      secondStage.close();
    });

    // when the secondButton2 is clicked,
    secondButton2.setOnAction(e1 -> {

      try {
        // check whether the input from ComboBox is valid
        // if the input is null, prompt the alert message
        if (userPick.getValue() == null) {
          Alert alert = new Alert(AlertType.WARNING, "Please select a month!");
          alert.setHeaderText("Error occured while generating report!");
          alert.showAndWait();
          return;
        }

        // input from user, indicating year
        int yearInput = Integer.parseInt(year_info.getText());
        // check whether the year input is valid
        // if the input is null or a negative number, prompt an alert
        // message
        if (year_info.getText() == null || yearInput < 0) {
          Alert alert = new Alert(AlertType.WARNING,
              "Please enter a valid year format (positive number!).");
          alert.setHeaderText("Error occured while generating report!");
          alert.showAndWait();
          return;
        }
        // user input from the drop box where the user gets to choose
        // the
        // month
        int userInput = Integer.parseInt(userPick.getValue());

        // 2D array saving the farm ID and its weight
        String[][] list = dataStorage.sortFarmWeight(dataStorage
            .rangeReport(yearInput, userInput, 1, yearInput, userInput, 31));

        // Create new window panes
        BorderPane resultContent = new BorderPane();
        ScrollPane result = new ScrollPane();

        if (list == null) {
          Alert alert =
              new Alert(AlertType.WARNING, "You must load data first!");
          alert.setHeaderText("Error occured while generating report!");
          alert.showAndWait();
          return;
        } else {
          // Create table view
          TableView table = new TableView();

          // Create columns for table
          TableColumn monthCol = new TableColumn("Farm");
          TableColumn milkWeight = new TableColumn("Weight");
          TableColumn percentage = new TableColumn("% of total weight");

          // Create collection for row data
          ObservableList<TableComponent> componentList =
              FXCollections.observableArrayList();

          // Add data for each month
          for (int i = 0; i < list.length; i++) {
            componentList.add(new TableComponent(list[i][0] + "",
                list[i][1] + "", list[i][2] + ""));
          }

          // col1 is the farm
          monthCol.setCellValueFactory(new PropertyValueFactory<>("col1"));
          milkWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
          percentage.setCellValueFactory(new PropertyValueFactory<>("percent"));

          // Link list(rows) to table
          table.setItems(componentList);

          // add columns
          table.getColumns().addAll(monthCol, milkWeight, percentage);
          table.setEditable(false);

          // Creating a string for save file
          String listText = "";
          for (int i = 0; i < list.length; i++) {
            listText +=
                list[i][0] + " : " + list[i][1] + ", " + list[i][2] + "\n";
          }

          // creating a horizontal box for the add and remove buttons
          HBox hbox = saveGUI(listText);

          // locating a text area
          resultContent.setCenter(table);

          // locating the box
          resultContent.setBottom(hbox);

          // Creating Scrollable Window
          result.setContent(resultContent);

          // display window
          Scene resultScene = new Scene(result, WINDOW_WIDTH, 430);
          Stage resultStage = new Stage();

          // display window next to main window
          resultStage.setTitle("Milk Weights for Month " + (int) userInput);
          resultStage
              .setX(getPrimaryStage().getX() + getPrimaryStage().getWidth());
          resultStage.setY(getPrimaryStage().getY());
          resultStage.setScene(resultScene);
          resultStage.show();
        }
        // if the input is not Integer, throw NumberFormatException and
        // prompt an alert message
      } catch (NumberFormatException e) {
        Alert alert = new Alert(AlertType.WARNING,
            "Please enter a valid year format (Must be an integer!).");
        alert.setHeaderText("Error occured while generating report!");
        alert.showAndWait();
        return;
      }

    });
    // show the secondStage and close the primary Stage
    secondStage.show();
    getPrimaryStage().close();
  }

  /**
   * This method is a helper method to display and set all the related functions
   * when the button3 is clicked in primary Stage.
   */
  private void AnnualReport() {

    // create a new BorderPane to display
    BorderPane root2 = new BorderPane();
    GridPane grid = new GridPane();
    // Display message
    Label enterYear = new Label("Year: ");
    // create a horizontal box to arrange elements
    HBox horizontalMenu1 = new HBox();
    // create a vertical box to arrange elements
    VBox verticalMenu = new VBox();

    // create a TextArea and textfield to instruct a user
    final TextArea year = new TextArea();
    TextField year_info = new TextField();
    year_info.setText("Please enter a valid year");

    horizontalMenu1.getChildren().addAll(enterYear, year_info);

    // create and form characteristics of a button to generate a report
    Button secondButton2 = new Button("Generate Report");
    secondButton2.setPrefHeight(30);
    secondButton2.prefWidthProperty().bind(pRoot.widthProperty());
    grid.add(secondButton2, 2, 0);

    verticalMenu.getChildren().addAll(horizontalMenu1, secondButton2);

    // create and form characteristics of a button to go back to the
    // previous window
    Button backButton = new Button("Back");
    backButton.setPrefHeight(30);
    backButton.prefWidthProperty().bind(pRoot.widthProperty());

    // Calls the methods to get the Top, Center, Bottom components with
    // specified variables
    root2.setTop(getTopLabel(pRoot));
    root2.setCenter(verticalMenu);
    root2.setBottom(backButton);

    // create a scene and a stage to display
    Scene secondScene = new Scene(root2, WINDOW_WIDTH, WINDOW_HEIGHT);
    Stage secondStage = new Stage();

    // set the title and scene on the window, and display the components
    secondStage.setTitle(APP_TITLE);
    secondStage.setScene(secondScene);

    // when the backButton is clicked, go to the previous window and
    // close the current window
    backButton.setOnAction(e1 -> {
      getPrimaryStage().show();
      secondStage.close();
    });

    // when the secondButton2 is clicked,
    secondButton2.setOnAction(e1 -> {

      try {
        // input from user, indicating year
        int yearInput = Integer.parseInt(year_info.getText());
        // check whether the year input is valid
        // if the input is null or a negative number, prompt an alert
        // message
        if (year_info.getText() == null || yearInput < 0) {
          Alert alert = new Alert(AlertType.WARNING,
              "Please enter a valid year format (positive number!).");
          alert.setHeaderText("Error occured while generating report!");
          alert.showAndWait();
          return;
        }

        // Search for whole year (January ~ December)
        String[][] list = dataStorage.sortFarmWeight(
            dataStorage.rangeReport(yearInput, 1, 1, yearInput, 12, 31));

        // Create new window panes
        BorderPane resultContent = new BorderPane();
        ScrollPane result = new ScrollPane();

        if (list == null) {
          Alert alert =
              new Alert(AlertType.WARNING, "You must load data first!");
          alert.setHeaderText("Error occured while generating report!");
          alert.showAndWait();
          return;
        } else {
          // Create table view
          TableView table = new TableView();

          // Create columns for table
          TableColumn monthCol = new TableColumn("Month");
          TableColumn milkWeight = new TableColumn("Weight");
          TableColumn percentage = new TableColumn("% of total weight");

          // Create collection for row data
          ObservableList<TableComponent> componentList =
              FXCollections.observableArrayList();

          // Add data for each month
          for (int i = 0; i < list.length; i++) {
            componentList.add(new TableComponent(list[i][0] + "",
                list[i][1] + "", list[i][2] + ""));
          }

          // col1 is the month
          monthCol.setCellValueFactory(new PropertyValueFactory<>("col1"));
          milkWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
          percentage.setCellValueFactory(new PropertyValueFactory<>("percent"));

          // Link list(rows) to table
          table.setItems(componentList);

          // add columns
          table.getColumns().addAll(monthCol, milkWeight, percentage);
          table.setEditable(false);

          // Creating a string for save file
          String listText = ""; // StringBuilder
          for (int i = 0; i < list.length; i++) {
            listText +=
                list[i][0] + " : " + list[i][1] + ", " + list[i][2] + "\n";
          }

          // add save button
          HBox hbox = saveGUI(listText);

          // locating table
          resultContent.setCenter(table);

          // locating the box
          resultContent.setBottom(hbox);

          // Creating Scrollable Window
          result.setContent(resultContent);

          // display window
          Scene resultScene = new Scene(result, WINDOW_WIDTH, 430);
          Stage resultStage = new Stage();

          // display window next to main window
          resultStage.setTitle("Milk Weights for Year " + yearInput);
          resultStage
              .setX(getPrimaryStage().getX() + getPrimaryStage().getWidth());
          resultStage.setY(getPrimaryStage().getY());
          resultStage.setScene(resultScene);
          resultStage.sizeToScene();
          resultStage.show();
        }

        // if the input is not Integer, throw NumberFormatException and
        // prompt an alert message
      } catch (NumberFormatException e) {
        Alert alert = new Alert(AlertType.WARNING,
            "Please enter a valid year format (Must be an integer!).");
        alert.setHeaderText("Error occured while generating report!");
        alert.showAndWait();
        return;
      }

    });
    // show the secondStage and close the primary Stage
    secondStage.show();
    getPrimaryStage().close();
  }

  /**
   * This method is a helper method to display and set all the related functions
   * when the button4 is clicked in primary Stage.
   */
  private void ChooseRange() {
    // create a new BorderPane
    BorderPane root2 = new BorderPane();
    // create a new grid
    GridPane grid = new GridPane();
    // create a vertical box
    VBox centerMenu2 = new VBox();
    // create horizontal boxes
    HBox horizontalMenu1 = new HBox();
    HBox horizontalMenu2 = new HBox();
    HBox horizontalMenu3 = new HBox();

    // create and form characteristics of Labels to instruct a user
    Label startMonth = new Label("Start Month:");
    Label startDay = new Label("Start Day: ");
    Label endMonth = new Label("End Month: ");
    Label endDay = new Label("End Day: ");
    Label enterYear = new Label("Year: ");
    grid.add(startMonth, 0, 1);
    grid.add(startDay, 0, 2);
    grid.add(endMonth, 1, 1);
    grid.add(endDay, 1, 2);
    grid.add(enterYear, 2, 1);

    // receives the data from comboBox Helper method
    ComboBox<String> userSelection1 = monthBox();
    ComboBox<String> userSelection2 = monthBox();
    ComboBox<String> userSelection3 = dayBox();
    ComboBox<String> userSelection4 = dayBox();

    // create a TextArea and textfield to instruct a user
    final TextArea year = new TextArea();
    TextField year_info = new TextField();
    year_info.setText("Please enter a valid year");

    // add elements to the horizontal box
    horizontalMenu1.getChildren().addAll(startMonth, userSelection1, startDay,
        userSelection3);
    horizontalMenu2.getChildren().addAll(endMonth, userSelection2, endDay,
        userSelection4);
    horizontalMenu3.getChildren().addAll(enterYear, year_info);

    // create a button to generate report
    Button bottomButton = new Button("Generate Report");
    bottomButton.setPrefHeight(30);
    bottomButton.prefWidthProperty().bind(pRoot.widthProperty());
    bottomButton.setAlignment(Pos.BASELINE_CENTER);

    // add all elements to the center
    centerMenu2.getChildren().addAll(horizontalMenu1, horizontalMenu2,
        horizontalMenu3, bottomButton);

    // create a button to go back to the previous window
    Button backButton = new Button("Back");
    backButton.setPrefHeight(30);
    backButton.prefWidthProperty().bind(pRoot.widthProperty());

    // when generate report is clicked
    bottomButton.setOnAction(submit -> {

      // if inputs from the user are invalid, prompt an alert message
      if ((userSelection1.getValue() == null)
          || (userSelection2.getValue() == null)) {
        Alert alert =
            new Alert(AlertType.WARNING, "Please enter a start and end date!");
        alert.setHeaderText("Error occured while generating report!");
        alert.showAndWait();
        return;
      }

      try {
        // input from user, indicating year
        // user inputs from the drop box where the user gets to choose
        // the
        // month
        int userInput1 = Integer.parseInt(userSelection1.getValue());
        int userInput2 = Integer.parseInt(userSelection2.getValue());
        int userInput3 = Integer.parseInt(userSelection3.getValue());
        int userInput4 = Integer.parseInt(userSelection4.getValue());

        // if start date is greater than end date, display error message
        if ((userInput2 < userInput1)
            || ((userInput1 == userInput2) && (userInput4 < userInput3))) {
          Alert alert = new Alert(AlertType.WARNING,
              "Please enter a valid start and end date! "
                  + "(The end date cannot be before the start date!)");
          alert.setHeaderText("Error occured while generating report!");
          alert.showAndWait();
          return;
        }

        int yearInput = Integer.parseInt(year_info.getText());
        // check whether the year input is valid
        // if the input is null or a negative number, prompt an alert
        // message
        if (year_info.getText() == null || yearInput < 0) {
          Alert alert = new Alert(AlertType.WARNING,
              "Please enter a valid year format (positive number!).");
          alert.setHeaderText("Error occured while generating report!");
          alert.showAndWait();
          return;
        }
        // 2D array saving the farm ID and its weight
        String[][] list =
            dataStorage.sortFarmWeight(dataStorage.rangeReport(yearInput,
                userInput1, userInput3, yearInput, userInput2, userInput4));

        // Create new window panes
        BorderPane resultContent = new BorderPane();
        ScrollPane result = new ScrollPane();

        // Alert if list is null (no data found)
        if (list == null) {
          Alert alert =
              new Alert(AlertType.WARNING, "You must load data first!");
          alert.setHeaderText("Error occured while generating report!");
          alert.showAndWait();
          return;
        } else {
          // Create table view
          TableView table = new TableView();

          // Create columns for table
          TableColumn monthCol = new TableColumn("Month");
          TableColumn milkWeight = new TableColumn("Weight");
          TableColumn percentage = new TableColumn("% of total weight");

          // Create collection for row data
          ObservableList<TableComponent> componentList =
              FXCollections.observableArrayList();

          // Add data for each month
          for (int i = 0; i < list.length; i++) {
            componentList.add(new TableComponent(list[i][0] + "",
                list[i][1] + "", list[i][2] + ""));
          }

          // col1 is the month
          monthCol.setCellValueFactory(new PropertyValueFactory<>("col1"));
          milkWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
          percentage.setCellValueFactory(new PropertyValueFactory<>("percent"));

          // Link list(rows) to table
          table.setItems(componentList);

          // add columns
          table.getColumns().addAll(monthCol, milkWeight, percentage);
          table.setEditable(false);

          // Creating a string for save file
          String listText = "";
          for (int i = 0; i < list.length; i++) {
            listText +=
                list[i][0] + " : " + list[i][1] + ",  " + list[i][2] + "\n";
          }

          // creating a horizontal box for the add and remove buttons
          HBox hbox = saveGUI(listText);

          // locating table
          resultContent.setCenter(table);

          // locating the box
          resultContent.setBottom(hbox);

          // Creating Scrollable Window
          result.setContent(resultContent);

          // put on show
          Scene resultScene = new Scene(result, WINDOW_WIDTH, 430);
          Stage resultStage = new Stage();

          resultStage.setTitle("Milk Weights for Range " + yearInput);
          resultStage
              .setX(getPrimaryStage().getX() + getPrimaryStage().getWidth());
          resultStage.setY(getPrimaryStage().getY());
          resultStage.setScene(resultScene);
          resultStage.show();
        }
        // if the input is not Integer, throw NumberFormatException and
        // prompt an alert message
      } catch (NumberFormatException e) {
        Alert alert = new Alert(AlertType.WARNING,
            "Please enter a valid year format (Must be an integer!).");
        alert.setHeaderText("Error occured while generating report!");
        alert.showAndWait();
        return;
      }
    });

    // Calls the methods to get the Top, Center, Bottom components with
    // specified variables
    root2.setTop(getTopLabel(pRoot));
    root2.setCenter(centerMenu2);
    root2.setBottom(backButton);

    // create a scene and a stage to display
    Scene secondScene = new Scene(root2, WINDOW_WIDTH, WINDOW_HEIGHT);
    Stage secondStage = new Stage();

    // set the title and scene on the window, and display the components
    secondStage.setTitle(APP_TITLE);
    secondStage.setScene(secondScene);

    // when back button is clicked, go back to the previous window and
    // close the current window
    backButton.setOnAction(e1 -> {
      getPrimaryStage().show();
      secondStage.close();
    });
    // show the secondStage and close the primary Stage
    secondStage.show();
    getPrimaryStage().close();
  }

  /**
   * This method deals with displaying a FileChooser for saving a file.
   * 
   * @return Button with updated information
   */
  private void saveButton(String saveText) {
    try {
      FileChooser fileChooser = new FileChooser();

      FileChooser.ExtensionFilter extFilter =
          new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
      fileChooser.getExtensionFilters().add(extFilter);

      File file = fileChooser.showSaveDialog(getPrimaryStage());

      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(saveText);
      fileWriter.close();
      System.out.println("Save Successful!");
    } catch (Exception e) {
      return;
    }
  }

  /**
   * This method is a helper method to display and set all the related functions
   * of ComboBox
   * 
   * @return ComboBox an updated ComboBox
   */
  private ComboBox monthBox() {

    // create a new GridPane
    GridPane grid = new GridPane();

    // creates forms characteristics of a ComboBox to select a month
    ComboBox<String> comboBox = new ComboBox<String>();
    comboBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9",
        "10", "11", "12");
    comboBox.setMaxSize(50, 140);
    grid.add(comboBox, 1, 1);

    return comboBox;
  }

  /**
   * This method is a helper method to display and set all the related functions
   * of ComboBox
   * 
   * @return ComboBox an updated ComboBox
   */
  private ComboBox dayBox() {

    // create a new GridPane
    GridPane grid = new GridPane();

    // creates forms characteristics of a ComboBox to select a month
    ComboBox<String> comboBox = new ComboBox<String>();
    comboBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9",
        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
        "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
    comboBox.setMaxSize(50, 140);
    grid.add(comboBox, 1, 1);

    return comboBox;
  }

  /**
   * This method is a helper method to display and set all the related functions
   * of HBox, especially for add and remove buttons in this GUI
   * 
   * @return HBox an updated HBox
   */
  private HBox saveGUI(String text) {

    // a button for removing existing entry
    Button save_button = new Button("Save");

    // when the user wants to remove the entry
    save_button.setOnAction(action -> {
      saveButton(text);
    });

    // creating a horizontal box for the add and remove buttons
    HBox hbox = new HBox(save_button);
    return hbox;
  }

  /**
   * This method is a helper method to prompt the user to choose their desired
   * data using FileCHooser
   * 
   */
  private void LoadData() {
    try {
      FileChooser fileChooser = new FileChooser();

      FileChooser.ExtensionFilter extFilter =
          new FileChooser.ExtensionFilter("csv (*.csv)", "*.csv");
      fileChooser.getExtensionFilters().add(extFilter);

      List<File> file = fileChooser.showOpenMultipleDialog(getPrimaryStage());

      for (int i = 0; i < file.size(); i++) {
        readFile(file.get(i).getPath());
      }

    } catch (Exception e) {
      return;
    }
  }

  /**
   * This method sets up the scene, especially Top, Bottom, Left, Right, and
   * Center on the stage and then displays it.
   * 
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    // creates a dataStorage
    dataStorage = new Data_Storage();
    // define a primary Root
    pRoot = new BorderPane();
    // set the stage with primary Stage
    setPrimaryStage(primaryStage);
    // define a primaryStage
    pStage = primaryStage;

    // creates a menu which will be located in the center
    VBox centerMenu = new VBox();

    // creates three buttons with customized width and height
    Button button0 = new Button("Load Data");
    Button button1 = new Button("Farm Report");
    Button button2 = new Button("Monthly Report");
    Button button3 = new Button("Annual Report");
    Button button4 = new Button("Choose Range");
    button0.setPrefHeight(30);
    button0.prefWidthProperty().bind(pRoot.widthProperty());
    button1.setPrefHeight(30);
    button1.prefWidthProperty().bind(pRoot.widthProperty());
    button2.setPrefHeight(30);
    button2.prefWidthProperty().bind(pRoot.widthProperty());
    button3.setPrefHeight(30);
    button3.prefWidthProperty().bind(pRoot.widthProperty());
    button4.setPrefHeight(30);
    button4.prefWidthProperty().bind(pRoot.widthProperty());

    // add all buttons to a menu which will be located in the center
    centerMenu.getChildren().addAll(button0, button1, button2, button3,
        button4);

    // when button0 is clicked
    button0.setOnAction(e -> LoadData());
    // when button1 is clicked
    button1.setOnAction(e -> FarmReport());
    // when button2 is clicked
    button2.setOnAction(e -> MonthlyReport());
    // when button3 is clicked
    button3.setOnAction(e -> AnnualReport());
    // when button4 is clicked
    button4.setOnAction(e -> ChooseRange());

    // Calls the methods to get the Top, Center, Bottom components with
    // specified variables
    pRoot.setTop(getTopLabel(pRoot));
    pRoot.setCenter(centerMenu);

    // create a scene to display
    Scene mainScene = new Scene(pRoot, WINDOW_WIDTH, WINDOW_HEIGHT);

    // set the title and scene on the window, and display the components
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(mainScene);

    // Close all windows when main window is closed
    primaryStage.setOnCloseRequest(e -> Platform.exit());
    primaryStage.show();
  }

  /**
   * This method serves as the driver of the application.
   * 
   * @param args - command line arguments are not used by this program
   */
  public static void main(String[] args) {
    launch(args);
  }
}
