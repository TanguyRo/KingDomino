package DomiNations;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Interface2test extends Application {
    public static void start() {
        launch(); // set up program as a javafx applicaton
    }

    Scene scene1, scene2;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Interface2test");

//Scene 1
        Label label1= new Label("Choix du nombre de joueurs");
        Button buttonValider= new Button("Valider");
        buttonValider.setOnAction(e -> primaryStage.setScene(scene2));
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, buttonValider);
        scene1= new Scene(layout1, 300, 250);

        ToggleGroup group = new ToggleGroup();
        RadioButton button2 = new RadioButton("2");
        button2.setToggleGroup(group);
        button2.setSelected(true);
        RadioButton button3 = new RadioButton("3");
        button3.setToggleGroup(group);
        RadioButton button4 = new RadioButton("4");
        button4.setToggleGroup(group);

//Scene 2
        Label label2= new Label("This is the second scene");
        Button button2scene1= new Button("Go to scene 1");
        button2scene1.setOnAction(e -> primaryStage.setScene(scene1));
        VBox layout2= new VBox(20);
        layout2.getChildren().addAll(label2, button2scene1);
        scene2= new Scene(layout2,300,250);


        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

