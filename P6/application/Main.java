package application;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import java.io.FileInputStream;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * JDK 11, Eclipse 2019-06
 * https://gluonhq.com/products/javafx/
 * https://openjfx.io/openjfx-docs/
 *
 * @author Ye Ji Kim
 */
public class Main extends Application {
    // store any command-line arguments that were entered.
    // NOTE: this.getParameters().getRaw() will get these also
    private List<String> args; // instance field - access

    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 250;
    private static final String APP_TITLE = "CS400 MyFirstJavaFX";
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // Main layout is Border Pane
        BorderPane root = new BorderPane();
        
        // add a combo box
        AnchorPane option = new AnchorPane();
        option.getChildren().add(new Label("Choose options"));
        ComboBox<String> combobox = new ComboBox<String>();
        combobox.getItems().addAll("option1", "option2", "option3");
        option.getChildren().add(combobox);
        option.setMaxSize(50, 150);
        option.setBottomAnchor(combobox, 100.0);
        
        // add an ImageView of an image
        FileInputStream input = new FileInputStream("mypic.jpg");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        // adjust the size of image
        imageView.setFitHeight(100);
        imageView.setFitWidth(80);
        
        // add a Button with label
        AnchorPane bottom = new AnchorPane();
        bottom.getChildren().add(new Button("Done"));
        bottom.setMaxSize(50, 50);
        
        // add a CheckBox
        CheckBox check1 = new CheckBox("Blue");
        CheckBox check2 = new CheckBox("Red");
        CheckBox check3 = new CheckBox("Yellow");

        AnchorPane check = new AnchorPane();
        check.getChildren().add(new Label("Colors"));
        
        check.getChildren().add(check1);
        check.setBottomAnchor(check1, 120.0);
        check.getChildren().add(check2);
        check.setBottomAnchor(check2, 80.0);
        check.getChildren().add(check3);
        check.setBottomAnchor(check3, 100.0);
        
        root.setTop(new Label(APP_TITLE)); // add a Label in the top panel
        root.setLeft(option); // combo box to the left panel
        root.setCenter(imageView); // put image in the center panel
        root.setBottom(bottom); // put Button in the bottom
        root.setRight(check);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        
        // change the width and height of scene
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        
        primaryStage.setTitle(APP_TITLE);
        primaryStage.show();

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
           launch(args);
    }
}

