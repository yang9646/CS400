/**
 * 
 */
package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 *
 */
public class Main extends Application {
    SocialNetwork socialNetwork = new SocialNetwork();

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CANVAS_WIDTH = 950;
    private static final int CANVAS_HEIGHT = 700;
    private static final String APP_TITLE = "Social Network App";

    private Desktop desktop = Desktop.getDesktop();
    // GUI Canvas
    private Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private Canvas canvas2 = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    private GraphicsContext gc2 = canvas2.getGraphicsContext2D();
    private int addCounter = 0;

    // USERGUI
    // List to keep track of GUI nodes
    class UserGUI {
        String name;
        double guiSize[] = new double[2];
        double guiSize2[] = new double[2];
        boolean scene2 = false;

        UserGUI() {
            name = null;
            guiSize = null;
        }

        UserGUI(String name) {
            this.name = name;
        }

        UserGUI(String name, double x, double y) {
            this.name = name;
            this.guiSize[0] = x;
            this.guiSize[1] = y;
            this.scene2 = false;
        }


    }

    List<UserGUI> userGUIList = new ArrayList<UserGUI>();
    // GUI circle location
    double counterx = 100;
    double countery = 100;

    public SocialNetworkADT SocialNetwork; // Is this right? Shouldn't it be SocialNetwork? -JV
    public AnchorPane firstScenePane;
    public AnchorPane secondScenePane;
    public AnchorPane thirdScenePane;
    public HBox topMenu;
    public VBox rightMenuCanvas2;
    public VBox rightMenuCanvas3;
    public HBox addUserMenu;
    public HBox removeUserMenu;
    public HBox searchUserMenu;
    public HBox findMutualsMenu;
    public VBox addFriendsMenu;
    public VBox removeFriendsMenu;
    public VBox selectCentralUserMenu1;
    public VBox selectCentralUserMenu2;
    public VBox canvasBox;
    public VBox canvasBox2;
    public HBox shortestPathMenu;
    

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene1;
        Scene scene2;
        Scene scene3;

        // Initialize labels and buttons for scene 1
        Label label1 = new Label("Load");
        Label label2 = new Label("Create");

        Button button1 = new Button("Browse Files");
        Button button2 = new Button("New Graph");
        Button saveButton = new Button("Save");
        Button exitNoSaveButton = new Button("Exit without Save");
        Button backButton = new Button("Back to Main Menu");
        Button nextScene3Button = new Button("Next");


        // scene 1
        firstScenePane = new AnchorPane();

        firstScenePane = setUpFirstScenePane(label1, label2, button1, button2);

        scene1 = new Scene(firstScenePane, 300, 250);

        // Scene2
        // Menu on the top to Add User, Remove User, Search User, Find Mutuals
        topMenu = setUpTopMenu();

        // Menu on the right to Add Friends, Remove Friends
        rightMenuCanvas2 = setUpRightMenu(gc,true);

        // Canvas for Scene2
        canvasBox = setUpCanvasBox();

        // Sets up the entire scene 2 pane
        secondScenePane = new AnchorPane();

        secondScenePane = setUpSecondScenePane(topMenu, rightMenuCanvas2, saveButton,
                exitNoSaveButton, backButton, canvasBox, nextScene3Button,shortestPathMenu);

        scene2 = new Scene(secondScenePane, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Scene3
        // Canvas for Scene 3
        canvasBox2 = setUpCanvasBox2();
        rightMenuCanvas3 = setUpRightMenu(gc2,false);
        Button changeCentralUserButton = new Button("Change Central User");
        Button backButton2 = new Button("Back");

        

        // Sets up the third scene pane
        thirdScenePane = new AnchorPane();
        thirdScenePane = setUpThirdScenePane(rightMenuCanvas3, canvasBox2,backButton2,changeCentralUserButton);
    
        scene3 = new Scene(thirdScenePane, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Code to move between scenes
        button2.setOnAction(e -> {
            primaryStage.setScene(scene2);
            primaryStage.centerOnScreen();
            primaryStage.setTitle(APP_TITLE);
        });
        backButton.setOnAction(e -> {
            primaryStage.setScene(scene1);
            primaryStage.centerOnScreen();
            primaryStage.setTitle(APP_TITLE);
        });
        backButton.setOnAction(e -> {
            primaryStage.setScene(scene1);
            primaryStage.centerOnScreen();
            primaryStage.setTitle(APP_TITLE);
        });
        nextScene3Button.setOnAction(e -> {
            primaryStage.setScene(scene3);
            primaryStage.centerOnScreen();
            primaryStage.setTitle(APP_TITLE);
            setUpCentralUserInScene3();
            drawGraphScene3(gc2);
        });
        backButton2.setOnAction(e -> {
            primaryStage.setScene(scene2);
            primaryStage.centerOnScreen();
            primaryStage.setTitle(APP_TITLE);
        });
        changeCentralUserButton.setOnAction(e -> {
            primaryStage.setScene(scene3);
            primaryStage.centerOnScreen();
            primaryStage.setTitle(APP_TITLE);
            gc2.setFill(Color.DARKGREEN);
            gc2.fillRect(0.0, 0.0, 900, 900);
            setUpCentralUserInScene3();
            drawGraphScene3(gc2);

        });

        // Save File
        saveButton.setOnAction(setUpSave());

        // Browse file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        button1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                // File file = new File(fileChooser.getInitialDirectory()); ? - JV
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null)
                    openFile(file);
                primaryStage.setScene(scene2);
                primaryStage.centerOnScreen();
                primaryStage.setTitle(APP_TITLE);
            }
        });
        Alert exitNoSaveAlert = new Alert(AlertType.NONE);
        exitNoSaveButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {

                exitNoSaveAlert.setAlertType(AlertType.CONFIRMATION);	
                // set content text	
                exitNoSaveAlert.setContentText("You have exited Network Visualizer without saving");	
                Optional<ButtonType> result = exitNoSaveAlert.showAndWait();	
                if(result.get() == ButtonType.OK) {       	
                    Stage stage = (Stage) exitNoSaveButton.getScene().getWindow();	
                    stage.close();	
                }	
            }	
        });	
        	
        Alert exitAlert = new Alert(AlertType.NONE);	
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {	
            @Override	
            public void handle(WindowEvent event) {	
                event.consume();	
                exitAlert.setAlertType(AlertType.CONFIRMATION);	
                exitAlert.setContentText("You are trying to exit the program\n" 	
                        + "Do you want to exit without saving?");	
                	
                Optional<ButtonType> result = exitAlert.showAndWait();	
                if(result.get() == ButtonType.OK) {       	
//                    Stage stage = (Stage) exitNoSaveButton.getScene().getWindow();	
                    primaryStage.close();	
                }

            }
        });

        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(scene1);
        primaryStage.show();

    }

    /**
     * Sets up the Sign Up Box for the social network
     */
    private AnchorPane setUpFirstScenePane(Label label1, Label label2, Button button1,
            Button button2) {

        AnchorPane scene1pane = new AnchorPane();

        // Text that is the header for scene 1
        Text t = new Text("Network Visualize Main Menu");
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);

        // Initializes the hboxes used for scene 1
        HBox hbox1 = new HBox(10);
        hbox1.getChildren().addAll(label1, button1);

        HBox hbox2 = new HBox(10);
        hbox2.getChildren().addAll(label2, button2);

        // Adds all neccessary components to scene 1
        scene1pane.getChildren().add(t);
        scene1pane.setBottomAnchor(t, 160.0);
        scene1pane.getChildren().add(hbox1);
        scene1pane.setBottomAnchor(hbox1, 120.0);
        scene1pane.getChildren().add(hbox2);
        scene1pane.setBottomAnchor(hbox2, 80.0);

        scene1pane.setMaxSize(50, 150);

        AnchorPane.setLeftAnchor(hbox1, 100.0);
        AnchorPane.setLeftAnchor(hbox2, 100.0);

        return scene1pane;

    }
    /**
    * Sets up the third scene pane	
    * 	
    * @param rightMenu        the right VBox of the second scene	
    * @param topMenu	
    * @param saveButton	
    * @param exitNoSaveButton	
    * @param backButton	
    * @param canvasBox	
    * @param button	
    * @return the Anchorpane that will be the basis of the 3rd scene pane	
    */
    private AnchorPane setUpSecondScenePane(HBox topMenu, VBox rightMenu, Button saveButton,
            Button exitNoSaveButton, Button backButton, VBox canvasBox, Button button,HBox shortestPath) {

        AnchorPane scene2pane = new AnchorPane();

        scene2pane.getChildren().addAll(topMenu, rightMenu, saveButton, exitNoSaveButton,
                backButton, canvasBox, button);

        AnchorPane.setRightAnchor(rightMenu, 10.0);
        AnchorPane.setBottomAnchor(rightMenu, 300.0);
        AnchorPane.setRightAnchor(saveButton, 100.0);
        AnchorPane.setBottomAnchor(saveButton, 120.0);
        AnchorPane.setRightAnchor(exitNoSaveButton, 60.0);
        AnchorPane.setBottomAnchor(exitNoSaveButton, 70.0);
        AnchorPane.setRightAnchor(backButton, 50.0);
        AnchorPane.setBottomAnchor(backButton, 30.0);
        AnchorPane.setTopAnchor(canvasBox, 100.0);
        AnchorPane.setTopAnchor(button, 410.0);
        AnchorPane.setRightAnchor(button, 60.0);
        

        return scene2pane;
    }

    /**	
     * Sets up the third scene pane	
     * 	
     * @param rightMenu the right VBox of the third scene	
     * @param canvas    the VBox canvas of the third scene	
     * @return the Anchorpane that will be the basis of the 3rd scene pane	
     */
    private AnchorPane setUpThirdScenePane(VBox rightMenu, VBox canvas, Button back,Button newCentral) {

        AnchorPane scene3pane = new AnchorPane();

        scene3pane.getChildren().addAll(canvas, rightMenu,back,newCentral);

        AnchorPane.setTopAnchor(canvasBox, 100.0);
        AnchorPane.setRightAnchor(rightMenu, 10.0);
        AnchorPane.setBottomAnchor(rightMenu, 300.0);
        AnchorPane.setBottomAnchor(back, 30.0);
        AnchorPane.setRightAnchor(back, 50.0);
        AnchorPane.setRightAnchor(rightMenu, 30.0);
        AnchorPane.setRightAnchor(newCentral, 25.0);
        AnchorPane.setBottomAnchor(newCentral, 200.0);
        
        



        return scene3pane;


    }

    /**	
     * Sets up the Center Box for the social network	
     * 	
     * @return the HBox that is the top menu for the scene	
     */
    private HBox setUpTopMenu() {

        topMenu = new HBox(15);

        addUserMenu = setUpAddUserMenu();

        removeUserMenu = setUpRemoveUserMenu();

        searchUserMenu = setUpSearchUserMenu();

        findMutualsMenu = setUpFindMutualsMenu();
        
        shortestPathMenu = setShortestPathMenu();

        topMenu.getChildren().addAll(addUserMenu, removeUserMenu, searchUserMenu, findMutualsMenu,shortestPathMenu);

        return topMenu;
    }

    private HBox setShortestPathMenu() {
		// TODO Auto-generated method stub
		shortestPathMenu = new HBox();
		
		TextField shortestPathBox = new TextField();
		Button shortestPathButton = new Button("Shortest Path");
		
		 Alert findMutualAlert = new Alert(AlertType.NONE);

	        // action event for findMutuals button
	        EventHandler<ActionEvent> findMutualsEvent = new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent e) {
	                // set alert type
	                int userTextLength = shortestPathBox.getText().length();
	                //Store the central user information
	                String cenUser = socialNetwork.cenUser;
	                //Store user input
	                String findShortest = shortestPathBox.getText();
	                for (int i = 0; i <= userTextLength; i++) {
	                    if (shortestPathBox.getText().charAt(i) == ' '
	                            || shortestPathBox.getText().charAt(i) == '\"') {
	                        findMutualAlert.setAlertType(AlertType.ERROR);

	                        findMutualAlert.setContentText("Username can't have "
	                                + shortestPathBox.getText().charAt(i) + " in it");

	                        findMutualAlert.show();
	                        break;
	                    }

	                    else {
	                    	List<Person> shortList = socialNetwork.getShortestPath(cenUser, findShortest);
	                    	List<Person> mutuals = new ArrayList<Person>();	
	                        mutuals.addAll(shortList);	
	                        List<String> mutualStrings = new ArrayList<String>();	
	                        String out ="--";
	                        for (int j = 0; j < mutuals.size(); j++) {	
	                            mutualStrings.add(mutuals.get(j).getName());	
	                        }	
	                        for (String shortestPathUser : mutualStrings) {	
	                        	out = out+shortestPathUser;
	                        }
	                     // set alert type
	                        findMutualAlert.setAlertType(AlertType.CONFIRMATION);

	                        // Setting prompt
	                        findMutualAlert.setContentText("Shortest Path for "+cenUser+" are: "+out);

	                        // Show prompt
	                        findMutualAlert.show();
	                    }
	                }
	            }
	        };
	        shortestPathButton.setOnAction(findMutualsEvent);
	        shortestPathMenu.getChildren().addAll(shortestPathBox, shortestPathButton);

	        return shortestPathMenu;
    }
		
		
	
	        

	/**
     * Sets up the Bottom Box for the social network
     */
    private HBox setUpAddUserMenu() {

        addUserMenu = new HBox();

        TextField addUserBox = new TextField();
        Button addUserButton = new Button("Add User");
        addUserMenu.getChildren().addAll(addUserBox, addUserButton);

        Alert addingUserAlert = new Alert(AlertType.NONE);

        // action event for addUser button
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                UserGUI addUserGUI = new UserGUI(addUserBox.getText(), counterx, countery);
                userGUIList.add(addUserGUI);
                if(addUserBox.getText().length() <= 0) {	
                	// set alert type	
                    addingUserAlert.setAlertType(AlertType.ERROR);	
                    // Setting prompt	
                    addingUserAlert.setContentText("Please insert a name to add to the network!");	
                    // Show prompt	
                    addingUserAlert.show();	
                }	
                else if(socialNetwork.addUser(addUserBox.getText()) == true) {	
                drawNode(gc, addUserBox.getText(), counterx, countery);
                
                socialNetwork.addUser(addUserBox.getText());
                drawNode(gc, addUserBox.getText(), counterx, countery);
                
                if (addCounter <= 7) {
                    counterx += 100;
                    addCounter++;
                }
                if (addCounter == 8) {
                    counterx -= 900;
                    countery += 100;
                    addCounter++;
                }
                if (addCounter > 8 && addCounter < 18) {
                    counterx += 100;
                    addCounter++;
                }
                if (addCounter == 18) {
                    counterx -= 900;
                    countery += 100;
                    addCounter++;
                }
                if (addCounter > 18) {
                    counterx += 100;
                    addCounter++;
                }
             // Setting prompt	
                addingUserAlert.setContentText(	
                        addUserBox.getText() + " has been successfully added!");	
                // Show prompt	
                addingUserAlert.show();	
                }	
                else {	
                    // set alert type	
                    addingUserAlert.setAlertType(AlertType.ERROR);	
                    // Setting prompt	
                    addingUserAlert.setContentText(	
                    addUserBox.getText() + " has not been added to the network because\n" + "the user is already in the network!");	
                    // Show prompt	
                    addingUserAlert.show();
                    
                    }
                }
            
        };
        addUserButton.setOnAction(event);

        return addUserMenu;

    }
    /*
    * Sets up the remove user menu box for the scene	
    * 	
    * @return the HBox of the remove user menu	
    */
    private HBox setUpRemoveUserMenu() {

        removeUserMenu = new HBox();

        TextField removeUserBox = new TextField();
        Button removeUserButton = new Button("Remove User");
        removeUserMenu.getChildren().addAll(removeUserBox, removeUserButton);

        Alert removingUserAlert = new Alert(AlertType.NONE);

        // action event for removeUser button
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	if(removeUserBox.getText().length() <= 0) {	
                 	// set alert type	
                     removingUserAlert.setAlertType(AlertType.ERROR);	
                     // Setting prompt	
                     removingUserAlert.setContentText("Please insert a name to remove from the network!");	
                     // Show prompt	
                     removingUserAlert.show();	
                 }	
            else if(socialNetwork.removeUser(removeUserBox.getText()) == true){
                socialNetwork.removeUser(removeUserBox.getText());
                UserGUI removeUserGUI = findUserGUI(removeUserBox.getText());

                // Remove GUI
                double a = removeUserGUI.guiSize[0];
                double b = removeUserGUI.guiSize[1];

                gc.clearRect(a, b, 55.0, 55.0);

                // set alert type
                removingUserAlert.setAlertType(AlertType.CONFIRMATION);	
                // Setting prompt	
                removingUserAlert.setContentText(	
                        removeUserBox.getText() + " has been successfully removed!");	
                // Show prompt	
                removingUserAlert.show();	
                	
                }	
                else {	
                	 // set alert type	
                    removingUserAlert.setAlertType(AlertType.ERROR);	
                    // Setting prompt	
                    removingUserAlert.setContentText(	
                    removeUserBox.getText() + " has not been removed from the network because\n" + "the user is not in the network!");	
                    // Show prompt	
                    removingUserAlert.show();
                    }
                }
            
        };
        removeUserButton.setOnAction(event1);

        return removeUserMenu;

    }

    /**	
     * Sets up the search menu box for the scene	
     * 	
     * @return the HBox of the search user menu	
     */
    private HBox setUpSearchUserMenu() {

        searchUserMenu = new HBox();

        TextField searchUserBox = new TextField();
        Button searchUserButton = new Button("Search User");

        Alert searchingUserAlert = new Alert(AlertType.NONE);

        // action event for searchUser button
        EventHandler<ActionEvent> searchUserEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                UserGUI centralUserGUI = findUserGUI(searchUserBox.getText());

                // Remove GUI
                if(searchUserBox.getText().length() <= 0) {	
                	// set alert type	
                    searchingUserAlert.setAlertType(AlertType.ERROR);	
                    // Setting prompt	
                    searchingUserAlert.setContentText("Please insert a name to search the network!");	
                    // Show prompt	
                    searchingUserAlert.show();	
                    return;	
                }	
                else if(socialNetwork.selectCentUser(searchUserBox.getText()) != false) {	
                    // Remove GUI	
                    double a = centralUserGUI.guiSize[0];	
                    double b = centralUserGUI.guiSize[1];	
                	 gc.drawImage(searchCircledNumber(searchUserBox.getText()), a, b);
                // set alert type
                int userTextLength = searchUserBox.getText().length();

                for (int i = 0; i <= userTextLength; i++) {
                	if (socialNetwork.selectCentUser(searchUserBox.getText()) != false) {	
                        searchingUserAlert.setAlertType(AlertType.CONFIRMATION);	
                        searchingUserAlert.setContentText("Found! " + searchUserBox.getText() + " has been found in the network!");
                        searchingUserAlert.show();
                        break;
                    }

                    else {
                        // set alert type
                        searchingUserAlert.setAlertType(AlertType.ERROR);
                        // Setting prompt
                        searchingUserAlert.setContentText(searchUserBox.getText()
                                + " is NOT in the network! Feel free to add the user!");
                        // Show prompt
                        searchingUserAlert.show();

                    }
                }
            }
            }
        };
        
        searchUserButton.setOnAction(searchUserEvent);
        searchUserMenu.getChildren().addAll(searchUserBox, searchUserButton);

        return searchUserMenu;

    }

    /**	
     * Sets up the find mutuals menu box for the scene	
     * 	
     * @return the HBox of the find mutuals menu	
     */
    private HBox setUpFindMutualsMenu() {

        findMutualsMenu = new HBox();

        TextField findMutualsBox = new TextField();
        Button findMutualsButton = new Button("Find Mutuals");

        Alert findMutualAlert = new Alert(AlertType.NONE);

        // action event for findMutuals button
        EventHandler<ActionEvent> findMutualsEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // set alert type
                int userTextLength = findMutualsBox.getText().length();
                //Store the central user information
                String cenUser = socialNetwork.cenUser;
                //Store user input
                String findMutuals = findMutualsBox.getText();
                for (int i = 0; i <= userTextLength; i++) {
                    if (findMutualsBox.getText().charAt(i) == ' '
                            || findMutualsBox.getText().charAt(i) == '\"') {
                        findMutualAlert.setAlertType(AlertType.ERROR);

                        findMutualAlert.setContentText("Username can't have "
                                + findMutualsBox.getText().charAt(i) + " in it");

                        findMutualAlert.show();
                        break;
                    }

                    else {
                    	Set<Person> mutualList = socialNetwork.getMutualFriends(cenUser, findMutuals);
                    	List<Person> mutuals = new ArrayList<Person>();	
                        mutuals.addAll(mutualList);	
                        List<String> mutualStrings = new ArrayList<String>();	
                        String out ="--";
                        for (int j = 0; j < mutuals.size(); j++) {	
                            mutualStrings.add(mutuals.get(j).getName());	
                        }	
                        for (String mutualUser : mutualStrings) {	
                        	out = out+mutualUser;
                        }
                     // set alert type
                        findMutualAlert.setAlertType(AlertType.CONFIRMATION);

                        // Setting prompt
                        findMutualAlert.setContentText("Mutuals for "+cenUser+" are: "+out);

                        // Show prompt
                        findMutualAlert.show();
                    }
                }
            }
        };

        findMutualsButton.setOnAction(findMutualsEvent);
        findMutualsMenu.getChildren().addAll(findMutualsBox, findMutualsButton);

        return findMutualsMenu;

    }
    /**
    * Sets up the right menu side of the scene	
    * 	
    * @return the VBox of the right menu of of the scene	
    */
    private VBox setUpRightMenu(GraphicsContext gc,boolean scene2) {

        VBox rightMenu = new VBox(15);

        addFriendsMenu = setUpAddFriendMenu();

        removeFriendsMenu = setUpRemoveFriendMenu();
        

        selectCentralUserMenu1 = setUpCentralUserMenu(gc,scene2);
        rightMenu.getChildren().addAll(addFriendsMenu, removeFriendsMenu, selectCentralUserMenu1);


        return rightMenu;
    }

    /**	
     * Sets up the central user menu box for the scene	
     * 	
     * @return the VBox of the central user menu for the scene	
     */
    private VBox setUpCentralUserMenu(GraphicsContext gc,boolean scene2) {

        selectCentralUserMenu1 = new VBox();
        Button setCentralUserButton = new Button("Set central user");
        TextField centralUserBox = new TextField();


        selectCentralUserMenu1.getChildren().addAll(centralUserBox, setCentralUserButton);
        Alert cenUserAlert = new Alert(AlertType.NONE);

        // action event for addFriends button
        EventHandler<ActionEvent> cenUserEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                String prevCen = socialNetwork.cenUser;
                if(scene2==true) {
                if(prevCen != null && !prevCen.isEmpty()) {     
                    UserGUI prev = findUserGUI(prevCen);
                    
                    // find the position of previous central user node
                    double x = prev.guiSize[0];
                    double y = prev.guiSize[1];
                    
                    drawNode(gc, prevCen, x, y);
                }
                }
                else {
                	if(prevCen != null && !prevCen.isEmpty()) {     
                        
                        // find the position of previous central user node
                        double x = 450.0;
                        double y = 300.0;
                        
                        drawNode(gc, prevCen, x, y);
                    }
                }
                
                UserGUI centralUserGUI = findUserGUI(centralUserBox.getText());
                socialNetwork.cenUser = centralUserBox.getText();

                // Draw Central User
                if(scene2==true) {
                	   double a = centralUserGUI.guiSize[0];
                       double b = centralUserGUI.guiSize[1];

                       gc.drawImage(centralCircledNumber(centralUserBox.getText()), a, b);
                }
                else {
                	double a =centralUserGUI.guiSize2[0];
                    double b = centralUserGUI.guiSize2[1];

                    gc.drawImage(centralCircledNumber(centralUserBox.getText()), a, b);
                }
             

                // set alert type
                int userTextLength = centralUserBox.getText().length();
                for (int i = 0; i <= userTextLength; i++) {
                    if (centralUserBox.getText().charAt(i) == ' '
                            || centralUserBox.getText().charAt(i) == '\"') {
                        cenUserAlert.setAlertType(AlertType.ERROR);
                        cenUserAlert.setContentText("Username can't have "
                                + centralUserBox.getText().charAt(i) + " in it");
                        cenUserAlert.show();
                        break;
                    } else {
                        // set alert type
                        cenUserAlert.setAlertType(AlertType.CONFIRMATION);
                        // Setting prompt
                        cenUserAlert.setContentText(centralUserBox.getText()
                                + " has been successfully became a central user!");
                        // Show prompt
                        cenUserAlert.show();
                    }
                }
            }
        };
        setCentralUserButton.setOnAction(cenUserEvent);

        return selectCentralUserMenu1;
    }
    private double[] setUpCentralUserInScene3() {
    	UserGUI centralUserGUI = findUserGUI(socialNetwork.cenUser);
        centralUserGUI.guiSize[0] = 450.0;
        centralUserGUI.guiSize[1] = 300.0;
        double x=centralUserGUI.guiSize[0];
        double y=centralUserGUI.guiSize[1];
        
    	String centralUser = socialNetwork.cenUser;
    	socialNetwork.selectCentUser(centralUser);
    	
    	double centralUserPosition[]= {x,y};
    	drawNode(gc2,centralUser,x,y);
		return centralUserPosition;
    }

    /**
    *Sets up the add friend menu box for the scene
    *@return the VBox of the add friend menu for the scene
    */
    private VBox setUpAddFriendMenu() {

        addFriendsMenu = new VBox();
        TextField addFriendsBox = new TextField();
        Button addFriendsButton = new Button("Add Friend");

        Alert addFriendAlert = new Alert(AlertType.NONE);

        // action event for addFriends button
        EventHandler<ActionEvent> addFriendsEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	socialNetwork.addFriends(socialNetwork.cenUser, addFriendsBox.getText());
            	UserGUI addUser = new UserGUI(addFriendsBox.getText(), counterx, countery);
                userGUIList.add(addUser);

            	UserGUI addFriendsGUI = findUserGUI(addFriendsBox.getText());
            	addFriendsGUI.guiSize2[0]=randomPositionGenerator()[0];
            	addFriendsGUI.guiSize2[1]=randomPositionGenerator()[1];
                
                UserGUI centralUserGUI = findUserGUI(socialNetwork.cenUser);
                double x = centralUserGUI.guiSize[0];
                double y = centralUserGUI.guiSize[1];

                double a = addFriendsGUI.guiSize2[0];
                double b = addFriendsGUI.guiSize2[1];
                drawEdge(gc2, x, y, a, b);
                drawNode(gc2, addFriendsGUI.name,a,b);
                

                int userTextLength = addFriendsBox.getText().length();

                for (int i = 0; i <= userTextLength; i++) {
                    if (addFriendsBox.getText().charAt(i) == ' '
                            || addFriendsBox.getText().charAt(i) == '\"') {
                        addFriendAlert.setAlertType(AlertType.ERROR);

                        addFriendAlert.setContentText("Username can't have "
                                + addFriendsBox.getText().charAt(i) + " in it");

                        addFriendAlert.show();
                        break;
                    }

                    else {
                        // addFriends from sociaNetwork class
                        
                        // addFriendsGUI


                        // set alert type
                        addFriendAlert.setAlertType(AlertType.CONFIRMATION);

                        // Setting prompt
                        addFriendAlert.setContentText(
                                addFriendsBox.getText() + " has been successfully added!");

                        // Show prompt
                        addFriendAlert.show();
                    }
                }
            }
        };

        addFriendsButton.setOnAction(addFriendsEvent);
        addFriendsMenu.getChildren().addAll(addFriendsBox, addFriendsButton);

        return addFriendsMenu;

    }

    /**	
     * Sets up the remove friend menu box for the scene	
     * 	
     * @return the VBox of the remove friend menu for the scene	
     */
    private VBox setUpRemoveFriendMenu() {

        removeFriendsMenu = new VBox();
        TextField removeFriendsBox = new TextField();
        Button removeFriendsButton = new Button("Remove Friend");

        Alert removeFriendAlert = new Alert(AlertType.NONE);

        // action event for removeFriends button
        EventHandler<ActionEvent> removeFriendsEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                UserGUI removeFriendGUI = findUserGUI(removeFriendsBox.getText());

                UserGUI centralUserGUI = findUserGUI(socialNetwork.cenUser);
                
                socialNetwork.removeFriends(socialNetwork.cenUser, removeFriendsBox.getText());

                double x = centralUserGUI.guiSize[0];
                double y = centralUserGUI.guiSize[1];

                double a = removeFriendGUI.guiSize2[0];
                double b = removeFriendGUI.guiSize2[1];
                gc2.clearRect(a, b, x-55, y-55); //TODO
                removeEdge(gc2, x, y, a, b);

                int userTextLength = removeFriendsBox.getText().length();

                for (int i = 0; i <= userTextLength; i++) {
                    if (removeFriendsBox.getText().charAt(i) == ' '
                            || removeFriendsBox.getText().charAt(i) == '\"') {
                        removeFriendAlert.setAlertType(AlertType.ERROR);

                        removeFriendAlert.setContentText("Username can't have "
                                + removeFriendsBox.getText().charAt(i) + " in it");

                        removeFriendAlert.show();
                        break;
                    }

                    else {
                        // removeFriends from socialNetwork class
                        SocialNetwork.removeFriends(socialNetwork.cenUser,
                                removeFriendsBox.getText());
                        // set alert type
                        removeFriendAlert.setAlertType(AlertType.CONFIRMATION);

                        // Setting prompt
                        removeFriendAlert.setContentText(
                                removeFriendsBox.getText() + " has been successfully removed!");

                        // Show prompt
                        removeFriendAlert.show();
                    }
                }
            }
        };

        removeFriendsButton.setOnAction(removeFriendsEvent);
        removeFriendsMenu.getChildren().addAll(removeFriendsBox, removeFriendsButton);

        return removeFriendsMenu;

    }
 

    /**	
     * Sets up a canvas for all users	
     * 	
     * @return the VBox that is the canvas for the scene	
     */
    private VBox setUpCanvasBox() {
        // Canvas
        VBox canvasBoxes = new VBox();
        canvasBoxes.setBackground(new Background(
                new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        canvasBoxes.getChildren().addAll(canvas);

        return canvasBoxes;
    }

    /**	
     * Sets up a canvas for all users	
     * 	
     * @return the VBox that is the canvas for the scene
     */
    private VBox setUpCanvasBox2() {
        // Canvas
        VBox canvasBoxes = new VBox();
        canvasBoxes.setBackground(new Background(
                new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        canvasBoxes.getChildren().addAll(canvas2);

        return canvasBoxes;
    }

    /**	
     * Finds the UserGUI of the specified name	
     * 	
     * @return the UserGUI of the specified name	
     */
    private UserGUI findUserGUI(String name) {
        UserGUI found = new UserGUI();
        for (UserGUI user : userGUIList) {
            if (user.name.equals(name)) {
                found = user;
            }
        }
        return found;
    }

     /**
    *Sets up the save button function
    *@return the EventHandler that will implement the save function
    */
    private EventHandler<ActionEvent> setUpSave() {

        // action event for addFriends button
        EventHandler<ActionEvent> saveEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                File saveFile = new File("log.txt");
                socialNetwork.saveToFile(saveFile);
            }
        };
        return saveEvent;

    }

    /**
     * Draws the graph for the entire social network.
     * 
     * @param gc - Canvas
     */
    private void drawGraphScene2(GraphicsContext gc) {
        List<String> people = socialNetwork.getAllPeople();
        for(String name : people) {
            UserGUI addUserGUI = new UserGUI(name, counterx, countery);
            userGUIList.add(addUserGUI);
            socialNetwork.addUser(name);
            drawNode(gc, name, counterx, countery);
            
            if (addCounter <= 7) {
                counterx += 100;
                addCounter++;
            }
            if (addCounter == 8) {
                counterx -= 900;
                countery += 100;
                addCounter++;
            }
            if (addCounter > 8 && addCounter < 18) {
                counterx += 100;
                addCounter++;
            }
            if (addCounter == 18) {
                counterx -= 900;
                countery += 100;
                addCounter++;
            }
            if (addCounter > 18) {
                counterx += 100;
                addCounter++;
            }
        }

        String cenUser = socialNetwork.cenUser;
        if(cenUser!=null) {
        	 UserGUI centralUserGUI = findUserGUI(cenUser);
             socialNetwork.selectCentUser(cenUser);

             double a = centralUserGUI.guiSize[0];
             double b = centralUserGUI.guiSize[1];
             gc.drawImage(centralCircledNumber(cenUser), a, b);
        }
        	
       

        

    }
    
    private void drawGraphScene3(GraphicsContext gc) {
    	String cenUser = socialNetwork.cenUser;	
        Set<Person> people = socialNetwork.getFriends(cenUser);	
        List<Person> friends = new ArrayList<Person>();	
        friends.addAll(people);	
        List<String> friendList = new ArrayList<String>();	
        for (int i = 0; i < friends.size(); i++) {	
            friendList.add(friends.get(i).getName());	
        }	
        for (String friend : friendList) {	
            UserGUI addFriendsGUI = findUserGUI(friend);	
            addFriendsGUI.guiSize2[0] = randomPositionGenerator()[0];	
            addFriendsGUI.guiSize2[1] = randomPositionGenerator()[1];	
            UserGUI centralUserGUI = findUserGUI(cenUser);	
            double x = centralUserGUI.guiSize[0];	
            double y = centralUserGUI.guiSize[1];	
            double a = addFriendsGUI.guiSize2[0];	
            double b = addFriendsGUI.guiSize2[1];	
            drawNode(gc2, friend, a, b);	
            drawEdge(gc2, x, y, a, b);
        }
    }

    /**
     * Creates a circle that represents the currently added user.
     * 
     * @param gc   - Canvas
     * @param name - name of user to place in node
     * @param x    - x coordinate for alignment
     * @param y    - y coordinate for alignment
     */
    private void drawNode(GraphicsContext gc, String name, double x, double y) {
        gc.drawImage(createCircledNumber(name), x, y);
    }

    /**
     * Creates an edge that represents a relationship between two users
     * 
     * @param gc
     * @param x1 - Coordinates
     * @param y1 - Coordinates
     * @param x2 - Coordinates
     * @param y2 - Coordinates
     */
    private void drawEdge(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeLine(x1 + 55/2, y1 + 55 / 2, x2+55/2, y2 + 55 / 2); //TODO
    }

    /**
     * Creates an edge that represents a relationship between two users
     * 
     * @param gc
     * @param x1 - Coordinates
     * @param y1 - Coordinates
     * @param x2 - Coordinates
     * @param y2 - Coordinates
     */
    private void removeEdge(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        gc.setStroke(Color.DARKGREEN);
        gc.setLineWidth(3);
        gc.strokeLine(x1 + 55, y1 + 55 / 2, x2, y2 + 55 / 2); //TODO
    }

    /**
     * Gets the username from x and y coordinates
     * 
     * @param x - x coordinate
     * @param y - y coordinate
     */
    private String getNameFromCoordinates(double x, double y) {
        return null;
    }

    /**
     * Sets the selected user with the given string
     * 
     * @param s
     */
    private void setSelectedUser(String s) {
        socialNetwork.cenUser = s;
    }
    
    private double[] randomPositionGenerator() {
    	UserGUI centralUserGUI = findUserGUI(socialNetwork.cenUser);
        double x = centralUserGUI.guiSize[0];
        double y = centralUserGUI.guiSize[1];
        double rectangleCentralUser[] = {x-300.0, y-300.0, x+300.0, y+300.0};
        Random random = new Random();
        
        
        double rangeX = rectangleCentralUser[0] + (rectangleCentralUser[2] - rectangleCentralUser[0]) * random.nextDouble();
        double rangeY = rectangleCentralUser[1] + (rectangleCentralUser[3] - rectangleCentralUser[1]) * random.nextDouble();
        
        double range[]= {rangeX,rangeY};
        return range;
        		
        }

    private WritableImage createCircledNumber(String name) {
        // createCircledNumber() method always returns 26px X 26px sized image
        StackPane sPane = new StackPane();
        sPane.setPrefSize(26, 26);

        Circle c = new Circle(50 / 2.0); // Change size of circle
        c.setStroke(Color.BLACK);
        c.setFill(Color.WHITE);
        c.setStrokeWidth(3);
        sPane.getChildren().add(c);

        Text txtNum = new Text(name + "");
        sPane.getChildren().add(txtNum);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        return sPane.snapshot(parameters, null);
    }

    private WritableImage centralCircledNumber(String name) {
        // createCircledNumber() method always returns 26px X 26px sized image
        StackPane sPane = new StackPane();
        sPane.setPrefSize(26, 26);
        Circle c = new Circle(50 / 2.0); // Change size of circle
        c.setStroke(Color.ORANGE);
        c.setFill(Color.TRANSPARENT);
        c.setStrokeWidth(3);
        sPane.getChildren().add(c);
        Text txtNum = new Text(name + "");
        sPane.getChildren().add(txtNum);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        return sPane.snapshot(parameters, null);
    }

    private WritableImage searchCircledNumber(String name) {
        // createCircledNumber() method always returns 26px X 26px sized image
        StackPane sPane = new StackPane();
        sPane.setPrefSize(26, 26);
        Circle c = new Circle(50 / 2.0); // Change size of circle
        c.setStroke(Color.BLACK);
        c.setFill(Color.AQUA);
        c.setStrokeWidth(3);
        sPane.getChildren().add(c);
        Text txtNum = new Text(name + "");
        sPane.getChildren().add(txtNum);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        return sPane.snapshot(parameters, null);
    }

    /**
     * Opens up File Explorer from desktop when prompted TODO: File might need to be
     * String. We need to create the file with the specific path in order for
     * loadFromFile to work.
     * 
     * @param file
     */
    private void openFile(File file) {
        socialNetwork.loadFromFile(file);
        drawGraphScene2(gc);
//        drawGraphScene3(gc);
    }

    /**
     * Main method
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
