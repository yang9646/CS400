//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: HelloFX
// Files: Main.java
// Semester: (CS 400) Spring 2020
//
// Author: Hyukjoon Yang
// Email: hyang348@wisc.edu
// CS Login: hyukjoon
// Lecturer's Name: Debra Deppeler
// Lecture Section: 001
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

import java.io.FileInputStream;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * This class is the main class that runs the application
 * 
 * @author Hyukjoon Yang
 *
 */
public class Main extends Application {
  // store any command-line arguments that were entered.
  // NOTE: this.getParameters().getRaw() will get these also
  private List<String> args;

  private static final int WINDOW_WIDTH = 300;
  private static final int WINDOW_HEIGHT = 200;
  private static final String APP_TITLE = "Hello World!";

  /**
   * This method sets up the settings of this application
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    
    // Basic pane to be viewed to the screen
    BorderPane background = new BorderPane();

    // Label located in the top panel
    Label topLabel = new Label("CS400 My First JavaFX Program");
    topLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD,14));
    topLabel.setAlignment(Pos.CENTER);
    topLabel.setMaxWidth(Double.POSITIVE_INFINITY);
    topLabel.setMaxHeight(Double.POSITIVE_INFINITY);
    background.setTop(topLabel);

    // ComboBox in the left panel
    ComboBox<String> leftCombo = new ComboBox<String>();

    // Options in the box
    leftCombo.getItems().add("Hello");
    leftCombo.getItems().add("Hola");
    leftCombo.getItems().add("Chao");
    leftCombo.setValue("Greetings");
    
    // TextField that user writes in
    TextField userText = new TextField();
    userText.setPromptText("Comments this photo");
    userText.setMaxHeight(Double.POSITIVE_INFINITY);
    

    // Image to be placed in the center panel
    Image img = new Image("Self-photo.jpg");
    ImageView image = new ImageView(img);

    // Set size of image
    image.setFitHeight(100);
    image.setFitWidth(100);


    // Button in the bottom panel
    Button button = new Button("Done");

    // Allocate elements into the border pane
    background.setLeft(leftCombo);
    background.setCenter(image);
    background.setBottom(button);
    background.setRight(userText);

    // Display the application
    Scene main = new Scene(background, WINDOW_WIDTH, WINDOW_HEIGHT);
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(main);
    primaryStage.show();
  }

  /**
   * Main method that launches the application
   * 
   * @param args is the argument passed from the command
   */
  public static void main(String[] args) {
    launch(args);
  }
}
