package DomiNations;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Interface extends Application {
    public static void start() {
        launch(); // set up program as a javafx applicaton
    }

    @Override
    public void start(Stage primaryStage) throws Exception { // main javafx code
        primaryStage.setTitle("DomiNations"); // set the window title

        Button button = new Button(); // create a button
        button.setText("Click me");

        StackPane layout = new StackPane(); // create a layout
        layout.getChildren().add(button); // add the button into the layout

        Scene scene = new Scene(layout, 300,255); // create a new scene
        primaryStage.setScene(scene); // add the scene to the Stage
        primaryStage.show(); // display
    }
}
